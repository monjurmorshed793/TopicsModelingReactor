package bd.ac.buet.TopicsModeling.util;

import io.netty.util.internal.StringUtil;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TopicsModelingUtils {
    public static List<String> extractTags(String tags){
        tags = tags.replaceAll("<","");
        tags = tags.replaceAll(">",",");
        List<String> tagList = new LinkedList<>(StringUtils.commaDelimitedListToSet(tags));
        tagList.remove(tagList.size()-1);
        return tagList;
    }

    public static Flux<String> extractTagsReactive(String tags){
        tags = tags.replaceAll("<","");
        tags = tags.replaceAll(">",",");
        List<String> tagList = new LinkedList<>(StringUtils.commaDelimitedListToSet(tags));
        tagList.remove(tagList.size()-1);
        return Flux.fromIterable(tagList);
    }
}
