package hf.view.main.head
{
    import flash.display.*;
    import flash.filters.*;
    import flash.text.*;
    import hf.view.*;

    public class NameText extends Sprite
    {
        private var _text:String = "";
        private var _vipBitmap:Bitmap;
        private var _textField:TextField;
        private var _vip:String = "";

        public function NameText()
        {
            return;
        }// end function

        private function setVipPosition() : void
        {
            if (_vipBitmap != null && _textField != null)
            {
                _vipBitmap.x = _textField.width + 5;
                _vipBitmap.y = 3;
            }// end if
            return;
        }// end function

        public function set text(param1:String) : void
        {
            _text = param1;
            if (_textField == null)
            {
                _textField = new TextField();
                _textField.filters = [new GlowFilter(16777215, 1, 3, 3, 7)];
                _textField.selectable = false;
                _textField.autoSize = TextFieldAutoSize.LEFT;
                _textField.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
                addChild(_textField);
            }// end if
            _textField.text = String(param1);
            setVipPosition();
            return;
        }// end function

        public function get text() : String
        {
            return _text;
        }// end function

        public function set vip(param1:String) : void
        {
            var _loc_2:Class;
            var _loc_3:BitmapData;
            if (param1 == "" || param1 == null)
            {
                if (_vipBitmap != null)
                {
                    removeChild(_vipBitmap);
                    _vipBitmap = null;
                }// end if
            }
            else if (param1 != _vip)
            {
                if (_vipBitmap != null)
                {
                    removeChild(_vipBitmap);
                    _vipBitmap = null;
                }// end if
                _loc_2 = MaterialLib.getInstance().getClass(param1) as Class;
                if (_loc_2 == null)
                {
                    return;
                }// end if
                _loc_3 = new _loc_2(16, 13);
                _vipBitmap = new Bitmap(_loc_3);
                addChild(_vipBitmap);
                setVipPosition();
            }
            else if (_vipBitmap != null)
            {
                setVipPosition();
            }// end else if
            _vip = param1;
            return;
        }// end function

        public function get vip() : String
        {
            return _vip;
        }// end function

    }
}
