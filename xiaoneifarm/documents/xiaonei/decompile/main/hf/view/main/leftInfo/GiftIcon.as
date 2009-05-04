package hf.view.main.leftInfo
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class GiftIcon extends ToolBase
    {

        public function GiftIcon()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("GiftIcon") as DisplayObject;
            addChild(_loc_1);
            buttonMode = true;
            useHandCursor = true;
            return;
        }// end function

    }
}
