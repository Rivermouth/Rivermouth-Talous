package fi.rivermouth.talous.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.domain.FileText;
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
		if (collection.equals("files")) collection = "!null";
		if (collection.charAt(0) == '!') {
			return returnFileList(fileRepository.findByOwnerAndCollectionNotInAndAttachedTo(
					ownerId, Arrays.asList(collection.substring(1).split(",")), parentId));
		}
		return returnFileList(fileRepository.findByOwnerAndCollectionInAndAttachedTo(
				ownerId, Arrays.asList(collection.split(",")), parentId));
	}
	
	public List<File> list(Long ownerId, String collection) {
		if (collection.equals("files")) return list(ownerId);
		if (collection.charAt(0) == '!') {
			return returnFileList(fileRepository.findByOwnerAndCollectionNotIn(
					ownerId, Arrays.asList(collection.substring(1).split(","))));
		}
		return returnFileList(fileRepository.findByOwnerAndCollectionIn(
				ownerId, Arrays.asList(collection.split(","))));
	}
	
	public List<File> list(Long ownerId) {
		return returnFileList(fileRepository.findByOwner(ownerId));
	}
	
	@Override
	public File get(Long id) {
		return returnFile(super.get(id));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S extends File> S save(S entity) {
		return (S) returnFile(super.save(entity));
	}

	@Override
	public JpaRepository<File, Long> getRepository() {
		return fileRepository;
	}
	
	/**
	 * Convert files in list to correct file types
	 * e.q. files with content type "text/*" will be converted to FileText
	 * @param files
	 * @return files
	 */
	private List<File> returnFileList(List<File> files) {
		if (files == null) return files;
		List<File> fileList = new ArrayList<File>();
		for (File file : files) {
			fileList.add(returnFile(file));
		}
		return fileList;
	}
	
	/**
	 * Convert file to correct file type
	 * e.q. file with content type "text/*" will be converted to FileText
	 * @param files
	 * @return files
	 */
	private File returnFile(File file) {
		if (file.getMimeType().split("/")[0].equals("text")) {
			return new FileText(file);
		}
		else {
			return file;
		}
	}
	

}
