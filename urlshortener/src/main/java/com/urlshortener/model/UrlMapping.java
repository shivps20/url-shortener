package com.urlshortener.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "url_mapping")
@Data
@NoArgsConstructor
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", unique = true, nullable = false, length = 10)
    private String shortCode;

    @Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "click_count")
    private Long clickCount = 0L;

    @Column(name = "custom_alias", length = 20)
    private String customAlias;
}


/**
 * If you plan to support custom aliases, ensure your service/controller logic handles it.
 */