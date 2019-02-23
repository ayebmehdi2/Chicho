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

public class AdapPersonMessaging extends RecyclerView.Adapter<AdapPersonMessaging.holder> {

    private ArrayList<UserInfo> dataMessage = null;

    public AdapPersonMessaging(clickMessagingPerson click) {
        this.click = click;
    }

    public void swapAdapter(ArrayList<UserInfo> data){
        if (dataMessage == data) return;
        this.dataMessage = data;
        if (data != null){
            this.notifyDataSetChanged();
        }
    }

    public interface clickMessagingPerson{
        void clickAllItem(String chiledName);
    }

    private final clickMessagingPerson click;

    class holder extends RecyclerView.ViewHolder{

        ImageView userI;
        TextView name;
        TextView lastMsg;
        public holder(@NonNull View itemView) {
            super(itemView);
            userI = itemView.findViewById(R.id.user_i);
            name = itemView.findViewById(R.id.user_name);
            lastMsg = itemView.findViewById(R.id.last_msg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.clickAllItem(dataMessage.get(getAdapterPosition()).getChildName());
                }
            });
        }
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_messagin_person, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        UserInfo userInfo = dataMessage.get(i);

        if (userInfo.getYourPhoto() != null){
            Picasso.get().load(userInfo.getYouName()).into(holder.userI);
        }

        if (userInfo.getYouName()!=null){
            holder.name.setText(userInfo.getYourPhoto());
        }

        if (userInfo.getLastMessage() != null){
            holder.lastMsg.setText(userInfo.getLastMessage());
        }

    }

    @Override
    public int getItemCount() {
        if (dataMessage == null){
            return 0;
        }
        return dataMessage.size();
    }

}
