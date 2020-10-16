package kada.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GuestREST {

    @Autowired
    private UserRepo userRepo;

    //get guests
    @GetMapping("/guests")
    public List<Guest> getAllGuests() {
        return this.userRepo.findAll();
    }

    //get guests by email
    @GetMapping("/guests/{userId}")
    public ResponseEntity<Guest> getGuestByEmail(@PathVariable(value = "userId") Long userId) {
        Guest guest = userRepo.findById( userId ).orElseThrow();
        return ResponseEntity.ok().body(guest);

    }

    //signup
    @PostMapping("/guests")
    public Guest signup(@Validated @RequestBody Guest guest) {
        return userRepo.save(guest);
    }
    //update

    @PutMapping("/guests/{userId}")
    public ResponseEntity<Guest> updateguest(@PathVariable(value = "userId") Long userId,
                                                   @Validated @RequestBody Guest guestDets)  {
        Guest guest = userRepo.findById(userId)
                .orElseThrow();
        guest.setLastName(guestDets.getLastName());
        guest.setFirstName(guestDets.getFirstName());
        guest.setUserId(guestDets.getUserId());
        guest.setDocumentId(guestDets.getDocumentId());
        guest.setDocumentType(guestDets.getDocumentType());
        guest.setHome(guestDets.getHome());
        guest.setMobile(guestDets.getMobile());
        guest.setAddress(guestDets.getAddress());
        guest.setEmail(guestDets.getEmail());
        guest.setPassword(guestDets.getPassword());
        final Guest updatedGuest = userRepo.save(guest);
        return ResponseEntity.ok(updatedGuest);
    }

    //delete

    @DeleteMapping("/guests/{userId}")
    public Map<String, Boolean> deleteGuest(@PathVariable(value = "userId") Long userId)
             {
        Guest guest = userRepo.findById(userId)
                .orElseThrow();

        userRepo.delete(guest);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
