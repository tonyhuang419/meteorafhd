package hf.view.farm.farmAnimal
{
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.main.cursor.*;

    public class DogSalver extends Sprite
    {
        private var mainData:MainData;
        private var salverY:int = 200;
        private var salverX:int = 400;

        public function DogSalver()
        {
            mainData = MData.getInstance().mainData;
            return;
        }// end function

        private function addFoodFn(param1:Event) : void
        {
            if (MData.getInstance().mainData.me)
            {
                Command.getInstance().mainCommand.dogFood();
            }// end if
            return;
        }// end function

        private function removeSalver() : void
        {
            var _loc_3:DisplayObject;
            var _loc_1:* = this.numChildren;
            var _loc_2:int;
            while (_loc_2 < _loc_1)
            {
                // label
                _loc_3 = this.removeChildAt(0);
                _loc_3 = null;
                _loc_2++;
            }// end while
            return;
        }// end function

        private function movhand(param1:MouseEvent) : void
        {
            Cursor.useSystem(false);
            return;
        }// end function

        public function set food(param1:Boolean) : void
        {
            var _loc_2:Sprite;
            var _loc_3:Sprite;
            removeSalver();
            if (param1)
            {
                _loc_2 = MaterialLib.getInstance().getMaterial("DogFoodNull") as Sprite;
                addChild(_loc_2);
                _loc_2.x = salverX;
                _loc_2.y = salverY;
                _loc_2.scaleX = 0.5;
                _loc_2.scaleY = 0.5;
                if (MData.getInstance().mainData.me)
                {
                    _loc_2.addEventListener(MouseEvent.ROLL_OVER, hand);
                    _loc_2.addEventListener(MouseEvent.ROLL_OUT, movhand);
                    _loc_2.addEventListener(MouseEvent.CLICK, addFoodFn);
                    _loc_2.buttonMode = true;
                    _loc_2.mouseEnabled = true;
                }
                else
                {
                    _loc_2.buttonMode = false;
                    _loc_2.mouseEnabled = false;
                }// end else if
            }
            else
            {
                _loc_3 = MaterialLib.getInstance().getMaterial("DogFood") as Sprite;
                addChild(_loc_3);
                _loc_3.x = salverX;
                _loc_3.y = salverY;
                _loc_3.scaleX = 0.5;
                _loc_3.scaleY = 0.5;
                if (MData.getInstance().mainData.me)
                {
                    _loc_3.addEventListener(MouseEvent.ROLL_OVER, hand);
                    _loc_3.addEventListener(MouseEvent.ROLL_OUT, movhand);
                    _loc_3.addEventListener(MouseEvent.CLICK, addFoodFn);
                    _loc_3.buttonMode = true;
                    _loc_3.mouseEnabled = true;
                }
                else
                {
                    _loc_3.buttonMode = false;
                    _loc_3.mouseEnabled = false;
                }// end else if
            }// end else if
            return;
        }// end function

        private function hand(param1:MouseEvent) : void
        {
            Cursor.useSystem(true);
            return;
        }// end function

    }
}
