package hf.view.main.window.shop
{
    import hf.control.*;
    import hf.view.*;
    import hf.view.common.*;

    public class PreviewBackWindow extends AddButtonWindow
    {
        public static const PREVIEW:String = "preview";

        public function PreviewBackWindow()
        {
            this.width = 200;
            this.height = 100;
            this.mode = false;
            title = Language.L["取消预览"];
            addButton(Language.L["取消预览"], "ButtonOrange", 100, 25, backShop);
            return;
        }// end function

        override protected function closeHandler() : void
        {
            super.closeHandler();
            var _loc_1:* = new ViewEvent(PreviewBackWindow.PREVIEW);
            ViewControl.getInstance().dispatchEvent(_loc_1);
            ViewControl.getInstance().dispatchEvent(new ViewEvent("showWindow"));
            Command.getInstance().mainCommand.preview(null);
            return;
        }// end function

        private function backShop() : void
        {
            closeHandler();
            return;
        }// end function

    }
}
