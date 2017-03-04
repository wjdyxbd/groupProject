package edu.gatech.seclass.glm.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by sheankim on 10/8/16.
 */
@IgnoreExtraProperties
public class Item {
    public String name;
    public String itemType;
    public String unit;

    public Item() {}

    public Item(String name, String itemType, String unit) {
        this.name = name;
        this.itemType = itemType;
        this.unit = unit;
    }
}
