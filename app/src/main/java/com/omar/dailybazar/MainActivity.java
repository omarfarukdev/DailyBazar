package com.omar.dailybazar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.dailybazar.Adapters.DateListAdapter;
import com.omar.dailybazar.Models.Bazar;
import com.omar.dailybazar.Models.Bazars;
import com.omar.dailybazar.Models.DailyBazar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fabBt)
    FloatingActionButton fabBt;
    @BindView(R.id.dateList)
    RecyclerView dateList;

    TextView dateTv;
    String currentdate,bazarText,bazarAmount;
    Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat format;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef,Reff;
    DailyBazar dailyBazar;
    private final List<Bazars> bazars=new ArrayList<>();
    ArrayList<String> dateLists=new ArrayList<>();
    List<HashMap<String,ArrayList<Object>>> test=new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    DateListAdapter dateListAdapter;
    String date;
    int i=0;
    HashMap<String,ArrayList<Object>> bazar=new HashMap();
    //List<HashMap<String,ArrayList<Object>>>

    ArrayList<Object> arr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FirebaseApp.initializeApp(getApplicationContext());

        linearLayoutManager=new LinearLayoutManager(this);
        dateList.setLayoutManager(linearLayoutManager);


        mAuth=FirebaseAuth.getInstance();
        RootRef= FirebaseDatabase.getInstance().getReference();
        Reff= FirebaseDatabase.getInstance().getReference();

        calendar=Calendar.getInstance();
        format = new SimpleDateFormat("dd MMMM yyyy");
        currentdate= format.format(calendar.getTime());

        fabBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        i=1;
        Toast.makeText(this, "FFFFF", Toast.LENGTH_SHORT).show();
        RootRef.child("UserBazar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dateLists.clear();
                for(DataSnapshot d: snapshot.getChildren()){
                    Log.d("tttt",d.getKey());
                    dateLists.add(d.getKey());

                    RootRef.child("UserBazar").child(d.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<Object> arrayList = new ArrayList<>();
                            for(DataSnapshot d : snapshot.getChildren()){
                                arrayList.add(d.getValue());
                            }
                            bazar.put(d.getKey(),arrayList);
                            Log.d("ttt",""+dateLists.size()+"  "+i);
                            int n=1;
                            if (i==dateLists.size()){

                                dateListAdapter=new DateListAdapter(MainActivity.this,dateLists,bazar);
                                dateList.setAdapter(dateListAdapter);
                                dateListAdapter.notifyDataSetChanged();
                                //dateList.smoothScrollToPosition(dateList.getAdapter().getItemCount());

                                Toast.makeText(MainActivity.this, "Call"+n, Toast.LENGTH_SHORT).show();
                                n++;
                                i=1;
                            }

                            i++;

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void showdialog() {

        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_bazar);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dateTv=dialog.findViewById(R.id.dateTv);
        EditText bazaeEt=dialog.findViewById(R.id.text);
        EditText amountEt=dialog.findViewById(R.id.amount);
        FloatingActionButton done=dialog.findViewById(R.id.done);

        dateTv.setText(currentdate);

        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, ""+currentdate, Toast.LENGTH_SHORT).show();

                bazarText=bazaeEt.getText().toString();
                bazarAmount=amountEt.getText().toString();
                addBazar();
                dialog.dismiss();
            }
        });

    }

    private void addBazar() {

        String bazarRef="UserBazar/"+currentdate;
        DatabaseReference databaseReference=RootRef.child("UserBazar").child(currentdate).push();

        String bazarPushId=databaseReference.getKey();
        Map bazarTextBody=new HashMap();
        bazarTextBody.put("Text",bazarText);
        bazarTextBody.put("Amount",bazarAmount);

        Map bazarBodyDetails=new HashMap();
        bazarBodyDetails.put(bazarRef+"/"+bazarPushId,bazarTextBody);

        RootRef.updateChildren(bazarBodyDetails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Bazar Add Successfully...", Toast.LENGTH_SHORT).show();
                    onStart();
                }
                else {
                    Toast.makeText(MainActivity.this, "error0", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showDatePicker() {

        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                currentdate=format.format(calendar.getTime());
                dateTv.setText(currentdate);
            }
        },year,month,day);
        datePickerDialog.show();
    }
}