package com.mehmetalivargun.proje;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.MyViewHolder> {
    ArrayList<Remind> mRemindList;
    LayoutInflater inflater;
    Context context;

    private LinkedHashMap<Integer, Integer> IDmap = new LinkedHashMap<>();
    private Database rb;
    public RemindAdapter(Context context, ArrayList<Remind> mRemindList) {
        rb=new Database(context.getApplicationContext());
        inflater = LayoutInflater.from(context);
        this.mRemindList = mRemindList;
        this.context=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        Remind selectedProduct = mRemindList.get(position);
        holder.setData(selectedProduct, position);
    }


    @Override
    public int getItemCount() {
        return mRemindList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView remindName, remindDate,remindFreq;
        ImageView deleteproduct;
        LayoutInflater inflater;



        public MyViewHolder(View itemView) {
            super(itemView);
            remindName = (TextView) itemView.findViewById(R.id.recycle_title);
            remindDate = (TextView) itemView.findViewById(R.id.recycle_date_time);
            remindFreq = (TextView) itemView.findViewById(R.id.recycle_repeat_info);
            deleteproduct = (ImageView) itemView.findViewById(R.id.delete);

            deleteproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int newPosition =getAdapterPosition();
                    Log.d("thien.van","on Click onBindViewHolder");
                    Remind e=null;
                    for(Remind r : rb.getAllReminders()){
                        if (r.getTitle().compareToIgnoreCase(remindName.getText().toString())==0){
                            e=r;
                            break;
                        }

                    }
                    if(e!=null){
                        rb.deleteReminder(e);
                    }


                    notifyItemRemoved(newPosition);
                    notifyItemRangeChanged(newPosition, rb.getAllReminders().size()-1);





                }
            });

        }

        public void setData(Remind selectedProduct, int position) {

            this.remindName.setText(selectedProduct.getTitle());
            this.remindDate.setText(selectedProduct.getDate());
            this.remindFreq.setText(selectedProduct.getTime());


        }


        @Override
        public void onClick(View v) {


        }


    }
}
