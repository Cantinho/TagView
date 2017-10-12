package com.cunoraz.tagview;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

public class Tag {

    private int id;

    private String text;

    private int tagTextColor;

    private float tagTextSize;

    private int layoutColor;

    private int layoutColorPress;

    private boolean isDeletable;

    private int deleteIndicatorColor;

    private float deleteIndicatorSize;

    private float borderRadius;

    private String deleteIcon;

    private float layoutBorderSize;

    private int layoutBorderColor;

    private Drawable background;


    public Tag(String text) {
        init(0, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE,
                Constants.DEFAULT_TAG_LAYOUT_COLOR, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
                Constants.DEFAULT_TAG_IS_DELETABLE, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR,
                Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS,
                Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE,
                Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
    }

    public Tag(String text, boolean isDeletable) {
        init(0, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE,
                Constants.DEFAULT_TAG_LAYOUT_COLOR, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
                isDeletable, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR,
                Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS,
                Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE,
                Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
    }

    private void init(int id, String text, int tagTextColor, float tagTextSize,
                      int layoutColor, int layoutColorPress, boolean isDeletable,
                      int deleteIndicatorColor, float deleteIndicatorSize, float radius,
                      String deleteIcon, float layoutBorderSize, int layoutBorderColor) {
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
        this.deleteIcon = deleteIcon;
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
     * @param deletable true to make deletable.
     */
    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }

    public String getDeleteIcon() {
        return deleteIcon;
    }

    /**
     * set the text as delete button, which upon click calls
     * the {@link TagView.OnTagDeleteListener#onTagDeleted(TagView, Tag, int)}, if attached
     * @param deleteIcon indicator text value
     */
    public void setDeleteIcon(String deleteIcon) {
        this.deleteIcon = deleteIcon;
    }

    public int getDeleteIndicatorColor() {
        return deleteIndicatorColor;
    }

    /**
     * set the delete indicator text color
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
     * @param layoutBorderColor resource value
     */
    public void setLayoutBorderColor(@ColorInt int layoutBorderColor) {
        this.layoutBorderColor = layoutBorderColor;
    }

    public Drawable getBackground() {
        return background;
    }

    /**
     * set custom background drawable for the tag
     * @param background drawable object
     */
    public void setBackground( Drawable background) {
        this.background = background;
    }
}
