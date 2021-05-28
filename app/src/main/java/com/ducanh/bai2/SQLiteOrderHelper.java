package com.ducanh.bai2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ducanh.bai2.model.LichThi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLiteOrderHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="LichThi.db";
    private static final int DATABSE_VERSION=1;


    public SQLiteOrderHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE LichThi(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT," +
                "ngay TEXT," +
                "viet TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void addLichTHi(LichThi lichThi){

        String check;
        if (lichThi.isThiViet()) check="1";
        else check="0";

        String sql="INSERT INTO LichThi(ten,ngay,viet) VALUES (?,?,?)";
        String[] args={lichThi.getName(),lichThi.getNgay() ,
                check};
        SQLiteDatabase statement =getWritableDatabase();
        statement.execSQL(sql,args);
    }
    public List<LichThi> getAll() throws ParseException {
        List<LichThi> list=new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SQLiteDatabase statement=getReadableDatabase();
        Cursor rs=statement.query("LichThi",null,
                null,null,null,
                null,null);
        while((rs!=null && rs.moveToNext())){
            int id=rs.getInt(0);
            String name=rs.getString(1);
            String date=rs.getString(2);
            String check=rs.getString(3);
            boolean active;
            if (check.equals("1")) active=true;
            else active=false;
            list.add(new LichThi(id,name,date,active));
        }
        return list;
    }
    public List<LichThi> searchCourse(String clause) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<LichThi> cours = new ArrayList<>();
        String whereClause = "ten LIKE ?";
        String[] whereArgs = {"%" + clause + "%"};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor rs = statement.query("LichThi", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id=rs.getInt(0);
            String name=rs.getString(1);
            String date=rs.getString(2);
            String check=rs.getString(3);
            boolean active;
            if (check.equals("1")) active=true;
            else active=false;
            cours.add(new LichThi(id,name,date,active));
        }
        rs.close();
        return cours;
    }

    public int update(LichThi lichThi){
        ContentValues values = new ContentValues();
        values.put("ten", lichThi.getName());
        values.put("ngay", lichThi.getNgay());

        if (lichThi.isThiViet())
            values.put("viet",  "1");
        else values.put("viet",  "0");

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(lichThi.getId())};
        SQLiteDatabase statement = getWritableDatabase();
        System.out.println("da chay qua");
        return statement.update("LichThi", values, whereClause, whereArgs);
    }


    public int deleteById(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase statement = getWritableDatabase();
        return statement.delete("LichThi", whereClause, whereArgs);
    }

}
