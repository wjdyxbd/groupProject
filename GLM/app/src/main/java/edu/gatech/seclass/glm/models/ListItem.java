package edu.gatech.seclass.glm.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by sheankim on 10/8/16.
 */
@IgnoreExtraProperties
public class ListItem {
    public Item item;
    public Double quantity;
    public Boolean checked;
    public String id;

    public ListItem() {}

    public ListItem(Item item, Double quantity, Boolean checked) {
        this.item = item;
        this.quantity = quantity;
        this.checked = checked;
    }



}
