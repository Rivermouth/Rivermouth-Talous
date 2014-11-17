package fi.rivermouth.talous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.rivermouth.spring.service.BaseService;
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

}
