package hf.view.main.cursor
{
    import flash.display.*;
    import hf.view.*;

    public class CursorTool extends BaseCursor
    {
        private var _cursor:MovieClip;

        public function CursorTool()
        {
            return;
        }// end function

        override public function click() : void
        {
            _cursor.gotoAndPlay(2);
            return;
        }// end function

        override public function set cursorArgument(param1:String) : void
        {
            super.cursorArgument = param1;
            var _loc_2:* = "CursorTool2" + param1;
            _cursor = MaterialLib.getInstance().getMaterial(_loc_2) as MovieClip;
            _cursor.cacheAsBitmap = true;
            addChild(_cursor);
            return;
        }// end function

    }
}
