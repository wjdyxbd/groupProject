package edu.gatech.seclass.glm.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.models.DatabaseManager;
import edu.gatech.seclass.glm.models.GroceryList;
import edu.gatech.seclass.glm.models.ListItem;

/**
 * Created by sheankim on 10/8/16.
 */
public class LogOutFragment extends DialogFragment{
    private FirebaseAuth mFirebaseAuth;
    private Button signOutUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int logOutCounter = 0;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_log_out, null);
        mFirebaseAuth = FirebaseAuth.getInstance();
        builder.setView(dialogView)
                .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFirebaseAuth.signOut();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogOutFragment.this.getDialog().cancel();
                    }
                });
       return builder.create();
    }
}
