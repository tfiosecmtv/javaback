package kada.project.hotels;

import javax.persistence.*;

@Entity
@Table(name = "hotel")
public class HotelEntity {
    Long hotelid;
    String name;
    String address;
    String city;
    String country;

    public HotelEntity(Long hotel_id, String name, String address, String city, String country) {
        this.hotelid = hotel_id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public HotelEntity() { }


    @Id
    @Column(name = "hotel_id")
    public Long getHotel_id() {
        return hotelid;
    }
    public void setHotel_id(Long hotel_id) {
        this.hotelid = hotel_id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
