/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pols.lab.services;

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
import pl.polsl.lab.model.BlogModel;

/**
 * Servlet for login page
 * @author Jakub Hoś
 * @version 1.4
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /**
     * Model object
     */
    private final BlogModel model;
    /**
     * LoginServlet constructor
     */
    public LoginServlet() {
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
        
        
        String login = request.getParameter("login");     
        String password = request.getParameter("password"); 
        String remember = request.getParameter("remember"); 
        
        PrintWriter out = response.getWriter(); 
        
        if(model.getUsers().isEmpty())
        {
            SelectDataApp mySelector = new SelectDataApp();
            model.setUsers(mySelector.getDatabaseUsers());
        }
        
        int userId = model.validLogin(login, password);
        if(userId==-1)
        {
            out.println("Niepoprawne dane logowania");
        }
        else
        {
            out.println("logged");
            
                Cookie cUserName = new Cookie("cookuser", login.trim());
                Cookie cRemember = new Cookie("cookrem", remember.trim());
                Cookie cId = new Cookie("cookid",  Integer.toString(userId));
                
		cUserName.setMaxAge(60 * 60 * 24 * 15);// 15 days
                cRemember.setMaxAge(60 * 60 * 24 * 15);
                cId.setMaxAge(60 * 60 * 24 * 15);
		response.addCookie(cUserName);
                response.addCookie(cRemember);
                response.addCookie(cId);
                if(remember.equals("yes"))
                {
                    Cookie cPassword = new Cookie("cookpass", password.trim());
                    cPassword.setMaxAge(60 * 60 * 24 * 15);
                    response.addCookie(cPassword);
                }
            HttpSession session = request.getSession(true);
            session.setAttribute("logged", "logged");
            
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
