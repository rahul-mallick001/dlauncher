package app.olauncher.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import app.olauncher.helper.dpToPx

class MaxHeightNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    // Default max height: 165dp (comfortably fits ~4.5 tasks, indicating scrollability)
    var maxHeightPx: Int = 165.dpToPx()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var newHeightSpec = heightMeasureSpec
        if (maxHeightPx > 0) {
            val mode = MeasureSpec.getMode(heightMeasureSpec)
            val size = MeasureSpec.getSize(heightMeasureSpec)
            val targetSize = if (mode == MeasureSpec.UNSPECIFIED) maxHeightPx else minOf(size, maxHeightPx)
            newHeightSpec = MeasureSpec.makeMeasureSpec(targetSize, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }
}
