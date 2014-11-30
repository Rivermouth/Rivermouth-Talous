package fi.rivermouth.talous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.Client;
import fi.rivermouth.talous.repository.ClientRepository;

@Service
public class ClientService extends BaseService<Client> {
	
	@Autowired
	ClientRepository clientRepository;

	@Override
	public JpaRepository<Client, Long> getRepository() {
		return clientRepository;
	}
	
}
