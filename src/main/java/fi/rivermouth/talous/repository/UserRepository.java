package fi.rivermouth.talous.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.rivermouth.talous.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmailIgnoreCase(String email);
	public User findByGoogleId(String googleId);
	public User findByFacebookId(String facebookId);
	
	/*
	@Query("select u from User u where u.email = :email or (u.googleId = :googleId and u.facebookId = :facebookId")
	public User findByEmailIgnoreCaseOrGoogleIdOrFacebookId(
			@Param("email") String email, @Param("googleId") String googleId, @Param("facebookId") String facebookId);
			*/	
}
