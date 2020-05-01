package com.example.wdsportz.utils;

/**
 * Created by khrishawn
 */

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * Class responsible for changing the view from full screen to non-full screen and vice versa.
 *
 * @author Pierfrancesco Soffritti
 */
    public class FullScreenHelper extends Fragment {

        private static Activity context;
        //    private Activity context;
        private static View[] views;

        /**
         * @param context
         * @param views to hide/show
         */
        public FullScreenHelper(Activity context, View ... views) {
            this.context = context;
            this.views = views;
        }

        /**
         * call this method to enter full screen
         */
        public static void enterFullScreen() {
            View decorView = context.getWindow().getDecorView();

            hideSystemUi(decorView);

            for(View view : views) {
                view.setVisibility(View.GONE);
                view.invalidate();
            }
        }

        /**
         * call this method to exit full screen
         */
        public static void exitFullScreen() {
            View decorView = context.getWindow().getDecorView();

            showSystemUi(decorView);

            for(View view : views) {
                view.setVisibility(View.VISIBLE);
                view.invalidate();
            }
        }

        private static void hideSystemUi(View mDecorView) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        private static void showSystemUi(View mDecorView) {
            mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

