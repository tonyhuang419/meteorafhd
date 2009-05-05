package hf.view.common
{
    import com.minutes.ui.core.*;
    import flash.display.*;
    import hf.control.*;

    public class BaseWindow extends LipiPanel
    {
        private var _mode:Boolean = false;
        private var _data:Object;
        private var _windowMode:Sprite;
        private var childList:Array;
        private var _isInit:Boolean = false;
        private var _windowName:String = "";

        public function BaseWindow()
        {
            addEventListener(UIEvent.CLOSE, _closeButtonClick);
            return;
        }// end function

        public function addAll() : void
        {
            if (childList != null)
            {
                while (childList.length > 0)
                {
                    // label
                    addChild(childList.shift());
                }// end while
            }// end if
            return;
        }// end function

        protected function closeHandler() : void
        {
            var _loc_1:* = new WindowEvent(WindowEvent.CLOSE);
            _loc_1.window = this;
            ViewControl.getInstance().dispatchEvent(_loc_1);
            return;
        }// end function

        public function set isInit(param1:Boolean) : void
        {
            _isInit = param1;
            return;
        }// end function

        public function addedToLayer() : void
        {
            return;
        }// end function

        public function set mode(param1:Boolean) : void
        {
            _mode = param1;
            return;
        }// end function

        public function removedFromLayer() : void
        {
            return;
        }// end function

        public function keyEnter() : void
        {
            return;
        }// end function

        public function get windowMode() : Sprite
        {
            return _windowMode;
        }// end function

        public function removeAll() : void
        {
            if (childList == null)
            {
                childList = [];
            }// end if
            while (numChildren > 0)
            {
                // label
                childList.push(removeChildAt(0));
            }// end while
            return;
        }// end function

        public function init() : void
        {
            return;
        }// end function

        public function set data(param1:Object) : void
        {
            _data = param1;
            return;
        }// end function

        public function get windowName() : String
        {
            return _windowName;
        }// end function

        public function get isInit() : Boolean
        {
            return _isInit;
        }// end function

        public function get mode() : Boolean
        {
            return _mode;
        }// end function

        public function set windowMode(param1:Sprite) : void
        {
            _windowMode = param1;
            return;
        }// end function

        public function get data() : Object
        {
            return _data;
        }// end function

        public function set windowName(param1:String) : void
        {
            _windowName = param1;
            return;
        }// end function

        private function _closeButtonClick(param1:UIEvent) : void
        {
            closeHandler();
            return;
        }// end function

    }
}
