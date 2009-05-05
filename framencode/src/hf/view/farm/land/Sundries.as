package hf.view.farm.land
{
    import flash.display.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.tip.*;

    public class Sundries extends Sprite
    {
        private var bigInsectId:String;
        private var lib:MaterialLib;
        private var insectSprite:Sprite;
        private var _insectPosition:Array;
        private var weedSprite:Sprite;
        private var _weedPosition:Array;
        private var bigInsectSprite:Sprite;

        public function Sundries()
        {
            _weedPosition = [[0, 10], [45, 20], [120, 10]];
            _insectPosition = [[0, 10], [45, 20], [120, 10]];
            lib = MaterialLib.getInstance();
            return;
        }// end function

        public function set weed(param1:int) : void
        {
            var _loc_2:Sprite;
            if (weedSprite == null && param1 != 0)
            {
                weedSprite = new Sprite();
                addChild(weedSprite);
            }// end if
            if (weedSprite == null)
            {
                return;
            }// end if
            if (weedSprite.numChildren > param1)
            {
                while (weedSprite.numChildren > param1)
                {
                    // label
                    weedSprite.removeChildAt(weedSprite.numChildren--);
                }// end while
            }
            else
            {
                while (weedSprite.numChildren < param1)
                {
                    // label
                    _loc_2 = lib.getMaterial("Weed") as Sprite;
                    _loc_2.x = _weedPosition[weedSprite.numChildren][0];
                    _loc_2.y = _weedPosition[weedSprite.numChildren][1];
                    weedSprite.addChild(_loc_2);
                }// end while
            }// end else if
            return;
        }// end function

        public function set insectPosition(param1:Array) : void
        {
            _insectPosition = param1;
            setInsectPosition();
            return;
        }// end function

        public function get insect() : int
        {
            return 0;
        }// end function

        private function setInsectPosition() : void
        {
            if (insectSprite == null && bigInsectSprite == null)
            {
                return;
            }// end if
            var _loc_1:int;
            if (insectSprite != null)
            {
                while (_loc_1 < insectSprite.numChildren)
                {
                    // label
                    insectSprite.getChildAt(_loc_1).x = insectPosition[_loc_1][0];
                    insectSprite.getChildAt(_loc_1).y = insectPosition[_loc_1][1];
                    _loc_1++;
                }// end while
            }// end if
            if (bigInsectSprite != null)
            {
                bigInsectSprite.x = 70;
                bigInsectSprite.y = -25;
            }// end if
            return;
        }// end function

        public function get bigInsect() : String
        {
            return bigInsectId;
        }// end function

        public function get weed() : int
        {
            if (weedSprite == null)
            {
                return 0;
            }// end if
            return weedSprite.numChildren;
        }// end function

        public function set insect(param1:int) : void
        {
            var _loc_2:Sprite;
            if (insectSprite == null && param1 != 0)
            {
                insectSprite = new Sprite();
                addChild(insectSprite);
            }// end if
            if (insectSprite == null)
            {
                return;
            }// end if
            if (insectSprite.numChildren > param1)
            {
                while (insectSprite.numChildren > param1)
                {
                    // label
                    insectSprite.removeChildAt(insectSprite.numChildren--);
                }// end while
            }
            else
            {
                while (insectSprite.numChildren < param1)
                {
                    // label
                    _loc_2 = lib.getMaterial("Insect") as Sprite;
                    insectSprite.addChild(_loc_2);
                }// end while
            }// end else if
            setInsectPosition();
            return;
        }// end function

        public function get insectPosition() : Array
        {
            return _insectPosition;
        }// end function

        public function set bigInsect(param1:String) : void
        {
            var _loc_2:MovieClip;
            var _loc_3:MovieClip;
            bigInsectId = param1;
            if (bigInsectSprite == null)
            {
                bigInsectSprite = new Sprite();
                addChild(bigInsectSprite);
            }// end if
            if (bigInsectSprite == null)
            {
                return;
            }// end if
            if (bigInsectSprite.numChildren > 0)
            {
                _loc_2 = bigInsectSprite.getChildAt(0) as MovieClip;
                if (param1 == "0" || param1 == "")
                {
                    TipControl.hide();
                    if (_loc_2.name == "BigBug" && MData.getInstance().mainData.killBigInsect)
                    {
                        MData.getInstance().mainData.killBigInsect = false;
                        _loc_2.ex = MData.getInstance().mainData.killBigInsectExp;
                        _loc_2.zh = Language.lang == Language.CN;
                        _loc_2.gotoAndPlay("kill");
                    }
                    else
                    {
                        bigInsectSprite.removeChild(_loc_2);
                    }// end else if
                }
                else
                {
                    bigInsectSprite.removeChild(_loc_2);
                    _loc_2 = null;
                }// end if
            }// end else if
            if (param1 == "1")
            {
                _loc_3 = lib.getMaterial("BigBug") as MovieClip;
                _loc_3.name = "BigBug";
                _loc_3.x = 0;
                _loc_3.y = 0;
                bigInsectSprite.addChild(_loc_3);
            }// end if
            setInsectPosition();
            return;
        }// end function

    }
}
