package hf.view.main.cursor
{
    import flash.display.*;
    import hf.view.*;

    public class CursorWeed extends BaseCursor
    {
        private var _cursor:MovieClip;

        public function CursorWeed()
        {
            _cursor = MaterialLib.getInstance().getMaterial("CursorWeed") as MovieClip;
            _cursor.cacheAsBitmap = true;
            addChild(_cursor);
            return;
        }// end function

        override public function click() : void
        {
            _cursor.gotoAndPlay(2);
            return;
        }// end function

    }
}
