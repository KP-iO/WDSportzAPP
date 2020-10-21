package ypw.app.wdsportz.Interfaces;

import android.content.Context;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

/**
 * Created by khrishawn
 */
public class CheckableCardView extends CardView implements Checkable {

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    // Constructors and view initialization
    private boolean isChecked = false;


    public CheckableCardView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }
    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }
    @Override
    public boolean isChecked() {
        return isChecked;
    }
    @Override
    public void toggle() {
        setChecked(!this.isChecked);
    }
}