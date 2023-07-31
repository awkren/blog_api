package com.ren.blog.controller;

import com.ren.blog.exception.PostNotFoundException;
import com.ren.blog.posts.Post;
import com.ren.blog.posts.PostRepository;
import com.ren.blog.posts.PostRequestDTO;
import com.ren.blog.posts.PostResponseDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/{id}")
  public ResponseEntity<Object> getSinglePost(@PathVariable(value = "id") Long id) {
    Optional<Post> getPost = postRepository.findById(id);
    if (getPost.isEmpty()) {
      throw new PostNotFoundException("Post not found.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(getPost.get());
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping
  public void savePost(@RequestBody PostRequestDTO data) {
    Post postData = new Post(data);
    postRepository.save(postData);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deletePost(@PathVariable(value = "id") Long id) {
    Optional<Post> getPost = postRepository.findById(id);
    if (getPost.isEmpty()) {
      throw new PostNotFoundException("Post not found.");
    }
    postRepository.delete(getPost.get());
    return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully!");
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updatePost(
      @PathVariable(value = "id") Long id, @RequestBody PostRequestDTO data) {
    Optional<Post> getPost = postRepository.findById(id);
    if (getPost.isEmpty()) {
      throw new PostNotFoundException("Post not found.");
    }
    Post postData = getPost.get();
    postData.update(data);
    Post updatedPost = postRepository.save(postData);
    return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
  }
}
