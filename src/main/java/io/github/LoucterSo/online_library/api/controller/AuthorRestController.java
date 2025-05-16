package io.github.LoucterSo.online_library.api.controller;

import io.github.LoucterSo.online_library.form.dto.AuthorRatingDto;
import io.github.LoucterSo.online_library.resource.UserResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorRestController {
    private final UserResource userResource;

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<AuthorRatingDto> getTop5Authors() {

        return userResource.findTopFiveAuthorsWithGreatestAverageRating();
    }
}
