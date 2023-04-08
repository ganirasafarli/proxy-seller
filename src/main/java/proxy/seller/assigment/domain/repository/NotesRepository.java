package proxy.seller.assigment.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import proxy.seller.assigment.domain.entity.Note;

@Repository
public interface NotesRepository extends ReactiveMongoRepository<Note, String> {

}