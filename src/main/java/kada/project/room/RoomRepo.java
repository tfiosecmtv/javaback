package kada.project.room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface RoomTypeFeaturesRepo extends JpaRepository<RoomTypeFeatures, RoomTypeFeaturesId> {
    List<RoomTypeFeatures> findByHotelid(Long hotel_id);
    List<RoomTypeFeatures> findByRoomtype(String room_type);
    List<RoomTypeFeatures> findByHotelidAndRoomtype(Long hotel_id, String room_type);
}

@Repository
public interface RoomRepo extends JpaRepository<Room, RoomId> {
    List<Room> findByHotelid(Long hotel_id);
    List<Room> findByRoomtype(String room_type);
    List<Room> findByRoomnumber(Integer room_number);
    Room findByHotelidAndRoomnumber(Long hotel_id, Integer room_number);
    List<Room> findByHotelidAndRoomtype(Long hotel_id, String room_type);
}

