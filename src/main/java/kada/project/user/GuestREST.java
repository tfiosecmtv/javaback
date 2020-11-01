package kada.project.user;

import com.sun.mail.iap.Response;
import kada.project.UserSession.UserService;
import kada.project.UserSession.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api")
public class GuestREST {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    //get guests
    @GetMapping("/guests")
    public List<Guest> getAllGuests() {
        return this.userRepo.findAll();
    }

//    //get guests by email
//    @GetMapping("/guests/{email}")
//    public ResponseEntity<Guest> getGuestByEmail(@PathVariable(value = "email") String email) {
//        Guest guest = userRepo.findByEmail( email );
//        return ResponseEntity.ok().body(guest);
//    }

//    @GetMapping("/guests/login/{email}")
//    public ResponseEntity<Guest> getGuestByEmail(@PathVariable(value = "email") String email) {
//        Guest guest = userRepo.findByEmail( email );
//        return ResponseEntity.ok().body(guest);
//    }

    @PostMapping("/login")
    public ResponseEntity<Guest> getGuestByEmail(@Validated @RequestBody Guest user) {
        Guest guest = userService.findByLoginAndPassword(user.getEmail(), user.getPassword());
        String token = jwtProvider.generateToken(guest.getEmail());
        guest.setToken( token );
        guest.setRole( "GUEST" );
        userRepo.save( guest );
        return ResponseEntity.ok().body(guest);
    }

    @GetMapping("/guests/{token}")
    public Guest getGuest(@PathVariable(value = "token") String token) {
        return this.userRepo.findByEmail(jwtProvider.getLoginFromToken( token ));
    }


    @PostMapping("/guests/logout/{token}")
    public ResponseEntity LogOut(@PathVariable(value = "token") String token) {
        String email = jwtProvider.getLoginFromToken( token );
        Guest guest = userRepo.findByEmail( email );
        guest.setRole( "" );
        guest.setToken( "" );
        userRepo.save( guest );
        return new ResponseEntity("logged out", HttpStatus.OK );
    }

    //signup
    @PostMapping("/signup")
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


