package com.ducanh.bai2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ducanh.bai2.model.LichThi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtId, txtName,txtSum;
    private CheckBox checkBoxActive;
    private Button btnAdd,btnUpdate,btnDelete;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private SQLiteOrderHelper sqlite;
    private DatePicker datePicker;
    private EditText txtSearch;
    TimePicker timePicker;
    List<LichThi> lichThiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        sqlite=new SQLiteOrderHelper(this);
        getAll();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= txtName.getText().toString();
                boolean active=checkBoxActive.isChecked();
                String date=getDateFromDatePicker();
                LichThi lichThi =new LichThi(1,name,date,active);

                sqlite.addLichTHi(lichThi);
                System.out.println(date+"");

                getAll();

            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String name = txtSearch.getText().toString().trim();
                    List<LichThi> cours = sqlite.searchCourse(name);
                    if (cours == null){
                        Toast.makeText(getApplicationContext(), "Khong co!", Toast.LENGTH_SHORT).show();
                    }else {
                        adapter=new Adapter(cours);
                        recyclerView.setAdapter(adapter);
                    }

                }catch (NumberFormatException | ParseException e){
                    System.out.println(e + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(txtId.getText().toString());
                String name= txtName.getText().toString();

                boolean active=checkBoxActive.isChecked();
                String date=getDateFromDatePicker();
                LichThi lichThi =new LichThi(id,name,date,active);
                sqlite.update(lichThi);
                System.out.println(sqlite.update(lichThi));
                getAll();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(txtId.getText().toString());
                List<LichThi> lichThis=new ArrayList<>();
                try {
                   lichThis = sqlite.getAll();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (LichThi lichThi: lichThis){
                    if (lichThi.getId()==id){
                        if (checkDate(lichThi.getName()))
                            try {

                                sqlite.deleteById(id);

                            }catch (NumberFormatException e){
                                System.out.println(e + "");
                            }
                        else  Toast.makeText(getApplicationContext(), "Khong xoa dc!", Toast.LENGTH_SHORT).show();
                    }
                }


                getAll();
            }
        });

    }


    private void getAll(){
        try {
            List<LichThi> cours = sqlite.getAll();
            int sum=0;
            for (LichThi lichThi : cours){
                if (lichThi.isThiViet())
                    sum++;
            }
            txtSum.setText("Tong: "+sum);
            adapter=new Adapter(cours);
            recyclerView.setAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void init(){
        txtId =findViewById(R.id.id);
        txtName =findViewById(R.id.name);
        txtSum =findViewById(R.id.txt_sum);
        datePicker = (DatePicker)findViewById(R.id.datePicker);

        checkBoxActive=findViewById(R.id.checkbox_active);
        txtSearch=findViewById(R.id.txt_search);
        timePicker = (TimePicker) this.findViewById(R.id.timePicker);

        btnAdd=findViewById(R.id.btn_add);
        btnDelete=findViewById(R.id.btn_delete);
        btnUpdate=findViewById(R.id.btn_update);
        recyclerView=findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private String getDateFromDatePicker(){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int gio =timePicker.getCurrentHour();
        int phut=timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day,gio,phut);
        System.out.println(day+"/"+month+"/"+year+"/"+gio+"/"+phut);
        System.out.println(calendar.getTime().toString());

        return year+"/"+month+"/"+day+"/"+gio+"/"+phut;

//        return calendar.getTime().toString();
    }
    private boolean checkDate(String key){
        Calendar c = Calendar.getInstance();
        int hour, minute, second;
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year =  c.get(Calendar.YEAR);

        String date[]=key.split("/");
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        int month = c.get(Calendar.MONTH);
//        int year =  c.get(Calendar.YEAR);
        System.out.println(date[3]+"-------------");
        final int hour1 = Integer.parseInt(date[3]);
        final int minute1 = Integer.parseInt(date[4]);

        if (hour1<hour && minute1<minute1 ) return true;
        else return false;


    }

}