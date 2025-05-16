package io.github.LoucterSo.online_library.form.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record BookDto(
        Long id,
        String isbn,
        @JsonProperty("book_name")
        String bookName,
        @JsonProperty("author_name")
        String authorName,
        @JsonProperty("published_date")
        LocalDate publishedDate,
        @JsonProperty("issued_date")
        LocalDate issuedDate
) {

}
