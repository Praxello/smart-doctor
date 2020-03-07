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
import com.praxello.smartdoctor.model.allinstruction.InstructionData;

import java.util.ArrayList;
import java.util.List;

public class InstructionAdapter extends ArrayAdapter<InstructionData> {

    private final Context mContext;
    private final List<InstructionData> instructionTypeList;
    private final List<InstructionData> instructionTypeListAll;
    private final int mLayoutResourceId;

    public InstructionAdapter(@NonNull Context context, int resource, List<InstructionData> instructionTypeList) {
        super(context, resource,instructionTypeList);
        this.mContext = context;
        this.instructionTypeList = new ArrayList<>(instructionTypeList);;
        this.mLayoutResourceId = resource;
        this.instructionTypeListAll = new ArrayList<>(instructionTypeList);
    }

    public int getCount() {
        return instructionTypeList.size();
    }

    public InstructionData getItem(int position) {
        return instructionTypeList.get(position);
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
            InstructionData department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textview);
            name.setText(department.getInstruction());
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
                return ((InstructionData) resultValue).getInstruction();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<InstructionData> instructionTypeListSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (InstructionData medicineType : instructionTypeListAll) {
                        if (medicineType.getInstruction().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            instructionTypeListSuggestion.add(medicineType);
                        }
                    }
                    filterResults.values = instructionTypeListSuggestion;
                    filterResults.count = instructionTypeListSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                instructionTypeList.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof InstructionData) {
                            instructionTypeList.add((InstructionData) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    instructionTypeList.addAll(instructionTypeListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
