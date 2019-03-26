package com.android.lixiang.nongbao.ui.fragment.area2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.activity.ShowImageActivity
import com.android.lixiang.nongbao.ui.fragment.ChooseBankFragment
import com.android.lixiang.nongbao.ui.fragment.ChooseSpeciesNameFragment
import com.android.lixiang.nongbao.ui.fragment.area1.IdInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area3.FillSurveyInfoFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.donkingliang.imageselector.ImageSelectorActivity
import com.donkingliang.imageselector.utils.ImageSelector
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_accept_insurance.*
import kotlinx.android.synthetic.main.fragment_info_collect.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.io.File


class AcceptInsuranceFragment : SupportFragment(), View.OnClickListener {
    private val REQUEST_CODE = 0x00000011
    private val OPTIONS = RequestOptions().centerCrop()
    private var IMAGE2_FLAG = false
    private var IMAGE3_FLAG = false
    private var IMAGE4_FLAG = false
    private var mImagesArray: MutableList<String>? = mutableListOf()
    private var mImages: Array<String>? = arrayOf()
    private var mSharedPreferences: SharedPreferences? = null
    private var MAX_IMAGE = 4
    private var flag1 = false
    private var flag2 = false
    private var flag3 = false
    private var flag4 = false
    private var flag5 = false
    private var mCommitInfoFlag = false

    override fun onClick(v: View?) {
        when (v) {
            mAcceptInsuranceIdInfoRL -> {
                start(IdInfoFragment().newInstance())
            }

            mAcceptInsuranceAddressRl -> {
                start(AddressInfoFragment().newInstance())
            }
            mBankRL -> {
                start(ChooseBankFragment().newInstance())
            }
            mInsureCountRL -> {
                start(InsureCountFragment().newInstance())
            }

            mAcceptInsuranceCommitRL -> {
                start(SuccessAcceptFragment().newInstance())
            }
            mSpeciesNameRL -> {
                start(ChooseSpeciesNameFragment().newInstance())

            }
            mMyInsuranceCheckRL -> {
                start(FillSurveyInfoFragment().newInstance())
            }
            mAIUploadImagesIV -> {
                selectImage()
            }
            mAIImage1 -> {
                startFullScreenActivity(0, mAIImage1)
            }
            mAIImage2 -> {
                if (!IMAGE2_FLAG) {
                    selectImage()
                } else {
//                    startActivity<ShowImageActivity>()
//                    startPictureActivity(mImage2)
                    startFullScreenActivity(1, mAIImage2)
                }
            }
            mAIImage3 -> {
                if (!IMAGE3_FLAG) {
                    selectImage()
                } else {
                    startFullScreenActivity(2, mAIImage3)
                }
            }
            mAIImage4 -> {
                if (!IMAGE4_FLAG) {
                    selectImage()
                } else {
                    startFullScreenActivity(3, mAIImage4)
                }
            }
        }
    }

    private fun initViews() {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        mAcceptInsuranceToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mAcceptInsuranceToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mAcceptInsuranceToolbar.setNavigationOnClickListener {
            pop()
        }
        mSharedPreferences = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)
    }

    private fun checkCommitFlag(){
        if (flag1 && flag2 && flag3
//                && flag4
                && flag5){
            mCommitInfoRL.setBackgroundColor(Color.parseColor("#6299FF"))
            mCommitInfoFlag = true
        }else{
            mCommitInfoRL.setBackgroundColor(Color.parseColor("#C5C5C5"))
            mCommitInfoFlag = false
        }
    }

    fun newInstance(): AcceptInsuranceFragment {
        val args = Bundle()
        val fragment = AcceptInsuranceFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_insurance, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        if (arguments!!.getString("fromMyInsure") != null){
            Log.e("string",arguments!!.getString("fromMyInsure"))
            if(arguments!!.getString("fromMyInsure").equals("MyInsure")){
                mAcceptInsuranceCommitRL.visibility=View.INVISIBLE
                mMyInsuranceCheckRL.visibility=View.VISIBLE
            }
        }
        mAIUploadImagesIV.setOnClickListener(this)
        mAIImage1.setOnClickListener(this)
        mAIImage2.setOnClickListener(this)
        mAIImage3.setOnClickListener(this)
        mAIImage4.setOnClickListener(this)
        mAcceptInsuranceIdInfoRL.setOnClickListener(this)
        mAcceptInsuranceAddressRl.setOnClickListener(this)
        mInsureCountRL.setOnClickListener(this)
        mAcceptInsuranceCommitRL.setOnClickListener(this)
        mBankRL.setOnClickListener(this)
        mSpeciesNameRL.setOnClickListener(this)
        mMyInsuranceCheckRL.setOnClickListener(this)
        mImages = arrayOf("0", "0", "0", "0")
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {

        super.onSupportVisible()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            mAIUploadImagesIV.visibility = View.GONE
            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
            val editor = mSharedPreferences!!.edit()
            editor.putString("upload_images", images.toString())
            editor.commit()


            val sp = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)
            var currImages = sp.getString("upload_images", "").substring(1, sp.getString("upload_images", "").length - 1)
            mImagesArray!!.addAll(currImages.split(",") as MutableList<String>)
            Logger.d(mImagesArray)
            for (i in 0 until mImagesArray!!.size) {
                mImagesArray!![i] = mImagesArray!![i].trim()
                mImages!![i] = mImagesArray!![i]
            }
            Logger.d(mImages)
            when (mImagesArray!!.size) {
                1 -> setImage1(mImagesArray!![0])
                2 -> setImage12(mImagesArray!![0], mImagesArray!![1])
                3 -> setImage123(mImagesArray!![0], mImagesArray!![1], mImagesArray!![2])
                4 -> setImage1234(mImagesArray!![0], mImagesArray!![1], mImagesArray!![2], mImagesArray!![3])
            }
        }
    }
    private fun setImage1(url: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        MAX_IMAGE -= 1
        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)


    }

    private fun setImage12(url: String, url2: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        mAIImage3.visibility = View.VISIBLE
        MAX_IMAGE -= 2
        IMAGE2_FLAG = true

        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
//        mAIImage2.setBackgroundColor(Color.parseColor("#00000000"))
        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mAIImage2)

    }

    private fun setImage123(url: String, url2: String, url3: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        mAIImage3.visibility = View.VISIBLE
        mAIImage4.visibility = View.VISIBLE
        MAX_IMAGE -= 3
        IMAGE2_FLAG = true
        IMAGE3_FLAG = true

        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage2.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage3.setBackgroundColor(Color.parseColor("#00000000"))

        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mAIImage2)
        Glide.with(this).load(File(url3.trim())).apply(OPTIONS).into(mAIImage3)
    }

    private fun setImage1234(url: String, url2: String, url3: String, url4: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        mAIImage3.visibility = View.VISIBLE
        mAIImage4.visibility = View.VISIBLE
        IMAGE2_FLAG = true
        IMAGE3_FLAG = true
        IMAGE4_FLAG = true


        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage2.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage3.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage4.setBackgroundColor(Color.parseColor("#00000000"))

        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mAIImage2)
        Glide.with(this).load(File(url3.trim())).apply(OPTIONS).into(mAIImage3)
        Glide.with(this).load(File(url4.trim())).apply(OPTIONS).into(mAIImage4)

    }
    private fun selectImage() {
        val intent = Intent(activity, ImageSelectorActivity::class.java)
        intent.putExtra(ImageSelector.MAX_SELECT_COUNT, MAX_IMAGE)
        intent.putExtra(ImageSelector.IS_SINGLE, false)
        intent.putExtra(ImageSelector.USE_CAMERA, true)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun startFullScreenActivity(position: Int, view: View) {
        var intent = Intent(activity, ShowImageActivity::class.java)
        var bundle = Bundle()
        bundle.putStringArray("IMAGES", mImages!!)
        bundle.putString("INDEX", position.toString())
        intent.putExtras(bundle)
//        startActivity(intent)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!, view, "image")
        try {
            ActivityCompat.startActivity(activity!!, intent, optionsCompat.toBundle())
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            startActivity(intent)
        }
    }
}
