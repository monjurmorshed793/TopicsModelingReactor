package bd.ac.buet.TopicsModeling.service;

import bd.ac.buet.TopicsModeling.models.Post;
import bd.ac.buet.TopicsModeling.models.PostTags;
import bd.ac.buet.TopicsModeling.models.Tag;
import bd.ac.buet.TopicsModeling.repositories.PostRepository;
import bd.ac.buet.TopicsModeling.repositories.PostTagsRepository;
import bd.ac.buet.TopicsModeling.repositories.TagRepository;
import bd.ac.buet.TopicsModeling.util.TopicsModelingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostTagService {
    private final PostRepository postRepository;
    private final PostTagsRepository postTagsRepository;
    private final TagRepository tagRepository;

    public PostTagService(PostRepository postRepository, PostTagsRepository postTagsRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.postTagsRepository = postTagsRepository;
        this.tagRepository = tagRepository;
    }

    public Mono<List<PostTags>> creatPostTagsReactor(){
        Map<String, Tag> nameMapTag = tagRepository.findAll().collectList().block()
                .parallelStream()
                .collect(Collectors.toMap(t-> t.getTagName(), t-> t));
        Flux<Post> postFlux = postRepository.findAll();
        return postFlux
                .collectList()
                .flatMap(p->{
                    List<PostTags> postTagsList = new ArrayList<>();
                    p.forEach(post->{
                        List<String> postTags = TopicsModelingUtils.extractTags(post.getTags());
                        for(String tag: postTags){
                            Tag tagMono = nameMapTag.get(tag);
                            PostTags postTag = PostTags
                                    .builder()
                                    .postId(post.getId())
                                    .tagId(tagMono.getId())
                                    .build();
                            postTagsList.add(postTag);
                        }
                    });
                    return postTagsRepository.saveAll(postTagsList).collectList();
                });
    }

    public Flux<PostTags> creatPostTagsReactorV2(){
        Map<String, Tag> nameMapTag = tagRepository.findAll().collectList().block()
                .parallelStream()
                .collect(Collectors.toMap(t-> t.getTagName(), t-> t));
        Flux<PostTags> postTagsFlux =  postRepository.findAll()
                .flatMap(p-> convertToPostTags(p, nameMapTag));
        return postTagsRepository.saveAll(postTagsFlux);
    }

    Flux<PostTags> convertToPostTags(Post post, Map<String, Tag> nameMapTag){
        List<PostTags> postTags = new ArrayList<>();
        return TopicsModelingUtils
                .extractTagsReactive(post.getTags())
                .map(t-> PostTags.builder().postId(post.getId()).tagId(nameMapTag.get(t).getId()).build());
    }
}
