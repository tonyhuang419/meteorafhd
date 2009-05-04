package hf.view
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import hf.model.*;

    public class Loading extends Sprite
    {
        private var _fileLoaded:uint = 1;
        private var _total:uint = 0;
        private var _errorText:String = "";
        private var _fileTotal:uint = 1;
        private var _loaded:uint = 0;
        private var _showText:String = "";
        private var _loader:Loader;
        private var _loadContent:DisplayObject;
        private var _isLoadComp:Boolean = false;

        public function Loading()
        {
            addEventListener(Event.ADDED_TO_STAGE, addedToStage);
            loadBar();
            return;
        }// end function

        private function ioError(param1:IOErrorEvent = null) : void
        {
            var _loc_2:* = new TextField();
            _loc_2.autoSize = TextFieldAutoSize.LEFT;
            _loc_2.defaultTextFormat = new TextFormat("Tahoma", 30);
            _loc_2.text = "Loading...";
            _loc_2.x = (stage.stageWidth - _loc_2.width) / 2;
            _loc_2.y = (stage.stageHeight - _loc_2.height) / 2;
            addChild(_loc_2);
            return;
        }// end function

        public function get total() : uint
        {
            return _total;
        }// end function

        public function set total(param1:uint) : void
        {
            _total = param1;
            if (_loadContent != null)
            {
                (_loadContent as Object).setProgress(loaded, total, showText, fileLoaded, fileTotal);
            }// end if
            return;
        }// end function

        public function set fileLoaded(param1:uint) : void
        {
            _fileLoaded = param1;
            return;
        }// end function

        public function get loaded() : uint
        {
            return _loaded;
        }// end function

        private function loadBar() : void
        {
            _loader = new Loader();
            var _loc_1:* = INI.getInstance().loadingUrl();
            _loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComplete);
            _loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioError);
            _loader.load(new URLRequest(_loc_1));
            return;
        }// end function

        public function set fileTotal(param1:uint) : void
        {
            _fileTotal = param1;
            return;
        }// end function

        public function set loaded(param1:uint) : void
        {
            _loaded = param1;
            if (_loadContent != null)
            {
                (_loadContent as Object).setProgress(loaded, total, showText, fileLoaded, fileTotal);
            }// end if
            return;
        }// end function

        public function set showText(param1:String) : void
        {
            _showText = param1;
            if (_loadContent != null)
            {
                (_loadContent as Object).setProgress(loaded, total, showText, fileLoaded, fileTotal);
            }// end if
            return;
        }// end function

        public function get errorText() : String
        {
            return _errorText;
        }// end function

        public function get fileLoaded() : uint
        {
            return _fileLoaded;
        }// end function

        public function get showText() : String
        {
            return _showText;
        }// end function

        private function addedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, addedToStage);
            if (_isLoadComp)
            {
                addBar();
            }// end if
            return;
        }// end function

        public function get fileTotal() : uint
        {
            return _fileTotal;
        }// end function

        public function set errorText(param1:String) : void
        {
            _errorText = param1;
            if (_loadContent != null)
            {
                (_loadContent as Object).setErrorText(param1);
            }// end if
            return;
        }// end function

        private function onComplete(param1:Event) : void
        {
            _isLoadComp = true;
            if (stage == null)
            {
                return;
            }// end if
            addBar();
            return;
        }// end function

        private function addBar() : void
        {
            _loader.x = (stage.stageWidth - _loader.contentLoaderInfo.width) / 2;
            _loader.y = (stage.stageHeight - _loader.contentLoaderInfo.height) / 2;
            _loadContent = _loader.contentLoaderInfo.content;
            (_loadContent as Object).setProgress(loaded, total, showText, fileLoaded, fileTotal);
            (_loadContent as Object).setStageSize(stage.stageWidth, stage.stageHeight, _loader.x, _loader.y);
            addChild(_loader);
            return;
        }// end function

        public function loadingEnd() : void
        {
            if (_loadContent != null)
            {
                (_loadContent as Object).loaded();
            }// end if
            return;
        }// end function

    }
}
