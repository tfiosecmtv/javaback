package kada.project.Employee;


import javax.persistence.*;
import java.io.Serializable;

class EmployeeId implements Serializable {
    Long hotelid;
    Long employeeid;

    public EmployeeId(Long hotelid, Long employeeid) {
        this.hotelid = hotelid;
        this.employeeid = employeeid;
    }
    public EmployeeId() {}
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
}
@Entity
@IdClass( EmployeeId.class )
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "hotel_id")
    Long hotelid;
    @Id
    @Column(name = "employee_id")
    Long employeeid;
    @Column(name = "job_title")
    String jobtitle;
    @Column(name = "first_name")
    String firstname;
    @Column(name = "last_name")
    String lastname;
    @Column(name = "payment_per_hour")
    Integer paymentperhour;
    @Column(name = "manager_id")
    Long managerid;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "token")
    String token;
    @Column(name = "role")
    String role;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getHotel_id() {
        return hotelid;
    }

    public void setHotel_id(Long hotelid) {
        this.hotelid = hotelid;
    }

    public Long getEmployee_id() {
        return employeeid;
    }

    public void setEmployee_id(Long employeeid) {
        this.employeeid = employeeid;
    }

    public String getJob_title() {
        return jobtitle;
    }

    public void setJob_title(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getFirst_name() {
        return firstname;
    }

    public void setFirst_name(String first_name) {
        this.firstname = first_name;
    }

    public String getLast_name() {
        return lastname;
    }

    public void setLast_name(String last_name) {
        this.lastname = last_name;
    }

    public Integer getPayment_per_hour() {
        return paymentperhour;
    }

    public void setPayment_per_hour(Integer paymentperhour) {
        this.paymentperhour = paymentperhour;
    }

    public Long getManager_id() {
        return managerid;
    }

    public void setManager_id(Long managerid) {
        this.managerid = managerid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
