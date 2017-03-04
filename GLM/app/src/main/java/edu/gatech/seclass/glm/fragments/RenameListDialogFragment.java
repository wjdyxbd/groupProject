package edu.gatech.seclass.glm.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.models.DatabaseManager;

/**
 * Created by Mehari on 10/12/16.
 */

public class RenameListDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_list, null);
        final EditText nameEditText = (EditText)dialogView.findViewById(R.id.nameEditText);
        builder.setView(dialogView)
                .setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String listId = getArguments().getString("listId");
                        DatabaseManager.getInstance().renameList(listId,nameEditText.getText().toString().trim());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RenameListDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
