package edu.gatech.seclass.glm;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import edu.gatech.seclass.glm.fragments.AddItemDialogFragment;
import edu.gatech.seclass.glm.fragments.SearchItemDialogFragment;
import edu.gatech.seclass.glm.models.DatabaseManager;
import edu.gatech.seclass.glm.models.ListItem;

public class ListActivity extends AppCompatActivity {
    private String listID;
    private String listName;
    private ListView listView;
    private TextView listNameView;
    private ItemAdapter itemAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        listID = intent.getStringExtra(ManageListsActivity.LIST_ID);
        listName = intent.getStringExtra(ManageListsActivity.LIST_NAME);

        listView = (ListView)findViewById(R.id.itemList);
        listNameView = (TextView)findViewById(R.id.listName);
        listNameView.setText(listName);
        itemAdapter = new ItemAdapter(this, listID);
        listView.setAdapter(itemAdapter);
        final DatabaseReference database = DatabaseManager.getInstance().getAllItemsForList(listID);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListItem listItem = dataSnapshot.getValue(ListItem.class);
                itemAdapter.addItem(listItem);
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
        database.addChildEventListener(childEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                DialogFragment addItemFragment = new AddItemDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("listID", listID);
                addItemFragment.setArguments(bundle);
                addItemFragment.show(getFragmentManager(), "addItem");
                return true;
            case R.id.search:
                DialogFragment searchItemFragment = new SearchItemDialogFragment();
                Bundle b = new Bundle();
                b.putString("listID", listID);
                searchItemFragment.setArguments(b);
                searchItemFragment.show(getFragmentManager(), "searchItem");
                return true;
            case R.id.checkOff:
                itemAdapter.checkOffItems();
                return true;
            case R.id.del:
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setMessage("Do you want to delete the list?")
                        .setTitle("Delete List")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseManager.getInstance().deleteList(listID);
                                finish();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
