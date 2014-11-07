package fi.rivermouth.talous.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.BaseEntity;
import fi.rivermouth.talous.domain.User;

@Repository
public interface UserRepository<T extends User, ID extends Serializable> extends JpaRepository<User, Long> {
	public User findByEmailIgnoreCaseOrGoogleIdOrFacebookId(String email, String googleId, String facebookId);
	public User findByEmailIgnoreCase(String email);
}
