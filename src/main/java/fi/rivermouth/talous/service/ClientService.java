package fi.rivermouth.talous.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.spring.service.BaseService;
import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.repository.ClientRepository;

@Service
public class ClientService extends BaseService<Client, Long> {
	
	@Autowired
	ClientRepository clientRepository;
	
	public List<Client> getByOwnerId(Long parentId) {
		return clientRepository.findByParentId(parentId);
	}

	@Override
	public JpaRepository<Client, Long> getRepository() {
		return clientRepository;
	}
	
}
