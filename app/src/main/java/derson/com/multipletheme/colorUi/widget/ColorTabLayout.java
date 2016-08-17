package derson.com.multipletheme.colorUi.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import derson.com.multipletheme.colorUi.ColorUiInterface;
import derson.com.multipletheme.colorUi.util.ViewAttributeUtil;

/**
 * Created by 00245338 on 2016/8/16.
 */
public class ColorTabLayout extends TabLayout implements ColorUiInterface {
    private int attr_background = -1;
    private int attr_textAppreance = -1;

    public ColorTabLayout(Context context) {
        super(context);
    }

    public ColorTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    public ColorTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        ViewAttributeUtil.applyTextAppearance(this, themeId, attr_textAppreance);
    }
}
