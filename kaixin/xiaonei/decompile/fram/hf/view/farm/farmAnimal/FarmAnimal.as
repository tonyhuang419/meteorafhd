package hf.view.farm.farmAnimal
{
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.cursor.*;

    public class FarmAnimal extends Sprite
    {
        private var mainData:MainData;

        public function FarmAnimal()
        {
            mainData = MData.getInstance().mainData;
            var _loc_1:* = mainData.dogData;
            var _loc_2:Boolean;
            var _loc_3:Boolean;
            if (mainData.dogData != null)
            {
                _loc_2 = true;
            }// end if
            if (mainData.dogDizzyState != null)
            {
                _loc_3 = true;
            }// end if
            mainData.addEventListener(MainData.DOG_DATA, initDog);
            mainData.addEventListener(MainData.DOG_DIZZY, setdogDizzyFn);
            if (!mainData.hasEventListener(MainData.DOG_FOOD_DATA))
            {
                mainData.addEventListener(MainData.DOG_FOOD_DATA, openFoodWinFn);
            }// end if
            if (_loc_2)
            {
                mainData.dogData = mainData.dogData;
                if (_loc_3)
                {
                    mainData.dogDizzyState = mainData.dogDizzyState;
                }// end if
            }// end if
            return;
        }// end function

        private function setdogDizzyFn(param1:Event) : void
        {
            var _loc_5:DizzyDog;
            if (getChildByName("dizzyDog") != null)
            {
                removeChild(getChildByName("dizzyDog"));
            }// end if
            if (mainData.dogData["dogId"] == "0")
            {
                return;
            }// end if
            var _loc_2:* = mainData.dogDizzyState;
            var _loc_3:* = getChildByName("greatDog");
            var _loc_4:* = _loc_2["dogUnWorkTime"] - _loc_2["requestTime"];
            if (_loc_3 != null)
            {
                if (_loc_4 > 0)
                {
                    _loc_3.visible = false;
                    _loc_5 = new DizzyDog(_loc_4);
                    _loc_5.name = "dizzyDog";
                    addChild(_loc_5);
                }
                else
                {
                    _loc_3.visible = true;
                }// end if
            }// end else if
            return;
        }// end function

        private function beatDog(param1:MouseEvent) : void
        {
            var _loc_2:* = Cursor.name;
            var _loc_3:* = Cursor.cursorArgument;
            if (Cursor.name == "CursorHitDogStick")
            {
                Command.getInstance().farmCommand.beatDog(Cursor.cursorArgument);
            }// end if
            return;
        }// end function

        private function initDog(param1:Event) : void
        {
            var _loc_2:DogSalver;
            var _loc_3:Dog;
            var _loc_4:DisplayObject;
            var _loc_5:DisplayObject;
            if (mainData.dogData["dogId"] == "1")
            {
                if (getChildByName("greatSalver") == null)
                {
                    _loc_2 = new DogSalver();
                    _loc_2.name = "greatSalver";
                    addChild(_loc_2);
                }
                else
                {
                    _loc_2 = getChildByName("greatSalver") as DogSalver;
                }// end else if
                if (mainData.dogData.isHungry == 0)
                {
                    _loc_2.food = false;
                }
                else
                {
                    _loc_2.food = true;
                }// end else if
                if (getChildByName("greatDog") == null)
                {
                    _loc_3 = new Dog();
                    _loc_3.name = "greatDog";
                    addChild(_loc_3);
                    _loc_3.addEventListener(MouseEvent.CLICK, beatDog);
                }// end if
            }
            else
            {
                if (getChildByName("greatDog") != null)
                {
                    _loc_4 = removeChild(getChildByName("greatDog"));
                    _loc_4 = null;
                }// end if
                if (getChildByName("greatSalver") != null)
                {
                    _loc_5 = removeChild(getChildByName("greatSalver"));
                    _loc_5 = null;
                }// end if
            }// end else if
            if (getChildByName("dizzyDog") != null)
            {
                removeChild(getChildByName("dizzyDog"));
            }// end if
            return;
        }// end function

        private function openFoodWinFn(param1:Event) : void
        {
            var _loc_2:DogFoodWindow;
            if (MData.getInstance().mainData.me)
            {
                _loc_2 = new DogFoodWindow();
                WControl.open(_loc_2);
            }// end if
            return;
        }// end function

    }
}
