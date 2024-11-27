package ProjectFiles.DAO;

import ProjectFiles.Models.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> all() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?", new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name,age,email) VALUES(?, ?, ?)", person.getName(), person.getAge(),
               person.getEmail());
    }

    public void update(int id , Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", person.getName(),
                person.getAge(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public void batchApdate() {
        List<Person> list = creation();
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO Person(name,age,email) VALUES(?, ?, ?)",new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(2,list.get(i).getName());
                ps.setInt(3,list.get(i).getAge());
                ps.setString(4,list.get(i).getEmail());

            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("Batch date: " + (end - start));
    }

    public void usualApdate(){
        List<Person> list = creation();
        long start = System.currentTimeMillis();
        for (Person person : list) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)", person.getId(), person.getName(),
                    person.getAge(), person.getEmail());
        }
        long end = System.currentTimeMillis();
        System.out.println("Usal date: " + (end - start));
    }

    public List<Person> creation(){
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new Person());
        }
        return list;
    }



}