package fi.rivermouth.talous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.UserNote;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.repository.UserRepository;

@Service
public class UserService extends BaseService<User, Long> {

	@Autowired
	private UserRepository userRepository;
	
	public User getByEmail(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}

	@Override
	public JpaRepository<User, Long> getRepository() {
		return userRepository;
	}
	
}
