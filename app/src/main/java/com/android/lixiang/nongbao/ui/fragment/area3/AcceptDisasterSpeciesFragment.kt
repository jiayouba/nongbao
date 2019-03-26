package com.android.lixiang.nongbao.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.Species
import com.android.lixiang.nongbao.ui.adapter.SpeciesAdapter
import kotlinx.android.synthetic.main.fragment_accept_disasterspecies.*
import kotlinx.android.synthetic.main.fragment_choose_speciesname.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class AcceptDisasterSpeciesFragment : SupportFragment() {
    var index = -1
    var index_index = -1
//    var flag = false
//    override fun onClick(v: View?) {
//        when (v) {
//            testbutton -> {
//                startForResult(ChooseRiceKindFragment().newInstance(),1)
//            }
//            speciesitems.setOnItemClickListener { adapterView, view, i, l ->
//                if (i==0){
//                    startForResult(ChooseRiceKindFragment().newInstance(),1)
//               }
//            }
//        }
//    }


    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("index")
            when (index) {
                "0" -> {
                    rice_kind.visibility = View.VISIBLE
                    rice_kind.text = "早稻"
                }
                "1" -> {
                    rice_kind.visibility = View.VISIBLE
                    rice_kind.text = "中稻"
                }
                "2" -> {
                    rice_kind.visibility = View.VISIBLE
                    rice_kind.text = "晚稻"
                }
                "-1" -> {
                    Log.e("返回错误", "return error")
                }
            }
        }
    }

    private var speciesList: MutableList<Species> = ArrayList()
    private fun initspecies() {
        for (i in 0..0) {
            val ziduan1 = Species("水稻")
            speciesList.add(ziduan1)
            val ziduan2 = Species("玉米")
            speciesList.add(ziduan2)
            val ziduan3 = Species("大豆")
            speciesList.add(ziduan3)
            val ziduan4 = Species("花生")
            speciesList.add(ziduan4)
            val ziduan5 = Species("黄瓜")
            speciesList.add(ziduan5)
            val ziduan6 = Species("茄子")
            speciesList.add(ziduan6)
            val ziduan7 = Species("葵花籽")
            speciesList.add(ziduan7)
            val ziduan8 = Species("字段")
            speciesList.add(ziduan8)
        }
    }

    private var ricekindList: MutableList<Species> = ArrayList()
    private fun initricekind() {
        for (i in 0..0) {
            val ziduan1 = Species("早稻")
            ricekindList.add(ziduan1)
            val ziduan2 = Species("中稻")
            ricekindList.add(ziduan2)
            val ziduan3 = Species("晚稻")
            ricekindList.add(ziduan3)

        }
    }

    //     val species = arrayOf("玉米", "花生", "土豆")

    private fun initViews() {
        mAcceptDisasterSpeciesToolbar.title = "受灾作物"
        (activity as AppCompatActivity).setSupportActionBar(mAcceptDisasterSpeciesToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): AcceptDisasterSpeciesFragment {
        val args = Bundle()
        val fragment = AcceptDisasterSpeciesFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_disasterspecies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initspecies()
        initricekind()
        val adapter = SpeciesAdapter(context, R.layout.simple_list_item1, speciesList)
        acceptspeciesitems.adapter = adapter
        acceptspeciesitems.choiceMode = AbsListView.CHOICE_MODE_SINGLE

        val ricekindadapter = SpeciesAdapter(context, R.layout.simple_list_item1, ricekindList)
        acceptspeciesriceitems.adapter = ricekindadapter
        acceptspeciesriceitems.choiceMode = AbsListView.CHOICE_MODE_SINGLE

        //  testbutton.setOnClickListener(this)
        acceptspeciesitems.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 0) {
                //隐藏旧listview 显现新listview
                acceptspeciesitems.visibility = View.INVISIBLE
                testicon1.visibility = View.INVISIBLE
                mAcceptDisasterSpeciesToolbar.title = "水稻"
                acceptspeciesriceitems.visibility = View.VISIBLE
                acceptspeciesriceitems.setOnItemClickListener { adapterView, view, i, l ->
                    index_index = i
                    val bundle = Bundle()
                    bundle.putString("index_index", index_index.toString())
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    pop()
                }
            } else {
                index = i
                val bundle = Bundle()
                bundle.putString("index", index.toString())
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                pop()
            }
        }

//        val selector = AddressSelector(context)
//        selector.onAddressSelectedListener = OnAddressSelectedListener { province, city, county, street -> }
//        val view = selector.view
//        mAddressRL.addView(view)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    override fun onBackPressedSupport(): Boolean {
        if (acceptspeciesriceitems.visibility==View.VISIBLE){
            acceptspeciesriceitems.visibility=View.INVISIBLE
            acceptspeciesitems.visibility=View.VISIBLE
            mAcceptDisasterSpeciesToolbar.title="受灾作物"
            return true
        }

        val bundle = Bundle()
        bundle.putString("index", "-1")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }
}
