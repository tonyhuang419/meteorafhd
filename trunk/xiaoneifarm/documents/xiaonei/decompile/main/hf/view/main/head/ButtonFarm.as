package hf.view.main.head
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class ButtonFarm extends ToolBase
    {

        public function ButtonFarm()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonFarm") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
