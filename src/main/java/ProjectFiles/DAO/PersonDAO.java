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
        people.add(new Person(++PERSON_ID, "John",18,"john@gmail.com"));
        people.add(new Person(++PERSON_ID, "Jane",19,"jane@gmail.com"));
        people.add(new Person(++PERSON_ID, "Jack",17,"jack@gmail.com"));
        people.add(new Person(++PERSON_ID, "Jona",18,"jona@gmail.com"));
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
        updatedPerson.setAge(person.getAge());
        updatedPerson.setEmail(person.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }


}