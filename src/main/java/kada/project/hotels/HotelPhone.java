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

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
class ListPhones<HotelPhone> implements List<HotelPhone> {

    @JsonProperty
    List<HotelPhone> list;
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
    public Iterator<HotelPhone> iterator() {
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
    public boolean add(HotelPhone hotelPhone) {
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
    public boolean addAll(Collection<? extends HotelPhone> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends HotelPhone> c) {
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
    public HotelPhone get(int index) {
        return null;
    }

    @Override
    public HotelPhone set(int index, HotelPhone element) {
        return null;
    }

    @Override
    public void add(int index, HotelPhone element) {

    }

    @Override
    public HotelPhone remove(int index) {
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
    public ListIterator<HotelPhone> listIterator() {
        return null;
    }

    @Override
    public ListIterator<HotelPhone> listIterator(int index) {
        return null;
    }

    @Override
    public List<HotelPhone> subList(int fromIndex, int toIndex) {
        return null;
    }
}
