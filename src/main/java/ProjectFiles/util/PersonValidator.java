package ProjectFiles.util;

import ProjectFiles.DAO.PersonDAO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {

        return PersonDAO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
