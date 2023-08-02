package com.ren.blog.posts;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByTitleContainingIgnoreCaseOrContentContaining(String title, String content);
}
