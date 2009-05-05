package hf.view.main.head
{
    import flash.display.*;
    import flash.events.*;
    import flash.ui.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.farm.toolBar.*;
    import hf.view.main.tip.*;

    public class HeadToolBar extends Sprite
    {
        private var buttonFarm:ButtonFarm;
        private var buttonDial:ButtonDial;
        private var buttonShop:ButtonShop;
        private var buttonWarehouse:ButtonWarehouse;
        private var buttonDecorate:ButtonDecorate;

        public function HeadToolBar()
        {
            buttonFarm = new ButtonFarm();
            var _loc_1:* = new ContextMenuItem("Build 04015B");
            _loc_1.enabled = false;
            var _loc_2:* = new ContextMenu();
            _loc_2.customItems = [_loc_1];
            _loc_2.hideBuiltInItems();
            buttonFarm.contextMenu = _loc_2;
            buttonFarm.tipText = Language.L["我的农场"];
            buttonFarm.name = "farm";
            buttonFarm.addEventListener(MouseEvent.CLICK, buttonClickHandler);
            buttonFarm.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            buttonFarm.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(buttonFarm);
            buttonWarehouse = new ButtonWarehouse();
            buttonWarehouse.tipText = Language.L["仓库（收获的果子会在这里）"];
            buttonWarehouse.name = "warehouse";
            buttonWarehouse.addEventListener(MouseEvent.CLICK, buttonClickHandler);
            buttonWarehouse.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            buttonWarehouse.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(buttonWarehouse);
            buttonShop = new ButtonShop();
            buttonShop.tipText = Language.L["开心商店（来这里买种子）"];
            buttonShop.name = "shop";
            buttonShop.addEventListener(MouseEvent.CLICK, buttonClickHandler);
            buttonShop.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            buttonShop.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(buttonShop);
            buttonDecorate = new ButtonDecorate();
            buttonDecorate.tipText = Language.L["装饰农场"];
            buttonDecorate.name = "decorate";
            buttonDecorate.addEventListener(MouseEvent.CLICK, buttonClickHandler);
            buttonDecorate.addEventListener(MouseEvent.ROLL_OVER, toolRollOver);
            buttonDecorate.addEventListener(MouseEvent.ROLL_OUT, toolRollOut);
            addChild(buttonDecorate);
            return;
        }// end function

        public function setPosition(param1:int = 0) : void
        {
            var _loc_7:DisplayObject;
            var _loc_2:int;
            var _loc_3:* = param1;
            var _loc_4:int;
            var _loc_5:* = this.numChildren;
            var _loc_6:int;
            while (_loc_6 < _loc_5)
            {
                // label
                _loc_7 = this.getChildAt(_loc_6);
                _loc_7.x = _loc_4;
                _loc_7.y = _loc_2;
                _loc_4 = _loc_4 + _loc_7.width + _loc_3;
                _loc_6++;
            }// end while
            return;
        }// end function

        private function buttonClickHandler(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_CLICK);
            _loc_2.data = {target:param1.currentTarget, name:param1.currentTarget.name};
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function toolRollOver(param1:MouseEvent) : void
        {
            TipControl.show("MouseTip", (param1.currentTarget as ToolBase).tipText);
            return;
        }// end function

        private function toolRollOut(param1:MouseEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

    }
}
