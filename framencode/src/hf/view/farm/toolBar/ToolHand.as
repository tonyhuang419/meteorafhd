package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolHand extends ToolBase
    {

        public function ToolHand()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonHand") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
