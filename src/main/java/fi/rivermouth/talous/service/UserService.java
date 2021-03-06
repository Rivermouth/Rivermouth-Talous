package fi.rivermouth.talous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fi.rivermouth.talous.auth.UserAuthenticationManager;
import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.repository.UserRepository;

@Service
public class UserService extends BaseService<User> {

	@Autowired
	private UserRepository userRepository;

	
	public User getByEmail(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}

	@Override
	public JpaRepository<User, Long> getRepository() {
		return userRepository;
	}
	
	public User getByEmailPassword(String email, String password) throws AuthenticationException {
		User user = getByEmail(email);
		
		if (user == null || !user.passwordEquals(password)) {
            throw new AuthenticationException("Unable to authenticate user " + email) {
            };
        }

        return user;
	}
	
	public boolean authenticate(String email, String password) {
		User user = getByEmailPassword(email, password);
		if (user == null) return false;
		
		try {
			AuthenticationManager am = new UserAuthenticationManager();
			Authentication request = new UsernamePasswordAuthenticationToken(email, password);
	        Authentication result = am.authenticate(request);
	        SecurityContextHolder.getContext().setAuthentication(result);
		} catch (AuthenticationException e) {
			System.out.println("Authentication failed: [" + password + "] " + e.getMessage());
			return false;
		}
        return true;
	}
	
	public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return null;
        String email = authentication.getName();
        if (email == null) return null;
        return getByEmail(email);
    }
	
	public boolean isAuthenticatedUserId(Long id) {
		User user = getAuthenticatedUser();
		return (user != null && id == user.getId());
	}
	
}
