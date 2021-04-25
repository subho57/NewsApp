package com.github.subho57.newsapp.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.subho57.newsapp.R;
import com.github.subho57.newsapp.utils.BookView;
import com.github.subho57.newsapp.utils.SunMoonView;
import com.github.subho57.newsapp.utils.ThirdScreenView;

import java.util.HashMap;
import java.util.Objects;

@SuppressLint("StaticFieldLeak")
public class InfoActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    public static Context mContext;
    private static ViewPager mPager;
    private static ImageView mCenterBox;
    private static ImageView mCamcordImage;
    private static ImageView mClockImage;
    private static ImageView mGraphImage;
    private static ImageView mAudioImage;
    private static ImageView mQuoteImage;
    private static ImageView mMapImage;
    private static ImageView mWordPressImage;
    private static AnimatorSet mAnimatorSet;
    private static boolean mSecondPageSelected;
    private static HashMap<ImageView, Float> mOriginalXValuesMap;
    //Second screen
    private static SunMoonView mAnimationView;
    private static boolean mViewPagerScrollingLeft;
    private static BookView mBookView;
    // Third screen
    private static boolean mShouldSpheresRotate = true;
    private static ThirdScreenView mRoundView;
    private LinearLayout mIndicatorLayout;
    private TextView[] mIndicatorView;
    private int mSelectedPosition = -1;
    private float mPreviousPositionOffset;
    private int mPreviousPosition;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void moveTheSpheres(float position, int pageWidth) {


        float camcordPos = (float) ((1 - position) * 0.15 * pageWidth);
        if (camcordPos > (-1 * Objects.requireNonNull(mOriginalXValuesMap.get(mCamcordImage)))) {
            mCamcordImage.setTranslationX(camcordPos);
        }


        float clockPos = (float) ((1 - position) * 0.50 * pageWidth);
        if (clockPos > (-1 * Objects.requireNonNull(mOriginalXValuesMap.get(mClockImage)))) {
            mClockImage.setTranslationX(clockPos);
        }

        float graphPos = (float) ((1 - position) * 0.50 * pageWidth);
        if (graphPos > (-1 * Objects.requireNonNull(mOriginalXValuesMap.get(mGraphImage)))) {
            mGraphImage.setTranslationX(graphPos);
        }

        float audioPos = (float) ((1 - position) * 0.30 * pageWidth);
        if (audioPos > (-1 * Objects.requireNonNull(mOriginalXValuesMap.get(mAudioImage)))) {
            mAudioImage.setTranslationX(audioPos);
        }


        float quotePos = (float) (-(1 - position) * 0.37 * pageWidth);
        if (quotePos > (-1 * Objects.requireNonNull(mOriginalXValuesMap.get(mQuoteImage)))) {
            mQuoteImage.setTranslationX(quotePos);
        }

        float mapPos = (float) (-(1 - position) * 1.1 * pageWidth);
        if (mapPos > (-1 * Objects.requireNonNull(mOriginalXValuesMap.get(mMapImage)))) {
            mMapImage.setTranslationX(mapPos);
        }

        float wordpressPos = (float) (-(1 - position) * 0.37 * pageWidth);
        if (wordpressPos > (-1 * Objects.requireNonNull(mOriginalXValuesMap.get(mWordPressImage)))) {
            mWordPressImage.setTranslationX(wordpressPos);
        }


    }

    private static void animateSecondScreen(float position, int direction) {

        if (direction == 0) {
            mAnimationView.animateSecondScreenClock(position);
        } else {
            mAnimationView.animateSecondScreenAntiClock(position);
        }
    }

    private static void initFirstScreenViews(View rootView, final Bundle savedInstanceState) {

        mCenterBox = rootView.findViewById(R.id.center_box);
        mCamcordImage = rootView.findViewById(R.id.imageView);
        mClockImage = rootView.findViewById(R.id.imageView6);
        mGraphImage = rootView.findViewById(R.id.imageView3);
        mAudioImage = rootView.findViewById(R.id.imageView4);
        mQuoteImage = rootView.findViewById(R.id.imageView5);
        mMapImage = rootView.findViewById(R.id.imageView2);
        mWordPressImage = rootView.findViewById(R.id.imageView7);

        initializeAlpha();

        rootView.post(new Runnable() {
            @Override
            public void run() {

                getOriginalXValues(savedInstanceState);

            }
        });

        if (savedInstanceState == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    doFadeAnimation();
                }
            }, 700);

        }

    }

    private static void getOriginalXValues(Bundle savedInstanceState) {

        mOriginalXValuesMap.put(mCenterBox, mCenterBox.getX());
        mOriginalXValuesMap.put(mCamcordImage, mCamcordImage.getX());
        mOriginalXValuesMap.put(mClockImage, mClockImage.getX());
        mOriginalXValuesMap.put(mGraphImage, mGraphImage.getX());
        mOriginalXValuesMap.put(mAudioImage, mAudioImage.getX());
        mOriginalXValuesMap.put(mQuoteImage, mQuoteImage.getX());
        mOriginalXValuesMap.put(mMapImage, mMapImage.getX());
        mOriginalXValuesMap.put(mWordPressImage, mWordPressImage.getX());

        if (savedInstanceState == null) {
            mPager.setPageTransformer(true, new CustomTransformer());
        }


    }

    private static void initializeAlpha() {

        mCamcordImage.setAlpha(0f);
        mClockImage.setAlpha(0f);
        mGraphImage.setAlpha(0f);
        mAudioImage.setAlpha(0f);
        mQuoteImage.setAlpha(0f);
        mMapImage.setAlpha(0f);
        mWordPressImage.setAlpha(0f);
    }

    private static void doFadeAnimation() {


        ObjectAnimator fadeCamcord = ObjectAnimator.ofFloat(mCamcordImage, "alpha", 0f, 1f);
        fadeCamcord.setDuration(700);

        ObjectAnimator fadeClock = ObjectAnimator.ofFloat(mClockImage, "alpha", 0f, 1f);
        fadeClock.setDuration(700);

        ObjectAnimator fadeGraph = ObjectAnimator.ofFloat(mGraphImage, "alpha", 0f, 1f);
        fadeGraph.setDuration(700);

        ObjectAnimator fadeAudio = ObjectAnimator.ofFloat(mAudioImage, "alpha", 0f, 1f);
        fadeAudio.setDuration(700);

        ObjectAnimator fadeQuote = ObjectAnimator.ofFloat(mQuoteImage, "alpha", 0f, 1f);
        fadeQuote.setDuration(700);

        ObjectAnimator fadeMap = ObjectAnimator.ofFloat(mMapImage, "alpha", 0f, 1f);
        fadeMap.setDuration(700);

        ObjectAnimator fadeWordpress = ObjectAnimator.ofFloat(mWordPressImage, "alpha", 0f, 1f);
        fadeWordpress.setDuration(700);

        //1 5    3 2  7 6  4

        mAnimatorSet = new AnimatorSet();
        fadeAudio.setStartDelay(50);
        fadeGraph.setStartDelay(200);
        fadeWordpress.setStartDelay(500);
        fadeClock.setStartDelay(700);
        fadeMap.setStartDelay(900);
        fadeQuote.setStartDelay(1100);

        mAnimatorSet.play(fadeCamcord).with(fadeAudio).with(fadeGraph).with(fadeWordpress).with(fadeClock).with(fadeMap).with(fadeQuote);
        mAnimatorSet.start();

    }

    private static void initSecondScreenViews(View rootView) {

        mBookView = rootView.findViewById(R.id.center_box_second);
        mAnimationView = rootView.findViewById(R.id.animation_view);
    }

    private static void initThirdScreenViews(View rootView) {

        mRoundView = rootView.findViewById(R.id.round_view);
        mRoundView.setContext(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mContext = this;

        setUpViews();


    }

    private void setUpViews() {

        mPager = findViewById(R.id.pager);
        mIndicatorLayout = findViewById(R.id.indicator_layout);
        mOriginalXValuesMap = new HashMap<>();

        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        setIndicatorLayout();
        setPageChangeListener(mPager);
        mPager.bringToFront();
        //mPagerBackground.setAlpha(0);

        //mPager.setOffscreenPageLimit(2);

    }

    private void setIndicatorLayout() {

        int dotsCount = NUM_PAGES;
        mIndicatorView = new TextView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {

            mIndicatorView[i] = new TextView(this);
            mIndicatorView[i].setWidth((int) getResources().getDimension(R.dimen.dimen_12));
            mIndicatorView[i].setHeight((int) getResources().getDimension(R.dimen.dimen_12));
            mIndicatorView[i].setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, (int) getResources().getDimension(R.dimen.dimen_15), 0);
            mIndicatorView[i].setLayoutParams(params);
            mIndicatorView[i].setBackgroundResource(R.drawable.rounded_cell_gray);
            mIndicatorLayout.addView(mIndicatorView[i]);

        }

        //mIndicatorView[0].setWidth(20);
        //mIndicatorView[0].setHeight(20);
        mIndicatorView[0].setBackgroundResource(R.drawable.rounded_cell_red);
        mIndicatorView[0].setGravity(Gravity.CENTER);
    }

    private void setPageChangeListener(ViewPager viewPager) {


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // Scrollling left or right
                if ((positionOffset > mPreviousPositionOffset && position == mPreviousPosition) || (positionOffset < mPreviousPositionOffset && position > mPreviousPosition)) {
                    mViewPagerScrollingLeft = true;
                } else if (positionOffset < mPreviousPositionOffset) {

                    mViewPagerScrollingLeft = false;
                }
                mPreviousPositionOffset = positionOffset;
                mPreviousPosition = position;

                // FADE the indicator layout
                if (position == 1 && mViewPagerScrollingLeft) {

                    mIndicatorLayout.setAlpha(1 - positionOffset);
                } else if (position == 1) {

                    mIndicatorLayout.setAlpha(1 - positionOffset);
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageSelected(int position) {

                if (position == 1) {
                    mSelectedPosition = 1;
                    mSecondPageSelected = true;
                    setViewsInOriginalPosition();
                    //initializeAlpha();
                    if (mAnimatorSet != null) {
                        mAnimatorSet.cancel();
                    }

                    animateBookView();
                }
                if (position == 0) {
                    mSelectedPosition = 0;
                    doFadeAnimation();

                }


                for (TextView textView : mIndicatorView) {
                    textView.setBackgroundResource(R.drawable.rounded_cell_gray);
                }
                mIndicatorView[position].setBackgroundResource(R.drawable.rounded_cell_red);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    mShouldSpheresRotate = false;
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mShouldSpheresRotate = true;
                }
                if (mRoundView != null) {
                    mRoundView.setRotatingPermission(mShouldSpheresRotate);
                }

                if (mSelectedPosition == 0 && state == ViewPager.SCROLL_STATE_IDLE) {
                    mSecondPageSelected = false;
                }

            }
        });

    }

    private void animateBookView() {

        mBookView.fadeInTheLines();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setViewsInOriginalPosition() {

        mCenterBox.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mCenterBox)));
        mCamcordImage.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mCamcordImage)));
        mClockImage.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mClockImage)));
        mGraphImage.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mGraphImage)));
        mAudioImage.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mAudioImage)));
        mQuoteImage.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mQuoteImage)));
        mMapImage.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mMapImage)));
        mWordPressImage.setX(Objects.requireNonNull(mOriginalXValuesMap.get(mWordPressImage)));

        initializeAlpha();

    }

    public void openNews(View view) {
        startActivity(new Intent(InfoActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class CustomTransformer implements ViewPager.PageTransformer {


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void transformPage(View page, float position) {

            int pageWidth = page.getWidth();
            if ((mViewPagerScrollingLeft && page.findViewById(R.id.center_box) != null)) {
                animateSecondScreen(position, 0);
            }

            if (!mViewPagerScrollingLeft && page.findViewById(R.id.center_box_second) != null) {
                animateSecondScreen(position, 1);
            }

            if (position <= 1) {

                if (!mSecondPageSelected && page.findViewById(R.id.center_box_second) != null) {
                    moveTheSpheres(position, pageWidth);
                }

                if (!mShouldSpheresRotate && page.findViewById(R.id.center_box_third) != null) {
                    mRoundView.translateTheSpheres(position, pageWidth);
                }


            }

        }
    }

    @SuppressLint("ValidFragment")
    public static class ScreenSlideFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Bundle args = getArguments();
            int position = args != null ? args.getInt("position") : 0;
            int layoutId = getLayoutId(position);


            ViewGroup rootView = (ViewGroup) inflater.inflate(layoutId, container, false);
            if (position == 0)
                initFirstScreenViews(rootView, savedInstanceState);
            else if (position == 1)
                initSecondScreenViews(rootView);
            else if (position == 2)
                initThirdScreenViews(rootView);

            return rootView;
        }

        private int getLayoutId(int position) {

            int id = 0;
            if (position == 0) {

                id = R.layout.first_screen;

            } else if (position == 1) {

                id = R.layout.second_screen;
            } else if (position == 2) {

                id = R.layout.third_screen;
            }
            return id;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            ScreenSlideFragment fragment = new ScreenSlideFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
