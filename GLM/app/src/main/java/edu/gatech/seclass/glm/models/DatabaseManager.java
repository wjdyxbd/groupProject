package edu.gatech.seclass.glm.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sheankim on 10/8/16.
 */
public class DatabaseManager {
    private DatabaseReference mDatabase;
    private static DatabaseManager instance = null;
    public ArrayList<String> itemTypes = new ArrayList<>();
    public ArrayList<String> unitTypes = new ArrayList<>();
    public ArrayList<Item> items = new ArrayList<>();

    private DatabaseManager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemTypesDatabase = mDatabase.child("itemTypes");
        DatabaseReference itemsDatabase = mDatabase.child("items");
        DatabaseReference unitTypesDatabase = mDatabase.child("units");
        ChildEventListener itemTypesChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                itemTypes.add(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ChildEventListener itemsChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Item item = new Item(((HashMap) dataSnapshot.getValue()).get("name").toString(), ((HashMap) dataSnapshot.getValue()).get("itemType").toString(), "unit");
                items.add(item);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ChildEventListener unitTypesChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                unitTypes.add(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        itemTypesDatabase.addChildEventListener(itemTypesChildEventListener);
        itemsDatabase.addChildEventListener(itemsChildEventListener);
        unitTypesDatabase.addChildEventListener(unitTypesChildEventListener);
    }

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void createNewList(GroceryList groceryList) {
        String listID = mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groceryLists").push().getKey();
        groceryList.id = listID;
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groceryLists").child(listID).setValue(groceryList);
    }

    public DatabaseReference getAllListsForCurrentUserReference() {
        return mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groceryLists");
    }

    public void createNewItemForList(String listID, ListItem listItem) {
        String itemID = mDatabase.child("listItems").child(listID).push().getKey();
        listItem.id = itemID;
        mDatabase.child("listItems").child(listID).child(itemID).setValue(listItem);
    }

    public void updateListItem(String itemID, String listID, ListItem listItem) {
        mDatabase.child("listItems").child(listID).child(itemID).setValue(listItem);
    }

    public DatabaseReference getAllItemsForList(String listID) {
        return mDatabase.child("listItems").child(listID);
    }

    public void renameList(String listID, String name) {
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groceryLists").child(listID).child("name").setValue(name);
    }

    public void deleteList(String listID) {
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groceryLists").child(listID).removeValue();
    }

    public void deleteListItem(String itemID, String listID) {
        mDatabase.child("listItems").child(listID).child(itemID).removeValue();
    }
    public List<String> findItemNamesByItemType(String itemType) {
        List<String> itemNames = new ArrayList<>();
        for (Item item : items) {
            if(item.itemType.equalsIgnoreCase(itemType))
                itemNames.add(item.name);
        }
        return itemNames;
    }

}
