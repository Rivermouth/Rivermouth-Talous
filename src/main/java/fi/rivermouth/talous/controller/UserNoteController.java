package fi.rivermouth.talous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.talous.domain.UserNote;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.BaseService;
import fi.rivermouth.talous.service.UserNoteService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/notes")
public class UserNoteController extends ChildCRUDController<User, Long, UserNote, Long> {

	@Autowired
	UserService userService;
	
	@Autowired
	UserNoteService noteService;
	
	@Override
	public BaseService<User, Long> getParentService() {
		return userService;
	}

	@Override
	public void setEntityParent(UserNote entity, User parent) {
		entity.setAttachedTo(parent);
	}

	@Override
	public void addEntityToParent(UserNote entity, User parent) {
		parent.getNotes().add(entity);
	}

	@Override
	public void removeEntityFromParent(UserNote entity, User parent) {
		parent.getNotes().remove(entity);
	}

	@Override
	public User getEntityParent(UserNote entity) {
		return entity.getAttachedTo();
	}

	@Override
	public List<UserNote> listByParent(User parent) {
		return noteService.getByAttachedTo(parent);
	}

	@Override
	public BaseService<UserNote, Long> getService() {
		return noteService;
	}

}
