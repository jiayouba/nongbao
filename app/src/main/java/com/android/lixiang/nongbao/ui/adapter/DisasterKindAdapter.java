package com.android.lixiang.nongbao.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lixiang.nongbao.R;
import com.android.lixiang.nongbao.ui.fragment.area3.DisasterKind;

import java.util.List;

public class DisasterKindAdapter extends ArrayAdapter<DisasterKind> {

    private int resourceId;
    private  int selectedItem=-1;

    public DisasterKindAdapter(Context context, int textViewResourceId, List<DisasterKind> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;


    }
    public View getView (int position, View convertView, ViewGroup parent){
        DisasterKind disasterKind = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//        ImageView disasterkindimageid = (ImageView) view.findViewById(R.id.disasterkind_picchoose);
        TextView disasterkindname = (TextView) view.findViewById(R.id.disasterkind_name);
//        disasterkindimageid.setImageResource(disasterKind.getImageId());
        disasterkindname.setText(disasterKind.getBank_name());

//        if (position == selectedItem) {
//
//            disasterkindimageid.setImageResource(R.drawable.img_nike);
//        }



        return view;

    }

}
