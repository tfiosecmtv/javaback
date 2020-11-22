package kada.project.hotels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelDiscountsRepo extends JpaRepository<HotelDiscounts, CategoryId> {
    HotelDiscounts findByHotelidAndCategory(Long hotelid, String category);
    List<HotelDiscounts> findByHotelid(Long hotelid);


}