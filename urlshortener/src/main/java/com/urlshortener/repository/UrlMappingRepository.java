package com.urlshortener.repository;

import com.urlshortener.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    /**
     * Finds a UrlMapping by its short code.
     *
     * @param shortCode the short code or alias.
     * @return an Optional containing the UrlMapping if found, otherwise empty.
     */
    Optional<UrlMapping> findByShortCode(String shortCode);

    /**
     * Finds a UrlMapping by its custom alias.
     *
     * @param customAlias the custom alias.
     * @return an Optional containing the UrlMapping if found, otherwise empty.
     */
    Optional<UrlMapping> findByCustomAlias(String customAlias);

    /**
     * Finds a UrlMapping by its original long URL.
     *
     * @param longUrl the original long URL.
     * @return an Optional containing the UrlMapping if found, otherwise empty.
     */
    Optional<UrlMapping> findByLongUrl(String longUrl);
}