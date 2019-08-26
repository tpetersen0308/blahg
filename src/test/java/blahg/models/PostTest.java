package blahg.models;

import static org.junit.Assert.assertEquals;

import blahg.models.post.Post;
import blahg.repositories.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.name=blahg-test-h2","blahg.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostTest {

  @Autowired
  private PostRepository repository;

  @Test
  public void shouldFormatPostAsString() throws Exception {
    Post post = repository.save(new Post("An Invitation To Love", "It is happening again. I hear that gum you like is going to come back in style"));
    String expectedPost = String.format("Post(id=%d, title=An Invitation To Love, body=It is happening again. I hear that gum you like is going to come back in style)", post.getId());    
    assertEquals(expectedPost, post.toString());
  }

}

