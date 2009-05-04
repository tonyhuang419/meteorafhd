package hf.view.main.leftInfo
{
    import com.minutes.ui.collections.*;
    import flash.text.*;
    import hf.view.common.*;

    public class GiftItem extends LipiListChild
    {
        private var numText:TextField;
        private var materialProxy:MaterialProxy;

        public function GiftItem()
        {
            materialProxy = new MaterialProxy();
            addChild(materialProxy);
            numText = new TextField();
            numText.defaultTextFormat = new TextFormat("fontForte", 22, 16737792, true, null, null, null, null, TextFormatAlign.CENTER);
            numText.embedFonts = true;
            numText.width = 60;
            numText.height = 22;
            numText.y = 65;
            numText.selectable = false;
            addChild(numText);
            return;
        }// end function

        override public function get data() : Object
        {
            return super.data;
        }// end function

        override public function set data(param1:Object) : void
        {
            super.data = param1;
            materialProxy.setContent(param1["eType"], param1["eParam"]);
            numText.text = param1["eNum"];
            return;
        }// end function

        override public function get width() : Number
        {
            return 60;
        }// end function

    }
}
