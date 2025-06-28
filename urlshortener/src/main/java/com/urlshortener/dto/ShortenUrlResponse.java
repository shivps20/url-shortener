package com.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShortenUrlResponse {
    private String shortUrl;
    private String customAlias; // Return custom alias if used
    private String originalUrl;
    private String message;
}