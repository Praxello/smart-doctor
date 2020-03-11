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
import com.praxello.smartdoctor.model.getAdvice.GetAdviceData;

import java.util.ArrayList;
import java.util.List;

public class GetAdviceAdapter extends ArrayAdapter<GetAdviceData> {

    private final Context mContext;
    private final List<GetAdviceData> getAdviceDataList;
    private final List<GetAdviceData> getAdviceDataListAll;
    private final int mLayoutResourceId;

    public GetAdviceAdapter(@NonNull Context context, int resource, List<GetAdviceData> getAdviceDataList) {
        super(context, resource,getAdviceDataList);
        this.mContext = context;
        this.getAdviceDataList = new ArrayList<>(getAdviceDataList);;
        this.mLayoutResourceId = resource;
        this.getAdviceDataListAll = new ArrayList<>(getAdviceDataList);
    }

    public int getCount() {
        return getAdviceDataList.size();
    }

    public GetAdviceData getItem(int position) {
        return getAdviceDataList.get(position);
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
            GetAdviceData department = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.textview);
            name.setText(department.getAdvice());

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
                return ((GetAdviceData) resultValue).getAdvice();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<GetAdviceData> getAdviceDataListSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (GetAdviceData getAdviceData : getAdviceDataListAll) {
                        if (getAdviceData.getAdvice().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            getAdviceDataListSuggestion.add(getAdviceData);
                        }
                    }
                    filterResults.values = getAdviceDataListSuggestion;
                    filterResults.count = getAdviceDataListSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                getAdviceDataList.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof GetAdviceData) {
                            getAdviceDataList.add((GetAdviceData) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    getAdviceDataList.addAll(getAdviceDataListAll);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}