package hf.view.main.window.dial
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;

    public class Dial extends BaseWindow
    {
        private var fbText:TextField;
        private var randomPosition:int = 1;
        private var fbButton:LipiButton;
        private var stepAngeleAdd:Boolean = true;
        private var timsText:TextField;
        private var goldText:TextField;
        private var stepAngeleSub:Boolean = false;
        private var retIndex:int = 2;
        private var goldButton:LipiButton;
        private var angeleArray:Array;
        private var FBDail:Sprite;
        private var buutonFbDial:Sprite;
        private var noGold:TextField;
        private var buttonGoldDial:Sprite;
        private var goldTimer:Timer;
        private var fbDial:MovieClip;
        private var baseAngele:Number;
        private var bitmapBuffer:BitmapData;
        private var noFB:TextField;
        private var partAngele:int = 45;
        private var goldDail:Sprite;
        private var goldDial:MovieClip;
        private var stepAngele:int = 5;
        private var dailTurnClass:Class;

        public function Dial()
        {
            angeleArray = [[0, 45], [45, 90], [90, 135], [135, 180], [-180, -135], [-135, -90], [-90, -45], [-45, 0]];
            width = 600;
            height = 400;
            title = Language.L["dialTitle"];
            windowName = "dial";
            mode = true;
            return;
        }// end function

        private function gainReturn(param1:Event) : void
        {
            var _loc_2:* = new Gain();
            WControl.open(_loc_2);
            return;
        }// end function

        override public function init() : void
        {
            var _loc_1:Sprite;
            _loc_1 = MaterialLib.getInstance().getMaterial("DialExpendMes") as Sprite;
            var _loc_2:* = MaterialLib.getInstance().getMaterial("DailStatic") as Sprite;
            var _loc_3:* = MaterialLib.getInstance().getMaterial("DailStatic") as Sprite;
            var _loc_4:* = MaterialLib.getInstance().getMaterial("GoldDailPoint") as Sprite;
            var _loc_5:* = MaterialLib.getInstance().getMaterial("FBDailPoint") as Sprite;
            dailTurnClass = MaterialLib.getInstance().getClass("DailTurn") as Class;
            var _loc_6:* = new BitmapData(200, 200, true, 0);
            new BitmapData(200, 200, true, 0).draw(_loc_2);
            var _loc_7:* = new Bitmap(_loc_6, "auto", true);
            addChild(_loc_1);
            _loc_1.x = 30;
            _loc_1.y = 38;
            goldText = new TextField();
            goldText.defaultTextFormat = new TextFormat("fontForte", 16, 16737792, true);
            goldText.embedFonts = true;
            goldText.selectable = false;
            goldText.width = 50;
            goldText.height = 23;
            goldText.x = 225;
            goldText.y = 38;
            goldText.text = "1000";
            addChild(goldText);
            fbText = new TextField();
            fbText.defaultTextFormat = new TextFormat("fontForte", 16, 26367, true);
            fbText.embedFonts = true;
            fbText.selectable = false;
            fbText.width = 50;
            fbText.height = 23;
            fbText.x = 530;
            fbText.y = 38;
            fbText.text = "1";
            addChild(fbText);
            timsText = new TextField();
            timsText.defaultTextFormat = new TextFormat("Tahoma", 12, 13369344, true);
            timsText.selectable = false;
            timsText.width = 200;
            timsText.height = 23;
            timsText.x = 60;
            timsText.y = 63;
            timsText.text = "你今天还剩下10次游戏机会";
            addChild(timsText);
            goldDail = new Sprite();
            goldDail.addChild(_loc_7);
            _loc_7.x = -100;
            _loc_7.y = -100;
            goldDail.x = 150;
            goldDail.y = 190;
            addChild(goldDail);
            FBDail = new Sprite();
            FBDail.addChild(_loc_3);
            _loc_3.x = -100;
            _loc_3.y = -100;
            FBDail.x = 450;
            FBDail.y = 190;
            addChild(FBDail);
            _loc_4.x = 139;
            _loc_4.y = 225;
            addChild(_loc_4);
            _loc_5.x = 439;
            _loc_5.y = 225;
            addChild(_loc_5);
            goldButton = new LipiButton();
            goldButton.width = 80;
            goldButton.height = 25;
            goldButton.name = "goldButton";
            goldButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            goldButton.label = "开始游戏";
            goldButton.textColor = 16777215;
            goldButton.bgAlpha = 0;
            goldButton.x = 110;
            goldButton.y = 340;
            addChild(goldButton);
            goldButton.addEventListener(MouseEvent.CLICK, goldDialPlay);
            fbButton = new LipiButton();
            fbButton.width = 80;
            fbButton.height = 25;
            fbButton.name = "fbButton";
            fbButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            fbButton.label = "开始游戏";
            fbButton.textColor = 16777215;
            fbButton.bgAlpha = 0;
            fbButton.x = 410;
            fbButton.y = 340;
            addChild(fbButton);
            fbButton.addEventListener(MouseEvent.CLICK, fbDialPlay);
            noGold = new TextField();
            noGold.defaultTextFormat = new TextFormat("Tahoma", 12, 13369344, true);
            noGold.selectable = false;
            noGold.width = 200;
            noGold.height = 23;
            noGold.x = 40;
            noGold.y = 370;
            noGold.text = "你的金币余额不足，无法开始游戏";
            addChild(noGold);
            noFB = new TextField();
            noFB.defaultTextFormat = new TextFormat("Tahoma", 12, 13369344, true);
            noFB.selectable = false;
            noFB.width = 200;
            noFB.height = 23;
            noFB.x = 340;
            noFB.y = 370;
            noFB.text = "你的F币余额不足，无法开始游戏";
            addChild(noFB);
            return;
        }// end function

        private function goldDialRun(param1:TimerEvent) : void
        {
            goldDail.rotation = goldDail.rotation + stepAngele;
            if (stepAngeleAdd)
            {
                stepAngele = stepAngele + 7.2;
            }// end if
            if (stepAngeleSub)
            {
                stepAngele = stepAngele - 7.2;
            }// end if
            if (stepAngele < 30 && !stepAngeleAdd)
            {
                stepAngeleSub = false;
                baseAngele = stepAngele;
                stopToRightPosition();
            }// end if
            param1.updateAfterEvent();
            return;
        }// end function

        private function fbDialPlay(param1:MouseEvent) : void
        {
            return;
        }// end function

        private function goldDialResultRet(param1:Event) : void
        {
            return;
        }// end function

        private function goldDialPlay(param1:MouseEvent) : void
        {
            randomPosition = int(Math.random() * 5) + 4;
            trace("randomPosition::::::::::::::::::::  " + randomPosition);
            goldTimer = new Timer(40);
            goldTimer.addEventListener(TimerEvent.TIMER, goldDialRun);
            goldTimer.start();
            goldButton.removeEventListener(MouseEvent.CLICK, goldDialPlay);
            goldButton.enable = false;
            setTimeout(setGoldFase, 2000);
            setTimeout(removeGoldFase, 4000);
            return;
        }// end function

        private function stopToRightPosition() : void
        {
            if (goldDail.rotation > angeleArray[retIndex][0] && goldDail.rotation < angeleArray[retIndex][1])
            {
                stepAngele = stepAngele - (stepAngele + 10) / baseAngele * 5;
                if (stepAngele > 10)
                {
                    return;
                }// end if
                goldButton.addEventListener(MouseEvent.CLICK, goldDialPlay);
                goldButton.enable = true;
                stepAngeleAdd = true;
                goldTimer.stop();
            }// end if
            return;
        }// end function

        private function setGoldFase() : void
        {
            var _loc_1:BitmapData;
            var _loc_2:Bitmap;
            if (goldDail.getChildByName("fast") == null)
            {
                _loc_1 = new dailTurnClass(202, 202);
                _loc_2 = new Bitmap(_loc_1);
                _loc_2.name = "fast";
                _loc_2.x = -101;
                _loc_2.y = -101;
                goldDail.addChild(_loc_2);
            }// end if
            stepAngeleAdd = false;
            return;
        }// end function

        private function removeGoldFase() : void
        {
            if (goldDail.getChildByName("fast") != null)
            {
                goldDail.removeChild(goldDail.getChildByName("fast"));
                stepAngeleSub = true;
            }// end if
            return;
        }// end function

    }
}
