package kada.project.hotels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelServicesRepo extends JpaRepository<HotelServices, ServicesId> {
    HotelServices findByHotelidAndService(Long hotel_id, String service);
    List<HotelServices> findByHotelid(Long hotel_id);
}
