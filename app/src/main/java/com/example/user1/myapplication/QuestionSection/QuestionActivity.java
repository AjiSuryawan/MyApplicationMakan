package com.example.user1.myapplication.QuestionSection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.Utility.CustomViewPager;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = QuestionActivity.class.getSimpleName();
    String period;
    private Bundle extras;
    private CustomViewPager viewPager;
    private ArrayList<QuestionResponse> questions = new ArrayList<>();

    private Button previousBtn, nextBtn;
    private QuestionAdapter adapter;
    private AlertDialog.Builder builder;
    private ArrayList<String> answers = new ArrayList<>();
    private String category;
    private Typeface font;
    private DatabaseProvider dbProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        viewPager = findViewById(R.id.viewpager);
        nextBtn = findViewById(R.id.next_btn);
        previousBtn = findViewById(R.id.previous_btn);
        font = Typeface.createFromAsset(getAssets(), "fonts/MontserratRegular.ttf");

        nextBtn.setTypeface(font);
        previousBtn.setTypeface(font);

        dbProvider = DatabaseProvider.getInstance();

        extras = getIntent().getExtras();
        if (extras != null) {
            period = extras.getString("period");
            category = extras.getString("extra_category_mg");
            questions = extras.getParcelableArrayList("extra_questions");
            answers = extras.getStringArrayList("extra_answers");
            initQuestions(questions);
        }

        previousBtn.setVisibility(View.GONE);
        previousBtn.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1));

        nextBtn.setOnClickListener(v -> {
            //finish
            if (viewPager.getCurrentItem() == questions.size()) {
                //dialog
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            dbProvider.insert(category, questions, answers);

                            Intent intent = new Intent(this, MainGroupActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                builder.setMessage("Are you sure want to save data ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
            //to preview
            else if (viewPager.getCurrentItem() == questions.size() - 1) {
                int position = viewPager.getCurrentItem();
                QuestionFragment previewFragment = (QuestionFragment) adapter.getItem(questions.size());
                QuestionFragment fragment = (QuestionFragment) adapter.getItem(position);
                if (questions.get(viewPager.getCurrentItem()).getTipe().equalsIgnoreCase("ma")) {
                    questions.get(position).setJawabanUser(fragment.getAnswers());

                } else {
                    questions.get(position).setJawabanUser(fragment.getAnswer());

                }
                previewFragment.notifyDataSetChanged();
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            //to ma
            else if (questions.get(viewPager.getCurrentItem()).getTipe().equalsIgnoreCase("ma")) {
                final int position = viewPager.getCurrentItem();
                QuestionFragment fragment = (QuestionFragment) adapter.getItem(position);

                final QuestionResponse questionModel = questions.get(position);
                questionModel.setJawabanUser(fragment.getAnswers());



                viewPager.setCurrentItem(position + 1);
            } else {
                final int position = viewPager.getCurrentItem();
                QuestionFragment fragment = (QuestionFragment) adapter.getItem(position);
                final QuestionResponse questionModel = questions.get(position);

                final String answer = fragment.getAnswer();
                questionModel.setJawabanUser(answer);

                viewPager.setCurrentItem(position + 1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == questions.size()) {
                    nextBtn.setText("Finish");
                } else {
                    nextBtn.setText("Next");
                }

                if (i == 0) {
                    previousBtn.setVisibility(View.GONE);
                } else {
                    previousBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void initQuestions(ArrayList<QuestionResponse> questions) {
        adapter = new QuestionAdapter(getSupportFragmentManager());
        for (int i = 0; i <= questions.size(); i++) {
            if (i == questions.size()) { // preview all
                adapter.initFragment(QuestionFragment.newInstance(R.layout.preview, questions));
            } // tipe sa
            else if (questions.get(i).getTipe().equalsIgnoreCase("sa")) {
                QuestionResponse questionModel = questions.get(i);
                adapter.initFragment(QuestionFragment.newInstance(questionModel.getPertanyaan(), i + 1, questions.size(), questions.get(i).getJawabanAwal(), R.layout.question_singleanswer));
            } // tipe ma
            else if (questions.get(i).getTipe().equalsIgnoreCase("ma")) {
                QuestionResponse questionModel = questions.get(i);
                adapter.initFragment(QuestionFragment.newInstance(questionModel.getPertanyaan(), i + 1, questions.size(), questions.get(i).getJawabanAwal(), R.layout.question_multipleanswer));
            } // tipe ketik
            else if (questions.get(i).getTipe().equalsIgnoreCase("ketik")) {
                QuestionResponse questionModel = questions.get(i);
                adapter.initFragment(QuestionFragment.newInstance(questionModel.getPertanyaan(), i + 1, questions.size(), R.layout.question_default));
            }
        }
        viewPager.setAdapter(adapter);
    }

    public void changeToDesiredQuestion(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbProvider.close();
    }
}
