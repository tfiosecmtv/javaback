package kada.project.hotels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelSeasonsRepo extends JpaRepository<HotelSeasons, SeasonsId> {
    List<HotelSeasons> findByHotelid(Long hotel_id);
    HotelSeasons findBySeason(String name);
    HotelSeasons findByHotelidAndSeason(Long hotel_id, String name);
}
