package kada.project.hotels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
class ListFeatures<HotelFeatures> implements List<HotelFeatures> {
    @JsonProperty
    List<HotelFeatures> list;

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
    public Iterator<HotelFeatures> iterator() {
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
    public boolean add(HotelFeatures hotelFeatures) {
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
    public boolean addAll(Collection<? extends HotelFeatures> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends HotelFeatures> c) {
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
    public HotelFeatures get(int index) {
        return null;
    }

    @Override
    public HotelFeatures set(int index, HotelFeatures element) {
        return null;
    }

    @Override
    public void add(int index, HotelFeatures element) {

    }

    @Override
    public HotelFeatures remove(int index) {
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
    public ListIterator<HotelFeatures> listIterator() {
        return null;
    }

    @Override
    public ListIterator<HotelFeatures> listIterator(int index) {
        return null;
    }

    @Override
    public List<HotelFeatures> subList(int fromIndex, int toIndex) {
        return null;
    }
}
