package kada.project.hotels;

import javax.persistence.*;
import java.io.Serializable;

class CategoryId implements Serializable {
    Long hotelid;
    String category;

    public CategoryId(Long hotelid, String category) {
        this.hotelid = hotelid;
        this.category = category;
    }
    public CategoryId() {}

    public Long getHotel_id() {
        return hotelid;
    }
    public void setHotel_id(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String name) {
        this.category = name;
    }
}

@Entity
@IdClass( CategoryId.class )
@Table(name = "hotel_discounts")
public class HotelDiscounts {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "category")
    String category;
    @Column(name = "discount")
    Integer discount;

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}


