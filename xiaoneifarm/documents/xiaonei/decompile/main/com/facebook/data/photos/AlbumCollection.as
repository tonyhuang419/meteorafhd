package com.facebook.data.photos
{
    import com.facebook.utils.*;

    public class AlbumCollection extends FacebookArrayCollection
    {

        public function AlbumCollection()
        {
            super(null, AlbumData);
            return;
        }// end function

        public function addAlbum(param1:AlbumData) : void
        {
            this.addItem(param1);
            return;
        }// end function

    }
}
