package kada.project.UserSession;

import kada.project.user.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Guest userEntity = userService.findByLogin(email);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
