package kada.project.hotels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.mail.iap.Response;
import kada.project.bookinghistory.BookingHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/hotels") // /api/hotels
    public List<HotelEntity> getAllHotels() {
        return this.hotelEntityRepo.findAll();
    }

    @PostMapping("/hotels")
    public HotelEntity addhotel(@Validated @RequestBody HotelEntity hotelEntity) {
        return hotelEntityRepo.save(hotelEntity);
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
