package com.puneet.android.custompopup.custompopupproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.MainThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Displays a floating container which appears on top of the current {@link android.app.Activity}.
 * Works similar to {@link PopupWindow} but with some extra features such as:
 * <ul>
 *     <li>Option to add a pointer drawable which can point to the view which triggered the popup.</li>
 *     <li>Option to set AUTO positioning of the popup around the anchor. i.e. It can programmatically
 *     decide whether to show it above the anchor or below depending on availability of space. Ideal for
 *     displaying popup on click of Items within a list.</li>
 * </ul>
 *
 * @see #setPointerDrawable(Drawable)
 * @see #setPointerBitmap(Bitmap, Context)
 * @see #show(View, ANCHOR)
 */
public class CustomPopupWindow extends PopupWindow {

    private static final String TAG = CustomPopupWindow.class.getName();
    /**
     * The position of the popup with reference to the anchor view.
     * <p>
     *     Options available are:
     *     <ul>
     *         <li>{@link #ABOVE} - Above the anchor view</li>
     *         <li>{@link #BELOW} - To the left of the anchor view</li>
     *         <li>{@link #AUTO} - Automatically decide where to show, based on availability of space. If space
     * available both above and below then shows below</li>
     *     </ul>
     * </p>
     */
    public enum ANCHOR {/** Above the anchor view */
                        ABOVE ,
                        /** Below the anchor view */
                        BELOW ,
                        /** Automatically decide where to show, based on availability of space. If space
                         * available both above and below then shows below */
                        AUTO };

    /**
     * The container view to be displayed in the {@link PopupWindow}
     */
    private View contentView;

    /**
     * The pointer drawable to show beside the popup pointing to the anchor.
     */
    private Drawable pointerDrawable;
    /**
     * <p>Create a new empty, non focusable popup window of dimension (0,0).</p>
     * <p/>
     * <p>The popup does provide a background.</p>
     *
     * @param context
     */
    public CustomPopupWindow(Context context) {
        super(context);
    }

    /**
     * <p>Create a new empty, non focusable popup window of dimension (0,0).</p>
     * <p/>
     * <p>The popup does provide a background.</p>
     *
     * @param context
     * @param attrs
     */
    public CustomPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * <p>Create a new empty, non focusable popup window of dimension (0,0).</p>
     * <p/>
     * <p>The popup does provide a background.</p>
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * <p>Create a new, empty, non focusable popup window of dimension (0,0).</p>
     * <p/>
     * <p>The popup does not provide a background.</p>
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public CustomPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * <p>Create a new empty, non focusable popup window of dimension (0,0).</p>
     * <p/>
     * <p>The popup does not provide any background. This should be handled
     * by the content view.</p>
     */
    public CustomPopupWindow() {
    }

    /**
     * <p>Create a new non focusable popup window which can display the
     * <tt>contentView</tt>. The dimension of the window are (0,0).</p>
     * <p/>
     * <p>The popup does not provide any background. This should be handled
     * by the content view.</p>
     *
     * @param contentView the popup's content
     */
    public CustomPopupWindow(View contentView) {
        super(contentView);
    }

    /**
     * <p>Create a new empty, non focusable popup window. The dimension of the
     * window must be passed to this constructor.</p>
     * <p/>
     * <p>The popup does not provide any background. This should be handled
     * by the content view.</p>
     *
     * @param width  the popup's width
     * @param height the popup's height
     */
    public CustomPopupWindow(int width, int height) {
        super(width, height);
    }

    /**
     * <p>Create a new non focusable popup window which can display the
     * <tt>contentView</tt>. The dimension of the window must be passed to
     * this constructor.</p>
     * <p/>
     * <p>The popup does not provide any background. This should be handled
     * by the content view.</p>
     *
     * @param contentView the popup's content
     * @param width       the popup's width
     * @param height      the popup's height
     */
    public CustomPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    /**
     * <p>Create a new popup window which can display the <tt>contentView</tt>.
     * The dimension of the window must be passed to this constructor.</p>
     * <p/>
     * <p>The popup does not provide any background. This should be handled
     * by the content view.</p>
     *
     * @param contentView the popup's content
     * @param width       the popup's width
     * @param height      the popup's height
     * @param focusable   true if the popup can be focused, false otherwise
     */
    public CustomPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    /**
     * The pointer drawable to display along with the popup pointing to the anchor. The default pointer
     * is put when popup is below the anchor, if the popup is above the anchor, then the pointer drawable is flipped.
     * @param pointerDrawable   The image to be displayed as pointer.
     */
    public void setPointerDrawable(final Drawable pointerDrawable){
        this.pointerDrawable = pointerDrawable;
    }

    /**
     * The pointer {@link Bitmap} to display along with the popup pointing to the anchor. The default pointer
     * is put when popup is below the anchor, if the popup is above the anchor, then the pointer {@link Bitmap} is flipped.
     * @param pointerBitmap The {@link Bitmap} to be displayed as pointer
     * @param context       The {@link Context} in which to display the pointer.
     */
    public void setPointerBitmap(final Bitmap pointerBitmap, final Context context){
        this.pointerDrawable = new BitmapDrawable(context.getResources(), pointerBitmap);
    }

    /**
     * Sets the contentview for the {@link PopupWindow}.
     * @param contentView   The view to be set as the contentView for the popup.
     */
    public void setContentView(final View contentView){
        this.contentView = contentView;
    }
    /**
     * Displays the popup calculating the position where it needs to be displayed on the screen.
     *
     * @param anchorView  The view which will serve as an anchor. The popup will be displayed keeping this view as anchor.
     * @param anchor The {@link ANCHOR} where the popup must be displayed with reference to the anchor view.
     */
    @MainThread
    public final void show(final View anchorView, ANCHOR anchor){
        Log.d(TAG, "Entered show");
        final WindowManager windowManager = (WindowManager) anchorView.getContext().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final Point point = new Point();
        display.getSize(point);

        int[] anchorLocation = new int[2];
        anchorView.getLocationOnScreen(anchorLocation);

        int popupX = 0, popupY = 0;
        float anchorY = anchorLocation[1];
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int maxPopupHeight = contentView.getMeasuredHeight();
        ViewGroup container = (ViewGroup) contentView;
        ImageView pointerHolder = null;

        if(this.pointerDrawable!=null){ //Shows that poiter drawable is provided hence the contentview
                                        //must be enclosed in a wrapper.
            Log.d(TAG, "pointerdrawable is not null, hence adding a wrapper with the pointer and the contentview");
            final int pointerHeight = this.pointerDrawable.getIntrinsicHeight();
            maxPopupHeight = contentView.getMeasuredHeight() + pointerHeight;
            container = new LinearLayout(anchorView.getContext());
            container.setLayoutParams(new LinearLayout.LayoutParams(contentView.getMeasuredWidth(), maxPopupHeight));
            ((LinearLayout)container).setOrientation(LinearLayout.VERTICAL);

            pointerHolder = new ImageView(contentView.getContext());
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = (int) anchorView.getX() + anchorView.getWidth()/2 ;
            pointerHolder.setImageDrawable(this.pointerDrawable);
            pointerHolder.setLayoutParams(layoutParams);

            switch(anchor){
                case AUTO:
                    if( (anchorY + anchorView.getHeight() + maxPopupHeight) <= point.y){//Means popup can fit below the anchor
                        Log.d(TAG, "AUTO layout, and popup can fit below the anchor");
                        container.addView(pointerHolder);
                        container.addView(contentView);
                        popupX = 0;
                        popupY = (int) anchorY+anchorView.getHeight();
                    }else if(anchorY > maxPopupHeight){//Means popup can fit above the anchor
                        Log.d(TAG, "AUTO layout, and popup can fit above the anchor");
                        container.addView(contentView);
                        pointerHolder.setImageDrawable(getRotateDrawable(this.pointerDrawable, 180));
                        container.addView(pointerHolder);
                        popupX = 0;
                        popupY = (int) anchorY - maxPopupHeight;
                    }
                    break;
                case BELOW:
                    Log.d(TAG, "BELOW layout, fitting popup below the anchor");
                    container.addView(pointerHolder);
                    container.addView(contentView);
                    popupX = 0;
                    popupY = (int) anchorY+anchorView.getHeight();
                    break;
                case ABOVE:
                    Log.d(TAG, "ABOVE layout, fitting popup above the anchor");
                    container.addView(contentView);
                    pointerHolder.setImageDrawable(getRotateDrawable(this.pointerDrawable, 180));
                    container.addView(pointerHolder);
                    popupX = 0;
                    popupY = (int) anchorY - maxPopupHeight;
                    break;
                default:
                    break;
            }

        }else{
            Log.d(TAG, "pointerdrawable is null");
            switch(anchor){
                case AUTO:
                    if( (anchorY + anchorView.getHeight() + maxPopupHeight) <= point.y){//Means popup can fit below the anchor
                        Log.d(TAG, "AUTO layout, and popup can fit below the anchor");
                        popupX = 0;
                        popupY = (int) anchorY+anchorView.getHeight();
                    }else if(anchorY > maxPopupHeight){//Means popup can fit above the anchor
                        Log.d(TAG, "AUTO layout, and popup can fit above the anchor");
                        popupX = 0;
                        popupY = (int) anchorY - maxPopupHeight;
                    }
                    break;
                case BELOW:
                    Log.d(TAG, "BELOW layout, fitting popup below the anchor");
                    popupX = 0;
                    popupY = (int) anchorY+anchorView.getHeight();
                    break;
                case ABOVE:
                    Log.d(TAG, "ABOVE layout, fitting popup above the anchor");
                    popupX = 0;
                    popupY = (int) anchorY - maxPopupHeight;
                    break;
                default:
                    break;
            }
        }
        super.setContentView(container);

        Log.d(TAG, String.format("Invoking super.showAtLocation with x=%d, y=%d", popupX, popupY));
        super.showAtLocation(container, Gravity.NO_GRAVITY, popupX, popupY);

        Log.d(TAG, "Exit show");
    }

    private Drawable getRotateDrawable(final Drawable d, final float angle) {
        final Drawable[] arD = { d };
        return new LayerDrawable(arD) {
            @Override
            public void draw(final Canvas canvas) {
                canvas.save();
                canvas.rotate(angle, d.getBounds().width() / 2, d.getBounds().height() / 2);
                super.draw(canvas);
                canvas.restore();
            }
        };
    }
}
