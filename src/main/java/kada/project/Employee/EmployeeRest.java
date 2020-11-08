package kada.project.Employee;

import kada.project.UserSession.CustomUserDetails;
import kada.project.UserSession.CustomUserDetailsService;
import kada.project.UserSession.JwtProvider;
import kada.project.UserSession.UserService;
import kada.project.bookinghistory.BookingHistory;
import kada.project.bookinghistory.BookingHistoryREST;
import kada.project.bookinghistory.BookingHistoryRepo;
import kada.project.hotels.*;
import kada.project.room.RoomType;
import kada.project.room.RoomTypeRepo;
import kada.project.user.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
@RestController
@RequestMapping("/api")
public class EmployeeRest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private BookingHistoryRepo bookingHistoryRepo;
    @Autowired
    private BookingHistoryREST bookingHistoryREST;
    @Autowired
    private OccupationHistoryRepo occupationHistoryRepo;
    @Autowired
    private RoomTypeRepo roomTypeRepo;
    @Autowired
    private HotelSeasonsRepo hotelSeasonsRepo;
    @Autowired
    private SeasonsRepo seasonsRepo;

//    @GetMapping("/employee/getemployee")
//    public List<Employee> findAllEmployees() {
//        System.out.println("curr  " + SecurityContextHolder.getContext().getAuthentication().getAuthorities()); return employeeRepo.findAll();
//    }

    @PostMapping("/logindeskclerk")
    public ResponseEntity<Employee> getGuestByEmail(@Validated @RequestBody Employee empl) {
        Employee employee = userService.findEmployeeByLoginAndPassword(empl.getEmail(), empl.getPassword()); //ok
        String token = jwtProvider.generateToken(empl.getEmail()); //ok
        employee.setToken( token );
        System.out.println(token);
        jwtProvider.hashmap.put( token, "deskclerk" );
        employee.setRole( "DESKCLERK" );
        employeeRepo.save( employee );
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/deskclerk/logout/{token}")
    public ResponseEntity<Employee> LogOut(@PathVariable(value = "token") String token) {
        String email = jwtProvider.getLoginFromToken( token );
        Employee employee = employeeRepo.findByEmail( email );
        employee.setRole( "" );
        jwtProvider.hashmap.remove( employee.getToken() );
        employee.setToken( "" );
        employeeRepo.save( employee );
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/deskclerk/changestatusbh/{bh_id}/{status}") //change status
    public ResponseEntity<BookingHistory> changeStatus(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "status") String status) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingid( bh_id );
        bookingHistory.setStatus( status );
        bookingHistoryRepo.save( bookingHistory );
        return ResponseEntity.ok().body(bookingHistory);
    }

    @PutMapping("/cancelbooking/{bh_id}/{number_of_rooms}") //change status
    public ResponseEntity<BookingHistory> changeStatus(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "number_of_rooms") Integer number_of_rooms) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingid( bh_id );
        System.out.println(bookingHistory.getGuestid());
        bookingHistory.setNumber_of_rooms( bookingHistory.getNumber_of_rooms()-number_of_rooms );

        bookingHistory.setPrice( 0 );
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
        bookingHistoryRepo.save(bookingHistory);

        List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByBookingid( bh_id );
        for(int i = 0; i < number_of_rooms; i++) {
            occupationHistoryRepo.delete( occupationHistoryList.get( 0 ) );
            occupationHistoryList.remove( 0 );
        }
        return ResponseEntity.ok().body(bookingHistory);
    }

    @PostMapping("/changebooking")
    public ResponseEntity<BookingHistory> changeBooking(@Validated @RequestBody BookingHistory bookingHistory) {
        BookingHistory toDelete = bookingHistoryRepo.findByBookingid( bookingHistory.getBookingid() );
        bookingHistoryRepo.delete( toDelete );
        bookingHistory.setPrice( 0 );
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
        bookingHistoryRepo.save(bookingHistory);
        return ResponseEntity.ok().body(bookingHistory);
    }


}
