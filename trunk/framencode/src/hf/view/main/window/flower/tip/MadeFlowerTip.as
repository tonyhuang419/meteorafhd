package hf.view.main.window.flower.tip
{
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.main.tip.*;

    public class MadeFlowerTip extends Tip
    {
        private var tipBg:Sprite;
        private var tipText:TextField;

        public function MadeFlowerTip()
        {
            tipBg = MaterialLib.getInstance().getMaterial("TipBg") as Sprite;
            addChild(tipBg);
            tipText = new TextField();
            tipText.x = 15;
            tipText.y = 5;
            tipText.defaultTextFormat = new TextFormat("Tahoma", 12, 0);
            addChild(tipText);
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            if (param1 != null && param1.hasOwnProperty("itemName"))
            {
                tipText.text = param1.itemName;
            }// end if
            super.data = param1;
            return;
        }// end function

    }
}
