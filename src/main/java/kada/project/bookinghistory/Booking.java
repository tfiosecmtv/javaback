package kada.project.bookinghistory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.*;
public class Booking {
    List<BookingHistory> bh;
    List<GuestUsesService> list;

    public Booking(List<BookingHistory> bh, List<GuestUsesService> list) {
        this.bh = bh;
        this.list = list;
    }

    String getJson() throws JsonProcessingException {
        Map<String, Object> theMap = new LinkedHashMap<>();
        theMap.put("booking", bh);
        theMap.put("services", list);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(theMap);
    }
}
