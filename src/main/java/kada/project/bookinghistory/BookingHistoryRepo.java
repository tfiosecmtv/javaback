package kada.project.bookinghistory;

import kada.project.bookinghistory.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface BookingHistoryRepo extends JpaRepository<BookingHistory, Long> {
    List<BookingHistory> findByGuestid(Long guest_id);
}
