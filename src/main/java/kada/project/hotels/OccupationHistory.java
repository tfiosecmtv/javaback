package kada.project.hotels;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

class OccupationHistoryId implements Serializable {
    Long hotelid;
    String roomtype;
    Integer roomnumber;
    Long guestid;

    public OccupationHistoryId(Long hotelid, String roomtype, Integer roomnumber, Long guestid) {
        this.hotelid = hotelid;
        this.roomtype = roomtype;
        this.roomnumber = roomnumber;
        this.guestid = guestid;
    }

    public OccupationHistoryId() {}

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

    public Long getGuestid() {
        return guestid;
    }

    public void setGuestid(Long guestid) {
        this.guestid = guestid;
    }
}
@Entity
@IdClass( OccupationHistoryId.class )
@Table(name = "occupation_history")
public class OccupationHistory {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "guest_id")
    Long guestid;
    @Id
    @Column(name = "room_type")
    String roomtype;
    @Id
    @Column(name = "room_number")
    Integer roomnumber;
    @Column(name = "from_date")
    Date fromdate;
    @Column(name = "to_date")
    Date todate;


    public Long getHotel_id() {
        return hotelid;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotelid = hotel_id;
    }

    public Long getGuest_id() {
        return guestid;
    }

    public void setGuest_id(Long guest_id) {
        this.guestid = guest_id;
    }

    public String getRoom_type() {
        return roomtype;
    }

    public void setRoom_type(String room_type) {
        this.roomtype = room_type;
    }

    public Integer getRoom_number() {
        return roomnumber;
    }

    public void setRoom_number(Integer room_number) {
        this.roomnumber = room_number;
    }

    public Date getFrom_date() {
        return fromdate;
    }

    public void setFrom_date(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTo_date() {
        return todate;
    }

    public void setTo_date(Date todate) {
        this.todate = todate;
    }
}
