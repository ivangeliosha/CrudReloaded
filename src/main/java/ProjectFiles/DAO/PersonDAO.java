package ProjectFiles.DAO;

import ProjectFiles.Models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private int PERSON_ID;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PERSON_ID, "John"));
        people.add(new Person(++PERSON_ID, "Jane"));
        people.add(new Person(++PERSON_ID, "Jack"));
        people.add(new Person(++PERSON_ID, "Jona"));
    }

    public List<Person> all() {
        return people;
    }

    public Person id(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(PERSON_ID);
        people.add(person);
    }
    public void update(int id , Person person) {
        Person updatedPerson = id(id);
        updatedPerson.setName(person.getName());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }


}