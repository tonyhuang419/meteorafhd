package hf.view.farm.pepsico
{
    import flash.text.*;
    import hf.view.common.*;

    public class LayersReward extends AddButtonWindow
    {
        private var result:Object;

        public function LayersReward(param1:Object)
        {
            result = param1;
            width = 350;
            height = 250;
            title = result["title"];
            mode = true;
            return;
        }// end function

        override public function init() : void
        {
            var _loc_3:int;
            var _loc_4:int;
            var _loc_5:int;
            var _loc_6:int;
            var _loc_7:MaterialProxy;
            var _loc_8:TextField;
            var _loc_1:* = new TextField();
            _loc_1.text = " " + result.direction;
            _loc_1.x = 20;
            _loc_1.y = 37;
            _loc_1.width = 315;
            _loc_1.height = 40;
            _loc_1.wordWrap = true;
            _loc_1.setTextFormat(new TextFormat("Tahoma", 14, 39423, true));
            addChild(_loc_1);
            var _loc_2:* = result.item.length;
            if (_loc_2 > 0)
            {
                _loc_3 = 50;
                _loc_4 = 20;
                _loc_5 = (width - (_loc_3 * _loc_2 + _loc_4 * _loc_2--)) * 0.5;
                _loc_6 = 0;
                while (_loc_6 < _loc_2)
                {
                    // label
                    _loc_7 = new MaterialProxy();
                    _loc_7.setContent(result.item[_loc_6]["eType"], result.item[_loc_6]["eParam"]);
                    _loc_7.x = _loc_5;
                    _loc_7.y = 80;
                    addChild(_loc_7);
                    _loc_8 = new TextField();
                    _loc_8.x = _loc_5 - _loc_4 * 0.5;
                    _loc_8.y = _loc_7.y + 60;
                    _loc_8.width = _loc_3 + _loc_4;
                    _loc_8.height = 50;
                    _loc_8.wordWrap = true;
                    _loc_8.htmlText = result.item[_loc_6].eNum;
                    _loc_8.setTextFormat(new TextFormat("fontForte", 22, 16737792, false, null, null, null, null, "center"));
                    _loc_8.embedFonts = true;
                    addChild(_loc_8);
                    _loc_5 = _loc_5 + _loc_3 + _loc_4;
                    _loc_6++;
                }// end while
            }// end if
            addButton("确定", "ButtonOrange", 60, 25, closeWindow);
            super.init();
            return;
        }// end function

    }
}
