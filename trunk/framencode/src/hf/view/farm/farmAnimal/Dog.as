package hf.view.farm.farmAnimal
{
    import com.minutes.ui.vectorCartoon.*;
    import com.minutes.ui.vectorCartoon.cartoonFrameRate.*;
    import flash.display.*;
    import flash.events.*;
    import flash.geom.*;
    import hf.view.*;

    public class Dog extends Sprite
    {
        private var frame2:int = 11;
        private var vectorCartoon:VectorCartoon;
        private var frame:int = 1;
        private var dogFrameRate:CartoonFrameRate;
        private var distance:Number = 250;
        private var startX:int = 150;
        private var direction:Boolean = true;

        public function Dog()
        {
            var _loc_1:* = MaterialLib.getInstance().getClass("FarmDog1") as Class;
            var _loc_2:* = new _loc_1(1000, 65);
            var _loc_3:* = new Matrix(-1, 0, 0, 1, 1000, 0);
            var _loc_4:* = new Matrix(1, 0, 0, 1, 1000, 0);
            var _loc_5:* = new BitmapData(2000, 65, true, 0);
            new BitmapData(2000, 65, true, 0).draw(_loc_2, _loc_3);
            _loc_5.draw(_loc_2, _loc_4);
            dogFrameRate = new CartoonFrameRate(12);
            vectorCartoon = new VectorCartoon(_loc_5, 100, 65, dogFrameRate);
            vectorCartoon.width = 100;
            vectorCartoon.height = 65;
            vectorCartoon.x = startX;
            vectorCartoon.y = 200;
            addChild(vectorCartoon);
            vectorCartoon.play();
            _loc_2.dispose();
            _loc_5.dispose();
            dogFrameRate.addEventListener(TimerEvent.TIMER, dogmove);
            return;
        }// end function

        private function dogmove(param1:Event) : void
        {
            if (vectorCartoon.x - startX > distance)
            {
                direction = false;
            }// end if
            if (vectorCartoon.x - startX < 0)
            {
                direction = true;
            }// end if
            if (direction)
            {
                vectorCartoon.x = vectorCartoon.x + 5;
                vectorCartoon.gotoAndPlay(frame2);
                frame2++;
                if (frame2 == 20)
                {
                    frame2 = 11;
                }// end if
            }
            else
            {
                vectorCartoon.x = vectorCartoon.x - 5;
                vectorCartoon.gotoAndPlay(frame);
                frame++;
                if (frame == 10)
                {
                    frame = 1;
                }// end if
            }// end else if
            return;
        }// end function

    }
}
