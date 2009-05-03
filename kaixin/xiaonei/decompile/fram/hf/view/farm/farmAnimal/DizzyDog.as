package hf.view.farm.farmAnimal
{
    import flash.display.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.model.*;
    import hf.view.*;

    public class DizzyDog extends Sprite
    {
        private var totalTime:int = 179;
        private var dizzyDog:MovieClip;
        private var time:Timer;

        public function DizzyDog(param1:int = 0)
        {
            totalTime = param1--;
            dizzyDog = MaterialLib.getInstance().getMaterial("DizzyDog") as MovieClip;
            dizzyDog.x = 400;
            dizzyDog.y = 200;
            addChild(dizzyDog);
            dizzyDog.timeText.alpha = 0.7;
            setTime();
            return;
        }// end function

        private function timeOutEventDispatcher(param1:TimerEvent) : void
        {
            MData.getInstance().mainData.dogDizzyState = {dogUnWorkTime:0, requestTime:0};
            return;
        }// end function

        private function countDown(param1:TimerEvent) : void
        {
            var _loc_2:* = totalTime % 60;
            var _loc_3:* = (totalTime - _loc_2) / 60;
            var _loc_4:String;
            if (_loc_3 <= 9)
            {
                _loc_4 = _loc_4 + ("0" + String(_loc_3) + ":");
            }
            else
            {
                _loc_4 = _loc_4 + (String(_loc_3) + ":");
            }// end else if
            if (_loc_2 <= 9)
            {
                _loc_4 = _loc_4 + ("0" + String(_loc_2));
            }
            else
            {
                _loc_4 = _loc_4 + String(_loc_2);
            }// end else if
            dizzyDog.timeText.text = _loc_4;
            totalTime--;
            return;
        }// end function

        public function setTime() : void
        {
            time = new Timer(1000, totalTime);
            time.addEventListener(TimerEvent.TIMER, countDown);
            time.addEventListener(TimerEvent.TIMER_COMPLETE, timeOutEventDispatcher);
            time.start();
            return;
        }// end function

    }
}
