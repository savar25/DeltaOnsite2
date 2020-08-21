package com.example.deltaonsite2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class rec_viewAdapter extends RecyclerView.Adapter<rec_viewAdapter.ViewHolder>{

    ArrayList<ScheduledModel> modelArrayList=new ArrayList<>();
    Context context;
    database db;

    public rec_viewAdapter(ArrayList<ScheduledModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        db=new database(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.element_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.num.setText(modelArrayList.get(position).getPnum());
        holder.msg.setSingleLine(true);
        holder.msg.setText(modelArrayList.get(position).getMessage());
        holder.time.setText(String.valueOf(modelArrayList.get(position).getHr())+":"+String.valueOf(modelArrayList.get(position).getMin()));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(context,service.class);
                intent.putExtra("request",modelArrayList.get(position).getID());
                intent.putExtra("message",modelArrayList.get(position).getMessage());
                intent.putExtra("number",modelArrayList.get(position).getPnum());


                PendingIntent pendingIntent= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    pendingIntent = PendingIntent.getForegroundService(context,modelArrayList.get(position).getID(),intent,0);
                }else{
                    pendingIntent = PendingIntent.getService(context,modelArrayList.get(position).getID(),intent,0);
                }
                base.scheduledModels.remove(position);
                base.adapter.notifyItemRemoved(position);
               alarmManager.cancel(pendingIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView num,msg,time;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num=itemView.findViewById(R.id.rec_no);
            msg=itemView.findViewById(R.id.rec_msg);
            button=itemView.findViewById(R.id.rec_remBtn);
            time=itemView.findViewById(R.id.rec_time);


        }
    }
}
