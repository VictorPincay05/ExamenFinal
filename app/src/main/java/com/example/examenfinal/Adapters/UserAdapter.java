package com.example.examenfinal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.examenfinal.Models.User;
import com.example.examenfinal.R;
import com.example.examenfinal.UserDetailsActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.name.first + " " + user.name.last);
        holder.country.setText(user.location.country);
        holder.email.setText(user.email);

        Glide.with(context)
                .load(user.picture.thumbnail)
                .into(holder.imageView);

        // Configurar OnClickListener para abrir la actividad de detalles
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserDetailsActivity.class);
            intent.putExtra("name", user.name.first + " " + user.name.last);
            intent.putExtra("email", user.email);
            intent.putExtra("age", user.dob.age);
            intent.putExtra("phone", user.phone);
            intent.putExtra("cell", user.cell);
            intent.putExtra("nationality", user.nat);
            intent.putExtra("idName", user.id.name);
            intent.putExtra("idValue", user.id.value);
            intent.putExtra("country", user.location.country);
            intent.putExtra("imageUrl", user.picture.large);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, country, email;
        ImageView imageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            country = itemView.findViewById(R.id.textViewCountry);
            email = itemView.findViewById(R.id.textViewEmail);
            imageView = itemView.findViewById(R.id.imageViewUser);
        }
    }
}
