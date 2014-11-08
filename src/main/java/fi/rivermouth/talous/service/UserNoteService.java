package fi.rivermouth.talous.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.domain.UserNote;
import fi.rivermouth.talous.repository.UserNoteRepository;

@Service
public class UserNoteService extends BaseService<UserNote, Long> {

	@Autowired
	UserNoteRepository noteRepository;
	
	@Override
	public UserNoteRepository getRepository() {
		return noteRepository;
	}
	
	public List<UserNote> getByAttachedTo(User user) {
		return noteRepository.findByAttachedTo(user);
	}

}
