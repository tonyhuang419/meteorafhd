package hf.view.main.tip
{
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;

    public class MouseTip extends Tip
    {
        private var tipBg:Sprite;
        private var tipText:TextField;

        public function MouseTip()
        {
            tipBg = MaterialLib.getInstance().getMaterial("TipBg") as Sprite;
            addChild(tipBg);
            tipText = new TextField();
            tipText.selectable = false;
            tipText.x = 5;
            tipText.y = 5;
            tipText.autoSize = TextFieldAutoSize.LEFT;
            tipText.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            addChild(tipText);
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            if (param1 == null)
            {
                param1 = "";
            }// end if
            tipText.text = param1.toString();
            tipBg.width = tipText.width + 10;
            tipBg.height = tipText.height + 10;
            super.data = param1;
            return;
        }// end function

        override public function set mX(param1:Number) : void
        {
            super.mX = param1;
            if (stage != null)
            {
                if (param1 - 5 <= 0)
                {
                    param1 = 0;
                }
                else if (param1 - 5 + width > stage.stageWidth)
                {
                    param1 = stage.stageWidth - width;
                }
                else
                {
                    param1 = param1 - 5;
                }// end else if
            }
            else
            {
                param1 = param1 - 5;
            }// end else if
            x = param1;
            return;
        }// end function

        override public function set mY(param1:Number) : void
        {
            super.mY = param1;
            if (stage != null)
            {
                if (param1 + height + 30 > stage.stageHeight)
                {
                    param1 = param1 - height - 8;
                }
                else
                {
                    param1 = param1 + 20;
                }// end else if
            }
            else
            {
                param1 = param1 + 20;
            }// end else if
            y = param1;
            return;
        }// end function

    }
}
