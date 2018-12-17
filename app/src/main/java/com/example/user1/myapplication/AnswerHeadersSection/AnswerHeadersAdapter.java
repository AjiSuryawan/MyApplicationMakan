package com.example.user1.myapplication.AnswerHeadersSection;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1.myapplication.Database.ObjectSurvey;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class AnswerHeadersAdapter extends RecyclerView.Adapter<AnswerHeadersAdapter.AHViewHolder> {

    private Context context;
    private ArrayList<ObjectSurvey> objectSurveys;
    private onItemClickListener listener;

    public AnswerHeadersAdapter(Context context, ArrayList<ObjectSurvey> objectSurveys) {
        this.context = context;
        this.objectSurveys = objectSurveys;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AHViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer_header, viewGroup, false);
        return new AHViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AHViewHolder ahViewHolder, int i) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/MontserratRegular.ttf");

        ahViewHolder.tvIdUser.setTypeface(font);
        ahViewHolder.setText(objectSurveys.get(i));
    }

    @Override
    public int getItemCount() {
        return objectSurveys.size();
    }

    class AHViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdUser;
        public AHViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdUser = itemView.findViewById(R.id.tv_id);

            itemView.setOnClickListener( view -> listener.onItemClick(getAdapterPosition()));

        }

        public void setText(ObjectSurvey objectSurvey){
            tvIdUser.setText(objectSurvey.getId());
        }
    }
}
