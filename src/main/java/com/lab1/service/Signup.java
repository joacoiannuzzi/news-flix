package com.lab1.service;

import com.lab1.entity.Users;
import com.lab1.model.User;
import org.omg.CORBA.SystemException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.lab1.util.LangUtils.*;
import static com.lab1.util.LangUtils.notEmpty;
import static java.lang.String.join;

@WebServlet("/signup.do")
public class Signup extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getSession().invalidate();

    final RequestDispatcher view = req.getRequestDispatcher("/signup.html");

    view.forward(req,resp);
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final User user = new User();

    user.setFirstName(req.getParameter("firstname"));
    user.setLastName(req.getParameter("lastname"));
    user.setEmail(req.getParameter("email"));
    user.setPassword(req.getParameter("password"));
    user.setActive(true);


    try {
      Users.persist(user);
    } catch(SystemException e){
      e.printStackTrace();
    }

    final RequestDispatcher view = req.getRequestDispatcher("login.html");

    view.forward(req, resp);
  }


}
