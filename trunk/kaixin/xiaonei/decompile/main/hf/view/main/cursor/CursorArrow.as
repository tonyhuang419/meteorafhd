package hf.view.main.cursor
{
    import flash.display.*;
    import hf.view.*;

    public class CursorArrow extends BaseCursor
    {
        private var _cursor:MovieClip;

        public function CursorArrow()
        {
            _cursor = MaterialLib.getInstance().getMaterial("CursorArrow") as MovieClip;
            addChild(_cursor);
            this.cacheAsBitmap = true;
            return;
        }// end function

        override public function down() : void
        {
            _cursor.gotoAndPlay(2);
            return;
        }// end function

        override public function up() : void
        {
            _cursor.gotoAndPlay(1);
            return;
        }// end function

    }
}
