package 
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.tip.*;
    import hf.view.main.window.shop.*;

    private class OneDivShow extends Sprite
    {
        private var buyWin:BuyDiyWindow;
        private var divRim:Sprite;
        private var fbText:TextField;
        private var divImg:MaterialProxy;
        private var priceText:TextField;
        private var _divData:Object;
        private var divMask:Sprite;

        private function OneDivShow(param1:MaterialProxy, param2:Class, param3:Object) : void
        {
            var _loc_5:MoneyIcon;
            this.buttonMode = true;
            this.useHandCursor = true;
            param3.FBPrice = param3.FBPrice - 0;
            this.graphics.beginFill(16777215, 0);
            this.graphics.drawRect(0, 0, 1, 130);
            this.graphics.endFill();
            divImg = param1;
            divMask = new param2 as Sprite;
            divRim = new param2 as Sprite;
            divImg.x = 0;
            divImg.y = 0;
            divMask.x = 0;
            divMask.y = 0;
            divRim.x = 0;
            divRim.y = 0;
            divMask.width = 60;
            divMask.height = 60;
            divRim.width = 60;
            divRim.height = 60;
            addChild(divImg);
            addChild(divMask);
            addChild(divRim);
            divImg.mask = divMask;
            priceText = new TextField();
            priceText.defaultTextFormat = new TextFormat("fontForte", 16, 16737792, true, null, null, null, null, TextFormatAlign.CENTER);
            priceText.embedFonts = true;
            priceText.selectable = false;
            priceText.width = 45;
            priceText.height = 23;
            if (Version.SNS == Version.QQ)
            {
                priceText.x = 27;
            }
            else
            {
                priceText.x = 17;
            }// end else if
            priceText.y = 63;
            priceText.text = param3.price;
            addChild(priceText);
            var _loc_4:* = new MoneyIcon();
            new MoneyIcon().x = -5;
            _loc_4.y = 65;
            addChild(_loc_4);
            if (param3.FBPrice != 0)
            {
                fbText = new TextField();
                fbText.defaultTextFormat = new TextFormat("fontForte", 16, 26367, true, null, null, null, null, "center");
                fbText.embedFonts = true;
                fbText.selectable = false;
                fbText.width = 30;
                fbText.height = 23;
                fbText.x = 17;
                if (Version.SNS == Version.QQ)
                {
                    fbText.y = 86;
                }
                else
                {
                    fbText.y = 90;
                }// end else if
                fbText.text = param3.FBPrice;
                addChild(fbText);
                _loc_5 = new MoneyIcon(MoneyIcon.FB);
                _loc_5.x = -5;
                _loc_5.y = 90;
                addChild(_loc_5);
            }// end if
            divData = param3;
            addEventListener(MouseEvent.MOUSE_OUT, mouseOuTAction);
            addEventListener(MouseEvent.MOUSE_OVER, mouseOverAction);
            TipClassLib.register("shopdiyTip", ShopDiyMouseTip);
            return;
        }// end function

        public function set divData(param1:Object) : void
        {
            _divData = param1;
            divImg.setContent("2", param1.itemId, true);
            addEventListener(MouseEvent.CLICK, useThisDiv);
            return;
        }// end function

        private function useThisDiv(param1:Event) : void
        {
            buyWin = new BuyDiyWindow(divData);
            buyWin.addEventListener(BuyDiyWindow.BUYDIY, buyRequest);
            buyWin.addEventListener(BuyDiyWindow.BUYDIY_FB, buyRequestFB);
            WControl.open(buyWin);
            return;
        }// end function

        private function buyRequest(param1:Event) : void
        {
            Command.getInstance().mainCommand.buyDiy(_divData.itemId);
            return;
        }// end function

        private function buyRequestFB(param1:ViewEvent) : void
        {
            Command.getInstance().mainCommand.buyDiy(_divData.itemId, true, param1.data["exp"], param1.data["diyName"]);
            return;
        }// end function

        public function get divData() : Object
        {
            return _divData;
        }// end function

        private function mouseOverAction(param1:Event) : void
        {
            var _loc_2:* = new TipEvent(TipEvent.TIP_SHOW);
            _loc_2.tipType = "shopdiyTip";
            _loc_2.tipArgument = divData;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        private function mouseOuTAction(param1:Event) : void
        {
            var _loc_2:* = new TipEvent(TipEvent.TIP_HIDE);
            _loc_2.tipType = "shopdiyTip";
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
