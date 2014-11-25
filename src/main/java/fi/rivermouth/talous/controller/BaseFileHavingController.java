package fi.rivermouth.talous.controller;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.rivermouth.spring.controller.CRUDController;
import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.FileHavingEntityInterface;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.service.FileService;

@RestController
public abstract class BaseFileHavingController<T extends BaseEntity<ID>, ID extends Serializable> 
extends CRUDController<T, ID> implements AbstractFileHavingControllerInterface<T, ID> {
	
	@Autowired
	protected FileService fileService;
	
	@Transactional
	protected void joinFileAndParent(ID parentId, File entity) {
		T parent = getService().get(parentId);
		addFileToParent(entity, parent);
		getService().update(parent);
		getService().getRepository().flush();
	}
	
	@Transactional
	protected boolean seperateFileAndParent(ID parentId, Long id) {
		File entity = fileService.get(id);
		T parent = getService().get(parentId);
		removeFileFromParent(entity, parent);
		Boolean res = fileService.delete(entity);
		getService().getRepository().flush();
		return res;
	}
	
	protected Response _createFile(ID parentId, File file) {
		File justCreated = fileService.create(file);
		if (justCreated != null) joinFileAndParent(parentId, file);
		return conditionalResponse(
				new Response(HttpStatus.CREATED, justCreated), 
				ifNullAlreadyExistsCondition(getEntityKind()));
	}
	
	protected Response _updateFile(Long id, File file) {
		if (file.getId() == null) {
			file.setId(id);
		}
		else if (file.getId() != id) {
			return new Response(HttpStatus.BAD_REQUEST, 
					new Response.Message(ID_S_DOES_NOT_MATCH_WITH_ID_S, file.getId(), id));
		}
		return conditionalResponse(
				new Response(HttpStatus.OK, fileService.update(file)), 
				ifNull(new Response(HttpStatus.NOT_MODIFIED, new Response.Message(FAILED_TO_UPDATE_ENTITY_WITH_ID_S, id))));
	}
	
	protected Response _getFile(Long id) {
		return conditionalResponse(
				new Response(HttpStatus.OK, fileService.get(id)),
				ifNull(notFoundWithIdResponse("file", id)));
	}
	
	protected Response _listFiles(ID parentId) {
		List<File> files = listFilesByParentId(parentId);
		return new Response(HttpStatus.OK, "file", files);
	}
	
	protected Response _deleteFile(ID parentId, Long id) {
		boolean isDeleted = seperateFileAndParent(parentId, id);
		return conditionalResponse(
				new Response(HttpStatus.OK, new Response.Message(S_WITH_ID_S_DELETED, "file", id)), 
				ifTrue(!isDeleted, notFoundWithIdResponse("file", id)));
	}
	
}
