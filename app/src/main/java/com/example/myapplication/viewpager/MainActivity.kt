package com.example.myapplication.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private val introSlideAdapter = IntroSlideAdapter (
        listOf(
            IntroSlide("Hey", "I am Rachel", R.drawable.onboarding_img1),
            IntroSlide("Hello", "We are FRIENDS", R.drawable.onboarding_img2),
            IntroSlide("Okay", "Finally! We met", R.drawable.onboarding_img3)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val introSliderViewPager : ViewPager2 = findViewById(R.id.introSliderPageViewer)
        val buttonNext : Button = findViewById(R.id.buttonNext)

        introSliderViewPager.adapter = introSlideAdapter

        setUpIndicators()

        introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)
                }
            }
        )

        buttonNext.setOnClickListener {
            if (introSliderViewPager.currentItem + 1 < introSlideAdapter.itemCount) {
                introSliderViewPager.currentItem ++
            } else if ( (introSliderViewPager.currentItem + 1  == introSlideAdapter.itemCount)) {
                buttonNext.text = "Get Started"
            }
        }
    }


    private fun setUpIndicators () {
        val indicatorContainer: LinearLayout = findViewById(R.id.indicatorContainer)

        val indicators = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive)
                )
                this?.layoutParams = layoutParams
            }
            indicatorContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator (index: Int) {
        val indicatorContainer: LinearLayout = findViewById(R.id.indicatorContainer)

        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_active)
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive)
                )
            }
        }
    }
}