package io.github.LoucterSo.online_library.resource;

import io.github.LoucterSo.online_library.entity.User;
import io.github.LoucterSo.online_library.form.dto.AuthorRatingDto;
import io.github.LoucterSo.online_library.form.dto.BookDto;
import org.hibernate.SessionFactory;
import org.hibernate.query.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class UserResource {
    private static final Integer USERS_PER_PAGE = 20;
    private final SessionFactory sessionFactory;

    public UserResource(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> findUsersOrderedByRegistrationTime(String field, Integer page) {
        return Queries.findUsersOrderedByWithPagination(sessionFactory, field, Page.page(USERS_PER_PAGE, page));
    }

    public List<AuthorRatingDto> findTopFiveAuthorsWithGreatestAverageRating() {
        return Queries.findTopFiveAuthorsWithGreatestAverageRating(sessionFactory);
    }

    public List<BookDto> findBooks(
            Boolean borrowed,
            Long genreId,
            Long authorId,
            BigDecimal minRating,
            Integer page
    ) {
        return Queries.findBooks(sessionFactory, borrowed, genreId, authorId, minRating, Page.page(5, page == null ? 0 : page));
    }

    public List<BookDto> findBooksByGenre(Long genreId) {
        return Queries.findBooksByGenre(sessionFactory, genreId);
    }
}
