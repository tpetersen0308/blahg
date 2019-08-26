package blahg.controllers;

import java.util.List;
import blahg.models.post.Post;
import blahg.models.post.PostNotFoundException;
import blahg.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class PostController {
    
  private final PostRepository repository;

  PostController(PostRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/posts")
  List<Post> all() {
    return repository.findAll();
  }

  @GetMapping("/posts/{id}")
  Post one(@PathVariable long id) {
    return repository.findById(id)
      .orElseThrow(() -> new PostNotFoundException(id));
  }

  @PostMapping("/posts")
  Post newPost(@RequestBody Post newPost) {
    return repository.save(newPost);
  }

  @PutMapping("/posts/{id}")
  Post updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
    return repository.findById(id)
      .map(post -> {
          post.setTitle(updatedPost.getTitle());
          post.setBody(updatedPost.getBody());
          return repository.save(post);
        }
      )
      .orElseThrow(() -> new PostNotFoundException(id));
  }

  @DeleteMapping("/posts/{id}")
  void deletePost(@PathVariable long id) {
    repository.deleteById(id);
  } 
}
