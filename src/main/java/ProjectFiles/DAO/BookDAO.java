package ProjectFiles.DAO;

import ProjectFiles.Models.Book;
import ProjectFiles.util.BookValidator;
import ProjectFiles.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final BookValidator bookValidator;


    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonValidator personValidator, BookValidator bookValidator) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookValidator = bookValidator;

    }

    public List<Book> people() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public Integer showPersonId(int id) {
    try {
        return jdbcTemplate.queryForObject(
            "SELECT person_id FROM Book WHERE id = ?",
            new Object[]{id},
            Integer.class
        );
    } catch (EmptyResultDataAccessException e) {
        // Если ничего не найдено, возвращаем null
        return null;
    }
}



    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?", updatedBook.getTitle(),
                updatedBook.getAuthor(),updatedBook.getYear(), id);
    }
    public int bookIsFree(int id) {
        return jdbcTemplate.update("update book set person_id = null where id = ?", id);
    }


    public void bookIsSlave(int person_id, int book_id) {
        jdbcTemplate.update("update book set person_id = ? where id = ?",person_id,book_id );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public List<Book> pikedBooks(int id) {
    return jdbcTemplate.query(
            "SELECT * FROM Book WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }


}
