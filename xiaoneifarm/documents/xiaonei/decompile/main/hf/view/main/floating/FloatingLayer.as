package hf.view.main.floating
{
    import flash.display.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.control.*;

    public class FloatingLayer extends Sprite
    {

        public function FloatingLayer()
        {
            mouseEnabled = false;
            mouseChildren = false;
            addEventListener(Event.ADDED_TO_STAGE, addedToStage);
            return;
        }// end function

        private function init() : void
        {
            ViewControl.getInstance().addEventListener(FloatingEvent.FLOATING_OPEN, floatingOpen, false, 0, true);
            ViewControl.getInstance().addEventListener(FloatingEvent.FLOATING_CLOSE, floatingClose, false, 0, true);
            return;
        }// end function

        private function floatingClose(param1:FloatingEvent) : void
        {
            if (contains(param1.floating))
            {
                removeChild(param1.floating);
            }// end if
            return;
        }// end function

        private function addedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, addedToStage);
            init();
            return;
        }// end function

        private function floatingOpen(param1:FloatingEvent) : void
        {
            var _loc_3:String;
            var _loc_4:int;
            var _loc_2:* = param1.floating.fid;
            if (_loc_2 != -1)
            {
                _loc_3 = getQualifiedClassName(param1.floating);
                _loc_4 = 0;
                while (_loc_4 < numChildren)
                {
                    // label
                    if (_loc_3 == getQualifiedClassName(getChildAt(_loc_4)))
                    {
                        if (_loc_2 == (getChildAt(_loc_4) as FloatingBase).fid)
                        {
                            getChildAt(_loc_4).visible = false;
                        }// end if
                    }// end if
                    _loc_4++;
                }// end while
            }// end if
            param1.floating.start();
            addChild(param1.floating);
            return;
        }// end function

    }
}
