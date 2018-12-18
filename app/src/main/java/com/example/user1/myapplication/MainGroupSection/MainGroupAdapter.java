package com.example.user1.myapplication.MainGroupSection;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class MainGroupAdapter extends RecyclerView.Adapter<MainGroupAdapter.MGViewHolder> {

    private Context context;
    private ArrayList<MainGroupResponse> mainGroups;
    private onItemClickListener listener;


    public MainGroupAdapter(Context context, ArrayList<MainGroupResponse> mainGroups) {
        this.context = context;
        this.mainGroups = mainGroups;

    }

    public void setListener(onItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MGViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_group, viewGroup, false);
        return new MGViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MGViewHolder mgViewHolder, int i) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/MontserratRegular.ttf");

        MainGroupResponse mainGroup = mainGroups.get(i);
        mgViewHolder.tvMainGroupTitle.setTypeface(font);
        mgViewHolder.setText(mainGroup);
        mgViewHolder.iconisasi.setImageResource(R.drawable.family);
    }

    @Override
    public int getItemCount() {
        return mainGroups.size();
    }

    class MGViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMainGroupTitle;
        private ImageView iconisasi;
        public MGViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMainGroupTitle = itemView.findViewById(R.id.tv_maingroup_title);
            iconisasi = itemView.findViewById(R.id.iconisasi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        void setText(MainGroupResponse mainGroup){
            tvMainGroupTitle.setText(mainGroup.getDescription());
        }
    }
}
