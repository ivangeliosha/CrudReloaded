package ProjectFiles.DAO;

import ProjectFiles.Models.Book;
import ProjectFiles.Models.Person;
import ProjectFiles.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    private final PersonValidator personValidator;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, PersonValidator personValidator, BookDAO bookDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personValidator = personValidator;
    }

    public List<Person> people() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void newPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age) VALUES(?, ?)", person.getName(), person.getAge());
    }

    public void updatePerson(int person_id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), person_id);
    }

    public void delete(int person_id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", person_id);
    }


}