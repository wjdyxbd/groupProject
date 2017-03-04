package edu.gatech.seclass.glm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import edu.gatech.seclass.glm.models.DatabaseManager;
import edu.gatech.seclass.glm.models.ListItem;

/**
 * Created by sheankim on 10/10/16.
 */

public class ItemAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_TYPE = 1;

    private HashMap<String, ArrayList<ListItem>> mData = new HashMap<String, ArrayList<ListItem>>();
    private ArrayList<String> types = new ArrayList<>();
    private HashMap<String, Integer> positions = new HashMap<String, Integer>();
    private LayoutInflater inflater;
    private String listID;
    private List<CheckBox> checkedBoxes =new ArrayList<CheckBox>();

    public ItemAdapter(Context context, String listID) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listID = listID;
    }

    public void addItem(ListItem listItem) {
        if(mData.containsKey(listItem.item.itemType)) {
            mData.get(listItem.item.itemType).add(listItem);
        } else {
            ArrayList<ListItem> newList = new ArrayList<ListItem>();
            newList.add(listItem);
            mData.put(listItem.item.itemType, newList);
            types.add(listItem.item.itemType);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int totalCount = 0;
        for (ArrayList<ListItem> item : mData.values()) {
            totalCount += item.size();
        }
        return totalCount + mData.keySet().size();
    }

    @Override
    public Object getItem(int position) {
        ArrayList<Object> flattened = new ArrayList<Object>();
        for(String type : types) {
            flattened.add(type);
            for(ListItem item : mData.get(type)) {
                flattened.add(item);
            }
        }
        return flattened.get(position);
    }


    public void deleteItem(ListItem listItem) {
        mData.get(listItem.item.itemType).remove(listItem);
        notifyDataSetChanged();
    }

    public void checkOffItems(){

        Collection<ArrayList<ListItem>> mValues = mData.values();
        for (ArrayList<ListItem> listItems : mValues) {
            for (ListItem listItem : listItems) {
                listItem.checked=false;
                DatabaseManager.getInstance().updateListItem(listItem.id, listID, listItem);
            }
        }

        notifyDataSetChanged();
        clearBoxes();

    }

    private void clearBoxes() {
        for (CheckBox checkedBox : checkedBoxes) {
            checkedBox.setChecked(false);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof  ListItem) {
            return TYPE_ITEM;
        }
        return TYPE_ITEM_TYPE;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_ITEM_TYPE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_ITEM_TYPE:
                convertView = inflater.inflate(R.layout.item_type_header, parent, false);
                String itemType = (String) getItem(position);
                TextView itemTypeView = (TextView)convertView.findViewById(R.id.itemTypeHeader);
                itemTypeView.setText(itemType);
                break;
            case TYPE_ITEM:
                convertView = inflater.inflate(R.layout.item_row, parent, false);
                final ListItem listItem = (ListItem) getItem(position);
                final CheckBox completedView = (CheckBox)convertView.findViewById(R.id.completeBox);
                completedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            checkedBoxes.add(completedView);
                        }
                        listItem.checked = isChecked;
                        DatabaseManager.getInstance().updateListItem(listItem.id, listID, listItem);
                    }
                });
                ImageButton deleteItemButton = (ImageButton)convertView.findViewById(R.id.deleteItem);
                deleteItemButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        deleteItem(listItem);
                        DatabaseManager.getInstance().deleteListItem(listItem.id,listID);
                    }
                });

                TextView itemNameView = (TextView)convertView.findViewById(R.id.itemNameTextView);
                TextView itemQuantityView = (TextView)convertView.findViewById(R.id.quantityTextView);
                completedView.setChecked(listItem.checked);
                itemNameView.setText(listItem.item.name);
                itemQuantityView.setText(listItem.quantity + " " + listItem.item.unit);
                break;
        }
        return convertView;
    }
}
