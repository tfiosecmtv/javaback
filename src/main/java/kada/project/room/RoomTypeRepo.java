package kada.project.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepo extends JpaRepository<RoomType, RoomTypeId> {
    List<RoomType> findByHotelid(Long hotel_id);
    List<RoomType> findByName(String name);
    RoomType findByHotelidAndName(Long hotel_id, String name);
}
