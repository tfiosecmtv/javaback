package kada.project.room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import kada.project.hotels.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class RoomTypeId implements Serializable {
    Long hotelid;
    String name;

    public RoomTypeId(Long hotelid, String name) {
        this.hotelid = hotelid;
        this.name = name;
    }
    public RoomTypeId() {}

    public Long getHotel_id() {
        return hotelid;
    }
    public void setHotel_id(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

@Entity
@IdClass( RoomTypeId.class )
@Table(name = "room_type")
public class RoomType {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "name")
    String name;
    @Column(name = "base_price_mon")
    Integer base_price_mon;
    @Column(name = "base_price_tue")
    Integer base_price_tue;
    @Column(name = "base_price_wed")
    Integer base_price_wed;
    @Column(name = "base_price_thu")
    Integer base_price_thu;
    @Column(name = "base_price_fri")
    Integer base_price_fri;
    @Column(name = "base_price_sat")
    Integer base_price_sat;
    @Column(name = "base_price_sun")
    Integer base_price_sun;
    @Column(name = "capacity")
    Integer capacity;
    @Column(name = "size")
    Float size;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBase_price_mon() {
        return base_price_mon;
    }

    public void setBase_price_mon(Integer base_price_mon) {
        this.base_price_mon = base_price_mon;
    }

    public Integer getBase_price_tue() {
        return base_price_tue;
    }

    public void setBase_price_tue(Integer base_price_tue) {
        this.base_price_tue = base_price_tue;
    }

    public Integer getBase_price_wed() {
        return base_price_wed;
    }

    public void setBase_price_wed(Integer base_price_wed) {
        this.base_price_wed = base_price_wed;
    }

    public Integer getBase_price_thu() {
        return base_price_thu;
    }

    public void setBase_price_thu(Integer base_price_thu) {
        this.base_price_thu = base_price_thu;
    }

    public Integer getBase_price_fri() {
        return base_price_fri;
    }

    public void setBase_price_fri(Integer base_price_fri) {
        this.base_price_fri = base_price_fri;
    }

    public Integer getBase_price_sat() {
        return base_price_sat;
    }

    public void setBase_price_sat(Integer base_price_sat) {
        this.base_price_sat = base_price_sat;
    }

    public Integer getBase_price_sun() {
        return base_price_sun;
    }

    public void setBase_price_sun(Integer base_price_sun) {
        this.base_price_sun = base_price_sun;
    }

}

class RoomTypeGeneral {
    RoomType roomType;
    List<RoomTypeFeatures> roomTypeFeatures;

    public RoomTypeGeneral(List<RoomTypeFeatures> roomTypeFeatures, RoomType roomType) {
        this.roomTypeFeatures = roomTypeFeatures;
        this.roomType = roomType;
    }

    String getJson() throws JsonProcessingException {
        Map<String, Object> theMap = new LinkedHashMap<>();
        theMap.put("room", roomType);
        theMap.put("features", roomTypeFeatures);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(theMap);
    }
}
