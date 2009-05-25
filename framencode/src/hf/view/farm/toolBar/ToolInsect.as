package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolInsect extends ToolBase
    {

        public function ToolInsect()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonInsect") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
