package module
{
    import com.minutes.ui.control.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.FBridge.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.*;
    import hf.view.main.OutSwf.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.cursor.*;
    import hf.view.main.floating.*;
    import hf.view.main.friend.*;
    import hf.view.main.head.*;
    import hf.view.main.leftInfo.*;
    import hf.view.main.shield.*;
    import hf.view.main.tip.*;

    public class Main extends Sprite
    {
        private var initLoading:InitLoading;
        private var addComp:Boolean = false;
        private var dataComp:Boolean = false;
        private var moduleComp:Boolean = false;
        private var FONT_FORTE:Class;
        private var moduleContent:ModuleContent;

        public function Main()
        {
            FONT_FORTE = Main_FONT_FORTE;
            Font.registerFont(FONT_FORTE);
            init();
            return;
        }// end function

        private function addCursorLayer() : void
        {
            var _loc_1:* = new CursorLayer();
            addChild(_loc_1);
            return;
        }// end function

        private function addTopLayer() : void
        {
            addChild(new UserChanging());
            return;
        }// end function

        private function addTask() : void
        {
            addChild(new TaskLayer());
            return;
        }// end function

        private function addFloatingLayer() : void
        {
            var _loc_1:* = new FloatingLayer();
            addChild(_loc_1);
            return;
        }// end function

        private function init() : void
        {
            setDefaultSkin();
            setRequestUrl();
            registerWindow();
            moduleContent = new ModuleContent();
            moduleContent.addEventListener(ViewEvent.MODULE_COMP, moduleLoadComp);
            addEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            var _loc_1:* = new Timer(120000);
            _loc_1.addEventListener(TimerEvent.TIMER, gc);
            _loc_1.start();
            return;
        }// end function

        private function levelReward(param1:Event) : void
        {
            var _loc_3:GiftWindow;
            var _loc_2:* = MData.getInstance().mainData.levelReward;
            if (_loc_2 != null)
            {
                _loc_3 = new GiftWindow();
                _loc_3.giftData = _loc_2;
                WControl.open(_loc_3);
            }// end if
            return;
        }// end function

        private function executeRun() : void
        {
            MData.getInstance().mainData.addEventListener(MainData.RUN_COMP, runCompHandler);
            MData.getInstance().mainData.addEventListener(MainData.SESSION_TIMEOUT, sessionTimeout);
            MData.getInstance().mainData.addEventListener(MainData.RUN_ERROR, runError);
            Command.getInstance().mainCommand.run();
            return;
        }// end function

        private function setDefaultSkin() : void
        {
            LipiDefaultSkin.WindowBg = MaterialLib.getInstance().getClass("WindowBg");
            LipiDefaultSkin.WindowCloseBg = MaterialLib.getInstance().getClass("CloseButton");
            LipiScrollBarSkin.BarIcon = MaterialLib.getInstance().getClass("ScrollBarIcon");
            LipiScrollBarSkin.BarSkin = MaterialLib.getInstance().getClass("ScrollBarSkin");
            LipiScrollBarSkin.UpSkin = MaterialLib.getInstance().getClass("ScrollUpSkin");
            LipiScrollBarSkin.DownSkin = MaterialLib.getInstance().getClass("ScrollDownSkin");
            NUmbericStepperSkin.BgSkin = MaterialLib.getInstance().getClass("StepperBg");
            NUmbericStepperSkin.IncSkin = MaterialLib.getInstance().getClass("RightButton");
            NUmbericStepperSkin.DecSkin = MaterialLib.getInstance().getClass("LeftButton");
            return;
        }// end function

        private function addLeftInfo() : void
        {
            var _loc_1:* = new LeftInfo();
            _loc_1.y = 80;
            _loc_1.x = 15;
            addChild(_loc_1);
            return;
        }// end function

        private function gameStart() : void
        {
            if (moduleComp && dataComp && addComp)
            {
                addChild(moduleContent);
                addHead();
                addLeftInfo();
                addTask();
                addFriend();
                addWindowLayer();
                addTipLayer();
                addFloatingLayer();
                addCursorLayer();
                addTopLayer();
                Cursor.setCursor("CursorArrow");
                Cursor.useSystem();
                MData.getInstance().mainData.addEventListener(MainData.WELCOME, welcome);
                welcome();
                removeChild(initLoading);
                MData.getInstance().mainData.addEventListener(MainData.LEVEL_REWARD, levelReward, false, 0, true);
                var _loc_1:Boolean;
                addComp = false;
                var _loc_1:* = _loc_1;
                dataComp = _loc_1;
                moduleComp = _loc_1;
            }// end if
            return;
        }// end function

        private function moduleLoadComp(param1:ViewEvent) : void
        {
            moduleComp = true;
            gameStart();
            return;
        }// end function

        private function welcome(param1:Event = null) : void
        {
            var _loc_2:HelpWindow;
            if (MData.getInstance().mainData.welcome == true)
            {
                _loc_2 = new HelpWindow();
                addChild(_loc_2);
                MData.getInstance().mainData.removeEventListener(MainData.WELCOME, welcome);
            }// end if
            return;
        }// end function

        private function gc(param1:TimerEvent) : void
        {
            var e:* = param1;
            try
            {
                new LocalConnection().connect("MoonSpirit");
                new LocalConnection().connect("MoonSpirit");
            }// end try
            catch (error:Error)
            {
            }// end catch
            return;
        }// end function

        private function onTimer(param1:Event) : void
        {
            return;
        }// end function

        private function addWindowLayer() : void
        {
            var _loc_1:* = new WindowLayer();
            addChild(_loc_1);
            return;
        }// end function

        private function addHead() : void
        {
            var _loc_1:* = new HeadBar();
            _loc_1.width = stage.stageWidth;
            _loc_1.height = 68;
            addChild(_loc_1);
            return;
        }// end function

        private function setRequestUrl() : void
        {
            FRequest.getInstance().httpUrl = INI.getInstance().getPostUrl();
            return;
        }// end function

        private function sessionTimeout(param1:Event) : void
        {
            WindowClassLib.register("AlertWindow", AlertWindow);
            var _loc_2:* = INI.getInstance().data.version.@loginurl;
            var _loc_3:String;
            WControl.openForName("AlertWindow", {type:"error", text:MData.getInstance().mainData.sessionTimeout, gotourl:_loc_2, target:_loc_3});
            return;
        }// end function

        private function addFriend() : void
        {
            var _loc_1:* = new FriendLayer();
            addChild(_loc_1);
            return;
        }// end function

        private function runCompHandler(param1:Event) : void
        {
            dataComp = true;
            gameStart();
            return;
        }// end function

        private function onAddedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            executeRun();
            initLoading = new InitLoading();
            initLoading.x = (stage.stageWidth - initLoading.width) / 2;
            initLoading.y = (stage.stageHeight - initLoading.height) / 2;
            addChild(initLoading);
            addComp = true;
            gameStart();
            return;
        }// end function

        private function addTipLayer() : void
        {
            var _loc_1:* = new TipLayer();
            addChild(_loc_1);
            return;
        }// end function

        private function registerWindow() : void
        {
            WindowClassLib.register("AlertWindow", AlertWindow);
            return;
        }// end function

        private function runError(param1:Event) : void
        {
            if (initLoading != null)
            {
                initLoading.errorText = MData.getInstance().mainData.runError;
            }// end if
            return;
        }// end function

    }
}
