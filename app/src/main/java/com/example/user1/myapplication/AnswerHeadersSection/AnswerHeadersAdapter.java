package com.example.user1.myapplication.AnswerHeadersSection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1.myapplication.Database.ObjectSurvey;
import com.example.user1.myapplication.R;

import java.util.ArrayList;

public class AnswerHeadersAdapter extends RecyclerView.Adapter<AnswerHeadersAdapter.AHViewHolder> {

    private Context context;
    private ArrayList<ObjectSurvey> objectSurveys;

    public AnswerHeadersAdapter(Context context, ArrayList<ObjectSurvey> objectSurveys) {
        this.context = context;
        this.objectSurveys = objectSurveys;
    }

    @NonNull
    @Override
    public AHViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer_header, viewGroup, false);
        return new AHViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AHViewHolder ahViewHolder, int i) {
        ahViewHolder.setText(objectSurveys.get(i));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AHViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdUser;
        public AHViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdUser = itemView.findViewById(R.id.tv_id);
        }

        public void setText(ObjectSurvey objectSurvey){
            tvIdUser.setText(objectSurvey.getId());
        }
    }
}
