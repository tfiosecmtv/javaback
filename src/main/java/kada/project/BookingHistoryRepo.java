package kada.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingHistoryRepo extends JpaRepository<BookingHistory, Long> {

}
