package kada.project.hotels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kada.project.room.RoomType;
import java.util.*;
import kada.project.room.Room;
import kada.project.room.RoomTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;

class Result {
    public HotelEntity hotelEntity;
    public List<RoomTypeInfo> roomTypeInfoList;

    Result(HotelEntity hotelEntity, List<RoomTypeInfo> roomTypeInfoList) {
        this.hotelEntity = hotelEntity;
        this.roomTypeInfoList = roomTypeInfoList;
    }

}



public class HotelFilterResult {
    HotelEntity hotelEntity;
    List<RoomTypeInfo> roomTypeInfoList;
//    HashMap<String, List<Room>> hashmap;

    public HotelFilterResult(HotelEntity hotelEntity) {
        this.hotelEntity = hotelEntity;
//        hashmap = new HashMap<String, List<Room>>();
        this.roomTypeInfoList = new ArrayList<RoomTypeInfo>();
    }
}

class RoomTypeInfo {
    public String type;
    public Float size;
    public Integer capacity;
    public Integer howmanyavailable;
    public Integer price;
    List<Integer> roomnumbers;

    RoomTypeInfo(String type, Float size, Integer capacity, Integer howmanyavailable, Integer price, List<Integer> roomnumbers) {
        this.type = type;
        this.size = size;
        this.capacity = capacity;
        this.howmanyavailable = howmanyavailable;
        this.price = price;
        this.roomnumbers = roomnumbers;
    }

    RoomTypeInfo getRoomTypeInfo() {
        return this;
    }
}
