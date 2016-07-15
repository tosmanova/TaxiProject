package ua.taxi.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by andrii on 04.07.16.
 */

@WebServlet(urlPatterns = {"/driver-register"})
public class DriverRegServletInit extends ServletInit {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //redirect to DriverRegServletInit.jsp
        req.getRequestDispatcher("/WEB-INF/pages/driver-register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      /*  //waiting data from the form
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

            errorMessage(req, resp, "input error", "Input data Error");
        } else {

            UserValidateMessage message = userService.register(
                    phone, pass, name, new Car(carNumber, carModel, carColor));
            if (message.getState()) {
                req.setAttribute("user", message.getUser());
                req.getRequestDispatcher("/WEB-INF/pages/test/userInfo.jsp").forward(req, resp);
            } else {
                errorMessage(req, resp, message.getTitle(), message.getBody());
            }
        }

        //current localhost:8080/TaxiProject
        //Absolute path = /http/error.jsp  -> localhost:8080/http/error
        //Relative path = http/error.jsp -> localhost:8080/TaxiProject/http/error.jsp
*/

    }
}
