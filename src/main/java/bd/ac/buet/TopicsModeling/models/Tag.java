package bd.ac.buet.TopicsModeling.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("Tags")
public class Tag {
    @Id
    @Column("Id")
    private Long id;
    @Column("TagName")
    private String tagName;
    @Column("Count")
    private Long count;
    @Column("ExcerptPostId")
    private Long excerptPostId;
    @Column("WikiPostId")
    private Long wikiPostId;
}
