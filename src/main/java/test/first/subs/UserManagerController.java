package test.first.subs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import test.first.subs.dto.UserRequest;
import test.first.subs.jpa.User;
import test.first.subs.jpa.UserRepository;

import java.util.Optional;

@RestController
public class UserManagerController {
    private final UserRepository userRepository;

    public UserManagerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/users")
    public @ResponseBody ResponseEntity<Void> addUser(@RequestBody UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setName(userRequest.name());
        user.setSurname(userRequest.surname());
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/users/{id}")
    public @ResponseBody ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/users/{id}")
    public @ResponseBody ResponseEntity<User> updateUser(@PathVariable("id") Long id,
                                                         @RequestBody UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setName(userRequest.name());
        user.setSurname(userRequest.surname());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/users/{id}")
    public @ResponseBody ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
