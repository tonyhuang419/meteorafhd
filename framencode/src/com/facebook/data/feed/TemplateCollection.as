package com.facebook.data.feed
{
    import com.facebook.utils.*;

    public class TemplateCollection extends FacebookArrayCollection
    {
        public var template_bundle_id:Number;
        public var time_created:Date;

        public function TemplateCollection()
        {
            super(null, TemplateData);
            return;
        }// end function

        public function addTemplateData(param1:TemplateData) : void
        {
            this.addItem(param1);
            return;
        }// end function

    }
}
