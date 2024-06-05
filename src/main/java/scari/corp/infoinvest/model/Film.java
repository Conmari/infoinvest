package scari.corp.infoinvest.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "test")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String film_name, genre, full_text;
    private String filename;
    private String film;
    private LocalDateTime creationData = LocalDateTime.now();

    public Film() {
        // пустой конструктор
    }

    public Film(String film_name, String genre, String full_text ) {
        this.film_name = film_name;
        this.genre = genre;
        this.full_text = full_text;
    }
}