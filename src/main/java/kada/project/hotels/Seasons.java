package kada.project.hotels;
import javax.persistence.*;
import java.util.*;
@Entity
@Table(name = "season")
public class Seasons {
    String name;
    Date start_date;
    Date end_date;

    public Seasons(String name, Date start_date, Date end_date) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Seasons() {}

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "start_date")
    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    @Column(name = "end_date")
    public Date getEnd_date() {
        return end_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
