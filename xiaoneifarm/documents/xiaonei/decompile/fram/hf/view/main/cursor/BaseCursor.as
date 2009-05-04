package hf.view.main.cursor
{
    import flash.display.*;

    public class BaseCursor extends Sprite implements IBaseCursor
    {
        private var _cursorArgument:String = "";

        public function BaseCursor()
        {
            return;
        }// end function

        public function down() : void
        {
            return;
        }// end function

        public function get cursorArgument() : String
        {
            return _cursorArgument;
        }// end function

        public function set cursorArgument(param1:String) : void
        {
            _cursorArgument = param1;
            return;
        }// end function

        public function click() : void
        {
            return;
        }// end function

        public function up() : void
        {
            return;
        }// end function

    }
}
