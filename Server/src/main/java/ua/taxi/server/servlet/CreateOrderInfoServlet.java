package ua.taxi.server.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by andrii on 15.07.16.
 */

@WebServlet(urlPatterns = {"/create-order-info"})
public class CreateOrderInfoServlet extends ServletInit{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/create-order-info.jsp").forward(req, resp);
    }

}
