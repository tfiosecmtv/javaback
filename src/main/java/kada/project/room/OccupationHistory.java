package kada.project.room;


import javax.persistence.*;
import java.io.Serializable;

class OccupationHistoryId implements Serializable {
    Long hotelid;
    Long guestid;
    String roomtype;
    String roomnumber;

    public OccupationHistoryId(Long hotelid, Long guestid, String roomtype, String roomnumber) {
        this.hotelid = hotelid;
        this.guestid = guestid;
        this.roomtype = roomtype;
        this.roomnumber = roomnumber;
    }

    public OccupationHistoryId() {}

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public Long getGuestid() {
        return guestid;
    }

    public void setGuestid(Long guestid) {
        this.guestid = guestid;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }
}
@Entity
@IdClass( OccupationHistoryId.class )
@Table(name = "occupation_history")
public class OccupationHistory {
    @Id
    @Column(name = "guest_id")
    Long guestid;
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "room_type")
    String roomtype;
    @Id
    @Column(name = "room_number")
    Integer roomnumber;
    @Column(name = "from_date")
    String fromdate;
    @Column(name = "to_date")
    String todate;

    public Long getGuestid() {
        return guestid;
    }

    public void setGuestid(Long guestid) {
        this.guestid = guestid;
    }

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

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public Integer getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(Integer roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }
}
