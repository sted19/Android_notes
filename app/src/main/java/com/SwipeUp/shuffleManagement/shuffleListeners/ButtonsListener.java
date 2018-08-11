package com.SwipeUp.shuffleManagement.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.asyncTasks.DislikeComputing;
import com.SwipeUp.utilities.asyncTasks.LikeComputing;

public class ButtonsListener {
    public static class LikeListener implements View.OnClickListener{
        private ShuffleActivity shuffleActivity;

        private Animation pulse;
        private ImageView heart;

        public LikeListener(ShuffleActivity shuffleActivity) {
            this.shuffleActivity = shuffleActivity;

            // animation initialization
            heart = shuffleActivity.findViewById(R.id.heart);
            heart.setVisibility(View.INVISIBLE);
            pulse = AnimationUtils.loadAnimation(shuffleActivity, R.anim.zoom_and_disappearence);
        }

        @Override
        public void onClick(View v) {
            if(!shuffleActivity.like_pressed) {
                shuffleActivity.like_pressed = true;
                shuffleActivity.dislike_pressed=false;

                Animation appearence=AnimationUtils.loadAnimation(shuffleActivity, R.anim.appearence_like_x);
                shuffleActivity.like.setImageResource(R.drawable.like_pressed);
                shuffleActivity.like.startAnimation(appearence);

                shuffleActivity.dislike.setImageResource(R.drawable.dislike);

                heart.startAnimation(pulse);


            }
            else{
                Animation appearence=AnimationUtils.loadAnimation(shuffleActivity, R.anim.appearence_like_x);
                shuffleActivity.like_pressed = false;
                shuffleActivity.like.setImageResource(R.drawable.like);
                shuffleActivity.like.startAnimation(appearence);
            }
            new LikeComputing().doInBackground();
        }

        public void clearAnimation() {
            heart.clearAnimation();
        }
    }


    public static class DislikeListener implements View.OnClickListener{
        private ShuffleActivity shuffleActivity;

        public DislikeListener(ShuffleActivity shuffleActivity) {
            this.shuffleActivity = shuffleActivity;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            if(!shuffleActivity.dislike_pressed) {
                shuffleActivity.like_pressed = false;
                shuffleActivity.dislike_pressed=true;
                shuffleActivity.like.setImageResource(R.drawable.like);
                shuffleActivity.dislike.setImageResource(R.drawable.dislike_pressed);

                //if we whant to change image after click X comment Animation appearence... and startAnimation(appearence);
                //and remove the comment to the other lines
                //Animation disappearence=AnimationUtils.loadAnimation(shuffleActivity, R.anim.disappearence);
                //shuffleActivity.dislike.startAnimation(disappearence);
                Animation appearence=AnimationUtils.loadAnimation(shuffleActivity, R.anim.appearence_like_x);
                shuffleActivity.dislike.startAnimation(appearence);
                //shuffleActivity.RightTap();


            }
            else{
                shuffleActivity.dislike_pressed = false;
                shuffleActivity.dislike.setImageResource(R.drawable.dislike);
                Animation appearence=AnimationUtils.loadAnimation(shuffleActivity, R.anim.appearence_like_x);
                shuffleActivity.dislike.startAnimation(appearence);
            }
            new DislikeComputing().doInBackground();
        }
    }

}
