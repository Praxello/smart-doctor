package com.praxello.smartdoctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.activity.RxActivity;
import com.praxello.smartdoctor.model.AddPrescription;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPrescriptionsAdapter extends RecyclerView.Adapter<AddPrescriptionsAdapter.AddPrescriptionsViewHolder> {

    Context context;
    ArrayList<AddPrescription> addPrescriptionArrayList;

    public AddPrescriptionsAdapter(Context context, ArrayList<AddPrescription> addPrescriptionArrayList) {
        this.context = context;
        this.addPrescriptionArrayList = addPrescriptionArrayList;
    }


    @NonNull
    @Override
    public AddPrescriptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.layout_add_prescription_row,parent,false);
        return new AddPrescriptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddPrescriptionsViewHolder holder, int position) {
        holder.tvMedicineName.setText(addPrescriptionArrayList.get(position).getTypeId()+". "+addPrescriptionArrayList.get(position).getMedicineId());
        holder.tvMorning.setText(addPrescriptionArrayList.get(position).getMorning()+"-"+addPrescriptionArrayList.get(position).getEvining()+"-"+addPrescriptionArrayList.get(position).getNight());
        holder.tvDuration.setText(addPrescriptionArrayList.get(position).getDuration()+" days");
        holder.tvInstruction.setText(addPrescriptionArrayList.get(position).getInst());

        holder.ivEdit.setOnClickListener(v -> {
            ((RxActivity) context).updatePrescription(addPrescriptionArrayList.get(position),position);
        });

        holder.ivDelete.setOnClickListener(v -> {
            addPrescriptionArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,addPrescriptionArrayList.size());
        });
    }

    @Override
    public int getItemCount() {
        return addPrescriptionArrayList.size();
    }

    public class AddPrescriptionsViewHolder extends RecyclerView.ViewHolder{

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

        public AddPrescriptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
