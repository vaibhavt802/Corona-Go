package com.example.coronago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter {

    private Context context;
    private List<StatesModel> statesModelList;
    private List<StatesModel> statesModelListFiltered;

    public MyCustomAdapter(@NonNull Context context, List<StatesModel> statesModelList) {
        super(context, R.layout.list_custom_item, statesModelList);
        this.context = context;
        this.statesModelList = statesModelList;
        this.statesModelListFiltered = statesModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView stateName = view.findViewById(R.id.tvstate);
        stateName.setText(statesModelListFiltered.get(position).getState());

        return view;
    }

    @Override
    public int getCount() {
        return statesModelListFiltered.size();
    }

    @Nullable
    @Override
    public StatesModel getItem(int position) {
        return statesModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0) {
                    filterResults.count = statesModelList.size();
                    filterResults.values = statesModelList;
                }
                else {
                    List<StatesModel> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();
                    for (StatesModel itemsModel:statesModelList){
                        if (itemsModel.getState().toLowerCase().contains(searchStr)){
                            resultModel.add(itemsModel);
                        }
                        filterResults.count = resultModel.size();
                        filterResults.values = resultModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                statesModelListFiltered = (List<StatesModel>) results.values;
                AffectedStates.statesModelList = (List<StatesModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
