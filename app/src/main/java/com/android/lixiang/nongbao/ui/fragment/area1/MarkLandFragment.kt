//package com.android.lixiang.nongbao.ui.fragment.area1
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.graphics.BitmapFactory
//import android.graphics.Color
//import android.os.Build
//import android.os.Bundle
//import android.os.Handler
//import android.support.annotation.RequiresApi
//import android.support.design.widget.Snackbar
//import android.support.v4.app.ActivityCompat
//import android.support.v7.app.AppCompatActivity
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewGroup
//import com.amap.api.location.AMapLocation
//import com.amap.api.location.AMapLocationClient
//import com.amap.api.location.AMapLocationClientOption
//import com.amap.api.location.AMapLocationListener
//import com.amap.api.maps.AMap
//import com.amap.api.maps.CameraUpdateFactory
//import com.amap.api.maps.LocationSource
//import com.amap.api.maps.model.*
//import com.android.lixiang.base.utils.view.DimenUtil
//import com.android.lixiang.base.utils.view.StatusBarUtil
//import com.android.lixiang.nongbao.R
//import com.blankj.utilcode.util.ImageUtils
//import com.orhanobut.logger.Logger
//import kotlinx.android.synthetic.main.activity_map_info.*
//import kotlinx.android.synthetic.main.activity_mark_land.*
//import me.yokeyword.fragmentation.ISupportFragment
//import me.yokeyword.fragmentation.SupportFragment
//import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
//import me.yokeyword.fragmentation.anim.FragmentAnimator
//import java.text.SimpleDateFormat
//import java.util.*
//
//class MarkLandFragment : SupportFragment(), AMap.OnMapClickListener,
//        AMap.OnMapLongClickListener, AMap.OnCameraChangeListener,
//        AMap.OnMapTouchListener, LocationSource, AMapLocationListener, View.OnClickListener {
//    private var aMap: AMap? = null
//    private val ids = ArrayList<String>()
//    private val latlngs = HashMap<String, LatLng>()
//    private var mPolyline: Polyline? = null
//    private var la: Double = 0.toDouble()
//    private var lo: Double = 0.toDouble()
//    private var size: Int = 0
//    private var polygon: Polygon? = null
//    private var pois2: MutableList<LatLng> = ArrayList()
//    private var location: MutableList<String> = ArrayList()
//    private var LOCATION_POINTS_ARRAY: String? = ""
//    private var currentPositon: LatLng? = null
//    private var flag = false
//    private var mLocationClient: AMapLocationClient? = null
//    private var mLocationOption: AMapLocationClientOption? = null
//    private var mListener: LocationSource.OnLocationChangedListener? = null
//    private var isFirstLoc = true
//    private var mPointCount = 0
//    private var marker: Marker? = null
//    private var markers: MutableList<Marker> = mutableListOf()
//    private var area: String? = null
//    private var TRANSFORM = 0.0015
//    private var position: LatLng? = null
//    private var cameraPosition: CameraPosition? = null
//    private var mapZoom: Float = 0.toFloat()
//    private var mSelectDoneFlag = false
//
//    fun newInstance(): MarkLandFragment {
//        val args = Bundle()
//        val fragment = MarkLandFragment()
//        fragment.arguments = args
//        return fragment
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.activity_mark_land, container, false)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        initViews(savedInstanceState)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun initViews(savedInstanceState: Bundle?) {
//        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
//        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//
//        mMapToolbar.title = ""
//        (activity as AppCompatActivity).setSupportActionBar(mMapToolbar)
//
//        mMapView!!.onCreate(savedInstanceState)
//        handlePermisson()
//        init()
//        initArea()
//        mDoneBtn.setOnClickListener(this)
//        mResetBtn.setOnClickListener(this)
//        mReset2Btn.setOnClickListener(this)
//        mLocateBtn.setOnClickListener(this)
//        mZoomInBtn.setOnClickListener(this)
//        mZoomOutBtn.setOnClickListener(this)
//        mMapCancelIV.setOnClickListener(this)
//        mResetRL.setOnClickListener(this)
////        mZoomInRL.setOnClickListener(this)
////        mZoomOutRL.setOnClickListener(this)
//    }
//
//    private fun initArea() {
//        if (arguments!!.getString("LOCATION_POINTS") != null) {
//            preDraw(arguments!!.getString("LOCATION_POINTS"))
//        }
//    }
//
//    override fun onMapClick(p0: LatLng?) {
//        mPointCount++
//        if (mPointCount >= 3) {
//            mDoneRelativeLayout.visibility = View.VISIBLE
//        }
//        if (mTitleRelativeLayout.visibility == View.VISIBLE)
//            mTitleRelativeLayout.visibility = View.GONE
//
//        println("" + p0!!.latitude + "  " + p0.longitude)
//        currentPositon = p0
//
//        if (!flag) {
//            addMarker(p0.latitude, p0!!.longitude)
//            if (ids.size >= 2) {
//                drawLine()
//            }
//        } else {
////            checkArea(p0, pois2)
//            mMapInfoMaskRL.visibility = View.VISIBLE
//            mCheck_Property_OwnerTV.background.alpha=180
//        }
//    }
//
//    override fun onMapLongClick(p0: LatLng?) {
//    }
//
//    override fun onCameraChangeFinish(p0: CameraPosition?) {
//    }
//
//    override fun onCameraChange(p0: CameraPosition?) {
//    }
//
//    override fun onTouch(p0: MotionEvent?) {
//    }
//
//    override fun deactivate() {
//        mListener = null
//    }
//
//    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
//        mListener = p0
//    }
//
//    override fun onLocationChanged(p0: AMapLocation?) {
//        if (p0 != null) {
//            if (p0.errorCode == 0) {
//                p0.locationType
//                p0.latitude
//                p0.longitude
//                p0.accuracy
//                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                val date = Date(p0.time)
//                df.format(date)
//                p0.address
//                p0.country
//                p0.province
//                p0.city
//                p0.district
//                p0.street
//                p0.streetNum
//                p0.cityCode
//                p0.adCode
//
//                if (isFirstLoc) {
//                    aMap!!.moveCamera(CameraUpdateFactory.zoomTo(17f))
//                    aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(p0.latitude, p0.longitude)))
//                    mListener!!.onLocationChanged(p0)
//                    val buffer = StringBuffer()
//                    buffer.append(p0.country + ""
//                            + p0.province + ""
//                            + p0.city + ""
//                            + p0.province + ""
//                            + p0.district + ""
//                            + p0.street + ""
//                            + p0.streetNum)
////                    Toast.makeText(applicationContext, buffer.toString(), Toast.LENGTH_LONG).show()
//                    isFirstLoc = false
//                }
//
//                position = LatLng(p0.latitude, p0.longitude)
//            } else {
//                Log.e("AmapError", "location Error, ErrCode:"
//                        + p0.errorCode + ", errInfo:"
//                        + p0.errorInfo)
////                Toast.makeText(applicationContext, "定位失败", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    override fun onClick(v: View?) {
//        when (v) {
//
//            mMapCancelIV -> {
//                mMapInfoMaskRL.visibility = View.GONE
//            }
//
////            mZoomOutRL -> {
////                Logger.d("fdfdfddf")
////                cameraPosition = aMap!!.cameraPosition
////                mapZoom = cameraPosition!!.zoom
////                position = cameraPosition!!.target
////                aMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(position, --mapZoom))
////            }
////
////            mZoomInRL -> {
////                Logger.d("xcxcxcxc")
////                cameraPosition = aMap!!.cameraPosition
////                mapZoom = cameraPosition!!.zoom
////                position = cameraPosition!!.target
////                aMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(position, ++mapZoom))
////            }
//            mDoneBtn -> {
//                if (!mSelectDoneFlag) {
//                    Logger.d(pois2.toString())
//                    for (i in 0 until pois2.size) {
//                        location.add(pois2[i].latitude.toString())
//                        location.add(pois2[i].longitude.toString())
//                    }
//                    LOCATION_POINTS_ARRAY = location.toString().substring(1, location.toString().length - 1)
//                    if (pois2.size != 0) {
//                        area = (getArea(pois2).toFloat() * TRANSFORM).toString()
//                        if (!area!!.contains("E")) {
//                            mDoneBtnTitle.text = "下一步"
//                            Logger.d(area)
////                            area = area!!.substring(0, area!!.indexOf(".") + 3)
//                            Logger.d(area)
//                            flag = true
//                            var l: LatLng? = null
//                            la = 0.0
//                            lo = 0.0
//                            size = ids.size
//
//                            for (i in 0 until size) {
//                                l = latlngs[ids[i]]
//                                la += l!!.latitude
//                                lo += l.longitude
//                            }
//                            drawPolygon()
//                            mProgressBar.visibility = View.VISIBLE
//                            mResetRelativeLayout.visibility = View.GONE
//                            mResetBtn.visibility = View.GONE
//                            Handler().postDelayed({
//                                mAreaTextView.text = area
//                                mProgressBar.visibility = View.GONE
//                                mReset2View.visibility = View.VISIBLE
//                                mReset2Btn.visibility = View.VISIBLE
//                                mReset2RL.visibility = View.VISIBLE
//                            }, 100)
//                            mSelectDoneFlag = true
//                        } else {
//                            val snackBar = Snackbar.make(mRootView, "无法识别面积，请重新选择区域", Snackbar.LENGTH_SHORT)
//                            snackBar.setAction(getString(R.string.confirm_2)) {
//                                snackBar.dismiss()
//                                mResetRL.visibility = View.VISIBLE
//                                mDoneRelativeLayout.visibility = View.GONE
//                            }
//                            snackBar.setActionTextColor(Color.parseColor("#F5A623"))
//                            snackBar.show()
//                        }
//                    } else {
//                        val snackBar = Snackbar.make(mRootView, "请选择区域", Snackbar.LENGTH_SHORT)
//                        snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
//                        snackBar.setActionTextColor(Color.parseColor("#F5A623"))
//                        snackBar.show()
//                    }
//                } else {
//                    mSelectDoneFlag = false
//                    val bundle = Bundle()
//                    bundle.putString("LOCATION_POINTS", LOCATION_POINTS_ARRAY)
//                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//                    pop()
//                }
////                this.finish()
//            }
//
//            mResetBtn -> {
//                mSelectDoneFlag = false
//
//                if (polygon != null)
//                    polygon!!.remove()
//                if (mPolyline != null)
//                    mPolyline!!.remove()
//                for (i in 0 until markers.size) {
//                    markers[i].remove()
//                }
//                latlngs.clear()
//                ids.clear()
//                flag = false
//                area = null
//                pois2.clear()
//            }
//
//            mReset2Btn -> {
//                mSelectDoneFlag = false
//
//                polygon!!.remove()
//                mPolyline!!.remove()
//                for (i in 0 until markers.size) {
//                    markers[i].remove()
//                }
//                latlngs.clear()
//                ids.clear()
//                flag = false
//                area = null
//                pois2.clear()
//
//
//                mResetRelativeLayout.visibility = View.VISIBLE
//                mResetBtn.visibility = View.VISIBLE
//
//                mReset2View.visibility = View.GONE
//                mReset2Btn.visibility = View.GONE
//                mReset2RL.visibility = View.GONE
//            }
//
//            mResetRL -> {
//                mSelectDoneFlag = false
//
//                if (polygon != null)
//                    polygon!!.remove()
//                mPolyline!!.remove()
//                for (i in 0 until markers.size) {
//                    markers[i].remove()
//                }
//                latlngs.clear()
//                ids.clear()
//                flag = false
//                area = null
//                pois2.clear()
//
//                mResetRL.visibility = View.GONE
//                mDoneRelativeLayout.visibility = View.VISIBLE
//            }
//
//            mLocateBtn -> {
//                aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(position))
//            }
//
//            mZoomInBtn -> {
//                cameraPosition = aMap!!.cameraPosition
//                mapZoom = cameraPosition!!.zoom
//                position = cameraPosition!!.target
//                aMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(position, ++mapZoom))
//            }
//
//            mZoomOutBtn -> {
//                cameraPosition = aMap!!.cameraPosition
//                mapZoom = cameraPosition!!.zoom
//                position = cameraPosition!!.target
//                aMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(position, --mapZoom))
//            }
//        }
//    }
//
//    private fun isPolygonContainsPoint(var0: List<LatLng>?, var1: LatLng?): Boolean {
//        if (var0 != null && var0.size != 0 && var1 != null) {
//            var var2: Int
//            var2 = 0
//            while (var2 < var0.size) {
//                if (var1.longitude == var0[var2].longitude && var1.latitude == var0[var2].latitude) {
//                    return true
//                }
//                ++var2
//            }
//
//            var2 = 0
//            val var3 = false
//            var var4: LatLng? = null
//            var var5: LatLng? = null
//            var var6 = 0.0
//            val var8 = var0.size
//
//            for (var9 in 0 until var8) {
//                var4 = var0[var9]
//                var5 = var0[(var9 + 1) % var8]
//                if (var4.latitude != var5.latitude && var1.latitude >= Math.min(var4.latitude, var5.latitude) && var1.latitude <= Math.max(var4.latitude, var5.latitude)) {
//                    var6 = (var1.latitude - var4.latitude) * (var5.longitude - var4.longitude) / (var5.latitude - var4.latitude) + var4.longitude
//                    if (var6 == var1.longitude) {
//                        return true
//                    }
//
//                    if (var6 < var1.longitude) {
//                        ++var2
//                    }
//                }
//            }
//
//            return var2 % 2 == 1
//        } else {
//            return false
//        }
//    }
//
//    private fun checkArea(p0: LatLng, pois: MutableList<LatLng>) {
//        println("3_______" + isPolygonContainsPoint(pois, p0))
//
////        var name: String? = null
////        var polygon: Polygon? = null
////        areas.clear()
////        for (i in aliasname.indices) {
////            name = aliasname[i]
////            Log.e("aaa", "检查的别名是：$name")
////            polygon = polygonMap[name]
////            val s = polygon!!.points.toString()
////            Log.e("aaa", "sssss---->$s")
////            polygonContainsPoint = SpatialRelationUtil.isPolygonContainsPoint(polygon!!.points, LatLng(latitude, longitude))
////            if (polygonContainsPoint) {
////                Toast.makeText(this, "该点在 $name 区域内。", Toast.LENGTH_SHORT).show()
////                areas.add(name)
////            }
////        }
////        Log.e("aaa", "areas" + areas.toString())
////        if (areas.size > 0) {
////            val message = areas.toString()
////            showDialog("所在的区域有：$message")
////        } else {
////            showDialog("该点不在任何区域内。")
////        }
//    }
//
//    private fun preDraw(string: String) {
//        var locationArray = string.split(",")
//        val pts = ArrayList<LatLng>()
//
//        for (i in 0 until locationArray.size step 2) {
//            var tempLocation: LatLng? = null
//            tempLocation = LatLng(locationArray[i].toDouble(), locationArray[i + 1].toDouble())
//            pts.add(tempLocation)
//        }
//
//        val polygonOption = PolygonOptions()
//                .addAll(pts)
//                .strokeColor(Color.parseColor("#EF6677"))
//                .fillColor(Color.parseColor("#8CEF6677"))
//                .strokeWidth(DimenUtil().dip2px(activity!!, 2F).toFloat())
//        polygon = aMap!!.addPolygon(polygonOption) as Polygon
//        pois2 = pts
//        flag = true
//    }
//
//    private fun drawLine() {
//        if (mPolyline != null) {
//            mPolyline!!.remove()
//        }
//        val points = ArrayList<LatLng>()
//        var l: LatLng? = null
//        for (i in ids.indices) {
//            l = latlngs[ids[i]]
//            points.add(l!!)
//        }
//
//        val ooPolyline = PolylineOptions().width(DimenUtil().dip2px(activity!!, 2F).toFloat())
//                .color(Color.parseColor("#EF6677")).addAll(points)
//        mPolyline = aMap!!.addPolyline(ooPolyline) as Polyline
//    }
//
//    private fun addMarker(latitude: Double, longitude: Double) {
//        val point = LatLng(latitude, longitude)
//        val option = MarkerOptions()
//                .position(point)
////                .snippet("DefaultMarker")
//                .draggable(true)
//                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                        .decodeResource(resources, R.drawable.ic_point)))
//                .anchor(0.5F, 0.5F)
//        marker = aMap!!.addMarker(option)
//        markers.add(marker!!)
//        val id = marker!!.id
//        latlngs[id] = LatLng(latitude, longitude)
//        pois2.add(LatLng(latitude, longitude))
//        ids.add(id)
//    }
//
//    private fun drawPolygon() {
////        if (polygon != null) {
////            polygon!!.remove()
////        }
//        var ll: LatLng? = null
//        val pts = ArrayList<LatLng>()
//        for (i in ids.indices) {
//            val s = ids[i]
//            Log.e("aaa", "key= " + s + " and value= " + latlngs[s].toString())
//            ll = latlngs[s]
//            pts.add(ll!!)
//        }
//        val polygonOption = PolygonOptions()
//                .addAll(pts)
//                .strokeColor(Color.parseColor("#EF6677"))
//                .fillColor(Color.parseColor("#8CEF6677"))
//                .strokeWidth(DimenUtil().dip2px(activity!!, 2F).toFloat())
//        polygon = aMap!!.addPolygon(polygonOption) as Polygon
//    }
//
//    private fun init() {
//        if (aMap == null) {
//            aMap = mMapView.map
//            val settings = aMap!!.uiSettings
//            aMap!!.setLocationSource(this)
//            aMap!!.isMyLocationEnabled = true
//            settings.isMyLocationButtonEnabled = false
//            settings.isZoomControlsEnabled = false
//            settings.setLogoBottomMargin(-50)
//            setUpMap()
//            initLocation()
//        }
//    }
//
//    private fun initLocation() {
//
//        val myLocationStyle = MyLocationStyle()
//        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0))
//        myLocationStyle.strokeWidth(0F)
//
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(ImageUtils.scale(BitmapFactory
//                .decodeResource(resources, R.drawable.img_location), DimenUtil().dip2px(activity!!, 18F), DimenUtil().dip2px(activity!!, 24F))))
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
//        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0))
//        aMap!!.myLocationStyle = myLocationStyle
//
//
//        mLocationClient = AMapLocationClient(activity!!.applicationContext)
//        mLocationClient!!.setLocationListener(this)
//        mLocationOption = AMapLocationClientOption()
//        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//        mLocationOption!!.isNeedAddress = true
//        mLocationOption!!.isOnceLocation = false
//        mLocationOption!!.isWifiActiveScan = true
//        mLocationOption!!.isMockEnable = false
//        mLocationOption!!.interval = 2000
//        mLocationClient!!.setLocationOption(mLocationOption)
//        mLocationClient!!.startLocation()
//    }
//
//    private fun setUpMap() {
//        aMap!!.setOnMapClickListener(this)
//        aMap!!.setOnMapLongClickListener(this)
//        aMap!!.setOnCameraChangeListener(this)
//        aMap!!.setOnMapTouchListener(this)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        if (mMapView != null)
//            mMapView!!.onDestroy()
//        mLocationClient!!.stopLocation()
//        mLocationClient!!.onDestroy()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mMapView!!.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mMapView!!.onPause()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    fun handlePermisson() {
//        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
//        val checkSelfPermission = ActivityCompat.checkSelfPermission(activity!!, permission)
//        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
//            } else {
//                myRequestPermission()
//            }
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun myRequestPermission() {
//        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        requestPermissions(permissions, 1)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//        }
//    }
//
//    private fun getArea(pts: List<LatLng>): String {
//        var totalArea = 0.0// 初始化总面积
//        var LowX = 0.0
//        var LowY = 0.0
//        var MiddleX = 0.0
//        var MiddleY = 0.0
//        var HighX = 0.0
//        var HighY = 0.0
//        var AM = 0.0
//        var BM = 0.0
//        var CM = 0.0
//        var AL = 0.0
//        var BL = 0.0
//        var CL = 0.0
//        var AH = 0.0
//        var BH = 0.0
//        var CH = 0.0
//        var CoefficientL = 0.0
//        var CoefficientH = 0.0
//        var ALtangent = 0.0
//        var BLtangent = 0.0
//        var CLtangent = 0.0
//        var AHtangent = 0.0
//        var BHtangent = 0.0
//        var CHtangent = 0.0
//        var ANormalLine = 0.0
//        var BNormalLine = 0.0
//        var CNormalLine = 0.0
//        var OrientationValue = 0.0
//        var AngleCos = 0.0
//        var Sum1 = 0.0
//        var Sum2 = 0.0
//        var Count2 = 0.0
//        var Count1 = 0.0
//        var Sum = 0.0
//        val Radius = 6378137.0// WGS84椭球半径
//        val Count = pts.size
//        //最少3个点
//        if (Count < 3) {
//            return ""
//        }
//        for (i in 0 until Count) {
//            if (i == 0) {
//                LowX = pts[Count - 1].longitude * Math.PI / 180
//                LowY = pts[Count - 1].latitude * Math.PI / 180
//                MiddleX = pts[0].longitude * Math.PI / 180
//                MiddleY = pts[0].latitude * Math.PI / 180
//                HighX = pts[1].longitude * Math.PI / 180
//                HighY = pts[1].latitude * Math.PI / 180
//            } else if (i == Count - 1) {
//                LowX = pts[Count - 2].longitude * Math.PI / 180
//                LowY = pts[Count - 2].latitude * Math.PI / 180
//                MiddleX = pts[Count - 1].longitude * Math.PI / 180
//                MiddleY = pts[Count - 1].latitude * Math.PI / 180
//                HighX = pts[0].longitude * Math.PI / 180
//                HighY = pts[0].latitude * Math.PI / 180
//            } else {
//                LowX = pts[i - 1].longitude * Math.PI / 180
//                LowY = pts[i - 1].latitude * Math.PI / 180
//                MiddleX = pts[i].longitude * Math.PI / 180
//                MiddleY = pts[i].latitude * Math.PI / 180
//                HighX = pts[i + 1].longitude * Math.PI / 180
//                HighY = pts[i + 1].latitude * Math.PI / 180
//            }
//            AM = Math.cos(MiddleY) * Math.cos(MiddleX)
//            BM = Math.cos(MiddleY) * Math.sin(MiddleX)
//            CM = Math.sin(MiddleY)
//            AL = Math.cos(LowY) * Math.cos(LowX)
//            BL = Math.cos(LowY) * Math.sin(LowX)
//            CL = Math.sin(LowY)
//            AH = Math.cos(HighY) * Math.cos(HighX)
//            BH = Math.cos(HighY) * Math.sin(HighX)
//            CH = Math.sin(HighY)
//            CoefficientL = (AM * AM + BM * BM + CM * CM) / (AM * AL + BM * BL + CM * CL)
//            CoefficientH = (AM * AM + BM * BM + CM * CM) / (AM * AH + BM * BH + CM * CH)
//            ALtangent = CoefficientL * AL - AM
//            BLtangent = CoefficientL * BL - BM
//            CLtangent = CoefficientL * CL - CM
//            AHtangent = CoefficientH * AH - AM
//            BHtangent = CoefficientH * BH - BM
//            CHtangent = CoefficientH * CH - CM
//            AngleCos = (AHtangent * ALtangent + BHtangent * BLtangent + CHtangent * CLtangent) / (Math.sqrt(AHtangent * AHtangent + BHtangent * BHtangent
//
//                    + CHtangent * CHtangent) * Math.sqrt(ALtangent * ALtangent + BLtangent * BLtangent + CLtangent * CLtangent))
//            AngleCos = Math.acos(AngleCos)
//            ANormalLine = BHtangent * CLtangent - CHtangent * BLtangent
//            BNormalLine = 0 - (AHtangent * CLtangent - CHtangent * ALtangent)
//            CNormalLine = AHtangent * BLtangent - BHtangent * ALtangent
//            if (AM != 0.0)
//                OrientationValue = ANormalLine / AM
//            else if (BM != 0.0)
//                OrientationValue = BNormalLine / BM
//            else
//                OrientationValue = CNormalLine / CM
//            if (OrientationValue > 0) {
//                Sum1 += AngleCos
//                Count1++
//            } else {
//                Sum2 += AngleCos
//                Count2++
//            }
//        }
//
//        val tempSum1: Double
//        val tempSum2: Double
//        tempSum1 = Sum1 + (2.0 * Math.PI * Count2 - Sum2)
//        tempSum2 = 2.0 * Math.PI * Count1 - Sum1 + Sum2
//        if (Sum1 > Sum2) {
//            if (tempSum1 - (Count - 2) * Math.PI < 1)
//                Sum = tempSum1
//            else
//                Sum = tempSum2
//        } else {
//            if (tempSum2 - (Count - 2) * Math.PI < 1)
//                Sum = tempSum2
//            else
//                Sum = tempSum1
//        }
//        totalArea = (Sum - (Count - 2) * Math.PI) * Radius * Radius
//
//        return Math.floor(totalArea).toString() // 返回总面积
//    }
//
//    override fun onCreateFragmentAnimator(): FragmentAnimator {
//        return DefaultHorizontalAnimator()
//    }
//}