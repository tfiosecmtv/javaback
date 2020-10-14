package kada.project;

import javax.persistence.*;

@Entity
@Table(name = "guests")
public class Guest {

    private Long username;
    private String firstName;
    private String lastName;



    public Guest(String firstName, String lastName, Long username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public Guest() {
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUsername() {
        return username;
    }
    public void setUsername(Long username) {
        this.username = username;
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


    @Override
    public String toString() {
        return "Guest [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName +"]";
    }

}