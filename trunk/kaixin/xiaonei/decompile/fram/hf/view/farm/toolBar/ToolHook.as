package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolHook extends ToolBase
    {

        public function ToolHook()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonHook") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
