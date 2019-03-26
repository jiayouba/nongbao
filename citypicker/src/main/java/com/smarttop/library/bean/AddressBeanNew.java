package com.smarttop.library.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddressBeanNew {


    private int status;
    private List<String> data;

    public static AddressBeanNew objectFromData(String str) {

        return new Gson().fromJson(str, AddressBeanNew.class);
    }

    public static AddressBeanNew objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), AddressBeanNew.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<AddressBeanNew> arrayAddressBeanNewFromData(String str) {

        Type listType = new TypeToken<ArrayList<AddressBeanNew>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<AddressBeanNew> arrayAddressBeanNewFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<AddressBeanNew>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
