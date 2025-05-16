package io.github.LoucterSo.online_library.api.controller;

import io.github.LoucterSo.online_library.form.dto.BookDto;
import io.github.LoucterSo.online_library.resource.UserResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {
    private final UserResource userResource;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BookDto> getBooks(
            @RequestParam(required = false) Boolean borrowed,
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) Integer page
            ) {

        return userResource.findBooks(borrowed, genreId, authorId, minRating, page);
    }
}
