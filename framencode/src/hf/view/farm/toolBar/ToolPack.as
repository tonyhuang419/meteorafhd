package hf.view.farm.toolBar
{
    import flash.display.*;
    import hf.view.*;

    public class ToolPack extends ToolBase
    {

        public function ToolPack()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonSeed") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
