package com.facebook.data.pages
{
    import com.facebook.utils.*;

    public class PageInfoCollection extends FacebookArrayCollection
    {

        public function PageInfoCollection()
        {
            super(null, PageInfoData);
            return;
        }// end function

        public function addPageInfo(param1:PageInfoData) : void
        {
            this.addItem(param1);
            return;
        }// end function

    }
}
