package com.cobacobaaja.consumerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cobacobaaja.consumerapp.R;
import com.cobacobaaja.consumerapp.activity.DetailActivity;
import com.cobacobaaja.consumerapp.model.User;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {

    private static List<User> dataList;
    private Context context;
    public void setTasks(List<User> personList) {
        this.dataList.clear();
        this.dataList.addAll(personList);
        notifyDataSetChanged();
    }

    public UserAdapter(Context context,List<User> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView txtTitle;
        CardView cardView;
        private ImageView coverImage;
        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            cardView = mView.findViewById(R.id.card_view);
            txtTitle = mView.findViewById(R.id.user_name);
            coverImage = mView.findViewById(R.id.user_image);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(holder.getAdapterPosition()).getLogin());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(holder.getAdapterPosition()).getAvatar_url())
                .resize(120,120)
                .into(holder.coverImage);
        holder.cardView.setOnClickListener(new CustomOnItemClickListener(holder.getAdapterPosition(), new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailActivity.EXTRA_USER, dataList.get(holder.getAdapterPosition()));
                User user = new User();
                intent.putExtra("USER", user.getLogin());
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}