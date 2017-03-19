package com.example.suythea.hrms.Interfaces;

/**
 * Created by lolzzlolzz on 3/6/17.
 */

public interface MySupporter_Interface {
    void onHttpFinished(String response);
    void onHttpError(String message);
    void onVolleyFinished(String response);
    void onVolleyError(String message);
}
