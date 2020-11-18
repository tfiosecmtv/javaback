package kada.project.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Filter {

    private List<DateInterval> distinctIntervals;
    public Filter() {
        distinctIntervals = new ArrayList<DateInterval>();
    }
    public boolean isDistinct(DateInterval interval) {
        boolean result = false;
        if(distinctIntervals != null && distinctIntervals.size() > 0) {
            for(int i = 0; i < distinctIntervals.size(); i++) {
                if(overlaps(interval, distinctIntervals.get(i))) {
                    result = true;
                }
            }
        }
        distinctIntervals.add(interval);
        return result;
    }

    public String findOverlap(List<DateInterval> intervals) {
        for(DateInterval element:intervals) {
            if(isDistinct(element)) {
                return "DUPLICATE:: "+
                        element.getStart().toString() +" — "+element.getEnd().toString();
            }
        }
        return "It’s a clean list";
    }

    public boolean overlaps(DateInterval left, DateInterval right) {
        boolean result = true;
        if(left.getEnd().before(right.getStart())
                && left.getEnd().before(right.getEnd())){
            return false;
        }
        if(left.getStart().after(right.getStart())
                && left.getStart().after(right.getEnd())) {
            return false;
        }
        return result;
    }
}



