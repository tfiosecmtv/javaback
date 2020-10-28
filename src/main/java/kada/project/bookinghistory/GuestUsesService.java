package kada.project.bookinghistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

class GuestUsesServiceId implements Serializable {
    Long hotelid;
    Long guestid;
    Long bookingid;
    String roomtype;
    String service;

    public GuestUsesServiceId(Long hotelid, Long guestid, Long bookingid, String roomtype, String service) {
        this.hotelid = hotelid;
        this.guestid = guestid;
        this.bookingid = bookingid;
        this.roomtype = roomtype;
        this.service = service;
    }
    public GuestUsesServiceId() {}

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

    public Long getBookingid() {
        return bookingid;
    }

    public void setBookingid(Long bookingid) {
        this.bookingid = bookingid;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
@Entity
@IdClass( GuestUsesServiceId.class )
public class GuestUsesService {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "guest_id")
    Long guestid;
    @Id
    @Column(name = "booking_id")
    Long bookingid;
    @Id
    @Column(name = "room_type")
    String roomtype;
    @Id
    @Column(name = "service")
    String service;
    @Column(name = "how_many_times")
    Integer howmanytimes;

    public GuestUsesService(Long hotelid, Long guest_id, Long booking_id, String roomtype, String service, Integer howmanytimes) {
        this.hotelid = hotelid;
        this.guestid = guest_id;
        this.bookingid = booking_id;
        this.roomtype = roomtype;
        this.service = service;
        this.howmanytimes = howmanytimes;
    }

    public GuestUsesService() {}

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public Long getGuest_id() {
        return guestid;
    }

    public void setGuest_id(Long guest_id) {
        this.guestid = guest_id;
    }

    public Long getBooking_id() {
        return bookingid;
    }

    public void setBooking_id(Long booking_id) {
        this.bookingid = booking_id;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getHowmanytimes() {
        return howmanytimes;
    }

    public void setHowmanytimes(Integer howmanytimes) {
        this.howmanytimes = howmanytimes;
    }
}
