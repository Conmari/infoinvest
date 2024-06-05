package scari.corp.infoinvest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.validation.BindingResult;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.ui.Model;
import jakarta.validation.Valid;

import scari.corp.infoinvest.model.User;
import scari.corp.infoinvest.repository.UserRepository;
import scari.corp.infoinvest.config.*;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String ViewActualInformationFilm(Model model) {
        Iterable<User> Film = userRepository.findAll();
        model.addAttribute("Film", Film);
        return "index";
    }
    
    @GetMapping("/update/{id}") /*Обновление(Редактирование фильма)*/
    public String showUpdateFormFilm(@PathVariable("id") long id, Model model) {
        User film = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("film", film);
        return "up";
    }
    @GetMapping("/reg")
    public String addFilm(Model model) {
        return "/adduser";
    }

    @PostMapping("/reg")
    public String postAddFilm(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String number,
            Model model
    ) throws IOException {
        // String HPassword = passwordEncoder.encode(password);
        User post = new User(name, password, email, number);
        userRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateFilm(@PathVariable("id") long id, @Valid User film,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            film.setId(id);
            return "film/up";
        }

        userRepository.save(film);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteFilm(@PathVariable("id") long id, Model model) {
        User film = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
                userRepository.delete(film);
        return "redirect:/";
    }
}

