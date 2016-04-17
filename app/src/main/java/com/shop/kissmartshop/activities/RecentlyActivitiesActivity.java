package com.shop.kissmartshop.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.shop.kissmartshop.R;
import com.shop.kissmartshop.custom.CustomLinearLayoutManager;
import com.shop.kissmartshop.adapters.ProductPagerAdapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shop.kissmartshop.adapters.ProductRecentlyAdapter;
import com.shop.kissmartshop.model.ProductRecentActivitiesModel;
import com.shop.kissmartshop.utils.Constants;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;


public class RecentlyActivitiesActivity extends BaseActivity {

    private ViewPager mViewPagerProduct;
    private ProductPagerAdapter mProductPagerAdapter;
    private RecyclerView mRecyclerViewRecentlyActivities;
    private CirclePageIndicator mPageIndicatorProduct;

    private List<ProductRecentActivitiesModel> lstProductRecently;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_activities);

        //initActionBar();
        initializeData();

        mViewPagerProduct = (ViewPager)findViewById(R.id.pager_product);
        mRecyclerViewRecentlyActivities = (RecyclerView)findViewById(R.id.recycler_view_list_recently_activities);
        mPageIndicatorProduct = (CirclePageIndicator)findViewById(R.id.page_indicator_product);

        mProductPagerAdapter = new ProductPagerAdapter(this);
        mViewPagerProduct.setAdapter(mProductPagerAdapter);

        mRecyclerViewRecentlyActivities.setNestedScrollingEnabled(false);
        mRecyclerViewRecentlyActivities.setHasFixedSize(false);
        LinearLayoutManager customLayout = new LinearLayoutManager(this);
        mRecyclerViewRecentlyActivities.setLayoutManager(customLayout);

        ProductRecentlyAdapter productAdapter = new ProductRecentlyAdapter(this, lstProductRecently);
        mRecyclerViewRecentlyActivities.setAdapter(productAdapter);

        mPageIndicatorProduct.setViewPager(mViewPagerProduct);
        mPageIndicatorProduct.setFillColor(getResources().getColor(R.color.pink));
        mPageIndicatorProduct.setStrokeColor(getResources().getColor(R.color.grey));

    }

    private void initializeData(){
        lstProductRecently = new ArrayList<>();
        lstProductRecently.add(new ProductRecentActivitiesModel("Fashionable Men's Athletic Shoes With Color Matching and Letter", "$14.4", "$12.3", R.drawable.example, 50, 10, 2, Constants.PRODUCT_STATE_BUYING));
        lstProductRecently.add(new ProductRecentActivitiesModel("Fashionable Men's Athletic Shoes With Color Matching and Letter", "$12.4", "$21.2", R.drawable.example1, 40,3,2, Constants.PRODUCT_STATE_TOUCHING));
        lstProductRecently.add(new ProductRecentActivitiesModel("Fashionable Men's Athletic Shoes With Color Matching and Letter", "$12.5", "$23.1", R.drawable.example, 45, 5, 6, Constants.PRODUCT_STATE_TRYING));
        lstProductRecently.add(new ProductRecentActivitiesModel("Fashionable Men's Athletic Shoes With Color Matching and Letter", "$12.5", "$23.1", R.drawable.example, 45, 5, 6, Constants.PRODUCT_STATE_TRYING));
    }
}
