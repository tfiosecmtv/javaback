package kada.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class GuestREST {

    @Autowired
    private UserRepo userRepo;

    //get guests
    @GetMapping("/guests")
    public List<Guest> getAllGuests() {
        return this.userRepo.findAll();
    }

    //get guests by email
    @GetMapping("/guests/{username}")
    public ResponseEntity<Guest> getGuestByEmail(@PathVariable(value = "username") Long username) {
        Guest guest = userRepo.findById( username ).orElseThrow();
        return ResponseEntity.ok().body(guest);

    }

    //signup
    @PostMapping("/guests")
    public Guest signup(@Validated @RequestBody Guest guest) {
        return userRepo.save(guest);
    }
    //update

    @PutMapping("/guests/{username}")
    public ResponseEntity<Guest> updateguest(@PathVariable(value = "username") Long username,
                                                   @Validated @RequestBody Guest guestDets)  {
        Guest guest = userRepo.findById(username)
                .orElseThrow();
        guest.setLastName(guestDets.getLastName());
        guest.setFirstName(guestDets.getFirstName());
        guest.setUsername(guestDets.getUsername());
        final Guest updatedGuest = userRepo.save(guest);
        return ResponseEntity.ok(updatedGuest);
    }

    //delete

    @DeleteMapping("/guests/{username}")
    public Map<String, Boolean> deleteGuest(@PathVariable(value = "username") Long username)
             {
        Guest guest = userRepo.findById(username)
                .orElseThrow();

        userRepo.delete(guest);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
