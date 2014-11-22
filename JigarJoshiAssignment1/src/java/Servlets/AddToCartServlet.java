package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

//Import Classes folder 
import Classes.*;
import javax.servlet.ServletConfig;

/**
 *
 * @author Jigar
 */
public class AddToCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HttpSession session;
    private String custId = "", custName = "", size = "";
    private String[] toppings;
    private Customer cust;
    private ServletContext context;

    public void init(ServletConfig config) {
        try {
            // init method to log errors
            super.init(config);
            context = config.getServletContext();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //Validate the form!
            String err = validateForm(request, response);
            if (!err.equals("")) //generate error form if validation fails
            {
                generateErrorForm(request, response, err);
            } else { // generate proper form after successful validation
                //Call HTTP CREATE SESSION METHOD
                createHttpSession(request, response);
                //generate proper form after successful validation
                generateValidatedForm(request, response);
            }
        }

    }

    /* Creates HTTP Session*/
    protected void createHttpSession(HttpServletRequest request, HttpServletResponse response) {
        try {
            //CREATE HTTP SESSION
            session = request.getSession();
            //CHECKS IF SESSION IS NEW 
            if (session.isNew()) {

                //create a new Customer object
                cust = new Customer(custId, custName);
                //add order object to shopping cart
                cust.addToCart(new PizzaOrder(Integer.parseInt(size), toppings));
                session.setAttribute("customer", cust);

            } else { //IF SESSION EXISTS
                //get existing Customer and add new order to cart  
                cust = (Customer) session.getAttribute("customer");
                cust.addToCart(new PizzaOrder(Integer.parseInt(size), toppings));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method gereates Error Form with the user options saved!
    protected void generateErrorForm(HttpServletRequest req, HttpServletResponse res, String err) {
        try {
            PrintWriter out = res.getWriter();
            res.setContentType("text/html");
            out.println("<html><head><title>Pizza Order</title></head><body>");
            out.println(err);
            out.println(
                    "<hr /><h1>Pizza Order Form</h1>\n"
                    + "        <form method=\"post\" action='addToCart.html' style=\"display:inline;\">\n"
            );

            //IF BOTH CUSTOMER NAME AND ID IS FILLED OUT DISPLAY THE NAME AND ID IN A LABEL INSTEAD OF TEXTBOX 
            if (custId.equals("") || custName.equals("")) {
                out.println("<p><label id=\"lblCustomerId\" for=\"txtCustId\">Customer ID:</label>\n"
                        + "                <input type=\"text\" id=\"txtCustId\" name=\"custId\" value=\"" + custId + "\"/></p>\n"
                        + "            <p><label id=\"lblCustomerName\" for=\"txtCustName\">Customer Name:</label>\n"
                        + "            <input type=\"text\" id=\"txtCustName\" name=\"custName\" value=\"" + custName + "\" /></p>\n");
            }
            //ELSE PROVIDE TEXTBOXES FOR CUSTOMER NAME AND ID
            else {
                out.println("<p><label id=\"lblCustomerId\">Customer ID: " + custId + "</label>\n"
                        + "<input type=\"hidden\" id=\"txtCustId\" name=\"custId\" value='" + custId + "'/></p>\n"
                        + "\n"
                        + "<p><label id=\"lblCustomerName\">Customer Name: " + custName + "</label>\n"
                        + "<input type=\"hidden\" id=\"txtCustName\" name=\"custName\" value='" + custName + "'/></p>\n"
                        + "\n");
            }

            out.println("<p>Size:\n");

            //IF SIZE IS SELECTED
            if (size != null) {
                //IF SIZE IS 6 CHECK RADIO BUTTON WITH A VALUE OF 6 
                if (size.equals("6")) {
                    out.println(
                            "<input type=\"radio\" name=\"radius\" value='6' checked=\"checked\" />6\n"
                    );
                } else {
                    out.println(
                            "<input type=\"radio\" name=\"radius\" value='6' />6\n"
                    );
                }

                //IF SIZE IS 9 CHECK RADIO BUTTON WITH A VALUE OF 9
                if (size.equals("9")) {
                    out.println(
                            "<input type=\"radio\" name=\"radius\" value='9' checked=\"checked\" />9\n"
                    );
                } else {
                    out.println(
                            "<input type=\"radio\" name=\"radius\" value='9' />9\n"
                    );
                }
                
                //IF SIZE IS 12 CHECK RADIO BUTTON WITH A VALUE OF 12
                if (size.equals("12")) {
                    out.println(
                            "<input type=\"radio\" name=\"radius\" value='12' checked=\"checked\" />12\n"
                    );
                } else {
                    out.println(
                            "<input type=\"radio\" name=\"radius\" value='12' />12\n"
                    );
                }
            }
            
            //IF RADIO BUTTONS WERE NOT CHECKED GENERATE DEFAULT RADIO BUTTON OPTIONS
            else {
                out.println("<input type=\"radio\" name=\"radius\" value='6' />6\n"
                        + "            <input type=\"radio\" name=\"radius\" value='9' />9\n"
                        + "            <input type=\"radio\" name=\"radius\" value='12' />12 </p>\n");
            }
            out.println(
                    "            <p>Toppings:\n"
                    + "                <select name=\"top\" size=\"4\" multiple>\n");
            
            //IF TOPPINGS WERE SELECTED
            if (toppings != null) {
                
                //BOOLEAN FLAGS FOR TOPPINGS TO MAKE SURE EACH TOPPING IS SHOWN ONCE ONLY
                boolean pepperoni = false, mushroom = false, greenPepp = false;
                
                //LOOP THROUGH TOPPINGS ARRAY
                for (int i = 0; i < toppings.length; i++) {
                    //IF PEPPERONI WAS SELECTED
                    if (toppings[i].equalsIgnoreCase("Pepperoni")) {
                        //MARK BOOLEAN TRUE AND SELECT THE PEPPERONI TOPPING
                        pepperoni = true;
                        out.println(
                                "                    <option value=\"Pepperoni\" selected>Pepperoni</option>\n"
                        );
                    }
                    
                    //IF MUSHROOMS WERE SELECTED
                    if (toppings[i].equalsIgnoreCase("Mushrooms")) {
                        //MARK BOOLEAN FOR MUSHROOM AS TRUE AND SELECT MUSHROOMS
                        mushroom = true;
                        out.println(
                                "                    <option value=\"Mushrooms\" selected>Mushrooms</option>\n"
                        );
                    }
                    
                    //IF GREEN PEPPERS WERE SELECTED
                    if (toppings[i].equalsIgnoreCase("Green Pepper")) {
                        //MARK BOOLEAN FOR GREEN PEPPERS AS TRUE AND SELECT GREEN PEPPERS
                        greenPepp = true;
                        out.println(
                                "                    <option value=\"Green Pepper\" selected>Green Pepper</option>\n"
                        );
                    }
                }
                //IF PEPPERONI WAS NOT SELECTED POPULATE DROP DOWN WITH UNSELECTED PEPPERONI
                if (!pepperoni) {
                    out.println(
                            "<option value=\"Pepperoni\">Pepperoni</option>\n"
                    );
                }
                //IF MUSHROOMS WERE NOT SELECTED POPULATE DROP DOWN WITH UNSELECTED MUSHROOMS
                if (!mushroom) {
                    out.println(
                            "<option value=\"Mushrooms\">Mushrooms</option>\n"
                    );
                }
                //IF GREEN PEPPERS WERE NOT SELECTED POPULATE DROP DOWN WITH UNSELECTED GREEN PEPPERS
                if (!greenPepp) {
                    out.println(
                            "<option value=\"Green Pepper\">Green Pepper</option>\n"
                    );
                }
            }
            //IF NONE OF THE TOPPINGS WERE SELECTED THEN GENERATE TOPPINGS DROP DOWN WITH UNSELECTED TOPPINGS
            else {
                out.println(
                        "                    <option value=\"Pepperoni\">Pepperoni</option>\n"
                        + "                    <option value=\"Mushrooms\">Mushrooms</option>\n"
                        + "                    <option value=\"Green Pepper\">Green Pepper</option>"
                );
            }

            out.println(
                    "                </select> </p>\n"
                    + "                <br /><input type=\"submit\" name=\"addCart\" value='Add To Cart' />\n"
                    + "</form> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                    + "  <form style=\"display: inline;\" method=\"post\" action=\"showCart.html\">\n"
                    + "            <input type=\"submit\" name=\"showCart\" value=\"Show Cart\" />\n"
                    + "        </form> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                    + "        \n"
                    + "        <form style=\"display:inline;\" method=\"post\" action=\"checkout.html\">\n"
                    + "            <input type=\"submit\" name=\"checkout\" value=\"Checkout\" />\n"
                    + "        </form>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                    + "        \n"
                    + "        <form style=\"display:inline;\" method=\"post\" action=\"invalidate.html\">\n"
                    + "            <input type=\"submit\" name=\"cancel\" value=\"Cancel Order\" />\n"
                    + "        </form>\n"
                    + "        \n"
                    + "        <p>No Of Pizza in Cart: ");
            //IF CUSTOMER OBJECT IS NOT NULL GET THE CART SIZE OTHER WISE PRINT 0
            if (cust != null) {
                out.println(cust.getCartSize());
            } else {
                out.println("0");
            }
            out.println("</p>\n"
                    + "    </body>\n"
                    + "</html>\n");

            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    //This method checks if all the inputs are valid
    protected String validateForm(HttpServletRequest request, HttpServletResponse response) {

        //get all parameters in variables
        String err = "", logErr = "";
        custId = request.getParameter("custId");
        custName = request.getParameter("custName");
        size = request.getParameter("radius");
        toppings = request.getParameterValues("top");
        try {
            //If request is from this page or orderform.html then validate
            //required to bypass validation when navigating from checkout servlet or showcart servlet
            if (request.getParameter("addCart") != null) {

                //ERROR CHECKING
                if (custId.trim().isEmpty()) {
                    logErr += "ID: ?\t";
                    err += "<p style=\"color:red;font-size:20px\">ID is required and must be 6 digits long</p>";
                } else {
                    if (!custId.trim().matches("^[0-9]{6}$")) {
                        err += "<p style=\"color:red;font-size:20px\">ID is required and must be 6 digits long</p>";
                        logErr += "Invalid Id ->" + custId + "\t";
                    }
                }

                if (custName.trim().isEmpty()) {
                    logErr += "Name: ?\t";
                    err += "<p style=\"color:red;font-size:20px\">Name is required and must only have alphabets</p>";
                } else {
                    if (!custName.trim().matches("^[a-zA-Z\\s]{1,}$")) {
                        err += "<p style=\"color:red;font-size:20px\">Name is required and must only have alphabets</p>";
                        logErr += "Invalid Name->" + custName + "\t";
                    }
                }
                if (size == null) {
                    err += "<p style=\"color:red;font-size:20px\">Please select a size</p>";
                    logErr += "Size:?  ";
                }
                if (toppings == null) {
                    err += "<p style=\"color:red;font-size:20px\">Please select a topping</p>";
                    logErr += "Toppings:?\r\n";
                }
                context.log(logErr);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        context.log(logErr);
        return err;
    }

    //This Method generates HTML form when all credentials are valid
    protected void generateValidatedForm(HttpServletRequest request, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            cust = (Customer) session.getAttribute("customer");
            out.println("<!DOCTYPE html>\n"
                    + "<!--\n"
                    + "To change this license header, choose License Headers in Project Properties.\n"
                    + "To change this template file, choose Tools | Templates\n"
                    + "and open the template in the editor.\n"
                    + "-->\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Pizza Order Form</title>\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <h1>Pizza Order Form</h1>\n"
                    + "        <form method=\"post\" action='addToCart.html' style=\"display:inline;\">\n"
                    + "            <p><label id=\"lblCustomerId\">Customer ID: " + custId + "</label>\n"
                    + "                <input type=\"hidden\" id=\"txtCustId\" name=\"custId\" value='" + custId + "'/></p>\n"
                    + "            \n"
                    + "            <p><label id=\"lblCustomerName\">Customer Name: " + custName + "</label>\n"
                    + "                <input type=\"hidden\" id=\"txtCustName\" name=\"custName\" value='" + custName + "'/></p>\n"
                    + "            \n"
                    + "            <p>Size:\n"
                    + "            <input type=\"radio\" name=\"radius\" value='6' />6\n"
                    + "            <input type=\"radio\" name=\"radius\" value='9' />9\n"
                    + "            <input type=\"radio\" name=\"radius\" value='12' />12 </p>\n"
                    + "            <p>Toppings:\n"
                    + "                <select name=\"top\" size=\"4\" multiple>\n"
                    + "                    <option value=\"Pepperoni\">Pepperoni</option>\n"
                    + "                    <option value=\"Mushrooms\">Mushrooms</option>\n"
                    + "                    <option value=\"Green Pepper\">Green Pepper</option>\n"
                    + "                </select> </p>\n"
                    + "                <br /><input type=\"submit\" name=\"addCart\" value='Add To Cart' />\n"
                    + "        </form> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                    + "        <form style=\"display: inline;\" method=\"post\" action=\"showCart.html\">\n"
                    + "            <input type=\"submit\" name=\"showCart\" value=\"Show Cart\" />\n"
                    + "        </form> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                    + "        \n"
                    + "        <form style=\"display:inline;\" method=\"post\" action=\"checkout.html\">\n"
                    + "            <input type=\"submit\" name=\"checkout\" value=\"Checkout\" />\n"
                    + "        </form>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n"
                    + "        \n"
                    + "        <form style=\"display:inline;\" method=\"post\" action=\"invalidate.html\">\n"
                    + "            <input type=\"submit\" name=\"cancel\" value=\"Cancel Order\" />\n"
                    + "        </form>\n"
                    + "        \n"
                    + "        <p>No Of Pizza in Cart: ");
            if (cust != null) {
                out.println(cust.getCartSize());
            } else {
                out.println("0");
            }
            out.println("</p>\n"
                    + "    </body>\n"
                    + "</html>\n");
        } catch (Exception e) {
            e.printStackTrace();
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
