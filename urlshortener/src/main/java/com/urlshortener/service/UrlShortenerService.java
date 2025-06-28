package com.urlshortener.service;

import com.urlshortener.exception.CustomAliasAlreadyExistsException;
import com.urlshortener.exception.UrlExpiredException;
import com.urlshortener.exception.UrlNotFoundException;
import com.urlshortener.model.UrlMapping;
import com.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    /**
     * Shortens a given long URL without custom alias or expiry.
     *
     * @param longUrl The original long URL to shorten.
     * @return The generated short code.
     */
    public String shortenUrl(String longUrl) {
        return shortenUrl(longUrl, null, null);
    }

    /**
     * Shortens a given long URL with optional custom alias and expiry.
     *
     * @param longUrl The original long URL to shorten.
     * @param customAlias Optional custom alias for the short URL.
     * @param expiresAt Optional expiry date/time for the short URL.
     * @return The generated short code or custom alias.
     * @throws CustomAliasAlreadyExistsException if the custom alias is already in use.
     */
    public String shortenUrl(String longUrl, String customAlias, LocalDateTime expiresAt) {
        // Handle custom alias
        if (customAlias != null && !customAlias.isBlank()) {
            // Check if alias already exists
            Optional<UrlMapping> aliasMapping = urlMappingRepository.findByCustomAlias(customAlias);
            if (aliasMapping.isPresent()) {
                throw new CustomAliasAlreadyExistsException("Custom alias already in use.");
            }
            // Save with custom alias
            UrlMapping mapping = new UrlMapping();
            mapping.setLongUrl(longUrl);
            mapping.setCustomAlias(customAlias);
            mapping.setShortCode(customAlias); // Use alias as short code
            mapping.setCreatedAt(LocalDateTime.now());
            mapping.setExpiresAt(expiresAt);
            urlMappingRepository.save(mapping);
            return customAlias;
        }

        // Check if URL already exists (and no custom alias)
        Optional<UrlMapping> existing = urlMappingRepository.findByLongUrl(longUrl);
        if (existing.isPresent()) {
            return existing.get().getShortCode();
        }

        // Save to get an auto-generated ID
        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setExpiresAt(expiresAt);
        mapping = urlMappingRepository.save(mapping);

        // Generate Base62 short code from ID
        String shortCode = encodeBase62(mapping.getId());
        mapping.setShortCode(shortCode);
        urlMappingRepository.save(mapping);

        return shortCode;
    }

    /**
     * Retrieves a UrlMapping entity by its short code.
     *
     * @param shortCode The short code or alias.
     * @return An Optional containing the UrlMapping if found, otherwise empty.
     */
    public Optional<UrlMapping> getByShortCode(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode);
    }

    /**
     * Retrieves the original long URL for a given short code.
     * Increments the click count and checks for expiry.
     *
     * @param shortCode The short code or alias.
     * @return An Optional containing the original long URL if found and not expired.
     * @throws UrlExpiredException if the short URL has expired.
     * @throws UrlNotFoundException if the short URL does not exist.
     */
    public Optional<String> retrieveOriginalUrl(String shortCode) {
        Optional<UrlMapping> mappingOpt = urlMappingRepository.findByShortCode(shortCode);
        if (mappingOpt.isPresent()) {
            UrlMapping mapping = mappingOpt.get();
            // Check expiry
            if (mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new UrlExpiredException("This short URL has expired.");
            }
            // Optionally increment click count
            mapping.setClickCount(mapping.getClickCount() == null ? 1 : mapping.getClickCount() + 1);
            urlMappingRepository.save(mapping);
            return Optional.of(mapping.getLongUrl());
        }
        throw new UrlNotFoundException("Short URL not found.");
    }

    /**
     * Encodes a numeric ID to a Base62 string.
     *
     * @param num The numeric ID to encode.
     * @return The Base62 encoded string.
     */
    private String encodeBase62(Long num) {
        StringBuilder sb = new StringBuilder();
        if (num == 0) return "0";
        while (num > 0) {
            sb.append(BASE62.charAt((int)(num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}