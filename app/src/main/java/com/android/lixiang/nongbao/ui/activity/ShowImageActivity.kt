package com.android.lixiang.nongbao.ui.activity

import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.*
import android.widget.ImageView
import com.android.lixiang.nongbao.R
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_show_image.*
import kotlinx.android.synthetic.main.show_image_fragment.view.*
import java.io.File

class ShowImageActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    lateinit var string_array: Array<String>
    lateinit var url: Array<String>
    private var mSize = 0

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build())
        setContentView(R.layout.activity_show_image)
        url = arrayOf("0","0","0","0")
//        string_array[0] = intent.getStringArrayListExtra("images").elementAt(0)
//        if (intent.getStringExtra("picAmount").toInt() >= 2 && !intent.getStringArrayListExtra("images").elementAt(1).isEmpty())
//            string_array[1] = intent.getStringArrayListExtra("images").elementAt(1)
//        if (intent.getStringExtra("picAmount").toInt() == 3 && !intent.getStringArrayListExtra("images").elementAt(2).isEmpty())
//            string_array[2] = intent.getStringArrayListExtra("images").elementAt(2)

        Logger.d("RERUN")
        for (i in 0 until intent.extras.getStringArray("IMAGES").size){
            url[i] = intent.extras.getStringArray("IMAGES")[i]
            if (url[i] != "0"){
                mSize ++
            }
        }
        Logger.d(url)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        show_image_vp.adapter = mSectionsPagerAdapter
        show_image_vp.currentItem = intent.extras.getString("INDEX").toInt()
        show_image_vp.offscreenPageLimit = 2
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position, url)
        }

        override fun getCount(): Int {
            return mSize
        }
    }


    class PlaceholderFragment : Fragment() {
        private var rootView: View? = null
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {

            if (rootView != null) {
                var parent = rootView!!.parent
                if (parent != null)
                    (parent as ViewGroup).removeView(rootView)
            } else {
                rootView = inflater.inflate(R.layout.show_image_fragment, container, false)
                rootView!!.image.setImageURI(Uri.fromFile(File(url[arguments!!.getInt(ARG_SECTION_NUMBER)])))
//                rootView!!.image.setImageResource()
//                rootView!!.image.setImageURI(Uri.fromFile(File(url[arguments!!.getInt(ARG_SECTION_NUMBER)])))
                urlString = url[arguments!!.getInt(ARG_SECTION_NUMBER)]
//                rootView!!.image.onPhotoTapListener = me.relex.photodraweeview.OnPhotoTapListener { _, _, _ ->
//                    activity!!.finish()
//                    activity!!.overridePendingTransition(0, 0)
//                }
            }
            return rootView
        }


        companion object {
            private val ARG_SECTION_NUMBER = "section_number"
            var url: Array<String> = arrayOf("0", "1", "2", "3")
            var urlString = ""

            fun newInstance(sectionNumber: Int, array: Array<String>): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                url = array
                return fragment
            }
        }
    }
}
