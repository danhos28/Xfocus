package com.example.xfocus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListHutangAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> rlabel = new ArrayList<>();
    ArrayList<String> hutangvalue = new ArrayList<>();
    ArrayList<String> piutangvalue = new ArrayList<>();

    public ListHutangAdapter(@NonNull Context c, ArrayList<String> label, ArrayList<String> Hutangvalue,ArrayList<String> Piutangvalue) {
        super(c,R.layout.listhutangpiutang,R.id.labelPiutang, label);
        this.context = c;
        this.rlabel = label;
        this.hutangvalue = Hutangvalue;
        this.piutangvalue = Piutangvalue;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listHutangPiutang = layoutInflater.inflate(R.layout.listhutangpiutang,parent,false);
        TextView myLabel = listHutangPiutang.findViewById(R.id.labelHutang);
        TextView myValue = listHutangPiutang.findViewById(R.id.valueHutang);
        TextView myLabel2 = listHutangPiutang.findViewById(R.id.labelPiutang);
        TextView myValue2 = listHutangPiutang.findViewById(R.id.valuePiutang);
        myLabel.setText(rlabel.get(position));
        myValue.setText(hutangvalue.get(position));
        myLabel2.setText(rlabel.get(position));
        myValue2.setText(piutangvalue.get(position));

        return listHutangPiutang;
    }
}
