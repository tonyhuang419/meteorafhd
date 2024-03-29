﻿package mx.core
{
    import flash.display.*;
    import mx.utils.*;

    public class FlexBitmap extends Bitmap
    {
        static const VERSION:String = "3.0.0.0";

        public function FlexBitmap(param1:BitmapData = null, param2:String = "auto", param3:Boolean = false)
        {
            var bitmapData:* = param1;
            var pixelSnapping:* = param2;
            var smoothing:* = param3;
            super(bitmapData, pixelSnapping, smoothing);
            try
            {
                name = NameUtil.createUniqueName(this);
            }// end try
            catch (e:Error)
            {
            }// end catch
            return;
        }// end function

        override public function toString() : String
        {
            return NameUtil.displayObjectToString(this);
        }// end function

    }
}
