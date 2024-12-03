package ProjectFiles.Controllers;

import ProjectFiles.DAO.BookDAO;
import ProjectFiles.DAO.PersonDAO;
import ProjectFiles.Models.Book;
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
    public String show(Model model,@PathVariable("id") int id) {
        model.addAttribute("book",bookDAO.show(id));
        model.addAttribute("person_id",bookDAO.showPersonId(id));
        model.addAttribute("person",personDAO.show(bookDAO.showPersonId(id)));
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
    @GetMapping("/{id}/freedom")
    public String freeForBook(Model model,@PathVariable("id") int id) {
        model.addAttribute("book",bookDAO.show(id));
        return "redirect:/books/";
    }
    @PatchMapping("/{id}/freedom")
    public String freeBook(@ModelAttribute("book") @Valid Book book,@PathVariable("id") int id ) {
        bookDAO.bookIsFree(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,
                               @PathVariable("id") int id ) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id,book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@ModelAttribute("book") Book book,
                               @PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

}
