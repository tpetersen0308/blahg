package blahg.models.post;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(long id) {
    super("Could not find post " + id);
  }
}
