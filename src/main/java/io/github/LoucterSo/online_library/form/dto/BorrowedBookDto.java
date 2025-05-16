package io.github.LoucterSo.online_library.form.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public record BorrowedBookDto(
        Long id,
        String isbn,
        @JsonProperty("book_name")
        String bookName,
        @JsonProperty("author_name")
        String authorName,
        @JsonProperty("published_date")
        Date publishedDate,
        @JsonProperty("issued_date")
        Date issuedDate,
        @JsonProperty("loan_date")
        Date loanDate,
        String email
) {

}
