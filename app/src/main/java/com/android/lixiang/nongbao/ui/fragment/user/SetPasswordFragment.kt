package com.android.lixiang.nongbao.ui.fragment.user

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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
import com.android.lixiang.nongbao.presenter.ResetPasswordPresenter
import com.android.lixiang.nongbao.presenter.data.bean.ResetPasswordBean
import com.android.lixiang.nongbao.presenter.injection.module.ResetPasswordModule
import com.android.lixiang.nongbao.presenter.view.ResetPasswordView
import com.android.lixiang.nongbao.ui.fragment.HomeFragment
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_modify_password.*
import kotlinx.android.synthetic.main.fragment_set_password.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class SetPasswordFragment : BaseMvpFragment<ResetPasswordPresenter>(), ResetPasswordView {
    var newPassword = String()
    var confirmPassword = String()
    var toast: Toast? = null
    var flag1 = false
    var flag2 = false
    fun newInstance(): SetPasswordFragment {
        val args = Bundle()
        val fragment = SetPasswordFragment()
        fragment.arguments = args
        return fragment
    }

    override fun testfromresetpassword(string: String) {
        TODO("测试") //To change body of created functions use File | Settings | File Templates.
    }

    override fun returnDatafromresetpassword(s: ResetPasswordBean) {
        TODO("重设密码返回的bean") //To change body of created functions use File | Settings | File Templates.
    }

    override fun injectComponent() {
        TODO("重设密码依赖注入") //To change body of created functions use File | Settings | File Templates.
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (mSetPasswordAppTitleRL != null) {
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mSetPasswordAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                    Handler().postDelayed({
                        mSetPasswordAppTitleRL.visibility = View.GONE
                    }, 200)
                }
            }

            override fun keyBoardHide(height: Int) {
                if (mSetPasswordAppTitleRL != null) {
                    mSetPasswordAppTitleRL.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mSetPasswordAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                }

            }
        })


    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mSetPasswordAppTitle.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/pingfang.ttf")
        mSetPasswordToolbar.title = "修改登录密码"
        (activity as AppCompatActivity).setSupportActionBar(mSetPasswordToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        reset_pwd_new_pwd_et.addTextChangedListener(mNewTextWatcher)
        reset_pwd_confirm_pwd_et.addTextChangedListener(mConfirmTextWatcher)

        reset_pwd_new_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        reset_pwd_confirm_pwd_et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        reset_pwd_new_pwd_et.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val temp = reset_pwd_new_pwd_et.text.toString()
                if (isLetterDigit(temp)) {
                    if (!checkMinLength(temp)) {
                        if (!checkMaxLength(temp)) {
                            newPassword = reset_pwd_new_pwd_et.text.toString()
                        } else {

                            if (toast != null) {
                                toast!!.setText(getString(R.string.pwd_max))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
//                            val snackBar = Snackbar.make(view!!, getString(R.string.pwd_max), Snackbar.LENGTH_SHORT)
//                            snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                            snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                            snackBar.show()
                        }

                    } else {
                        if (toast != null) {
                            toast!!.setText(getString(R.string.pwd_min))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
//                        val snackBar = Snackbar.make(view!!, getString(R.string.pwd_min), Snackbar.LENGTH_SHORT)
//                        snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                        snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                        snackBar.show()
                    }

                } else {
                    if (toast != null) {
                        toast!!.setText(getString(R.string.error_pwd_format))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
//                    val snackBar = Snackbar.make(view!!, getString(R.string.error_pwd_format), Snackbar.LENGTH_SHORT)
//                    snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                    snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                    snackBar.show()
                }
            }
        }

        mSetPasswordConfirmBtn.setOnClickListener {
            val temp = reset_pwd_new_pwd_et.text.toString()
            if (isLetterDigit(temp)) {
                if (!checkMinLength(temp)) {
                    if (!checkMaxLength(temp)) {
                        newPassword = reset_pwd_new_pwd_et.text.toString()
                        confirmPassword = reset_pwd_confirm_pwd_et.text.toString()
                        if (newPassword == confirmPassword) {
                            if (toast != null) {
                                toast!!.setText(getString(R.string.test_success))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.test_success), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                            //TODO:密码修改正确后的逻辑

                        } else {
                            if (toast != null) {
                                toast!!.setText(getString(R.string.pwd_no_match))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.pwd_no_match), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
//                            val snackBar = Snackbar.make(view!!, getString(R.string.pwd_no_match), Snackbar.LENGTH_SHORT)
//                            snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                            snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                            snackBar.show()
                        }
                    } else {
                        if (toast != null) {
                            toast!!.setText(getString(R.string.pwd_max))
                            toast!!.duration = Toast.LENGTH_SHORT
                            toast!!.show()
                        } else {
                            toast = Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT)
                            toast!!.show()
                        }
//                        val snackBar = Snackbar.make(view!!, getString(R.string.pwd_max), Snackbar.LENGTH_SHORT)
//                        snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                        snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                        snackBar.show()
                    }
                } else {
                    if (toast != null) {
                        toast!!.setText(getString(R.string.pwd_min))
                        toast!!.duration = Toast.LENGTH_SHORT
                        toast!!.show()
                    } else {
                        toast = Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT)
                        toast!!.show()
                    }
//                    val snackBar = Snackbar.make(view!!, getString(R.string.pwd_min), Snackbar.LENGTH_SHORT)
//                    snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                    snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                    snackBar.show()
                }
            } else {
                if (toast != null) {
                    toast!!.setText(getString(R.string.error_pwd_format))
                    toast!!.duration = Toast.LENGTH_SHORT
                    toast!!.show()
                } else {
                    toast = Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT)
                    toast!!.show()
                }
//                val snackBar = Snackbar.make(view!!, getString(R.string.error_pwd_format), Snackbar.LENGTH_SHORT)
//                snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                snackBar.show()
            }
        }
        mSetPasswordConfirmBtn.isClickable = false
    }

    private var mNewTextWatcher: TextWatcher = object : TextWatcher {
        /**
         * 大于8小于20
         */
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
            if (flag1 && flag2) {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_success)
                mSetPasswordConfirmBtn.isClickable = true

            }
            if (reset_pwd_new_pwd_et.text.toString() == "") {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                mSetPasswordConfirmBtn.isClickable = false
            }
        }
    }

    private var mConfirmTextWatcher: TextWatcher = object : TextWatcher {
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

            if (flag1 && flag2) {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_success)
                mSetPasswordConfirmBtn.isClickable = true
            }

            if (reset_pwd_confirm_pwd_et.text.toString() == "") {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                mSetPasswordConfirmBtn.isClickable = false
            }
        }
    }

    fun isLetterDigit(str: String): Boolean {
        var isDigit = false//定义一个boolean值，用来表示是否包含数字
        var isLetter = false//定义一个boolean值，用来表示是否包含字母
        for (i in 0 until str.length) {
            if (Character.isDigit(str[i])) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true
            }
            if (Character.isLetter(str[i])) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true
            }
        }
        val regex = "^[a-zA-Z0-9]+$"
        return isDigit && isLetter && str.matches(regex.toRegex())
    }

    fun checkMinLength(string: String): Boolean {
        return string.length < 8
    }

    fun checkMaxLength(string: String): Boolean {
        return string.length > 20
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}