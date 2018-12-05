package com.example.user1.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class SlideFragment extends Fragment {

    private String TAG = SlideFragment.class.getSimpleName();
    private TextView tvPageIndicator;
    private TextView tvQuestion;
    private EditText inputAnswer;
    private RadioGroup radioGroup;
    private String question = "";
    private int totalQuestion = 0;
    private int questNo = 0;
    private int layout = 0;

    private PreviewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Survey> surveyList = new ArrayList<>();

    public SlideFragment() {
        //empty constructor
    }

    public static SlideFragment newInstance(String question, int questNo, int totalQuestion, int layout) {
        SlideFragment slideFragment = new SlideFragment();

        Bundle args = new Bundle();
        args.putString("question", question);
        args.putInt("quest_no", questNo);
        args.putInt("total_question", totalQuestion);
        args.putInt("layout", layout);

        slideFragment.setArguments(args);

        return slideFragment;
    }

    public static SlideFragment newInstance(int layout, ArrayList<Survey> surveys) {
        SlideFragment slideFragment = new SlideFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("survey_list", surveys);
        args.putInt("layout", layout);

        slideFragment.setArguments(args);
        return slideFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            layout = getArguments().getInt("layout");
            question = getArguments().getString("question");
            questNo = getArguments().getInt("quest_no");
            totalQuestion = getArguments().getInt("total_question");
            surveyList = getArguments().getParcelableArrayList("survey_list");
        }
        View view = inflater.inflate(layout, container, false);

        if (layout == R.layout.survey_default) {
            tvPageIndicator = view.findViewById(R.id.page_indicator);
            tvQuestion = view.findViewById(R.id.tv_question);
            inputAnswer = view.findViewById(R.id.input_answer);
            tvQuestion.setText(question);
            tvPageIndicator.setText(questNo + "/" + totalQuestion);
        }
        if (layout == R.layout.preview) {
            recyclerView = view.findViewById(R.id.rv);
            adapter = new PreviewAdapter(surveyList, getActivity());
            adapter.setListener(new onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    SlideActivity slideActivity = (SlideActivity) getActivity();
                    slideActivity.changeToDesiredQuestion(position);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }

        if(layout == R.layout.survey_singleanswer){
            tvPageIndicator = view.findViewById(R.id.page_indicator);
            tvQuestion = view.findViewById(R.id.tv_question);
            tvQuestion.setText(question);
            tvPageIndicator.setText(questNo + "/" + totalQuestion);
            radioGroup = view.findViewById(R.id.radio_group);
            final RadioButton[] rb = new RadioButton[5];
            for(int i = 0; i < 5; i++){
                rb[i]  = new RadioButton(getActivity());
                rb[i].setText("lorem ipsum " + i);
                rb[i].setId(i);
                radioGroup.addView(rb[i]);
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (surveyList != null) {
            for (Survey survey : surveyList) {
                Log.e(TAG, "onViewCreated: " + survey.getAnswer());
            }
        }
    }

    public String getAnswer() {
        return inputAnswer.getText().toString().isEmpty() ? "" : inputAnswer.getText().toString();
    }

    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }
}
