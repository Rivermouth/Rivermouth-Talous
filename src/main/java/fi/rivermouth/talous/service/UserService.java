package fi.rivermouth.talous.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.repository.UserRepository;

@Service
public class UserService<T extends User, ID extends Long> extends BaseService<User, Long> {

	@Autowired
	private UserRepository userRepository;
	
	public User getByEmail(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}

	@Override
	public JpaRepository<User, Long> getRepository() {
		return userRepository;
	}
	
	@Override
	public <S extends User> boolean isUnique(S entity) {
		return super.isUnique(entity) && (userRepository.findByEmailIgnoreCaseOrGoogleIdOrFacebookId(
				entity.getEmail(), 
				entity.getGoogleId(), 
				entity.getFacebookId()) 
				== null);
	}
	
}
