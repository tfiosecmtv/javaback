package kada.project.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, ScheduleRepo> {
    List<Schedule> findByHotelid(Long hotel_id);
    Schedule findByHotelidAndEmployeeidAndDate(Long hotel_id, Long employee_id, Date date);
}