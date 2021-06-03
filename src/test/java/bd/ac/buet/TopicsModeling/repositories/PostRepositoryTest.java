package bd.ac.buet.TopicsModeling.repositories;

import bd.ac.buet.TopicsModeling.models.Post;
import bd.ac.buet.TopicsModeling.models.PostTags;
import bd.ac.buet.TopicsModeling.models.Tag;
import bd.ac.buet.TopicsModeling.service.PostTagService;
import bd.ac.buet.TopicsModeling.util.TopicsModelingUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostTagsRepository postTagsRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagService postTagService;


    @Test
    public void repoTest(){
        Post post = postRepository.findById(4L).block();
        assertThat(post).isNotNull();
    }

    @Test
    public void creatPostTagsReactor(){
//        postTagService.creatPostTagsReactorV2().blockLast();
        Map<String, Tag> nameMapTag = tagRepository.findAll().collectList().block()
                .parallelStream()
                .collect(Collectors.toMap(t-> t.getTagName(), t-> t));
        Flux<PostTags> postTagsFlux =  postRepository.findAll()
                .delayElements(Duration.of(1, ChronoUnit.SECONDS))
                .flatMap(p-> convertToPostTags(p, nameMapTag));
        postTagsRepository.saveAll(postTagsFlux).blockLast();
    }

    Flux<PostTags> convertToPostTags(Post post, Map<String, Tag> nameMapTag){
        List<PostTags> postTags = new ArrayList<>();
        return TopicsModelingUtils
                .extractTagsReactive(post.getTags())
                .map(t-> PostTags.builder().postId(post.getId()).tagId(nameMapTag.get(t).getId()).build());
    }


    @Test
    public void createPostTags(){
        Long totalElements = 20890055L; //postRepository.count().block();
        Long totalPages = totalElements/10000000;
        totalPages = totalPages+1L;
        Long page = 1L;

        Map<String, Tag> nameMapTag = tagRepository.findAll().collectList().block()
                .parallelStream()
                .collect(Collectors.toMap(t-> t.getTagName(), t-> t));

        //List<PostTags> postTagsList = new ArrayList<>();

        while(page<=totalPages){
            List<PostTags> postTagsList = new ArrayList<>();
            System.out.println("Page: "+page.toString());
            Pageable pageable  = PageRequest.of(Integer.parseInt(page.toString()), 10000000);

            List<Post> posts = postRepository.findAllBy(pageable).collectList().block();
            posts.stream()
                    .forEach(post->{
//                            List<PostTags> postTagsList = new ArrayList<>();
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
/*            for(Post post: posts){
                if(post.getTags()!=null && post.getTags().length()>1){
                    List<PostTags> postTagsList = new ArrayList<>();
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
                    postTagsRepository.saveAll(postTagsList).subscribe();
                }
            }*/
            postTagsRepository.saveAll(postTagsList).blockLast();
            page = page+1L;
        }
        //postTagsRepository.saveAll(postTagsList).subscribe();
    }


    @Test
    public void createPostTagsV2(){


        Map<String, Tag> nameMapTag = tagRepository.findAll().collectList().block()
                .parallelStream()
                .collect(Collectors.toMap(t-> t.getTagName(), t-> t));



//            List<PostTags> postTagsList = new ArrayList<>();

            List<Post> posts = postRepository.findAll().collectList().block();
            posts.stream()
                    .forEach(post->{
                            List<PostTags> postTagsList = new ArrayList<>();
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
                        postTagsRepository.saveAll(postTagsList).subscribe();

                    });
/*            for(Post post: posts){
                if(post.getTags()!=null && post.getTags().length()>1){
                    List<PostTags> postTagsList = new ArrayList<>();
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
                    postTagsRepository.saveAll(postTagsList).subscribe();
                }
            }*/


    }
    @Test
    public void tagExtractionTest(){
        List<String> tagList = TopicsModelingUtils.extractTags("<react-native><java>");
        assertThat(tagList.size()).isEqualTo(2);
    }
}
