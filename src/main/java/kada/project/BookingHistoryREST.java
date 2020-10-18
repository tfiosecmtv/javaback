package kada.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class BookingHistoryREST {

    @Autowired
    private BookingHistoryRepo bookingHistoryRepo;

    //get guests
    @GetMapping("/bookinghistory")
    public List<BookingHistory> getAllBookingHistory() {
        return this.bookingHistoryRepo.findAll();
    }

    //get guests by email
    @GetMapping("/bookinghistory/{booking_id}")
    public ResponseEntity<BookingHistory> getBookingHistoryByBookingId(@PathVariable(value = "booking_id") Long booking_id) {
        BookingHistory bookingHistory = bookingHistoryRepo.findById( booking_id ).orElseThrow();
        return ResponseEntity.ok().body(bookingHistory);
    }

    //signup
    @PostMapping("/bookinghistory")
    public BookingHistory signup(@Validated @RequestBody BookingHistory bookingHistory) {
        return bookingHistoryRepo.save(bookingHistory);
    }
    //update

    @PutMapping("/bookinghistory/{booking_id}")
    public ResponseEntity<BookingHistory> updatebooking(@PathVariable(value = "booking_id") Long booking_id,
                                             @Validated @RequestBody BookingHistory bookingDets)  {
        System.out.println(booking_id.toString());
        BookingHistory bookingHistory = bookingHistoryRepo.findById(booking_id)
                .orElseThrow( );

        bookingHistory.setAppointment_status( bookingDets.getAppointment_status() );
        bookingHistory.setRoom_type( bookingDets.getRoom_type() );
        bookingHistory.setDate_reservation( bookingDets.getDate_reservation() );
        bookingHistory.setDue_date( bookingDets.getDue_date() );
        bookingHistory.setNumber_of_rooms( bookingDets.getNumber_of_rooms() );
        bookingHistory.setPayment_status( bookingDets.getPayment_status() );
        final BookingHistory updatedBooking = bookingHistoryRepo.save(bookingHistory);
        return ResponseEntity.ok(updatedBooking);
    }

    //delete

    @DeleteMapping("/bookinghistory/{booking_id}")
    public Map<String, Boolean> deleteBooking(@PathVariable(value = "booking_id") Long booking_id)
    {
        BookingHistory bookingHistory = bookingHistoryRepo.findById(booking_id)
                .orElseThrow();

        bookingHistoryRepo.delete(bookingHistory);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
