package controllers;


import com.sachin_s_k.shopping_cart.entities.User;
import com.sachin_s_k.shopping_cart.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
}
