package com.nctam.servlet;

	import java.io.*;
	import javax.servlet.*;
	import javax.servlet.http.*;

	public abstract class CatalogPage extends HttpServlet {
		private static final long serialVersionUID = 1L;

		private CatalogItem[] items;
		private String[] itemIDs;
		private String title;

		protected void setItems(String[] itemIDs) {
			this.itemIDs = itemIDs;
			items = new CatalogItem[itemIDs.length];
			for (int i = 0; i < items.length; i++) {
				items[i] = Catalog.getItem(itemIDs[i]);
			}
		}

		protected void setTitle(String title) {
			this.title = title;
		}

		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if (items == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Missing Items.");
				return;
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
			out.println(docType + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE>"
					+ "<style>"
			        + "body {"
			        + " background-image: url('http://localhost:8080/OBS/data/cart.jpg');"
			        + " background-size: cover;"
			        + " background-position: center;"
			        + "}"
			        + ".home-icon {"
			        + " position: absolute;"
			        + " top: 20px;"
			        + " left: 20px;"
			        + "}"
			        + ".cart-icon {"
			        + " position: absolute;"
			        + " top: 20px;"
			        + " right: 20px;"
			        + "}"
			        + "</style>"
				    + "<a href=\"index.html\" class=\"home-icon\">Home</a>"
					+ "<a href=\"/OBS/OrderPage\" class=\"cart-icon\">Cart</a>" + "<H1 ALIGN=\"CENTER\">"
					+ title + "</H1>");
			CatalogItem item;
			for (int i = 0; i < items.length; i++) {
				out.println("<HR>");
				item = items[i];
				if (item == null) {
					out.println("<FONT COLOR=\"RED\">" + "Unknown item ID " + itemIDs[i] + "</FONT>");
				} else {
					out.println();
					String formURL = "OrderPage";
					formURL = response.encodeURL(formURL);
					out.println("<FORM ACTION=\"" + formURL + "\">\n" + "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" "
							+ " VALUE=\"" + item.getItemID() + "\">\n" + "<H2>" + item.getShortDescription() + " ($"
							+ item.getCost() + ")</H2>\n" + item.getLongDescription() + "\n" + "<P>\n<CENTER>\n"
							+ "<INPUT TYPE=\"SUBMIT\" " + "VALUE=\"Add to Shopping Cart\">\n" + "</CENTER>\n<P>\n</FORM>");
				}
			}
			String currentServletUrl = request.getRequestURL().toString();
			HttpSession session = request.getSession();
			session.setAttribute("previousServletUrl", currentServletUrl);
			out.println("<HR>\n</BODY></HTML>");
		}
	}

