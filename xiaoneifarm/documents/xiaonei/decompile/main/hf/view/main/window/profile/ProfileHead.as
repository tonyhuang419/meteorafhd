package hf.view.main.window.profile
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;

    public class ProfileHead extends Sprite
    {
        private var gradeText:TextField;
        private var photoLoader:Loader;
        private var expText:TextField;
        private var _homePage:String = "";
        private var homeLink:LipiButton;
        private var assets1Text:TextField;
        private var assets2Text:TextField;
        private var nameText:TextField;

        public function ProfileHead()
        {
            var _loc_3:TextField;
            var _loc_7:MoneyIcon;
            var _loc_1:* = new Sprite();
            _loc_1.graphics.beginFill(15658734);
            _loc_1.graphics.drawRect(0, 0, 100, 100);
            _loc_1.graphics.endFill();
            _loc_1.x = 39;
            _loc_1.y = 16;
            addChild(_loc_1);
            photoLoader = new Loader();
            photoLoader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComp);
            photoLoader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioError);
            _loc_1.addChild(photoLoader);
            var _loc_2:* = new Sprite();
            _loc_2.graphics.beginFill(15658734);
            _loc_2.graphics.drawRoundRect(0, 0, 100, 100, 10, 10);
            _loc_2.graphics.endFill();
            _loc_2.x = 39;
            _loc_2.y = 16;
            addChild(_loc_2);
            _loc_1.mask = _loc_2;
            nameText = new TextField();
            nameText.selectable = false;
            nameText.x = 163;
            nameText.y = 26;
            nameText.width = 200;
            nameText.height = 22;
            nameText.defaultTextFormat = new TextFormat("Tahoma", 14, 3355443, true);
            nameText.text = "";
            addChild(nameText);
            _loc_3 = new TextField();
            _loc_3.selectable = false;
            _loc_3.x = 163;
            _loc_3.y = 65;
            _loc_3.width = 100;
            _loc_3.height = 22;
            _loc_3.defaultTextFormat = new TextFormat("Tahoma", 14, 3355443);
            _loc_3.text = Language.L["等级:"];
            addChild(_loc_3);
            gradeText = new TextField();
            gradeText.selectable = false;
            gradeText.x = 210;
            gradeText.y = 61;
            gradeText.width = 100;
            gradeText.height = 22;
            gradeText.defaultTextFormat = new TextFormat("fontForte", 20, 39423);
            gradeText.embedFonts = true;
            gradeText.text = "";
            addChild(gradeText);
            var _loc_4:* = new TextField();
            new TextField().autoSize = TextFieldAutoSize.LEFT;
            _loc_4.selectable = false;
            _loc_4.x = 263;
            _loc_4.y = 65;
            _loc_4.defaultTextFormat = new TextFormat("Tahoma", 14, 3355443);
            _loc_4.text = Language.L["经验:"];
            addChild(_loc_4);
            expText = new TextField();
            expText.selectable = false;
            expText.x = _loc_4.x + _loc_4.width + 5;
            expText.y = 61;
            expText.width = 100;
            expText.height = 22;
            expText.defaultTextFormat = new TextFormat("fontForte", 20, 39423);
            expText.embedFonts = true;
            expText.text = "";
            addChild(expText);
            var _loc_5:* = new TextField();
            new TextField().selectable = false;
            _loc_5.x = 163;
            _loc_5.y = 90;
            _loc_5.width = 100;
            _loc_5.height = 22;
            _loc_5.defaultTextFormat = new TextFormat("Tahoma", 14, 3355443);
            _loc_5.text = Language.L["现金:"];
            addChild(_loc_5);
            var _loc_6:* = new MoneyIcon();
            new MoneyIcon().x = 210;
            _loc_6.y = 90;
            addChild(_loc_6);
            assets1Text = new TextField();
            assets1Text.selectable = false;
            assets1Text.x = 248;
            assets1Text.y = 88;
            assets1Text.width = 100;
            assets1Text.height = 24;
            assets1Text.defaultTextFormat = new TextFormat("fontForte", 18, 16737792);
            assets1Text.embedFonts = true;
            assets1Text.text = "";
            addChild(assets1Text);
            if (Version.SNS == Version.XIAONEI)
            {
                assets1Text.x = 238;
                assets1Text.y = 90;
                _loc_7 = new MoneyIcon(MoneyIcon.FB);
                _loc_7.x = 320;
                _loc_7.y = 90;
                addChild(_loc_7);
                assets2Text = new TextField();
                assets2Text.x = 345;
                assets2Text.y = 90;
                assets2Text.width = 100;
                assets2Text.height = 24;
                assets2Text.defaultTextFormat = new TextFormat("fontForte", 18, 39423);
                assets2Text.embedFonts = true;
                assets2Text.text = "";
                addChild(assets2Text);
            }// end if
            homeLink = new LipiButton();
            homeLink.bgAlpha = 0;
            homeLink.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonGreen"));
            homeLink.visible = false;
            homeLink.addEventListener(MouseEvent.CLICK, onClick);
            homeLink.x = 330;
            homeLink.y = 26;
            homeLink.width = 100;
            homeLink.height = 24;
            homeLink.label = Language.L["去TA的首页"];
            homeLink.textColor = 16777215;
            addChild(homeLink);
            return;
        }// end function

        private function ioError(param1:IOErrorEvent) : void
        {
            trace("profile头像加载错误");
            return;
        }// end function

        public function set data(param1:Object) : void
        {
            nameText.text = String(param1["uName"]);
            assets1Text.text = String(param1["money"]);
            _homePage = String(param1["homePage"]);
            gradeText.text = String(param1["uLevel"]);
            if (assets2Text != null)
            {
                assets2Text.text = String(param1["FB"]);
            }// end if
            var _loc_2:* = int(param1["uLevel"]);
            expText.text = String(param1["uExp"] - 100 * _loc_2 * (_loc_2 + 1));
            photoLoader.load(new URLRequest(String(param1["headPicBig"])));
            if (MData.getInstance().mainData.me == true)
            {
                homeLink.visible = false;
            }
            else
            {
                homeLink.visible = true;
            }// end else if
            return;
        }// end function

        private function onClick(param1:MouseEvent) : void
        {
            if (_homePage != "")
            {
                navigateToURL(new URLRequest(_homePage));
            }// end if
            return;
        }// end function

        private function onComp(param1:Event) : void
        {
            photoLoader.width = 100;
            photoLoader.height = 100;
            return;
        }// end function

    }
}
