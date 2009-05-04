package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolWeed extends ToolBase
    {

        public function ToolWeed()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonWeed") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
