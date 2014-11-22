/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Classes.*;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Jigar
 */
public class ShowCartServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            //GET ACTIVE HTTP SESSION
            HttpSession session = request.getSession();
            //GET THE CUSTOMER OBJECT
            Customer cust = (Customer) session.getAttribute("customer");
            out.println("<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <title>Show Cart</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <div>\n" +
"            <h1>The Cart contains these Pizzas:</h1>\n" +
"            <table border=\"1\">\n" +
"\n <thead><th>No</th><th>Size</th><th>Toppings</th></thead><tbody>");
            
             //IF CUSTOMER OBJECT IS NOT NULL
            //PRINT THE SIZE AND TOPPINGS OF PIZZA ORDERS
            if(cust!=null){
                for(int i=0;i<cust.getCartSize();i++){
                    out.println("<tr><td>" + (i+1) + "</td><td>" + cust.getOrderElementAt(i).getSize() + "</td><td>" + cust.getOrderElementAt(i).ToppingsToString() + "</td></tr>");
                }
            }
            
            out.println(
"            </tbody></table><br />\n" +
"\n" +
"            <form style=\"display: inline;\" method=\"post\" action=\"addToCart.html\">\n" +
        "<input type='hidden' name='custName' value='" + cust.getCustomerName() +"' />"+
        "<input type='hidden' name='custId' value='" + cust.getCustomerId() +"' />" +
"                <input type=\"submit\" name=\"orderPizza\" value=\"Order Another Pizza\" />\n" +
"            </form> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
"\n" +
"            <form style=\"display:inline;\" method=\"post\" action=\"checkout.html\">\n" +
"                <input type=\"submit\" name=\"checkout\" value=\"Checkout\" />\n" +
"            </form>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
"\n" +
"            <form style=\"display:inline;\" method=\"post\" action=\"invalidate.html\">\n" +
"                <input type=\"submit\" name=\"cancel\" value=\"Cancel Order\" />\n" +
"            </form>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>");
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
