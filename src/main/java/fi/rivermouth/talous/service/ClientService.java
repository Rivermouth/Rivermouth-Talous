package fi.rivermouth.talous.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.repository.ClientRepository;

@Service
public class ClientService extends BaseService<Client, Long> {
	
	@Autowired
	ClientRepository clientRepository;
	
	public List<Client> getByOwner(User owner) {
		return clientRepository.findByOwner(owner);
	}

	@Override
	public JpaRepository<Client, Long> getRepository() {
		return clientRepository;
	}
	
}
