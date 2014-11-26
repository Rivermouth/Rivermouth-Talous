package fi.rivermouth.talous.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.File;
import fi.rivermouth.talous.repository.ClientRepository;

@Service
public class ClientService extends AbstractFileHavingService<Client, Long> {
	
	@Autowired
	ClientRepository clientRepository;

	@Override
	public JpaRepository<Client, Long> getRepository() {
		return clientRepository;
	}

	@Override
	public void addFileToParent(File file, Client parent) {
		parent.getNotes().add(file);
	}

	@Override
	public void removeFileFromParent(File file, Client parent) {
		parent.getNotes().remove(file);
	}

	@Override
	public List<File> listFilesByParentId(Long parentId) {
		return get(parentId).getNotes();
	}
	
}
