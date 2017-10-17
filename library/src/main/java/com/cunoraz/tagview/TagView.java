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
     * tagItem list
     */
    private List<TagItem> tagItemList = new ArrayList<>();

    /**
     * System Service
     */
    private LayoutInflater mInflater;

    private ViewTreeObserver mViewTreeObserver;

    /**
     * listeners
     */
    private OnTagItemClickListener tagItemClickListener;

    private OnTagItemDeleteListener tagItemDeleteListener;

    private OnTagItemLongClickListener tagItemLongClickListener;

    /**
     * view size param
     */
    private int tagViewWidth;

    /**
     * layout initialize flag
     */
    private boolean layoutInitialized = false;

    /**
     * margin value between rows of tags for multiline tagView
     */
    private int lineMargin;

    /**
     * Margin between two tags
     */
    private int tagItemMargin;

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
    private boolean horizontalScrollable;

    private boolean tagItemCenterVertical;


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
        mViewTreeObserver = getViewTreeObserver();
        mViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
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
        this.tagItemMargin = (int) typeArray.getDimension(R.styleable.TagView_tagItemMargin, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagView_textPaddingLeft, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagView_textPaddingRight, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagView_textPaddingTop, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_TOP));
        this.textPaddingBottom = (int) typeArray.getDimension(R.styleable.TagView_textPaddingBottom, Utils.dipToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM));
        this.horizontalScrollable= typeArray.getBoolean(R.styleable.TagView_horizontalScrollable,Constants.DEFAULT_LAYOUT_HORIZONTAL_SCROLL);
        this.tagItemCenterVertical= typeArray.getBoolean(R.styleable.TagView_tagItemCenterVertical,Constants.DEFAULT_TAG_ITEM_CENTER_VERTICAL);
        typeArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        tagViewWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (width <= 0)
            return;
        tagViewWidth = getMeasuredWidth();
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
        TagItem tagItemPre = null;

        // loop through tags list and add one by one to view
        for (TagItem item : tagItemList) {
            final int position = listIndex - 1;
            final TagItem tagItem = item;

            // inflate tagItem layout
            View tagLayout = mInflater.inflate(R.layout.tagview_item, null);
            // set id of the tagItem
            tagLayout.setId(listIndex);

            tagLayout.setBackground(getSelector(tagItem));

            // tagItem text
            TextView tagView = tagLayout.findViewById(R.id.tv_tag_item_contain);
            tagView.setText(tagItem.getText());
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, textPaddingBottom);
            tagView.setLayoutParams(params);
            tagView.setTextColor(tagItem.getTagTextColor());
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tagItem.getTagTextSize());

            tagLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tagItemClickListener != null) {
                        tagItemClickListener.onTagClick(tagItem, position);
                    }
                }
            });

            tagLayout.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (tagItemLongClickListener != null) {
                        tagItemLongClickListener.onTagLongClick(tagItem, position);
                    }
                    return true;
                }
            });

            // calculate　of tagItem layout width including the left and right padding
            float tagWidth = tagView.getPaint().measureText(tagItem.getText()) + textPaddingLeft + textPaddingRight;

            // deletable text view config, if deletable set true
            TextView deletableView = tagLayout.findViewById(R.id.tv_tag_item_delete);
            if (tagItem.isDeletable()) {
                deletableView.setVisibility(View.VISIBLE);
                deletableView.setText(tagItem.getDeleteIndicator());
                // offset between text and the delete button
                int offset = Utils.dipToPx(getContext(), 2f);
                deletableView.setPadding(offset, textPaddingTop, textPaddingRight + offset, textPaddingBottom);

                // set the color of delete indicator
                deletableView.setTextColor(tagItem.getDeleteIndicatorColor());
                deletableView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tagItem.getDeleteIndicatorSize());

                // attach delete listener to the deletable view
                deletableView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tagItemDeleteListener != null) {
                            tagItemDeleteListener.onTagDeleted(TagView.this, tagItem, position);
                        }
                    }
                });

                // add the width of deletable view to the tagItem width including padding
                tagWidth += deletableView.getPaint().measureText(tagItem.getDeleteIndicator()) + textPaddingLeft + textPaddingRight;
            }

            // params for adding tagItem to the tagItem view
            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //add margin of each line
            tagParams.bottomMargin = lineMargin;

            //need to add in new line in tagItem view
            if (!horizontalScrollable && tagViewWidth <= total + tagWidth + Utils.dipToPx(this.getContext(), Constants.LAYOUT_WIDTH_OFFSET)) {

                // check if there is already a line, add a below rule.
                if (tagItemPre != null)
                    tagParams.addRule(RelativeLayout.BELOW, anchorTagId);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                anchorTagId = listIndex;
                firstTagInLineId = listIndex;
            }
            //no need to new line
            else {
                tagParams.addRule(RelativeLayout.ALIGN_TOP, firstTagInLineId);
                if(tagItemCenterVertical)
                tagParams.addRule(RelativeLayout.CENTER_VERTICAL);
                //not header of the line
                if (listIndex != firstTagInLineId) {
                    tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    tagParams.leftMargin = tagItemMargin;
                    total += tagItemMargin;
                    // if we have a new tagItem whose height is greater than last greatest tagItem set that tagItem as anchor
                    if (tagItemPre.getTagTextSize() < tagItem.getTagTextSize()) {
                        anchorTagId = listIndex;
                    }
                }
            }
            total += tagWidth;
            addView(tagLayout, tagParams);
            tagItemPre = tagItem;
            listIndex++;
        }
    }

    /**
     * get the drawable to be set for the tag item
     * @param tagItem tag item for which drawable is needed.
     * @return drawable to be set as background.
     */
    private Drawable getSelector(TagItem tagItem) {
        if (tagItem.getBackground() != null)
            return tagItem.getBackground();

        StateListDrawable states = new StateListDrawable();
        GradientDrawable gdNormal = new GradientDrawable();
        gdNormal.setColor(tagItem.getLayoutColor());
        gdNormal.setCornerRadius(tagItem.getBorderRadius());
        if (tagItem.getLayoutBorderSize() > 0) {
            gdNormal.setStroke(Utils.dipToPx(getContext(), tagItem.getLayoutBorderSize()), tagItem.getLayoutBorderColor());
        }
        GradientDrawable gdPress = new GradientDrawable();
        gdPress.setColor(tagItem.getLayoutColorPress());
        gdPress.setCornerRadius(tagItem.getBorderRadius());
        states.addState(new int[]{android.R.attr.state_pressed}, gdPress);
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(new int[]{}, gdNormal);
        return states;
    }

    /**
     * add single tag item to tag view
     * @param tagItem tag item with all properties set.
     */
    public void add(TagItem tagItem) {

        tagItemList.add(tagItem);
        drawTags();
    }

    /**
     * add multiple tag items at once.
     * @param tagItems list of all tags to add
     */
    public void add(List<TagItem> tagItems) {
        if (tagItems == null) return;
        tagItemList = new ArrayList<>();
        if (tagItems.isEmpty())
            drawTags();
        tagItemList.addAll(tagItems);
        drawTags();
    }


    /**
     * create tag items from string array using defa
     * @param tags array of strings
     */
    public void add(String[] tags) {
        if (tags == null) return;
        for (String item : tags) {
            TagItem tagItem = new TagItem(getContext(),item);
            tagItemList.add(tagItem);
        }
        drawTags();
    }


    /**
     * get tag list
     * @return tagItemList TagObject List
     */
    public List<TagItem> getTagItemList() {
        return tagItemList;
    }

    /**
     * remove single tag with position of the tag in the list
     * @param tagItemPosition position of tag to be removed
     */
    public void remove(int tagItemPosition) {
        if (tagItemPosition < tagItemList.size()) {
            tagItemList.remove(tagItemPosition);
            drawTags();
        }
    }

    /**
     * remove all tag item views
     */
    public void removeAll() {
        tagItemList.clear();
        removeAllViews();
    }

    public int getLineMargin() {
        return lineMargin;
    }

    public void setLineMargin(float lineMargin) {
        this.lineMargin = Utils.dipToPx(getContext(), lineMargin);
    }

    public int getTagItemMargin() {
        return tagItemMargin;
    }

    public void setTagItemMargin(float tagItemMargin) {
        this.tagItemMargin = Utils.dipToPx(getContext(), tagItemMargin);
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
     * setter for OnTagItemLongClickListener
     *
     * @param tagItemLongClickListener
     */
    public void setOnTagLongClickListener(OnTagItemLongClickListener tagItemLongClickListener) {
        this.tagItemLongClickListener = tagItemLongClickListener;
    }

    /**
     * setter for OnTagSelectListener
     *
     * @param tagItemClickListener
     */
    public void setOnTagClickListener(OnTagItemClickListener tagItemClickListener) {
        this.tagItemClickListener = tagItemClickListener;
    }

    /**
     * setter for OnTagItemDeleteListener
     *
     * @param tagItemDeleteListener
     */
    public void setOnTagDeleteListener(OnTagItemDeleteListener tagItemDeleteListener) {
        this.tagItemDeleteListener = tagItemDeleteListener;
    }

    public void setTagItemCenterVertical(boolean tagItemCenterVertical) {
        this.tagItemCenterVertical = tagItemCenterVertical;
    }

    /**
     * Listeners
     */
    public interface OnTagItemDeleteListener {
        void onTagDeleted(TagView view, TagItem tagItem, int position);
    }

    public interface OnTagItemClickListener {
        void onTagClick(TagItem tagItem, int position);
    }

    public interface OnTagItemLongClickListener {
        void onTagLongClick(TagItem tagItem, int position);
    }
}
