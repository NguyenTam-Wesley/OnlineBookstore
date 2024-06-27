package com.nctam.servlet;

import javax.servlet.annotation.WebServlet;

@WebServlet("/PhilosophyBooks")
public class Philosophy_Books extends CatalogPage {
    private static final long serialVersionUID = 1L;

    public void init() {
        String[] ids = { "philosophy01", "philosophy02", "philosophy03" };
        setItems(ids);
        setTitle("Philosophy books");
    }
}
