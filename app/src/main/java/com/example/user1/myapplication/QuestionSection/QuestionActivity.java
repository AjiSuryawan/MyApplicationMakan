package com.example.user1.myapplication.QuestionSection;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user1.myapplication.Database.AnsweredQuestionByObject;
import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Database.ObjectSurvey;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.Network.SurveyClient;
import com.example.user1.myapplication.Network.SurveyService;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.Utility.CustomViewPager;
import com.example.user1.myapplication.Utility.ServicePassword;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = QuestionActivity.class.getSimpleName();
    private Bundle extras;
    private String categoryMainGroup;
    private CustomViewPager viewPager;
    private ArrayList<QuestionResponse> questions = new ArrayList<>();
    private Button previousBtn;
    private Button nextBtn;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        viewPager = findViewById(R.id.viewpager);

        extras = getIntent().getExtras();
        if (extras != null) {
            categoryMainGroup = extras.getString("extra_maingroup_id", "");
        }

        fetchQuestions(categoryMainGroup, ServicePassword.getPassword(this));

        previousBtn = findViewById(R.id.previous_btn);
        previousBtn.setVisibility(View.GONE);
        previousBtn.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1));

        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(v -> {
            //finish
            if (viewPager.getCurrentItem() == questions.size()) {
                DatabaseProvider dbProvider = DatabaseProvider.getInstance();
                dbProvider.insert(questions);
                finish();
            }
            //to preview
            else if (viewPager.getCurrentItem() == questions.size() - 1) {
                int position = viewPager.getCurrentItem();
                QuestionFragment previewFragment = (QuestionFragment) adapter.getItem(questions.size());
                QuestionFragment fragment = (QuestionFragment) adapter.getItem(position);
                questions.get(position).setJawabanUser(fragment.getAnswer());
                previewFragment.notifyDataSetChanged();
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            //to ma
            else if (questions.get(viewPager.getCurrentItem()).getTipe().equalsIgnoreCase("ma")) {
                final int position = viewPager.getCurrentItem();
                QuestionFragment fragment = (QuestionFragment) adapter.getItem(position);
                final QuestionResponse questionModel = questions.get(position);
                questionModel.setJawabanUser(fragment.getAnswers());
                Log.e(TAG, "onClick: " + questionModel.getJawabanUser() );
                viewPager.setCurrentItem(position + 1);
            }
            else {
                final int position = viewPager.getCurrentItem();
                QuestionFragment fragment = (QuestionFragment) adapter.getItem(position);
                final QuestionResponse questionModel = questions.get(position);
                final String answer = fragment.getAnswer();
                questionModel.setJawabanUser(answer);
                Log.e("answer", "onClick: " + answer);
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

    private void fetchQuestions(String kategory, String password) {
        Toast.makeText(this, "fetch questions......", Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("kategori", kategory);
            jsonObject.put("period", 1);
            jsonObject.put("password", password);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            Call<ArrayList<QuestionResponse>> getQuestions = service.getQuestions(jsonObject.toString());
            getQuestions.enqueue(new Callback<ArrayList<QuestionResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<QuestionResponse>> call, Response<ArrayList<QuestionResponse>> response) {
                    try {
                        questions.addAll(response.body());
                        initQuestions(questions);
                        Log.e("questions", "onResponse: " + questions.size());
                    } catch (Exception e) {
                        Toast.makeText(QuestionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<QuestionResponse>> call, Throwable t) {
                    Toast.makeText(QuestionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
}
