package hf.view.main.head
{
    import flash.events.*;
    import flash.utils.*;

    public class NumAnimation extends Object
    {
        private var aObject:Object;
        private var timer:Timer;
        private var startValue:Number = 0;
        private var aAttr:String;
        private var endValue:Number = 0;
        private var currentValue:Number = 0;

        public function NumAnimation(param1:Object, param2:String, param3:int, param4:int)
        {
            aObject = param1;
            aAttr = param2;
            startValue = param3;
            endValue = param4;
            start();
            return;
        }// end function

        private function start() : void
        {
            if (timer == null)
            {
                timer = new Timer(50);
                timer.addEventListener(TimerEvent.TIMER, onTimer);
                timer.start();
                currentValue = startValue;
            }// end if
            return;
        }// end function

        private function onTimer(param1:TimerEvent) : void
        {
            execute();
            return;
        }// end function

        private function execute() : void
        {
            var _loc_1:* = currentValue;
            _loc_1 = _loc_1 + (endValue - _loc_1) * 0.2;
            currentValue = _loc_1;
            aObject[aAttr] = String(int(_loc_1));
            if (Math.abs(endValue - _loc_1) < 1)
            {
                aObject[aAttr] = endValue;
                timer.stop();
                timer.removeEventListener(TimerEvent.TIMER, onTimer);
                aObject = null;
                aAttr = null;
            }// end if
            return;
        }// end function

    }
}
