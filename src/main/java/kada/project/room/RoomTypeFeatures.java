package kada.project.room;

import javax.persistence.*;
import java.io.Serializable;

class RoomTypeFeaturesId implements Serializable {
    Long hotelid;
    String roomtype;

    public RoomTypeFeaturesId(Long hotelid, String roomtype) {
        this.hotelid = hotelid;
        this.roomtype = roomtype;
    }
    public RoomTypeFeaturesId() {}

    public Long getHotel_id() {
        return hotelid;
    }
    public void setHotel_id(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getName() {
        return roomtype;
    }
    public void setName(String name) {
        this.roomtype = name;
    }
}
@Entity
@IdClass( RoomTypeFeaturesId.class )
@Table(name = "room_type_features")
public class RoomTypeFeatures {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "room_type")
    String roomtype;
    @Column(name = "feature")
    String feature;

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
