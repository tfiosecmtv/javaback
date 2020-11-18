package kada.project.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, EmployeeId> {
    Employee findByEmail(String email);
    Employee findByHotelidAndEmployeeid(Long hotel_id, Long employee_id);
}
