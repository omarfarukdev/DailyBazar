package com.omar.dailybazar.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.dailybazar.Models.Bazar;
import com.omar.dailybazar.R;

import java.util.ArrayList;
import java.util.List;

public class BazarListAdapter extends RecyclerView.Adapter<BazarListAdapter.ViewHolder> {

    Context context;
    List<Bazar> bazarList=new ArrayList<>();
    ArrayList<ArrayList<Object>> test=new ArrayList<ArrayList<Object>>();
    ArrayList<Object> test1=new ArrayList<>();
    Object object=new Object();
    ArrayList<String> bazar=new ArrayList<>();
    ArrayList<String> text=new ArrayList<>();
    ArrayList<String> amount=new ArrayList<>();

    public BazarListAdapter(Context context, ArrayList<String> text, ArrayList<String> amount) {
        this.context = context;
        this.text = text;
        this.amount = amount;
    }

    public BazarListAdapter(Context context, ArrayList<Object> test1) {
        this.context = context;
        this.test1 = test1;
        bazar.clear();
        for(int i=0;i<test1.size();i++){

           bazar.add(test1.get(i).toString());
           //Bazar bazar1=new Bazar(test1.get(i));
            String[] baza=test1.get(i).toString().split(",");
            String[] amoun=baza[0].split("=");
            amount.add(amoun[1]);
            String[] tex=baza[1].split("[=}]");
            text.add(tex[1]);
            Log.d("SSSS",""+tex[1]+"  ");
        }


    }

    public BazarListAdapter(Context context, List<Bazar> bazarList) {
        this.context = context;
        this.bazarList = bazarList;
    }

    /*public BazarListAdapter(Context context, ArrayList<ArrayList<Object>> test) {
        this.context = context;
        this.test = test;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_bazar_list,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*holder.amountTv.setText(bazarList.get(position).getAmount());
        holder.bazarTextTv.setText(bazarList.get(position).getText());

        Log.d("AAAAAAAA",""+bazarList.size());*/
        holder.bazarTextTv.setText(text.get(position));
        holder.amountTv.setText(amount.get(position));
        Log.d("DDD",""+bazar.size());

    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bazarTextTv,amountTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bazarTextTv=itemView.findViewById(R.id.bazartext);
            amountTv=itemView.findViewById(R.id.totalamount);
        }
    }
}
