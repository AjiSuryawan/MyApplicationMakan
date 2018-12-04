package com.example.user1.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.MyViewHolder> {

    private ArrayList<Survey> surveyList;
    private Context context;
    private onItemClickListener listener;

    public PreviewAdapter(ArrayList<Survey> surveyList, Context context) {
        this.surveyList = surveyList;
        this.context = context;
    }

    public void setListener(onItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.preview_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Survey survey = surveyList.get(i);
        myViewHolder.updateUI(survey, i + 1);
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        TextView tvAnswer;
        TextView tvNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNo = itemView.findViewById(R.id.tv_no);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvAnswer = itemView.findViewById(R.id.tv_answer);

            tvAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        void updateUI(Survey survey, int position) {
            tvQuestion.setText(survey.getQuestion());
            tvAnswer.setText(survey.getAnswer());
            tvNo.setText(position + ") ");
        }
    }
}
