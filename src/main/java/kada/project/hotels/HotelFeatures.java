package kada.project.hotels;

import javax.persistence.*;
import java.io.Serializable;

class FeaturesId implements Serializable {
    Long hotelid;
    String name;

    public FeaturesId(Long hotelid, String name) {
        this.hotelid = hotelid;
        this.name = name;
    }
    public FeaturesId() {}

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
@IdClass( FeaturesId.class )
@Table(name = "hotel_features")
class HotelFeatures {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "feature")
    String name;


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
}

