package ProjectFiles.DAO;

import ProjectFiles.Models.Book;
import ProjectFiles.Models.Person;
import ProjectFiles.util.PersonValidator;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;
    private final PersonValidator personValidator;

    @Autowired
    public PersonDAO(PersonValidator personValidator, BookDAO bookDAO, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.personValidator = personValidator;
    }
    @Transactional
    public List<Person> people() {
        //return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p",Person.class ).getResultList();
        return people;
    }
    @Transactional
    public Person show(int id) {
        //return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
        //        .stream().findAny().orElse(null);
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);

        return person;
    }
    @Transactional
    public void newPerson(Person person) {
        //jdbcTemplate.update("INSERT INTO Person(name, age) VALUES(?, ?)", person.getName(), person.getAge());
        Session session = sessionFactory.getCurrentSession();
        Person newPerson = new Person();
        newPerson.setName(person.getName());
        newPerson.setAge(person.getAge());
        session.save(newPerson);
    }
    @Transactional
    public void updatePerson(int person_id, Person updatedPerson) {
        //jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?", updatedPerson.getName(),
        //        updatedPerson.getAge(), person_id);
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        person.setName(updatedPerson.getName());
        person.setAge(updatedPerson.getAge());
        session.update(person);
    }
    @Transactional
    public void delete(int person_id) {
        //jdbcTemplate.update("DELETE FROM Person WHERE id=?", person_id);
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        session.delete(person);
    }


}