package fi.rivermouth.talous.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fi.rivermouth.talous.domain.User;

public class UserAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) {
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();

		grantedAuths.add(new SimpleGrantedAuthority(User.ROLE));
		
		return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), grantedAuths);
	}

}
