package com.SwipeUp.utilities.asyncTasks;

import android.arch.lifecycle.ViewModelStoreOwner;
import android.os.AsyncTask;
import android.util.Log;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.shuffleManagement.ShuffleFragment;

import static java.lang.Thread.sleep;

/**
 * Instances of this class are created to recognize a long press by waiting for a certain amount
 * of time (LONG_PRESS_MILLIS milliseconds)
 */
public class ButtonHider extends AsyncTask<ViewModelStoreOwner, Void, ViewModelStoreOwner> {
    public static final int LONG_PRESS_MILLIS = 300;

    private boolean slept = false;

    /**
     * @param b the value that slept will assume
     */
    public synchronized void setSlept(boolean b){ slept = b; }

    /**
     * @return true if the buttonHider waited LONG_PRESS_MILLIS without interruption, false otherwise
     */
    public synchronized boolean getSlept(){ return slept; }

    /**
     * @param viewModelStoreOwners are the shuffleActivity and the shuffleFragment
     * @return the shuffle Activity if buttons should be hidden, null otherwise
     */
    protected ViewModelStoreOwner doInBackground(ViewModelStoreOwner... viewModelStoreOwners) {
        try{
            sleep(LONG_PRESS_MILLIS);
        }
        catch(InterruptedException e){
            Log.d("Interrupted", "Someone interrupted me");
            return null;
        }
        setSlept(true);
        Log.i("Button hider", "I slept");

        ShuffleFragment thisFragment = (ShuffleFragment)viewModelStoreOwners[1];
        if( thisFragment.isResumed() )  //checks is this fragment is active before hiding buttons
            return viewModelStoreOwners[0];

        return null;
    }

    @Override
    protected void onPostExecute(ViewModelStoreOwner aViewModelStoreOwner) {
        if(aViewModelStoreOwner != null)
            ((ShuffleFragment)aViewModelStoreOwner).hideButtons();
    }
}
