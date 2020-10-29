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
interface RoomRepo extends JpaRepository<Room, RoomId> {
    List<Room> findByHotelid(Long hotel_id);
    List<Room> findByRoomtype(String room_type);
    List<Room> findByRoomnumber(Integer room_number);
}

@Repository
interface OccupationHistoryRepo extends JpaRepository<OccupationHistory, OccupationHistoryId> {
    List<OccupationHistory> findByHotelid(Long hotel_id);
    List<OccupationHistory> findByRoomtype(String room_type);
    List<OccupationHistory> findByRoomnumber(Integer room_number);
    List<OccupationHistory> findByGuestid(Long guest_id);
}