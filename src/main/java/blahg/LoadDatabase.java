package blahg;

import lombok.extern.slf4j.Slf4j;

import blahg.models.post.Post;
import blahg.repositories.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(PostRepository repository) {
    return args -> {
      log.info("Preloading " + repository.save(new Post("Plumbus", "Welcome to the exciting world of Plumbus ownership! A Plumbus will aid many things in life, making life easier. With proper maintenance, handling, storage, and urging, Plumbus will provide you with a lifetime of better living and happiness.")));
      log.info("Preloading " + repository.save(new Post("Otter Space", "A treatise on the ecology and territorial behaviors of river otters.")));
    };
  }
}
