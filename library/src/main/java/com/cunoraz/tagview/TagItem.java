package com.cunoraz.tagview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

public class TagItem {

    /**
     * Id value for internal use.
     */
    private int id;

    /**
     * Optional string variable to hold extra identifier
     */
    @Nullable
    private String key;

    /**
     * text to be displayed in tag item
     */
    private String text;

    /**
     * Tag item text color resource int
     */
    private int tagTextColor;

    /**
     * Tag item size in sp
     */
    private float tagTextSize;

    /**
     * Tag item background color resource
     */
    private int layoutColor;

    /**
     * Tag item background color resource in pressed state.
     */
    private int layoutColorPress;

    /**
     * boolean to allow removing tag item, default is false.
     */
    private boolean isDeletable;

    /**
     * String to be displayed delete indicator, usually one character is preferred
     */
    private String deleteIndicator;

    /**
     * Color resource int for the delete indicator
     */
    private int deleteIndicatorColor;

    /**
     * Size in sp for delete indicator
     */
    private float deleteIndicatorSize;

    /**
     * border corner radius value in pixels
     */
    private float borderRadius;

    /**
     * size of border to be shown in dp
     */
    private float layoutBorderSize;

    /**
     * Color resource int
     */
    private int layoutBorderColor;

    /**
     * custom background drawable
     */
    private Drawable background;


    public TagItem(Context context, String text) {
        init(0, text,
                ContextCompat.getColor(context, R.color.primaryTextColor),
                Constants.DEFAULT_TAG_TEXT_SIZE,
                ContextCompat.getColor(context, R.color.primaryLightColor),
                ContextCompat.getColor(context, R.color.primaryColor),
                Constants.DEFAULT_TAG_IS_DELETABLE,
                ContextCompat.getColor(context, R.color.primaryTextColor),
                Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE,
                Constants.DEFAULT_TAG_RADIUS_PIXELS,
                context.getResources().getString(R.string.default_delete_icon),
                Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE,
                ContextCompat.getColor(context, R.color.primaryColor));
    }

    public TagItem(Context context, String text, boolean isDeletable) {
        init(0, text,
                ContextCompat.getColor(context, R.color.primaryTextColor),
                Constants.DEFAULT_TAG_TEXT_SIZE,
                ContextCompat.getColor(context, R.color.primaryLightColor),
                ContextCompat.getColor(context, R.color.primaryColor),
                isDeletable,
                ContextCompat.getColor(context, R.color.primaryTextColor),
                Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE,
                Constants.DEFAULT_TAG_RADIUS_PIXELS,
                context.getResources().getString(R.string.default_delete_icon),
                Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE,
                ContextCompat.getColor(context, R.color.primaryColor));
    }

    private void init(int id, String text, int tagTextColor, float tagTextSize,
                      int layoutColor, int layoutColorPress, boolean isDeletable,
                      int deleteIndicatorColor, float deleteIndicatorSize, float radius,
                      String deleteIndicator, float layoutBorderSize, int layoutBorderColor) {
        this.id = id;
        this.text = text;
        this.tagTextColor = tagTextColor;
        this.tagTextSize = tagTextSize;
        this.layoutColor = layoutColor;
        this.layoutColorPress = layoutColorPress;
        this.isDeletable = isDeletable;
        this.deleteIndicatorColor = deleteIndicatorColor;
        this.deleteIndicatorSize = deleteIndicatorSize;
        this.borderRadius = radius;
        this.deleteIndicator = deleteIndicator;
        this.layoutBorderSize = layoutBorderSize;
        this.layoutBorderColor = layoutBorderColor;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    /**
     * text value to be shown on tag
     *
     * @param text value
     */
    public void setText(String text) {
        this.text = text;
    }

    public int getTagTextColor() {
        return tagTextColor;
    }

    /**
     * set the color resource for tag text
     *
     * @param tagTextColor color resource value
     */
    public void setTagTextColor(@ColorInt int tagTextColor) {
        this.tagTextColor = tagTextColor;
    }

    public float getTagTextSize() {
        return tagTextSize;
    }

    /**
     * size of tag text
     *
     * @param tagTextSizeSp size in sp
     */
    public void setTagTextSize(float tagTextSizeSp) {
        this.tagTextSize = tagTextSizeSp;
    }

    public int getLayoutColor() {
        return layoutColor;
    }

    /**
     * set the normal state color of the tag
     *
     * @param layoutColor color resource value
     */
    public void setLayoutColor(@ColorInt int layoutColor) {
        this.layoutColor = layoutColor;
    }

    public int getLayoutColorPress() {
        return layoutColorPress;
    }

    /**
     * set pressed state color of the tag
     *
     * @param layoutColorPress color resource value
     */
    public void setLayoutColorPress(@ColorInt int layoutColorPress) {
        this.layoutColorPress = layoutColorPress;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    /**
     * set the visibility of delete button in tag
     *
     * @param deletable true to make deletable.
     */
    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }

    public String getDeleteIndicator() {
        return deleteIndicator;
    }

    /**
     * set the text as delete button, which upon click calls
     * the {@link TagView.OnTagItemDeleteListener#onTagDeleted(TagView, TagItem, int)}, if attached
     *
     * @param deleteIndicator indicator text value
     */
    public void setDeleteIndicator(String deleteIndicator) {
        this.deleteIndicator = deleteIndicator;
    }

    public int getDeleteIndicatorColor() {
        return deleteIndicatorColor;
    }

    /**
     * set the delete indicator text color
     *
     * @param deleteIndicatorColor color resource value
     */
    public void setDeleteIndicatorColor(@ColorInt int deleteIndicatorColor) {
        this.deleteIndicatorColor = deleteIndicatorColor;
    }

    public float getDeleteIndicatorSize() {
        return deleteIndicatorSize;
    }

    /**
     * set the size of delete indicator text
     *
     * @param deleteIndicatorSize size in sp
     */
    public void setDeleteIndicatorSize(float deleteIndicatorSize) {
        this.deleteIndicatorSize = deleteIndicatorSize;
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    /**
     * set radius of tag border to make it round or straight around edges
     *
     * @param pixelSize radius in pixels
     */
    public void setBorderRadius(float pixelSize) {
        this.borderRadius = pixelSize;
    }

    public float getLayoutBorderSize() {
        return layoutBorderSize;
    }

    public void setLayoutBorderSize(float layoutBorderSize) {
        this.layoutBorderSize = layoutBorderSize;
    }

    public int getLayoutBorderColor() {
        return layoutBorderColor;
    }

    /**
     * set the color of tag border
     *
     * @param layoutBorderColor resource value
     */
    public void setLayoutBorderColor(@ColorInt int layoutBorderColor) {
        this.layoutBorderColor = layoutBorderColor;
    }

    @Nullable
    public Drawable getBackground() {
        return background;
    }

    /**
     * set custom background drawable for the tag
     *
     * @param background drawable object
     */
    public void setBackground(Drawable background) {
        this.background = background;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
