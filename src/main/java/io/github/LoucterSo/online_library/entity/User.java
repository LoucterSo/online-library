package io.github.LoucterSo.online_library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter @Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NaturalId
    @EqualsAndHashCode.Include
    private String email;

    @NotNull
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime registrationTime = LocalDateTime.now();

    public User(String email) {
        this.email = email;
    }
}
