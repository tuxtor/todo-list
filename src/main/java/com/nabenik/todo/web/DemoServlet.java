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
 *
 * @author tuxtor
 */
@WebServlet(name = "DemoServlet", urlPatterns = {"/DemoServlet"})
public class DemoServlet extends HttpServlet {
    
    private static final Logger logger = Logger
            .getLogger(ItemServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("application/json");
	PrintWriter out = response.getWriter();
	String name = request.getParameter("name");
        
        
        logger.log(Level.INFO, "generando saludos para: {0}", name);
        try (JsonGenerator generator = Json.createGenerator(out)) {
			
            generator.writeStartObject();
            generator.write("saludo", "Hola "+name);
            generator.writeEnd();
            generator.close();
        }
    }
}
