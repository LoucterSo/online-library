package io.github.LoucterSo.online_library.entity;

import io.github.LoucterSo.online_library.entity.converter.BigDecimalToDoubleConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter @Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NaturalId
    @Column(length = 20)
    @EqualsAndHashCode.Include
    private String isbn;

    @NotNull
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Author author;

    @NotNull
    @Setter(AccessLevel.PRIVATE)
    @Column(precision = 10, scale = 2)
    @Convert(converter = BigDecimalToDoubleConverter.class)
    private BigDecimal averageRating;

    @NotNull
    @Setter(AccessLevel.PRIVATE)
    private Boolean borrowed;

    @NotNull
    private LocalDate issuedDate;

    @NotNull
    private LocalDate publishedDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    public Book(String isbn, String name, Author author, LocalDate issuedDate) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.issuedDate = issuedDate;
    }
}
