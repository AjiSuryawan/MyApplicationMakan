package com.example.user1.myapplication.AnswerHeadersSection;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.ObjectSurvey;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class AnswerHeadersAdapter extends RecyclerView.Adapter<AnswerHeadersAdapter.AHViewHolder> {
    MainGroupResponse mainGroupResponse;
    private Context context;
    private ArrayList<ObjectSurvey> objectSurveys;
    private onItemClickListener listener;

    public AnswerHeadersAdapter(Context context, ArrayList<ObjectSurvey> objectSurveys, MainGroupResponse mainGroupResponse) {
        this.context = context;
        this.objectSurveys = objectSurveys;
        this.mainGroupResponse = mainGroupResponse;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AHViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer_header, viewGroup, false);
        return new AHViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AHViewHolder ahViewHolder, int i) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/MontserratRegular.ttf");

        ahViewHolder.tvIdUser.setTypeface(font);
        ahViewHolder.setText(objectSurveys.get(i));
    }

    @Override
    public int getItemCount() {
        return objectSurveys.size();
    }

    class AHViewHolder extends RecyclerView.ViewHolder {

        private TextView tvIdUser;
        private TextView tvStatus;

        public AHViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdUser = itemView.findViewById(R.id.tv_id);
            tvStatus = itemView.findViewById(R.id.txtstatus);
            itemView.setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));

        }

        public void setText(ObjectSurvey objectSurvey) {
            String tmp = "";
            for (int i = 0; i < objectSurvey.getAnswerHeader().size(); i++) {
                String jawab = objectSurvey.getAnswerHeader().get(i);
                String tanya = mainGroupResponse.getAnswerHeaderFields().get(i);
                tmp += tanya + " : " + jawab + "\n";
            }
            tvIdUser.setText(tmp);
            if (!objectSurvey.isStatus()) {
                tvStatus.setText("not synchronized");
                tvStatus.setTextColor(Color.parseColor("#1637ad"));
            } else {
                tvStatus.setText("synchronized");
                tvStatus.setTextColor(Color.parseColor("#29d820"));
            }
        }
    }
}
