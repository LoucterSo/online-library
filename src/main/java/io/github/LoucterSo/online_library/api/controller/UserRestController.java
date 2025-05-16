package io.github.LoucterSo.online_library.api.controller;

import io.github.LoucterSo.online_library.entity.User;
import io.github.LoucterSo.online_library.form.dto.AuthorRatingDto;
import io.github.LoucterSo.online_library.resource.UserResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserResource userResource;

    @GetMapping("/{field}/{page}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<User> getUsersOrderedByRegistrationTime(
            @PathVariable String field,
            @PathVariable Integer page
    ) {

        return userResource.findUsersOrderedByRegistrationTime(field, page);
    }
}
