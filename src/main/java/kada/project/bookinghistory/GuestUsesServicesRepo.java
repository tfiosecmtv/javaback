package kada.project.bookinghistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestUsesServicesRepo extends JpaRepository<GuestUsesService, GuestUsesServiceId> {
    List<GuestUsesService> findByBookingid(Long booking_id);
}
