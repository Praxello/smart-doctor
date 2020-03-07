package com.praxello.smartdoctor.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.praxello.smartdoctor.R;
import com.praxello.smartdoctor.activity.AllPatientsActivity;
import com.praxello.smartdoctor.activity.RxActivity;
import com.praxello.smartdoctor.activity.UpdatePatientActivity;
import com.praxello.smartdoctor.model.CommonResponse;
import com.praxello.smartdoctor.model.allpatient.AllPatientData;
import com.praxello.smartdoctor.services.ApiRequestHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AllPatientAdapter extends RecyclerView.Adapter<AllPatientAdapter.AllPatientViewHolder> {

    Context context;
    ArrayList<AllPatientData> allPatientDataArrayList;
    private static String TAG="AllPatientAdapter";

    public AllPatientAdapter(Context context, ArrayList<AllPatientData> allPatientDataArrayList) {
        this.context = context;
        this.allPatientDataArrayList = allPatientDataArrayList;
    }

    @NonNull
    @Override
    public AllPatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.layout_allpatient_row,parent,false);
        return new AllPatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPatientViewHolder holder, int position) {
        holder.tvName.setText(allPatientDataArrayList.get(position).getFirstName()+" "+allPatientDataArrayList.get(position).getLastName());
        holder.tvMobileNumber.setText(allPatientDataArrayList.get(position).getMobile());
        holder.tvAddress.setText(allPatientDataArrayList.get(position).getAddress());

        holder.ivEdit.setOnClickListener(v -> {
            Activity activity=(Activity) context;
            Intent intent=new Intent(context, UpdatePatientActivity.class);
            //intent.putExtra("data",quizDataArrayList.get(position).getQuestions());
            intent.putExtra("data",allPatientDataArrayList.get(position));
            intent.putExtra("position",position);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        });

        holder.ivRx.setOnClickListener(v -> {
            Activity activity=(Activity) context;
            Intent intent=new Intent(context, RxActivity.class);
            //intent.putExtra("data",quizDataArrayList.get(position).getQuestions());
            //intent.putExtra("data",allPatientDataArrayList.get(position));
            //intent.putExtra("position",position);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        });

        holder.ivDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("SmartQuiz")
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            deletePatient(allPatientDataArrayList.get(position).getPatientId(),allPatientDataArrayList.get(position).getDoctorId(),position);
                        }
                    })
                     // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return allPatientDataArrayList.size();
    }

    public class AllPatientViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvMobileNumber;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;
        @BindView(R.id.iv_rx)
        ImageView ivRx;
        @BindView(R.id.iv_trash)
        ImageView ivDelete;

        public AllPatientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void deletePatient(int patientId,int doctorId,int position){
        Map<String,String> params=new HashMap<>();

        params.put("patientId", String.valueOf(patientId));
        params.put("doctorId", String.valueOf(doctorId));

        Log.e(TAG, "submitLeave: "+params );
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);

        AllPatientsActivity.smartQuiz.getApiRequestHelper().deletePatient(params,new ApiRequestHelper.OnRequestComplete() {
            @Override
            public void onSuccess(Object object) {
                CommonResponse commonResponse=(CommonResponse) object;

                Log.e(TAG, "onSuccess: "+commonResponse.getResponsecode());
                Log.e(TAG, "onSuccess: "+commonResponse.getMessage());

                progress.dismiss();

                if(commonResponse.getResponsecode()==200){
                    Toast.makeText(context, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    allPatientDataArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,allPatientDataArrayList.size());
                }else{
                    Toast.makeText(context, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(String apiResponse) {
                progress.dismiss();
                Toast.makeText(context, apiResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
