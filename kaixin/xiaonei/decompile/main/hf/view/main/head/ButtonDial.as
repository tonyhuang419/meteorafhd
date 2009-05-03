package hf.view.main.head
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class ButtonDial extends ToolBase
    {

        public function ButtonDial()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("DailTag") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
