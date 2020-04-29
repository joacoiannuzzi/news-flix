package com.lab1.service;

import com.google.gson.Gson;
import com.lab1.model.Article;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.lab1.entity.Articles.*;

@WebServlet(name = "articleServlet",urlPatterns = "/secure/articles")
public class Articles extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        final List<Article> articles = listAll();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        String json = gson.toJson(articles);
        out.print(json);
        out.flush();

    }

}


