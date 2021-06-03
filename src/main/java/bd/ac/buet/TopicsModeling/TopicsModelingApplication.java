package bd.ac.buet.TopicsModeling;

import bd.ac.buet.TopicsModeling.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableTransactionManagement
public class TopicsModelingApplication  {
	@Autowired
	PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(TopicsModelingApplication.class, args);
	}

}
