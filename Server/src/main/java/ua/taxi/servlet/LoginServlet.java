package ua.taxi.servlet;

import ua.taxi.model.User.UserValidateMessage;
import ua.taxi.utils.Utils;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by andrii on 07.07.16.
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends Servlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String phone = Utils.phoneValidate(req.getParameter("phone"));
        String pass = req.getParameter("pass");
        UserValidateMessage message = userService.login(phone, pass);


        if (message.getState()) {

            HttpSession session =  req.getSession(true);

            session.setAttribute("inSystem", true);
            session.setAttribute("currentUserPhone", message.getUser().getPhone());

            req.setAttribute("user", message.getUser());
            req.getRequestDispatcher("/WEB-INF/pages/test/userInfo.jsp").forward(req, resp);
        } else {
            errorMessage(req, resp, message.getTitle(), message.getBody());
        }
    }

}
