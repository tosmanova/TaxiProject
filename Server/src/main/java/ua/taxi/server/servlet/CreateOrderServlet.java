package ua.taxi.server.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by andrii on 08.07.16.
 */
@WebServlet(urlPatterns = {"/create-order"})
public class CreateOrderServlet extends Servlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //redirect to DriverRegServlet.jsp
        req.getRequestDispatcher("/WEB-INF/pages/create-order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

     /*   String name = req.getParameter("name");
        String phone = Utils.phoneValidate(req.getParameter("phone"));
        String goFrom = req.getParameter("goFrom");
        String goTo = req.getParameter("goTo");


        if (name == null
                || phone == null
                || pass.length() < 6
                ) {

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
*/
    }


}

