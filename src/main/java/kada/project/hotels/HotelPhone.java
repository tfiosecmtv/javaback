package kada.project.hotels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


class PhoneId implements Serializable {

    Long hotelid;
    String name;

    public PhoneId(Long hotel_id, String phone_num) {
        this.hotelid = hotel_id;
        this.name = phone_num;
    }
    public PhoneId() {}

    public Long getHotel_id() {
        return hotelid;
    }
    public void setHotel_id(Long hotel_id) {
        this.hotelid = hotel_id;
    }

    public String getPhone_num() {
        return name;
    }
    public void setPhone_num(String phone_num) {
        this.name = phone_num;
    }
}
@Entity
@IdClass( PhoneId.class )
@Table(name = "hotel_phone_nums")
class HotelPhone {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "phone_num")
    String name;

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getPhone_num() {
        return name;
    }

    public void setPhone_num(String phone_num) {
        this.name = phone_num;
    }
}

