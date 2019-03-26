package com.smarttop.library.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.smarttop.library.R;
import com.smarttop.library.bean.AddressBeanNew;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Cun;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.db.ApiService;
import com.smarttop.library.db.NetUtils;
import com.smarttop.library.db.manager.AddressDictManager;
import com.smarttop.library.utils.Lists;
import com.smarttop.library.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by smartTop on 2016/10/19.
 */

public class AddressSelector implements AdapterView.OnItemClickListener {
    private static final int INDEX_TAB_PROVINCE = 0;//省份标志
    private static final int INDEX_TAB_CITY = 1;//城市标志
    private static final int INDEX_TAB_COUNTY = 2;//乡镇标志
    private static final int INDEX_TAB_STREET = 3;//街道标志
    private static final int INDEX_TAB_CUN = 4;//街道标志

    private int tabIndex = INDEX_TAB_PROVINCE; //默认是省份

    private static final int INDEX_INVALID = -1;
    private int provinceIndex = INDEX_INVALID; //省份的下标
    private int cityIndex = INDEX_INVALID;//城市的下标
    private int countyIndex = INDEX_INVALID;//乡镇的下标
    private int streetIndex = INDEX_INVALID;//街道的下标
    private int cunIndex = INDEX_INVALID;//街道的下标


    private Context context;
    private final LayoutInflater inflater;
    private View view;

    private View indicator;

    private LinearLayout layout_tab;
    private TextView textViewProvince;
    private TextView textViewCity;
    private TextView textViewCounty;
    private TextView textViewStreet;
    private TextView textViewCun;

    private ProgressBar progressBar;

    public ListView listView;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private CountyAdapter countyAdapter;
    private StreetAdapter streetAdapter;
    private CunAdapter cunAdapter;

    private List<Province> provinces;
    private List<City> cities;
    private List<County> counties;
    private List<Street> streets;
    private List<Cun> cuns;

    private OnAddressSelectedListener listener;
    private OnDialogCloseListener dialogCloseListener;
    private onSelectorAreaPositionListener selectorAreaPositionListener;

    private static final int WHAT_PROVINCES_PROVIDED = 0;
    private static final int WHAT_CITIES_PROVIDED = 1;
    private static final int WHAT_COUNTIES_PROVIDED = 2;
    private static final int WHAT_STREETS_PROVIDED = 3;
    private static final int WHAT_CUNS_PROVIDED = 4;

    private AddressDictManager addressDictManager;
    private ImageView iv_colse;
    private int selectedColor;
    private int unSelectedColor;
    public int provincePostion;
    public int cityPosition;
    public int countyPosition;
    public int streetPosition;
    public int cunPosition;

//    private List<Province> mProvinceList = new ArrayList<>();
//    private List<County> mCountyList = new ArrayList<>();
//    private List<Street> mStreetList = new ArrayList<>();
//    private List<Cun> mCunList = new ArrayList<>();

    private String mCityName = "";
    private String mCountyName = "";


    @SuppressWarnings("unchecked")
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_PROVINCES_PROVIDED: //更新省份列表
                    provinces = (List<Province>) msg.obj;
                    Logger.d("PROVINCES" + provinces);

                    provinceAdapter.notifyDataSetChanged();
                    listView.setAdapter(provinceAdapter);
                    break;

                case WHAT_CITIES_PROVIDED: //更新城市列表
                    cities = (List<City>) msg.obj;
                    Logger.d("CITIES" + cities);
                    cityAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(cities)) {
                        // 以次级内容更新列表
                        listView.setAdapter(cityAdapter);
                        // 更新索引为次级
                        tabIndex = INDEX_TAB_CITY;
                    } else {
                        // 次级无内容，回调
                        callbackInternal();
                    }

                    break;

                case WHAT_COUNTIES_PROVIDED://更新乡镇列表
                    counties = (List<County>) msg.obj;
                    countyAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(counties)) {
                        listView.setAdapter(countyAdapter);
                        tabIndex = INDEX_TAB_COUNTY;
                    } else {
                        callbackInternal();
                    }

                    break;

                case WHAT_STREETS_PROVIDED://更新街道列表
                    streets = (List<Street>) msg.obj;
                    streetAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(streets)) {
                        listView.setAdapter(streetAdapter);
                        tabIndex = INDEX_TAB_STREET;
                    } else {
                        callbackInternal();
                    }

                    break;

                case WHAT_CUNS_PROVIDED://更新街道列表
                    cuns = (List<Cun>) msg.obj;
                    cunAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(cuns)) {
                        listView.setAdapter(cunAdapter);
                        tabIndex = INDEX_TAB_CUN;
                    } else {
                        callbackInternal();
                    }

                    break;
            }

            updateTabsVisibility();
            updateProgressVisibility();
            updateIndicator();

            return true;
        }
    });


    public AddressSelector(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        addressDictManager = new AddressDictManager(context);
        initViews();
        initAdapters();
        Logger.d("LLLLLLLLL");
        retrieveProvinces();
    }

    /**
     * 得到数据库管理者
     *
     * @return
     */
    public AddressDictManager getAddressDictManager() {
        return addressDictManager;
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        view = inflater.inflate(R.layout.address_selector, null);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);//进度条
        this.listView = (ListView) view.findViewById(R.id.listView);//listview
        this.indicator = view.findViewById(R.id.indicator); //指示器
        this.layout_tab = (LinearLayout) view.findViewById(R.id.layout_tab);
        this.textViewProvince = (TextView) view.findViewById(R.id.textViewProvince);//省份
        this.textViewCity = (TextView) view.findViewById(R.id.textViewCity);//城市
        this.textViewCounty = (TextView) view.findViewById(R.id.textViewCounty);//区 乡镇
        this.textViewStreet = (TextView) view.findViewById(R.id.textViewStreet);//街道
        this.textViewCun = (TextView) view.findViewById(R.id.textViewCun);//街道
        this.textViewProvince.setOnClickListener(new OnProvinceTabClickListener());
        this.textViewCity.setOnClickListener(new OnCityTabClickListener());
        this.textViewCounty.setOnClickListener(new onCountyTabClickListener());
        this.textViewStreet.setOnClickListener(new OnStreetTabClickListener());
        this.textViewCun.setOnClickListener(new OnCunTabClickListener());

        this.listView.setOnItemClickListener(this);
        updateIndicator();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void conflict(final ScrollView scrollView) {
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }

    /**
     * 设置字体选中的颜色
     */
    public void setTextSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * 设置字体没有选中的颜色
     */
    public void setTextUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    /**
     * 设置字体的大小
     */
    public void setTextSize(float dp) {
        textViewProvince.setTextSize(dp);
        textViewCity.setTextSize(dp);
        textViewCounty.setTextSize(dp);
        textViewStreet.setTextSize(dp);
    }

    /**
     * 设置字体的背景
     */
    public void setBackgroundColor(int colorId) {
        layout_tab.setBackgroundColor(context.getResources().getColor(colorId));
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(int colorId) {
        indicator.setBackgroundColor(context.getResources().getColor(colorId));
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(String color) {
        indicator.setBackgroundColor(Color.parseColor(color));
    }

    /**
     * 初始化adapter
     */
    private void initAdapters() {
        provinceAdapter = new ProvinceAdapter();
        cityAdapter = new CityAdapter();
        countyAdapter = new CountyAdapter();
        streetAdapter = new StreetAdapter();
        cunAdapter = new CunAdapter();
    }

    /**
     * 更新tab 指示器
     */
    private void updateIndicator() {
        view.post(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case INDEX_TAB_PROVINCE: //省份
                        buildIndicatorAnimatorTowards(textViewProvince).start();
                        break;
                    case INDEX_TAB_CITY: //城市
                        buildIndicatorAnimatorTowards(textViewCity).start();
                        break;
                    case INDEX_TAB_COUNTY: //乡镇
                        buildIndicatorAnimatorTowards(textViewCounty).start();
                        break;
                    case INDEX_TAB_STREET: //街道
                        buildIndicatorAnimatorTowards(textViewStreet).start();
                        break;
                    case INDEX_TAB_CUN: //街道
                        buildIndicatorAnimatorTowards(textViewCun).start();
                        break;
                }
            }
        });
    }

    /**
     * tab 来回切换的动画
     *
     * @param tab
     * @return
     */
    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                indicator.setLayoutParams(params);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);

        return set;
    }

    /**
     * 更新tab显示
     */
    private void updateTabsVisibility() {
        textViewProvince.setVisibility(Lists.notEmpty(provinces) ? View.VISIBLE : View.GONE);
        textViewCity.setVisibility(Lists.notEmpty(cities) ? View.VISIBLE : View.GONE);
        textViewCounty.setVisibility(Lists.notEmpty(counties) ? View.VISIBLE : View.GONE);
        textViewStreet.setVisibility(Lists.notEmpty(streets) ? View.VISIBLE : View.GONE);
        textViewCun.setVisibility(Lists.notEmpty(cuns) ? View.VISIBLE : View.GONE);

        //按钮能不能点击 false 不能点击 true 能点击
        textViewProvince.setEnabled(tabIndex != INDEX_TAB_PROVINCE);
        textViewCity.setEnabled(tabIndex != INDEX_TAB_CITY);
        textViewCounty.setEnabled(tabIndex != INDEX_TAB_COUNTY);
        textViewStreet.setEnabled(tabIndex != INDEX_TAB_STREET);
        textViewCun.setEnabled(tabIndex != INDEX_TAB_CUN);

        if (selectedColor != 0 && unSelectedColor != 0) {
            updateTabTextColor();
        }
    }

    /**
     * 更新字体的颜色
     */
    private void updateTabTextColor() {
        if (tabIndex != INDEX_TAB_PROVINCE) {
            textViewProvince.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewProvince.setTextColor(context.getResources().getColor(unSelectedColor));
        }
        if (tabIndex != INDEX_TAB_CITY) {
            textViewCity.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewCity.setTextColor(context.getResources().getColor(unSelectedColor));
        }
        if (tabIndex != INDEX_TAB_COUNTY) {
            textViewCounty.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewCounty.setTextColor(context.getResources().getColor(unSelectedColor));
        }
        if (tabIndex != INDEX_TAB_STREET) {
            textViewStreet.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewStreet.setTextColor(context.getResources().getColor(unSelectedColor));
        }
        if (tabIndex != INDEX_TAB_CUN) {
            textViewCun.setTextColor(context.getResources().getColor(selectedColor));
        } else {
            textViewCun.setTextColor(context.getResources().getColor(unSelectedColor));
        }

    }

    /**
     * 点击省份的监听
     */
    class OnProvinceTabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_PROVINCE;
            listView.setAdapter(provinceAdapter);

            if (provinceIndex != INDEX_INVALID) {
                listView.setSelection(provinceIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击城市的监听
     */
    class OnCityTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_CITY;
            listView.setAdapter(cityAdapter);

//            cityAdapter.setSelectedItem(cityIndex);
            cityAdapter.notifyDataSetChanged();
            if (cityIndex != INDEX_INVALID) {
                listView.setSelection(cityIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击区 乡镇的监听
     */
    class onCountyTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_COUNTY;
            listView.setAdapter(countyAdapter);
//            countyAdapter.setSelectedItem(countyIndex);
            countyAdapter.notifyDataSetChanged();

            if (countyIndex != INDEX_INVALID) {
                listView.setSelection(countyIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击街道的监听
     */
    class OnStreetTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_STREET;
            listView.setAdapter(streetAdapter);
//            streetAdapter.setSelectedItem(streetIndex);
            streetAdapter.notifyDataSetChanged();

            if (streetIndex != INDEX_INVALID) {
                listView.setSelection(streetIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    class OnCunTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_CUN;
            listView.setAdapter(cunAdapter);
//            cunAdapter.setSelectedItem(cunIndex);
            cunAdapter.notifyDataSetChanged();

            if (cunIndex != INDEX_INVALID) {
                listView.setSelection(cunIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击右边关闭dialog监听
     */
    class onCloseClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (dialogCloseListener != null) {
                dialogCloseListener.dialogclose();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (tabIndex) {
            case INDEX_TAB_PROVINCE: //省份
                Province province = provinceAdapter.getItem(position);
                provincePostion = position;
                // 更新当前级别及子级标签文本
                textViewProvince.setText(province.name);
                textViewCity.setText("选择市");
                textViewCounty.setText("选择县");
                textViewStreet.setText("选择乡镇");
                textViewCun.setText("选择村");
                //根据省份的id,从数据库中查询城市列表
                retrieveCitiesWith();
                // 清空子级数据
                cities = null;
                counties = null;
                streets = null;
                cuns = null;
                cityAdapter.notifyDataSetChanged();
                countyAdapter.notifyDataSetChanged();
                streetAdapter.notifyDataSetChanged();
                cunAdapter.notifyDataSetChanged();
                // 更新已选中项
                this.provinceIndex = position;
                this.cityIndex = INDEX_INVALID;
                this.countyIndex = INDEX_INVALID;
                this.streetIndex = INDEX_INVALID;
                this.cunIndex = INDEX_INVALID;
                // 更新选中效果
                provinceAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_CITY://城市
                City city = cityAdapter.getItem(position);
                cityPosition = position;
                textViewCity.setText(city.name);
                textViewCounty.setText("选择县");
                textViewStreet.setText("选择乡镇");
                textViewCun.setText("选择村");

                //根据城市的id,从数据库中查询城市列表
                mCityName = city.name;
                retrieveCountiesWith(city.name);
                // 清空子级数据
                counties = null;
                streets = null;
                cuns = null;
                countyAdapter.setSelectedItem(-1);
                streetAdapter.setSelectedItem(-1);
                cunAdapter.setSelectedItem(-1);

                countyAdapter.notifyDataSetChanged();
                streetAdapter.notifyDataSetChanged();
                cunAdapter.notifyDataSetChanged();

                // 更新已选中项
                this.cityIndex = position;
                this.countyIndex = INDEX_INVALID;
                this.streetIndex = INDEX_INVALID;
                this.cunIndex = INDEX_INVALID;

                // 更新选中效果
                cityAdapter.setSelectedItem(position);
                cityAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_COUNTY:
                County county = countyAdapter.getItem(position);
                countyPosition = position;
                textViewCounty.setText(county.name);
                textViewStreet.setText("选择乡镇");
                textViewCun.setText("选择村");

                mCountyName = county.name;
                retrieveStreetsWith(mCityName, county.name);
                streets = null;
                cuns = null;
                streetAdapter.setSelectedItem(-1);
                cunAdapter.setSelectedItem(-1);
                streetAdapter.notifyDataSetChanged();
                cunAdapter.notifyDataSetChanged();

                this.countyIndex = position;
                this.streetIndex = INDEX_INVALID;
                this.cunIndex = INDEX_INVALID;
                countyAdapter.setSelectedItem(position);
                countyAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_STREET:
                Street street = streetAdapter.getItem(position);
                streetPosition = position;
                textViewStreet.setText(street.name);
                textViewCun.setText("选择村");
                streetAdapter.setSelectedItem(cityIndex);

                retrieveCunsWith(mCityName, mCountyName, street.name);
                cuns = null;
                this.streetIndex = position;
                streetAdapter.notifyDataSetChanged();
                cunAdapter.setSelectedItem(-1);
                cunAdapter.notifyDataSetChanged();
                this.cunIndex = INDEX_INVALID;

//                callbackInternal();
//                if (selectorAreaPositionListener != null) {
//                    selectorAreaPositionListener.selectorAreaPosition(provincePostion, cityPosition, countyPosition, streetPosition);
//                }

                streetAdapter.setSelectedItem(position);
                streetAdapter.notifyDataSetChanged();
                break;

            case INDEX_TAB_CUN:
                Cun cun = cunAdapter.getItem(position);
                cunPosition = position;
                textViewCun.setText(cun.name);
                cunAdapter.setSelectedItem(cityIndex);

                this.cunIndex = position;

                cunAdapter.notifyDataSetChanged();

                callbackInternal();
                if (selectorAreaPositionListener != null) {
                    selectorAreaPositionListener.selectorAreaPosition(provincePostion, cityPosition, countyPosition, streetPosition, cunPosition);
                }
                cunAdapter.setSelectedItem(position);
                cunAdapter.notifyDataSetChanged();
                break;
        }
    }


    /**
     * 查询省份列表
     */
    private void retrieveProvinces() {
        progressBar.setVisibility(View.VISIBLE);
//        Retrofit retrofit = NetUtils.getRetrofit();
//        ApiService apiService = retrofit.create(ApiService.class);
//        Call<AddressBeanNew> call = apiService.selectAddress1("长春市");
//        call.enqueue(new Callback<AddressBeanNew>() {
//            @Override
//            public void onResponse(Call<AddressBeanNew> call, Response<AddressBeanNew> response) {
//                if (response.body() != null) {
//                    addressBeanNew = response.body();
//                    for (int i = 0; i < addressBeanNew.getData().size(); i++){
//                        Province province = new Province();
//                        province.name = addressBeanNew.getData().get(i);
//                        list.add(province);
//                    }
//                    handler.sendMessage(Message.obtain(handler, WHAT_PROVINCES_PROVIDED, list));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddressBeanNew> call, Throwable t) {
//
//            }
//        });
        List<Province> mProvinceList = new ArrayList<>();
        provinces = new ArrayList<Province>();
        Province province = new Province();
        province.name = "吉林省";
        mProvinceList.add(province);
        handler.sendMessage(Message.obtain(handler, WHAT_PROVINCES_PROVIDED, mProvinceList));

    }

    /**
     * 根据省份id查询城市列表
     *
     * @param provinceId 省份id
     */
    private void retrieveCitiesWith(int provinceId) {
        progressBar.setVisibility(View.VISIBLE);
        List<City> cityList = addressDictManager.getCityList(provinceId);
        handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, cityList));
    }

    private void retrieveCitiesWith() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = NetUtils.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddressBeanNew> call = apiService.selectAddress();
        final List<City> mCityList = new ArrayList<>();
        call.enqueue(new Callback<AddressBeanNew>() {
            @Override
            public void onResponse(Call<AddressBeanNew> call, Response<AddressBeanNew> response) {
                if (response.body() != null) {
                    AddressBeanNew addressBeanNew = response.body();
                    for (int i = 0; i < addressBeanNew.getData().size(); i++) {
                        City city = new City();
                        city.name = addressBeanNew.getData().get(i);
                        mCityList.add(city);
                    }
                    handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, mCityList));
                }
            }

            @Override
            public void onFailure(Call<AddressBeanNew> call, Throwable t) {

            }
        });
//        List<City> cityList = addressDictManager.getCityList(provinceId);
//        handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, cityList));
    }

    /**
     * 根据城市id查询乡镇列表
     *
     * @param cityId 城市id
     */
    private void retrieveCountiesWith(int cityId) {
        progressBar.setVisibility(View.VISIBLE);
        List<County> countyList = addressDictManager.getCountyList(cityId);
        handler.sendMessage(Message.obtain(handler, WHAT_COUNTIES_PROVIDED, countyList));
    }

    private void retrieveCountiesWith(String cityName) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = NetUtils.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddressBeanNew> call = apiService.selectAddress1(cityName);
        final List<County> mCountyList = new ArrayList<>();
        call.enqueue(new Callback<AddressBeanNew>() {
            @Override
            public void onResponse(Call<AddressBeanNew> call, Response<AddressBeanNew> response) {
                if (response.body() != null) {
                    AddressBeanNew addressBeanNew = response.body();
                    for (int i = 0; i < addressBeanNew.getData().size(); i++) {
                        County county = new County();
                        county.name = addressBeanNew.getData().get(i);
                        mCountyList.add(county);
                    }
                    handler.sendMessage(Message.obtain(handler, WHAT_COUNTIES_PROVIDED, mCountyList));
                }
            }

            @Override
            public void onFailure(Call<AddressBeanNew> call, Throwable t) {

            }
        });
//        List<City> cityList = addressDictManager.getCityList(provinceId);
//        handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, cityList));
    }

    /**
     * 根据乡镇id查询乡镇列表
     *
     * @param countyId 乡镇id
     */
    private void retrieveStreetsWith(int countyId) {
        progressBar.setVisibility(View.VISIBLE);
        List<Street> streetList = addressDictManager.getStreetList(countyId);
        handler.sendMessage(Message.obtain(handler, WHAT_STREETS_PROVIDED, streetList));
    }


    private void retrieveStreetsWith(String cityName, String countyName) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = NetUtils.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddressBeanNew> call = apiService.selectAddress2(cityName, countyName);
        final List<Street> mStreetList = new ArrayList<>();
        call.enqueue(new Callback<AddressBeanNew>() {
            @Override
            public void onResponse(Call<AddressBeanNew> call, Response<AddressBeanNew> response) {
                if (response.body() != null) {
                    AddressBeanNew addressBeanNew = response.body();
                    for (int i = 0; i < addressBeanNew.getData().size(); i++) {
                        Street street = new Street();
                        street.name = addressBeanNew.getData().get(i);
                        mStreetList.add(street);
                    }
                    handler.sendMessage(Message.obtain(handler, WHAT_STREETS_PROVIDED, mStreetList));
                }
            }

            @Override
            public void onFailure(Call<AddressBeanNew> call, Throwable t) {

            }
        });
//        List<City> cityList = addressDictManager.getCityList(provinceId);
//        handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, cityList));
    }

    private void retrieveCunsWith(String cityName, String countyName, String streetName) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = NetUtils.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddressBeanNew> call = apiService.selectAddress3(cityName, countyName, streetName);
        final List<Cun> mCunList = new ArrayList<>();
        call.enqueue(new Callback<AddressBeanNew>() {
            @Override
            public void onResponse(Call<AddressBeanNew> call, Response<AddressBeanNew> response) {
                if (response.body() != null) {
                    AddressBeanNew addressBeanNew = response.body();
                    for (int i = 0; i < addressBeanNew.getData().size(); i++) {
                        Cun cun = new Cun();
                        cun.name = addressBeanNew.getData().get(i);
                        mCunList.add(cun);
                    }
                    handler.sendMessage(Message.obtain(handler, WHAT_CUNS_PROVIDED, mCunList));
                }
            }

            @Override
            public void onFailure(Call<AddressBeanNew> call, Throwable t) {

            }
        });
//        List<City> cityList = addressDictManager.getCityList(provinceId);
//        handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, cityList));
    }

    /**
     * 省份 城市 乡镇 街道 都选中完 后的回调
     */
    private void callbackInternal() {
        if (listener != null) {
            Province province = provinces == null || provinceIndex == INDEX_INVALID ? null : provinces.get(provinceIndex);
            City city = cities == null || cityIndex == INDEX_INVALID ? null : cities.get(cityIndex);
            County county = counties == null || countyIndex == INDEX_INVALID ? null : counties.get(countyIndex);
            Street street = streets == null || streetIndex == INDEX_INVALID ? null : streets.get(streetIndex);
            Cun cun = cuns == null || cunIndex == INDEX_INVALID ? null : cuns.get(cunIndex);

            listener.onAddressSelected(province, city, county, street, cun);
        }
    }

    /**
     * 更新进度条
     */
    private void updateProgressVisibility() {
        ListAdapter adapter = listView.getAdapter();
        int itemCount = adapter.getCount();
        progressBar.setVisibility(itemCount > 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 获得view
     *
     * @return
     */
    public View getView() {
        return view;
    }

    /**
     * 省份的adapter
     */
    class ProvinceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return provinces == null ? 0 : provinces.size();
        }

        @Override
        public Province getItem(int position) {
            return provinces.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            Province item = getItem(position);
            holder.textView.setText(item.name);

            boolean checked = provinceIndex != INDEX_INVALID && provinces.get(provinceIndex).id == item.id;
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 城市的adaoter
     */
    class CityAdapter extends BaseAdapter {
        private int selectedItem = -1;

        @Override
        public int getCount() {
            return cities == null ? 0 : cities.size();
        }

        @Override
        public City getItem(int position) {
            return cities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        public void setSelectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            City item = getItem(position);
            holder.textView.setText(item.name);

//            boolean checked = cityIndex != INDEX_INVALID && cities.get(cityIndex).id == item.id;
            boolean checked = false;
            if (position == selectedItem) {
//                mCheckIV.setVisibility(View.VISIBLE);
                checked = true;
            }

            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 乡镇的adapter
     */
    class CountyAdapter extends BaseAdapter {
        private int selectedItem = -1;

        @Override
        public int getCount() {
            return counties == null ? 0 : counties.size();
        }

        @Override
        public County getItem(int position) {
            return counties.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        public void setSelectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            County item = getItem(position);
            holder.textView.setText(item.name);

//            boolean checked = countyIndex != INDEX_INVALID && counties.get(countyIndex).id == item.id;
            boolean checked = false;
            if (position == selectedItem) {
//                mCheckIV.setVisibility(View.VISIBLE);
                checked = true;
            }
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 街道的adapter
     */
    class StreetAdapter extends BaseAdapter {
        private int selectedItem = -1;

        @Override
        public int getCount() {
            return streets == null ? 0 : streets.size();
        }

        @Override
        public Street getItem(int position) {
            return streets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        public void setSelectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            Street item = getItem(position);
            holder.textView.setText(item.name);

//            boolean checked = streetIndex != INDEX_INVALID && streets.get(streetIndex).id == item.id;

            boolean checked = false;
            if (position == selectedItem) {
//                mCheckIV.setVisibility(View.VISIBLE);
                checked = true;
            }

            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }


    class CunAdapter extends BaseAdapter {
        private int selectedItem = -1;

        @Override
        public int getCount() {
            return cuns == null ? 0 : cuns.size();
        }

        @Override
        public Cun getItem(int position) {
            return cuns.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }


        public void setSelectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            Cun item = getItem(position);
            holder.textView.setText(item.name);

//            boolean checked = cunIndex != INDEX_INVALID && cuns.get(cunIndex).id == item.id;

            boolean checked = false;
            if (position == selectedItem) {
//                mCheckIV.setVisibility(View.VISIBLE);
                checked = true;
            }

            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }


    public OnAddressSelectedListener getOnAddressSelectedListener() {
        return listener;
    }

    /**
     * 设置地址监听
     *
     * @param listener
     */
    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnDialogCloseListener {
        void dialogclose();
    }

    /**
     * 设置close监听
     */
    public void setOnDialogCloseListener(OnDialogCloseListener listener) {
        this.dialogCloseListener = listener;
    }

    public interface onSelectorAreaPositionListener {
        void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition, int cunPosition);
    }

    public void setOnSelectorAreaPositionListener(onSelectorAreaPositionListener listener) {
        this.selectorAreaPositionListener = listener;
    }

    /**
     * 根据code 来显示选择过的地区
     */
    public void getSelectedArea(String provinceCode, int provincePostion, String cityCode, int cityPosition, String countyCode, int countyPosition, String streetCode, int streetPosition, String cunCode, int cunPosition) {
        LogUtil.d("数据", "getSelectedArea省份id=" + provinceCode);
        LogUtil.d("数据", "getSelectedArea城市id=" + cityCode);
        LogUtil.d("数据", "getSelectedArea乡镇id=" + countyCode);
        LogUtil.d("数据", "getSelectedArea 街道id=" + streetCode);
        if (!TextUtils.isEmpty(provinceCode)) {
//            Province province = addressDictManager.getProvinceBean(provinceCode);
//            textViewProvince.setText(province.name);
//            LogUtil.d("数据", "省份=" + province);
            // 更新当前级别及子级标签文本
            //根据省份的id,从数据库中查询城市列表
            retrieveCitiesWith();

            // 清空子级数据
            cities = null;
            counties = null;
            streets = null;
            cuns = null;
            cityAdapter.notifyDataSetChanged();
            countyAdapter.notifyDataSetChanged();
            streetAdapter.notifyDataSetChanged();
            cunAdapter.notifyDataSetChanged();
            // 更新已选中项
            this.provinceIndex = provincePostion;
            this.cityIndex = INDEX_INVALID;
            this.countyIndex = INDEX_INVALID;
            this.streetIndex = INDEX_INVALID;
            this.cunIndex = INDEX_INVALID;
            // 更新选中效果
            provinceAdapter.notifyDataSetChanged();
        }
        if (!TextUtils.isEmpty(cityCode)) {
//            City city = addressDictManager.getCityBean(cityCode);
//            textViewCity.setText(city.name);
//            LogUtil.d("数据", "城市=" + city.name);
            //根据城市的id,从数据库中查询城市列表
            retrieveCountiesWith(mCityName);
            // 清空子级数据
            counties = null;
            streets = null;
            cuns = null;
            countyAdapter.notifyDataSetChanged();
            streetAdapter.notifyDataSetChanged();
            cunAdapter.notifyDataSetChanged();

            // 更新已选中项
            this.cityIndex = cityPosition;
            this.countyIndex = INDEX_INVALID;
            this.streetIndex = INDEX_INVALID;
            this.cunIndex = INDEX_INVALID;

            // 更新选中效果
            cityAdapter.notifyDataSetChanged();

        }
        if (!TextUtils.isEmpty(countyCode)) {
//            County county = addressDictManager.getCountyBean(countyCode);
//            textViewCounty.setText(county.name);
//            LogUtil.d("数据", "乡镇=" + county.name);
            retrieveStreetsWith(mCityName, mCountyName);

            streets = null;
            cuns = null;

            streetAdapter.notifyDataSetChanged();
            cunAdapter.notifyDataSetChanged();

            this.countyIndex = countyPosition;
            this.streetIndex = INDEX_INVALID;
            this.cunIndex = INDEX_INVALID;

            countyAdapter.notifyDataSetChanged();
        }
        if (!TextUtils.isEmpty(streetCode)) {
            Street street = addressDictManager.getStreetBean(streetCode);
            textViewStreet.setText(street.name);
            LogUtil.d("数据", "街道=" + street.name);
            retrieveCunsWith(mCityName, mCountyName, street.name);

            cuns = null;

            cunAdapter.notifyDataSetChanged();

            this.streetIndex = streetPosition;
            this.cunIndex = INDEX_INVALID;

            streetAdapter.notifyDataSetChanged();


        }
        if (!TextUtils.isEmpty(cunCode)) {
            Cun cun = addressDictManager.getCunBean(cunCode);
            textViewCun.setText(cun.name);
            LogUtil.d("数据", "街道=" + cun.name);

            this.cunIndex = INDEX_INVALID;

            cunAdapter.notifyDataSetChanged();
        }
        callbackInternal();
    }

}
