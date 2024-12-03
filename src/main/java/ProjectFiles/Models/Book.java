package ProjectFiles.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

public class Book {
    //@UniqueElements
    private int id;
    @Pattern(regexp = "[A-Z]\\w{3,20}",message = "Write another way")
    private String title;
    @Pattern(regexp = "[A-Z]\\w{3,10} [A-Z]\\w{3,10}",message = "Write another way")
    private String author;
    @Min(value = 0, message = "Date should be greater than 0")
    private int year;

    //private int personId;
    //public Book(int id,int personId, String title, String author, int year) {
    //    this.id = id;
    //    this.personId = personId;
    //    this.title = title;
    //    this.author = author;
    //    this.year = year;
    //}

    public Book(int id, String title, String author, int year) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.year = year;
    }

    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //public int getPersonId() {
    //    return personId;
    //}
//
    //public void setPersonId(int personId) {
    //    this.personId = personId;
    //}
}
