package ua.taxi.servlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import ua.taxi.model.User.Car;
import ua.taxi.model.User.UserValidateMessage;
import ua.taxi.service.UserServiceImpl;
import ua.taxi.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by andrii on 04.07.16.
 */

@WebServlet(urlPatterns = {"/driverReg"} )
public class DriverRegServlet extends HttpServlet {

    private ApplicationContext applicationContext;
    private UserServiceImpl userService;

    private static Logger LOG = Logger.getLogger(DriverRegServlet.class);

    @Override
    public void init() throws ServletException {
        applicationContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
        userService = (UserServiceImpl) applicationContext.getBean("userServiceImpl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //redirect to DriverRegServlet.jsp
        req.getRequestDispatcher("/WEB-INF/pages/driverReg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //waiting data from the form
        String name = req.getParameter("name");
        String phone = Utils.phoneValidate(req.getParameter("phone"));
        String pass = req.getParameter("pass");
        String carModel = req.getParameter("carModel");
        String carColor = req.getParameter("carColor");
        String carNumber = req.getParameter("carNumber");


        if (name == null
                || phone == null
                || pass.length() < 6
                || carModel.length() < 2
                || carColor.length() < 2
                || carNumber.length() < 8) {

            resp.sendRedirect("html/error.jsp");
        } else {

            UserValidateMessage message = userService.register(
                    phone, pass, name, new Car(carNumber, carModel, carColor));
            if(message.getState()) {
                req.setAttribute("user", message.getUser());
                req.getRequestDispatcher("/WEB-INF/pages/userInfo.jsp").forward(req, resp);
            }else {
                resp.sendRedirect("html/error.jsp");
            }
        }

        //current localhost:8080/TaxiProject
        //Absolute path = /http/error.jsp  -> localhost:8080/http/error
        //Relative path = http/error.jsp -> localhost:8080/TaxiProject/http/error.jsp


    }
}
