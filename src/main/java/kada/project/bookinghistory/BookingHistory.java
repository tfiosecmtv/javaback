
package kada.project.bookinghistory;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
//import java.time.LocalDate;
class BookingHistoryId implements Serializable {
    Long hotelid;
    Long guestid;
    Long bookingid;
    String roomtype;

    public BookingHistoryId(Long hotelid, Long guestid, Long bookingid, String roomtype) {
        this.hotelid = hotelid;
        this.guestid = guestid;
        this.bookingid = bookingid;
        this.roomtype = roomtype;
    }

    public BookingHistoryId() {}

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
}

@Entity
@IdClass( BookingHistoryId.class )
@Table(name = "bookinghistory")
public class BookingHistory {


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
    @Column(name = "status")
    String status;
    @Column(name = "date_reservation")
    Date date_reservation;
    @Column(name = "due_date")
    Date due_date;
    @Column(name = "number_of_rooms")
    Integer number_of_rooms;
    @Column(name = "price")
    Integer price;
    @Column(name = "service_price")
    Integer serviceprice;
    @Column(name = "category")
    String category;
    public String prevroomtype;
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    public BookingHistory(Long hotelid, Long guestid, Long bookingid, String roomtype, String status, Date date_reservation, Date due_date, Integer number_of_rooms, Integer price, Integer serviceprice) {
        this.hotelid = hotelid;
        this.guestid = guestid;
        this.bookingid = bookingid;
        this.roomtype = roomtype;
        this.status = status;
        this.date_reservation = date_reservation;
        this.due_date = due_date;
        this.number_of_rooms = number_of_rooms;
        this.price = price;
        this.serviceprice = serviceprice;
    }

    public BookingHistory() {

    }

    public Integer getService_price() {
        return serviceprice;
    }

    public void setService_price(Integer serviceprice) {
        this.serviceprice = serviceprice;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Integer getNumber_of_rooms() {
        return number_of_rooms;
    }

    public void setNumber_of_rooms(Integer number_of_rooms) {
        this.number_of_rooms = number_of_rooms;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
