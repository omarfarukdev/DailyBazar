package com.omar.dailybazar.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.dailybazar.Models.Bazar;
import com.omar.dailybazar.Models.DailyBazar;
import com.omar.dailybazar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.ViewHolder> {

    Context context;
    ArrayList<String> date=new ArrayList<>();

    BazarListAdapter bazarListAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ArrayList<Object>> test=new ArrayList<ArrayList<Object>>();
    HashMap<String,ArrayList<Object>> bazar=new HashMap();

    TreeMap<String, ArrayList<Object>> sorted = new TreeMap<>();

    ArrayList<String> text=new ArrayList<>();
    ArrayList<String> amount=new ArrayList<>();

    public DateListAdapter(Context context, HashMap<String, ArrayList<Object>> bazar) {
        this.context = context;
        this.bazar = bazar;
    }

    public DateListAdapter(Context context, ArrayList<String> date) {
        this.context = context;
        this.date = date;
    }
    public DateListAdapter(Context context, ArrayList<String> date, HashMap<String, ArrayList<Object>> bazar) {
        this.context = context;
        this.date = date;
        this.bazar = bazar;
        int n=1;

        sorted.putAll(bazar);
        for (Map.Entry<String,ArrayList<Object>> entry : sorted.entrySet()) {
            // holder.dateTv.setText((new ArrayList<>(bazar.keySet())).get(position));

                Log.d("AAAA",""+entry.getKey()+"  "+entry.getValue());
            //}

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_date_list,parent,false);

        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.bazarList.setLayoutManager(linearLayoutManager);
        holder.bazarList.setItemAnimator(new DefaultItemAnimator());
        holder.bazarList.addItemDecoration(new DividerItemDecoration(context,1));

        holder.dateTv.setText(date.get(position));

        Log.d("FFFFF",""+sorted.get(date.get(position)).size());
        test.clear();
        test.add(sorted.get(date.get(position)));
        double total=0;
        amount.clear();
        text.clear();
        for(int i=0;i<sorted.get(date.get(position)).size();i++){

            String[] baza=sorted.get(date.get(position)).get(i).toString().split(",");
            String[] amoun=baza[0].split("=");
            amount.add(amoun[1]);
            total=total+Double.parseDouble(amoun[1]);
            String[] tex=baza[1].split("[=}]");
            text.add(tex[1]);
            Log.d("SSSS",""+tex[1]+"  ");
        }

        bazarListAdapter=new BazarListAdapter(context,text,amount);
        holder.bazarList.setAdapter(bazarListAdapter);
        holder.totalAmountTv.setText(String.valueOf(total));

    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv,totalAmountTv;
        RecyclerView bazarList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv=itemView.findViewById(R.id.dateTv);
            totalAmountTv=itemView.findViewById(R.id.totalamount);
            bazarList=itemView.findViewById(R.id.bazarList);
        }
    }
}
