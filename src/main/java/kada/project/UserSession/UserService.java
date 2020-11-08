package kada.project.UserSession;

import kada.project.Employee.Employee;
import kada.project.Employee.EmployeeRepo;
import kada.project.user.Guest;
import kada.project.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepo userEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeRepo employeeEntityRepo;


    public Guest saveUser(Guest userEntity) {
        //RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        //userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public Guest findByLogin(String email) {
        return userEntityRepository.findByEmail(email);
    }
    public Employee findEmployeeByLogin(String email) {
        return employeeEntityRepo.findByEmail(email);
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

    public Employee findEmployeeByLoginAndPassword(String email, String password) {
        Employee employee = employeeEntityRepo.findByEmail( email );
        if (employee != null) {
            if (password.equals( employee.getPassword() )) {
                return employee;
            }
        }
        return null;
    }
}
