package hf.view.main.cursor
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.land.*;

    public class CursorCropSeed extends BaseCursor
    {
        private var _cursor:Sprite;

        public function CursorCropSeed()
        {
            return;
        }// end function

        override public function set cursorArgument(param1:String) : void
        {
            super.cursorArgument = param1;
            var _loc_2:* = GetCropID.idToSeedName(param1);
            _cursor = MaterialLib.getInstance().getMaterial(_loc_2) as Sprite;
            _cursor.cacheAsBitmap = true;
            addChild(_cursor);
            return;
        }// end function

    }
}
