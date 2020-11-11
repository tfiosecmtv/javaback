package kada.project.hotels;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationHistoryRepo extends JpaRepository<OccupationHistory, OccupationHistoryId> {
    List<OccupationHistory> findByHotelidAndRoomtype(Long hotel_id, String room_type);
    List<OccupationHistory> findByHotelid(Long hotel_id);
    List<OccupationHistory> findByBookingid(Long booking_id);
    OccupationHistory findByRoomnumberAndBookingid(Integer roomnumber, Long bookingid);
    List<OccupationHistory> findByBookingidAndRoomtype(Long booking_id, String room_type);
    List<OccupationHistory> findByHotelidAndRoomnumber(Long hotel_id, Integer room_number);
}
