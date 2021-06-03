package bd.ac.buet.TopicsModeling.repositories;

import bd.ac.buet.TopicsModeling.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public interface PostRepository extends R2dbcRepository<Post, Long> {
    Flux<Post> findAllBy(Pageable pageable);
}
