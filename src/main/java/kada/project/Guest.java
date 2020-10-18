package kada.project;

import javax.persistence.*;

@Entity
@Table(name = "guests")
public class Guest {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String home;
    private String mobile;
    private String documentType;
    private String documentId;
    private String address;


    public Guest(Long userId, String firstName, String lastName, String email, String password, String home, String mobile, String documentType, String documentId, String address) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.home = home;
        this.mobile = mobile;
        this.documentType = documentType;
        this.documentId = documentId;
        this.address = address;
    }

    public Guest() {
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long username) {
        this.userId = username;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "home")
    public String getHome() {
        return home;
    }
    public void setHome(String home) {
        this.home = home;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "documentType")
    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Column(name = "documentId")
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Guest [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", home=" + home + ", mobile=" + mobile + ", documentId=" + documentId + ", documentType=" + documentType + ", address=" + address +", email=" + email + ", password=" + password + "]";
    }

}