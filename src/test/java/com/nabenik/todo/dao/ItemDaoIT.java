package com.nabenik.todo.dao;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nabenik.todo.model.Item;
import static org.junit.Assert.assertNull;

@RunWith(Arquillian.class)
public class ItemDaoIT {
	
	private static Long itemId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "item-dao-test.war")
                .addClasses(ItemDao.class, Item.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }
    
    @EJB
    private ItemDao itemService;
    
    @Test
    @InSequence(1)
    public void testAddItem() {
        // Save a new bid.
        Item item = new Item();
        item.setTitle("Test item");
        item.setDescription("This is a test item");

        itemService.create(item);

        // Make sure it was correctly saved.
        itemId = item.getId();

        item = itemService.findById(itemId);
        assertEquals("Test item", item.getTitle());
    }
    
    @Test
    @InSequence(2)
    public void testModifyItem() {
        // Retrieve from database
        Item item = itemService.findById(itemId);
        item.setDescription("New description");
        itemService.update(item);
        
        // Make sure it was correctly saved.
        itemId = item.getId();
        item = itemService.findById(itemId);
        assertEquals("Test item", item.getTitle());
        assertEquals("New description", item.getDescription());
    }
    
    @Test
    @InSequence(3)
    public void testDeleteItem() {
        itemService.deleteById(itemId);
        
        Item item = itemService.findById(itemId);
        assertNull(item);
    }

}
