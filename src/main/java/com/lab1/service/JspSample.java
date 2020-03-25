package com.lab1.service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lab1.util.LangUtils.*;
import static java.lang.String.join;

@WebServlet("/jsp")
public class JspSample extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    final String param1 = join(", ", notNull(req.getParameterValues("param1"), EMPTY_ARRAY));
    final String param2 = join(", ", notNull(req.getParameterValues("param2"), EMPTY_ARRAY));

    req.setAttribute("param1", notEmpty(param1, "None"));
    req.setAttribute("param2", notEmpty(param2, "None"));

    final RequestDispatcher view = req.getRequestDispatcher("index.jsp");
    view.forward(req, resp);
  }

}


