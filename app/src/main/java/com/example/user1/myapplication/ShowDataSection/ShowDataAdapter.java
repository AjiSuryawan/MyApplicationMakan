package com.example.user1.myapplication.ShowDataSection;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class ShowDataAdapter extends RecyclerView.Adapter<ShowDataAdapter.MGViewHolder> {

    private Context context;
    private ArrayList<MainGroupResponse> mainGroups;
    private onItemClickListener listener;
    private ArrayList<Integer> image;

    public ShowDataAdapter(Context context, ArrayList<MainGroupResponse> mainGroups, ArrayList<Integer> image) {
        this.context = context;
        this.mainGroups = mainGroups;
        this.image = image;
    }

    public void setListener(onItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MGViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_data, viewGroup, false);
        return new MGViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MGViewHolder mgViewHolder, int i) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/MontserratRegular.ttf");

        MainGroupResponse mainGroup = mainGroups.get(i);
        mgViewHolder.tvMainGroupTitle.setTypeface(font);
        mgViewHolder.setText(mainGroup);
        mgViewHolder.iconiasi.setImageResource(image.get(i));
    }

    @Override
    public int getItemCount() {
        return mainGroups.size();
    }

    class MGViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMainGroupTitle;
        private ImageView iconiasi;
        public MGViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMainGroupTitle = itemView.findViewById(R.id.tv_maingroup_title);
            iconiasi = itemView.findViewById(R.id.iconisasi);

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
