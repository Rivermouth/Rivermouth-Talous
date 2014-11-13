package fi.rivermouth.talous.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import fi.rivermouth.talous.domain.User;
import fi.rivermouth.talous.service.UserService;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String username = a.getPrincipal().toString();
        String password = a.getCredentials().toString();

        User user = userService.getByEmailPassword(username, password);

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();

        grantedAuths.add(new SimpleGrantedAuthority(User.ROLE));

        return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    }

	@Override
	public boolean supports(Class<?> type) {
		return true;
	}

}
