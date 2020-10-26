package kada.project.hotels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class SeasonsId implements Serializable {
    Long hotelid;
    String season;

    public SeasonsId(Long hotel_id, String season_name) {
        this.hotelid = hotel_id;
        this.season = season_name;
    }
    public SeasonsId() {}

    public Long getHotel_id() {
        return hotelid;
    }
    public void setHotel_id(Long hotel_id) {
        this.hotelid = hotel_id;
    }
    public String getSeason_name() {
        return season;
    }
    public void setSeason_name(String season_name) {
        this.season = season_name;
    }

}
@Entity
@IdClass(SeasonsId.class)
@Table(name = "hotel_has_season")
class HotelSeasons {

    @Id
    @Column(name = "hotel_id")
    Long hotelid;

    @Id
    @Column(name = "season_name")
    String season;

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Column(name = "add_price")
    int add_price;
    public int getAdd_price() {
        return add_price;
    }
    public void setAdd_price(int add_price) {
        this.add_price = add_price;
    }
}


