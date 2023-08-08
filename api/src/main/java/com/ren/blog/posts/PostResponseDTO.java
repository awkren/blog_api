package com.ren.blog.posts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record PostResponseDTO(Long id, String title, String content, LocalDateTime createdAt) {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");

  public PostResponseDTO(Post post) {
    this(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt());
  }

  public String getFormattedCreatedAt() {
    return createdAt.format(FORMATTER);
  }
}
