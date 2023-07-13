package th.co.toei.exviewpager2carousel

import androidx.viewpager.widget.ViewPager




class ViewPagerScrollSync : ViewPager.OnPageChangeListener {
    private var actor: ViewPager? = null
    private var target: ViewPager? = null

    fun ViewPagerScrollSync(actor: ViewPager, target: ViewPager?) {
        this.actor = actor
        this.target = target
        actor.setOnPageChangeListener(this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (actor?.isFakeDragging() == true) {
            return;
        }
        if (target?.isFakeDragging() == true) {
            // calculate drag amount in pixels.
            // i don't have code for this off the top of my head, but you'll probably
            // have to store the last position and offset from the previous call to
            // this method and take the difference.
//            float dx = ...
//            target.fakeDragBy(dx);
        }
    }

    override fun onPageSelected(position: Int) {
        if (actor?.isFakeDragging() == true) {
            return;
        }

        // Check isFakeDragging here because this callback also occurs when
        // the user lifts his finger on a drag. If it was a real drag, we will
        // have begun a fake drag of the target; otherwise it was probably a
        // programmatic change of the current page.
        if (!target?.isFakeDragging()!!) {
            target!!.setCurrentItem(position);
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (actor?.isFakeDragging() == true) {
            return;
        }

        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            // actor has begun a drag
            target?.beginFakeDrag();
        } else if (state == ViewPager.SCROLL_STATE_IDLE) {
            // actor has finished settling
            target?.endFakeDrag();
        }
    }
}