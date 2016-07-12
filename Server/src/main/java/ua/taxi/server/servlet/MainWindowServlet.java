package ua.taxi.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by andrii on 08.07.16.
 */

@WebServlet(urlPatterns = {"/main-window"})
public class MainWindowServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String button = req.getParameter("button");

        if (button.equals("regDriverButton")) {
            req.getRequestDispatcher("/WEB-INF/pages/driver-register.jsp").forward(req, resp);
        }

    }

}
