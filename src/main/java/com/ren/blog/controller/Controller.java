package com.ren.blog.controller;

import com.ren.blog.posts.Post;
import com.ren.blog.posts.PostRepository;
import com.ren.blog.posts.PostRequestDTO;
import com.ren.blog.posts.PostResponseDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("posts")
public class Controller {

  @Autowired private PostRepository postRepository;

  @GetMapping
  public List<PostResponseDTO> getPosts() {
    List<PostResponseDTO> postList =
        postRepository.findAll().stream().map(PostResponseDTO::new).toList();
    return postList;
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping
  public void savePost(@RequestBody PostRequestDTO data) {
    Post postData = new Post(data);
    postRepository.save(postData);
  }
}
