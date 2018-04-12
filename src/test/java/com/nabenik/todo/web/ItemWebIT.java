/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabenik.todo.web;

import com.nabenik.todo.dao.ItemDao;
import com.nabenik.todo.model.Item;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author tuxtor
 */
@RunWith(Arquillian.class)
public class ItemWebIT {
    
    @ArquillianResource
    URL contextPath;

    @Drone
    WebDriver browser;

    private static final String WEBAPP_SRC = "src/main/webapp";
    /**
     * Creates a testing WAR of using ShrinkWrap
     *
     * @return WebArchive to be tested
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "todo-list.war")
                // add classes
                .addClasses(ItemDao.class, Item.class, ItemManagedBean.class)
                // add configuration
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-faces-config.xml", "faces-config.xml")
                // add pages
                .addAsWebInfResource("test-web.xml", "web.xml")
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                    .importDirectory(WEBAPP_SRC).as(GenericArchive.class),
                    "/", Filters.include(".*\\.xhtml$"))
                ;
    }

    @Test
    public void testAddBid() {
        browser.navigate().to(contextPath);
        System.out.println(browser.getPageSource());

        browser.findElement(By.id("itemForm:titleField")).sendKeys("Test item");
        browser.findElement(By.id("itemForm:descriptionField")).sendKeys("Victor orozco");

        browser.findElement(By.id("itemForm:addUserButton")).click();

        assertEquals("Test item", browser.findElement(By.id("titleField")).getText());
        assertEquals("Victor orozco", browser.findElement(By.id("descriptionField")).getText());
    }
}
