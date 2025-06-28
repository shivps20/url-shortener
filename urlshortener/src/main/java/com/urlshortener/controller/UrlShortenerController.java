package com.urlshortener.controller;

import com.urlshortener.dto.OriginalUrlResponse;
import com.urlshortener.dto.ShortenUrlRequest;
import com.urlshortener.dto.ShortenUrlResponse;
import com.urlshortener.service.UrlShortenerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    @Operation(summary = "Shorten a URL", description = "Returns a shortened URL for the given original URL.")
    public ResponseEntity<ShortenUrlResponse> createShortUrl(@RequestBody ShortenUrlRequest request) {
        String shortUrl = urlShortenerService.shortenUrl(
            request.getOriginalUrl(),
            request.getCustomAlias(),
            request.getExpiresAt()
        );
        return ResponseEntity.ok(
            new ShortenUrlResponse(
                shortUrl,
                request.getCustomAlias(),
                request.getOriginalUrl(),
                "Short URL created successfully"
            )
        );
    }

    @GetMapping("/original")
    public ResponseEntity<OriginalUrlResponse> getOriginalUrl(@RequestParam String shortUrl) {
        String url = urlShortenerService.retrieveOriginalUrl(shortUrl).get();
        return ResponseEntity.ok(new OriginalUrlResponse(url));
    }
}