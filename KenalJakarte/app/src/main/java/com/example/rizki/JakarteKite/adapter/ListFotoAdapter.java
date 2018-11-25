package com.example.rizki.JakarteKite.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rizki.JakarteKite.R;
import com.example.rizki.JakarteKite.presenter.Foto;

import java.util.ArrayList;

public class ListFotoAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Foto> fishslist;

    public ListFotoAdapter(Context context, int layout, ArrayList<Foto> fishslist) {
        this.context = context;
        this.layout = layout;
        this.fishslist = fishslist;
    }

    @Override
    public int getCount() {
        return fishslist.size();
    }

    @Override
    public Object getItem(int position) {
        return fishslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imgIkan;
        TextView txtName,txtHarga;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtHarga = (TextView) row.findViewById(R.id.txtHarga);
            holder.imgIkan = (ImageView) row.findViewById(R.id.imgIkan);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Foto foto = fishslist.get(position);

        holder.txtName.setText(foto.getName());
        holder.txtHarga.setText(foto.getHarga());

        byte[] ikangmb2 = foto.getImage();
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(ikangmb2, 0, ikangmb2.length);
        holder.imgIkan.setImageBitmap(bitmap2);



        return row;
    }
}
