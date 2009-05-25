package hf.view.farm
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.farm.toolBar.*;
    import hf.view.main.cursor.*;
    import hf.view.main.tip.*;

    public class ToolBar extends SimpleCollection
    {
        private var lib:MaterialLib;
        private var aToolPack:ToolPack;
        private var _showMe:Boolean = true;
        private var aToolHook:ToolHook;
        private var aToolInsect:ToolInsect;
        private var aToolHoe:ToolHoe;
        private var aToolWeed:ToolWeed;
        private var aToolTheft:ToolTheft;
        private var aToolHand:ToolHand;
        private var aToolArrow:ToolArrow;
        private var aToolPesticide:ToolPesticide;
        private var aToolWater:ToolWater;

        public function ToolBar()
        {
            lib = MaterialLib.getInstance();
            bg = lib.getMaterial("ToolBarBg") as Sprite;
            addTool();
            setPosition();
            showMe = true;
            addEventListener(Event.ADDED_TO_STAGE, onAddedToStage, false, 0, true);
            return;
        }// end function

        private function keyUpHandler(param1:KeyboardEvent) : void
        {
            if (param1.target is TextField)
            {
                return;
            }// end if
            switch(param1.keyCode)
            {
                case 81:
                {
                    Cursor.setCursor("CursorWater");
                    break;
                }// end case
                case 87:
                {
                    Cursor.setCursor("CursorHook");
                    break;
                }// end case
                case 69:
                {
                    Cursor.setCursor("CursorPesticide");
                    break;
                }// end case
                case 82:
                {
                    Cursor.setCursor("CursorHand");
                    break;
                }// end case
                default:
                {
                    break;
                }// end default
            }// end switch
            return;
        }// end function

        private function toolClick(param1:MouseEvent) : void
        {
            if (param1.currentTarget == aToolArrow)
            {
                Cursor.setCursor("CursorArrow");
            }
            else if (param1.currentTarget == aToolWeed)
            {
                if (Cursor.name != "CursorWeed")
                {
                    Cursor.setCursor("CursorWeed");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }
            else if (param1.currentTarget == aToolInsect)
            {
                if (Cursor.name != "CursorInsect")
                {
                    Cursor.setCursor("CursorInsect");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }
            else if (param1.currentTarget == aToolHook)
            {
                if (Cursor.name != "CursorHook")
                {
                    Cursor.setCursor("CursorHook");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }
            else if (param1.currentTarget == aToolHoe)
            {
                if (Cursor.name != "CursorHoe")
                {
                    Cursor.setCursor("CursorHoe");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }
            else if (param1.currentTarget == aToolPesticide)
            {
                if (Cursor.name != "CursorPesticide")
                {
                    Cursor.setCursor("CursorPesticide");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }
            else if (param1.currentTarget == aToolHand)
            {
                if (Cursor.name != "CursorHand")
                {
                    Cursor.setCursor("CursorHand");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }
            else if (param1.currentTarget == aToolWater)
            {
                if (Cursor.name != "CursorWater")
                {
                    Cursor.setCursor("CursorWater");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }
            else if (param1.currentTarget == aToolPack)
            {
                Cursor.setCursor("CursorArrow");
                dispatchEvent(new Event("packClick"));
            }
            else if (param1.currentTarget == aToolTheft)
            {
                if (Cursor.name != "CursorHand")
                {
                    Cursor.setCursor("CursorHand");
                }
                else
                {
                    Cursor.setCursor("CursorArrow");
                }// end else if
            }// end else if
            param1.stopPropagation();
            return;
        }// end function

        private function toolRollOut(param1:MouseEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function onAddedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            stage.addEventListener(KeyboardEvent.KEY_UP, keyUpHandler, false, 0, true);
            return;
        }// end function

        private function toolRollOver(param1:MouseEvent) : void
        {
            TipControl.show("MouseTip", (param1.currentTarget as ToolBase).tipText);
            return;
        }// end function

        public function set showMe(param1:Boolean) : void
        {
            _showMe = param1;
            aToolInsect.visible = !param1;
            aToolWeed.visible = !param1;
            aToolTheft.visible = !param1;
            aToolHand.visible = param1;
            aToolHoe.visible = param1;
            if (OpenControl.open("pack") == false)
            {
                aToolPack.visible = param1;
            }// end if
            setPosition();
            return;
        }// end function

        private function setPosition() : void
        {
            var _loc_2:int;
            var _loc_6:DisplayObject;
            var _loc_1:int;
            _loc_2 = 52;
            var _loc_3:int;
            var _loc_4:Array;
            var _loc_5:int;
            while (_loc_5 < _loc_4.length)
            {
                // label
                _loc_6 = _loc_4[_loc_5] as DisplayObject;
                if (_loc_6.visible)
                {
                    _loc_6.x = _loc_1;
                    _loc_6.y = _loc_3;
                    _loc_1 = _loc_1 + _loc_2;
                }// end if
                _loc_5++;
            }// end while
            width = _loc_1;
            if (stage != null)
            {
                x = (stage.stageWidth - width) / 2;
            }// end if
            return;
        }// end function

        private function addTool() : void
        {
            aToolArrow = new ToolArrow();
            aToolArrow.tipText = Language.L["可拖动屏幕"];
            aToolArrow.addEventListener(MouseEvent.CLICK, toolClick);
            aToolArrow.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolArrow.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolArrow);
            aToolPack = new ToolPack();
            aToolPack.tipText = Language.L["已经购买的物品"];
            aToolPack.addEventListener(MouseEvent.CLICK, toolClick);
            aToolPack.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolPack.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolPack);
            aToolWeed = new ToolWeed();
            aToolWeed.tipText = Language.L["在地里放杂草"];
            aToolWeed.addEventListener(MouseEvent.CLICK, toolClick);
            aToolWeed.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolWeed.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolWeed);
            aToolInsect = new ToolInsect();
            aToolInsect.tipText = Language.L["在地里放害虫"];
            aToolInsect.addEventListener(MouseEvent.CLICK, toolClick);
            aToolInsect.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolInsect.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolInsect);
            aToolHook = new ToolHook();
            aToolHook.tipText = Language.L["清除地里的杂草"];
            aToolHook.addEventListener(MouseEvent.CLICK, toolClick);
            aToolHook.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolHook.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolHook);
            aToolHoe = new ToolHoe();
            aToolHoe.tipText = Language.L["可用来翻地"];
            aToolHoe.addEventListener(MouseEvent.CLICK, toolClick);
            aToolHoe.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolHoe.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolHoe);
            aToolPesticide = new ToolPesticide();
            aToolPesticide.tipText = Language.L["清除地里的害虫"];
            aToolPesticide.addEventListener(MouseEvent.CLICK, toolClick);
            aToolPesticide.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolPesticide.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolPesticide);
            aToolHand = new ToolHand();
            aToolHand.tipText = Language.L["用来收获果实"];
            aToolHand.addEventListener(MouseEvent.CLICK, toolClick);
            aToolHand.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolHand.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolHand);
            aToolTheft = new ToolTheft();
            aToolTheft.tipText = Language.L["偷窃好友的果实"];
            aToolTheft.addEventListener(MouseEvent.CLICK, toolClick);
            aToolTheft.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolTheft.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolTheft);
            aToolWater = new ToolWater();
            aToolWater.tipText = Language.L["用来浇水"];
            aToolWater.addEventListener(MouseEvent.CLICK, toolClick);
            aToolWater.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            aToolWater.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(aToolWater);
            return;
        }// end function

        public function get showMe() : Boolean
        {
            return _showMe;
        }// end function

    }
}
