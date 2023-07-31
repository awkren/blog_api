package com.ren.blog.posts;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @NotEmpty private String title;
  @NotEmpty private String content;

  public Post(PostRequestDTO data) {
    this.title = data.title();
    this.content = data.content();
  }

  public void update(PostRequestDTO data) {
    this.title = data.title();
    this.content = data.content();
  }
}
