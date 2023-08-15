package com.ren.blog.posts;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Table(name = "posts")
@Entity(name = "posts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String title;
  @NotEmpty
  private String content;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
  private List<Comment> comments;

  public Post(PostRequestDTO data) {
    this.title = data.title();
    this.content = data.content();
    this.createdAt = data.createdAt();
  }

  public void update(PostRequestDTO data) {
    this.title = data.title();
    this.content = data.content();
    this.createdAt = data.createdAt();
  }
}
