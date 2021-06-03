package bd.ac.buet.TopicsModeling.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("Posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @Column("Id")
    private Long id;

    @Column("AcceptedAnswerId")
    private Long acceptedAnswerId;

    @Column("AnswerCount")
    private Long answerCount;

    @Column("Body")
    private String body;

    @Column("ClosedDate")
    private Instant closedDate;

    @Column("CommentCount")
    private Long commentCount;

    @Column("CommunityOwnedDate")
    private Instant communityOwnedDate;

    @Column("CreationDate")
    private Instant creationDate;

    @Column("FavoriteCount")
    private Long favouriteCount;

    @Column("LastActivityDate")
    private Instant lastActivityDate;

    @Column("LastEditDate")
    private Instant lastEditDate;

    @Column("LastEditorDisplayName")
    private String lastEditorDisplayName;

    @Column("LastEditorUserId")
    private String lastEditorUserId;

    @Column("OwnerUserId")
    private Long ownerUserId;

    @Column("ParentId")
    private Long parentId;

    @Column("PostTypeId")
    private Long postTypeId;

    @Column("Score")
    private Long score;

    @Column("Tags")
    private String tags;

    @Column("Title")
    private String title;

    @Column("ViewCount")
    private Long viewCount;
}
