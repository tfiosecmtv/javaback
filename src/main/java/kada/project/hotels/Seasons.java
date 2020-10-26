package kada.project.hotels;
import javax.persistence.*;

@Entity
@Table(name = "seasons")
public class Seasons {
    String name;
    String start_date;
    String end_date;

    public Seasons(String name, String start_date, String end_date) {
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
    public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @Column(name = "end_date")
    public String getEnd_date() {
        return end_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
