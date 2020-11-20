package kada.project.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, EmployeeId> {
    Employee findByEmail(String email);
    Employee findByHotelidAndEmployeeid(Long hotel_id, Long employee_id);
    List<Employee> findByHotelid(Long hotelid);
}
