package com.example.xfocus.HomePages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xfocus.HomePages.KategoriRecycleNav.KategoriRecyclerNav;
import com.example.xfocus.HomePages.KategoriRecycleNav.KategoriRecyclerNavAdp;
import com.example.xfocus.HomePages.KategoriRecycleNav.MenuRecycler;
import com.example.xfocus.HomePages.KategoriRecycleNav.MenuRecyclerAdp;
import com.example.xfocus.HomePages.KategoriRecycleNav.UpdateRecyclerView;
import com.example.xfocus.R;

import java.util.ArrayList;

public class Kategori_Fragment extends Fragment implements UpdateRecyclerView {
    private RecyclerView recyclerView,recyclerView2;
    private KategoriRecyclerNavAdp kategoriRecycleNavAdp;

    ArrayList<MenuRecycler> items = new ArrayList<>();
    MenuRecyclerAdp menuRecyclerAdp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_kategori,container,false);

        ArrayList<KategoriRecyclerNav> item = new ArrayList<>();
        item.add(new KategoriRecyclerNav(R.drawable.ic_master,"Master"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_pembelian,"Pembelian"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_store,"Gudang"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_produksi,"Produksi"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_penjualan,"Penjualan"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_finance,"Finance"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_aset,"Aset"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_accounting,"Accounting"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_quality,"Quality"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_exim,"Exim"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_pengingat,"Pengingat"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_komisi,"Komisi"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_auditlog,"Audit Log"));
        item.add(new KategoriRecyclerNav(R.drawable.ic_laporan,"Laporan"));

        recyclerView = root.findViewById(R.id.rv_1);
        kategoriRecycleNavAdp = new KategoriRecyclerNavAdp(item, getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(kategoriRecycleNavAdp);

        items = new ArrayList<>();

        recyclerView2 = root.findViewById(R.id.rv_2);
        menuRecyclerAdp = new MenuRecyclerAdp(items);
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        recyclerView2.setAdapter(menuRecyclerAdp);

        return root;
    }

    public void callback(int position, ArrayList<MenuRecycler> items) {
        menuRecyclerAdp = new MenuRecyclerAdp(items);
        menuRecyclerAdp.notifyDataSetChanged();
        recyclerView2.setAdapter(menuRecyclerAdp);
    }
}
