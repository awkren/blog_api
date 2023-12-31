package com.ren.blog.controller;

import com.ren.blog.exception.PostNotFoundException;
import com.ren.blog.posts.Comment;
import com.ren.blog.posts.CommentRepository;
import com.ren.blog.posts.Post;
import com.ren.blog.posts.PostRepository;
import com.ren.blog.posts.PostRequestDTO;
import com.ren.blog.posts.PostResponseDTO;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("posts")
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {

  @Autowired private PostRepository postRepository;
  @Autowired private CommentRepository commentRepository;

  @GetMapping // -> http://localhost:8080/posts
  public List<PostResponseDTO> getPosts(
      @RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {
    // Here in paging we define what we want to show. the 0 represents the page we
    // are,
    // and the 5 is the amount of content(in this case posts) we want per page.
    Pageable paging = PageRequest.of(page.orElse(0), 5, Sort.by(sortBy.orElse("id")));
    Page<Post> pagedResult = postRepository.findAll(paging);
    return pagedResult.getContent().stream().map(PostResponseDTO::new).toList();
  }

  @GetMapping("/{id}") // -> http://localhost:8080/posts/{id}
  public ResponseEntity<Object> getSinglePost(@PathVariable(value = "id") Long id) {
    Optional<Post> getPost = postRepository.findById(id);
    if (getPost.isEmpty()) {
      throw new PostNotFoundException("Post not found.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(getPost.get());
  }

  @GetMapping("/search") // -> http://localhost:8080/posts/search?keyword={word}
  public List<Post> searchPosts(@RequestParam(value = "keyword", required = false) String keyword) {
    if (keyword != null) {
      return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
          keyword, keyword);
    } else {
      return postRepository.findAll();
    }
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping // http://localhost:8080/posts
  public void savePost(@Valid @RequestBody PostRequestDTO data) {
    Post postData = new Post(data);
    postRepository.save(postData);
  }

  @DeleteMapping("/{id}") // -> http://localhost:8080/posts/{id}
  public ResponseEntity<Object> deletePost(@PathVariable(value = "id") Long id) {
    Optional<Post> getPost = postRepository.findById(id);
    if (getPost.isEmpty()) {
      throw new PostNotFoundException("Post not found.");
    }
    List<Comment> comments = commentRepository.findByPost(getPost.get());
    commentRepository.deleteAll(comments);
    postRepository.delete(getPost.get());
    return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully!");
  }

  @DeleteMapping // -> http://localhost:8080/posts
  public ResponseEntity<Object> deleteAllPosts() {
    postRepository.deleteAll();
    return ResponseEntity.status(HttpStatus.OK).body("All posts deleted successfully!");
  }

  @PutMapping("/{id}") // -> http://localhost:8080/posts/{id}
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

  @GetMapping("/{post_id}/comments/{id}") // -> http://localhost:8080/posts/{post_id}/comments/{id}
  public ResponseEntity<Object> getSingleComment(@PathVariable(value = "id") Long id){
    Optional<Comment> getComment = commentRepository.findById(id);
    if (getComment.isEmpty()) {
      throw new PostNotFoundException("Commentary not found.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(getComment.get());
  }

  @PostMapping("/{post_id}/comments") // -> http://localhost:8080/posts/{post_id}/comments
  public void saveComment(
      @PathVariable(value = "post_id") Long post_id, @Valid @RequestBody Comment comment) {
    Optional<Post> post = postRepository.findById(post_id);
    if (post.isEmpty()) {
      throw new PostNotFoundException("Post not found.");
    }

    comment.setPost(post.get());
    commentRepository.save(comment);
  }

  @GetMapping("/{post_id}/comments") // -> http://localhost:8080/posts/{post_id}/comments
  public List<Comment> getComments(@PathVariable(value = "post_id") Long post_id){
    Optional<Post> post = postRepository.findById(post_id);
    if(post.isEmpty()){
      throw new PostNotFoundException("Post not found.");
    }
    List<Comment> comments = commentRepository.findByPost(post.get());
    return comments;
  }

  @DeleteMapping("/{post_id}/comments/{commentary_id}")
  public ResponseEntity<Object> deleteSingleComment(@PathVariable(value = "commentary_id") Long commentaryId){
    Optional<Comment> getComment = commentRepository.findById(commentaryId);
    if(getComment.isEmpty()){
      throw new PostNotFoundException("Commentary not found.");
    }
    commentRepository.delete(getComment.get());
    return ResponseEntity.status(HttpStatus.OK).body("Commentary deleted successfully.");
  }
}