package hf.view.main.window.flower.made
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.view.*;

    public class FlowerItem extends LipiListChild
    {
        private var clickFnIndex:int;
        private var bg:Sprite;
        private var _data:Object;

        public function FlowerItem(param1:int = 0)
        {
            this.buttonMode = true;
            this.useHandCursor = true;
            clickFnIndex = param1;
            bg = MaterialLib.getInstance().getMaterial("ItemBg") as Sprite;
            addChild(bg);
            addEventListener(MouseEvent.CLICK, mouseClick);
            addEventListener(MouseEvent.MOUSE_OVER, mouseOver);
            addEventListener(MouseEvent.MOUSE_OUT, mouseOut);
            return;
        }// end function

        public function mouseClick(param1:MouseEvent) : void
        {
            var _loc_2:ViewEvent;
            if (clickFnIndex == 0)
            {
                return;
            }// end if
            if (data != null)
            {
                if (clickFnIndex == 1)
                {
                    _loc_2 = new ViewEvent(ViewEvent.CHILD_CLICK, true);
                    _loc_2.data = {action:"make", data:data};
                    dispatchEvent(_loc_2);
                }
                else if (clickFnIndex == 2)
                {
                }// end if
            }// end else if
            return;
        }// end function

        public function mouseOut(param1:MouseEvent) : void
        {
            var _loc_2:* = new TipEvent(TipEvent.TIP_HIDE);
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        public function mouseOver(param1:MouseEvent) : void
        {
            var _loc_2:TipEvent;
            if (data != null && data.hasOwnProperty("itemName"))
            {
                _loc_2 = new TipEvent(TipEvent.TIP_SHOW);
                _loc_2.tipType = "MadeFlowerTip";
                _loc_2.tipArgument = _data;
                ViewControl.getInstance().dispatchEvent(_loc_2);
            }// end if
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            _data = param1;
            return;
        }// end function

    }
}
