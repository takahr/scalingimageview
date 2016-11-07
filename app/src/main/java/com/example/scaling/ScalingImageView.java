package com.example.scaling;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ScalingImageView extends ImageView {
    private float mRatio;

    public ScalingImageView(Context context) {
        super(context);
    }

    public ScalingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScalingImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        int paddingHorizontal = getPaddingLeft() + getPaddingRight();
        int paddingVertical = getPaddingTop() + getPaddingBottom();

        // 最大サイズでのレイアウトの縦横比(横長の場合 ratio > 1)
        float ratio = (float) (maxWidth - paddingHorizontal) / (maxHeight - paddingVertical);

        final int widthSpec;
        final int heightSpec;
        if (mRatio < ratio) {
            // レイアウトのほうが横長の場合 : 縦が最大・横は画像比率に合わせる
            heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
            widthSpec = MeasureSpec.makeMeasureSpec((int) ((maxHeight - paddingVertical) * mRatio), MeasureSpec.EXACTLY);
        } else {
            // レイアウトのほうが縦長の場合 : 横が最大・縦は画像比率に合わせる
            widthSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY);
            heightSpec = MeasureSpec.makeMeasureSpec((int) ((maxWidth - paddingHorizontal) / mRatio), MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthSpec, heightSpec);
    }

    public void updateScaleSize() {
        Drawable drawable = getDrawable();
        // 画像の縦横比 (横長の場合 mRatio > 1)
        mRatio = (float) drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
    }
}
