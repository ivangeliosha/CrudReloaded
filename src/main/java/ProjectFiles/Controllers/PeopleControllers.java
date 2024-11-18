package ProjectFiles.Controllers;

import ProjectFiles.DAO.PersonDAO;
import ProjectFiles.Models.Person;
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

    @Autowired
    public PeopleControllers(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String people(Model model) {
        model.addAttribute("people",personDAO.all());
        return "/people/all";
    }

    @GetMapping("/{id}")
    public String id(Model model,@PathVariable("id") int id) {
        model.addAttribute("person",personDAO.id(id));
        return "people/id";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person",new Person());
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(Model model,@PathVariable("id") int id) {
        model.addAttribute(personDAO.id(id));
        return "people/edit";
    }
    @PatchMapping("{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult,
                               @PathVariable("id") int id ) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@ModelAttribute("person") Person person,
                               @PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}