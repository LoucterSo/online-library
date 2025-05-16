package io.github.LoucterSo.online_library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotNull
    @Column(updatable = false, columnDefinition = "date default current_date")
    private LocalDate loanDate = LocalDate.now();

    @Check(constraints = "return_date is null or return_date >= loan_date")
    @Setter(AccessLevel.PRIVATE)
    private LocalDate returnDate;

    public void setReturnDate(LocalDate returnDate) {
        if (returnDate == null || loanDate.isAfter(returnDate)) {
            throw new IllegalArgumentException("Return date cannot be before loan date");
        }

        this.returnDate = returnDate;
    }

    public Borrowing(Book book, User user) {
        this.book = book;
        this.user = user;
    }
}
