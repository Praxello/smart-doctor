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
import com.praxello.smartdoctor.model.getallcomplaints.GetAllComplaintsData;
import java.util.ArrayList;
import java.util.List;

public class GetAllComplaintsAdapter extends ArrayAdapter<GetAllComplaintsData> {

    private final Context mContext;
    private final List<GetAllComplaintsData> getAllComplaintsList;
    private final List<GetAllComplaintsData> getAllComplaintsListAll;
    private final int mLayoutResourceId;

    public GetAllComplaintsAdapter(@NonNull Context context, int resource, List<GetAllComplaintsData> getAllComplaintsList) {
        super(context, resource,getAllComplaintsList);
        this.mContext = context;
        this.getAllComplaintsList = new ArrayList<>(getAllComplaintsList);;
        this.mLayoutResourceId = resource;
        this.getAllComplaintsListAll = new ArrayList<>(getAllComplaintsList);
    }

    public int getCount() {
        return getAllComplaintsList.size();
    }

    public GetAllComplaintsData getItem(int position) {
        return getAllComplaintsList.get(position);
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
            GetAllComplaintsData department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textview);
            name.setText(department.getComplaint());

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
                return ((GetAllComplaintsData) resultValue).getComplaint();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<GetAllComplaintsData> getAllComplaintsListSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (GetAllComplaintsData medicineType : getAllComplaintsListAll) {
                        if (medicineType.getComplaint().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            getAllComplaintsListSuggestion.add(medicineType);
                        }
                    }
                    filterResults.values = getAllComplaintsListSuggestion;
                    filterResults.count = getAllComplaintsListSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                getAllComplaintsList.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof GetAllComplaintsData) {
                            getAllComplaintsList.add((GetAllComplaintsData) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    getAllComplaintsList.addAll(getAllComplaintsListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}