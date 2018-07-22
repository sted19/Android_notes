package com.example.SwipeUp.buttonsListener;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.SwipeUp.swipeUp.MainActivity;
import com.example.SwipeUp.swipeUp.R;
import com.example.SwipeUp.swipeUp.asyncTasks.DislikeComputing;
import com.example.SwipeUp.swipeUp.asyncTasks.LikeComputing;
import com.example.SwipeUp.swipeUp.asyncTasks.SwipeUpComputing;

public class ButtonsListener {
    public static class LikeListener implements View.OnClickListener{
        private MainActivity mainActivity;

        private Animation pulse;
        private ImageView heart;

        public LikeListener(MainActivity mainActivity) {
            this.mainActivity = mainActivity;

            // animation initialization
            heart = mainActivity.findViewById(R.id.heart);
            heart.setVisibility(View.INVISIBLE);
            pulse = AnimationUtils.loadAnimation(mainActivity, R.anim.zoom_and_disappearence);
        }

        @Override
        public void onClick(View v) {
            if(!mainActivity.like_pressed) {
                mainActivity.like_pressed = true;
                mainActivity.dislike_pressed=false;
                mainActivity.like.setImageResource(R.drawable.like_pressed);
                mainActivity.dislike.setImageResource(R.drawable.dislike);

                heart.startAnimation(pulse);
            }
            else{
                mainActivity.like_pressed = false;
                mainActivity.like.setImageResource(R.drawable.like);
            }
            new LikeComputing().doInBackground();
        }

        public void clearAnimation() {
            heart.clearAnimation();
        }
    }

    public static class DislikeListener implements View.OnClickListener{
        private MainActivity mainActivity;

        public DislikeListener(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onClick(View v) {
            if(!mainActivity.dislike_pressed) {
                mainActivity.like_pressed = false;
                mainActivity.dislike_pressed=true;
                mainActivity.like.setImageResource(R.drawable.like);
                mainActivity.dislike.setImageResource(R.drawable.dislike_pressed);
            }
            else{
                mainActivity.dislike_pressed = false;
                mainActivity.dislike.setImageResource(R.drawable.dislike);
            }
            new DislikeComputing().doInBackground();
        }
    }

    public static class SwipeUpListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            new SwipeUpComputing().doInBackground();
        }
    }
}
