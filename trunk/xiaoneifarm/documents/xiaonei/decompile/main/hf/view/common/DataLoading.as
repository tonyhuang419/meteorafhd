package hf.view.common
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.view.*;

    public class DataLoading extends Sprite
    {
        private var errorTextField:TextField;
        private var _dataLoading:MovieClip;
        private var _errorText:String = "";

        public function DataLoading()
        {
            _dataLoading = MaterialLib.getInstance().getMaterial("DataLoading") as MovieClip;
            _dataLoading.stop();
            return;
        }// end function

        override public function set visible(param1:Boolean) : void
        {
            super.visible = param1;
            if (errorTextField != null)
            {
                errorTextField.visible = false;
            }// end if
            if (param1)
            {
                if (!contains(_dataLoading))
                {
                    addChild(_dataLoading);
                }// end if
                _dataLoading.play();
                _dataLoading.visible = true;
            }
            else
            {
                _dataLoading.stop();
                if (contains(_dataLoading))
                {
                    removeChild(_dataLoading);
                }// end if
            }// end else if
            return;
        }// end function

        public function set errorText(param1:String) : void
        {
            _errorText = param1;
            if (errorTextField == null)
            {
                createTextField();
            }// end if
            errorTextField.htmlText = param1;
            _dataLoading.visible = false;
            errorTextField.visible = true;
            return;
        }// end function

        private function linkClick(param1:TextEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.LINK_CLICK);
            _loc_2.data = param1.text;
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function createTextField() : void
        {
            errorTextField = new TextField();
            errorTextField.defaultTextFormat = new TextFormat("Tahoma");
            errorTextField.x = -70;
            errorTextField.y = -10;
            errorTextField.autoSize = TextFieldAutoSize.LEFT;
            errorTextField.visible = false;
            addChild(errorTextField);
            errorTextField.addEventListener(TextEvent.LINK, linkClick);
            return;
        }// end function

        public function get errorText() : String
        {
            return _errorText;
        }// end function

    }
}
