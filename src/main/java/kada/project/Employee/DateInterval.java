package kada.project.Employee;

import java.util.Date;

public class DateInterval {
    private Date start, end;

    public DateInterval(Date startDate, Date endDate) {
        start = startDate;
        end = endDate;
    }

    public DateInterval(DateInterval interval) {
        this.start = interval.start;
        this.end = interval.end;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }
}
