package com.example.scaling;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private int mImageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image);
        switchImage(1);
    }

    private void switchImage(int index) {
        try {
            InputStream is = getAssets().open(String.format(Locale.US, "cat%d.jpg", index));
            Bitmap bm = BitmapFactory.decodeStream(is);
            mImageView.setImageBitmap(bm);
            if (mImageView instanceof ScalingImageView) {
                ((ScalingImageView) mImageView).updateScaleSize();
            }
            mImageView.getParent().requestLayout();
            mImageIndex = index;

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchImage(mImageIndex == 1 ? 2 : 1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
