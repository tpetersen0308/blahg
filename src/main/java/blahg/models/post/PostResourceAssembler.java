package blahg.models.post;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import blahg.controllers.PostController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class PostResourceAssembler implements ResourceAssembler<Post, Resource<Post>> {

  @Override
  public Resource<Post> toResource(Post post) {

    return new Resource<>(post,
      linkTo(methodOn(PostController.class).one(post.getId())).withSelfRel(),
      linkTo(methodOn(PostController.class).all()).withRel("posts"));
  }
}
