package kada.project.hotels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonsRepo extends JpaRepository<Seasons, String> {
    Seasons findSeasonsByName(String name);

}
