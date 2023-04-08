package proxy.seller.assigment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import proxy.seller.assigment.domain.dto.NoteDto;
import proxy.seller.assigment.domain.entity.Note;
import proxy.seller.assigment.domain.model.UserLike;
import proxy.seller.assigment.domain.repository.NotesRepository;
import proxy.seller.assigment.exception.NotFoundException;
import proxy.seller.assigment.exception.ResourceAlreadyExistsException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final NotesRepository notesRepository;
    private final ReactiveMongoTemplate template;

    public Flux<NoteDto> getAllNotes() {
        return notesRepository.findAll()
                .map(NoteDto::from)
                .sort((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()));
    }

    public Mono<Note> createNote(NoteDto dto) {
        return notesRepository.save(dto.toNote().setCreatedAt(LocalDateTime.now()));
    }

    public Mono<String> likeOrUnlike(String id, String userId) {
        Query query = Query.query(Criteria.where("id").is(id));
        Mono<Note> noteMono = template.findOne(query, Note.class);
        return noteMono.switchIfEmpty(Mono.error(new NotFoundException(String.format("Note not found for id %s", id))))
                .flatMap(note -> {
                    Update update = new Update().set("updateAt", LocalDateTime.now());
                    if (note == null || note.getLike() == null || !note.getLike().hasUser(userId)) {
                        update.inc("like.like", 1).addToSet("like.userIds", userId);
                        return template.updateFirst(query, update, Note.class).thenReturn("Note liked successfully");
                    } else {
                        update.pull("like.userIds", userId).inc("like.like", -1);
                        return template.updateFirst(query, update, Note.class).thenReturn("Note unliked successfully");
                    }
                });
    }


}
