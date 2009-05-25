package hf.view.main.window.flower.made
{
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;

    public class GeneralMade extends Sprite
    {

        public function GeneralMade()
        {
            var _loc_1:* = new TextField();
            _loc_1.text = "制作 普通 花束：";
            _loc_1.x = 0;
            _loc_1.y = 0;
            _loc_1.width = 250;
            _loc_1.selectable = false;
            _loc_1.setTextFormat(new TextFormat("Tahoma", 14, 39423, true));
            addChild(_loc_1);
            var _loc_2:* = MaterialLib.getInstance().getMaterial("ItemBg") as Sprite;
            _loc_2.scaleX = 2.5;
            _loc_2.scaleY = 2.5;
            _loc_2.x = 0;
            _loc_2.y = 25;
            addChild(_loc_2);
            return;
        }// end function

    }
}
