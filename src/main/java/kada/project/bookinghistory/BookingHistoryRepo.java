package kada.project.bookinghistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
interface BookingHistoryRepo extends JpaRepository<BookingHistory, Long> {
    List<BookingHistory> findByGuestid(Long guest_id);
    List<BookingHistory> findByHotelid(Long hotel_id);
    List<BookingHistory> findByRoomtype(Long room_type);
    BookingHistory findByBookingid(Long booking_id);
}

interface GuestUsesServicesRepo extends JpaRepository<GuestUsesService, GuestUsesServiceId> {
    List<GuestUsesService> findByBookingid(Long booking_id);
    GuestUsesService findByBookingidAndService(Long booking_id, String service);
}
