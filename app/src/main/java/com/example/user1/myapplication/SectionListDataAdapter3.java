package com.example.user1.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.user1.myapplication.Model.ModelDatabase;

import java.util.ArrayList;

public class SectionListDataAdapter3 extends RecyclerView.Adapter<SectionListDataAdapter3.SingleItemRowHolder> {

    private View view;
    ArrayList<ModelDatabase> articleFilm;
    Context context;

    public SectionListDataAdapter3(Context context, ArrayList<ModelDatabase> articleFilm) {
        Log.d("lala3", "doInBackground: ");
        Log.d("di adapter", "SectionListDataAdapter: "+articleFilm.size());
        this.articleFilm=articleFilm;
        this.context=context;
    }

    @SuppressLint("InflateParams")
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, viewGroup,false);
        SingleItemRowHolder rowHolder = new SingleItemRowHolder(view);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SectionListDataAdapter3.SingleItemRowHolder holder, int position) {
        final ModelDatabase singleItem = articleFilm.get(position);
        holder.tvTitle.setText(singleItem.getName());
        holder.tvdesc.setText(singleItem.getNim());
        Uri image = Uri.parse(singleItem.getUriImage());

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), image);
            holder.imageView.setImageBitmap(bitmap);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e)  {}




    }

    @Override
    public int getItemCount() {
        return articleFilm.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvdesc;
        private ImageView imageView;

        private SingleItemRowHolder(final View view) {
            super(view);
            this.tvTitle = (TextView)view.findViewById(R.id.tvjudul);
            this.tvdesc = (TextView)view.findViewById(R.id.tvdesc);
            this.imageView = view.findViewById(R.id.imageView);
        }

    }
}
