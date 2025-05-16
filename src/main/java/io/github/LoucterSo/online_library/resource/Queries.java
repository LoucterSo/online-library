package io.github.LoucterSo.online_library.resource;

import io.github.LoucterSo.online_library.entity.Author;
import io.github.LoucterSo.online_library.entity.Book;
import io.github.LoucterSo.online_library.entity.Genre;
import io.github.LoucterSo.online_library.entity.User;
import io.github.LoucterSo.online_library.form.dto.AuthorRatingDto;
import io.github.LoucterSo.online_library.form.dto.BookDto;
import io.github.LoucterSo.online_library.form.dto.BorrowedBookDto;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Page;
import org.hibernate.query.Query;
import org.hibernate.query.SelectionQuery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Queries {

    static List<User> findUsersOrderedByWithPagination(SessionFactory sessionFactory, String field, Page page) {
        return sessionFactory.fromSession(
                (Session session) -> {
                    SelectionQuery<User> query = session.createSelectionQuery("FROM User u ORDER BY :field DESC", User.class);
                    query.setParameter("field", field);
                    query.setPage(page);
                    return query.getResultList();
                });
    }

    static List<AuthorRatingDto> findTopFiveAuthorsWithGreatestAverageRating(SessionFactory sessionFactory) {
        return sessionFactory.fromSession(
                session -> {
                    Query<AuthorRatingDto> query = session.createNativeQuery("""
                            SELECT a.id as author_id, a.name as author_name,
                            ROUND(AVG(b.average_rating),2) as average_book_rating
                            FROM author a
                            JOIN book b ON a.id = b.author_id
                            GROUP BY a.id, a.name
                            ORDER BY average_book_rating DESC
                            LIMIT 5;
                            """, AuthorRatingDto.class);
                    query.setReadOnly(true);
                    return query.list();
                });
    }

    static List<BorrowedBookDto> findBorrowedBooks(SessionFactory sessionFactory) {
        return sessionFactory.fromSession(
                session -> {
                    Query<BorrowedBookDto> query = session.createNativeQuery("""
                            SELECT b.id,
                                   b.isbn,
                                   b.name as book_name,
                                   a.name as author_name,
                                   b.published_date,
                                   b.issued_date,
                                   bor.loan_date,
                                   u.email
                            FROM book b
                            JOIN author a ON b.author_id = a.id
                            JOIN borrowing bor ON bor.book_id = b.id
                            JOIN users u ON bor.user_id = u.id
                            WHERE bor.return_date IS NULL
                            ORDER BY bor.loan_date DESC
                            """, BorrowedBookDto.class);
                    query.setReadOnly(true);
                    return query.list();
                });
    }

    static List<BookDto> findBooksByGenre(SessionFactory sessionFactory, Long genreId) {
        return sessionFactory.fromSession(
                session -> {
                    Query<BookDto> query = session.createNativeQuery("""
                            SELECT b.id,
                                   b.isbn,
                                   b.name as book_name,
                                   a.name as author_name,
                                   b.published_date,
                                   b.issued_date
                            FROM book b
                            JOIN author a ON a.id = b.author_id
                            JOIN books_genres bg ON bg.book_id = b.id
                            WHERE bg.genre_id = :genre_id
                            """, BookDto.class);
                    query.setParameter("genre_id", genreId);
                    query.setReadOnly(true);
                    return query.list();
                });
    }

    static List<BookDto> findBooks(SessionFactory sessionFactory,
                                   Boolean borrowed,
                                   Long genreId,
                                   Long authorId,
                                   BigDecimal minRating,
                                   Page page) {

        return sessionFactory.fromSession(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<BookDto> dtoQuery = cb.createQuery(BookDto.class);
            Root<Book> root = dtoQuery.from(Book.class);

            List<Predicate> filters = new ArrayList<>();

            if (borrowed != null) {
                filters.add(cb.equal(root.get("borrowed"), borrowed));
            }

            if (minRating != null) {
                filters.add(cb.ge(root.get("averageRating"), minRating));
            }

            if (genreId != null) {
                Join<Book, Genre> genreJoin = root.join("genres");
                filters.add(cb.equal(genreJoin.get("id"), genreId));
            }

            if (authorId != null) {
                filters.add(cb.equal(root.get("author").get("id"), authorId));
            }

            if (!filters.isEmpty()) {
                dtoQuery.where(cb.and(filters.toArray(new Predicate[0])));
            }

            dtoQuery.multiselect(
                    root.get("id"),
                    root.get("isbn"),
                    root.get("name"),
                    root.get("author").get("name"),
                    root.get("publishedDate"),
                    root.get("issuedDate")
            );

            return session.createQuery(dtoQuery)
                    .setFirstResult(page.getFirstResult())
                    .setMaxResults(page.getMaxResults())
                    .setReadOnly(true)
                    .getResultList();
        });
    }

}
