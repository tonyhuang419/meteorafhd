package hf.view.main.task
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import hf.view.*;
    import hf.view.common.*;

    public class Task extends BaseWindow
    {
        private var _operationButtonHeight:int = 25;
        private var _cancelButtonLabel:String;
        private var _cancelButtonbgAlpha:int = 0;
        private var _cancelButtonTextBold:Boolean = false;
        private var _operationButtonbgAlpha:int = 0;
        private var _cancelButtonTextSize:int = 12;
        private var _operationButtonTextColor:int = 16777215;
        private var _operationButtonTextSize:int = 12;
        private var _operationButtonWidth:int = 60;
        private var _cancelButtonTextFont:String = "";
        private var _cancelButtonHeight:int = 25;
        private var _addOperationButton:Boolean = true;
        private var _operationButtonTextBold:Boolean = false;
        private var addChildCache:Array;
        private var _cancelButtonTextColor:int = 16777215;
        private var cancelButton:LipiButton = null;
        private var _addCancelButton:Boolean = true;
        private var _operationButtonLabel:String;
        private var _cancelButtonWidth:int = 60;
        private var operationButton:LipiButton = null;
        private var _operationButtonTextFont:String = "";
        private var fun:Function = null;

        public function Task()
        {
            _operationButtonLabel = Language.L["接受"];
            _cancelButtonLabel = Language.L["取消"];
            addChildCache = [];
            this.mode = true;
            width = 360;
            height = 240;
            return;
        }// end function

        public function set operationButtonHeight(param1:int) : void
        {
            _operationButtonHeight = param1;
            return;
        }// end function

        public function set operationButtonbgAlpha(param1:int) : void
        {
            _operationButtonbgAlpha = param1;
            return;
        }// end function

        public function closeWindow(param1:Event = null) : void
        {
            this.closeHandler();
            return;
        }// end function

        override public function init() : void
        {
            if (_addOperationButton)
            {
                operationButton = new LipiButton();
                operationButton.name = "operation";
                operationButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
                operationButton.label = _operationButtonLabel;
                operationButton.textColor = _operationButtonTextColor;
                operationButton.textSize = _operationButtonTextSize;
                operationButton.textBold = _operationButtonTextBold;
                operationButton.textFont = _operationButtonTextFont;
                operationButton.bgAlpha = _operationButtonbgAlpha;
                operationButton.width = _operationButtonWidth;
                operationButton.height = _operationButtonHeight;
                this.addChild(operationButton);
                operationButton.addEventListener(MouseEvent.CLICK, operation);
            }// end if
            if (_addCancelButton)
            {
                cancelButton = new LipiButton();
                cancelButton.name = "cancel";
                cancelButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
                cancelButton.label = _cancelButtonLabel;
                cancelButton.textColor = _cancelButtonTextColor;
                cancelButton.textSize = _cancelButtonTextSize;
                cancelButton.textBold = _cancelButtonTextBold;
                cancelButton.textFont = _cancelButtonTextFont;
                cancelButton.bgAlpha = _cancelButtonbgAlpha;
                cancelButton.width = _cancelButtonWidth;
                cancelButton.height = _cancelButtonHeight;
                this.addChild(cancelButton);
                cancelButton.addEventListener(MouseEvent.CLICK, closeWindow);
            }// end if
            var _loc_1:int;
            while (_loc_1 < addChildCache.length)
            {
                // label
                this.addChild(addChildCache[_loc_1]);
                _loc_1++;
            }// end while
            if (operationButton != null && cancelButton != null)
            {
                operationButton.x = (this.width - operationButton.width - cancelButton.width - 20) * 0.5;
                operationButton.y = this.height - operationButton.height - 15;
                cancelButton.x = operationButton.x + operationButton.width + 20;
                cancelButton.y = this.height - cancelButton.height - 15;
            }
            else if (operationButton != null)
            {
                operationButton.x = (this.width - operationButton.width) * 0.5;
                operationButton.y = this.height - operationButton.height - 15;
            }
            else if (cancelButton != null)
            {
                cancelButton.x = (this.width - cancelButton.width) * 0.5;
                cancelButton.y = this.height - cancelButton.height - 15;
            }// end else if
            toCenterPositon();
            return;
        }// end function

        public function set operationAction(param1:Function) : void
        {
            fun = param1;
            return;
        }// end function

        public function set cancelButtonTextColor(param1:int) : void
        {
            _cancelButtonTextColor = param1;
            return;
        }// end function

        public function set cancelButtonLabel(param1:String) : void
        {
            _cancelButtonLabel = param1;
            return;
        }// end function

        public function set addCancelButton(param1:Boolean) : void
        {
            _addCancelButton = param1;
            return;
        }// end function

        public function set operationButtonWidth(param1:int) : void
        {
            _operationButtonWidth = param1;
            return;
        }// end function

        public function set cancelButtonHeight(param1:int) : void
        {
            _cancelButtonHeight = param1;
            return;
        }// end function

        public function taskAddChild(param1:DisplayObject) : DisplayObject
        {
            addChildCache.push(param1);
            return param1;
        }// end function

        public function set cancelButtonbgAlpha(param1:int) : void
        {
            _cancelButtonbgAlpha = param1;
            return;
        }// end function

        public function set addOperationButton(param1:Boolean) : void
        {
            _addOperationButton = param1;
            return;
        }// end function

        public function set cancelButtonWidth(param1:int) : void
        {
            _cancelButtonWidth = param1;
            return;
        }// end function

        public function set cancelButtonTextFont(param1:String) : void
        {
            _cancelButtonTextFont = param1;
            return;
        }// end function

        public function set operationButtonTextSize(param1:int) : void
        {
            _operationButtonTextSize = param1;
            return;
        }// end function

        public function set operationButtonTextBold(param1:Boolean) : void
        {
            _operationButtonTextBold = param1;
            return;
        }// end function

        public function toCenterPositon() : void
        {
            x = stage.stageWidth * 0.5 - width * 0.5;
            y = stage.stageHeight * 0.5 - height * 0.5;
            return;
        }// end function

        public function operation(param1:Event) : void
        {
            if (fun != null)
            {
                fun();
            }// end if
            return;
        }// end function

        public function set operationButtonTextFont(param1:String) : void
        {
            _operationButtonTextFont = param1;
            return;
        }// end function

        public function set cancelButtonTextSize(param1:int) : void
        {
            _cancelButtonTextSize = param1;
            return;
        }// end function

        public function set operationButtonLabel(param1:String) : void
        {
            _operationButtonLabel = param1;
            return;
        }// end function

        public function set operationButtonTextColor(param1:int) : void
        {
            _operationButtonTextColor = param1;
            return;
        }// end function

        public function set cancelButtonTextBold(param1:Boolean) : void
        {
            _cancelButtonTextBold = param1;
            return;
        }// end function

    }
}
