
package kada.project.bookinghistory;
import javax.persistence.*;
//import java.time.LocalDate;

@Entity
@Table(name = "bookinghistory")
public class BookingHistory {
    private Long booking_id;
    private Long guestid;
    private Long hotel_id;
    private String room_type;
    private String date_reservation;
    private String due_date;
    private String number_of_rooms;
    private String payment_status;
    private String status;
    private Integer price;

    public BookingHistory(Long hotel_id, Long guestid, Integer price, String room_type, String date_reservation, String due_date, String number_of_rooms, String payment_status, String appointment_status, Long booking_id) {
        this.room_type = room_type;
        this.date_reservation = date_reservation;
        this.due_date = due_date;
        this.number_of_rooms = number_of_rooms;
        this.payment_status = payment_status;
        this.status = appointment_status;
        this.booking_id = booking_id;
        this.price = price;
        this.guestid = guestid;
        this.hotel_id = hotel_id;
    }

    public BookingHistory() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getBooking_id() {
        return booking_id;
    }
    public void setBooking_id(Long booking_id) {
        this.booking_id = booking_id;
    }

    @Column(name = "room_type")
    public String getRoom_type() {
        return room_type;
    }
    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    @Column(name = "date_reservation")
    public String getDate_reservation() {
        return date_reservation;
    }
    public void setDate_reservation(String date_reservation) {
        this.date_reservation = date_reservation;
    }

    @Column(name = "due_date")
    public String getDue_date() {
        return due_date;
    }
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    @Column(name = "number_of_rooms")
    public String getNumber_of_rooms() {
        return number_of_rooms;
    }
    public void setNumber_of_rooms(String number_of_rooms) {
        this.number_of_rooms = number_of_rooms;
    }

    @Column(name = "payment_status")
    public String getPayment_status() {
        return payment_status;
    }
    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }
    public void setStatus(String appointment_status) {
        this.status = appointment_status;
    }

    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }

    @Column(name = "hotel_id")
    public Long getHotel_id() {
        return hotel_id;
    }
    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }
    @Column(name = "guest_id")
    public Long getGuestid() {
        return guestid;
    }
    public void setGuestid(Long guest_id) {
        this.guestid = guest_id;
    }
}
