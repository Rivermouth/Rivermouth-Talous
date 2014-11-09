package fi.rivermouth.talous.test.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.UserNote;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.repository.UserRepository;
import fi.rivermouth.talous.service.UserNoteService;
import fi.rivermouth.talous.service.UserService;

public class UserNoteControllerTest extends BaseChildControllerTest<User, Long, UserNote, Long> {
			
	@Autowired
	UserService userService;
	
	@Autowired
	UserNoteService noteService;
	
	@Override
	public User getRandomParent() {
		return new UserControllerTest().getRandomEntity();
	}

	@Override
	public User getTotallyRandomParent() {
		return new UserControllerTest().getTotallyRandomEntity();
	}

	@Override
	public BaseService<User, Long> getParentService() {
		return userService;
	}

	@Override
	public void setEntityParent(UserNote entity, User parent) {
		entity.setAttachedTo(parent);
	}

	@Override
	public UserNote getRandomEntity() {
		UserNote note = new UserNote(
				RandomStringUtils.random(17), 
				RandomStringUtils.random((int) (24 + Math.round(Math.random() * 100))));
		
		return note;
	}

	@Override
	public UserNote getTotallyRandomEntity() {
		return getRandomEntity();
	}

	@Override
	public String getAPIPath() {
		return "/notes";
	}

	@Override
	public BaseService<UserNote, Long> getService() {
		return noteService;
	}

}
