package com.example.user1.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Survey> surveyList;
    private Activity activity;
    private EditText inputAnswer;

    public SlideAdapter(Context context, ArrayList<Survey> surveyList, Activity activity) {
        this.surveyList = surveyList;
        this.context = context;
        this.activity = activity;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        Survey survey = surveyList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.survey, container, false);

        TextView tvPageIndicator = viewGroup.findViewById(R.id.page_indicator);
        tvPageIndicator.setText(position + 1 + "/" + surveyList.size());
        TextView tvQuestion = viewGroup.findViewById(R.id.tv_question);
        tvQuestion.setText(survey.getQuestion());

        inputAnswer = viewGroup.findViewById(R.id.input_answer);

        if(position == surveyList.size() - 1 ){
            for (Survey surv : surveyList) {
                Log.e("SlideAdapter", "instantiateItem: " + surv.getAnswer() );
            }
        }

        container.addView(viewGroup);

        return viewGroup;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Survey survey = surveyList.get(position);
        String answer = inputAnswer.getText().toString();
        Log.e("SlideAdapter", "destroyItem: " + answer );
        survey.setAnswer(answer);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return surveyList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

}
