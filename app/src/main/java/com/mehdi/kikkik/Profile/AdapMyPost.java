package com.mehdi.kikkik.Profile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehdi.kikkik.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapMyPost extends RecyclerView.Adapter<AdapMyPost.holder> {


    private ArrayList<String> dataMyPost = null;

    public void swapAdapter(ArrayList<String> data){
        if (dataMyPost == data) return;
        this.dataMyPost = data;
        if (data != null){
            this.notifyDataSetChanged();
        }
    }

    class holder extends RecyclerView.ViewHolder{

        ImageView conImage;
        TextView conText;

        public holder(@NonNull View itemView) {
            super(itemView);
            conImage = itemView.findViewById(R.id.con_img);
            conText = itemView.findViewById(R.id.con_tex);
        }
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_post, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        String da = dataMyPost.get(i);
        String tex = da.split(",")[0];
        String image = da.split(",")[1];
        if (tex != null && !(tex.equals("%%%%%"))){
            holder.conText.setVisibility(View.VISIBLE);
            holder.conText.setText(tex);
        }else {
            holder.conText.setVisibility(View.GONE);
        }
        if (image != null && !(image.equals("%%%%%"))){
            holder.conImage.setVisibility(View.VISIBLE);
            Picasso.get().load(image).into(holder.conImage);
        }else {
            holder.conImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (dataMyPost == null){
            return 0;
        }
        return dataMyPost.size();
    }
}
