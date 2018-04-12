/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabenik.todo.web;

import com.nabenik.todo.dao.ItemDao;
import com.nabenik.todo.model.Item;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

@ManagedBean
@SessionScoped
public class ItemManagedBean implements Serializable {

    @Inject
    ItemDao itemService;
    
    private Item item;
        
    @PostConstruct
    public void init() { 
        item = new Item();
    }
    
    public Item getItem() {
		return item;
    }
        
    public String addUser(){
        System.out.println("Saving " + item);
        itemService.create(item);
        return "confirm-item.xhtml";
    }
}