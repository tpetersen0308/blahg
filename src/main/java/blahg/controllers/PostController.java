package blahg.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import blahg.models.post.Post;
import blahg.models.post.PostNotFoundException;
import blahg.models.post.PostResourceAssembler;
import blahg.repositories.PostRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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

  private final PostResourceAssembler assembler;

  PostController(PostRepository repository, PostResourceAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  @GetMapping("/posts")
  public Resources<Resource<Post>> all() {
    List<Resource<Post>> posts = repository
      .findAll()
      .stream()
      .map(assembler::toResource)
      .collect(Collectors.toList());

    return new Resources<>(posts,
        linkTo(methodOn(PostController.class).all()).withSelfRel());
  }

  @GetMapping("/posts/{id}")
  public Resource<Post> one(@PathVariable long id) {
    Post post = repository.findById(id)
      .orElseThrow(() -> new PostNotFoundException(id));

    return assembler.toResource(post);
  }

  @PostMapping("/posts")
  Resource<Post> newPost(@RequestBody Post newPost) {
    Post post = repository.save(newPost);
    
    return assembler.toResource(post);
  }

  @PutMapping("/posts/{id}")
  Resource<Post> updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
    return repository.findById(id)
      .map(post -> {
        post.setTitle(updatedPost.getTitle());
        post.setBody(updatedPost.getBody());
        return assembler.toResource(repository.save(post));
      })
      .orElseThrow(() -> new PostNotFoundException(id));
  }

  @DeleteMapping("/posts/{id}")
  void deletePost(@PathVariable long id) {
    repository.deleteById(id);
  } 
}
