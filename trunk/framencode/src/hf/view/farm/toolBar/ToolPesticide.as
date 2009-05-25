package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolPesticide extends ToolBase
    {

        public function ToolPesticide()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonPesticide") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
