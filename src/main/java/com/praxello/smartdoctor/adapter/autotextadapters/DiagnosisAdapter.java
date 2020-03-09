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
import com.praxello.smartdoctor.model.getalldiagnosis.AllDiagnosisData;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisAdapter extends ArrayAdapter<AllDiagnosisData> {

    private final Context mContext;
    private final List<AllDiagnosisData> allDiagnosisDataList;
    private final List<AllDiagnosisData> allDiagnosisDataListAll;
    private final int mLayoutResourceId;

    public DiagnosisAdapter(@NonNull Context context, int resource, List<AllDiagnosisData> allDiagnosisDataList) {
        super(context, resource,allDiagnosisDataList);
        this.mContext = context;
        this.allDiagnosisDataList = new ArrayList<>(allDiagnosisDataList);;
        this.mLayoutResourceId = resource;
        this.allDiagnosisDataListAll = new ArrayList<>(allDiagnosisDataList);
    }

    public int getCount() {
        return allDiagnosisDataList.size();
    }

    public AllDiagnosisData getItem(int position) {
        return allDiagnosisDataList.get(position);
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
            AllDiagnosisData department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textview);
            name.setText(department.getDiagnosis());

          /*  RxActivity.actType.setText(department.getType());
            RxActivity.actMorning.setText(department.getMorning());
            RxActivity.actEvening.setText(department.getNoon());
            RxActivity.actNight.setText(department.getNight());
            RxActivity.actNotesInstruction.setText(department.getInstruction());
*/

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
                return ((AllDiagnosisData) resultValue).getDiagnosis();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<AllDiagnosisData> allDiagnosisDataListSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (AllDiagnosisData medicineType : allDiagnosisDataListAll) {
                        if (medicineType.getDiagnosis().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            allDiagnosisDataListSuggestion.add(medicineType);
                        }
                    }
                    filterResults.values = allDiagnosisDataListSuggestion;
                    filterResults.count = allDiagnosisDataListSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                allDiagnosisDataList.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof AllDiagnosisData) {
                            allDiagnosisDataList.add((AllDiagnosisData) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    allDiagnosisDataList.addAll(allDiagnosisDataListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
