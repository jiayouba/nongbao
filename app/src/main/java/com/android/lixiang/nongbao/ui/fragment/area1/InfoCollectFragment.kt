package com.android.lixiang.nongbao.ui.fragment.area1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.presenter.InfoCollectPresenter
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.injection.component.DaggerInfoCollectFragmentComponent

import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectModule
import com.android.lixiang.nongbao.presenter.view.InfoCollectPictureView
import com.android.lixiang.nongbao.presenter.view.InfoCollectView
import com.android.lixiang.nongbao.ui.activity.ShowImageActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.donkingliang.imageselector.ImageSelectorActivity
import com.donkingliang.imageselector.utils.ImageSelector
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_info_collect.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.io.File
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import es.dmoral.toasty.Toasty
import me.yokeyword.fragmentation.SupportFragmentDelegate
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class InfoCollectFragment : BaseMvpFragment<InfoCollectPresenter>(), View.OnClickListener, InfoCollectView, InfoCollectPictureView {

    private var loginNameId: String = ""
    private var plotNumber: String = ""
    private var insurer: String = ""
    private var identityType: String = ""
    private var idNumber: String = ""
    private var insurerAddress: String = ""
    private var phoneNumber: String = ""
    private var plantingPlace: String = ""
    private var insuranceAmount: String = ""
    private var shape: String = ""
    private var collectingPictureName: String = ""
    private val REQUEST_CODE = 0x00000011
    private val OPTIONS = RequestOptions().centerCrop()
    private var IMAGE2_FLAG = false
    private var IMAGE3_FLAG = false
    private var IMAGE4_FLAG = false
    private var LOCATION_POINTS: String? = ""
    private var LOCATION_CENTER: String? = ""
    private var INSURER_ADDRESS: String? = ""
    private var PLANTING_PLACE: String? = ""
    private var mImagesArray: MutableList<String>? = mutableListOf()
    private var mImages: Array<String>? = arrayOf()
    private var mIdIndex = -1
    private var mIdNumber: String? = ""
    private var mIdTypeList: MutableList<String> = mutableListOf("身份证", "组织机构代码", "统一社会信用代码", "其他")
    private var mSharedPreferences: SharedPreferences? = null
    private var MAX_IMAGE = 4
    private var flag1 = false
    private var flag2 = false
    private var flag3 = false
    private var flag4 = false
    private var flag5 = false
    private var mCommitInfoFlag = false
    private var LOCATION_AREA: String? = null
    private var mFileArray: Array<File>? = null
    private var mFile: File? = null
    private var parts: MutableList<MultipartBody.Part>? = mutableListOf()


    override fun test(string: String) {
        Logger.d(string)
    }

    override fun testfromPic(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun returnDatafromPic(s: InfoCollectPictureBean) {
        Logger.d(s.message)
    }

    override fun returnData2(s: DetailBean) {
        Logger.d(s.data.originalPrice)
    }

    override fun returnData3(s: InfoCollectBean) {
        Logger.d(s.message)
//        Toast.makeText(context, s.message, Toast.LENGTH_LONG).show()
        if (s.message == "添加成功") {
            start(SuccessCommitFragment().newInstance())
        }
    }

    override fun returnData(s: DetailBean) {
        Logger.d(s.data.originalPrice)
    }

    override fun injectComponent() {

        DaggerInfoCollectFragmentComponent.builder().fragmentComponent(fragmentComponent).infoCollectModule(InfoCollectModule()).build().inject(this)
        //   DaggerInfoCollectEntryFragmentComponent.builder().fragmentComponent(fragmentComponent).infoCollectEntryModule(InfoCollectEntryModule()).build().inject(InfoCollectFragment())
        mPresenter.mView = this
    }


    override fun onClick(v: View?) {
        when (v) {
            mMarkDirtRL -> {
                KeyboardUtils.hideSoftInput(activity)
                val fragment: MarkLand2Fragment = MarkLand2Fragment().newInstance()
                Logger.d(LOCATION_POINTS)
                Logger.d(LOCATION_CENTER)
                if (!LOCATION_POINTS!!.isEmpty() && !LOCATION_CENTER!!.isEmpty()) {
                    val bundle = Bundle()
                    bundle.putString("LOCATION_POINTS", LOCATION_POINTS)
                    bundle.putString("LOCATION_CENTER", LOCATION_CENTER)
                    bundle.putString("LOCATION_AREA", LOCATION_AREA)
                    fragment.arguments = bundle
                }
                startForResult(fragment, 0x002)
            }
            mIdInfoRL -> {
                KeyboardUtils.hideSoftInput(activity)
                val fragment: IdInfoFragment = IdInfoFragment().newInstance()
                if (mIdIndex != -1 && !mIdNumber!!.isEmpty()) {
                    val bundle = Bundle()
                    bundle.putString("ID_INDEX", mIdIndex.toString())
                    bundle.putString("ID_NUMBER", mIdNumber)
                    fragment.arguments = bundle
                }
                startForResult(fragment, 0x001)
            }
            mDirtLocationRl -> {
                KeyboardUtils.hideSoftInput(activity)
                startForResult(LocationInfoFragment().newInstance(), 0x003)
            }
            mUploadImagesIV -> {
                selectImage()
            }
            mImage1 -> {
                startFullScreenActivity(0, mImage1)
            }

            mImage2 -> {
                if (!IMAGE2_FLAG) {
                    selectImage()
                } else {
//                    startActivity<ShowImageActivity>()
//                    startPictureActivity(mImage2)
                    startFullScreenActivity(1, mImage2)
                }
            }

            mImage3 -> {
                if (!IMAGE3_FLAG) {
                    selectImage()
                } else {
                    startFullScreenActivity(2, mImage3)
                }
            }

            mImage4 -> {
                if (!IMAGE4_FLAG) {
                    selectImage()
                } else {
                    startFullScreenActivity(3, mImage4)
                }
            }

            mCommitInfoRL -> {
                val sp = activity!!.getSharedPreferences("ADDRE", Context.MODE_PRIVATE)
                loginNameId = "id01"
                plotNumber = "220122"
                insurer = mPropertyOwnerNameET.text.toString()
                phoneNumber = mTelET.text.toString()

                if (RegexUtils.isMobileExact(phoneNumber)) {
                    mPresenter.insertCollectingInformation(loginNameId, insurer, identityType, idNumber, phoneNumber, plantingPlace, insuranceAmount, shape,sp.getString("shi",""),sp.getString("xian",""),sp.getString("xiang",""),sp.getString("cun",""))
                    sp.edit().clear().commit()

//                    start(SuccessCommitFragment().newInstance())
                } else {
                    Toasty.error(activity!!, "请输入正确的手机号码", Toast.LENGTH_SHORT, true).show()
                }


            }
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        KeyboardUtils.hideSoftInput(activity)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    }

    @SuppressLint("SetTextI18n")
    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x001 && resultCode == ISupportFragment.RESULT_OK) {
            mIdIndex = data.getString("ID_INFO").split(" ")[0].toInt()
            mIdNumber = data.getString("ID_INFO").split(" ")[1]
            Logger.d(data.getString("ID_INFO"))
            if (mIdIndex != -1) {
                identityType = mIdTypeList[mIdIndex]
                idNumber = mIdNumber.toString()
                mIdTV.text = mIdTypeList[mIdIndex] + " " + mIdNumber
                flag3 = true
                checkCommitFlag()
            }
        } else if (requestCode == 0x002 && resultCode == ISupportFragment.RESULT_OK) {
            LOCATION_POINTS = data.getString("LOCATION_POINTS")
            LOCATION_CENTER = data.getString("LOCATION_CENTER")
            Logger.d("LOCATION_POINTSLOCATION_POINTSLOCATION_POINTS" + data.getString("LOCATION_POINTS"))
            shape = "[" + data.getString("LOCATION_POINTS") + "]"
            insuranceAmount = data.getString("LOCATION_AREA")
            mMarkTV.text = "已标记"
            flag5 = true
            checkCommitFlag()
        } else if (requestCode == 0x003 && resultCode == ISupportFragment.RESULT_OK) {
            insurerAddress = data.getString("INSURER_ADDRESS")
            plantingPlace = data.getString("PLANTING_PLACE")
            mDirtLocationTV.text = insurerAddress + plantingPlace
            flag4 = true
            checkCommitFlag()
        }
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


    private fun selectImage() {
        val intent = Intent(activity, ImageSelectorActivity::class.java)
        intent.putExtra(ImageSelector.MAX_SELECT_COUNT, MAX_IMAGE)
        intent.putExtra(ImageSelector.IS_SINGLE, false)
        intent.putExtra(ImageSelector.USE_CAMERA, true)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun initViews() {
        mInfoCollectToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mInfoCollectToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mInfoCollectToolbar.setNavigationOnClickListener {
            pop()
        }
        mSharedPreferences = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)
        mPropertyOwnerNameET.addTextChangedListener(mPropertyOwnerNameETTextWatcher)
        mTelET.addTextChangedListener(mTelETTextWatcher)

//        mPresenter.getData2()


//        mFileArray!![0] =
//        mPresenter.insertCollectingPicture("33052018082100001", mFileArray!!)


    }

    private var mPropertyOwnerNameETTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            flag1 = !mPropertyOwnerNameET.text.toString().isEmpty()
            checkCommitFlag()
        }
    }

    private var mTelETTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            flag2 = !mTelET.text.toString().isEmpty()
            checkCommitFlag()

        }
    }

    private fun checkCommitFlag() {
        if (flag1 && flag2 && flag3
//                && flag4
                && flag5) {
            mCommitInfoRL.setBackgroundColor(Color.parseColor("#6299FF"))
            mCommitInfoFlag = true
        } else {
            mCommitInfoRL.setBackgroundColor(Color.parseColor("#C5C5C5"))
            mCommitInfoFlag = false
        }
    }

    fun newInstance(): InfoCollectFragment {
        val args = Bundle()
        val fragment = InfoCollectFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_collect, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mMarkDirtRL.setOnClickListener(this)
        mIdInfoRL.setOnClickListener(this)
        mDirtLocationRl.setOnClickListener(this)
        mUploadImagesIV.setOnClickListener(this)
        mImage1.setOnClickListener(this)
        mImage2.setOnClickListener(this)
        mImage3.setOnClickListener(this)
        mImage4.setOnClickListener(this)
        mCommitInfoRL.setOnClickListener(this)
        mImages = arrayOf("0", "0", "0", "0")
        mFileArray = arrayOf(File(""), File(""), File(""), File(""))
//        mFileArray = arrayOf(File(""))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            mUploadImagesIV.visibility = View.GONE
            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
            val editor = mSharedPreferences!!.edit()
            editor.putString("upload_images", images.toString())
            editor.commit()


            val sp = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)
            var currImages = sp.getString("upload_images", "").substring(1, sp.getString("upload_images", "").length - 1)
            mImagesArray!!.addAll(currImages.split(",") as MutableList<String>)
            Logger.d("mImagesArray" + mImagesArray)
            for (i in 0 until mImagesArray!!.size) {
                mImagesArray!![i] = mImagesArray!![i].trim()
                mImages!![i] = mImagesArray!![i]
                mFileArray!![i] = File(mImagesArray!![i])
                mFile = File(mImagesArray!![i])
            }
            Logger.d("mImages" + mImages)
            when (mImagesArray!!.size) {
                1 -> setImage1(mImagesArray!![0])
                2 -> setImage12(mImagesArray!![0], mImagesArray!![1])
                3 -> setImage123(mImagesArray!![0], mImagesArray!![1], mImagesArray!![2])
                4 -> setImage1234(mImagesArray!![0], mImagesArray!![1], mImagesArray!![2], mImagesArray!![3])
            }
            Logger.d(mFileArray)
//            mPresenter.insertCollectingPicture("33052018082100001", mFileArray!!)
//            mPresenter.insertCollectingPicture("33052018082100001", mFile!!)

//            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile)
//            var body = MultipartBody.Part.createFormData("collectingPictureNames", mFile!!.getName(), requestFile);
//            mPresenter.insertCollectingPicture("33052018082100001", body!!)
//
            var size = 0
            for (i in 0 until mImages!!.size) {
                if (mImages!![i] != "0")
                    size += 1
            }
            Logger.d(size)
            if (size == 1) {
                var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
                var body = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![0].name, requestFile)
                parts!!.add(body)
            }
            if (size == 2) {
                var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
                var body = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![0].name, requestFile)
                var requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![1])
                var body2 = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![1].name, requestFile2)
                parts!!.add(body)
                parts!!.add(body2)
            }

            if (size == 3) {
                var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
                var body = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![0].name, requestFile)

                var requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![1])
                var body2 = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![1].name, requestFile2)

                var requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![2])
                var body3 = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![2].name, requestFile3)

                parts!!.add(body)
                parts!!.add(body2)
                parts!!.add(body3)
            }
            if (size == 4) {
                var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
                var body = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![0].name, requestFile)

                var requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![1])
                var body2 = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![1].name, requestFile2)

                var requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![2])
                var body3 = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![2].name, requestFile3)

                var requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![3])
                var body4 = MultipartBody.Part.createFormData("collectingPictureNames", mFileArray!![3].name, requestFile4)

                parts!!.add(body)
                parts!!.add(body2)
                parts!!.add(body3)
                parts!!.add(body4)
            }

            mPresenter.insertCollectingPicture("33052018082100001", parts!!.toList())


        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {

        return DefaultHorizontalAnimator()
    }


    private fun setImage1(url: String) {
        mImage1.visibility = View.VISIBLE
        mImage2.visibility = View.VISIBLE
        MAX_IMAGE -= 1
        mImage1.setBackgroundColor(Color.parseColor("#00000000"))
        Glide.with(this).load(File(url)).apply(OPTIONS).into(mImage1)


    }

    private fun setImage12(url: String, url2: String) {
        mImage1.visibility = View.VISIBLE
        mImage2.visibility = View.VISIBLE
        mImage3.visibility = View.VISIBLE
        MAX_IMAGE -= 2
        IMAGE2_FLAG = true

        mImage1.setBackgroundColor(Color.parseColor("#00000000"))
//        mImage2.setBackgroundColor(Color.parseColor("#00000000"))
        Glide.with(this).load(File(url)).apply(OPTIONS).into(mImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mImage2)

    }

    private fun setImage123(url: String, url2: String, url3: String) {
        mImage1.visibility = View.VISIBLE
        mImage2.visibility = View.VISIBLE
        mImage3.visibility = View.VISIBLE
        mImage4.visibility = View.VISIBLE
        MAX_IMAGE -= 3
        IMAGE2_FLAG = true
        IMAGE3_FLAG = true

        mImage1.setBackgroundColor(Color.parseColor("#00000000"))
        mImage2.setBackgroundColor(Color.parseColor("#00000000"))
        mImage3.setBackgroundColor(Color.parseColor("#00000000"))

        Glide.with(this).load(File(url)).apply(OPTIONS).into(mImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mImage2)
        Glide.with(this).load(File(url3.trim())).apply(OPTIONS).into(mImage3)
    }

    private fun setImage1234(url: String, url2: String, url3: String, url4: String) {
        mImage1.visibility = View.VISIBLE
        mImage2.visibility = View.VISIBLE
        mImage3.visibility = View.VISIBLE
        mImage4.visibility = View.VISIBLE
        IMAGE2_FLAG = true
        IMAGE3_FLAG = true
        IMAGE4_FLAG = true


        mImage1.setBackgroundColor(Color.parseColor("#00000000"))
        mImage2.setBackgroundColor(Color.parseColor("#00000000"))
        mImage3.setBackgroundColor(Color.parseColor("#00000000"))
        mImage4.setBackgroundColor(Color.parseColor("#00000000"))

        Glide.with(this).load(File(url)).apply(OPTIONS).into(mImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mImage2)
        Glide.with(this).load(File(url3.trim())).apply(OPTIONS).into(mImage3)
        Glide.with(this).load(File(url4.trim())).apply(OPTIONS).into(mImage4)

    }
}
