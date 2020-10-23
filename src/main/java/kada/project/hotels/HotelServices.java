package kada.project.hotels;

import javax.persistence.*;
import java.io.Serializable;

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
class HotelServices {
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