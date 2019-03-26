package com.android.lixiang.nongbao.ui.activity

import android.content.Intent
import android.os.Bundle
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.ui.fragment.HomeFragment
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectFragment
import com.facebook.drawee.backends.pipeline.Fresco
import me.yokeyword.fragmentation.SupportActivity
import java.util.logging.Logger
import android.R.attr.data
import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.donkingliang.imageselector.utils.ImageSelector
import com.mapbox.mapboxsdk.Mapbox
import java.io.File
import me.yokeyword.fragmentation.anim.FragmentAnimator




class MainActivity : SupportActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Fresco.initialize(this)

        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null)

        if (findFragment(HomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_tab_container, HomeFragment().newInstance())
        }
    }

///////
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
////        if (resultCode == REQUEST_CODE){
////            val fragment = fragmentManager.findFragmentById(R.id.fl_tab_container)
////            fragment.onActivityResult(requestCode, resultCode, data)
////        }
////        val fragment = supportFragmentManager.findFragmentById(R.id.fl_tab_container)
////        fragment.onActivityResult(requestCode, resultCode, data)
//        if (data != null) {
//            val editor = mSharedPreferences!!.edit()
//            editor.putString("upload_images", data.getStringArrayListExtra(ImageSelector.SELECT_RESULT).toString())
//            editor.commit()
//        }
//
//
////        Glide.with(this).load(File(image[0])).into(InfoCollectFragment().image1!!)
//
//    }
}
