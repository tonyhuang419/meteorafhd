package hf.view.main.head
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class ButtonDecorate extends ToolBase
    {

        public function ButtonDecorate()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonDecorate") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
