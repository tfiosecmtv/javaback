package kada.project.hotels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class ServicesId implements Serializable {
    Long hotelid;
    String service;

    public ServicesId(Long hotel_id, String service_name) {
        this.hotelid = hotel_id;
        this.service = service_name;
    }

    public ServicesId() {
    }


    public Long getHotel_id() {
        return hotelid;
    }
    public void setHotel_id(Long hotel_id) {
        this.hotelid = hotel_id;
    }

    public String getService_name() {
        return service;
    }
    public void setService_name(String service_name) {
        this.service = service_name;
    }

}
@Entity
@IdClass(ServicesId.class)
@Table(name = "services")
public class HotelServices {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }
    @Id
    @Column(name = "service")
    String service;
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
    @Column(name = "price")
    int price;
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}

