package com.nabenik.todo.web;

import com.nabenik.todo.util.TestConfig;
import static org.junit.Assert.*;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ItemServletIT {

	@Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "todo-list-servlet-test.war")
                .addClass(ItemServlet.class);
    }
	@Before
	public void setUp() throws Exception {
	}

	@Test
    public void testGetAlerts() {
        Client client = ClientBuilder.newClient();

        // Get account balance
        JsonObject response = client
                .target(TestConfig.TEST_BASE_URL + "/todo-list-servlet-test/items")
                .queryParam("user_id", "99").request("application/json")
                .get(JsonObject.class);
        // TODO Assert more of the content.
        assertEquals(3, response.getJsonArray("items").size());
    }

}
