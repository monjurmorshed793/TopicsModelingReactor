package bd.ac.buet.TopicsModeling.repositories;

import bd.ac.buet.TopicsModeling.models.PostTags;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PostTagsRepository extends R2dbcRepository<PostTags, Long> {
}
