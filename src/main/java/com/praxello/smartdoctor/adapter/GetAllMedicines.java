package com.praxello.smartdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.model.getallprescription.MedicinesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetAllMedicines  extends RecyclerView.Adapter<GetAllMedicines.GetAllMedicinesViewHolder> {

    Context context;
    ArrayList<MedicinesData> medicinesDataArrayList;

    public GetAllMedicines(Context context, ArrayList<MedicinesData> medicinesDataArrayList) {
        this.context = context;
        this.medicinesDataArrayList = medicinesDataArrayList;
    }


    @NonNull
    @Override
    public GetAllMedicines.GetAllMedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.layout_add_prescription_row,parent,false);
        return new GetAllMedicines.GetAllMedicinesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllMedicines.GetAllMedicinesViewHolder holder, int position) {
        holder.ivDelete.setVisibility(View.GONE);
        holder.ivEdit.setVisibility(View.GONE);
        holder.tvMedicineName.setText(medicinesDataArrayList.get(position).getType()+". "+ medicinesDataArrayList.get(position).getName());
        holder.tvMorning.setText(medicinesDataArrayList.get(position).getMorning()+"-"+ medicinesDataArrayList.get(position).getEvining()+"-"+ medicinesDataArrayList.get(position).getNight());
        holder.tvDuration.setText(medicinesDataArrayList.get(position).getPeriod()+" days");
        holder.tvInstruction.setText(medicinesDataArrayList.get(position).getInstruction());
    }

    @Override
    public int getItemCount() {
        return medicinesDataArrayList.size();
    }

    public class GetAllMedicinesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_medicine_name)
        TextView tvMedicineName;
        @BindView(R.id.tv_morning)
        TextView tvMorning;
        @BindView(R.id.tv_duration)
        TextView tvDuration;
        @BindView(R.id.tv_instruction)
        TextView tvInstruction;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public GetAllMedicinesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}