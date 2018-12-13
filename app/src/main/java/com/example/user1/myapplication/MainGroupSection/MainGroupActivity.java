package com.example.user1.myapplication.MainGroupSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Network.SurveyClient;
import com.example.user1.myapplication.Network.SurveyService;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainGroupActivity extends AppCompatActivity implements onItemClickListener {

    private SharedPreferences preferences;
    private MainGroupAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isVisibilityOn = false;
    private ArrayList<MainGroupResponse> mainGroups = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_group);

        recyclerView = findViewById(R.id.recycler_view);
        preferences = getSharedPreferences("pref_user", MODE_PRIVATE);
        final String password = preferences.getString("user_password", "");

        adapter = new MainGroupAdapter(this, mainGroups);
        adapter.setListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getMainGroups(password);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_show:
                isVisibilityOn = !isVisibilityOn;
                if (isVisibilityOn)
                    item.setIcon(R.drawable.ic_action_visibility_on);
                else
                    item.setIcon(R.drawable.ic_action_visibility_off);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getMainGroups(String password){
        Toast.makeText(this, "fetching data.....", Toast.LENGTH_SHORT).show();
        try{
            JSONObject body = new JSONObject();
            body.put("password", password);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            Call<ArrayList<MainGroupResponse>> getMainGroupService = service.getMainGroups(body.toString());
            getMainGroupService.enqueue(new Callback<ArrayList<MainGroupResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<MainGroupResponse>> call, Response<ArrayList<MainGroupResponse>> response) {
                    try{
                        mainGroups.clear();
                        mainGroups.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e){
                        Toast.makeText(MainGroupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<MainGroupResponse>> call, Throwable t) {
                    Toast.makeText(MainGroupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClick(int position) {
        if(!isVisibilityOn) {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("extra_maingroup_id", mainGroups.get(position).getId());
            startActivity(intent);
        } else {
            Log.e("MainGroupActivity", "onItemClick: ");
            Intent intent = new Intent(this, AnswerHeadersActivity.class);
            startActivity(intent);
        }
    }
}
