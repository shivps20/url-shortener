package com.urlshortener.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortenUrlRequest {
    private String originalUrl;
    private String customAlias; // Optional custom alias
    private LocalDateTime expiresAt; // Optional expiry
}