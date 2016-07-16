package ua.taxi.server.servlet;


import ua.taxi.base.model.order.Address;
import ua.taxi.base.model.order.OrderValidateMessage;
import ua.taxi.base.model.user.UserValidateMessage;
import ua.taxi.base.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by andrii on 08.07.16.
 */
@WebServlet(urlPatterns = {"/create-order"})
public class CreateOrderServlet extends ServletInit {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/create-order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String phone = Utils.phoneValidate(req.getParameter("phone"));
        Address goFrom = Utils.addressValidate(req.getParameter("goFrom"));
        Address goTo = Utils.addressValidate(req.getParameter("goTo"));


        if (name == null
                || phone == null
                || goFrom == null
                || goTo == null
                ) {

            errorMessage(req, resp, "input error", "Input data Error");
        } else {

            OrderValidateMessage message = orderService.createOrder(
                    phone, name, goFrom, goTo);
            if (message.isState()) {
                req.setAttribute("order", message.getOrder());
                req.getRequestDispatcher("/WEB-INF/pages/create-order-info.jsp").forward(req, resp);
            } else {
                errorMessage(req, resp, message.getTitle(), message.getBody());
            }
        }

    }


}

