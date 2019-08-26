package blahg.controllers;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import blahg.controllers.PostController;
import blahg.repositories.PostRepository;
import blahg.models.post.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.name=blahg-test-h2","blahg.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostControllerTest {

  private static ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private PostRepository repository;

  @Test
  public void shouldShowAllPosts() throws Exception {
    mockMvc.perform(get("/posts"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$._embedded.postList", hasSize(2)))
      .andExpect(content().string(containsString("Plumbus")))
      .andExpect(content().string(containsString("Otter Space")));
  }
  
  @Test
  public void shouldShowPost() throws Exception {
    mockMvc.perform(get("/posts/2"))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Otter Space")));
  }

  @Test
  public void showReturnsErrorWhenIdDoesntMatch() throws Exception {
    mockMvc.perform(get("/posts/3"))
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Could not find post 3")));
  }

  @Test
  public void shouldCreatePost() throws Exception {
    Post expectedPost = new Post(
      "An Invitation To Love", 
      "It is happening again. I hear that gum you like is going to come back in style"
    );

    mockMvc.perform(
      post("/posts")
      .contentType("application/json")
      .content(objectMapper.writeValueAsString(expectedPost))
    )
      .andExpect(status().isOk())
      .andExpect(content()
      .string(containsString("An Invitation To Love")));
  }

  @Test 
  public void shouldUpdatePost() throws Exception {
    repository.save(new Post(
      "An Invitation To Love",
      "It is happening again. I hear that gum you like is going to come back in style"
    ));

    Post expectedPost = new Post(
      "Damn Fine Coffee",
      "It is happening again. I hear that gum you like is going to come back in style"
    );

    mockMvc.perform(
      put("/posts/3")
      .contentType("application/json")
      .content(objectMapper.writeValueAsString(expectedPost))
    )
      .andExpect(status().isOk())
      .andExpect(content()
        .string(containsString("Damn Fine Coffee")));
  }

  @Test
  public void updateReturnsErrorWhenIdDoesntMatch() throws Exception {
    Post expectedPost = new Post(
      "Damn Fine Coffee",
      "It is happening again. I hear that gum you like is going to come back in style"
    );

    mockMvc.perform(put("/posts/3")
      .contentType("application/json")
      .content(objectMapper.writeValueAsString(expectedPost))
    )
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Could not find post 3")));
  }

  @Test
  public void shouldDeletePost() throws Exception {
    mockMvc.perform(delete("/posts/1"));

    mockMvc.perform(get("/posts/1"))
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Could not find post 1")));
  }
}

