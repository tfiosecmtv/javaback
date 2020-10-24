package kada.project.hotels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.*;
public class Hotel {
    HotelEntity hotelEntity;
    List<HotelFeatures> hotelFeatures;
    List<HotelPhone> hotelPhones;
    List<HotelSeasons> hotelSeasons;
    List<HotelServices> hotelServices;

    Hotel(HotelEntity hotelEntity, List<HotelFeatures> hotelFeatures, List<HotelPhone> hotelPhones, List<HotelSeasons> hotelSeasons, List<HotelServices> hotelServices) {
        this.hotelEntity = hotelEntity;
        this.hotelFeatures = hotelFeatures;
        this.hotelPhones = hotelPhones;
        this.hotelSeasons = hotelSeasons;
        this.hotelServices = hotelServices;
    }

    String getJson() throws JsonProcessingException {
        Map<String, Object> theMap = new LinkedHashMap<>();
        theMap.put("hotel", hotelEntity);
        theMap.put("features", hotelFeatures);
        theMap.put("phone", hotelPhones);
        theMap.put("seasons", hotelSeasons);
        theMap.put("services", hotelServices);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(theMap);
    }
}




