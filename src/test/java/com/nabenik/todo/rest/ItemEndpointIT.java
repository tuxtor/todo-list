/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabenik.todo.rest;

import com.nabenik.todo.dao.ItemDao;
import com.nabenik.todo.model.Item;
import com.nabenik.todo.util.TestConfig;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public class ItemEndpointIT {
    private static Long itemId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "todo-list-rest-test.war")
                .addClasses(ItemEndpoint.class, RestApplication.class,
                        ItemDao.class, Item.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }
//
    @Test
    @InSequence(1)
    public void testAddBid() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/todo-list-rest-test/rest/items");
        // Save a new item.
        Item item = new Item();

        item.setTitle("Rest item");
        item.setDescription("Item over rest");

        item = target.request("application/json").post(Entity.json(item), Item.class);
        itemId = item.getId();

        // Make sure it was correctly saved.
        item = target.path("{id}").resolveTemplate("id", itemId)
                .request("application/json").get(Item.class);

        assertEquals("Rest item", item.getTitle());
    }

    @Test
    @InSequence(2)
    public void testUpdateItem() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/todo-list-rest-test/rest/items/{id}")
                .resolveTemplate("id", itemId);

        // Update item.
        Item item = target.request("application/json").get(Item.class);

        item.setDescription("Updated description");

        target.request().put(Entity.json(item));

        // Make sure item was updated.
        item = target.request("application/json").get(Item.class);

        assertEquals("Rest item", item.getTitle());
        assertEquals("Updated description", item.getDescription());
    }

    @Test
    @InSequence(3)
    public void testDeleteBid() {
        WebTarget target = ClientBuilder.newClient()
                .target(TestConfig.TEST_BASE_URL + "/todo-list-rest-test/rest/items/{id}")
                .resolveTemplate("id", itemId);
        System.out.println(itemId);
        target.request().delete();
        Item item = null;
        try {
            item = target.request("application/json").get(Item.class);
        } catch (NotFoundException e) {
        }
        assertNull(item);
    }
    
}
