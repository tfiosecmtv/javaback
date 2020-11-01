package kada.project.hotels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.mail.iap.Response;
import kada.project.bookinghistory.Booking;
import kada.project.bookinghistory.BookingHistory;
import kada.project.bookinghistory.BookingHistoryRepo;
import kada.project.room.Room;
import kada.project.room.RoomRepo;
import kada.project.room.RoomType;
import kada.project.room.RoomTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api") //localhost:8181/api/
public class HotelRest {
    @Autowired
    private  HotelEntityRepo hotelEntityRepo;
    @Autowired
    private  HotelFeaturesRepo hotelFeaturesRepo;
    @Autowired
    private  HotelPhoneRepo hotelPhoneRepo;
    @Autowired
    private  HotelSeasonsRepo hotelSeasonsRepo;
    @Autowired
    private HotelServicesRepo hotelServicesRepo;
    @Autowired
    private BookingHistoryRepo bookingHistoryRepo;
    @Autowired
    private RoomTypeRepo roomTypeRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private OccupationHistoryRepo occupationHistoryRepo;
    @Autowired
    private SeasonsRepo seasonsRepo;
    @GetMapping("/hotels") // /api/hotels
    public List<HotelEntity> getAllHotels() {
        return this.hotelEntityRepo.findAll();
    }

    @PostMapping("/hotels")
    public HotelEntity addhotel(@Validated @RequestBody HotelEntity hotelEntity) {
        return hotelEntityRepo.save(hotelEntity);
    }

    @PostMapping("/hotels/filter")
    public ResponseEntity getHotelInfoByCity(@RequestBody HotelFilter hotelFilter)  throws JsonProcessingException{
        List<HotelEntity> hotelEntityList = hotelEntityRepo.findByCity( hotelFilter.getCity() );
        List<Room> availableRoomList = new ArrayList<Room>();
        for(HotelEntity hotelEntity : hotelEntityList) {
            List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByHotelid( hotelEntity.getHotel_id());
            List<Room> roomList = roomRepo.findByHotelid( hotelEntity.getHotel_id() );
            List<Room> rooms = new ArrayList<Room>();
            for(Room room : roomList) {
                for(OccupationHistory occupationHistory : occupationHistoryList) {
                    if(occupationHistory.getRoom_number().compareTo( room.getRoomnumber() ) == 0) {
                        rooms.add( room );
                    }
                }
            }
            roomList.removeAll( rooms );
            availableRoomList.addAll( roomList );
            Calendar cStart = Calendar.getInstance();
            Calendar cEnd = Calendar.getInstance();
            cEnd.setTime( hotelFilter.getDuedate() );
            cEnd.add( Calendar.DAY_OF_MONTH,1 );

            for (OccupationHistory occupationHistory: occupationHistoryList) {
                cStart.setTime( hotelFilter.getStartdate() );
                Boolean overlap = false;
                while(cStart.before( cEnd )) {
                    cStart.set(Calendar.HOUR_OF_DAY, 0);
                    cStart.set(Calendar.HOUR, 0);
                    cStart.set(Calendar.MINUTE, 0);
                    cStart.set(Calendar.SECOND, 0);
                    cStart.set(Calendar.MILLISECOND, 0);
                    if (cStart.getTime().compareTo( occupationHistory.getFrom_date() ) >= 0 && cStart.getTime().compareTo( occupationHistory.getTo_date() ) <= 0)
                    {

                        overlap = true;
                        break;
                    }
                    // if current date is does not overlap with record, then we can move to next day
                else
                    {
                        cStart.add(Calendar.DAY_OF_MONTH, 1);
                    }
                }
                // when we finish iterating over dates, if there was not overlap we can add this room to available rooms
                if(overlap == false)
                {
                    availableRoomList.add(roomRepo.findByHotelidAndRoomnumber( hotelEntity.getHotel_id(), occupationHistory.getRoom_number() ));
                }

            }
            }
            HashMap<Long, HotelFilterResult> hashMap = new HashMap<Long, HotelFilterResult>();

            for(Room room : availableRoomList) {
                Long hotel_id = room.getHotelid();
                if(hashMap.containsKey( hotel_id )) {
                    if(hashMap.get( hotel_id ).hashmap.containsKey( room.getRoomtype() )) {
                        hashMap.get( hotel_id ).hashmap.get( room.getRoomtype() ).add( room );
                    } else {
                        hashMap.get(hotel_id).hashmap.put( room.getRoomtype(), new ArrayList<Room>());
                        hashMap.get( hotel_id ).hashmap.get( room.getRoomtype() ).add( room );
                    }
                } else {
                    System.out.println(hotel_id);
                        HotelEntity he = hotelEntityRepo.findById( hotel_id ).orElseThrow();
                        hashMap.put(hotel_id, new HotelFilterResult(he));
                        hashMap.get(hotel_id).hashmap.put( room.getRoomtype(), new ArrayList<Room>());
                        hashMap.get( hotel_id ).hashmap.get( room.getRoomtype() ).add( room );
                }

            }    //end of for loop

        System.out.println("quitted room loop");
        Date start = hotelFilter.getStartdate();
        Date end = hotelFilter.getDuedate();
        for(Long l : hashMap.keySet()) {
            for(String str : hashMap.get( l ).hashmap.keySet()) {
                RoomType roomType = roomTypeRepo.findByHotelidAndName( hashMap.get( l ).hotelEntity.getHotel_id(), str );
                Integer s = hashMap.get( l ).hashmap.get( str ).size();
                Integer price = 0;

                Calendar cStart = Calendar.getInstance();
                cStart.setTime( start );
                //cStart.add( Calendar.DAY_OF_MONTH,-1 );
                Calendar cEnd = Calendar.getInstance();

                cEnd.setTime( end );
                System.out.println(roomType.getName());
                while(cStart.before( cEnd )) {
                    System.out.println(price);
                    cStart.set(Calendar.HOUR_OF_DAY, 0);
                    cStart.set(Calendar.HOUR, 0);
                    cStart.set(Calendar.MINUTE, 0);
                    cStart.set(Calendar.SECOND, 0);
                    cStart.set(Calendar.MILLISECOND, 0);
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
                //cStart.add( Calendar.DAY_OF_MONTH,-1 );
                List<HotelSeasons> hotelSeasons = hotelSeasonsRepo.findByHotelid( hashMap.get( l ).hotelEntity.getHotel_id() );
                while (cStart.before( cEnd )) {
                    System.out.println(price);
                    cStart.set(Calendar.HOUR_OF_DAY, 0);
                    cStart.set(Calendar.HOUR, 0);
                    cStart.set(Calendar.MINUTE, 0);
                    cStart.set(Calendar.SECOND, 0);
                    cStart.set(Calendar.MILLISECOND, 0);
                    for(HotelSeasons hotelseason : hotelSeasons ) {
                        Seasons season = seasonsRepo.findSeasonsByName( hotelseason.getSeason() );
                        if(cStart.getTime().getTime() >= season.getStart_date().getTime() &&
                                cStart.getTime().getTime() <= season.getEnd_date().getTime()) {
                            price += hotelseason.getAdd_price() ;
                            break;
                        }
                    }
                    cStart.add(Calendar.DAY_OF_MONTH, 1);
                }
                hashMap.get( l ).roomTypeInfoList.add( new RoomTypeInfo( roomType.getName(), roomType.getSize(),roomType.getCapacity(), s, price ) );
            }
        }
        Map<String, Object> theMap = new LinkedHashMap<>();
        for (HotelFilterResult hotelFilterResult : hashMap.values()) {
            theMap.put(hotelFilterResult.hotelEntity.name, new Result( hotelFilterResult.hotelEntity, hotelFilterResult.roomTypeInfoList ));
        }
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return new ResponseEntity(ow.writeValueAsString(theMap), HttpStatus.OK);
        }


    @GetMapping("/hotels/{hotelid}")
    public ResponseEntity getHotelInfo(@PathVariable(value = "hotelid") Long hotelid) throws JsonProcessingException {
        HotelEntity hotelEntity = hotelEntityRepo.findById( hotelid ).orElseThrow();
        List<HotelFeatures> hotelFeatures = hotelFeaturesRepo.findByHotelid( hotelid );
        List<HotelPhone>  hotelPhones = hotelPhoneRepo.findByHotelid( hotelid );
        List<HotelSeasons> hotelSeasons = hotelSeasonsRepo.findByHotelid( hotelid );
        List<HotelServices> hotelServices = hotelServicesRepo.findByHotelid( hotelid );
        Hotel hotel = new Hotel(hotelEntity, hotelFeatures, hotelPhones, hotelSeasons, hotelServices);
        return new ResponseEntity(hotel.getJson(), HttpStatus.OK);
    }
    @GetMapping("/hotels/service/{hotelid}")
    public @ResponseBody List<HotelServices> getservice(@PathVariable(value = "hotelid") Long hotelid) {
        return hotelServicesRepo.findByHotelid( hotelid );
    }
    @GetMapping("/hotels/service")
    public @ResponseBody List<HotelServices> getserv() {
        return hotelServicesRepo.findAll();
    }
    @GetMapping("/hotels/feature/{hotelid}")
    public @ResponseBody List<HotelFeatures> getfeature(@PathVariable(value = "hotelid") Long hotelid) {
        return hotelFeaturesRepo.findByHotelid( hotelid );
    }
    @GetMapping("/hotels/season/{hotelid}")
    public @ResponseBody List<HotelSeasons> getseasons(@PathVariable(value = "hotelid") Long hotelid) {
        return hotelSeasonsRepo.findByHotelid( hotelid );
    }
    @GetMapping("/hotels/phone/{hotelid}")
    public @ResponseBody List<HotelPhone> getphone(@PathVariable(value = "hotelid") Long hotelid) {
        return hotelPhoneRepo.findByHotelid( hotelid );
    }


}
