package com.android.lixiang.nongbao.ui.fragment

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectEntryFragment
import com.android.lixiang.nongbao.ui.fragment.area2.FastInsureFragment
import com.android.lixiang.nongbao.ui.fragment.area3.AssistSurveyFragment
import com.android.lixiang.nongbao.ui.fragment.user.LoginFragment
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment
import android.widget.Toast
import com.android.lixiang.nongbao.ui.activity.MainActivity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import com.android.lixiang.base.utils.view.AlignedTextUtils
import com.donkingliang.imageselector.ClipImageActivity
import com.donkingliang.imageselector.ImageSelectorActivity
import com.donkingliang.imageselector.utils.ImageSelector
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_info_collect.*
import java.io.File


class HomeFragment : SupportFragment(), View.OnClickListener {


    private var isLogedinFlag = false
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        handlePermisson()
        val rl = RelativeLayout(activity)
        val rlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (DimenUtil().getScreenHeight(context!!) - SizeUtils.getMeasuredHeight(mLineaeLayout)) + 200)
        rl.layoutParams = rlParams
        rl.setBackgroundColor(Color.parseColor("#FFFFFF"))
        mLineaeLayout.addView(rl, rlParams)

        wind_direction.text = AlignedTextUtils.formatText("风  向：")
        dress_index.text = AlignedTextUtils.formatText("穿衣指数：")
        ultraviolet_index.text = "紫外线指数："
    }

    override fun onClick(v: View?) {
        when (v) {
            mUserNameRL -> {
//                if (!isLogedinFlag) {
//                    start(LoginFragment().newInstance())
//                    isLogedinFlag = true
//                } else{
//                    selectAvatar()
//                    isLogedinFlag = false
//                }
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
            mSeeAllAppsTV -> {
//                start(AllAppsFragment().newInstance())
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
            mInfoCollectRL -> {
                start(InfoCollectEntryFragment().newInstance())
//                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
//                snackBar.show()
            }
            mFastInsureRL -> {
//                start(FastInsureFragment().newInstance())
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }

            mAssistSurveyRL -> {
//                start(AssistSurveyFragment().newInstance())
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }

            mPigRL -> {
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
        }
    }

    private fun selectAvatar() {
        val intent = Intent(activity, ClipImageActivity::class.java)
        intent.putExtra(ImageSelector.MAX_SELECT_COUNT, 1)
        intent.putExtra(ImageSelector.IS_SINGLE, true)
        intent.putExtra(ImageSelector.USE_CAMERA, true)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && data != null) {
            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
            Logger.d(images)
            mAvatarImageView.setImageURI(Uri.fromFile(File(images[0])))
        }
    }

    fun newInstance(): HomeFragment {
        val args = Bundle()
        val fragment = HomeFragment()
        fragment.arguments = args
        return fragment
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun handlePermisson() {
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
        val checkSelfPermission = ActivityCompat.checkSelfPermission(activity!!, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
            } else {
                myRequestPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        mUserNameRL.setOnClickListener(this)
        mSeeAllAppsTV.setOnClickListener(this)
        mInfoCollectRL.setOnClickListener(this)
        mFastInsureRL.setOnClickListener(this)
        mAssistSurveyRL.setOnClickListener(this)
        mPigRL.setOnClickListener(this)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}
