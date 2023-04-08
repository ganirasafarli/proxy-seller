package proxy.seller.assigment.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import proxy.seller.assigment.domain.enumeration.UserStatusesEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    @Field("status")
    private UserStatusesEnum status;
    @Field("created_time")
    private LocalDateTime createdAt;
    @Field("updated_time")
    private LocalDateTime updateAt;
}
