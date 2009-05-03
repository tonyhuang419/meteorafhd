package hf.view.main.head
{
    import flash.display.*;
    import hf.view.*;
    import hf.view.farm.toolBar.*;

    public class ButtonWarehouse extends ToolBase
    {

        public function ButtonWarehouse()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ButtonWarehouse") as SimpleButton;
            addChild(_loc_1);
            return;
        }// end function

    }
}
