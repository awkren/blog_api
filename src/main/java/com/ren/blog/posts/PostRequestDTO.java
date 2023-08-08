package com.ren.blog.posts;

import java.time.LocalDateTime;

public record PostRequestDTO(String title, String content, LocalDateTime createdAt) {}
