package ProjectFiles.Controllers;

import ProjectFiles.DAO.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/choice")
public class BatchController {

    @Autowired
    private final PersonDAO personDAO;

    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index() {
        return "/choice";
    }
    @GetMapping("/usual")
    public String usual() {
        personDAO.usualApdate();
        return "redirect:/people";
    }
    @GetMapping("/batch")
    public String batch() {
        personDAO.batchApdate();
        return "redirect:/people";
    }
}
