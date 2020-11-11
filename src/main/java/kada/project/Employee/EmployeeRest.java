package kada.project.Employee;

import kada.project.UserSession.CustomUserDetails;
import kada.project.UserSession.CustomUserDetailsService;
import kada.project.UserSession.JwtProvider;
import kada.project.UserSession.UserService;
import kada.project.bookinghistory.Booking;
import kada.project.bookinghistory.BookingHistory;
import kada.project.bookinghistory.BookingHistoryREST;
import kada.project.bookinghistory.BookingHistoryRepo;
import kada.project.hotels.*;
import kada.project.room.RoomType;
import kada.project.room.RoomTypeRepo;
import kada.project.user.Guest;
import kada.project.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import kada.project.room.Room;
import kada.project.room.RoomRepo;

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
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoomRepo roomRepo;

//    @GetMapping("/employee/getemployee")
//    public List<Employee> findAllEmployees() {
//        System.out.println("curr  " + SecurityContextHolder.getContext().getAuthentication().getAuthorities()); return employeeRepo.findAll();
//    }

    @PutMapping("/deskclerk/findguest")
    public List<BookingHistory> getguestsbooking(@Validated @RequestBody Guest guest) {
        List<BookingHistory> bookingHistoryList = new ArrayList<BookingHistory>();
        List<Guest> guestList = userRepo.findByFirstNameAndLastName( guest.getFirstName(), guest.getLastName() );
        System.out.println(guest.getFirstName());
        System.out.println(guest.getLastName());
        for(Guest guest1 : guestList) {
            bookingHistoryList.addAll( bookingHistoryRepo.findByGuestid( guest1.getUserId() ) );
        }
        return bookingHistoryList;
    }

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

    @PutMapping("/deskclerk/changestatusbh/{bh_id}/{roomtype}/{status}") //change status
    public ResponseEntity<BookingHistory> changeStatus(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "status") String status, @PathVariable(value = "roomtype") String roomtype) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingidAndRoomtype( bh_id, roomtype );
        bookingHistory.setStatus( status );
        bookingHistoryRepo.save( bookingHistory );
        return ResponseEntity.ok().body(bookingHistory);
    }

    @PutMapping("/deskclerk/cancelbooking/{bh_id}/{room_type}/{number_of_rooms}") //change status
    public ResponseEntity<BookingHistory> changeStatus(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "number_of_rooms") Integer number_of_rooms, @PathVariable(value = "room_type") String room_type) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingidAndRoomtype( bh_id, room_type );
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

        List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByHotelidAndRoomtype( bh_id, room_type );
        for(int i = 0; i < number_of_rooms; i++) {
            occupationHistoryRepo.delete( occupationHistoryList.get( 0 ) );
            occupationHistoryList.remove( 0 );
        }

        return ResponseEntity.ok().body(bookingHistory);
    }

    @PostMapping("/deskclerk/changebooking")
    public ResponseEntity<BookingHistory> changeBooking(@Validated @RequestBody BookingHistory bookingHistory) {
        String prevroomtype = bookingHistoryRepo.findByBookingid( bookingHistory.getBookingid() ).getRoomtype();
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

//        bookingHistoryRepo.delete( bookingHistoryRepo.findByBookingidAndRoomtype( bookingHistory.getBookingid(), prevroomtype ) );


        List<OccupationHistory> occupationHistory = occupationHistoryRepo.findByBookingidAndRoomtype( bookingHistory.getBookingid(), prevroomtype );

        cStart.setTime( start );
        cStart.add( Calendar.DAY_OF_MONTH,-1 );
        for (OccupationHistory oh : occupationHistory) {
            oh.setTo_date( cStart.getTime() );
            occupationHistoryRepo.save( oh );
        }

        // creating new occupationhistory
        // frontend stores available room numbers and booking history record
        // through loop

        return ResponseEntity.ok().body(bookingHistory);
    }

    @PostMapping("/deskclerk/filterbyroomtype")
    public List<Integer> filter(@Validated @RequestBody BookingHistory bookingHistory) {
        List<Integer> resultList = new ArrayList<>();
        List<Room> roomList = roomRepo.findByHotelidAndRoomtype( bookingHistory.getHotelid(), bookingHistory.getRoomtype() );
        for(Room room : roomList) {
            List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByHotelidAndRoomnumber( room.getHotelid(), room.getRoomnumber() );
            Filter filter = new Filter();
            List<DateInterval> dateIntervals = new ArrayList<DateInterval>();
            for(OccupationHistory occupationHistory : occupationHistoryList) {
                dateIntervals.add( new DateInterval( occupationHistory.getFrom_date(), occupationHistory.getTo_date() ) );
            }
            dateIntervals.add( new DateInterval( bookingHistory.getDate_reservation(), bookingHistory.getDue_date() ) );
            String res = filter.findOverlap( dateIntervals );
            if(res.equals( "It’s a clean list" ) == true)
                resultList.add( room.getRoomnumber() );
        }
        return resultList;
    }

    @PutMapping("/deskclerk/changeroom/{bh_id}/{room_num}") //change room
    public ResponseEntity changeRoom(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "room_num") Integer room_num) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingid( bh_id );

        List<Integer> availableRoomList = filter( bookingHistory );
        if(availableRoomList.size() == 0)
            return  new ResponseEntity("no rooms available!", HttpStatus.OK);


        for (Integer r : availableRoomList )
        {

            Calendar today = Calendar.getInstance();
            today.setTime( bookingHistory.getDate_reservation() );

            Date lastCleaned = roomRepo.findByHotelidAndRoomnumber( bookingHistory.getHotelid(), r ).getLastcleaned();

            List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByHotelidAndRoomnumber( bookingHistory.getHotelid(), r );
            List<Date> dateList = new ArrayList<>();
            for(OccupationHistory occupationHistory : occupationHistoryList)
                dateList.add( occupationHistory.getTo_date() );
            Collections.sort( dateList );

            if(lastCleaned.after( dateList.get( dateList.size()-1 ) ) || lastCleaned.equals( dateList.get( dateList.size()-1 ) )) {
                List<OccupationHistory> occupationHistoryList1 = occupationHistoryRepo.findAll();
                OccupationHistory occupationHistory = occupationHistoryRepo.findByRoomnumberAndBookingid( room_num, bh_id );
                occupationHistory.setRoomnumber( r );
                occupationHistoryRepo.save( occupationHistory );
                return ResponseEntity.ok().body(occupationHistory);
            }
        }

        return ResponseEntity.ok().body("cannot change!!!!");

    }


}
