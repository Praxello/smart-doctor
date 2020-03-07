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
import com.praxello.smartdoctor.model.alldosage.DosageData;
import java.util.ArrayList;
import java.util.List;

public class DosageAdapter extends ArrayAdapter<DosageData> {

    private final Context mContext;
    private final List<DosageData> dosageDataList;
    private final List<DosageData> dosageDataListAll;
    private final int mLayoutResourceId;

    public DosageAdapter(@NonNull Context context, int resource, List<DosageData> dosageDataList) {
        super(context, resource,dosageDataList);
        this.mContext = context;
        this.dosageDataList = new ArrayList<>(dosageDataList);;
        this.mLayoutResourceId = resource;
        this.dosageDataListAll = new ArrayList<>(dosageDataList);
    }

    public int getCount() {
        return dosageDataList.size();
    }

    public DosageData getItem(int position) {
        return dosageDataList.get(position);
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
            DosageData department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textview);
            name.setText(department.getDosage());
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
                return ((DosageData) resultValue).getDosage();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<DosageData> dosageDataListSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (DosageData medicineType : dosageDataListAll) {
                        if (medicineType.getDosage().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            dosageDataListSuggestion.add(medicineType);
                        }
                    }
                    filterResults.values = dosageDataListSuggestion;
                    filterResults.count = dosageDataListSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dosageDataList.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof DosageData) {
                            dosageDataList.add((DosageData) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    dosageDataList.addAll(dosageDataListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}