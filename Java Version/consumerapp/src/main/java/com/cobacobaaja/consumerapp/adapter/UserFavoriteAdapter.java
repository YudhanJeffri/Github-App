package com.cobacobaaja.consumerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cobacobaaja.consumerapp.R;
import com.cobacobaaja.consumerapp.activity.DetailActivity;
import com.cobacobaaja.consumerapp.model.User;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class UserFavoriteAdapter extends RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public UserFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setUserCursor(Cursor cursor){
        this.cursor = cursor;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }
    public User getUserItems(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Invalid");
        }
        return new User(cursor);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User user = getUserItems(position);
        holder.txtTitle.setText(user.getLogin());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(user.getAvatar_url())
                .resize(120,120)
                .into(holder.coverImage);
        holder.cardView.setOnClickListener(new CustomOnItemClickListener(holder.getAdapterPosition(), new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailActivity.EXTRA_USER, getUserItems(position));
                User user = new User();
                intent.putExtra("USER", user.getLogin());
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (cursor==null)return 0;
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            TextView txtTitle;
            CardView cardView;
            private ImageView coverImage;
            ViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                cardView = mView.findViewById(R.id.card_view);
                txtTitle = mView.findViewById(R.id.user_name);
                coverImage = mView.findViewById(R.id.user_image);
            }
    }
}
