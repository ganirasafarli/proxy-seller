package proxy.seller.assigment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLike {
    private Long like;
    private Set<String> userIds = new HashSet<>();

    public boolean hasUser(String userId) {
        return userIds.contains(userId);
    }
}
