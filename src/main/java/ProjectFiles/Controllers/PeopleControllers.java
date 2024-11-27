package ProjectFiles.Controllers;

import ProjectFiles.DAO.BookDAO;
import ProjectFiles.DAO.PersonDAO;
import ProjectFiles.Models.Person;
import ProjectFiles.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleControllers {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleControllers(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String people(Model model) {
        model.addAttribute("people",personDAO.people());
        return "/people/all";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("person",personDAO.show(id));
        model.addAttribute("listBooks",bookDAO.pikedBooks(id));
        return "people/id";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person",new Person());
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, @Valid BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.newPerson(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(Model model,@PathVariable("id") int person_id) {
        model.addAttribute(personDAO.show(person_id));
        return "people/edit";
    }
    @PatchMapping("{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult,
                               @PathVariable("id") int person_id ) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.updatePerson(person_id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@ModelAttribute("person") Person person,
                               @PathVariable("id") int person_id) {
        personDAO.delete(person_id);
        return "redirect:/people";
    }

}