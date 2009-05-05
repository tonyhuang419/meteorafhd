package hf.view.main.head
{
    import flash.display.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.view.*;
    import hf.view.main.floating.*;

    public class AddMoneyFloating extends FloatingBase
    {
        private var preTime:int = 0;
        private var addAddAnimation:MovieClip;

        public function AddMoneyFloating()
        {
            addAddAnimation = MaterialLib.getInstance().getMaterial("AddAnimation") as MovieClip;
            addChild(addAddAnimation);
            return;
        }// end function

        override public function start() : void
        {
            if (preTime == 0)
            {
                preTime = getTimer();
                addEventListener(Event.ENTER_FRAME, onEnterFrame);
            }// end if
            addAddAnimation.gotoAndPlay(2);
            return;
        }// end function

        public function get data() : String
        {
            return addAddAnimation.addValueBox.addValue.text;
        }// end function

        public function set data(param1:String) : void
        {
            if (int(param1) >= 0)
            {
                addAddAnimation.addValueBox.addValue.text = "+" + param1;
            }
            else
            {
                addAddAnimation.addValueBox.addValue.text = param1;
            }// end else if
            return;
        }// end function

        private function onEnterFrame(param1:Event) : void
        {
            if (getTimer() - preTime > 5000)
            {
                removeEventListener(Event.ENTER_FRAME, onEnterFrame);
                preTime = 0;
                Float.close(this);
            }// end if
            return;
        }// end function

    }
}
