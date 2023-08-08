package com.ren.blog.posts;

public record PostResponseDTO(Long id, String title, String content) {
  public PostResponseDTO(Post post) {
    this(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt());
  }
}
