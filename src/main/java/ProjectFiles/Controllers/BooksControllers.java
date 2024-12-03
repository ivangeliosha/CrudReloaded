package ProjectFiles.Controllers;

import ProjectFiles.DAO.BookDAO;
import ProjectFiles.DAO.PersonDAO;
import ProjectFiles.Models.Book;
import ProjectFiles.Models.Person;
import ProjectFiles.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksControllers {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;//не удаляй


    @Autowired
    public BooksControllers(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;

        this.personDAO = personDAO;
    }

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books",bookDAO.people());
        return "/books/all";
    }
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
    // Получаем данные о книге
    model.addAttribute("book", bookDAO.show(id));

    // Получаем person_id
    Integer personId = bookDAO.showPersonId(id);
    model.addAttribute("person_id", personId);

    // Получаем список всех людей
    model.addAttribute("people", personDAO.people());

    // Если person_id найден, загружаем данные о человеке
    if (personId != null) {
        model.addAttribute("person", personDAO.show(personId));
    } else {
        model.addAttribute("person", null); // Явно указываем, что человека нет
    }

    return "books/id";
}



    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book",new Book());
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, @Valid BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String editBook(Model model,@PathVariable("id") int id) {
        model.addAttribute("book",bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/freedom")
    public String freeBook(@ModelAttribute("person") @Valid Person person,@PathVariable("id") int id ) {
        bookDAO.bookIsSlave(person.getId(),id);//добавить id
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/slave")
    public String slaveBook(@RequestParam("personId") int personId, @PathVariable("id") int id) {
    // Назначаем владельца книги
    bookDAO.bookIsSlave(personId, id);
    return "redirect:/books/" + id;
}



    @PatchMapping("{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,
                               @PathVariable("id") int id ) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id,book);
        return "redirect:/books/"+id;//+id
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@ModelAttribute("book") Book book,
                               @PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

}
