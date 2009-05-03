package hf.view.main.window.shop
{
    import com.lipi.*;
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;

    public class ShopWindow extends BaseWindow
    {
        private var cropTile:ShopSeedTile;
        private var mainData:MainData;
        private var tabNav:JerryTabNavigation;
        private var diyTile:ShopDiyTile;
        private var toolTile:ShopToolTile;

        public function ShopWindow()
        {
            width = 500;
            height = 360;
            title = Language.L["shopTitle"];
            windowName = "shop";
            mode = true;
            return;
        }// end function

        private function shopDiyErr(param1:Event) : void
        {
            diyTile.errText = mainData.shopDiyErr;
            return;
        }// end function

        private function showWindow(param1:Event) : void
        {
            this.visible = true;
            return;
        }// end function

        override public function init() : void
        {
            mainData = MData.getInstance().mainData;
            mainData.addEventListener(MainData.SEED_INFO, seedInfo, false, 0, true);
            mainData.addEventListener(MainData.TOOL_INFO, toolInfo, false, 0, true);
            mainData.addEventListener(MainData.DIY_INFO, diyInfo, false, 0, true);
            mainData.addEventListener(MainData.SHOP_SEED_LOADING, shopSeedLoading, false, 0, true);
            mainData.addEventListener(MainData.SHOP_TOOL_LOADING, shopToolLoading, false, 0, true);
            mainData.addEventListener(MainData.SHOP_DIY_LOADING, shopDiyLoading, false, 0, true);
            mainData.addEventListener(MainData.SHOP_SEED_ERR, shopSeedErr, false, 0, true);
            mainData.addEventListener(MainData.SHOP_TOOL_ERR, shopToolErr, false, 0, true);
            mainData.addEventListener(MainData.SHOP_DIY_ERR, shopDiyErr, false, 0, true);
            ViewControl.getInstance().addEventListener("hideWindow", hideWindow);
            ViewControl.getInstance().addEventListener("showWindow", showWindow);
            addTab();
            Command.getInstance().mainCommand.getSeedInfo();
            return;
        }// end function

        private function hideWindow(param1:Event) : void
        {
            this.visible = false;
            return;
        }// end function

        private function shopToolErr(param1:Event) : void
        {
            toolTile.errText = mainData.shopToolErr;
            return;
        }// end function

        private function shopDiyLoading(param1:Event) : void
        {
            if (mainData.shopDiyLoading == true)
            {
                if (diyTile != null)
                {
                    diyTile.loadingVisible = true;
                }// end if
            }
            else if (diyTile != null)
            {
                diyTile.loadingVisible = false;
            }// end else if
            return;
        }// end function

        private function getStringWidth(param1:String, param2:int = 55, param3:int = 10) : Number
        {
            var _loc_4:* = LipiUtil.getTextLangth(param1);
            _loc_4 = LipiUtil.getTextLangth(param1) + param3 * 2;
            if (_loc_4 < param2)
            {
                _loc_4 = param2;
            }// end if
            return _loc_4;
        }// end function

        private function shopSeedErr(param1:Event) : void
        {
            cropTile.errText = mainData.shopSeedErr;
            return;
        }// end function

        private function diyInfo(param1:Event) : void
        {
            diyTile.dataList = mainData.diyInfo;
            return;
        }// end function

        private function toolInfo(param1:Event) : void
        {
            toolTile.dataList = mainData.toolsInfo;
            return;
        }// end function

        private function shopToolLoading(param1:Event) : void
        {
            if (mainData.shopToolLoading == true)
            {
                if (toolTile != null)
                {
                    toolTile.loadingVisible = true;
                }// end if
            }
            else if (toolTile != null)
            {
                toolTile.loadingVisible = false;
            }// end else if
            return;
        }// end function

        private function tabSelect(param1:UIEvent) : void
        {
            if (param1.data == 0)
            {
                Command.getInstance().mainCommand.getSeedInfo();
            }
            else if (param1.data == 1)
            {
                if (OpenControl.open("tool"))
                {
                    Command.getInstance().mainCommand.getToolsInfo();
                }
                else
                {
                    Command.getInstance().mainCommand.getDiyInfo();
                }// end else if
            }
            else if (param1.data == 2)
            {
                Command.getInstance().mainCommand.getDiyInfo();
            }// end else if
            return;
        }// end function

        private function addTab() : void
        {
            tabNav = new JerryTabNavigation();
            tabNav.selectedFontStyle(12, 16777215, "", true);
            tabNav.defaultFontStyle(12, 39423);
            tabNav.menuLeftDistance = 25;
            tabNav.menuSpaceDistance = 3;
            tabNav.menuDefaultButtonWidth = 55;
            tabNav.menuDefaultButtonHeight = 22;
            tabNav.menuHeight = 25;
            tabNav.topHeight = 2;
            tabNav.x = 0;
            tabNav.y = 33;
            tabNav.width = 499;
            tabNav.menuBgClass = MaterialLib.getInstance().getClass("TabBg");
            tabNav.menuDefaultSkin = MaterialLib.getInstance().getClass("TabDefault");
            tabNav.menuSelectedSkin = MaterialLib.getInstance().getClass("TabSelected");
            tabNav.addEventListener(JerryTabNavigation.TAB_SELECT, tabSelect);
            cropTile = new ShopSeedTile();
            cropTile.addEventListener(ViewEvent.LINK_CLICK, cropLinkClick);
            if (mainData.seedInfo != null)
            {
                cropTile.dataList = mainData.seedInfo;
            }// end if
            if (OpenControl.open("tool"))
            {
                toolTile = new ShopToolTile();
                toolTile.addEventListener(ViewEvent.LINK_CLICK, toolLinkClick);
                if (mainData.toolsInfo != null)
                {
                    toolTile.dataList = mainData.toolsInfo;
                }// end if
            }// end if
            diyTile = new ShopDiyTile();
            diyTile.addEventListener(ViewEvent.LINK_CLICK, diyLinkClick);
            if (mainData.diyInfo != null)
            {
                diyTile.dataList = mainData.diyInfo;
            }// end if
            tabNav.addItem(Language.L["shopTagSell"], cropTile, getStringWidth(Language.L["shopTagSell"]), 22);
            if (toolTile != null)
            {
                tabNav.addItem(Language.L["shopTagFertilizer"], toolTile, getStringWidth(Language.L["shopTagFertilizer"]), 22);
            }// end if
            tabNav.addItem(Language.L["shopTagDecorate"], diyTile, getStringWidth(Language.L["shopTagDecorate"]), 22);
            addChild(tabNav);
            return;
        }// end function

        private function shopSeedLoading(param1:Event) : void
        {
            if (mainData.shopSeedLoading == true)
            {
                if (cropTile != null)
                {
                    cropTile.loadingVisible = true;
                }// end if
            }
            else if (cropTile != null)
            {
                cropTile.loadingVisible = false;
            }// end else if
            return;
        }// end function

        private function toolLinkClick(param1:ViewEvent) : void
        {
            if (param1.data == "reload")
            {
                Command.getInstance().mainCommand.getToolsInfo();
                toolTile.loadingVisible = true;
            }// end if
            return;
        }// end function

        private function diyLinkClick(param1:ViewEvent) : void
        {
            if (param1.data == "reload")
            {
                Command.getInstance().mainCommand.getDiyInfo();
                diyTile.loadingVisible = true;
            }// end if
            return;
        }// end function

        private function cropLinkClick(param1:ViewEvent) : void
        {
            if (param1.data == "reload")
            {
                Command.getInstance().mainCommand.getSeedInfo();
                cropTile.loadingVisible = true;
            }// end if
            return;
        }// end function

        private function seedInfo(param1:Event) : void
        {
            cropTile.dataList = mainData.seedInfo;
            return;
        }// end function

    }
}
