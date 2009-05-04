package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolHoe extends ToolBase
    {

        public function ToolHoe()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonHoe") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
