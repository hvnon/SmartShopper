package com.shop.kissmartshop.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.DirectionalViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.robototextview.util.RobotoTypefaceManager;
import com.devspark.robototextview.util.RobotoTypefaceUtils;
import com.devspark.robototextview.widget.RobotoTextView;
import com.shop.kissmartshop.R;
import com.shop.kissmartshop.adapters.ProductPagerAdapter;
import com.shop.kissmartshop.model.ProductCartTouchModel;
import com.shop.kissmartshop.model.ProductRecentActivitiesModel;
import com.shop.kissmartshop.model.SizeColorModel;
import com.shop.kissmartshop.utils.CommonUtils;
import com.shop.kissmartshop.utils.Constants;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {

    private DirectionalViewPager mViewPagerProduct;
    private CirclePageIndicator mPageIndicatorProduct;

    private LinearLayout mLinearLayoutSizeColor;
    private LinearLayout mLinearLayoutAddToCart;
    private LinearLayout mLinearLayoutProductSizeColors;
    private TextView mTextViewSelected;
    private TextView mTextViewProductPriceOriginal;
    private TextView mTextViewNumberAddToCart;
    private TextView mTextViewProductDescription;
    private TextView mTextViewProductPricePromotion;
    private TextView mTextViewButtonBuy;
    private Button mButtonTryNow;
    private FloatingActionButton mFABAddToFavourite;

    private List<SizeColorModel> mListColorSizes;
    private int numOfAddCart = 0;
    private ProductRecentActivitiesModel mProduct;

    private View mViewDisable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mProduct = (ProductRecentActivitiesModel)getIntent().getSerializableExtra(Constants.EXTRA_PRODUCT_DETAIL);

        mViewPagerProduct = (DirectionalViewPager)findViewById(R.id.view_pager_product);
        mViewPagerProduct.setOrientation(DirectionalViewPager.VERTICAL);

        final List<Integer> lstProductPhotos = new ArrayList<>();
        lstProductPhotos.add(mProduct.getPhotoId());
        lstProductPhotos.add(R.drawable.shoes11);
        lstProductPhotos.add(R.drawable.shoes12);
        ProductPagerAdapter adapter = new ProductPagerAdapter(this, lstProductPhotos);
        mViewPagerProduct.setAdapter(adapter);

        mPageIndicatorProduct = (CirclePageIndicator)findViewById(R.id.page_indicator_product);
        mPageIndicatorProduct.setViewPager(mViewPagerProduct);
        mPageIndicatorProduct.setFillColor(getResources().getColor(R.color.pink));
        mPageIndicatorProduct.setStrokeColor(getResources().getColor(R.color.grey));

        mLinearLayoutProductSizeColors = (LinearLayout)findViewById(R.id.ln_product_colors);
        mTextViewProductDescription = (TextView)findViewById(R.id.tv_product_description);
        mTextViewProductPricePromotion = (TextView)findViewById(R.id.tv_product_price_promotion);

        mLinearLayoutSizeColor = (LinearLayout)findViewById(R.id.ln_size_color);
        mTextViewProductPriceOriginal = (TextView)findViewById(R.id.tv_price_original);
        mTextViewProductPriceOriginal.setPaintFlags(mTextViewProductPriceOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        mTextViewNumberAddToCart = (TextView)findViewById(R.id.tv_number_add_to_card);
        mLinearLayoutAddToCart = (LinearLayout)findViewById(R.id.ln_add_to_cart);
        mLinearLayoutAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfAddCart += 1;
                mTextViewNumberAddToCart.setText(String.format("(%d)", numOfAddCart));
                if (mListColorSizes == null) {
                    mListColorSizes = new ArrayList<SizeColorModel>();
                }
                mListColorSizes.clear();
                getListValueOfColorSize(mLinearLayoutProductSizeColors);

                String prodId = String.valueOf(numOfAddCart);
                String prodDes = mTextViewProductDescription.getText().toString();
                String prodPriceOriginal = mTextViewProductPriceOriginal.getText().toString();
                String prodPricePromotion = mTextViewProductPricePromotion.getText().toString();

                ProductCartTouchModel product = new ProductCartTouchModel();
                product.setProductId(prodId);
                product.setDescription(prodDes);
                product.setLstSizeColors(mListColorSizes);
                product.setPriceOriginal(prodPriceOriginal);
                product.setPricePromotion(prodPricePromotion);
                product.setPhotoId(lstProductPhotos.get(0));
                CommonUtils.lstProductCart.add(product);

                onUpdateCartNumber(1);
            }
        });

        mFABAddToFavourite = (FloatingActionButton)findViewById(R.id.fab_add_favourite);
        mFABAddToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFABAddToFavourite.setImageResource(R.drawable.ic_favorite_product_detail);
                mFABAddToFavourite.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
            }
        });

        mViewDisable = findViewById(R.id.disable_view);
        mTextViewButtonBuy = (TextView)findViewById(R.id.tv_button_buy);
        mButtonTryNow = (Button)findViewById(R.id.btn_try_now);

        enableActionBottom(false);
        popularUI();
        createSizeColorOfProduct();
        createListSize();
    }

    private void popularUI()
    {
        mTextViewProductDescription.setText(mProduct.getDescription());
        mTextViewProductPricePromotion.setText(mProduct.getPricePromotion());
        mTextViewProductPriceOriginal.setText(mProduct.getPriceOriginal());
    }
    private void createSizeColorOfProduct()
    {
        for(int i=0;i<4;i++) {

            RelativeLayout rlSizeColorBg = new RelativeLayout(this);
            LinearLayout.LayoutParams paramLayoutSizeColorBg = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rlSizeColorBg.setLayoutParams(paramLayoutSizeColorBg);

            RobotoTextView tvSizeColor = new RobotoTextView(this);
            tvSizeColor.setTextColor(this.getResources().getColor(R.color.white));
            tvSizeColor.setBackgroundResource(R.drawable.circle);
            Typeface typeface = RobotoTypefaceManager.obtainTypeface(
                    this,
                    RobotoTypefaceManager.Typeface.ROBOTO_LIGHT);
            RobotoTypefaceUtils.setUp(tvSizeColor, typeface);

            switch (i) {
                case 0:
                    tvSizeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#020302")));
                    break;
                case 1:
                    tvSizeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#475784")));
                    break;
                case 2:
                    tvSizeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0099F2")));
                    break;
                case 3:
                    tvSizeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00B14D")));
                    break;

            }
            tvSizeColor.setGravity(Gravity.CENTER);

            RelativeLayout.LayoutParams paramsTextViewSizeColor = new RelativeLayout.LayoutParams(CommonUtils.dpToPx(this, 34), CommonUtils.dpToPx(this, 34));
            paramsTextViewSizeColor.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            tvSizeColor.setLayoutParams(paramsTextViewSizeColor);

            TextView tvBgSizeColor = new TextView(this);
            tvBgSizeColor.setTextColor(this.getResources().getColor(R.color.white));
            tvBgSizeColor.setGravity(Gravity.CENTER);

            tvSizeColor.setTag(tvBgSizeColor);

            RelativeLayout.LayoutParams paramsTextViewSizeColorBg = new RelativeLayout.LayoutParams(CommonUtils.dpToPx(this, 45), CommonUtils.dpToPx(this, 45));
            paramsTextViewSizeColorBg.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            tvBgSizeColor.setLayoutParams(paramsTextViewSizeColorBg);

            rlSizeColorBg.addView(tvBgSizeColor);
            rlSizeColorBg.addView(tvSizeColor);

            LinearLayout lnSizeColor = new LinearLayout(this);
            LinearLayout.LayoutParams paramSizeColor = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lnSizeColor.setOrientation(LinearLayout.VERTICAL);
            paramSizeColor.rightMargin = CommonUtils.dpToPx(this, 0);
            paramSizeColor.gravity = Gravity.CENTER;
            lnSizeColor.setLayoutParams(paramSizeColor);
            lnSizeColor.setGravity(Gravity.CENTER);

            ImageView ivSizeIcon = new ImageView(this);
            LinearLayout.LayoutParams paramSizeIcon = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ivSizeIcon.setLayoutParams(paramSizeIcon);
            ivSizeIcon.setImageResource(R.drawable.ic_cart_bottom_action);
            ivSizeIcon.setVisibility(View.GONE);

            lnSizeColor.addView(ivSizeIcon);
//            lnSizeColor.addView(tvSizeColor);
            lnSizeColor.addView(rlSizeColorBg);
            mLinearLayoutProductSizeColors.addView(lnSizeColor);

            tvSizeColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tvSelected = (TextView) v;
                    if (mTextViewSelected == tvSelected) {
                        mLinearLayoutSizeColor.setVisibility(View.GONE);
                        mTextViewSelected = null;
                    } else {
                        mLinearLayoutSizeColor.setVisibility(View.VISIBLE);
                        mTextViewSelected = tvSelected;
                        String size = mTextViewSelected.getText().toString();
                        reselectSize(mLinearLayoutSizeColor, size);
                    }
                }
            });
        }

    }

    private void createListSize()
    {
        final LinearLayout lnSizeColorFirst = (LinearLayout)findViewById(R.id.ln_size_color_first);
        for(int i = 6; i<12; i++) {

            final FrameLayout lnSize = new FrameLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lnSize.setLayoutParams(params);

            RobotoTextView tvSize = new RobotoTextView(this);
            FrameLayout.LayoutParams paramsSize = new FrameLayout.LayoutParams(CommonUtils.dpToPx(this, 30), CommonUtils.dpToPx(this, 30));
            paramsSize.gravity = Gravity.CENTER;
            tvSize.setGravity(Gravity.CENTER);
            tvSize.setLayoutParams(paramsSize);
            tvSize.setText(String.valueOf(i));
            lnSize.addView(tvSize);
            lnSizeColorFirst.addView(lnSize);
            tvSize.setTag(i);

            Typeface typeface = RobotoTypefaceManager.obtainTypeface(
                    this,
                    RobotoTypefaceManager.Typeface.ROBOTO_LIGHT);
            RobotoTypefaceUtils.setUp(tvSize, typeface);

            tvSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectSize(v);
                }
            });
        }

        final LinearLayout lnSizeColorSecond = (LinearLayout)findViewById(R.id.ln_size_color_second);
        for(int i = 12; i<18; i++) {

            final FrameLayout lnSize = new FrameLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lnSize.setLayoutParams(params);

            RobotoTextView tvSize = new RobotoTextView(this);
            FrameLayout.LayoutParams paramsSize = new FrameLayout.LayoutParams(CommonUtils.dpToPx(this, 30), CommonUtils.dpToPx(this, 30));
            paramsSize.gravity = Gravity.CENTER;
            tvSize.setGravity(Gravity.CENTER);
            tvSize.setLayoutParams(paramsSize);
            tvSize.setText(String.valueOf(i));
            lnSize.addView(tvSize);
            lnSizeColorSecond.addView(lnSize);
            tvSize.setTag(i);

            Typeface typeface = RobotoTypefaceManager.obtainTypeface(
                    this,
                    RobotoTypefaceManager.Typeface.ROBOTO_LIGHT);
            RobotoTypefaceUtils.setUp(tvSize, typeface);

            tvSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectSize(v);
                }
            });
        }
    }

//    private void getListValueOfColorSize()
//    {
//        for(int i=0; i<mLinearLayoutProductSizeColors.getChildCount();i++) {
//            ViewGroup view = (ViewGroup)mLinearLayoutProductSizeColors.getChildAt(i);
//            getValueOfColorSize(view);
//        }
//    }

    private void getListValueOfColorSize(ViewGroup viewGroup){
        for(int i=0; i<viewGroup.getChildCount();i++)
        {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                getListValueOfColorSize((ViewGroup) view);
            } else if ((view instanceof TextView)) {
                TextView tvSizeColor = ((TextView)view);
                String value = tvSizeColor.getText().toString();
                if (!value.equalsIgnoreCase("")) {
                    SizeColorModel colorSize = new SizeColorModel();
                    colorSize.setColor(String.format("#%06X", (0xFFFFFF & tvSizeColor.getBackgroundTintList().getDefaultColor())));
                    colorSize.setSize(value);
                    mListColorSizes.add(colorSize);
                    return;
                }
            }
        }
    }

    private void reselectSize(ViewGroup viewGroup, String value)
    {
        for(int i=0; i<viewGroup.getChildCount();i++)
        {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                reselectSize((ViewGroup) view, value);
            } else if ((view instanceof TextView)) {
                String val = ((TextView)view).getText().toString();
                if(value.equalsIgnoreCase(val)) {
                    view.setBackgroundResource(R.drawable.button_selector_blue);
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
            }
        }
    }

    private void selectSize(View v)
    {
        int value = Integer.parseInt(v.getTag().toString());
        v.setBackgroundResource(R.drawable.button_selector_blue);
        deselectSize(mLinearLayoutSizeColor, v);
        mTextViewSelected.setText(String.valueOf(value));
        mViewDisable.setVisibility(View.GONE);
        enableActionBottom(true);
        TextView tvBgSizeColor = (TextView)mTextViewSelected.getTag();
        tvBgSizeColor.setBackgroundResource(R.drawable.ic_bg_color);
        mLinearLayoutSizeColor.setVisibility(View.GONE);
    }

    private void deselectSize(ViewGroup viewGroup, View v)
    {
        for(int i=0; i<viewGroup.getChildCount();i++)
        {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                deselectSize((ViewGroup)view, v);
            } else if ((view instanceof TextView)&& view != v) {
                view.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }
    }

    private void enableActionBottom(boolean enable)
    {
        mLinearLayoutAddToCart.setEnabled(enable);
        mButtonTryNow.setEnabled(enable);
        mTextViewButtonBuy.setEnabled(enable);
    }
}

