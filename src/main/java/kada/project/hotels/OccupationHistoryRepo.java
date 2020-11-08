package kada.project.hotels;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationHistoryRepo extends JpaRepository<OccupationHistory, OccupationHistoryId> {
    List<OccupationHistory> findByHotelidAndRoomtype(Long hotel_id, String room_type);
    List<OccupationHistory> findByHotelid(Long hotel_id);
    List<OccupationHistory> findByRoomtype(String room_type);
    List<OccupationHistory> findByRoomnumber(Integer room_number);
    List<OccupationHistory> findByGuestid(Long guest_id);
    List<OccupationHistory> findByBookingid(Long booking_id);
}
