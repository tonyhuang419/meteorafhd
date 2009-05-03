package module
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.farm.*;
    import hf.view.farm.farmAnimal.*;
    import hf.view.farm.land.*;
    import hf.view.farm.packBar.*;
    import hf.view.farm.pepsico.*;

    public class Farm extends Sprite
    {
        private var toolBar:ToolBar;
        private var farmlands:Farmlands;
        private var diyScene:DiyScene;
        private var packBar:PackBar;
        private var rollCollection:RollCollection;

        public function Farm()
        {
            addEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            return;
        }// end function

        private function onAddedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            init();
            return;
        }// end function

        private function takeHandler(param1:Event) : void
        {
            var _loc_2:* = new ViewEvent("takeComd");
            _loc_2.data = rollCollection.getBitmapData(1000, 768, 0.61);
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        private function packClickHandler(param1:Event) : void
        {
            if (packBar == null)
            {
                createPackBar();
            }// end if
            packBar.visible = !packBar.visible;
            if (packBar.visible)
            {
                Command.getInstance().farmCommand.getUserSeed();
            }// end if
            return;
        }// end function

        private function currentUserChange(param1:Event) : void
        {
            toolBar.showMe = MData.getInstance().mainData.me;
            return;
        }// end function

        private function init() : void
        {
            var _loc_4:String;
            var _loc_5:String;
            var _loc_6:String;
            var _loc_7:String;
            diyScene = new DiyScene();
            var _loc_1:* = MData.getInstance().mainData.items;
            if (_loc_1 != null)
            {
                _loc_4 = "";
                _loc_5 = "";
                _loc_6 = "";
                _loc_7 = "";
                if (_loc_1.hasOwnProperty("1"))
                {
                    _loc_4 = GetCropID.idToDiyUrl2(_loc_1["1"]["itemId"]);
                }// end if
                if (_loc_1.hasOwnProperty("2"))
                {
                    _loc_5 = GetCropID.idToDiyUrl2(_loc_1["2"]["itemId"]);
                }// end if
                if (_loc_1.hasOwnProperty("3"))
                {
                    _loc_6 = GetCropID.idToDiyUrl2(_loc_1["3"]["itemId"]);
                }// end if
                if (_loc_1.hasOwnProperty("4"))
                {
                    _loc_7 = GetCropID.idToDiyUrl2(_loc_1["4"]["itemId"]);
                }// end if
                diyScene.setDIYUrl(_loc_4, _loc_6, _loc_5, _loc_7);
            }// end if
            MData.getInstance().mainData.addEventListener(MainData.ITEMS, itemsChange);
            farmlands = new Farmlands();
            var _loc_2:* = new FarmAnimal();
            var _loc_3:* = new PotatoMachineLayer();
            rollCollection = new RollCollection();
            rollCollection.maxWidth = 1000;
            rollCollection.maxHeight = 768;
            rollCollection.width = stage.stageWidth;
            rollCollection.height = stage.stageHeight;
            rollCollection.top = 100;
            rollCollection.left = 50;
            rollCollection.reRoll();
            rollCollection.addChild(diyScene);
            rollCollection.addChild(_loc_2);
            rollCollection.addChild(_loc_3);
            rollCollection.addChild(farmlands);
            addChild(rollCollection);
            toolBar = new ToolBar();
            toolBar.width = 380;
            toolBar.height = 40;
            toolBar.x = (stage.stageWidth - toolBar.width) / 2;
            toolBar.y = stage.stageHeight - toolBar.height - 10;
            toolBar.addEventListener("packClick", packClickHandler, false, 0, true);
            addChild(toolBar);
            MData.getInstance().mainData.addEventListener(MainData.CURRENT_USER_CHANGE, currentUserChange, false, 0, true);
            ViewControl.getInstance().addEventListener("take", takeHandler, false, 0, true);
            return;
        }// end function

        private function createPackBar() : void
        {
            packBar = new PackBar();
            packBar.visible = false;
            packBar.x = (stage.stageWidth - packBar.width) / 2;
            packBar.y = toolBar.y - packBar.height - 10;
            addChild(packBar);
            return;
        }// end function

        private function itemsChange(param1:ModelEvent) : void
        {
            var _loc_3:String;
            var _loc_4:String;
            var _loc_5:String;
            var _loc_6:String;
            var _loc_2:* = MData.getInstance().mainData.items;
            if (_loc_2 != null)
            {
                _loc_3 = "";
                _loc_4 = "";
                _loc_5 = "";
                _loc_6 = "";
                if (_loc_2.hasOwnProperty("1"))
                {
                    _loc_3 = GetCropID.idToDiyUrl2(_loc_2["1"]["itemId"]);
                }// end if
                if (_loc_2.hasOwnProperty("2"))
                {
                    _loc_4 = GetCropID.idToDiyUrl2(_loc_2["2"]["itemId"]);
                }// end if
                if (_loc_2.hasOwnProperty("3"))
                {
                    _loc_5 = GetCropID.idToDiyUrl2(_loc_2["3"]["itemId"]);
                }// end if
                if (_loc_2.hasOwnProperty("4"))
                {
                    _loc_6 = GetCropID.idToDiyUrl2(_loc_2["4"]["itemId"]);
                }// end if
                diyScene.setDIYUrl(_loc_3, _loc_5, _loc_4, _loc_6);
            }// end if
            return;
        }// end function

    }
}
