package com.android.lixiang.nongbao.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.amap.api.maps.model.LatLng
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.R.id.mChooseSpeciesRLToolbar
import com.android.lixiang.nongbao.R.id.speciesitems
import com.android.lixiang.nongbao.ui.Bank
import com.android.lixiang.nongbao.ui.Species
import com.android.lixiang.nongbao.ui.adapter.BankAdapter
import com.android.lixiang.nongbao.ui.adapter.SpeciesAdapter
import com.android.lixiang.nongbao.ui.fragment.area1.IdInfoFragment
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.OnAddressSelectedListener
import kotlinx.android.synthetic.main.fragment_accept_insurance.*
import kotlinx.android.synthetic.main.fragment_address_info.*
import kotlinx.android.synthetic.main.fragment_all_apps.*
import kotlinx.android.synthetic.main.fragment_choose_bank.*
import kotlinx.android.synthetic.main.fragment_choose_speciesname.*
import kotlinx.android.synthetic.main.fragment_fill_survey.*
import kotlinx.android.synthetic.main.fragment_id_info.*
import kotlinx.android.synthetic.main.fragment_location_info.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class ChooseSpeciesNameFragment : SupportFragment() {
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



    override fun onFragmentResult(requestCode: Int,resultCode: Int,data: Bundle){
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK){
            val index = data.getString("index")
            when (index) {
                "0" -> {
                    rice_kind.visibility=View.VISIBLE
                    rice_kind.text="早稻"
                }
                "1" -> {
                    rice_kind.visibility=View.VISIBLE
                    rice_kind.text="中稻"
                }
                "2" -> {
                    rice_kind.visibility=View.VISIBLE
                    rice_kind.text="晚稻"
                }
                "-1" -> {
                    Log.e("返回错误","return error")
                }
            }
        }
    }

    private var speciesList: MutableList<Species> = ArrayList()
    private fun initspecies(){
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
            val ziduan6 =Species("茄子")
            speciesList.add(ziduan6)
            val ziduan7 = Species("葵花籽")
            speciesList.add(ziduan7)
            val ziduan8 = Species("字段")
            speciesList.add(ziduan8)
        }
    }

    //     val species = arrayOf("玉米", "花生", "土豆")

    private fun initViews() {
        mChooseSpeciesRLToolbar.title = "品种名称"
        (activity as AppCompatActivity).setSupportActionBar(mChooseSpeciesRLToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): ChooseSpeciesNameFragment {
        val args = Bundle()
        val fragment = ChooseSpeciesNameFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_speciesname, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initspecies()

        val adapter= SpeciesAdapter(context,R.layout.simple_list_item1,speciesList)
        speciesitems.adapter=adapter
        speciesitems.choiceMode=AbsListView.CHOICE_MODE_SINGLE
        //  testbutton.setOnClickListener(this)
        speciesitems.setOnItemClickListener {adapterView, view, i, l ->
            if (i==0){
                startForResult(ChooseRiceKindFragment().newInstance(),1)
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
}
