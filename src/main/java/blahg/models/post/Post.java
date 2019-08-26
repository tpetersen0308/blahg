package blahg.models.post;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Post {

  @Id @GeneratedValue Long id;
  private String title;
  private String body;

  protected Post() {}

  public Post(String title, String body) {
    this.title = title;
    this.body = body;
  }
  
}

