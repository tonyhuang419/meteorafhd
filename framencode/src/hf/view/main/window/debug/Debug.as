package hf.view.main.window.debug
{
    import com.adobe.serialization.json.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.main.window.flower.xml.*;

    public class Debug extends Sprite
    {

        public function Debug()
        {
            scene();
            return;
        }// end function

        private function scene() : void
        {
            var _loc_1:* = new TextField();
            _loc_1.mouseEnabled = true;
            _loc_1.selectable = false;
            _loc_1.multiline = true;
            _loc_1.htmlText = "<body><a href=\'event:{\"a\":1}\'><b>场景1</b></a>  <a href=\'event:{\"b\":2}\'><b>房子1</b></a>  <a href=\'event:{\"c\":3}\'><b>栅栏1</b></a>  <a href=\'event:{\"d\":4}\'><b>狗窝1</b></a>  <br>" + "<a href=\'event:{\"a\":11}\'><b>场景2</b></a>  <a href=\'event:{\"b\":12}\'><b>房子2</b></a>  <a href=\'event:{\"c\":13}\'><b>栅栏2</b></a>  <a href=\'event:{\"d\":14}\'><b>狗窝2</b></a>  <br>" + "<a href=\'event:{\"a\":16}\'><b>场景3</b></a>  <a href=\'event:{\"b\":17}\'><b>房子3</b></a>  <a href=\'event:{\"c\":18}\'><b>栅栏3</b></a>  <a href=\'event:{\"d\":19}\'><b>狗窝3</b></a>  <br>" + "<a href=\'event:{\"a\":21}\'><b>场景4</b></a>  <a href=\'event:{\"b\":22}\'><b>房子4</b></a>  <a href=\'event:{\"c\":23}\'><b>栅栏4</b></a>  <a href=\'event:{\"d\":24}\'><b>狗窝4</b></a>  <br>" + "<a href=\'event:{\"a\":26}\'><b>场景5</b></a>  <a href=\'event:{\"b\":27}\'><b>房子5</b></a>  <a href=\'event:{\"c\":28}\'><b>栅栏5</b></a>  <a href=\'event:{\"d\":29}\'><b>狗窝5</b></a>  <br>" + "<a href=\'event:{\"a\":31}\'><b>场景6</b></a>  <a href=\'event:{\"b\":32}\'><b>房子6</b></a>  <a href=\'event:{\"c\":33}\'><b>栅栏6</b></a>  <a href=\'event:{\"d\":34}\'><b>狗窝6</b></a>  <br>" + "<a href=\'event:{\"a\":36}\'><b>场景7</b></a>  <a href=\'event:{\"b\":37}\'><b>房子7</b></a>  <a href=\'event:{\"c\":38}\'><b>栅栏7</b></a>  <a href=\'event:{\"d\":39}\'><b>狗窝7</b></a>  <br>" + "<a href=\'event:{\"a\":41}\'><b>场景8</b></a>  <a href=\'event:{\"b\":42}\'><b>房子8</b></a>  <a href=\'event:{\"c\":43}\'><b>栅栏8</b></a>  <a href=\'event:{\"d\":44}\'><b>狗窝8</b></a>  <br>" + "35 class " + Configure.getInstance().getFlowerClass("35") + "<br> 35 is flower? " + Configure.getInstance().isFlower("35") + "<br>" + "35 className " + Configure.getInstance().getFlowerClassName("35") + "<br> Rose flowerId list" + Configure.getInstance().getFlowerIdList("Rose") + "<br>" + "Rose number list" + Configure.getInstance().getFlowerNumberList("Rose") + "<br> get gift id" + Configure.getInstance().getGiftId("Rose", "35", "3");
            _loc_1.addEventListener(TextEvent.LINK, changScene);
            addChild(_loc_1);
            _loc_1.height = 500;
            _loc_1.width = 400;
            _loc_1.x = 100;
            _loc_1.y = 200;
            var _loc_2:* = new TextField();
            _loc_2.selectable = false;
            _loc_2.mouseEnabled = true;
            _loc_2.width = width;
            _loc_2.height = 20;
            _loc_2.x = 300;
            _loc_2.y = 200;
            _loc_2.text = "Do you want to get more gifts everyday ？";
            var _loc_3:* = INI.getInstance().data.version.@loginurl + "/?act=invite";
            _loc_2.setTextFormat(new TextFormat("Tahoma", 12, 26367, true, null, true, _loc_3));
            addChild(_loc_2);
            var _loc_4:* = MaterialLib.getInstance().getMaterial("Dog") as Sprite;
            (MaterialLib.getInstance().getMaterial("Dog") as Sprite).x = 400;
            _loc_4.y = 200;
            addChild(_loc_4);
            return;
        }// end function

        private function changScene(param1:TextEvent) : void
        {
            var _loc_2:* = param1.text;
            var _loc_3:* = JSON.decode(_loc_2) as Object;
            var _loc_4:* = MData.getInstance().mainData.items;
            if (_loc_3.hasOwnProperty("a"))
            {
                _loc_4["1"] = {itemId:_loc_3["a"]};
            }
            else if (_loc_3.hasOwnProperty("b"))
            {
                _loc_4["2"] = {itemId:_loc_3["b"]};
            }
            else if (_loc_3.hasOwnProperty("c"))
            {
                _loc_4["3"] = {itemId:_loc_3["c"]};
            }
            else if (_loc_3.hasOwnProperty("d"))
            {
                _loc_4["4"] = {itemId:_loc_3["d"]};
            }// end else if
            MData.getInstance().mainData.items = _loc_4;
            return;
        }// end function

    }
}
