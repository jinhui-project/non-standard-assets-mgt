package com.jinhui.model.review;

/**
 * Created by jinhui on 2017/7/3.
 */
public interface Feature {
    String code();
    String featureName();
    Review.State reviewState();
    Review.State nextState();
}
