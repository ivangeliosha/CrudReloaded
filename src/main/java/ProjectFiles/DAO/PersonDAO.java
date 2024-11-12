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
        people.add(new Person(++PERSON_ID, "John", "Doe"));
        people.add(new Person(++PERSON_ID, "Jane", "Doe"));
        people.add(new Person(++PERSON_ID, "Jack", "Doe"));
        people.add(new Person(++PERSON_ID, "Jona", "Boo"));
    }

    public List<Person> all() {
        return people;
    }

    public Person id(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public Person findByName(String name) {
        return people.stream().filter(person -> person.getName().equals(name)).findAny().orElse(null);
    }

    public Person findBySurname(String surname) {
        return people.stream().filter(person -> person.getSurname().equals(surname)).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(PERSON_ID);
        people.add(person);
    }


}
