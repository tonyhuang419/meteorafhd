package com.facebook.data.photos
{
    import com.facebook.utils.*;

    public class PhotoCollection extends FacebookArrayCollection
    {

        public function PhotoCollection()
        {
            super(null, PhotoData);
            return;
        }// end function

        public function addPhoto(param1:PhotoData) : void
        {
            this.addItem(param1);
            return;
        }// end function

    }
}
