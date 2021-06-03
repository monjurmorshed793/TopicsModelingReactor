package bd.ac.buet.TopicsModeling.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("PostTags")
public class PostTags {
    @Id
    @Column("Id")
    private Long id;
    @Column("PostId")
    private Long postId;
    @Column("TagId")
    private Long tagId;
}
