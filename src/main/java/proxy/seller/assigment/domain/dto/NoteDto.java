package proxy.seller.assigment.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import proxy.seller.assigment.domain.entity.Note;
import proxy.seller.assigment.domain.model.UserLike;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class NoteDto implements Serializable {

    private String id;
    private String content;
    private UserLike like;
    private LocalDateTime createdAt;

    public static NoteDto from(Note note) {
        return new NoteDto()
                .setId(note.getId())
                .setContent(note.getContent())
                .setLike(note.getLike())
                .setCreatedAt(note.getCreatedAt());
    }

    public Note toNote() {
        return new Note()
                .setId(this.id)
                .setContent(this.content)
                .setLike(this.like)
                .setCreatedAt(this.createdAt);
    }

}
