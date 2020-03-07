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
import com.praxello.smartdoctor.model.allmedicine.MedicineData;
import java.util.ArrayList;
import java.util.List;

public class AllMedicineAdapter extends ArrayAdapter<MedicineData> {

    private final Context mContext;
    private final List<MedicineData> medicineTypeList;
    private final List<MedicineData> medicineTypeListAll;
    private final int mLayoutResourceId;

    public AllMedicineAdapter(@NonNull Context context, int resource, List<MedicineData> medicineTypeList) {
        super(context, resource,medicineTypeList);
        this.mContext = context;
        this.medicineTypeList = new ArrayList<>(medicineTypeList);;
        this.mLayoutResourceId = resource;
        this.medicineTypeListAll = new ArrayList<>(medicineTypeList);
    }

    public int getCount() {
        return medicineTypeList.size();
    }

    public MedicineData getItem(int position) {
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
            MedicineData department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textview);
            name.setText(department.getName());

          /*  RxActivity.actType.setText(department.getType());
            RxActivity.actMorning.setText(department.getMorning());
            RxActivity.actEvening.setText(department.getNoon());
            RxActivity.actNight.setText(department.getNight());
            RxActivity.actNotesInstruction.setText(department.getInstruction());
*/
            name.setOnClickListener(v -> {
                RxActivity.actMedicine.setText(department.getName());
                RxActivity.actType.setText(department.getType());
                RxActivity.actMorning.setText(department.getMorning());
                RxActivity.actEvening.setText(department.getNoon());
                RxActivity.actNight.setText(department.getNight());
                RxActivity.actNotesInstruction.setText(department.getInstruction());
                RxActivity.actMedicine.dismissDropDown();
            });
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
                return ((MedicineData) resultValue).getName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<MedicineData> medicineTypeListSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (MedicineData medicineType : medicineTypeListAll) {
                        if (medicineType.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
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
                        if (object instanceof MedicineData) {
                            medicineTypeList.add((MedicineData) object);
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