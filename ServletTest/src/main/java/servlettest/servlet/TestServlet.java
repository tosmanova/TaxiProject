package servlettest.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by andrii on 04.07.16.
 */
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String query = req.getQueryString();
        Cookie[] cookies = req.getCookies();

        System.out.printf("client %s, name %s, querry %s\n",
                req.getRemoteUser(), name, query);

        if(cookies !=null) {
            Arrays.stream(cookies).forEach((cookie ->
                    System.out.printf("name %s, value %s\n",
                            cookie.getName(), cookie.getValue())));
        }

        PrintWriter pw = resp.getWriter();
        pw.printf("<h1>Hello %s</h1>", name);
        pw.flush();

    }
}
