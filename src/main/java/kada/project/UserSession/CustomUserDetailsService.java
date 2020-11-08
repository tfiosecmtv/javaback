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
        System.out.println("started checking");
        Guest userEntity = userService.findByLogin(email);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }

    public CustomUserDetails loadEmployeeByUsername(String email) throws UsernameNotFoundException {
        return CustomUserDetails.fromEmployeeEntityToCustomUserDetails(userService.findEmployeeByLogin( email ));
    }
}
