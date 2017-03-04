package edu.gatech.seclass.glm;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import edu.gatech.seclass.glm.fragments.AddListDialogFragment;
import edu.gatech.seclass.glm.fragments.RenameListDialogFragment;
import edu.gatech.seclass.glm.fragments.LogOutFragment;
import edu.gatech.seclass.glm.models.DatabaseManager;
import edu.gatech.seclass.glm.models.GroceryList;

import static android.R.attr.button;

public class ManageListsActivity extends AppCompatActivity {
    public final static String LIST_ID = "edu.gatech.seclass.glm.LIST_ID";
    public final static String LIST_NAME = "edu.gatech.seclass.glm.LIST_NAME";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lists);
        listView = (ListView) findViewById(R.id.groceryListView);
        final ArrayList<String> values = new ArrayList<String>();
        final ArrayList<String> ids = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        DatabaseReference database = DatabaseManager.getInstance().getAllListsForCurrentUserReference();
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroceryList groceryList = dataSnapshot.getValue(GroceryList.class);
                values.add(groceryList.name);
                ids.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                GroceryList groceryList = dataSnapshot.getValue(GroceryList.class);
                int position = ids.indexOf(dataSnapshot.getKey());
                values.set(position, groceryList.name);
                adapter.notifyDataSetChanged();
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
        database.addChildEventListener(childEventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listId = ids.get(position);
                Intent intent = new Intent(ManageListsActivity.this, ListActivity.class);
                intent.putExtra(LIST_ID, listId);
                intent.putExtra(LIST_NAME, values.get(position));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String listId = ids.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("listId", listId);
                DialogFragment renameList = new RenameListDialogFragment();
                renameList.setArguments(bundle);
                renameList.show(getFragmentManager(), "renameList");
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_list_manager_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                DialogFragment addListFragment = new AddListDialogFragment();
                addListFragment.show(getFragmentManager(), "addList");
                return true;
            case R.id.logOut:
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ManageListsActivity.this);
                builder.setMessage("Are you sure you want to log out?")
                        .setTitle("Logging out")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(ManageListsActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null);

                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }

    public void onRestart() {
        super.onRestart();
        Intent i = new Intent(this, ManageListsActivity.class);
        startActivityForResult(i, 1);
    }
}

