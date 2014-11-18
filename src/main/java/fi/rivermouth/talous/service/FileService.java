package fi.rivermouth.talous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.repository.FileRepository;

@Service
public class FileService extends BaseService<File, Long> {
	
	@Autowired
	private FileRepository fileRepository;

	@Override
	public JpaRepository<File, Long> getRepository() {
		return fileRepository;
	}

}
