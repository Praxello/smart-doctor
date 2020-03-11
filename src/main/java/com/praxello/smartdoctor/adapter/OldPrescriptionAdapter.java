package com.praxello.smartdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.model.getallprescription.AllPrescriptionData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OldPrescriptionAdapter extends RecyclerView.Adapter<OldPrescriptionAdapter.OldPrescriptinViewHolder> {

    Context context;
    ArrayList<AllPrescriptionData> allPrescriptionDataArrayList;

    public OldPrescriptionAdapter(Context context, ArrayList<AllPrescriptionData> allPrescriptionDataArrayList) {
        this.context = context;
        this.allPrescriptionDataArrayList = allPrescriptionDataArrayList;
    }

    @NonNull
    @Override
    public OldPrescriptinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.layout_old_prescription,parent,false);
        return new OldPrescriptinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OldPrescriptinViewHolder holder, int position) {
        holder.tvDrName.setText(allPrescriptionDataArrayList.get(position).getFirstName()+" "+allPrescriptionDataArrayList.get(position).getLastName());
        holder.tvComplaint.setText(allPrescriptionDataArrayList.get(position).getComplaint());
        holder.tvDiagnosis.setText(allPrescriptionDataArrayList.get(position).getDiagnosis());
        holder.tvRemark.setText(allPrescriptionDataArrayList.get(position).getAdvice());

        try{
            String startDate = allPrescriptionDataArrayList.get(position).getNextVisitDate();
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try {
                newDate = spf.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat(" EEE, dd MMM yy");
            startDate = spf.format(newDate);
            holder.tvNextVisitDate.setText("Next visit date "+startDate);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            String visitDate = allPrescriptionDataArrayList.get(position).getVisitDate();
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try {
                newDate = spf.parse(visitDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spf = new SimpleDateFormat(" EEE, dd MMM yy");
            visitDate = spf.format(newDate);
            holder.tvVisitDate.setText(visitDate);
        }catch (Exception e){
            e.printStackTrace();
        }



        if(allPrescriptionDataArrayList.get(position).getMedicines()!=null){
            GetAllMedicines getAllMedicines=new GetAllMedicines(context,allPrescriptionDataArrayList.get(position).getMedicines());
            holder.rvMedicine.setAdapter(getAllMedicines);
        }
    }

    @Override
    public int getItemCount() {
        return allPrescriptionDataArrayList.size();
    }

    public class OldPrescriptinViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_drname)
        TextView tvDrName;
        @BindView(R.id.tv_complaint)
        TextView tvComplaint;
        @BindView(R.id.tv_diagnosis)
        TextView tvDiagnosis;
        @BindView(R.id.rvMedicine)
        RecyclerView rvMedicine;
        @BindView(R.id.tv_nextvisitdate)
        TextView tvNextVisitDate;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.tv_visitdate)
        TextView tvVisitDate;

        public OldPrescriptinViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            rvMedicine.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
