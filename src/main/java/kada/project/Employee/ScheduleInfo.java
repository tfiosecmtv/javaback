package kada.project.Employee;

import java.sql.Timestamp;
import java.util.Date;

public class ScheduleInfo {
    public Long employee_id;
    public String firstname;
    public String lastname;
    public String jobtitle;
    public int paymentperhour;
    public Date date;
    public Timestamp starttime;
    public Timestamp endtime;

    public ScheduleInfo(Long employee_id, String firstname, String lastname, String jobtitle, int paymentperhour, Date date, Timestamp starttime, Timestamp endtime) {
        this.employee_id = employee_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobtitle = jobtitle;
        this.paymentperhour = paymentperhour;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
    }
}
