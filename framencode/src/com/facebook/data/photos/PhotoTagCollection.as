﻿package com.facebook.data.photos
{
    import com.facebook.utils.*;

    public class PhotoTagCollection extends FacebookArrayCollection
    {

        public function PhotoTagCollection(param1:Array = null)
        {
            super(null, TagData);
            return;
        }// end function

        public function addPhotoTag(param1:TagData) : void
        {
            this.addItem(param1);
            return;
        }// end function

    }
}
