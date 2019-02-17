package com.mehdi.kikkik;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.holder> {

    private ArrayList<DATA_POST> data_posts;

    public interface Click{
        void like(String uid);
        void comment(String uid);
        void user(String uid);
    }

    class holder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName;
        ImageView like;
        TextView time;
        ImageView comment;
        TextView content;
        ImageView imageContent;
        TextView numLike;
        TextView numComment;
        public holder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.img);
            userName = itemView.findViewById(R.id.name);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.commnet);
            content = itemView.findViewById(R.id.content);
            numLike = itemView.findViewById(R.id.num_likes);
            numComment = itemView.findViewById(R.id.num_comment);
            imageContent = itemView.findViewById(R.id.ci);
            time = itemView.findViewById(R.id.time);

                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.like(data_posts.get(getAdapterPosition()).getUid());
                    }
                });
                comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.comment(data_posts.get(getAdapterPosition()).getUid());
                    }
                });
                userImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.user(data_posts.get(getAdapterPosition()).getUid());
                    }

             });
        }


    }

    public void swapAdapter(ArrayList<DATA_POST> posts){
        if (data_posts == posts)return;
        this.data_posts = posts;
        if (posts != null) this.notifyDataSetChanged();
    }

    private final Click click;
    private Context context;

    public AdapterPost( Context context, Click click){
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        DATA_POST post = data_posts.get(i);

        if (post == null) return;
        if (post.getContent() == null && post.getContentImage() == null) return;

        if (post.getContent() != null) {
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText(post.getContent());
        }else {
            holder.content.setVisibility(View.GONE);
        }


        if (post.getContentImage() != null) {
            holder.imageContent.setVisibility(View.VISIBLE);
            Picasso.get().load(post.getContentImage()).into(holder.imageContent);
        }else {
            holder.imageContent.setVisibility(View.GONE);
        }

        Picasso.get().load(Uri.parse(post.getImg())).into(holder.userImage);
        holder.userName.setText(post.getName());
        holder.time.setText(new SimpleDateFormat("dd MM yyyy").format(new Date()));
        holder.numLike.setText(String.valueOf(post.getNumLike()));
        holder.numComment.setText(String.valueOf(post.getNumComment()));
        if (post.getIsLike()){
            holder.like.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like1));
        }else {
            holder.like.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like2));
        }
        if (post.getIsComment()){
            holder.comment.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_c1));
        }else {
            holder.comment.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_c));
        }
    }

    @Override
    public int getItemCount() {
        if (data_posts == null){
            return 0;

        }
        return data_posts.size();
    }
}
