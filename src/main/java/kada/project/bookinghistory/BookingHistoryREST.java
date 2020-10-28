package kada.project.bookinghistory;

import com.fasterxml.jackson.core.JsonProcessingException;
import kada.project.hotels.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLTableCaptionElement;
import kada.project.hotels.HotelServicesRepo;
import java.util.*;

// 10 oct - 20 oct
// 10 wed
// for(int i = 0; i < 11; i++) {
//
// }
@RestController
@RequestMapping("/api")
public class BookingHistoryREST {

    @Autowired
    private BookingHistoryRepo bookingHistoryRepo;
    @Autowired
    private GuestUsesServicesRepo guestUsesServicesRepo;
    @Autowired
    private HotelServicesRepo hotelServicesRepo;

    //get guests
    @GetMapping("/bookinghistory")
    public List<BookingHistory> getAllBookingHistory() {
        return this.bookingHistoryRepo.findAll();
    }

    //get guests by booking_id
    @GetMapping("/bookinghistory/{bookingid}")
    public ResponseEntity findByBI(@PathVariable(value = "bookingid") Long bookingid) throws JsonProcessingException {

        BookingHistory objects = bookingHistoryRepo.findByBookingid( bookingid );
        List<GuestUsesService> list = guestUsesServicesRepo.findByBookingid( bookingid );
        Booking booking = new Booking( objects,  list);
        return new ResponseEntity(booking.getJson(), HttpStatus.OK );
    }

    //get guests by booking_id
    @GetMapping("/bookinghistory/byguest/{guestid}")
    public @ResponseBody List<BookingHistory> findByGI(@PathVariable(value = "guestid") Long guestid) {

        List<BookingHistory> objects = bookingHistoryRepo.findByGuestid( guestid );
        return objects;
    }

    @PostMapping("/booking/addservice/{bookingid}/{service}")
    public ResponseEntity addService(@PathVariable(value = "bookingid") Long bookingid, @PathVariable(value = "service") String service) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingid(bookingid);
        HotelServices hotelServices = hotelServicesRepo.findByHotelidAndService( bookingHistory.hotelid, service );
        bookingHistory.setPrice( bookingHistory.getPrice() + hotelServices.getPrice() );
        bookingHistoryRepo.save( bookingHistory );
        GuestUsesService guestUsesService = guestUsesServicesRepo.findByBookingidAndService( bookingid, service );
        guestUsesService.setHowmanytimes( guestUsesService.getHowmanytimes() + 1 );
        guestUsesServicesRepo.save( guestUsesService );

        return new ResponseEntity("success!!!", HttpStatus.OK);
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
        bookingHistory.setStatus( bookingDets.getStatus() );
        bookingHistory.setRoomtype( bookingDets.getRoomtype() );
        bookingHistory.setDate_reservation( bookingDets.getDate_reservation() );
        bookingHistory.setDue_date( bookingDets.getDue_date() );
        bookingHistory.setNumber_of_rooms( bookingDets.getNumber_of_rooms() );
        bookingHistory.setGuestid( bookingDets.getGuestid() );
        bookingHistory.setPrice( bookingDets.getPrice() );
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
