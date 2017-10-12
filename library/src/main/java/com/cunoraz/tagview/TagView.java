package com.cunoraz.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TagView extends RelativeLayout {

    /**
     * tag list
     */
    private List<Tag> mTags = new ArrayList<>();

    /**
     * System Service
     */
    private LayoutInflater mInflater;

    private ViewTreeObserver mViewTreeObserber;

    /**
     * listeners
     */
    private OnTagClickListener mClickListener;

    private OnTagDeleteListener mDeleteListener;

    private OnTagLongClickListener mTagLongClickListener;

    /**
     * view size param
     */
    private int mWidth;

    /**
     * layout initialize flag
     */
    private boolean layoutInitialized = false;

    /**
     * margin value between rows of tags for multiline tagview
     */
    private int lineMargin;

    /**
     * Margin between two tags
     */
    private int tagMargin;

    /**
     * tag text padding left
     */
    private int textPaddingLeft;

    /**
     * tag text padding right
     */
    private int textPaddingRight;

    /**
     * tag text padding top
     */
    private int textPaddingTop;

    /**
     * tag text padding bottom
     */
    private int textPaddingBottom;

    /**
     * boolean to keep all tags in same row or add new rows
     */
    private boolean horizontalScrollable=false;


    public TagView(Context context) {
        this(context, null, 0);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs, defStyle);
    }

    /**
     * initialize instance
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    private void initialize(Context context, AttributeSet attrs, int defStyle) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewTreeObserber = getViewTreeObserver();
        mViewTreeObserber.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!layoutInitialized) {
                    layoutInitialized = true;
                    drawTags();
                }
            }
        });

        // get AttributeSet
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyle, defStyle);
        this.lineMargin = (int) typeArray.getDimension(R.styleable.TagView_lineMargin, Utils.dipToPx(this.getContext(), Constants.DEFAULT_LINE_MARGIN));
        this.tagMargin = (int) typeArray.getDimension(R.styleable.TagView_tagMargin, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagView_textPaddingLeft, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagView_textPaddingRight, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagView_textPaddingTop, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_TOP));
        this.textPaddingBottom = (int) typeArray.getDimension(R.styleable.TagView_textPaddingBottom, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM));
        this.horizontalScrollable= (boolean) typeArray.getBoolean(R.styleable.TagView_horizontalScrollable,Constants.DEFAULT_HORIZONTAL_SCROLL);
        typeArray.recycle();
    }

    /**
     * onSizeChanged
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (width <= 0)
            return;
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTags();
    }

    /**
     * draw all the tags
     */
    private void drawTags() {

        if (!layoutInitialized)
            return;

        // clear all previous tags, if present any
        removeAllViews();

        // layout padding left & layout padding right
        float total = getPaddingLeft() + getPaddingRight();

        // List Index
        int listIndex = 1;

        // for multiline we need anchor object form above line to correctly place
        int anchorTagId = 1;

        // The header tag of this line
        int firstTagInLineId = 1;

        // previous tag holder
        Tag tagPre = null;

        // loop through tags list and add one by one to view
        for (Tag item : mTags) {
            final int position = listIndex - 1;
            final Tag tag = item;

            // inflate tag layout
            View tagLayout = mInflater.inflate(R.layout.tagview_item, null);
            // set id of the tag
            tagLayout.setId(listIndex);

            tagLayout.setBackground(getSelector(tag));

            // tag text
            TextView tagView = tagLayout.findViewById(R.id.tv_tag_item_contain);
            tagView.setText(tag.getText());
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, textPaddingBottom);
            tagView.setLayoutParams(params);
            tagView.setTextColor(tag.getTagTextColor());
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.getTagTextSize());

            tagLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onTagClick(tag, position);
                    }
                }
            });

            tagLayout.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mTagLongClickListener != null) {
                        mTagLongClickListener.onTagLongClick(tag, position);
                    }
                    return true;
                }
            });

            // calculate　of tag layout width including the left and right padding
            float tagWidth = tagView.getPaint().measureText(tag.getText()) + textPaddingLeft + textPaddingRight;

            // deletable text view config, if set deletable
            TextView deletableView = tagLayout.findViewById(R.id.tv_tag_item_delete);
            if (tag.isDeletable()) {
                deletableView.setVisibility(View.VISIBLE);
                deletableView.setText(tag.getDeleteIcon());
                // offset between text and the delete button
                int offset = Utils.dipToPx(getContext(), 2f);
                deletableView.setPadding(offset, textPaddingTop, textPaddingRight + offset, textPaddingBottom);

                // set the color of delete indicator
                deletableView.setTextColor(tag.getDeleteIndicatorColor());
                deletableView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.getDeleteIndicatorSize());

                // attach delete listener to the deletable view
                deletableView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDeleteListener != null) {
                            mDeleteListener.onTagDeleted(TagView.this, tag, position);
                        }
                    }
                });

                // add the width of deletable view to the tag width including padding
                tagWidth += deletableView.getPaint().measureText(tag.getDeleteIcon()) + textPaddingLeft + textPaddingRight;
            }

            // params for adding tag to the tag view
            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //add margin of each line
            tagParams.bottomMargin = lineMargin;

            //need to add in new line in tag view
            if (!horizontalScrollable && mWidth <= total + tagWidth + Utils.dipToPx(this.getContext(), Constants.LAYOUT_WIDTH_OFFSET)) {

                // check if there is already a line, add a below rule.
                if (tagPre != null)
                    tagParams.addRule(RelativeLayout.BELOW, anchorTagId);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                anchorTagId = listIndex;
                firstTagInLineId = listIndex;
            }
            //no need to new line
            else {
                tagParams.addRule(RelativeLayout.ALIGN_TOP, firstTagInLineId);
                //not header of the line
                if (listIndex != firstTagInLineId) {
                    tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                    // if we have a new tag whose height is greater than last greatest tag set that tag as anchor
                    if (tagPre.getTagTextSize() < tag.getTagTextSize()) {
                        anchorTagId = listIndex;
                    }
                }
            }
            total += tagWidth;
            addView(tagLayout, tagParams);
            tagPre = tag;
            listIndex++;
        }
    }

    private Drawable getSelector(Tag tag) {
        if (tag.getBackground() != null)
            return tag.getBackground();

        StateListDrawable states = new StateListDrawable();
        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setColor(tag.getLayoutColor());
        gdNormal.setCornerRadius(tag.getBorderRadius());
        if (tag.getLayoutBorderSize() > 0) {
            gdNormal.setStroke(Utils.dipToPx(getContext(), tag.getLayoutBorderSize()), tag.getLayoutBorderColor());
        }
        GradientDrawable gdPress = new GradientDrawable();
        gdPress.setColor(tag.getLayoutColorPress());
        gdPress.setCornerRadius(tag.getBorderRadius());
        states.addState(new int[]{android.R.attr.state_pressed}, gdPress);
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(new int[]{}, gdNormal);
        return states;
    }


    //public methods
    //----------------- separator  -----------------//

    /**
     * @param tag
     */
    public void addTag(Tag tag) {

        mTags.add(tag);
        drawTags();
    }

    public void addTags(List<Tag> tags) {
        if (tags == null) return;
        mTags = new ArrayList<>();
        if (tags.isEmpty())
            drawTags();
        for (Tag item : tags) {
            mTags.add(item);
        }
        drawTags();
    }


    public void addTags(String[] tags) {
        if (tags == null) return;
        for (String item : tags) {
            Tag tag = new Tag(item);
            mTags.add(tag);
        }
        drawTags();
    }


    /**
     * get tag list
     *
     * @return mTags TagObject List
     */
    public List<Tag> getTags() {
        return mTags;
    }

    /**
     * remove tag
     *
     * @param position
     */
    public void remove(int position) {
        if (position < mTags.size()) {
            mTags.remove(position);
            drawTags();
        }
    }

    /**
     * remove all views
     */
    public void removeAll() {
        mTags.clear(); //clear all of tags
        removeAllViews();
    }

    public int getLineMargin() {
        return lineMargin;
    }

    public void setLineMargin(float lineMargin) {
        this.lineMargin = Utils.dipToPx(getContext(), lineMargin);
    }

    public int getTagMargin() {
        return tagMargin;
    }

    public void setTagMargin(float tagMargin) {
        this.tagMargin = Utils.dipToPx(getContext(), tagMargin);
    }

    public int getTextPaddingLeft() {
        return textPaddingLeft;
    }

    public void setTextPaddingLeft(float textPaddingLeft) {
        this.textPaddingLeft = Utils.dipToPx(getContext(), textPaddingLeft);
    }

    public int getTextPaddingRight() {
        return textPaddingRight;
    }

    public void setTextPaddingRight(float textPaddingRight) {
        this.textPaddingRight = Utils.dipToPx(getContext(), textPaddingRight);
    }

    public int getTextPaddingTop() {
        return textPaddingTop;
    }

    public void setTextPaddingTop(float textPaddingTop) {
        this.textPaddingTop = Utils.dipToPx(getContext(), textPaddingTop);
    }

    public int getTextPaddingBottom() {
        return textPaddingBottom;
    }

    public void setTextPaddingBottom(int textPaddingBottom) {
        this.textPaddingBottom = textPaddingBottom;
    }

    public boolean isHorizontalScrollable() {
        return horizontalScrollable;
    }

    public void setHorizontalScrollable(boolean horizontalScrollable) {
        this.horizontalScrollable = horizontalScrollable;
    }

    /**
     * setter for OnTagLongClickListener
     *
     * @param longClickListener
     */
    public void setOnTagLongClickListener(OnTagLongClickListener longClickListener) {
        mTagLongClickListener = longClickListener;
    }

    /**
     * setter for OnTagSelectListener
     *
     * @param clickListener
     */
    public void setOnTagClickListener(OnTagClickListener clickListener) {
        mClickListener = clickListener;
    }

    /**
     * setter for OnTagDeleteListener
     *
     * @param deleteListener
     */
    public void setOnTagDeleteListener(OnTagDeleteListener deleteListener) {
        mDeleteListener = deleteListener;
    }

    /**
     * Listeners
     */
    public interface OnTagDeleteListener {
        void onTagDeleted(TagView view, Tag tag, int position);
    }

    public interface OnTagClickListener {
        void onTagClick(Tag tag, int position);
    }

    public interface OnTagLongClickListener {
        void onTagLongClick(Tag tag, int position);
    }
}
