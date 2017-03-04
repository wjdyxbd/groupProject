package edu.gatech.seclass.glm.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by sheankim on 10/8/16.
 */
@IgnoreExtraProperties
public class GroceryList {
    public String name;
    public String id;

    public GroceryList() {}

    public GroceryList(String name) {
        this.name = name;
    }
}
