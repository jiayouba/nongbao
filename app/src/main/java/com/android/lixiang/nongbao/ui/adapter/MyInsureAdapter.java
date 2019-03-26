package com.android.lixiang.nongbao.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.android.lixiang.nongbao.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MyInsureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MyInsureAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.mMyInsureNumberTV,item);
    }
}
