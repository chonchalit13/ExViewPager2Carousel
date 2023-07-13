package th.co.toei.exviewpager2carousel

import androidx.viewpager2.widget.ViewPager2


class SyncScrollOnTouchListener2(private val master: ViewPager2, private val slave: ViewPager2) :
    ViewPager2.OnPageChangeCallback() {
    private var mScrollState = ViewPager2.SCROLL_STATE_IDLE
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        slave.scrollTo(
            master.scrollX *
                    slave.width /
                    master.width, 0
        )
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {
        mScrollState = state
        if (state == ViewPager2.SCROLL_STATE_IDLE) {
            slave.setCurrentItem(
                master
                    .currentItem, false
            )
        }
    }
}