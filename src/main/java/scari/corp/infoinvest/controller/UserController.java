package scari.corp.infoinvest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import scari.corp.infoinvest.model.Role;
import scari.corp.infoinvest.model.User;
import scari.corp.infoinvest.repository.RoleRepository;
import scari.corp.infoinvest.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private final UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @GetMapping("/")
    public String ViewActualInformationUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Получаем имя пользователя из контекста безопасности
        auth.getDetails();
        Iterable<User> User = userRepository.findAll(Sort.by(Sort.Direction.DESC, "dataRegistration")); // на первом месте последний зарегистрированный пользователь
        model.addAttribute("User", User);       

        if (username != null && !username.isEmpty()) {
            model.addAttribute("username", username);
        }
        return "index";
    }

    @GetMapping("/update/{id}") 
    public String showUpdateFormUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "updateuser";
    }

    @GetMapping("/reg")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "adduser";
    }

    @PostMapping("/reg")
    public String addUser(@Valid User user, BindingResult result, Model model) {
       if (result.hasErrors()) {
           return "adduser";
       }
       // Шифрование пароля перед сохранением
       String encryptedPassword = passwordEncoder.encode(user.getPassword()); 
       user.setPassword(encryptedPassword);

       // Присвоение роли "ROLE_USER"
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singleton(userRole));
       
        userRepository.save(user);

       return "redirect:/";
   }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "/updateuser";
        }
        String encryptedPassword = passwordEncoder.encode(user.getPassword()); 
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
                userRepository.delete(user);
        return "redirect:/";
    }
}

