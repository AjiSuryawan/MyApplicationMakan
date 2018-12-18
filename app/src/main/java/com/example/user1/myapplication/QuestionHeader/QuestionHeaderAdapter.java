package com.example.user1.myapplication.QuestionHeader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionHeaderAdapter extends RecyclerView.Adapter<QuestionHeaderAdapter.MyViewHolder> {

    private MainGroupResponse mgResponses;
    private Context context;
    private String[] answers;

    public QuestionHeaderAdapter(MainGroupResponse mgResponses, Context context) {
        this.mgResponses = mgResponses;
        this.context = context;
        answers = new String[mgResponses.getAnswerHeaderFields().size()];
    }

    @Override
    public QuestionHeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final QuestionHeaderAdapter.MyViewHolder holder, final int position) {
        holder.setText(mgResponses, position);
    }

    @Override
    public int getItemCount() {
        return mgResponses == null ? 0 : mgResponses.getAnswerHeaderFields().size();
    }

    public boolean isAllFieldAnswered() {
        for (String answer : answers) {
            if (answer == null) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        answers.addAll(Arrays.asList(this.answers));
        return answers;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private EditText etInputAnswer;
        private TextView tvFormField;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvFormField = itemView.findViewById(R.id.tv_form_field);
            etInputAnswer = itemView.findViewById(R.id.et_input_answer);
            etInputAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String answer = etInputAnswer.getText().toString();
                    answers[getAdapterPosition()] = answer;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

        public void setText(MainGroupResponse mainGroupResponse, int position) {
            tvFormField.setText(mainGroupResponse.getAnswerHeaderFields().get(position));
            etInputAnswer.setHint(mainGroupResponse.getAnswerHeaderFields().get(position));
        }
    }
}
