package hf.view.farm.land
{
    import flash.display.*;
    import flash.events.*;
    import hf.view.*;
    import hf.view.main.cursor.*;

    public class Wasteland extends Sprite
    {
        private var lib:MaterialLib;
        private var _reclaim:Boolean = false;
        private var reclaimButton:Sprite;

        public function Wasteland()
        {
            lib = MaterialLib.getInstance();
            var _loc_1:* = lib.getMaterial("Wasteland") as DisplayObject;
            addChild(_loc_1);
            return;
        }// end function

        public function set reclaim(param1:Boolean) : void
        {
            if (param1 && reclaimButton == null)
            {
                reclaimButton = lib.getMaterial("Reclaim") as Sprite;
                reclaimButton.x = 65;
                reclaimButton.y = -25;
                reclaimButton.addEventListener(MouseEvent.CLICK, reclaimClick);
                reclaimButton.addEventListener(MouseEvent.MOUSE_OVER, reclaimOver);
                reclaimButton.addEventListener(MouseEvent.MOUSE_OUT, reclaimOut);
                reclaimButton.buttonMode = true;
                reclaimButton.useHandCursor = true;
                addChild(reclaimButton);
            }
            else if (!param1 && reclaimButton != null)
            {
                reclaimButton.removeEventListener(MouseEvent.CLICK, reclaimClick);
                reclaimButton.removeEventListener(MouseEvent.MOUSE_OVER, reclaimOver);
                removeChild(reclaimButton);
                reclaimButton = null;
            }// end else if
            _reclaim = param1;
            return;
        }// end function

        public function get reclaim() : Boolean
        {
            return _reclaim;
        }// end function

        private function reclaimOut(param1:MouseEvent) : void
        {
            Cursor.useSystem(false);
            return;
        }// end function

        private function reclaimOver(param1:MouseEvent) : void
        {
            Cursor.useSystem(true);
            return;
        }// end function

        private function reclaimClick(param1:MouseEvent) : void
        {
            dispatchEvent(new Event(ViewEvent.RECLAIM_CLICK, true));
            return;
        }// end function

    }
}
