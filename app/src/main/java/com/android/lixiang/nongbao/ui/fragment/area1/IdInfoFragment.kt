package com.android.lixiang.nongbao.ui.fragment.area1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.nongbao.R
import kotlinx.android.synthetic.main.fragment_id_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import io.card.payment.CardIOActivity
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import com.orhanobut.logger.Logger
import io.card.payment.CreditCard
import me.yokeyword.fragmentation.ISupportFragment


class IdInfoFragment : SupportFragment(), View.OnClickListener {
    private var mChooseFlag = false
    private var mClickFlag = false
    private var mIdTypeList: MutableList<String> = mutableListOf("身份证", "组织机构代码", "统一社会信用代码", "其他")
    private var index = -1
    private var mIdNumber: String? = ""
    override fun onClick(v: View?) {
        when (v) {
            mIdConfirmRL -> {
                if (mClickFlag) {
                    val bundle = Bundle()
                    bundle.putString("ID_INFO", "$index $mIdNumber")
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    pop()
                }
            }

            mScanIV -> {
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
//                val scanIntent = Intent(activity, CardIOActivity::class.java)
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false) // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false) // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false) // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.RED)
//                scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
//                scanIntent.putExtra(CardIOActivity.ACTIVITY_SERVICE, false)
//                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true)
//                scanIntent.putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "zh-Hans")
//                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true)
//                scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
//                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
//                scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false)
//                scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true)
//                scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS, "1233")
//                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
//                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
//                startActivityForResult(scanIntent, 3)
            }

            mArea1 -> {
                index = 0
                mArea1.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea3.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea4.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }

            mArea2 -> {
                index = 1
                mArea2.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea3.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea4.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }

            mArea3 -> {
                index = 2
                mArea3.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea4.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }

            mArea4 -> {
                index = 3
                mArea4.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea3.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }
        }
    }

    private fun checkEditText() {
        if (!mIdET.text.toString().isEmpty()) {
            mIdConfirmRL.setBackgroundColor(Color.parseColor("#6299FF"))
            mClickFlag = true
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 3) {
            var resultDisplayStr: String
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                val scanResult = data.getParcelableExtra<CreditCard>(CardIOActivity.EXTRA_SCAN_RESULT)

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.redactedCardNumber + "\n"

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n"
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length + " digits.\n"
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n"
                }
            } else {
                resultDisplayStr = "Scan was canceled."
            }
            // do something with resultDisplayStr, maybe display it in a textView
            Logger.d(resultDisplayStr)
        }
        // else handle other activity results
    }

    /**
     *  scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false) // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false) // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false) // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.RED)
    scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
    scanIntent.putExtra(CardIOActivity.ACTIVITY_SERVICE,false)
    scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "zh-Hans")
    scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
    scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false)
    scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS, "1233")
    scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
    scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)

     */
    private fun initViews() {
        mIdInfoToolbar.title = "证件信息"
        (activity as AppCompatActivity).setSupportActionBar(mIdInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mIdInfoToolbar.setNavigationOnClickListener {
            pop()
        }
        mIdET.addTextChangedListener(mIdETTextWatcher)
        mIdTypeList.add("")
        Logger.d( arguments!!.getString("ID_INDEX") + arguments!!.getString("ID_NUMBER"))
        if (arguments!!.getString("ID_INDEX") != null && arguments!!.getString("ID_NUMBER") != null) {
            index = arguments!!.getString("ID_INDEX").toInt()
            mIdNumber = arguments!!.getString("ID_NUMBER")
            when (index) {
                0 -> mArea1.setBackgroundResource(R.drawable.round_relativelayout_blue)
                1 -> mArea2.setBackgroundResource(R.drawable.round_relativelayout_blue)
                2 -> mArea3.setBackgroundResource(R.drawable.round_relativelayout_blue)
                3 -> mArea4.setBackgroundResource(R.drawable.round_relativelayout_blue)
            }
            mIdET.setText(mIdNumber)
            mChooseFlag = true
            checkEditText()
        }
    }

    private var mIdETTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {}

        override fun afterTextChanged(s: Editable) {
            if (mChooseFlag && !mIdET.text.toString().isEmpty()) {
                mIdConfirmRL.setBackgroundColor(Color.parseColor("#6299FF"))
                mIdNumber = mIdET.text.toString()
                mClickFlag = true
            } else {
                mIdConfirmRL.setBackgroundColor(Color.parseColor("#C5C5C5"))
                mClickFlag = false
            }
        }
    }

    fun newInstance(): IdInfoFragment {
        val args = Bundle()
        val fragment = IdInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_id_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mIdConfirmRL.setOnClickListener(this)
        mScanIV.setOnClickListener(this)
        mArea1.setOnClickListener(this)
        mArea2.setOnClickListener(this)
        mArea3.setOnClickListener(this)
        mArea4.setOnClickListener(this)

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator() as FragmentAnimator
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("ID_INFO", "-1 -1")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }
}
