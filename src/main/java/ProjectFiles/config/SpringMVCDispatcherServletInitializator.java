package ProjectFiles.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.EnumSet;

public class SpringMVCDispatcherServletInitializator
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Укажи конфигурационные классы для корневого приложения (если они есть)
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};  // Можешь указать классы, если они нужны
    }

    // Укажи конфигурационный класс для Spring MVC
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    // Указываем URL для маппинга сервлета
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};  // Это обеспечит обработку всех запросов
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        // Регистрация фильтра для поддержки скрытых HTTP методов (например, PUT, DELETE)
        registerHiddenHttpMethodFilter(servletContext);
    }

    // Регистрируем фильтр HiddenHttpMethodFilter для поддержки метода PUT и DELETE
    private void registerHiddenHttpMethodFilter(ServletContext servletContext) {
        servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
                      .addMappingForUrlPatterns(null, true, "/*");
    }
    private void registerCharacterEncodingFilter(ServletContext aContext) {
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic characterEncoding = aContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }
}
