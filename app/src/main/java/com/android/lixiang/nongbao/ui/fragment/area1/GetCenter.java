package com.android.lixiang.nongbao.ui.fragment.area1;

import com.facebook.common.internal.Ints;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

public class GetCenter {
    public static final double MIN_LAT = -90;
    public static final double MAX_LAT = 90;
    public static final double MIN_LNG = -180;
    public static final double MAX_LNG = 180;

    /**
     * 获取不规则多边形几何中心点
     *
     * @param mPoints
     * @return
     */
    public static LatLng getCenterPoint(List<LatLng> mPoints) {
        // 1 自己计算
        double latitude = (getMinLatitude(mPoints) + getMaxLatitude(mPoints)) / 2;
        double longitude = (getMinLongitude(mPoints) + getMaxLongitude(mPoints)) / 2;
        return new LatLng(latitude, longitude);
        // 2 使用Google map API提供的方法（推荐）
//        LatLngBounds.Builder boundsBuilder = LatLngBounds.Builder();
//        for (LatLng ll : mPoints)
//            boundsBuilder.include(ll);
//        return boundsBuilder.build().getCenter();
    }

    // 经度最小值
    public static double getMinLongitude(List<LatLng> mPoints) {
        double minLongitude = MAX_LNG;
        if (mPoints.size() > 0) {
            minLongitude = mPoints.get(0).getLongitude();
            for (LatLng latlng : mPoints) {
                // 经度最小值
                if (latlng.getLongitude() < minLongitude)
                    minLongitude = latlng.getLongitude();
            }
        }
        return minLongitude;
    }

    // 经度最大值
    public static double getMaxLongitude(List<LatLng> mPoints) {
        double maxLongitude = MIN_LNG;
        if (mPoints.size() > 0) {
            maxLongitude = mPoints.get(0).getLongitude();
            for (LatLng latlng : mPoints) {
                // 经度最大值
                if (latlng.getLongitude() > maxLongitude)
                    maxLongitude = latlng.getLongitude();
            }
        }
        return maxLongitude;
    }

    // 纬度最小值
    public static double getMinLatitude(List<LatLng> mPoints) {
        double minLatitude = MAX_LAT;
        if (mPoints.size() > 0) {
            minLatitude = mPoints.get(0).getLatitude();
            for (LatLng latlng : mPoints) {
                // 纬度最小值
                if (latlng.getLatitude() < minLatitude)
                    minLatitude = latlng.getLatitude();
            }
        }
        return minLatitude;
    }

    // 纬度最大值
    public static double getMaxLatitude(List<LatLng> mPoints) {
        double maxLatitude = MIN_LAT;
        if (mPoints.size() > 0) {
            maxLatitude = mPoints.get(0).getLatitude();
            for (LatLng latlng : mPoints) {
                // 纬度最大值
                if (latlng.getLatitude() > maxLatitude)
                    maxLatitude = latlng.getLatitude();
            }
        }
        return maxLatitude;
    }


}
