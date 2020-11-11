package kada.project.room;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

class RoomId implements Serializable {
    Long hotelid;
    String roomtype;
    Integer roomnumber;

    public RoomId(Long hotelid, String roomtype, Integer roomnumber) {
        this.hotelid = hotelid;
        this.roomtype = roomtype;
        this.roomnumber = roomnumber;
    }

    public RoomId() {}

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

    public Integer getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(Integer roomnumber) {
        this.roomnumber = roomnumber;
    }
}
@Entity
@IdClass( RoomId.class )
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "room_type")
    String roomtype;
    @Id
    @Column(name = "room_number")
    Integer roomnumber;
    @Column(name = "floor")
    Integer floor;
    @Column(name = "last_cleaned")
    Date lastcleaned;

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

    public Integer getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(Integer roomnumber) {
        this.roomnumber = roomnumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Date getLastcleaned() {
        return lastcleaned;
    }

    public void setLastcleaned(Date lastcleaned) {
        this.lastcleaned = lastcleaned;
    }
}
