package com.ren.blog.posts;

import java.time.LocalDateTime;

public record PostResponseDTO(Long id, String title, String content, LocalDateTime createdAt) {
  public PostResponseDTO(Post post) {
    this(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt());
  }
}
