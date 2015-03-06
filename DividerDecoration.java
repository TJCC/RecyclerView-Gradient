```
// your package here

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ItemDecoration implementation that applies and inset margin
 * around each child of the RecyclerView. It also draws item dividers
 * that are expected from a vertical list implementation, such as
 * ListView.
 */
public class DividerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = {android.R.attr.listDivider};

    private Drawable mDivider;
    private int mInsets;
    private DividerType mDividerType;

    // Set a gap at the beginning and end of the line.
    // Works better for the solid line as the gradient fades
    // at beginning and end anyway giving the same look without
    // the need to offset
    private int mbeginEndOffset = 10;

    // Setting the value to 1 will stop the code drawing
    // a line under the last item in the list. If you want a
    // line under the last item set it to 0
    private int mUnderlineLastItem = 1;

    private int mGradientColour;

    enum DividerType {
        SOLID_LINE,
        GRADIENT
    }

    /**
     * Creates either a solid line or a gradient line between the list items in a
     * RecyclerView
     * @param context - the context
     * @param dividerType - Solid or gradient
     * @param gradientColour - the middle colour of the gradient. The gradient consists
     *                       of three colours, the first and last are taken from the
     *                       RecyclerView programmatically
     */
    public DividerDecoration(Context context, DividerType dividerType, int gradientColour) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mDividerType = dividerType;
        mGradientColour = gradientColour;
        //mInsets = context.getResources().getDimensionPixelSize(R.dimen.card_insets);
        mInsets = 0;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDividerType == DividerType.SOLID_LINE)
            drawVertical(c, parent);
        else if (mDividerType == DividerType.GRADIENT)
            drawGradient(c, parent);
    }

    /**
     * Draw solid dividers underneath each child view
     */
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + mbeginEndOffset;
        final int right = (parent.getWidth() - parent.getPaddingRight()) - mbeginEndOffset;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - mUnderlineLastItem; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + mInsets;
            final int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * Draw gradient dividers underneath each child view
     */
    public void drawGradient(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = (parent.getWidth() - parent.getPaddingRight());

        ColorDrawable recyclerViewColour = (ColorDrawable) parent.getBackground();
        int recyclerViewBGColour = recyclerViewColour.getColor();

        Shader shader;
        Paint paint;
        int[] colors = {recyclerViewBGColour, mGradientColour, recyclerViewBGColour};

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - mUnderlineLastItem; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + mInsets;
            final int bottom = top + mDivider.getIntrinsicHeight();

            shader = new LinearGradient((float) left, (float) top, (float) right, (float) bottom, colors, null, Shader.TileMode.CLAMP);
            paint = new Paint();
            paint.setShader(shader);
            c.drawRect(new Rect(left, top, right, bottom), paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}
```
