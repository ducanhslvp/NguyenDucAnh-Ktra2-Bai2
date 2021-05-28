package com.ducanh.bai2;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ducanh.bai2.model.LichThi;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<LichThi> lichThiList;

    public Adapter(List<LichThi> lichThiList) {
        this.lichThiList = lichThiList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.item_course,parent,false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        LichThi lichThi = lichThiList.get(position);
        holder.id.setText("ID: "+ lichThi.getId());
        holder.name.setText("Name: "+ lichThi.getName());

        holder.date.setText("Ngay: "+ lichThi.getNgay());


        holder.active.setChecked(lichThi.isThiViet());

    }

    @Override
    public int getItemCount() {
        return lichThiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id,name,date,major;
        private CheckBox active;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.txt_id);
            name=itemView.findViewById(R.id.txt_name);
            date=itemView.findViewById(R.id.txt_date);
            active=itemView.findViewById(R.id.active);
        }
    }
}
