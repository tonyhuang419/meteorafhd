package hf.view.farm.toolBar
{
    import flash.display.*;

    public class ToolBase extends Sprite
    {
        private var _tipText:String = "";

        public function ToolBase()
        {
            return;
        }// end function

        public function set tipText(param1:String) : void
        {
            _tipText = param1;
            return;
        }// end function

        public function get tipText() : String
        {
            return _tipText;
        }// end function

    }
}
