package ProjectFiles.DAO;

import ProjectFiles.Models.Book;
import ProjectFiles.Models.Person;
import ProjectFiles.util.BookValidator;
import ProjectFiles.util.PersonValidator;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final BookValidator bookValidator;
    private final PersonValidator personValidator;

    private final SessionFactory sessionFactory;
    @Autowired
    public BookDAO(PersonValidator personValidator, BookValidator bookValidator, PersonValidator personValidator1, SessionFactory sessionFactory) {
        this.bookValidator = bookValidator;
        this.personValidator = personValidator1;
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public List<Book> people() {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("from Book", Book.class).getResultList();
        return books;
    }
    @Transactional
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        return book;
        //return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class).stream().findAny().orElse(null);
    }
    @Transactional
    public Integer showPersonId(int id) {
        Session session = sessionFactory.getCurrentSession();
        try{
            Book book = session.get(Book.class, id);
            Person person = null;
            if(book.getOwner() == null){
                return null;
            }
            else {
                person = session.get(Person.class,book.getOwner().getId());
                return person.getId();
            }
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    public void save(Book book) {
        //jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES(?, ?, ?)",
        //        book.getTitle(), book.getAuthor(), book.getYear());
        Session session = sessionFactory.getCurrentSession();
        Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getYear());
        session.save(newBook);
    }
    @Transactional
    public void update(int id, Book updatedBook) {
        //jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?", updatedBook.getTitle(),
        //        updatedBook.getAuthor(),updatedBook.getYear(), id);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setYear(updatedBook.getYear());
        session.update(book);
    }
    @Transactional
    public void bookIsFree(int id) {
    Session session = sessionFactory.getCurrentSession();
    Book book = session.get(Book.class, id);
    if (book == null) {
        throw new IllegalArgumentException("Book not found");
    }

    // Проверяем валидность полей
    if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
        book.setAuthor("Unknown Author"); // Или установите значение по умолчанию
    }

    Person person = session.get(Person.class, book.getOwner().getId());
    book.setOwner(null);
    person.getBook().remove(book);
    session.update(book);
    session.update(person);
    }


    @Transactional
    public void bookIsSlave(int person_id, int book_id) {
        //jdbcTemplate.update("update book set person_id = ? where id = ?",person_id,book_id );
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        Person person = session.get(Person.class, person_id);
        book.setOwner(person);
        person.getBook().add(book);
        session.update(book);
        session.update(person);
    }
    @Transactional
    public void delete(int id) {
        //jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.delete(book);
    }
    @Transactional
    public List<Book> pikedBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("SELECT b FROM Book b WHERE b.owner.id = :person_id", Book.class)
                          .setParameter("person_id", id)
                          .getResultList();
        return books;
        //return jdbcTemplate.query(
           // "SELECT * FROM Book WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }


}
