package com.example.user1.myapplication.QuestionSection;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class QuestionFragment extends Fragment {

    private String TAG = QuestionFragment.class.getSimpleName();

    private TextView tvPageIndicator;
    private TextView tvQuestion;
    private EditText inputAnswer;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;
    private ViewGroup checkboxLayout;
    private CheckBox[] checkBoxes;
    private String question = "";
    private int totalQuestion = 0;
    private int questNo = 0;
    private int layout = 0;

    private PreviewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<QuestionResponse> questionsModel = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();

    public QuestionFragment() {
        //empty constructor
    }

    public static QuestionFragment newInstance(String question, int questNo, int totalQuestion, int layout) {
        QuestionFragment questionFragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putString("question", question);
        args.putInt("quest_no", questNo);
        args.putInt("total_question", totalQuestion);
        args.putInt("layout", layout);

        questionFragment.setArguments(args);

        return questionFragment;
    }

    public static QuestionFragment newInstance(String question, int questNo, int totalQuestion, ArrayList<String> answers, int layout) {
        QuestionFragment questionFragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putString("question", question);
        args.putInt("quest_no", questNo);
        args.putInt("total_question", totalQuestion);
        args.putInt("layout", layout);
        args.putStringArrayList("answers", answers);

        questionFragment.setArguments(args);

        return questionFragment;
    }

    public static QuestionFragment newInstance(int layout, ArrayList<QuestionResponse> questionsModel) {
        QuestionFragment questionFragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("survey_list", questionsModel);
        args.putInt("layout", layout);

        questionFragment.setArguments(args);
        return questionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            layout = getArguments().getInt("layout");
            question = getArguments().getString("question");
            questNo = getArguments().getInt("quest_no");
            totalQuestion = getArguments().getInt("total_question");
            questionsModel = getArguments().getParcelableArrayList("survey_list");
            answers = getArguments().getStringArrayList("answers");
        }

        View view = inflater.inflate(layout, container, false);

        //ketik
        if (layout == R.layout.question_default) {
            tvPageIndicator = view.findViewById(R.id.page_indicator);
            tvQuestion = view.findViewById(R.id.tv_question);
            inputAnswer = view.findViewById(R.id.input_answer);
            tvQuestion.setText(question);
            tvPageIndicator.setText(questNo + "/" + totalQuestion);
        } //preview
        if (layout == R.layout.preview) {
            recyclerView = view.findViewById(R.id.rv);
            adapter = new PreviewAdapter(questionsModel, getActivity());
            adapter.setListener(new onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    QuestionActivity slideActivity = (QuestionActivity) getActivity();
                    slideActivity.changeToDesiredQuestion(position);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
        //sa
        if(layout == R.layout.question_singleanswer){
            tvPageIndicator = view.findViewById(R.id.page_indicator);
            tvQuestion = view.findViewById(R.id.tv_question);
            tvQuestion.setText(question);
            tvPageIndicator.setText(questNo + "/" + totalQuestion);
            radioGroup = view.findViewById(R.id.radio_group);
            radioButtons = new RadioButton[answers.size()];
            for(int i = 0; i < answers.size(); i++){
                radioButtons[i]  = new RadioButton(getActivity());
                radioButtons[i].setText(answers.get(i));
                radioButtons[i].setId(i);
                radioGroup.addView(radioButtons[i]);
            }
        }
        //ma
        if(layout == R.layout.question_multipleanswer){
            tvPageIndicator = view.findViewById(R.id.page_indicator);
            tvQuestion = view.findViewById(R.id.tv_question);
            tvQuestion.setText(question);
            tvPageIndicator.setText(questNo + "/" + totalQuestion);
            checkboxLayout = view.findViewById(R.id.checkbox_layout);
            checkBoxes = new CheckBox[answers.size()];
            for(int i = 0; i < answers.size(); i++){
                checkBoxes[i] = new CheckBox(getActivity());
                checkBoxes[i].setText(answers.get(i));
                checkBoxes[i].setId(i);
                checkboxLayout.addView(checkBoxes[i]);
            }
        }
        return view;
    }

    public String getAnswer() {
        switch (layout){
            case R.layout.question_default:
                return inputAnswer.getText().toString().isEmpty() ? "" : inputAnswer.getText().toString();
            case R.layout.question_singleanswer:
                String answer = "";
                for (RadioButton radioButton : radioButtons) {
                    if(radioButton.getId() == radioGroup.getCheckedRadioButtonId()){
                        answer = radioButton.getText().toString();
                    }
                }
                return answer;
                default:
                    return "";
        }
    }

    public ArrayList<String> getAnswers(){
        ArrayList<String> answers = new ArrayList<>();
        for (CheckBox checkbox : checkBoxes) {
            if(checkbox.isChecked()){
                answers.add(checkbox.getText().toString());
                Log.e("Answer", "getAnswers: " + checkbox.getText().toString() );
            }
        }
        return answers;
    }

    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }
}
