package com.nctam.servlet;

import javax.servlet.annotation.WebServlet;

@WebServlet("/ScienceBooks")
public class Science_Books extends CatalogPage {
    private static final long serialVersionUID = 1L;

    public void init() {
        String[] ids = { "science01", "science02", "science03" };
        setItems(ids);
        setTitle("Top of science books");
    }

	
}
