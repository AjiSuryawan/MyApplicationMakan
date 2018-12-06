package com.example.user1.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private View view;
    ArrayList<Survey> articleFilm;
    Context context;

    public SectionListDataAdapter(Context context, ArrayList<Survey> articleFilm) {
        this.articleFilm=articleFilm;
        this.context=context;
    }

    @SuppressLint("InflateParams")
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder rowHolder = new SingleItemRowHolder(view);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SectionListDataAdapter.SingleItemRowHolder holder, int position) {
        final Survey singleItem = articleFilm.get(position);

        Log.d("di adapter", "SectionListDataAdapter: "+singleItem.getNamaguru());
        holder.tvTitle.setText(singleItem.getNamaguru());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startActivity(new Intent(view.getContext(),DetailQuesioner.class));
            }
        });
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
        private SingleItemRowHolder(final View view) {
            super(view);
            this.tvTitle = (TextView)view.findViewById(R.id.tvjudul);


        }

    }
}
