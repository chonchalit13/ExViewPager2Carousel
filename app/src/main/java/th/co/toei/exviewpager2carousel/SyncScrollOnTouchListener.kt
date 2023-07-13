package th.co.toei.exviewpager2carousel

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.viewpager2.widget.ViewPager2


class SyncScrollOnTouchListener(private val syncedView: ViewPager2) : OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val syncEvent = MotionEvent.obtain(motionEvent)
        val width1 = view.width.toFloat()
        val width2 = syncedView.width.toFloat()

        //sync motion of two view pagers by simulating a touch event
        //offset by its X position, and scaled by width ratio
        syncEvent.setLocation(
            syncedView.x + motionEvent.x * width2 / width1,
            motionEvent.y
        )
        syncedView.onTouchEvent(syncEvent)
        return false
    }
}