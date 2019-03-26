package com.android.lixiang.nongbao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lixiang.nongbao.R;
import com.android.lixiang.nongbao.ui.Bank;

import java.util.List;

public class BankAdapter extends ArrayAdapter<Bank> {

    private int resourceId;
    private  int selectedItem=-1;

    public BankAdapter(Context context, int textViewResourceId,List<Bank> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;


    }
        public View getView ( int position, View convertView, ViewGroup parent){
            Bank bank = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ImageView imageid = (ImageView) view.findViewById(R.id.picchoose);
            TextView bankname = (TextView) view.findViewById(R.id.bank_name);
             imageid.setImageResource(bank.getImageId());
            bankname.setText(bank.getBank_name());

            if (position == selectedItem) {

                imageid.setImageResource(R.drawable.img_rednike);
            }



            return view;

        }

}
