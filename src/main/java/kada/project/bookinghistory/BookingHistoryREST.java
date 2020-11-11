package kada.project.bookinghistory;

import com.fasterxml.jackson.core.JsonProcessingException;
import kada.project.hotels.*;
import kada.project.room.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLTableCaptionElement;

import java.util.*;

// 10 oct - 20 oct
// 10 wed
// for(int i = 0; i < 11; i++) {
//
// }
@RestController
@RequestMapping("/api")
public class BookingHistoryREST {

    public BookingHistoryREST(){
    }

    @Autowired
    private BookingHistoryRepo bookingHistoryRepo;
    @Autowired
    private GuestUsesServicesRepo guestUsesServicesRepo;
    @Autowired
    private HotelServicesRepo hotelServicesRepo;
    @Autowired
    private RoomTypeRepo roomTypeRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private HotelSeasonsRepo hotelSeasonsRepo;
    @Autowired
    private SeasonsRepo seasonsRepo;
    @Autowired
    private OccupationHistoryRepo occupationHistoryRepo;
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

    @PostMapping("/booking/addservice")
    public ResponseEntity addService(@Validated @RequestBody GuestUsesService guestUsesService) {
        System.out.println(guestUsesService.getHotel_id()); //
        System.out.println(guestUsesService.getGuest_id());
        System.out.println(guestUsesService.getBookingid());
        System.out.println(guestUsesService.getRoom_type()); //
        System.out.println(guestUsesService.getService());
        System.out.println(guestUsesService.getHow_many_times()); //
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingidAndRoomtype(guestUsesService.getBookingid(), guestUsesService.getRoom_type());
        HotelServices hotelServices = hotelServicesRepo.findByHotelidAndService( bookingHistory.hotelid, guestUsesService.getService() );
        bookingHistory.setService_price( bookingHistory.getService_price() + hotelServices.getPrice()*guestUsesService.getHow_many_times() );
        bookingHistory.setPrice( bookingHistory.getPrice() + hotelServices.getPrice()*guestUsesService.getHow_many_times() );
        bookingHistoryRepo.save( bookingHistory );
        guestUsesService.setHow_many_times( guestUsesService.getHow_many_times()+1 );
        guestUsesServicesRepo.save( guestUsesService );

        return new ResponseEntity("success!!!", HttpStatus.OK);
    }



    @PostMapping("/bookinghistory/addoccupation")
    public OccupationHistory addoh(@Validated @RequestBody OccupationHistory occupationHistory)  {
        occupationHistoryRepo.save( occupationHistory );
        return occupationHistory;
    }

    @PostMapping("/bookinghistory")
    public BookingHistory signup(@Validated @RequestBody BookingHistory bookingHistory) {
        System.out.println("in "+ bookingHistory.getGuestid());
        Date start = bookingHistory.getDate_reservation();
        Date end = bookingHistory.getDue_date();
        Calendar cStart = Calendar.getInstance();
        cStart.setTime( start );
        cStart.add( Calendar.DAY_OF_MONTH,-1 );
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime( end );
        RoomType roomType = roomTypeRepo.findByHotelidAndName( bookingHistory.getHotelid(), bookingHistory.getRoomtype() );
        while(cStart.before( cEnd )) {
            if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                bookingHistory.setPrice( bookingHistory.getPrice() + roomType.getBase_price_mon() );
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                bookingHistory.setPrice( bookingHistory.getPrice() + roomType.getBase_price_tue() );
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                bookingHistory.setPrice( bookingHistory.getPrice() + roomType.getBase_price_wed() );
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                bookingHistory.setPrice( bookingHistory.getPrice() + roomType.getBase_price_thu() );
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                bookingHistory.setPrice( bookingHistory.getPrice() + roomType.getBase_price_fri() );
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                bookingHistory.setPrice( bookingHistory.getPrice() + roomType.getBase_price_sat() );
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                bookingHistory.setPrice( bookingHistory.getPrice() + roomType.getBase_price_sun() );
            cStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        cStart.setTime( start );
        cStart.add( Calendar.DAY_OF_MONTH,-1 );
        List<HotelSeasons> hotelSeasons = hotelSeasonsRepo.findByHotelid( bookingHistory.getHotelid() );
        while (cStart.before( cEnd )) {
            for(HotelSeasons hotelseason : hotelSeasons ) {
                Seasons season = seasonsRepo.findSeasonsByName( hotelseason.getSeason() );
                if(cStart.getTime().getTime() >= season.getStart_date().getTime() &&
                        cStart.getTime().getTime() <= season.getEnd_date().getTime()) {
                    bookingHistory.setPrice( bookingHistory.getPrice()+hotelseason.getAdd_price() );
                    break;
                }
            }
            cStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        bookingHistory.setPrice( bookingHistory.getPrice()*bookingHistory.getNumber_of_rooms());
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
    public String deleteBooking(@PathVariable(value = "booking_id") Long booking_id)
    {
        BookingHistory bookingHistory = bookingHistoryRepo.findById(booking_id)
                .orElseThrow();
        List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByBookingid( booking_id );
        bookingHistoryRepo.delete(bookingHistory);
        occupationHistoryRepo.deleteAll(occupationHistoryList);
        return "deleted";
    }

}
