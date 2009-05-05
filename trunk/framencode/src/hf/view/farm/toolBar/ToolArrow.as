package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolArrow extends ToolBase
    {

        public function ToolArrow()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("Cursor") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
