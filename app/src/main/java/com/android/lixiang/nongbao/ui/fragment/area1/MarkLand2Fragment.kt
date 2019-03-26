package com.android.lixiang.nongbao.ui.fragment.area1

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amap.api.mapcore.util.it
import com.amap.api.maps.utils.SpatialRelationUtil
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.R
import com.blankj.utilcode.util.ImageUtils
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.*
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.expressions.Expression.zoom
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_mark_land.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.util.ArrayList
import java.util.HashMap

class MarkLand2Fragment : SupportFragment(), View.OnClickListener {

    private var mapboxMap: MapboxMap? = null
    private val PERMISSIONS_REQUEST_LOCATION = 100
    private var mPolyline: Polyline? = null
    private var mPolyline2: Polyline? = null
    private val ids = ArrayList<String>()
    private val latlngs = HashMap<String, LatLng>()
    private var marker: Marker? = null
    private var markers: MutableList<Marker> = mutableListOf()
    private var pois2: MutableList<LatLng> = ArrayList()
    private var polygon: Polygon? = null
    private var flag = false
    private var pointCount = 0
    private var f: MarkLand2Fragment? = null
    private var mSelectDoneFlag = false
    private var area: String? = null
    private var location: MutableList<String> = ArrayList()
    private var LOCATION_POINTS_ARRAY: String? = ""
    private var la: Double = 0.toDouble()
    private var lo: Double = 0.toDouble()
    private var size: Int = 0
    private var TRANSFORM = 0.0015
    private var position: Location? = null
    private var ZOOM_LEVEL: Double = 15.0
    private var layerFlag: Boolean? = false
    override fun onClick(p0: View?) {
        when (p0) {
            mMapToolbarBackBtn -> {
                val bundle = Bundle()
                bundle.putString("LOCATION_POINTS", "")
                bundle.putString("LOCATION_CENTER", "")
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                mapView.onDestroy()
                pop()
            }

            mMapCancelIV -> {
                mMapInfoMaskRL.visibility = View.GONE

            }

            mDoneBtn -> {
                if (!mSelectDoneFlag) {
                    if (LOCATION_POINTS_ARRAY!!.isNotEmpty())
                        LOCATION_POINTS_ARRAY = ""
                    if (location.size != 0)
                        location.clear()
                    for (i in 0 until pois2.size) {
                        location.add(pois2[i].latitude.toString())
                        location.add(pois2[i].longitude.toString())
                    }
                    LOCATION_POINTS_ARRAY = location.toString().substring(1, location.toString().length - 1)
                    Logger.d(LOCATION_POINTS_ARRAY)

                    if (pois2.size != 0) {
                        area = (getArea(pois2).toFloat() * TRANSFORM).toString()
                        if (!area!!.contains("E")) {
                            mDoneBtnTitle.text = "下一步"
                            flag = true
                            var l: LatLng? = null
                            la = 0.0
                            lo = 0.0
                            size = ids.size

                            for (i in 0 until size) {
                                l = latlngs[ids[i]]
                                la += l!!.latitude
                                lo += l.longitude
                            }

                            drawLine2()
                            drawPolygon()
                            mProgressBar.visibility = View.VISIBLE
                            mResetRelativeLayout.visibility = View.GONE
                            mResetBtn.visibility = View.GONE
                            Handler().postDelayed({
                                mAreaTextView.text = area
                                mProgressBar.visibility = View.GONE
                                mReset2View.visibility = View.VISIBLE
                                mReset2Btn.visibility = View.VISIBLE
                                mReset2RL.visibility = View.VISIBLE
                            }, 100)
                            mSelectDoneFlag = true
                        } else {
                            mResetRL.visibility = View.VISIBLE
                            mDoneRelativeLayout.visibility = View.GONE
                            val snackBar = Snackbar.make(mRootView, "无法识别面积，请重新选择区域", Snackbar.LENGTH_SHORT)
                            snackBar.setAction(getString(R.string.confirm_2)) {
                                snackBar.dismiss()
                            }
                            snackBar.setActionTextColor(Color.parseColor("#F5A623"))
                            snackBar.show()
                        }
                    } else {
                        val snackBar = Snackbar.make(mRootView, "请选择区域", Snackbar.LENGTH_SHORT)
                        snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
                        snackBar.setActionTextColor(Color.parseColor("#F5A623"))
                        snackBar.show()
                    }
                } else {
                    mSelectDoneFlag = false
                    val bundle = Bundle()
                    bundle.putString("LOCATION_POINTS", LOCATION_POINTS_ARRAY)
                    bundle.putString("LOCATION_AREA", area)
                    bundle.putString("LOCATION_CENTER", "" + GetCenter.getCenterPoint(pois2).latitude + "," + GetCenter.getCenterPoint(pois2).longitude)
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    pop()
                }
            }

            mResetBtn -> {
                mSelectDoneFlag = false
                if (mTitleRelativeLayout.visibility == View.GONE)
                    mTitleRelativeLayout.visibility = View.VISIBLE
                if (polygon != null)
                    polygon!!.remove()
                if (mPolyline2 != null)
                    mPolyline2!!.remove()
                if (mPolyline != null)
                    mPolyline!!.remove()
                for (i in 0 until markers.size) {
                    markers[i].remove()
                }
                latlngs.clear()
                ids.clear()
                flag = false
                area = null
                pois2.clear()
            }

            mReset2Btn -> {
                mSelectDoneFlag = false
                if (mTitleRelativeLayout.visibility == View.GONE)
                    mTitleRelativeLayout.visibility = View.VISIBLE
                if (polygon != null)
                    polygon!!.remove()
                if (mPolyline != null)
                    mPolyline!!.remove()
                if (mPolyline2 != null)
                    mPolyline2!!.remove()
                for (i in 0 until markers.size) {
                    markers[i].remove()
                }
                latlngs.clear()
                ids.clear()
                flag = false
                area = null
                pois2.clear()

                mResetRelativeLayout.visibility = View.VISIBLE
                mResetBtn.visibility = View.VISIBLE

                mReset2View.visibility = View.GONE
                mReset2Btn.visibility = View.GONE
                mReset2RL.visibility = View.GONE
            }

            mResetRL -> {
                mSelectDoneFlag = false

                if (polygon != null)
                    polygon!!.remove()
                if (mPolyline2 != null)
                    mPolyline2!!.remove()
                mPolyline!!.remove()
                for (i in 0 until markers.size) {
                    markers[i].remove()
                }
                latlngs.clear()
                ids.clear()
                flag = false
                area = null
                pois2.clear()

                mResetRL.visibility = View.GONE
                mDoneRelativeLayout.visibility = View.VISIBLE
            }

            mLocateBtn -> {
                moveToCurrentLocation()
            }

            mZoomInBtn -> {
                ++ZOOM_LEVEL
                mapboxMap!!.animateCamera(CameraUpdateFactory.zoomIn())
            }

            mZoomOutBtn -> {
                --ZOOM_LEVEL
                mapboxMap!!.animateCamera(CameraUpdateFactory.zoomOut())
            }

            mLayerBtn -> {
                layerFlag = if (!layerFlag!!) {
                    mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2019/03/25/02ac406d4018dd5e8010f22d7f3760ab.json")
                    true
                } else {
                    mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2019/03/25/1f7144f640b2e9bf80bd6f49fb3bb821.json")
                  false
                }
            }
        }
    }

    fun newInstance(): MarkLand2Fragment {
        val args = Bundle()
        val fragment = MarkLand2Fragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(activity!!, "pk.eyJ1IjoibGl4eGlhbmciLCJhIjoiY2psbnRndnJpMWhwMjN3bmRzZHZ6ejIzaCJ9.2PN9SeRacMAAI8dhc3-1ew")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews(savedInstanceState)

    }

    private fun initViews(savedInstanceState: Bundle?) {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        mMapToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mMapToolbar)
        if (!moveToCurrentLocation()) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
        }
        mapView.onCreate(savedInstanceState)

        mMapToolbarBackBtn.setOnClickListener(this)
        mDoneBtn.setOnClickListener(this)
        mResetBtn.setOnClickListener(this)
        mReset2Btn.setOnClickListener(this)
        mLocateBtn.setOnClickListener(this)
        mZoomInBtn.setOnClickListener(this)
        mZoomOutBtn.setOnClickListener(this)
        mMapCancelIV.setOnClickListener(this)
        mResetRL.setOnClickListener(this)
        mMapCancelIV.setOnClickListener(this)
        mLayerBtn.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_mark_land, container, false)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION ->
                if (grantResults.isNotEmpty()) {
                    moveToCurrentLocation()
                } else {
                    toast("您拒绝了定位权限,无法更新到您当前位置")
                }
        }
    }


    override fun onResume() {
        super.onResume()
        if (mapView != null)
            mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (mapView != null)
            mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        if (mapView != null)

            mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mapView != null)
            mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (mapView != null)

            mapView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mapView != null)
            mapView.onDestroy()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    @SuppressLint("MissingPermission")
    fun moveToCurrentLocation(): Boolean {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.toast("您未允许定位权限,无法更新到您的位置")
            return false
        }

        if (mapView != null) {
            mapView.getMapAsync {
                mapboxMap = it
                val uiSettings = mapboxMap!!.uiSettings
                uiSettings.isCompassEnabled = false//隐藏指南针
                uiSettings.isLogoEnabled = false//隐藏logo
                uiSettings.isTiltGesturesEnabled = true//设置是否可以调整地图倾斜角
                uiSettings.isRotateGesturesEnabled = true//设置是否可以旋转地图
                uiSettings.isAttributionEnabled = false//设置是否显示那个提示按钮
//                mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2018/08/27/981883084077745280e89583ff09ee42.json")
                mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2019/03/25/02ac406d4018dd5e8010f22d7f3760ab.json")
//                        app:mapbox_styleUrl="http://bmob-cdn-20607.b0.upaiyun.com/2018/08/27/981883084077745280e89583ff09ee42.json"
                mapboxMap!!.setOnMapClickListener {
                    Logger.d("CHECKAREA" + checkArea(it, pois2))

                    pointCount++
                    if (pointCount > 2) {
                        mDoneRelativeLayout.visibility = View.VISIBLE
                    }

                    if (mTitleRelativeLayout.visibility == View.VISIBLE)
                        mTitleRelativeLayout.visibility = View.GONE

                    if (!flag) {
                        addMarker(it.latitude, it.longitude, mapboxMap)
                        drawLine()
                    } else {
                        if (checkArea(it, pois2))
                            mMapInfoMaskRL.visibility = View.VISIBLE
                    }
                }

                if (arguments!!.getString("LOCATION_POINTS") != null && arguments!!.getString("LOCATION_CENTER") != null) {
                    initArea(arguments!!.getString("LOCATION_POINTS"), arguments!!.getString("LOCATION_CENTER"))

                }
            }
            mapView.getMapAsync {
//                mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2018/08/27/981883084077745280e89583ff09ee42.json")
                mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2019/03/25/02ac406d4018dd5e8010f22d7f3760ab.json")
                val locationEngine = LocationEngineProvider(activity!!).obtainBestLocationEngineAvailable();
                locationEngine.activate()
                val lastLocation = locationEngine.lastLocation
                lastLocation?.let {
                    if (mapView != null)
                        mapView.getMapAsync {
                            position = lastLocation
                            it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastLocation), 15.0), 1000)
                            mapboxMap!!.addMarker(MarkerOptions()
                                    .position(LatLng(lastLocation))
                                    .icon(IconFactory.getInstance(activity!!).fromBitmap(ImageUtils.scale(BitmapFactory.decodeResource(resources, R.drawable.img_location), DimenUtil().dip2px(activity!!, 18F), DimenUtil().dip2px(activity!!, 24F)))))
//                            Handler().postDelayed({
//                                mMapViewMask.visibility = View.GONE
//                            }, 2000)

                        }
                }
                locationEngine.addLocationEngineListener(object : LocationEngineListener {
                    override fun onLocationChanged(location: Location?) {
                        location?.let {
                            //                            mapView.getMapAsync {
//                                it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location), 15.0), 1000)
//                            }
//                            mapView.getMapAsync {
//                                it.addMarker(MarkerOptions()
//                                        .position(LatLng(location))
//                                        .icon(IconFactory.getInstance(activity!!).fromBitmap(ImageUtils.scale(BitmapFactory.decodeResource(resources, R.drawable.img_location), DimenUtil().dip2px(activity!!, 18F), DimenUtil().dip2px(activity!!, 24F)))))
//
//                            }

//                            mapView.getMapAsync {
//                                it.addMarker(MarkerOptions().position(LatLng(location)).icon(IconFactory.getInstance(activity!!).fromResource(R.drawable.img_location)))
//                            }
                            position = location
                            mapboxMap!!.addMarker(MarkerOptions()
                                    .position(LatLng(location))
                                    .icon(IconFactory.getInstance(activity!!).fromBitmap(ImageUtils.scale(BitmapFactory.decodeResource(resources, R.drawable.img_location), DimenUtil().dip2px(activity!!, 18F), DimenUtil().dip2px(activity!!, 24F)))))

//                            Handler().postDelayed({
//                                mMapViewMask.visibility = View.GONE
//                            }, 2000)
                        }
                    }

                    override fun onConnected() {
                        //链接定位服务
                    }
                })
            }
        }
        return true
    }

    private fun getArea(pts: List<LatLng>): String {
        var totalArea = 0.0// 初始化总面积
        var LowX = 0.0
        var LowY = 0.0
        var MiddleX = 0.0
        var MiddleY = 0.0
        var HighX = 0.0
        var HighY = 0.0
        var AM = 0.0
        var BM = 0.0
        var CM = 0.0
        var AL = 0.0
        var BL = 0.0
        var CL = 0.0
        var AH = 0.0
        var BH = 0.0
        var CH = 0.0
        var CoefficientL = 0.0
        var CoefficientH = 0.0
        var ALtangent = 0.0
        var BLtangent = 0.0
        var CLtangent = 0.0
        var AHtangent = 0.0
        var BHtangent = 0.0
        var CHtangent = 0.0
        var ANormalLine = 0.0
        var BNormalLine = 0.0
        var CNormalLine = 0.0
        var OrientationValue = 0.0
        var AngleCos = 0.0
        var Sum1 = 0.0
        var Sum2 = 0.0
        var Count2 = 0.0
        var Count1 = 0.0
        var Sum = 0.0
        val Radius = 6378137.0// WGS84椭球半径
        val Count = pts.size
        //最少3个点
        if (Count < 3) {
            return ""
        }
        for (i in 0 until Count) {
            if (i == 0) {
                LowX = pts[Count - 1].longitude * Math.PI / 180
                LowY = pts[Count - 1].latitude * Math.PI / 180
                MiddleX = pts[0].longitude * Math.PI / 180
                MiddleY = pts[0].latitude * Math.PI / 180
                HighX = pts[1].longitude * Math.PI / 180
                HighY = pts[1].latitude * Math.PI / 180
            } else if (i == Count - 1) {
                LowX = pts[Count - 2].longitude * Math.PI / 180
                LowY = pts[Count - 2].latitude * Math.PI / 180
                MiddleX = pts[Count - 1].longitude * Math.PI / 180
                MiddleY = pts[Count - 1].latitude * Math.PI / 180
                HighX = pts[0].longitude * Math.PI / 180
                HighY = pts[0].latitude * Math.PI / 180
            } else {
                LowX = pts[i - 1].longitude * Math.PI / 180
                LowY = pts[i - 1].latitude * Math.PI / 180
                MiddleX = pts[i].longitude * Math.PI / 180
                MiddleY = pts[i].latitude * Math.PI / 180
                HighX = pts[i + 1].longitude * Math.PI / 180
                HighY = pts[i + 1].latitude * Math.PI / 180
            }
            AM = Math.cos(MiddleY) * Math.cos(MiddleX)
            BM = Math.cos(MiddleY) * Math.sin(MiddleX)
            CM = Math.sin(MiddleY)
            AL = Math.cos(LowY) * Math.cos(LowX)
            BL = Math.cos(LowY) * Math.sin(LowX)
            CL = Math.sin(LowY)
            AH = Math.cos(HighY) * Math.cos(HighX)
            BH = Math.cos(HighY) * Math.sin(HighX)
            CH = Math.sin(HighY)
            CoefficientL = (AM * AM + BM * BM + CM * CM) / (AM * AL + BM * BL + CM * CL)
            CoefficientH = (AM * AM + BM * BM + CM * CM) / (AM * AH + BM * BH + CM * CH)
            ALtangent = CoefficientL * AL - AM
            BLtangent = CoefficientL * BL - BM
            CLtangent = CoefficientL * CL - CM
            AHtangent = CoefficientH * AH - AM
            BHtangent = CoefficientH * BH - BM
            CHtangent = CoefficientH * CH - CM
            AngleCos = (AHtangent * ALtangent + BHtangent * BLtangent + CHtangent * CLtangent) / (Math.sqrt(AHtangent * AHtangent + BHtangent * BHtangent

                    + CHtangent * CHtangent) * Math.sqrt(ALtangent * ALtangent + BLtangent * BLtangent + CLtangent * CLtangent))
            AngleCos = Math.acos(AngleCos)
            ANormalLine = BHtangent * CLtangent - CHtangent * BLtangent
            BNormalLine = 0 - (AHtangent * CLtangent - CHtangent * ALtangent)
            CNormalLine = AHtangent * BLtangent - BHtangent * ALtangent
            if (AM != 0.0)
                OrientationValue = ANormalLine / AM
            else if (BM != 0.0)
                OrientationValue = BNormalLine / BM
            else
                OrientationValue = CNormalLine / CM
            if (OrientationValue > 0) {
                Sum1 += AngleCos
                Count1++
            } else {
                Sum2 += AngleCos
                Count2++
            }
        }

        val tempSum1: Double
        val tempSum2: Double
        tempSum1 = Sum1 + (2.0 * Math.PI * Count2 - Sum2)
        tempSum2 = 2.0 * Math.PI * Count1 - Sum1 + Sum2
        if (Sum1 > Sum2) {
            if (tempSum1 - (Count - 2) * Math.PI < 1)
                Sum = tempSum1
            else
                Sum = tempSum2
        } else {
            if (tempSum2 - (Count - 2) * Math.PI < 1)
                Sum = tempSum2
            else
                Sum = tempSum1
        }
        totalArea = (Sum - (Count - 2) * Math.PI) * Radius * Radius

        return Math.floor(totalArea).toString() // 返回总面积
    }

    private fun drawLine() {
        if (mPolyline != null) {
            mPolyline!!.remove()
        }
        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        for (i in ids.indices) {
            l = latlngs[ids[i]]
            points.add(l!!)
        }
        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline = mapboxMap!!.addPolyline(ooPolyline)
    }

    private fun drawLine2() {
        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        l = latlngs[ids[0]]
        points.add(l!!)

        var l2: LatLng? = null
        l2 = latlngs[ids[ids.size - 1]]
        points.add(l2!!)

        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline2 = mapboxMap!!.addPolyline(ooPolyline)
    }

    fun drawPolygon() {
        var ll: LatLng? = null
        val pts = ArrayList<LatLng>()
        for (i in ids.indices) {
            val s = ids[i]
            Log.e("aaa", "key= " + s + " and value= " + latlngs[s].toString())
            ll = latlngs[s]
            pts.add(ll!!)
        }
        val polygonOption = PolygonOptions()
                .addAll(pts)
                .fillColor(Color.parseColor("#8CEF6677"))
        polygon = mapboxMap!!.addPolygon(polygonOption) as Polygon
    }

    private fun addMarker(latitude: Double, longitude: Double, mapboxMap: MapboxMap?) {
        val point = LatLng(latitude, longitude)
        val option = MarkerOptions()
                .position(point)
                .icon(IconFactory.getInstance(activity!!).fromResource(R.drawable.ic_point))
        marker = mapboxMap!!.addMarker(option)
        markers.add(marker!!)
        val id = marker!!.id.toString()
        latlngs[id] = LatLng(latitude, longitude)
        pois2.add(LatLng(latitude, longitude))
        ids.add(id)
    }

    private fun initArea(mLocationPoints: String?, string: String) {
        if (mLocationPoints != null) {
            preDraw(mLocationPoints, string)
        }
    }

    private fun preDraw(string: String, string3: String) {
        Logger.d("PREDRAW")
        mDoneRelativeLayout.visibility = View.VISIBLE
        mResetRelativeLayout.visibility = View.GONE
        mResetBtn.visibility = View.GONE
        mAreaTextView.text = arguments!!.getString("LOCATION_AREA")
        mProgressBar.visibility = View.GONE
        mReset2View.visibility = View.VISIBLE
        mReset2Btn.visibility = View.VISIBLE
        mReset2RL.visibility = View.VISIBLE
        mSelectDoneFlag = true
        if (mTitleRelativeLayout.visibility == View.VISIBLE)
            mTitleRelativeLayout.visibility = View.GONE


        var locationArray = string.split(",")
        val pts = ArrayList<LatLng>()

        for (i in 0 until locationArray.size step 2) {
            var tempLocation: LatLng? = null
            tempLocation = LatLng(locationArray[i].toDouble(), locationArray[i + 1].toDouble())
            pts.add(tempLocation)
        }

        /**
         * polygon
         */
        val polygonOption = PolygonOptions()
                .addAll(pts)
                .fillColor(Color.parseColor("#8CEF6677"))
        polygon = mapboxMap!!.addPolygon(polygonOption) as Polygon

        /**
         * line
         */

        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(pts)
        mPolyline = mapboxMap!!.addPolyline(ooPolyline)

        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        l = pts[0]
        points.add(l!!)

        var l2: LatLng? = null
        l2 = pts[pts.size - 1]
        points.add(l2!!)

        val ooPolyline2 = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline2 = mapboxMap!!.addPolyline(ooPolyline2)

        pois2 = pts
        flag = true
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("LOCATION_POINTS", "")
        bundle.putString("LOCATION_CENTER", "")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        if (mapView != null)
            mapView.onDestroy()
        return super.onBackPressedSupport()
    }


    private fun isPolygonContainsPoint(var0: List<LatLng>?, var1: LatLng?): Boolean {
        if (var0 != null && var0.size != 0 && var1 != null) {
            var var2: Int
            var2 = 0
            while (var2 < var0.size) {
                if (var1.longitude == var0[var2].longitude && var1.latitude == var0[var2].latitude) {
                    return true
                }
                ++var2
            }

            var2 = 0
            val var3 = false
            var var4: LatLng? = null
            var var5: LatLng? = null
            var var6 = 0.0
            val var8 = var0.size

            for (var9 in 0 until var8) {
                var4 = var0[var9]
                var5 = var0[(var9 + 1) % var8]
                if (var4.latitude != var5.latitude && var1.latitude >= Math.min(var4.latitude, var5.latitude) && var1.latitude <= Math.max(var4.latitude, var5.latitude)) {
                    var6 = (var1.latitude - var4.latitude) * (var5.longitude - var4.longitude) / (var5.latitude - var4.latitude) + var4.longitude
                    if (var6 == var1.longitude) {
                        return true
                    }

                    if (var6 < var1.longitude) {
                        ++var2
                    }
                }
            }

            return var2 % 2 == 1
        } else {
            return false
        }
    }

    private fun checkArea(p0: LatLng, pois: MutableList<LatLng>): Boolean {
        return isPolygonContainsPoint(pois, p0)
//        var name: String? = null
//        var polygon: Polygon? = null
//        areas.clear()
//        for (i in aliasname.indices) {
//            name = aliasname[i]
//            Log.e("aaa", "检查的别名是：$name")
//            polygon = polygonMap[name]
//            val s = polygon!!.points.toString()
//            Log.e("aaa", "sssss---->$s")
//            polygonContainsPoint = SpatialRelationUtil.isPolygonContainsPoint(polygon!!.points, LatLng(latitude, longitude))
//            if (polygonContainsPoint) {
//                Toast.makeText(this, "该点在 $name 区域内。", Toast.LENGTH_SHORT).show()
//                areas.add(name)
//            }
//        }
//        Log.e("aaa", "areas" + areas.toString())
//        if (areas.size > 0) {
//            val message = areas.toString()
//            showDialog("所在的区域有：$message")
//        } else {
//            showDialog("该点不在任何区域内。")
//        }
    }


}