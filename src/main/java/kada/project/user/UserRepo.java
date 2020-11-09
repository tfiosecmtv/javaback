package kada.project.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Guest, Long> {
    Guest findByEmail(String email);
    List<Guest> findByFirstNameAndLastName(String first, String last);
}
