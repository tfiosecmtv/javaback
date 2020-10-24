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

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
class ListSeasons<HotelSeasons> implements List<HotelSeasons> {

    @JsonProperty
    List<HotelSeasons> list;
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<HotelSeasons> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(HotelSeasons hotelSeasons) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends HotelSeasons> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends HotelSeasons> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public HotelSeasons get(int index) {
        return null;
    }

    @Override
    public HotelSeasons set(int index, HotelSeasons element) {
        return null;
    }

    @Override
    public void add(int index, HotelSeasons element) {

    }

    @Override
    public HotelSeasons remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<HotelSeasons> listIterator() {
        return null;
    }

    @Override
    public ListIterator<HotelSeasons> listIterator(int index) {
        return null;
    }

    @Override
    public List<HotelSeasons> subList(int fromIndex, int toIndex) {
        return null;
    }
}
