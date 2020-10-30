package kada.project.hotels;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
public class HotelFilter {

    @JsonProperty("city")
    String city;
    @JsonProperty("start_date")
    Date startdate;
    @JsonProperty("due_date")
    Date duedate;

    public HotelFilter(String city, Date startdate, Date duedate) {
        this.city = city;
        this.startdate = startdate;
        this.duedate = duedate;
    }
    public HotelFilter() {}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }
}
