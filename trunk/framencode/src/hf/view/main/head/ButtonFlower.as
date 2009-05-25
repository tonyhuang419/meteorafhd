package hf.view.main.head
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class ButtonFlower extends ToolBase
    {

        public function ButtonFlower()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("FlowerButton") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
