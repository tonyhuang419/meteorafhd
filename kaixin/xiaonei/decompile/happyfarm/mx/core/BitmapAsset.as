package mx.core
{
    import flash.display.*;

    public class BitmapAsset extends FlexBitmap implements IFlexAsset, IFlexDisplayObject
    {
        static const VERSION:String = "3.0.0.0";

        public function BitmapAsset(param1:BitmapData = null, param2:String = "auto", param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function get measuredWidth() : Number
        {
            if (bitmapData)
            {
                return bitmapData.width;
            }// end if
            return 0;
        }// end function

        public function get measuredHeight() : Number
        {
            if (bitmapData)
            {
                return bitmapData.height;
            }// end if
            return 0;
        }// end function

        public function setActualSize(param1:Number, param2:Number) : void
        {
            width = param1;
            height = param2;
            return;
        }// end function

        public function move(param1:Number, param2:Number) : void
        {
            this.x = param1;
            this.y = param2;
            return;
        }// end function

    }
}
