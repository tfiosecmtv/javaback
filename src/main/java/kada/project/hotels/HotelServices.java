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

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
class ListServices<HotelServices> implements List<HotelServices> {

    @JsonProperty
    List<HotelServices> list;
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
    public Iterator<HotelServices> iterator() {
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
    public boolean add(HotelServices hotelServices) {
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
    public boolean addAll(Collection<? extends HotelServices> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends HotelServices> c) {
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
    public HotelServices get(int index) {
        return null;
    }

    @Override
    public HotelServices set(int index, HotelServices element) {
        return null;
    }

    @Override
    public void add(int index, HotelServices element) {

    }

    @Override
    public HotelServices remove(int index) {
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
    public ListIterator<HotelServices> listIterator() {
        return null;
    }

    @Override
    public ListIterator<HotelServices> listIterator(int index) {
        return null;
    }

    @Override
    public List<HotelServices> subList(int fromIndex, int toIndex) {
        return null;
    }
}