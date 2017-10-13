package com.cunoraz.tagview;

/**
 * Constant class holding default values for {@link TagView} and {@link TagItem}.
 * Note: Size values are in dp or sp, except {@link Constants#DEFAULT_TAG_RADIUS_PIXELS} is in pixels
 */
public class Constants {


    //----------------- separator TagView-----------------//
    public static final float DEFAULT_LINE_MARGIN = 5;

    public static final float DEFAULT_TAG_MARGIN = 5;

    public static final float DEFAULT_TAG_TEXT_PADDING_LEFT = 8;

    public static final float DEFAULT_TAG_TEXT_PADDING_TOP = 5;

    public static final float DEFAULT_TAG_TEXT_PADDING_RIGHT = 8;

    public static final float DEFAULT_TAG_TEXT_PADDING_BOTTOM = 5;

    public static final float LAYOUT_WIDTH_OFFSET = 2;

    public static final boolean DEFAULT_LAYOUT_HORIZONTAL_SCROLL = false;

    //----------------- separator TagItem Item-----------------//

    public static final float DEFAULT_TAG_TEXT_SIZE = 14f;

    public static final boolean DEFAULT_TAG_IS_DELETABLE = false;

    public static final float DEFAULT_TAG_DELETE_INDICATOR_SIZE = 14f;

    public static final float DEFAULT_TAG_LAYOUT_BORDER_SIZE = 0f;

    public static final float DEFAULT_TAG_RADIUS_PIXELS = 100;;


    private Constants() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }
}
