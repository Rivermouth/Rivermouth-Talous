package fi.rivermouth.talous.repository;

import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.domain.UserNote;

@Repository
public interface UserNoteRepository extends AbstractNoteRepository<UserNote, User, Long> {
}
