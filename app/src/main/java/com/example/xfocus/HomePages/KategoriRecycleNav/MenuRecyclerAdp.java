package com.example.xfocus.HomePages.KategoriRecycleNav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xfocus.R;

import java.util.ArrayList;

public class MenuRecyclerAdp extends RecyclerView.Adapter<MenuRecyclerAdp.MenuRecyclerHolder>{

    public ArrayList<MenuRecycler> menuRecycler;

    public MenuRecyclerAdp(ArrayList<MenuRecycler> menuRecycler){
        this.menuRecycler = menuRecycler;
    }

    public class MenuRecyclerHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        ConstraintLayout constraintLayout;
        public MenuRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_icon);
            textView = itemView.findViewById(R.id.menu_name);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Clicked -> "+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public MenuRecyclerAdp.MenuRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout,parent,false);
        MenuRecyclerHolder menuRecyclerHolder=new MenuRecyclerHolder(view);
        return menuRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerAdp.MenuRecyclerHolder holder, int position) {
        MenuRecycler currentItem = menuRecycler.get(position);
        holder.imageView.setImageResource(currentItem.getImage());
        holder.textView.setText(currentItem.getName());

    }

    @Override
    public int getItemCount() {
        return menuRecycler.size();
    }


}