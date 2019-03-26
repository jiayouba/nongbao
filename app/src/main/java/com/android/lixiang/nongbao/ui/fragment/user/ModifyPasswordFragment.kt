package com.android.lixiang.nongbao.ui.fragment.user

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.Toast
import com.android.lixiang.base.utils.view.SoftKeyboardListener
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.OnMultiClickListener
import com.blankj.utilcode.util.RegexUtils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_modify_password.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class ModifyPasswordFragment : SupportFragment() {
    var tel = String()
    var vCode = String()
    var flag1 = false
    var flag2 = false
    var wrongToast: Toast? = null
    var emptyToast: Toast? = null
    var successfulToast: Toast? = null

    fun newInstance(): ModifyPasswordFragment {
        val args = Bundle()
        val fragment = ModifyPasswordFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (mModifyPasswordAppTitleRL != null) {
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mModifyPasswordAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                    Handler().postDelayed({
                        mModifyPasswordAppTitleRL.visibility = View.GONE
                    }, 200)
                }
            }

            override fun keyBoardHide(height: Int) {
                if (mModifyPasswordAppTitleRL != null) {
                    mModifyPasswordAppTitleRL.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mModifyPasswordAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                }

            }
        })


    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mModifyPasswordAppTitle.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/pingfang.ttf")
        mModifyPasswordToolbar.title = "修改登录密码"
        (activity as AppCompatActivity).setSupportActionBar(mModifyPasswordToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        sign_in_by_tel_vcode_et.addTextChangedListener(mVcodeTextWatcher)
        sign_in_by_tel_tel_et.addTextChangedListener(mTelTextWatcher)
        sign_in_by_tel_count_down.setCountDownMillis(60000)

        mModifyPasswordCommitBtn.setOnClickListener(object : OnMultiClickListener() {
            override fun onMultiClick(v: View?) {
                tel = sign_in_by_tel_tel_et.text.toString()
                vCode = sign_in_by_tel_vcode_et.text.toString()
                if (RegexUtils.isMobileExact(tel)) {
                    //TODO:检验验证码和电话号是否正确的逻辑在这里写
                    //checkMessage(tel,vCode)
                    if (vCode.isNotEmpty()) {

                        if (successfulToast != null) {
                            successfulToast!!.setText(getString(R.string.test_phonenumber_success))
                            successfulToast!!.duration = Toast.LENGTH_SHORT
                            successfulToast!!.show()
                        } else {
                            successfulToast = Toast.makeText(activity, getString(R.string.test_phonenumber_success), Toast.LENGTH_SHORT)
                            successfulToast!!.show()
                        }
                        start(SetPasswordFragment().newInstance())
                    }
                } else {
                    if (wrongToast != null) {
                        wrongToast!!.setText(getString(R.string.wrong_tel))
                        wrongToast!!.duration = Toast.LENGTH_SHORT
                        wrongToast!!.show()
                    } else {
                        wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT)
                        wrongToast!!.show()
                    }
//                    val snackBar = Snackbar.make(view!!, getString(R.string.wrong_tel), Snackbar.LENGTH_SHORT)
//                    snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                    snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                    snackBar.show()
                }
            }
        })
        mModifyPasswordCommitBtn.isClickable = false

        sign_in_by_tel_count_down.setOnClickListener {
            tel = sign_in_by_tel_tel_et.text.toString()
            if (tel.isEmpty()) {
                /**
                 * empty tel
                 */
                if (emptyToast != null) {
                    emptyToast!!.setText(getString(R.string.empty_tel))
                    emptyToast!!.duration = Toast.LENGTH_SHORT
                    emptyToast!!.show()
                } else {
                    emptyToast = Toast.makeText(activity, getString(R.string.empty_tel), Toast.LENGTH_SHORT)
                    emptyToast!!.show()
                }
//                val snackBar = Snackbar.make(view!!, getString(R.string.empty_tel), Snackbar.LENGTH_SHORT)
//                snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                snackBar.show()
            } else {
                /**
                 * tel ok
                 */
                if (RegexUtils.isMobileExact(tel)) {
                    /**
                     * tel is valid
                     */
                    sign_in_by_tel_count_down.start()
                    showHint()
                } else {
                    /**
                     * tel is invalid
                     */
                    if (wrongToast != null) {
                        wrongToast!!.setText(getString(R.string.wrong_tel))
                        wrongToast!!.duration = Toast.LENGTH_SHORT
                        wrongToast!!.show()
                    } else {
                        wrongToast = Toast.makeText(activity, getString(R.string.wrong_tel), Toast.LENGTH_SHORT)
                        wrongToast!!.show()
                    }
//                    val snackBar = Snackbar.make(view!!, getString(R.string.wrong_tel), Snackbar.LENGTH_SHORT)
//                    snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                    snackBar.setActionTextColor(Color.parseColor("#B4A078"))
//                    snackBar.show()
                }
            }
        }

    }

    private fun showHint() {
        sign_in_by_tel_vcode_tv.text = String.format("已向手机%s发送验证码", hideTel())
        sign_in_by_tel_vcode_tv.visibility = View.VISIBLE
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
            if (sign_in_by_tel_tel_et.text.toString() == "") {
                mModifyPasswordCommitBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                mModifyPasswordCommitBtn.isClickable = false
            }
            if (flag1 && flag2) {
                mModifyPasswordCommitBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_success)
                mModifyPasswordCommitBtn.isClickable = true
            }
        }
    }

    private var mVcodeTextWatcher: TextWatcher = object : TextWatcher {
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
            if (sign_in_by_tel_vcode_et.text.toString() == "") {
                mModifyPasswordCommitBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                mModifyPasswordCommitBtn.isClickable = false
            }

            if (flag1 && flag2) {
                mModifyPasswordCommitBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_success)
                mModifyPasswordCommitBtn.isClickable = true

            }
        }
    }

    private fun hideTel(): String {
        return String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11))
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}