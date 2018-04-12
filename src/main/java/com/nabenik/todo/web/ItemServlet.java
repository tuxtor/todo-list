package com.nabenik.todo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Item
 */
@WebServlet(name = "ItemServlet", urlPatterns = {"/items"})
public class ItemServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger
            .getLogger(ItemServlet.class.getName());
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		long userId = Long.parseLong(request.getParameter("user_id"));
		logger.log(Level.INFO, "Generating alerts for: {0}", userId);
		try (JsonGenerator generator = Json.createGenerator(out)) {
			
            generator.writeStartObject();
            generator.write("greeting", "Welcome, will sending available items");
            generator.writeStartArray("items");

            String[] items = {"Item 1", "Item 2",
                "Item N"};

            for (String item : items) {
                generator
                        .writeStartObject()
                        .write("user", userId)
                        .write("text", item)
                        .writeEnd();
            }

            generator.writeEnd();

            generator.write("goodbye", "No more alerts for now, timing out");

            generator.writeEnd();
            generator.close();
        }
	}

}
