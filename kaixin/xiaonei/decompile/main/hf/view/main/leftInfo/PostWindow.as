package hf.view.main.leftInfo
{
    import flash.text.*;
    import hf.control.*;
    import hf.view.common.*;

    public class PostWindow extends AddButtonWindow
    {
        private var _text:String = "";
        private var postText:TextField;

        public function PostWindow()
        {
            width = 370;
            height = 260;
            mode = true;
            title = Language.L["公告"];
            return;
        }// end function

        override public function init() : void
        {
            addButton(Language.L["确定"], "ButtonOrange", 65, 25, confirmFn);
            super.init();
            postText = new TextField();
            postText.wordWrap = true;
            postText.multiline = true;
            var _loc_1:* = new TextFormat("Tahoma");
            _loc_1.leading = 4;
            postText.defaultTextFormat = _loc_1;
            postText.y = 30;
            postText.x = 5;
            postText.width = 360;
            postText.height = 180;
            postText.htmlText = text;
            addChild(postText);
            return;
        }// end function

        public function set text(param1:String) : void
        {
            _text = param1;
            if (postText != null)
            {
                postText.text = param1;
            }// end if
            return;
        }// end function

        public function get text() : String
        {
            return _text;
        }// end function

        private function getPostDataFn(param1:Object) : void
        {
            if (param1["code"] == 1)
            {
                if (param1["content"] != null)
                {
                    text = param1["content"];
                }// end if
            }// end if
            return;
        }// end function

        public function getPostData() : void
        {
            Command.getInstance().mainCommand.getNotice(getPostDataFn);
            return;
        }// end function

        private function confirmFn() : void
        {
            closeHandler();
            return;
        }// end function

    }
}
