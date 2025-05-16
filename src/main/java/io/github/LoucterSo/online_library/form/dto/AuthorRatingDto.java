package io.github.LoucterSo.online_library.form.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AuthorRatingDto(
        Long id,
        @JsonProperty("author_name") String authorName,
        @JsonProperty("average_book_rating") BigDecimal averageRating
) {
}
