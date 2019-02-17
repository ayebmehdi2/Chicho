package com.mehdi.kikkik.Messaging;

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

public class AdapMessage extends RecyclerView.Adapter<AdapMessage.holder> {


    private ArrayList<Message> dataMessage = null;

    public void swapAdapter(ArrayList<Message> data){
        if (dataMessage == data) return;
        this.dataMessage = data;
        if (data != null){
            this.notifyDataSetChanged();
        }
    }

    class holder extends RecyclerView.ViewHolder{

        ImageView userI;
        ImageView cI;
        TextView cT;
        public holder(@NonNull View itemView) {
            super(itemView);
            userI = itemView.findViewById(R.id.user);
            cI = itemView.findViewById(R.id.ci);
            cT = itemView.findViewById(R.id.ct);


        }
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        Message message = dataMessage.get(i);

        if (message.getConStr() != null && message.getConImg() != null){
            holder.cI.setVisibility(View.VISIBLE);
            Picasso.get().load(message.getConImg()).into(holder.cI);
            holder.cT.setVisibility(View.VISIBLE);
            holder.cT.setText(message.getConStr());
        }else if (message.getConStr() != null && message.getConImg() == null){
            holder.cI.setVisibility(View.GONE);
            holder.cT.setVisibility(View.VISIBLE);
            holder.cT.setText(message.getConStr());
        }else if (message.getConStr() == null && message.getConImg() != null){
            holder.cI.setVisibility(View.VISIBLE);
            Picasso.get().load(message.getConImg()).into(holder.cI);
            holder.cT.setVisibility(View.GONE);
        }else {
            holder.cI.setVisibility(View.GONE);
            holder.cT.setVisibility(View.GONE);
            return;
        }

        Picasso.get().load(message.getUserImage()).into(holder.userI);
    }

    @Override
    public int getItemCount() {
        if (dataMessage == null){
            return 0;
        }
        return dataMessage.size();
    }

}
