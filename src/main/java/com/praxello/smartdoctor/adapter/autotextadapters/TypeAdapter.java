package com.praxello.smartdoctor.adapter.autotextadapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.activity.RxActivity;
import com.praxello.smartdoctor.model.medicinetype.MedicineType;

import java.util.ArrayList;
import java.util.List;


public class TypeAdapter extends ArrayAdapter<MedicineType> {

    private final Context mContext;
    private final List<MedicineType> medicineTypeList;
    private final List<MedicineType> medicineTypeListAll;
    private final int mLayoutResourceId;

    public TypeAdapter(@NonNull Context context, int resource, List<MedicineType> medicineTypeList) {
        super(context, resource,medicineTypeList);
        this.mContext = context;
        this.medicineTypeList = new ArrayList<>(medicineTypeList);;
        this.mLayoutResourceId = resource;
        this.medicineTypeListAll = new ArrayList<>(medicineTypeList);
    }

    public int getCount() {
        return medicineTypeList.size();
    }

    public MedicineType getItem(int position) {
        return medicineTypeList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            MedicineType department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textview);
            name.setText(department.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((MedicineType) resultValue).getType();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<MedicineType> medicineTypeListSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (MedicineType medicineType : medicineTypeListAll) {
                        if (medicineType.getType().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            medicineTypeListSuggestion.add(medicineType);
                        }
                    }
                    filterResults.values = medicineTypeListSuggestion;
                    filterResults.count = medicineTypeListSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                medicineTypeList.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof MedicineType) {
                            medicineTypeList.add((MedicineType) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    medicineTypeList.addAll(medicineTypeListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}



