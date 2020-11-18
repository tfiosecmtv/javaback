package kada.project.Employee;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

class ScheduleId implements Serializable {
    Long hotelid;
    Long employeeid;
    Date date;

    public ScheduleId(Long hotelid, Long employeeid, Date date) {
        this.hotelid = hotelid;
        this.employeeid = employeeid;
        this.date = date;
    }
    public ScheduleId() {}
    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public Long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Long employeeid) {
        this.employeeid = employeeid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

@Entity
@IdClass( ScheduleId.class )
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "employee_id")
    Long employeeid;
    @Id
    @Column(name = "date")
    Date date;
    @Column(name = "start_time")
    Timestamp starttime;
    @Column(name = "end_time")
    Timestamp endtime;

    public Long getHotelid() {
        return hotelid;
    }

    public void setHotelid(Long hotelid) {
        this.hotelid = hotelid;
    }

    public Long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Long employeeid) {
        this.employeeid = employeeid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
}
