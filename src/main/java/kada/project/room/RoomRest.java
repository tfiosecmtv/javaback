package kada.project.room;

import com.fasterxml.jackson.core.JsonProcessingException;
import kada.project.hotels.HotelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomRest {
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private  RoomTypeRepo roomTypeRepo;
    @Autowired
    private  RoomTypeFeaturesRepo roomTypeFeaturesRepo;
    @Autowired
    private  OccupationHistoryRepo occupationHistoryRepo;

    @GetMapping("/roomtypes")
    public List<RoomType> getAllRoomTypes() {
        return this.roomTypeRepo.findAll();
    }

    @GetMapping("/roomtypes/{type}/{hotelid}")
    public ResponseEntity getRoomType(@PathVariable(value = "type") String type, @PathVariable(value = "hotelid") Long hotelid) throws JsonProcessingException {
        RoomType roomType = roomTypeRepo.findByHotelidAndName( hotelid, type );
        List<RoomTypeFeatures> list = roomTypeFeaturesRepo.findByHotelidAndRoomtype( hotelid,type );
        RoomTypeGeneral gen = new RoomTypeGeneral( list, roomType );
        return new ResponseEntity(gen.getJson(), HttpStatus.OK );
    }

    @GetMapping("/roomtypes/{type}")
    public List<RoomType> filterRoomType(@PathVariable(value = "type") String type) throws JsonProcessingException {
        List<RoomType> roomType = roomTypeRepo.findByName( type );
        return  roomType;
    }
}
