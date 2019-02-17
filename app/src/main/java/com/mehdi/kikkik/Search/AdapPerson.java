package com.mehdi.kikkik.Search;

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

public class AdapPerson extends RecyclerView.Adapter<AdapPerson.holder>{


private ArrayList<DataPerson> dataPerson = null;

public interface clickPrson{
    void follow(String uid);
    void msg(String uid);
}

private final clickPrson clickPrson;

public AdapPerson(clickPrson clickPrson){
    this.clickPrson = clickPrson;
}

public void swapAdapter(ArrayList<DataPerson> data){
        if (dataPerson == data) return;
        this.dataPerson = data;
        if (data != null){
        this.notifyDataSetChanged();
        }
        }

class holder extends RecyclerView.ViewHolder{

    ImageView userImage;
    TextView userName;
    ImageView follow;
    ImageView msgToPerson;
    public holder(@NonNull View itemView) {
        super(itemView);
        userImage = itemView.findViewById(R.id.im);
        userName = itemView.findViewById(R.id.user_name);
        follow = itemView.findViewById(R.id.follow);
        msgToPerson = itemView.findViewById(R.id.msg_to_person);

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPrson.follow(dataPerson.get(getAdapterPosition()).getUid());
            }
        });

        msgToPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPrson.msg(dataPerson.get(getAdapterPosition()).getUid());
            }
        });
    }
}

    @NonNull
    @Override
    public  holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new  holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_person, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  holder holder, int i) {
    DataPerson data = dataPerson.get(i);
        holder.userName.setText(data.getImg());
        Picasso.get().load(data.getName()).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        if (dataPerson == null){
            return 0;
        }
        return dataPerson.size();
    }
}
