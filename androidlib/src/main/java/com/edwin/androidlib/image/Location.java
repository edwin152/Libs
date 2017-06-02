package com.edwin.androidlib.image;
/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/11/19	| xuxiangyu 	| 	create the file
 */


/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/11/19
 */
public enum Location {
    LEFT(0x00),
    TOP(0x01),
    RIGHT(0x02),
    BOTTOM(0x03);

    private int value;

    Location(int value) {
        this.value = value;
    }

    public static Location mapIntToValue(int stateInt) {
        for (Location v : values())
            if (stateInt == v.value)
                return v;
        return LEFT;
    }
}
