package fi.rivermouth.talous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.ChildCRUDController;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.UserNote;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.UserNoteService;
import fi.rivermouth.talous.service.UserService;

@RestController
@RequestMapping("/users/{parentId}/notes")
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
	public void addEntityToParent(UserNote entity, User parent) {
		parent.getNotes().add(entity);
	}

	@Override
	public void removeEntityFromParent(UserNote entity, User parent) {
		parent.getNotes().remove(entity);
	}

	@Override
	public List<UserNote> listByParentId(Long parentId)  {
		return userService.get(parentId).getNotes();
	}

	@Override
	public BaseService<UserNote, Long> getService() {
		return noteService;
	}

	@Override
	public String getEntityKind() {
		return "note";
	}

}
