package bd.ac.buet.TopicsModeling.repositories;

import bd.ac.buet.TopicsModeling.models.Tag;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface TagRepository extends R2dbcRepository<Tag, Long> {
    Mono<Tag> findByTagName(String tagname);
}
