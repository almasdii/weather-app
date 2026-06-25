package testingSpring;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.WebApplicationInitializer;

public class WebConfig
        implements
        WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        servletContext.
//        servletContext.setAttribute("dispatcher",new DispatcherServlet());
    }
}

