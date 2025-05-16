package io.github.LoucterSo.online_library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1) @Max(5)
    private Integer rating;

    @NotNull
    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    private User user;

    @NotNull
    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    private Book book;

    @Column(columnDefinition = "text")
    private String comment;

    @NotNull
    @Setter(AccessLevel.PRIVATE)
    @Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdTime = LocalDateTime.now();

    public Review(Integer rating, User user, Book book) {
        this.rating = rating;
        this.user = user;
        this.book = book;
    }
}
