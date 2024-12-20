package ProjectFiles.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.UniqueElements;
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    @Pattern(regexp = "[A-Z]\\w{3,100}",message = "Write another way")
    private String title;
    @Column(name = "author")
    @Pattern(regexp = "[A-Z]\\w{3,100}",message = "Write another way")
    private String author;
    @Column(name = "year")
    @Min(value = 0, message = "Date should be greater than 0")
    private int year;
    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName ="id")
    private Person owner;

    public Book(String title, String author, int year) {
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
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", owner=" + "Dobav potom" +
                '}';
    }


}
