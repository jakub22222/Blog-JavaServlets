/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pols.lab.services;

import database.ExecuteQueryApp;
import database.SelectDataApp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import pl.polsl.lab.model.BlogModel;
import pl.polsl.lab.model.Document;
import pl.polsl.lab.model.User;

/**
 * Servlet for blog main page
 * @author Jakub Hoś
 * @version 1.4
 */
@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    /**
     * Model object
     */
    private final BlogModel model;
    /**
     * BlogServlet constructor
     */
    public BlogServlet() {
        this.model = BlogModel.getInstance();
    }
    /**
     * Init method
     */
    @Override
    public void init() {
      //  super.init();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        String obj = (String) session.getAttribute("logged");
        if(obj.equals("logged"))
        {
        Cookie[] cookies = request.getCookies();
            String name = "user";
            int authodId=0;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cookuser")) {
                        name = cookie.getValue();
                    }
                    else if(cookie.getName().equals("cookid"))
                    {
                        authodId = Integer.parseInt(cookie.getValue());
                    }
                }
            }
        
        String newPost = request.getParameter("content");
        if(newPost != null && newPost.length() > 0 && authodId>=0)
        {
            model.addDocument(newPost, authodId);
            
            String query[] = {"INSERT INTO APP.DOCUMENTS (CONTENT, ADDED, AUTHORID) VALUES ('"+newPost+"', CURRENT_TIMESTAMP, "+Integer.toString(authodId)+")"};
            ExecuteQueryApp.main(query);
        }
        
        String firstName = request.getParameter("firstName");       
       
        boolean showAll = firstName == null || firstName.length() == 0;
        
        
        ArrayList<Document> documents = model.getDocuments();
        if(documents.isEmpty())
        {
            SelectDataApp mySelector = new SelectDataApp();
            documents = mySelector.getDatabaseDocuments();
            model.setDocuments(documents);
        }
        
        
        ArrayList<User> users = model.getUsers();
        
        
        PrintWriter out = response.getWriter();        
        for(Document d : documents){
            if(showAll || d.getContent().contains(firstName)){
                out.println("<tr class=\"upper\">");
                out.println("<td>Dodane przez ");
                out.println(users.get(d.getAuthor()).getLogin());
                out.println(":</td>");
                out.println("<td class=\"right\">");
                out.println(d.getTimeAgo());
                out.println("</td>");
                out.println("</tr>"); 
                out.println("<tr>");
                out.println("<td colspan=\"2\" class=\"lower\">");
                out.println(d.getContent());
                out.println("</td>");
                out.println("</tr>");
              
            }
        }
        }
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}