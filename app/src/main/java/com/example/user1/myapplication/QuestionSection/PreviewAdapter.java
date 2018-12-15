package com.example.user1.myapplication.QuestionSection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.MyViewHolder> {

    private ArrayList<QuestionResponse> questionsModel;
    private Context context;
    private onItemClickListener listener;

    public PreviewAdapter(ArrayList<QuestionResponse> questionsModel, Context context) {
        this.questionsModel = questionsModel;
        this.context = context;
    }

    public void setListener(onItemClickListener listener) {
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
        QuestionResponse questionModel = questionsModel.get(i);
        myViewHolder.updateUI(questionModel, i + 1);
    }

    @Override
    public int getItemCount() {
        return questionsModel.size();
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

            tvAnswer.setOnClickListener(v -> {
                if(listener != null) listener.onItemClick(getAdapterPosition());
            });
        }

        void updateUI(QuestionResponse questionsModel, int position) {
            tvQuestion.setText(questionsModel.getPertanyaan());
            StringBuilder answers = new StringBuilder();

            Log.e(TAG, "updateUI: " + questionsModel.getPertanyaan());
            for (int i = 0; i < questionsModel.getJawabanUser().size(); i++) {
                String answer = questionsModel.getJawabanUser().get(i);
                if (i == questionsModel.getJawabanUser().size() - 1 )
                    answers.append(answer);
                else
                    answers.append(answer+ ", ");

            }
            tvAnswer.setText(answers.toString());
            tvNo.setText(position + ") ");
        }
    }
}
