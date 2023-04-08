package proxy.seller.assigment.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import proxy.seller.assigment.domain.entity.User;
import proxy.seller.assigment.domain.enumeration.UserStatusesEnum;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<Boolean> existsByUsernameOrEmailAndStatus(String username, String email, UserStatusesEnum deleted);

    Mono<User> findOneByUsernameOrEmailAndStatus(String username, String email,UserStatusesEnum active);

    Mono<User> findByIdAndStatus(String id, UserStatusesEnum active);
}