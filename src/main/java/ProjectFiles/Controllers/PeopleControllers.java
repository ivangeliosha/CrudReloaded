package ProjectFiles.Controllers;

import ProjectFiles.DAO.PersonDAO;
import ProjectFiles.Models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/{param}")
    public String id(Model model,@PathVariable("param") int id) {
        model.addAttribute("person",personDAO.id(id));
        return "people/id";
    }

    @GetMapping("/{name}")
    public String name(Model model,@PathVariable("name") String name) {
        model.addAttribute("personName",personDAO.findByName(name));
        return "people/name";
    }

    @GetMapping("/{surname}")
    public String surname(Model model,@PathVariable("surname") String surname) {
        model.addAttribute("personSurname",personDAO.findBySurname(surname));
        return "people/surname";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person",new Person());
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }
}