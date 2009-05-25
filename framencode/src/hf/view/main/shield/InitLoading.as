package hf.view.main.shield
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.view.common.*;

    public class InitLoading extends Sprite
    {
        private var dataLoading:DataLoading;
        private var _textField:TextField;

        public function InitLoading()
        {
            dataLoading = new DataLoading();
            dataLoading.x = 50;
            dataLoading.y = 50;
            dataLoading.visible = true;
            addChild(dataLoading);
            _textField = new TextField();
            _textField.addEventListener(TextEvent.LINK, linkClick);
            _textField.defaultTextFormat = new TextFormat("Tahoma", 14, 3355443);
            _textField.x = 90;
            _textField.y = 40;
            _textField.text = Language.L["数据初始化中..."];
            _textField.width = 200;
            _textField.height = 25;
            _textField.selectable = false;
            addChild(_textField);
            return;
        }// end function

        public function set errorText(param1:String) : void
        {
            _textField.htmlText = param1;
            _textField.x = 0;
            _textField.y = 0;
            dataLoading.visible = false;
            return;
        }// end function

        private function linkClick(param1:TextEvent) : void
        {
            if (param1.text == "reload")
            {
                _textField.text = Language.L["数据初始化中..."];
                _textField.x = 90;
                _textField.y = 40;
                dataLoading.visible = true;
                Command.getInstance().mainCommand.run();
            }// end if
            return;
        }// end function

    }
}
