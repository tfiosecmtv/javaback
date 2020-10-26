package kada.project.UserSession;

import kada.project.user.Guest;
import kada.project.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Guest saveUser(Guest userEntity) {
        //RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        //userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public Guest findByLogin(String email) {
        return userEntityRepository.findByEmail(email);
    }

    public Guest findByLoginAndPassword(String email, String password) {
        Guest userEntity = findByLogin(email);
        if (userEntity != null) {
            if (password.equals( userEntity.getPassword() )) {
                return userEntity;
            }
        }
        return null;
    }
}
