package proxy.seller.assigment.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import proxy.seller.assigment.domain.model.UserLike;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "notes")
public class Note implements Serializable {
    @Id
    private String id;
    private String content;
    private UserLike like;
    @Field("created_time")
    private LocalDateTime createdAt;
    @Field("updated_time")
    private LocalDateTime updateAt;

}
