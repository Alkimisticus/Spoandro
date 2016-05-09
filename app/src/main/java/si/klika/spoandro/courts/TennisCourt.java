package si.klika.spoandro.courts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jernej on 27.4.2016.
 */
public class TennisCourt extends ImageView implements MatchPitchCallback {

    public TennisCourt(Context context) {
        super(context);
        throw new UnsupportedOperationException("This view was only ment to be constructed from XML layout");
    }

    public TennisCourt(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TennisCourt(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setResult(int home, int away) {

    }

}
