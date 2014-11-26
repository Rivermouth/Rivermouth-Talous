package fi.rivermouth.talous.service;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fi.rivermouth.spring.entity.BaseEntity;
import fi.rivermouth.spring.entity.Response;
import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.repository.FileRepository;

/**
 * 
 * @author Karri Rasinmäki
 *
 * @param <PARENT>
 * @param <PARENT_ID>
 */
@Service
public class FileService<PARENT extends BaseEntity<PARENT_ID>, PARENT_ID extends Serializable> extends BaseService<File, Long> {
	
	@Autowired
	private FileRepository fileRepository;

	@Override
	public FileRepository getRepository() {
		return fileRepository;
	}
	
	public void joinFileAndParent(AbstractFileHavingService<PARENT, PARENT_ID> parentService, PARENT_ID parentId, File entity) {
		PARENT parent = parentService.get(parentId);
		parentService.addFileToParent(entity, parent);
		parentService.update(parent);
		parentService.getRepository().flush();
	}
	
	public boolean seperateFileAndParent(AbstractFileHavingService<PARENT, PARENT_ID> parentService, PARENT_ID parentId, Long id) {
		File entity = get(id);
		PARENT parent = parentService.get(parentId);
		parentService.removeFileFromParent(entity, parent);
		Boolean res = delete(entity);
		parentService.getRepository().flush();
		return res;
	}
	
	public File create(AbstractFileHavingService<PARENT, PARENT_ID> parentService, PARENT_ID parentId, File file) {
		File justCreated = create(file);
		if (justCreated == null) return null;
		joinFileAndParent(parentService, parentId, file);
		return justCreated;
	}
	
	public File update(Long id, File file) {
		if (file.getId() == null) {
			file.setId(id);
		}
		else if (file.getId() != id) {
			return null;
		}
		return update(file);
	}
	
	public File getFile(Long id) {
		return get(id);
	}
	
	public List<File> list(AbstractFileHavingService<PARENT, PARENT_ID> parentService, PARENT_ID parentId) {
		return parentService.listFilesByParentId(parentId);
	}
	
	public boolean delete(AbstractFileHavingService<PARENT, PARENT_ID> parentService, PARENT_ID parentId, Long id) {
		return seperateFileAndParent(parentService, parentId, id);
	}

}
