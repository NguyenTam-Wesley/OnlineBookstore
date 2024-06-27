package com.nctam.servlet;


import javax.servlet.annotation.WebServlet;

@WebServlet("/NNA_Books")
public class NNA_Books extends CatalogPage {
    private static final long serialVersionUID = 1L;

    public void init() {
        String[] ids = { "nna01", "nna02", "nna03" };
        setItems(ids);
        setTitle("Featured Nguyen Nhat Anh");
    }
}
