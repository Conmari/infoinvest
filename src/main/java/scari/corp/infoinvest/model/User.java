package scari.corp.infoinvest.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

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
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Поле должно быть заполнено")
    private String password;
    @NotBlank(message = "Поле должно быть заполнено")
    @Column(unique = true)
    private String email;
    @Column(name = "numbertel")
    @NotBlank(message = "Поле должно быть заполнено")
    private String number;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataRegistration;

    public User() {
        // пустой конструктор
    }

    public User(String username, String password, String email, String number ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.number = number;
    }

}




