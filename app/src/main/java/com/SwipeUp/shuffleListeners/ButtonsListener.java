package com.SwipeUp.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.SwipeUp.swipeUp.R;
import com.bumptech.glide.Glide;
import com.SwipeUp.swipeUp.MainActivity;
import com.SwipeUp.swipeUp.asyncTasks.DislikeComputing;
import com.SwipeUp.swipeUp.asyncTasks.LikeComputing;
import com.SwipeUp.swipeUp.asyncTasks.SwipeUpComputing;

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

                Animation appearence=AnimationUtils.loadAnimation(mainActivity, R.anim.appearence_like_x);
                mainActivity.like.setImageResource(R.drawable.like_pressed);
                mainActivity.like.startAnimation(appearence);

                mainActivity.dislike.setImageResource(R.drawable.dislike);

                heart.startAnimation(pulse);


            }
            else{
                Animation appearence=AnimationUtils.loadAnimation(mainActivity, R.anim.appearence_like_x);
                mainActivity.like_pressed = false;
                mainActivity.like.setImageResource(R.drawable.like);
                mainActivity.like.startAnimation(appearence);
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            if(!mainActivity.dislike_pressed) {
                mainActivity.like_pressed = false;
                mainActivity.dislike_pressed=true;
                mainActivity.like.setImageResource(R.drawable.like);
                mainActivity.dislike.setImageResource(R.drawable.dislike_pressed);

                //if we whant to change image after click X comment Animation appearence... and startAnimation(appearence);
                //and remove the comment to the other lines
                //Animation disappearence=AnimationUtils.loadAnimation(mainActivity, R.anim.disappearence);
                //mainActivity.dislike.startAnimation(disappearence);
                Animation appearence=AnimationUtils.loadAnimation(mainActivity, R.anim.appearence_like_x);
                mainActivity.dislike.startAnimation(appearence);
                //mainActivity.RightTap();


            }
            else{
                mainActivity.dislike_pressed = false;
                mainActivity.dislike.setImageResource(R.drawable.dislike);
                Animation appearence=AnimationUtils.loadAnimation(mainActivity, R.anim.appearence_like_x);
                mainActivity.dislike.startAnimation(appearence);
            }
            new DislikeComputing().doInBackground();
        }
    }

    public static class SwipeUpListener implements View.OnClickListener{
        private MainActivity mainActivity;

        public SwipeUpListener(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            mainActivity.startSwipeUpActivity(null);
            new SwipeUpComputing().doInBackground();
        }
    }
}
