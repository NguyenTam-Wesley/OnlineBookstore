package com.nctam.servlet;


import javax.servlet.annotation.WebServlet;

@WebServlet("/TWMFLW_Books")
public class TWMFLW_Books extends CatalogPage {
    private static final long serialVersionUID = 1L;

    public void init() {
        String[] ids = { "twmflw01", "twmflw02", "twmflw03" };
        setItems(ids);
        setTitle("The World's Most Famous Literary Works");
    }

	
}
