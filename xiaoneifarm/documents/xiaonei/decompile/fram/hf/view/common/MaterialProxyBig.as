package hf.view.common
{
    import flash.display.*;
    import hf.view.*;

    public class MaterialProxyBig extends Sprite
    {
        private var materialProxy:MaterialProxy;

        public function MaterialProxyBig()
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ItemBg") as Sprite;
            _loc_1.name = "ItemBg";
            _loc_1.width = 120;
            _loc_1.height = 120;
            addChild(_loc_1);
            materialProxy = new MaterialProxy(MaterialProxy.BIG);
            addChild(materialProxy);
            return;
        }// end function

        public function setContent(param1:String, param2:int, param3:Boolean = true) : void
        {
            materialProxy.setContent(param1, param2, param3);
            return;
        }// end function

    }
}
