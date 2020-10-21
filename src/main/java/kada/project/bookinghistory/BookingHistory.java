package kada.project.bookinghistory;
import javax.persistence.*;
//import java.time.LocalDate;

@Entity
@Table(name = "bookinghistory")
public class BookingHistory {
    private Long booking_id;
    private String room_type;
    private String date_reservation;
    private String due_date;
    private String number_of_rooms;
    private String payment_status;
    private String appointment_status;
    private String email;



    public BookingHistory(String email,String room_type, String date_reservation, String due_date, String number_of_rooms, String payment_status, String appointment_status, Long booking_id) {
        this.room_type = room_type;
        this.date_reservation = date_reservation;
        this.due_date = due_date;
        this.number_of_rooms = number_of_rooms;
        this.payment_status = payment_status;
        this.appointment_status = appointment_status;
        this.booking_id = booking_id;
        this.email = email;
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

    @Column(name = "appointment_status")
    public String getAppointment_status() {
        return appointment_status;
    }
    public void setAppointment_status(String appointment_status) {
        this.appointment_status = appointment_status;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "BookingHistory [room_type=" + room_type + ", date_reservation=" + date_reservation + ", due_date=" + due_date + ", number_of_rooms=" + number_of_rooms + ", payment_status=" + payment_status + ", appointment_status=" + appointment_status + ", booking_id=" + booking_id + ", email=" + email+ "]";
    }
}
