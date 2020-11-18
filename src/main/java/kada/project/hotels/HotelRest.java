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
import kada.project.Employee.*;

import javax.persistence.criteria.CriteriaBuilder;
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

    int price(HotelFilter hotelFilter, String roomtypename, Long hotelid) {
        int price = 0;
        Date start = hotelFilter.getStartdate();
        Date end = hotelFilter.getDuedate();
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
//        while (cStart.before( cEnd )) {
//            for(HotelSeasons hotelseason : hotelSeasons ) {
//                Seasons season = seasonsRepo.findSeasonsByName( hotelseason.getSeason() );
//                if(cStart.getTime().getTime() >= season.getStart_date().getTime() &&
//                        cStart.getTime().getTime() <= season.getEnd_date().getTime()) {
//                    price += hotelseason.getAdd_price() ;
//                    break;
//                }
//            }
//            cStart.add(Calendar.DAY_OF_MONTH, 1);
//        }
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

    @PostMapping("/hotels/filter")
    public ResponseEntity getHotelInfoByCity(@RequestBody HotelFilter hotelFilter)  throws JsonProcessingException{
        List<HotelEntity> hotelEntityList = hotelEntityRepo.findByCity( hotelFilter.getCity() );
        List<HotelFilterResult> hotelFilterResultList = new ArrayList<>();

        for(HotelEntity hotelEntity : hotelEntityList) {
            HotelFilterResult hotelFilterResult = new HotelFilterResult(hotelEntity);
            List<RoomType> roomTypeList = roomTypeRepo.findByHotelid(hotelEntity.getHotel_id());
            for(RoomType roomType : roomTypeList) {

                List<Room> roomList = roomRepo.findByHotelidAndRoomtype( hotelEntity.getHotel_id(), roomType.getName() );
                List<Integer> roomnumberlist = new ArrayList<>();
                for(Room room : roomList) {

                    List<OccupationHistory> occupationHistoryList = occupationHistoryRepo.findByHotelidAndRoomnumber(  hotelEntity.getHotel_id(), room.getRoomnumber() );
                    List<DateInterval> dateIntervalList = new ArrayList<>();
                    dateIntervalList.add( new DateInterval( hotelFilter.getStartdate(), hotelFilter.getDuedate() ) );
                    for(OccupationHistory occupationHistory : occupationHistoryList) {
                        dateIntervalList.add( new DateInterval(occupationHistory.getFrom_date(), occupationHistory.getTo_date()) );
                    }
                    Filter filter = new Filter();
                    String result = filter.findOverlap( dateIntervalList );
                    if(result.equals( "It’s a clean list" )) {
                        roomnumberlist.add( room.getRoomnumber() );
                    }

                }
                if(roomnumberlist.size() != 0) {
                    int price = price( hotelFilter, roomType.getName(), hotelEntity.getHotel_id() );
                    RoomTypeInfo roomTypeInfo = new RoomTypeInfo( roomType.getName(), roomType.getSize(), roomType.getCapacity(), roomnumberlist.size(), price, roomnumberlist );
                    hotelFilterResult.roomTypeInfoList.add(roomTypeInfo);
                }

            }
            hotelFilterResultList.add( hotelFilterResult );

        }

        Map<String, Object> theMap = new LinkedHashMap<>();
        for (HotelFilterResult hotelFilterResult : hotelFilterResultList) {
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
