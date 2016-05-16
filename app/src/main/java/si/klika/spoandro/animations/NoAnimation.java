package si.klika.spoandro.animations;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jernej on 27.4.2016.
 */
public class NoAnimation extends ImageView implements MatchPitchCallback {

    public NoAnimation(Context context) {
        super(context);
        throw new UnsupportedOperationException("This view was only ment to be constructed from XML layout");
    }

    public NoAnimation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoAnimation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setResult(int home, int away) {

    }

}
