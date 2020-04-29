package com.lab1.service;

import com.google.gson.Gson;
import com.lab1.model.Article;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static com.lab1.entity.Articles.*;

@WebServlet(name = "articleServlet",urlPatterns = "/secure/articles")
public class Articles extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String,String[]> map = req.getParameterMap();
        Set s = map.entrySet();
        Iterator it = s.iterator();
        String diario;
        String[] diario1;

        while(it.hasNext()) {

            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
            diario = entry.getKey();
            diario1 = entry.getValue();




        final List<Article> articles = findByDiario(diario);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        String json = gson.toJson(articles);
        out.print(json);
        out.flush();

    }        }


}


