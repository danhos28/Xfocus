package com.example.xfocus.HomePages.KategoriRecycleNav;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xfocus.R;

import java.util.ArrayList;

public class KategoriRecyclerNavAdp extends RecyclerView.Adapter<KategoriRecyclerNavAdp.katRecycleNavViewHolder> {
    private ArrayList<KategoriRecyclerNav> items;
    int row_index=-1;
    UpdateRecyclerView updateRecyclerView;
    Activity activity;
    boolean check = true;
    boolean select = true;

    public KategoriRecyclerNavAdp(ArrayList<KategoriRecyclerNav> items, Activity activity, UpdateRecyclerView updateRecyclerView) {
        this.items = items;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;
    }

    @NonNull
    @Override
    public katRecycleNavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_nav_item,parent,false);
        katRecycleNavViewHolder katRecycleNavViewHolder = new katRecycleNavViewHolder(view);
        return katRecycleNavViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull katRecycleNavViewHolder holder, final int position) {
        KategoriRecyclerNav currentItem = items.get(position);
        holder.imageView.setImageResource(currentItem.getImage());
        holder.textView.setText(currentItem.getText());

        if (check){
            ArrayList<MenuRecycler> items = new ArrayList<MenuRecycler>();
            items.add(new MenuRecycler("Master 1", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 2", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 3", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 4", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 5", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 6", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 7", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 8", R.drawable.ic_master));
            items.add(new MenuRecycler("Master 9", R.drawable.ic_master));

            updateRecyclerView.callback(position,items);

            check = false;
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

                if (position == 0){
                    ArrayList<MenuRecycler> items = new ArrayList<MenuRecycler>();
                    items.add(new MenuRecycler("Master 1", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 2", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 3", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 4", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 5", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 6", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 7", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 8", R.drawable.ic_master));
                    items.add(new MenuRecycler("Master 9", R.drawable.ic_master));

                    updateRecyclerView.callback(position,items);
                }
                else if(position==1){
                    ArrayList<MenuRecycler> items = new ArrayList<MenuRecycler>();
                    items.add(new MenuRecycler("Pembelian 1", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 2", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 3", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 4", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 5", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 6", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 7", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 8", R.drawable.ic_pembelian));
                    items.add(new MenuRecycler("Pembelian 9", R.drawable.ic_pembelian));

                    updateRecyclerView.callback(position,items);
                }
                else if(position==2){
                    ArrayList<MenuRecycler> items = new ArrayList<MenuRecycler>();
                    items.add(new MenuRecycler("Gudang 1", R.drawable.ic_store));
                    items.add(new MenuRecycler("Gudang 2", R.drawable.ic_store));
                    items.add(new MenuRecycler("Gudang 3", R.drawable.ic_store));
                    items.add(new MenuRecycler("Gudang 4", R.drawable.ic_store));
                    items.add(new MenuRecycler("Gudang 5", R.drawable.ic_store));

                    updateRecyclerView.callback(position,items);
                }
                else if(position==3){
                    ArrayList<MenuRecycler> items = new ArrayList<MenuRecycler>();
                    items.add(new MenuRecycler("Produksi 1", R.drawable.ic_produksi));
                    items.add(new MenuRecycler("Produksi 2", R.drawable.ic_produksi));
                    items.add(new MenuRecycler("Produksi 3", R.drawable.ic_produksi));
                    items.add(new MenuRecycler("Produksi 4", R.drawable.ic_produksi));
                    items.add(new MenuRecycler("Produksi 5", R.drawable.ic_produksi));
                    items.add(new MenuRecycler("Produksi 6", R.drawable.ic_produksi));

                    updateRecyclerView.callback(position,items);
                }
                else if(position==4){
                    ArrayList<MenuRecycler> items = new ArrayList<MenuRecycler>();
                    items.add(new MenuRecycler("Penjualan 1", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 2", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 3", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 4", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 5", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 6", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 7", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 8", R.drawable.ic_penjualan));
                    items.add(new MenuRecycler("Penjualan 9", R.drawable.ic_penjualan));

                    updateRecyclerView.callback(position,items);
                }
            }
        });

        if(select){
            if (position==0)
                holder.linearLayout.setBackgroundResource(R.drawable.navbar_bg);
            select = false;
        }
        else{
            if (row_index == position){
                holder.linearLayout.setBackgroundResource(R.drawable.navbar_selected_bg);
            }
            else{
                holder.linearLayout.setBackgroundResource(R.drawable.navbar_bg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class katRecycleNavViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;

        public katRecycleNavViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textNav);
            imageView = itemView.findViewById(R.id.imageNav);
            linearLayout = itemView.findViewById(R.id.navbar_linearLayout);

        }
    }
}
