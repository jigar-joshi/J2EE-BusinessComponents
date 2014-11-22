/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Classes.Customer;

/**
 *
 * @author Jigar
 */
public class InvalidateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private ObjectOutputStream objOut;
    private String filePath;
    private FileOutputStream fileOut;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm a");
    private Date date;
    private Customer cust;
    private HttpSession session;

    
    //INIT METHOD TO GET FILE PATH
    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
            filePath = "";
            filePath = config.getInitParameter("FilePath");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            //GET ACTIVE HTTP SESSION
            session = request.getSession();
            
            //GET CUSTOMER OBJECT
            cust = (Customer) session.getAttribute("customer");
            //INITIALIZE DATE OBJECT
            date = new Date();
            
            //IF CONFIRM OPTION IS SELECTED ON THE ORDER AND CUSTOMER OBJECT EXISTS
            //SAVE THE CUSTOMER SHOPPING CART OBJECT 
            if (request.getParameter("confirm") != null && cust != null) {
                
                //APPEND FILE PATH WITH "CUSTOMER-ID-DATE-TIME.DAT" FORMAT IN C:\TEMP\ FOLDER 
                //CREATE A FILE OUTPUT STREAM
                fileOut = new FileOutputStream(filePath + cust.getCustomerId() + "-" + dateFormat.format(date) + "-" + timeFormat.format(date) + ".dat");
                //CREATE AN OBJECT OUTPUT STREAM
                objOut = new ObjectOutputStream(fileOut);
                
                //WRITE THE CUSTOMER OBJECT TO THE FILE
                objOut.writeObject(cust);
                
                //INVALIDATE THE SESSION
                session.invalidate();
                //REDIRECT THE CUSTOMER TO ORDERFORM.HTML
                response.sendRedirect("orderform.html");
            } else {
                //IF CANCEL ORDER OPTION WAS SELECTED END THE SESSION AND REDIRECT BACK TO ORDERFORM.HTML
                session.invalidate();
                response.sendRedirect("orderform.html");
            }
        }
    }

    @Override
    public void destroy() {
        try {       
            fileOut.flush();
            objOut.flush();
            fileOut.close();
            objOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
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
