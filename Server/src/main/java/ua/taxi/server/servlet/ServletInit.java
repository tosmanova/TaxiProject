package ua.taxi.server.servlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import ua.taxi.base.service.OrderService;
import ua.taxi.base.service.UserService;
import ua.taxi.server.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by andrii on 07.07.16.
 */
public class ServletInit extends HttpServlet {


    private ApplicationContext applicationContext;
    UserService userService;
    OrderService orderService;

    private static Logger LOG = Logger.getLogger(DriverRegServlet.class);

    @Override
    public void init() throws ServletException {
        applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
        userService = applicationContext.getBean(UserService.class);
        orderService = applicationContext.getBean(OrderService.class);
    }

    public void errorMessage(HttpServletRequest req, HttpServletResponse resp,
                             String errorTitle, String errorMessage) throws ServletException, IOException {
        req.setAttribute("errorTitle", errorTitle);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
    }


}
