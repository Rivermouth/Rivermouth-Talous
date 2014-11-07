package fi.rivermouth.talous.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
