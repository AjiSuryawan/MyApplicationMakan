package com.example.user1.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

public class Splashku extends Activity {

    private ConstraintLayout layout;
    private Animation smallToBig;
    private TextView title, desc;
    private Typeface fredoka, montSerratReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FullScreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashku);

        layout = findViewById(R.id.rootSplash);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        fredoka = Typeface.createFromAsset(getAssets(), "fonts/Fredoka.ttf");
        montSerratReg = Typeface.createFromAsset(getAssets(), "fonts/MontserratRegular.ttf");
        smallToBig = AnimationUtils.loadAnimation(this, R.anim.smalll_to_big);

        layout.startAnimation(smallToBig);

        title.setTypeface(fredoka);
        title.setTranslationY(-800);
        title.setAlpha(0);
        title.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(1000).setInterpolator(new BounceInterpolator()).start();

        new Handler().postDelayed(() -> {
            Intent i = new Intent(Splashku.this.getApplicationContext(), ControlClass.class);
            startActivity(i);
            finish();
        }, 1600);
    }
}
