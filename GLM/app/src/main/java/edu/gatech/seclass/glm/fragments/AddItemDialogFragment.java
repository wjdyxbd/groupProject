package edu.gatech.seclass.glm.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.models.DatabaseManager;
import edu.gatech.seclass.glm.models.Item;
import edu.gatech.seclass.glm.models.ListItem;

import static edu.gatech.seclass.glm.R.id.itemNameEditText;

/**
 * Created by sheankim on 10/11/16.
 */

public class AddItemDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String listId = getArguments().getString("listID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_list_item, null);

        final AutoCompleteTextView nameEditText = (AutoCompleteTextView)dialogView.findViewById(itemNameEditText);


        final Spinner itemTypeSpinner = (Spinner)dialogView.findViewById(R.id.itemTypeSpinner);
        ArrayAdapter<String> itemTypeAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.custom_spinner_item, DatabaseManager.getInstance().itemTypes);
        itemTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeSpinner.setAdapter(itemTypeAdapter);

        itemTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItemType = itemTypeSpinner.getSelectedItem().toString();
                ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, DatabaseManager.getInstance().findItemNamesByItemType(selectedItemType));
                nameEditText.setAdapter(itemAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });


        final EditText quantityEditText = (EditText)dialogView.findViewById(R.id.quanityEditText);

        final Spinner itemUnitSpinner = (Spinner)dialogView.findViewById(R.id.itemUnitSpinner);
        final ArrayAdapter<String> unitTypeAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.custom_spinner_item, DatabaseManager.getInstance().unitTypes);
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemUnitSpinner.setAdapter(unitTypeAdapter);

        builder.setView(dialogView)
                .setPositiveButton("Create item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Item item = new Item(nameEditText.getText().toString(), itemTypeSpinner.getSelectedItem().toString(), itemUnitSpinner.getSelectedItem().toString());
                        Double quantity;
                        try {
                            quantity = Double.parseDouble(quantityEditText.getText().toString());
                        } catch (NumberFormatException e) {
                            quantity = 0.0;
                        }
                        ListItem listItem = new ListItem(item, quantity, false);
                        DatabaseManager.getInstance().createNewItemForList(listId, listItem);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddItemDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
