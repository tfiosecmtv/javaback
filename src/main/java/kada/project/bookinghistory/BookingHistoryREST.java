package kada.project.bookinghistory;

import com.fasterxml.jackson.core.JsonProcessingException;
import kada.project.Employee.DateInterval;
import kada.project.Employee.Filter;
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

    int price(Date s, Date e,String roomtypename, Long hotelid) {
        int price = 0;
        Date start = s;
        Date end = e;
        Calendar cStart = Calendar.getInstance();
        cStart.setTime( start );
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime( end );
        cEnd.add( Calendar.DAY_OF_MONTH,1 );
        RoomType roomType = roomTypeRepo.findByHotelidAndName( hotelid, roomtypename );
        while(cStart.before( cEnd )) {
            if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                price += roomType.getBase_price_mon() ;
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                price += roomType.getBase_price_tue() ;
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                price += roomType.getBase_price_wed() ;
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                price += roomType.getBase_price_thu() ;
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                price += roomType.getBase_price_fri() ;
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                price += roomType.getBase_price_sat() ;
            else if (cStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                price += roomType.getBase_price_sun() ;
            cStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        cStart.setTime( start );

        List<HotelSeasons> hotelSeasons = hotelSeasonsRepo.findByHotelid( hotelid );
        while(start.before( end ) || start.equals( end )) {
            for(HotelSeasons hotelseason : hotelSeasons) {
                Seasons season = seasonsRepo.findSeasonsByName( hotelseason.getSeason() );
                Filter filter = new Filter();
                if(filter.overlaps( new DateInterval( start, start ),  new DateInterval( season.getStart_date(), season.getEnd_date() )))
                    price += hotelseason.getAdd_price();
            }
            start = new Date(start.getTime() + (1000 * 60 * 60 * 24));
        }
        return price;
    }

    @PostMapping("/bookinghistory")
    public BookingHistory signup(@Validated @RequestBody BookingHistory bookingHistory) {
        bookingHistory.setPrice(bookingHistory.number_of_rooms*price( bookingHistory.date_reservation, bookingHistory.due_date, bookingHistory.roomtype, bookingHistory.hotelid ) );
        List<BookingHistory> bookingHistoryList = bookingHistoryRepo.findAll();
        bookingHistory.setBookingid( Long.valueOf( bookingHistoryList.size()+1 ) );
        bookingHistoryRepo.save(bookingHistory);
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
