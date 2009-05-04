package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolWater extends ToolBase
    {

        public function ToolWater()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonWater") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
