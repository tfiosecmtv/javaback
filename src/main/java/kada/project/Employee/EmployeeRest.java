package kada.project.Employee;

import kada.project.UserSession.CustomUserDetailsService;
import kada.project.UserSession.JwtProvider;
import kada.project.UserSession.UserService;
import kada.project.bookinghistory.*;
import kada.project.hotels.*;
import kada.project.room.RoomType;
import kada.project.room.RoomTypeRepo;
import kada.project.user.Guest;
import kada.project.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import kada.project.room.Room;
import kada.project.room.RoomRepo;
//import java.text.DateFormat;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import java.util.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import java.sql.Timestamp;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.format.annotation.DateTimeFormat;
import java.text.ParseException;
import java.text.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@RestController
@RequestMapping("/api")
public class EmployeeRest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private HotelDiscountsRepo hotelDiscountsRepo;
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
    @Autowired
    private GuestUsesServicesRepo guestUsesServiceRepo;
    @Autowired
    private ScheduleRepo scheduleRepo;

    @GetMapping("/employee/getemployee") //works
    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    @PutMapping("/deskclerk/findguest") //works
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

    @PostMapping("/loginemployee") //works
    public ResponseEntity<Employee> getGuestByEmail(@Validated @RequestBody Employee empl) {
        Employee employee = userService.findEmployeeByLoginAndPassword(empl.getEmail(), empl.getPassword()); //ok
        String token = jwtProvider.generateToken(empl.getEmail()); //ok
        employee.setToken( token );
        System.out.println(token);
        if(employee.getJob_title().equals( "Desk Clerk" )) {
            jwtProvider.hashmap.put( token, "DESKCLERK" );
            employee.setRole( "DESKCLERK" );
        } else if(employee.getJob_title().equals( "Manager" )){
            jwtProvider.hashmap.put( token, "MANAGER" );
            employee.setRole( "MANAGER" );
        }

        employeeRepo.save( employee );
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/deskclerk/logout/{token}") //works
    public ResponseEntity<Employee> LogOut(@PathVariable(value = "token") String token) {
        String email = jwtProvider.getLoginFromToken( token );
        Employee employee = employeeRepo.findByEmail( email );
        employee.setRole( "" );
        jwtProvider.hashmap.remove( employee.getToken() );
        employee.setToken( "" );
        employeeRepo.save( employee );
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/deskclerk/changestatusbh/{bh_id}/{roomtype}/{status}") //works
    public ResponseEntity<BookingHistory> changeStatus(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "status") String status, @PathVariable(value = "roomtype") String roomtype) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingidAndRoomtype( bh_id, roomtype );
        if(status.equals( "Paid" )) {
            Long guestid = bookingHistory.getGuestid();
            Guest guest = userRepo.findByUserId( guestid );
            int price = bookingHistory.getPrice() + bookingHistory.getService_price();
            int discount = hotelDiscountsRepo.findByHotelidAndCategory( bookingHistory.getHotelid(), bookingHistory.getCategory() ).getDiscount();
            price -= (price*discount/100);
            bookingHistory.setPrice( price );
            guest.setPrice( guest.getPrice() +  price);
            userRepo.save( guest );
        }
        bookingHistory.setStatus( status );
        bookingHistoryRepo.save( bookingHistory );
        return ResponseEntity.ok().body(bookingHistory);
    }

    @PutMapping("/deskclerk/cancelbooking/{bh_id}/{room_type}/{number_of_rooms}") //works
    public ResponseEntity<BookingHistory> changeStatus(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "number_of_rooms") Integer number_of_rooms, @PathVariable(value = "room_type") String room_type) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingidAndRoomtype( bh_id, room_type );
        System.out.println(bookingHistory.getGuestid());
        bookingHistory.setNumber_of_rooms( bookingHistory.getNumber_of_rooms()-number_of_rooms );

        bookingHistory.setPrice( bookingHistory.getNumber_of_rooms()*price( bookingHistory.getDate_reservation(), bookingHistory.getDue_date(), bookingHistory.getRoomtype(), bookingHistory.getHotelid() ) );
        bookingHistoryRepo.save(bookingHistory);

        List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByBookingidAndRoomtype( bh_id, room_type );
        for(int i = 0; i < number_of_rooms; i++) {
            occupationHistoryRepo.delete( occupationHistoryList.get( 0 ) );
            occupationHistoryList.remove( 0 );
        }

        return ResponseEntity.ok().body(bookingHistory);
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

    @GetMapping("/deskclerk/alloccupationhistory/{hotelid}/{bookingid}")
    public List<OccupationHistory> getoh(@PathVariable("hotelid") Long hotelid, @PathVariable("bookingid") Long bookingid) {
        return occupationHistoryRepo.findByHotelidAndBookingid(hotelid, bookingid);
    }

    @PostMapping("/deskclerk/changebooking/{prevroomtype}")
    public ResponseEntity<BookingHistory> changeBooking(@Validated @RequestBody BookingHistory bookingHistory, @PathVariable("prevroomtype") String prevroomtype) {
//        String prevroomtype = bookingHistoryRepo.findByBookingid( bookingHistory.getBookingid() ).getRoomtype();

        bookingHistory.setPrice( 0 );
        Date start = bookingHistory.getDate_reservation();
        Date end = bookingHistory.getDue_date();
        Calendar cStart = Calendar.getInstance();
        Calendar cEnd = Calendar.getInstance();
        bookingHistory.setPrice( bookingHistory.getNumber_of_rooms()*price( bookingHistory.getDate_reservation(), bookingHistory.getDue_date(), bookingHistory.getRoomtype(), bookingHistory.getHotelid() ) );
        bookingHistoryRepo.save(bookingHistory);

        if(prevroomtype.equals( "none" ))
            return ResponseEntity.ok().body(bookingHistory);

//        List<GuestUsesService> guestUsesService = guestUsesServiceRepo.findByBookingid( bookingHistory.getBookingid() );
//        for (GuestUsesService guestUsesService1 : guestUsesService) {
//            guestUsesServiceRepo.delete( guestUsesService1 );
//            guestUsesService1.setRoom_type( bookingHistory.getRoomtype() );
//            guestUsesServiceRepo.save( guestUsesService1 );
//        } //works

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

    @PutMapping("/deskclerk/changeroom/{bh_id}/{roomtype}/{room_num}") //change room
    public ResponseEntity changeRoom(@PathVariable(value = "bh_id") Long bh_id, @PathVariable(value = "room_num") Integer room_num, @PathVariable(value = "roomtype") String roomtype) {
        BookingHistory bookingHistory = bookingHistoryRepo.findByBookingidAndRoomtype( bh_id, roomtype );

        List<Integer> availableRoomList = filter( bookingHistory );
        if(availableRoomList.size() == 0)
            return  new ResponseEntity("no rooms available!", HttpStatus.OK);


        for (Integer r : availableRoomList )
        {

            Calendar today = Calendar.getInstance();
            today.setTime( bookingHistory.getDate_reservation() );

            Date lastCleaned = roomRepo.findByHotelidAndRoomnumber( bookingHistory.getHotelid(), r ).getLastcleaned();

            List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByHotelidAndRoomnumber( bookingHistory.getHotelid(), r );
            if(occupationHistoryList.size() == 0) {
                OccupationHistory occupationHistory = occupationHistoryRepo.findByRoomnumberAndBookingid( room_num, bh_id );
                occupationHistoryRepo.delete( occupationHistory );
                occupationHistory.setRoomnumber( r );
                occupationHistoryRepo.save( occupationHistory );
                return ResponseEntity.ok().body(occupationHistory);
            }


            List<Date> dateList = new ArrayList<>();
            for(OccupationHistory occupationHistory : occupationHistoryList)
                dateList.add( occupationHistory.getTo_date() );
            Collections.sort( dateList );


            if(lastCleaned.after( dateList.get( dateList.size()-1 ) ) || lastCleaned.equals( dateList.get( dateList.size()-1 ) )) {
                List<OccupationHistory> occupationHistoryList1 = occupationHistoryRepo.findAll();
                OccupationHistory occupationHistory = occupationHistoryRepo.findByRoomnumberAndBookingid( room_num, bh_id );
                occupationHistoryRepo.delete( occupationHistory );
                occupationHistory.setRoomnumber( r );
                occupationHistoryRepo.save( occupationHistory );
                return ResponseEntity.ok().body(occupationHistory);
            }
        }

        return ResponseEntity.ok().body("cannot change!!!!");

    }

    @PostMapping("/manager/sendemail/{hotelid}")
    public String sendEmail(@Validated @RequestBody Mail mail, @PathVariable("hotelid") Long hotelid) throws AddressException, MessagingException, IOException {
        sendmail(mail.title, mail.price, mail.message, hotelid);
        return "Email sent successfully";
    }

    @GetMapping("/manager/getallseasons/{hotelid}")
    public List<HotelSeasons> getAllSeasons(@PathVariable("hotelid") Long hotelid) {
        return hotelSeasonsRepo.findByHotelid( hotelid );
    }

    @GetMapping("/manager/getallseasons")
    public List<Seasons> getAllSeasons() {
        return seasonsRepo.findAll();
    }

    @PostMapping("/manager/addseason")
    public ResponseEntity addSeason(@Validated @RequestBody HotelSeasons hotelSeasons) {
        List<HotelSeasons> hotelSeasonsList = hotelSeasonsRepo.findByHotelid( hotelSeasons.getHotelid() );
        System.out.println(hotelSeasons.getSeason());
        System.out.println(hotelSeasons.getAdd_price());
        System.out.println(hotelSeasons.getHotelid());
        List<DateInterval> dateIntervalList = new ArrayList<>();
        hotelSeasonsList.add( hotelSeasons );
        for(HotelSeasons hotelSeason : hotelSeasonsList) {
            Seasons seasons = seasonsRepo.findSeasonsByName( hotelSeason.getSeason() );
            dateIntervalList.add( new DateInterval( seasons.getStart_date(), seasons.getEnd_date() ) );
        }
        String ans = new Filter().findOverlap( dateIntervalList );
        if(ans.equals( "It’s a clean list" )) {
            hotelSeasonsRepo.save( hotelSeasons);
            return ResponseEntity.ok().body(hotelSeasons);
        }
        return ResponseEntity.ok().body("cannot add!!!!");
    }

    @DeleteMapping("/manager/deleteseason/{name}")
    public ResponseEntity deleteSeason(@PathVariable("name") String name) {
        HotelSeasons hotelSeasons = hotelSeasonsRepo.findBySeason( name );
        hotelSeasonsRepo.delete( hotelSeasons );
        return ResponseEntity.ok().body("deleted!");
    }

    @PostMapping("/manager/emailtoemployees/{hotelid}")
    public void sendemailtoemployees(@PathVariable("hotelid") Long hotelid, @Validated @RequestBody Mail mail) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kadahotelchain@gmail.com", "KADAhotel123");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("kadahotelchain@gmail.com", false));

        List<Address> addressList = new ArrayList<>();
        List<Employee> employeeList = employeeRepo.findByHotelid(hotelid);
        for (Employee employee : employeeList)
            addressList.add( new InternetAddress(employee.getEmail()) );

        msg.addRecipients( RecipientType.TO, addressList.toArray(new Address[addressList.size()]) );
        //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tfiosecmtv@gmail.com"));
        msg.setSubject(mail.title);
        msg.setContent(mail.message, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mail.message, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

    private void sendmail(String title, Integer price, String message, Long hotelid) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kadahotelchain@gmail.com", "KADAhotel123");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("kadahotelchain@gmail.com", false));

        List<Address> addressList = new ArrayList<>();
        List<BookingHistory> bhlist = bookingHistoryRepo.findByHotelid(hotelid);
        Set<Guest> guestList = new HashSet<>();
        for(BookingHistory bookingHistory : bhlist) {
            Guest guest = userRepo.findByUserId( bookingHistory.getGuestid() );
            guestList.add( guest );
        }
        List<Employee> employeeList = employeeRepo.findByHotelid(hotelid);
        for (Guest guest : guestList) {
            if(guest.getPrice() >= price)
                addressList.add( new InternetAddress(guest.getEmail()) );
        }
        for (Employee employee : employeeList)
            addressList.add( new InternetAddress(employee.getEmail()) );




        msg.addRecipients( RecipientType.TO, addressList.toArray(new Address[addressList.size()]) );
        //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tfiosecmtv@gmail.com"));
        msg.setSubject(title);
        msg.setContent(message, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

    //    @GetMapping("/manager/getscheduleforall/{hotel_id}")
    @GetMapping("/manager/getscheduleforall/{hotel_id}")
    public ResponseEntity getScheduleForAll(@PathVariable(value = "hotel_id") Long hotel_id) throws JsonProcessingException {
        List<Schedule> schedules = scheduleRepo.findByHotelid(hotel_id);
        List<ScheduleInfo> empSchMap = new ArrayList<>();
        for( Schedule s : schedules )
        {
            // new
            DateFormat dfDate = new SimpleDateFormat("yyyy-mm-dd");
            DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
            //
            Employee emp = employeeRepo.findByHotelidAndEmployeeid(hotel_id,s.getEmployeeid());
            ScheduleInfo info = new ScheduleInfo(s.getEmployeeid(), emp.getFirst_name(), emp.getLast_name(), emp.getJob_title(), emp.paymentperhour, dfDate.format(s.getDate()), dfTime.format(s.getStarttime()), dfTime.format(s.getEndtime()));
            empSchMap.add(info);
        }
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return new ResponseEntity(ow.writeValueAsString(empSchMap), HttpStatus.OK);
    }

    //    @PutMapping("/manager/changepayroll/{hotel_id}/{emp_id}/{new_pay}")
    @PutMapping("/manager/changepayroll/{hotel_id}/{emp_id}/{new_pay}")
    public ResponseEntity<Employee> changePayroll(@PathVariable(value = "hotel_id") Long hotel_id, @PathVariable(value = "emp_id") Long emp_id, @PathVariable(value = "new_pay") int new_pay) {
        Employee employee = employeeRepo.findByHotelidAndEmployeeid(hotel_id, emp_id);
        employee.setPayment_per_hour(new_pay);
        employeeRepo.save( employee );
        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/manager/schedule/{hotel_id}/{emp_id}/{date}")
    public String deleteWorkDay(@PathVariable(value = "hotel_id") Long hotel_id, @PathVariable(value = "emp_id") Long emp_id, @PathVariable(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date)
    {
        Schedule schedule = scheduleRepo.findByHotelidAndEmployeeidAndDate(hotel_id, emp_id, date);
        scheduleRepo.delete(schedule);
        return "deleted";
    }

    @DeleteMapping("/deletebooking")
    public String deletebooking(@Validated @RequestBody BookingHistory bh) {
        List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByBookingid( bh.getBookingid() );
        for(OccupationHistory occupationHistory : occupationHistoryList) {
            occupationHistoryRepo.delete( occupationHistory );
        }
        List<BookingHistory> bookingHistoryList = bookingHistoryRepo.findByBookingid( bh.getBookingid() );
        for(BookingHistory bookingHistory : bookingHistoryList) {
            bookingHistory.setStatus( "canceled" );
            bookingHistoryRepo.save( bookingHistory );
        }
        return "canceled";
    }

    @PutMapping("/manager/scheduleStart/{hotel_id}/{emp_id}/{date}/{time}")
    public ResponseEntity<ScheduleInfo> changeStartTime(@PathVariable(value = "hotel_id") Long hotel_id, @PathVariable(value = "emp_id") Long emp_id,
                                                        @PathVariable(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                                        @PathVariable(value = "time") String time) throws ParseException {

        DateFormat dfDate = new SimpleDateFormat("yyyy-mm-dd");
        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
        Timestamp timeStamp = new Timestamp((dfTime.parse(time)).getTime());


        Employee emp = employeeRepo.findByHotelidAndEmployeeid(hotel_id, emp_id);
        Schedule schedule = scheduleRepo.findByHotelidAndEmployeeidAndDate(hotel_id, emp_id, date);


        ScheduleInfo info = new ScheduleInfo(hotel_id, emp.getFirst_name(), emp.getLast_name(), emp.getJob_title(),
                emp.getPayment_per_hour(), dfDate.format(date), dfTime.format(timeStamp), dfTime.format(schedule.getEndtime()));


        schedule.setStarttime(timeStamp);
        scheduleRepo.save(schedule);
        return ResponseEntity.ok().body(info);
    }


    @PutMapping("/manager/scheduleEnd/{hotel_id}/{emp_id}/{date}/{time}")
    public ResponseEntity<ScheduleInfo> changeEndTime(@PathVariable(value = "hotel_id") Long hotel_id, @PathVariable(value = "emp_id") Long emp_id,
                                                      @PathVariable(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                                      @PathVariable(value = "time") String time) throws ParseException {

        DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
        Timestamp timeStamp = new Timestamp((dfTime.parse(time)).getTime());


        Employee emp = employeeRepo.findByHotelidAndEmployeeid(hotel_id, emp_id);
        Schedule schedule = scheduleRepo.findByHotelidAndEmployeeidAndDate(hotel_id, emp_id, date);

        ScheduleInfo info = new ScheduleInfo(hotel_id, emp.getFirst_name(), emp.getLast_name(), emp.getJob_title(),
                emp.getPayment_per_hour(), dfDate.format(date), dfTime.format(schedule.getStarttime()), dfTime.format(timeStamp));


        schedule.setStarttime(timeStamp);
        scheduleRepo.save(schedule);
        return ResponseEntity.ok().body(info);
    }


    @PostMapping("/manager/addschedule/{hotel_id}/{emp_id}/{date}/{starttime}/{endtime}")
    public ResponseEntity<ScheduleInfo> addSchedule(@PathVariable(value = "hotel_id") Long hotel_id, @PathVariable(value = "emp_id") Long emp_id,
                                                    @PathVariable(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                                    @PathVariable(value = "starttime") String starttime,
                                                    @PathVariable(value = "endtime") String endtime) throws ParseException {
        DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");

        Timestamp startStamp = new Timestamp((dfTime.parse(starttime)).getTime());
        Timestamp endStamp = new Timestamp((dfTime.parse(endtime)).getTime());

        Schedule schedule = new Schedule();

        schedule.setHotelid(hotel_id);
        schedule.setEmployeeid(emp_id);
        schedule.setDate(date);
        schedule.setStarttime(startStamp);
        schedule.setEndtime(endStamp);
        scheduleRepo.save(schedule);

        Employee emp = employeeRepo.findByHotelidAndEmployeeid(hotel_id, emp_id);

        ScheduleInfo info = new ScheduleInfo(hotel_id, emp.getFirst_name(), emp.getLast_name(), emp.getJob_title(),
                emp.getPayment_per_hour(), dfDate.format(date), starttime, endtime);

        return ResponseEntity.ok().body(info);
    }


}
