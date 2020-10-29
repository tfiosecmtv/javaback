package kada.project.bookinghistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

class GuestUsesServiceId implements Serializable {
    Long hotel_id;
    Long guest_id;
    Long bookingid;
    String room_type;
    String service;

    public GuestUsesServiceId(Long hotelid, Long guestid, Long bookingid, String roomtype, String service) {
        this.hotel_id = hotelid;
        this.guest_id = guestid;
        this.bookingid = bookingid;
        this.room_type = roomtype;
        this.service = service;
    }
    public GuestUsesServiceId() {}

    public Long getHotelid() {
        return hotel_id;
    }

    public void setHotelid(Long hotelid) {
        this.hotel_id = hotelid;
    }

    public Long getGuestid() {
        return guest_id;
    }

    public void setGuestid(Long guestid) {
        this.guest_id = guestid;
    }

    public Long getBookingid() {
        return bookingid;
    }

    public void setBookingid(Long bookingid) {
        this.bookingid = bookingid;
    }

    public String getRoomtype() {
        return room_type;
    }

    public void setRoomtype(String roomtype) {
        this.room_type = roomtype;
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
    Long hotel_id;
    @Id
    @Column(name = "guest_id")
    Long guest_id;
    @Id
    @Column(name = "booking_id")
    Long bookingid;
    @Id
    @Column(name = "room_type")
    String room_type;
    @Id
    @Column(name = "service")
    String service;
    @Column(name = "how_many_times")
    Integer how_many_times;

    public GuestUsesService(Long hotel_id, Long guest_id, Long booking_id, String room_type, String service, Integer how_many_times) {
        this.hotel_id = hotel_id;
        this.guest_id = guest_id;
        this.bookingid = booking_id;
        this.room_type = room_type;
        this.service = service;
        this.how_many_times = how_many_times;
    }

    public GuestUsesService() {}

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Long getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Long guest_id) {
        this.guest_id = guest_id;
    }

    public Long getBooking_id() {
        return bookingid;
    }

    public void setBooking_id(Long booking_id) {
        this.bookingid = booking_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getHow_many_timestimes() {
        return how_many_times;
    }

    public void setHow_many_times(Integer how_many_times) {
        this.how_many_times = how_many_times;
    }
}
