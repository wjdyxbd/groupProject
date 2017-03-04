package edu.gatech.seclass.glm.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.models.DatabaseManager;
import edu.gatech.seclass.glm.models.GroceryList;
import edu.gatech.seclass.glm.models.ListItem;

/**
 * Created by sheankim on 10/8/16.
 */
public class AddListDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_list, null);
        final EditText nameEditText = (EditText)dialogView.findViewById(R.id.nameEditText);
        builder.setView(dialogView)
                .setPositiveButton("Create list", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GroceryList groceryList = new GroceryList(nameEditText.getText().toString().trim());
                        DatabaseManager.getInstance().createNewList(groceryList);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddListDialogFragment.this.getDialog().cancel();
                    }
                });
       return builder.create();
    }
}
