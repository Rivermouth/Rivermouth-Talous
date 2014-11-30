package fi.rivermouth.talous.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.repository.FileRepository;

/**
 * 
 * @author Karri Rasinmäki
 *
 * @param <PARENT>
 * @param <Long>
 */
@Service
public class FileService extends BaseService<File> {
	
	@Autowired
	FileRepository fileRepository;

	public List<File> list(Long ownerId, String collection, Long parentId) {
		if (parentId == null) return list(ownerId, collection);
		return fileRepository.findByOwnerAndCollectionAndAttachedTo(ownerId, collection, parentId);
	}
	
	public List<File> list(Long ownerId, String collection) {
		if (collection.equals("files")) return list(ownerId);
		return fileRepository.findByOwnerAndCollection(ownerId, collection);
	}
	
	public List<File> list(Long ownerId) {
		return fileRepository.findByOwner(ownerId);
	}

	@Override
	public JpaRepository<File, Long> getRepository() {
		return fileRepository;
	}

}
