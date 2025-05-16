package io.github.LoucterSo.online_library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NaturalId
    @EqualsAndHashCode.Include
    private String name;

    @NotNull
    @NaturalId
    @EqualsAndHashCode.Include
    private LocalDate birthDate;

    @NotNull
    @NaturalId
    @EqualsAndHashCode.Include
    private String country;

    @Column(columnDefinition = "text")
    private String biography;

    public Author(String name, LocalDate birthDate, String country) {
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
    }
}
