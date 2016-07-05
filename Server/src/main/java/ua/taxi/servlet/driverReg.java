package ua.taxi.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by andrii on 04.07.16.
 */
public class driverReg extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //redirect to driverReg.jsp
        req.getRequestDispatcher("/WEB-INF/pages/driverReg.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //waiting data from the form
        String name = req.getParameter("name");

        if(name == null){
            //current localhost:8080/TaxiProject
            //Absolute path = /http/error.jsp  -> localhost:8080/http/error
            //Relative path = http/error.jsp -> localhost:8080/TaxiProject/http/error.jsp
           resp.sendRedirect("http/error.jsp");
        }

        



    }
}
