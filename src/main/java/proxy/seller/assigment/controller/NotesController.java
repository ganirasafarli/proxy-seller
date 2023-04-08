package proxy.seller.assigment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import proxy.seller.assigment.domain.dto.NoteDto;
import proxy.seller.assigment.domain.entity.Note;
import proxy.seller.assigment.domain.model.AuthUserDetails;
import proxy.seller.assigment.service.NotesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {

    private final NotesService notesService;

    @GetMapping("/")
    public Flux<NoteDto> getAllNotes() {
        return notesService.getAllNotes();
    }

    @PostMapping("/")
    public Mono<Note> createNote(@RequestBody NoteDto note) {
        return notesService.createNote(note);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/likeOrUnlike")
    public Mono<String> likeNote(@PathVariable String id, Authentication authentication) {
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        return notesService.likeOrUnlike(id,userDetails.getId());
    }

}
