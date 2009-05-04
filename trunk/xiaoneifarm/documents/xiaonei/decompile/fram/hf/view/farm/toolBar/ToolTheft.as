package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolTheft extends ToolBase
    {

        public function ToolTheft()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonTheft") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
