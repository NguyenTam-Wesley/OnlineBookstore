package com.nctam.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

@WebServlet("/OrderPage")
public class OrderPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String itemID = request.getParameter("itemID");
		ShoppingCart cart;
		synchronized (session) {
			cart = (ShoppingCart) session.getAttribute("shoppingCart");
			if (cart == null) {
				cart = new ShoppingCart();
				session.setAttribute("shoppingCart", cart);
			}
			if (itemID != null) {
				String numItemsString = request.getParameter("numItems");
				if (numItemsString == null) {
					cart.addItem(itemID);
				} else {
					int numItems;
					try {
						numItems = Integer.parseInt(numItemsString);
					} catch (NumberFormatException nfe) {
						numItems = 1;
					}
					cart.setNumOrdered(itemID, numItems);
				}
			}
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Status of Your Order";
		String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
		out.println(docType + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE>"
				+ "<style>"
		        + "body {"
		        + " background-image: url('http://localhost:8080/OBS/data/order3.jpg');"
		        + " background-size: cover;"
		        + " background-position: center;"
		        + "}"
		        + "</style>"
		        + "<script>\r\n"
		        + "function incrementQuantity(index) {\r\n"
		        + "var quantityField = document.getElementById('quantity' + index);\r\n"
		        + "var quantity = parseInt(quantityField.value);\r\n"
		        + "quantityField.value = quantity + 1;\r\n"
		        + "}\r\n" + "\r\n"
		        + "function decrementQuantity(index) {\r\n"
		        + "var quantityField = document.getElementById('quantity' + index);\r\n"
		        + "var quantity = parseInt(quantityField.value);\r\n"
		        + "if (quantity > 0) {\r\n"
		        + "quantityField.value = quantity - 1;\r\n"
		        + "}\r\n" + "}\r\n" 
		        + "</script> </HEAD>\n"
		        + "<a href=\"index.html\" class=\"home-icon\">Home</a>" + "<H1 ALIGN=\"CENTER\">" + title + "</H1>");
		synchronized (session) {
		    List<?> itemsOrdered = cart.getItemsOrdered();
		    if (itemsOrdered.size() == 0) {
		        out.println("<H2><I>No items in your cart...</I></H2>");
		    } else {
		        out.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" + "<TR BGCOLOR=\"#FFAD00\">\n"
		                + " <TH>Item ID<TH>Description\n" + " <TH>Unit Cost<TH>Number<TH>Total Cost");
		        ItemOrder order;
		        NumberFormat formatter = NumberFormat.getCurrencyInstance();
		        for (int i = 0; i < itemsOrdered.size(); i++) {
		            order = (ItemOrder) itemsOrdered.get(i);
		            out.println("<TR>\n" + " <TD>" + order.getItemID() + "\n" + " <TD>" + order.getShortDescription()
		                    + "\n" + " <TD>" + formatter.format(order.getUnitCost()) + "\n" + " <TD>" + "<FORM>\n"
		                    + "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" VALUE=\"" + order.getItemID() + "\">\n"
		                    + "<INPUT TYPE=\"BUTTON\" VALUE=\"-\" onclick=\"decrementQuantity(" + i + ")\">\n"
		                    + "<INPUT TYPE=\"TEXT\" id=\"quantity" + i + "\" NAME=\"numItems\" SIZE=3 VALUE=\""
		                    + order.getNumItems() + "\">\n"
		                    + "<INPUT TYPE=\"BUTTON\" VALUE=\"+\" onclick=\"incrementQuantity(" + i + ")\">\n"
		                    + "<SMALL>\n" + "<INPUT TYPE=\"SUBMIT\"\n " + " VALUE=\"Update\">\n" + "</SMALL>\n"
		                    + "</FORM>\n" + " <TD>" + formatter.format(order.getTotalCost()));
		        }
		        out.println("</TABLE>\n");
		        String previousServletUrl = (String) session.getAttribute("previousServletUrl");
		        out.println("<form align=\"center\" action=\"" + previousServletUrl + "\">"
		                + "<button type=\"submit\">Back</button>" + "</form>");
		    }

		    out.println("</BODY></HTML>");
		}
		}
	}
