package ProjectFiles.Models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[a-zA-Z]{3,20}",message = "Write another way")
    private String name;
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    public Person() {    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int person_id) {
        this.id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}