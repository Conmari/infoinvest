package scari.corp.infoinvest.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Поле должно быть заполнено")
    private String name;
    @NotBlank(message = "Поле должно быть заполнено")
    private String password;
    @NotBlank(message = "Поле должно быть заполнено")
    @Column(unique = true)
    private String email;
    @Column(name = "numbertel")
    @NotBlank(message = "Поле должно быть заполнено")
    private String number;

    @Column(name= "role")
    private String role = "user";
    private LocalDateTime dataRegistration = LocalDateTime.now();

    public User() {
        // пустой конструктор
    }

    public User(String name, String password, String email, String number ) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.number = number;
    }

}




