package com.android.lixiang.nongbao.ui.fragment.area3

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.fragment.area2.AcceptDisasterDegreeFragment
import com.android.lixiang.nongbao.ui.fragment.area2.AcceptDisasterKindFragment
import com.android.lixiang.nongbao.ui.fragment.area2.AcceptDisasterSpeciesFragment
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_choose_speciesname.*
import kotlinx.android.synthetic.main.fragment_fill_survey.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.SimpleDateFormat
import java.util.*


class FillSurveyInfoFragment : SupportFragment(), View.OnClickListener {
    var ca = Calendar.getInstance()
    var mYear = ca.get(Calendar.YEAR)
    var mMonth = ca.get(Calendar.MONTH)
    var mDay = ca.get(Calendar.DAY_OF_MONTH)
    var time = String()
    override fun onClick(v: View?) {
        when (v) {
            mAcceptDisasterKindRL -> {
                startForResult(AcceptDisasterKindFragment().newInstance(), 1)
            }
            mAcceptDisasterSpeciesRL -> {
                startForResult(AcceptDisasterSpeciesFragment().newInstance(), 2)
            }
            mAcceptDisasterTimeRL -> {
//                startForResult(AcceptDisasterTimeFragment().newInstance(),3)
                KeyboardUtils.hideSoftInput(activity)

                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            }
            mAcceptInsuranceAddressRl -> {
                startForResult(AcceptDisasterDegreeFragment().newInstance(),3)
            }

//            mMarkDirtRL -> {
//                startActivity<MapActivity>()
//            }
//            mIdInfoRL -> {
//                start(IdInfoFragment().newInstance())
//            }
//            mDirtLocationRl -> {
//                start(LocationInfoFragment().newInstance())
//            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("index")
            when (index) {
                "0" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "暴雨"
                }
                "1" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "洪水"
                }
                "2" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "内涝"
                }
                "3" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "风灾"
                }
                "4" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "雹灾"
                }
                "5" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "旱灾"
                }
                "6" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "冻灾"
                }
                "7" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "泥石流"
                }
                "8" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "地震山体滑坡"
                }
                "9" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "病虫草鼠害"
                }
                "10" -> {
                    disaster_kind.visibility = View.VISIBLE
                    disaster_kind.text = "其他灾害"
                }

                "-1" -> {
                    Log.e("返回错误", "return error")
                }
            }
        } else if (requestCode == 2 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("index")
            when (index) {

                "1" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "玉米"
                }
                "2" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "大豆"
                }
                "3" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "花生"
                }
                "4" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "黄瓜"
                }
                "5" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "茄子"
                }
                "6" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "葵花籽"
                }
                "7" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "字段"
                }


                "-1" -> {
                    Log.e("返回错误", "return error")
                }
            }
            val index_index = data.getString("index_index")
            when (index_index) {
                "0" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "水稻, 早稻"
                }
                "1" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "水稻, 中稻"
                }
                "2" -> {
                    disaster_species.visibility = View.VISIBLE
                    disaster_species.text = "水稻, 晚稻"
                }
                "-1" -> {
                    Log.e("返回错误", "return error")
                }
            }
        }else if (requestCode == 3 && resultCode == ISupportFragment.RESULT_OK){
            val index = data.getString("index")
            when (index) {
                "0" -> {
                    mDisaster_degreeTV.visibility = View.VISIBLE
                    mDisaster_degreeTV.text = "轻度"
                }
                "1" -> {
                    mDisaster_degreeTV.visibility = View.VISIBLE
                    mDisaster_degreeTV.text = "中度"
                }
                "2" -> {
                    mDisaster_degreeTV.visibility = View.VISIBLE
                    mDisaster_degreeTV.text = "重度"
                }
                "3" -> {
                    mDisaster_degreeTV.visibility = View.VISIBLE
                    mDisaster_degreeTV.text = "接近绝收"
                }
                "-1" -> {
                    Log.e("返回错误", "return error")
                }
            }
        }

    }

    private fun initViews() {
        mFillSurveyToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mFillSurveyToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mFillSurveyToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    fun newInstance(): FillSurveyInfoFragment {
        val args = Bundle()
        val fragment = FillSurveyInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill_survey, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mAcceptDisasterKindRL.setOnClickListener(this)
        mAcceptDisasterSpeciesRL.setOnClickListener(this)
        mAcceptDisasterTimeRL.setOnClickListener(this)
        mAcceptInsuranceAddressRl.setOnClickListener(this)
//        mMarkDirtRL.setOnClickListener(this)
//        mIdInfoRL.setOnClickListener(this)
//        mDirtLocationRl.setOnClickListener(this)
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }
        mDisaster_timeTV.visibility = View.VISIBLE
        mDisaster_timeTV.text = days

    }

    private fun compare_date(DATE1: String, DATE2: String): Int {
        val df = SimpleDateFormat("yyyy-MM-dd")
        try {
            val dt1 = df.parse(DATE1)
            val dt2 = df.parse(DATE2)


            return when {
                dt1.time > dt2.time -> {
                    1
                }
                dt1.time < dt2.time -> {
                    -1
                }
                else -> 0
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return 0
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }
}
