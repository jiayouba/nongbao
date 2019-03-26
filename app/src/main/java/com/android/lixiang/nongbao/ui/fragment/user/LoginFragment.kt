package com.android.lixiang.nongbao.ui.fragment.user

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.Toast
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.SoftKeyboardListener
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.presenter.LoginInfoPresenter
import com.android.lixiang.nongbao.presenter.data.bean.LoginInfoBean
import com.android.lixiang.nongbao.presenter.injection.component.DaggerInfoCollectFragmentComponent
import com.android.lixiang.nongbao.presenter.injection.component.DaggerLoginInfoFragmentComponent
import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectModule
import com.android.lixiang.nongbao.presenter.injection.module.LoginInfoModule
import com.android.lixiang.nongbao.presenter.view.LoginInfoView
import com.android.lixiang.nongbao.ui.OnMultiClickListener
import com.android.lixiang.nongbao.ui.fragment.ChooseAreaFragment
import com.android.lixiang.nongbao.ui.fragment.ChooseBankFragment
import com.android.lixiang.nongbao.ui.fragment.HomeFragment
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_login.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.imageBitmap


class LoginFragment : BaseMvpFragment<LoginInfoPresenter>(), View.OnClickListener,LoginInfoView {
    var loginName :String=""
    var passWord:String=""
    var isHide: Boolean = true
    var flag1 = false
    var flag2 = false
    var tel = String()
    var password = String()
    var toast: Toast? = null
    var new_drawable2bitmap:Bitmap?=null
    var temp_drawable2bitmap:Bitmap?=null
    override fun testfromlogin(string: String) {
       com.orhanobut.logger.Logger.d(string)
    }

    override fun returnDatafromLogin(s: LoginInfoBean) {
        Logger.d(s.message)
    }

    override fun injectComponent() {

       DaggerLoginInfoFragmentComponent.builder().fragmentComponent(fragmentComponent).loginInfoModule(LoginInfoModule()).build().inject(this)
        //   DaggerInfoCollectEntryFragmentComponent.builder().fragmentComponent(fragmentComponent).infoCollectEntryModule(InfoCollectEntryModule()).build().inject(InfoCollectFragment())
        mPresenter.mView = this
    }

    override fun onClick(v: View?) {
        when (v) {

            mForgotPasswordTV -> start(ModifyPasswordFragment().newInstance())
        }
    }

    fun newInstance(): LoginFragment {
        val args = Bundle()
        val fragment = LoginFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (mAppTitleRL != null) {
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                    Handler().postDelayed({
                        mAppTitleRL.visibility = View.GONE
                    }, 200)
                }
            }

            override fun keyBoardHide(height: Int) {
                if (mAppTitleRL != null) {
                    mAppTitleRL.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                }

            }
        })


        mForgotPasswordTV.setOnClickListener(this)
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mAppTitle.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/pingfang.ttf")
        mLoginToolbar.title = "账号登录"
        (activity as AppCompatActivity).setSupportActionBar(mLoginToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //new_drawable2bitmap=drawable2Bitmap(context!!.getDrawable(R.drawable.img_eye_open))

        mLoginBtn.isClickable = false

        mAccountET.requestFocus()//账号登录获取焦点

        mAccountET.addTextChangedListener(mTelTextWatcher)
        mPwdET.addTextChangedListener(mPwdTextWatcher)

        val passwordLength = mPwdET.text.length
        mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        mPwdET.setSelection(passwordLength)


        mShowHideRL_hidepwd.setOnClickListener {
            mShowHideRL_hidepwd.visibility=View.INVISIBLE
            mShowHideRL_showpwd.visibility=View.VISIBLE
            mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            mPwdET.setSelection(mPwdET.text.length)
//            if (isHide) {
//
//              //  mShowHideIV.setImageBitmap(new_drawable2bitmap)
//                mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//                mPwdET.setSelection(mPwdET.text.length)
//                isHide = false
//            } else {
//                mShowHideIV.setImageResource(R.drawable.img_eye_close)
//                mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//                mPwdET.setSelection(mPwdET.text.length)
//                isHide = true
//            }
        }
        mShowHideRL_showpwd.setOnClickListener {
            mShowHideRL_showpwd.visibility=View.INVISIBLE
            mShowHideRL_hidepwd.visibility=View.VISIBLE
            mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            mPwdET.setSelection(mPwdET.text.length)

        }

        mLoginBtn.setOnClickListener(object : OnMultiClickListener() {
            override fun onMultiClick(v: View?) {
                tel = mAccountET.text.toString()
                password = mPwdET.text.toString()
                if (RegexUtils.isMobileExact(tel)) {
                    //TODO:电话验证成功测试
                    if (toast != null) {
                        toast!!.setText(getString(R.string.test_login_success))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                        pop()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.test_login_success), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
                    //TODO:登录接口实现
                    mPresenter.login(tel,password)

                } else {
                    if (toast != null) {
                        toast!!.setText(getString(R.string.wrong_tel))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
//                    val snackBar = Snackbar.make(view!!, getString(R.string.wrong_tel), Snackbar.LENGTH_SHORT)
//                    snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                    snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                    snackBar.show()
                }
            }
        })


    }
//    private fun checkAccount() {
//        findTelephoneParams!!["telephone"] = tel
//        findTelephoneParams!!["client"] = "android"
//
//        RestClient().builder()
//                .url("http://59.110.161.48:8023/findTelephone.do")
//                .params(findTelephoneParams!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        commonBean = Gson().fromJson(response, CommonBean::class.java)
//                        if (commonBean.meta == "success") {
//                            /**
//                             * success
//                             */
//                            login()
//                        } else {
////                            if (toast != null) {
////                                toast!!.setText(getString(R.string.not_register))
////                                toast!!.duration = Toast.LENGTH_SHORT
////                                toast!!.show()
////                            } else {
////                                toast = Toast.makeText(activity, getString(R.string.not_register), Toast.LENGTH_SHORT)
////                                toast!!.show()
////                            }
//                            val snackBar = Snackbar.make(view!!, getString(R.string.not_register), Snackbar.LENGTH_SHORT)
//                            snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                            snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                            snackBar.show()
//                        }
//                    }
//                })
//                .error(object : IError {
//                    override fun onError(code: Int, msg: String) {}
//                })
//                .failure(object : IFailure {
//                    override fun onFailure() {}
//                })
//                .build()
//                .post()
//    }
//
//    private fun login() {
//        userNameLoginParams!!["userTelephone"] = tel
//        passwordMD5 = EncryptUtils.encryptMD5ToString(password).toLowerCase()
//        EmallLogger.d(passwordMD5)
//        userNameLoginParams!!["password"] = passwordMD5
////        userNameLoginParams!!["client"] = "android"
//
//
//        RestClient().builder()
//                .url("http://59.110.161.48:8023/global/mall/UserNameLogin.do?client=android")
//                .params(userNameLoginParams!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        userNameLoginBean = Gson().fromJson(response, UserNameLoginBean::class.java)
//                        if (userNameLoginBean.meta == "success") {
//                            /**
//                             * success
//                             */
//                            EmallLogger.d(response)
//                            /**
//                             * test------------------------------------
//                             */
////                            val info = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0]
////                            if (info != null) {
////                                info.userPassword = passwordMD5
////                                DatabaseManager().getInstance()!!.getDao()!!.update(info)
////                            }
//                            SignHandler().onSignIn(response.replaceFirst("null", "\"" + tel + "\"").replaceFirst("null", "\"" + passwordMD5 + "\""), mISignListener!!)
////                            SignHandler().onSignIn(response.replaceFirst("null", "\"" + tel + "\""), mISignListener!!)
//
//                            val bundle = Bundle()
//                            bundle.putString("USER_NAME", userNameLoginBean.user.username)
//                            KeyboardUtils.hideSoftInput(activity)
////                            popTo(preFragment.javaClass, false)
//                            when {
//                                arguments.getString("PAGE_FROM") == "CLASSIFY" -> popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
//                                arguments.getString("PAGE_FROM") == "ORDER_LIST" -> popTo(findFragment(HomeDelegate().javaClass).javaClass, false)
//                                arguments.getString("PAGE_FROM") == "ME" -> popTo(findFragment(HomeDelegate().javaClass).javaClass, false)
//                                arguments.getString("PAGE_FROM") == "GOODS_DETAIL" -> popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
//                                arguments.getString("PAGE_FROM") == "PAYMENT" -> {
//                                    if (findFragment(GoodsDetailDelegate().javaClass) == null) {
//                                        popTo(findFragment(HomeDelegate().javaClass).javaClass, false)
//                                    } else
//                                        popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
//                                }
//                                arguments.getString("PAGE_FROM") == "PROGRAM_INDEX" ->
//                                    popTo(findFragment(HomeDelegate().javaClass).javaClass, false)
//                                arguments.getString("PAGE_FROM") == "PROGRAM" ->
//                                    popTo(findFragment(HomeDelegate().javaClass).javaClass, false)
//                                else -> pop()
//                            }
//                            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                            setFragmentResult(RESULT_OK, bundle)
//                        } else {
//
////                            if (toast != null) {
////                                toast!!.setText(getString(R.string.account_pwd_error))
////                                toast!!.duration = Toast.LENGTH_SHORT
////                                toast!!.show()
////                            } else {
////                                toast = Toast.makeText(activity, getString(R.string.account_pwd_error), Toast.LENGTH_SHORT)
////                                toast!!.show()
////                            }
//                            val snackBar = Snackbar.make(view!!, getString(R.string.account_pwd_error), Snackbar.LENGTH_SHORT)
//                            snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                            snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                            snackBar.show()
//                        }
//                    }
//                })
//                .error(object : IError {
//                    override fun onError(code: Int, msg: String) {}
//                })
//                .failure(object : IFailure {
//                    override fun onFailure() {}
//                })
//                .build()
//                .post()
//    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    private var mTelTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub

            flag1 = true
            if (mAccountET.text.toString() == "") {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                mLoginBtn.isClickable = false
            }
            if (flag1 && flag2) {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_success)
                mLoginBtn.isClickable = true
            }
        }
    }

    private var mPwdTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub

            flag2 = true
            if (mPwdET.text.toString() == "") {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                mLoginBtn.isClickable = false
            }
            if (flag1 && flag2) {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_success)
                mLoginBtn.isClickable = true
            }
        }
    }

    private fun drawable2Bitmap(drawable: Drawable) :Bitmap {
        val width=drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config = Bitmap.Config.ARGB_8888
        Log.e("config","config="+config)// 取 drawable 的颜色格式
        Log.e("PixelFormat","PixelFormat.OPAQUE"+PixelFormat.OPAQUE)// 取 drawable 的颜色格式
        val bitmap = Bitmap.createBitmap((width).toInt(), (height).toInt(), config)     // 建立对应 bitmap
        val canvas = Canvas(bitmap)         // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, (width*1.2).toInt(), (height*1.2).toInt())
        drawable.draw(canvas)      // 把 drawable 内容画到画布中
        return bitmap
    }
    private fun drawabletoBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return (drawable as BitmapDrawable).getBitmap()
        } else if (drawable is NinePatchDrawable) {
            val bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            if (drawable.getOpacity() !== PixelFormat.OPAQUE)
                                Bitmap.Config.ARGB_8888
                            else
                                Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight())
            drawable.draw(canvas)
            return bitmap
        } else {
            return null
        }
    }

    private fun zoomDrawable(drawable: Drawable, w: Int, h: Int): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val oldbmp = drawable2Bitmap(drawable) // drawable 转换成 bitmap
        val matrix = Matrix()   // 创建操作图片用的 Matrix 对象
        val scaleWidth = w.toFloat() / width   // 计算缩放比例
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidth, scaleHeight)         // 设置缩放比例
        val newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true)       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return newbmp       // 把 bitmap 转换成 drawable 并返回
    }
}