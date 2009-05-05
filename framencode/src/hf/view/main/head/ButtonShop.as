package hf.view.main.head
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class ButtonShop extends ToolBase
    {

        public function ButtonShop()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonShop") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
