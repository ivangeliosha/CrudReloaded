package ProjectFiles.util;

import ProjectFiles.DAO.BookDAO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {

        return BookDAO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
