package th.co.toei.exviewpager2carousel

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import th.co.toei.exviewpager2carousel.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter1 = CategoryAdapter()
    private val adapter2 = CategoryAdapter2()

    val categories = listOf(
        Category(1, "Your Recording"),
        Category(2, "Film"),
        Category(3, "Series"),
        Category(4, "Kids"),
        Category(5, "Sport")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pageMarginPx1 = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx1 = resources.getDimensionPixelOffset(R.dimen.offset)

        val pageTransformer1 = CompositePageTransformer().apply {
//            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val x = if (position > 1f) {
                    abs(1)
                } else if (position < -1f) {
                    abs(1)
                } else {
                    abs(position)
                }.toFloat()

                val r = 1 - x.toFloat()
                val scaleY = 0.85f + r * 0.15f
                page.scaleY = scaleY

                val viewPager = page.parent.parent as ViewPager2
                val offset = position * -(2 * offsetPx1 + pageMarginPx1)
                if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else {
                    page.translationY = offset
                }
            }
        }

        val pageMarginPx2 = resources.getDimensionPixelOffset(R.dimen.pageMargin2)
        val offsetPx2 = resources.getDimensionPixelOffset(R.dimen.offset2)

        val pageTransformer2 = CompositePageTransformer().apply {
            addTransformer { page, position ->
                val r = 1 - abs(position)
                val scaleY = 0.85f + r * 0.15f
                page.scaleY = scaleY

                val viewPager = page.parent.parent as ViewPager2
                val offset = position * -(2 * offsetPx2 + pageMarginPx2)
                if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else {
                    page.translationY = offset
                }
            }
        }

        with(binding.viewPager1) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            adapter = this@MainActivity.adapter1
            setPageTransformer(pageTransformer1)
        }

        with(binding.viewPager2) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            adapter = this@MainActivity.adapter2
            setPageTransformer(pageTransformer2)
        }


        adapter1.setListData(categories)
        adapter2.setListData(categories)

        binding.viewPager1.getRecyclerView().setOnTouchListener { view, motionEvent ->
            Log.e("toei1","${motionEvent.action}")
//            if (motionEvent.action == MotionEvent.ACTION_UP) {
//                return@setOnTouchListener true
//
//            }
            binding.viewPager2.getRecyclerView().onTouchEvent(motionEvent)
            return@setOnTouchListener false
        }
        binding.viewPager2.getRecyclerView().setOnTouchListener { view, motionEvent ->
            Log.e("toei2","${motionEvent.action}")
//            if (motionEvent.action == MotionEvent.ACTION_UP) {
//                return@setOnTouchListener true
//            }
            binding.viewPager1.getRecyclerView().onTouchEvent(motionEvent)
            return@setOnTouchListener false
        }

        binding.viewPager1.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (binding.viewPager2.currentItem != binding.viewPager1.currentItem) {
                        binding.viewPager2.setCurrentItem(binding.viewPager1.currentItem, true)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (binding.viewPager2.currentItem != position) {
                    binding.viewPager2.setCurrentItem(position, true)
                }
            }
        })
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (binding.viewPager1.currentItem != binding.viewPager2.currentItem) {
                        binding.viewPager1.setCurrentItem(binding.viewPager2.currentItem, true)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (binding.viewPager1.currentItem != position) {
                    binding.viewPager1.setCurrentItem(position, true)
                }
            }
        })
    }
}

fun ViewPager2.getRecyclerView(): RecyclerView {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    return recyclerViewField.get(this) as RecyclerView
}