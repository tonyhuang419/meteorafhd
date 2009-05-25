package hf.view
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;

    public class MaterialLoad extends EventDispatcher
    {
        private var _arr:Array;
        private var loader:Loader;
        private var loadIndex:int = 0;

        public function MaterialLoad(param1:Array)
        {
            _arr = param1;
            loader = new Loader();
            loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComp);
            loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioError);
            loader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatus);
            loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, onProgress);
            return;
        }// end function

        private function clearListener() : void
        {
            loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, onComp);
            loader.contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, ioError);
            loader.contentLoaderInfo.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatus);
            loader.contentLoaderInfo.removeEventListener(ProgressEvent.PROGRESS, onProgress);
            return;
        }// end function

        private function ioError(param1:IOErrorEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.ERROR_LOAD);
            _loc_2.data = {error:"ioerror", value:0};
            dispatchEvent(_loc_2);
            clearListener();
            return;
        }// end function

        private function onProgress(param1:ProgressEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.PROGRESS_LOAD);
            var _loc_3:Object;
            _loc_3["bytesLoaded"] = param1.bytesLoaded;
            _loc_3["bytesTotal"] = param1.bytesTotal;
            _loc_3["loadInfo"] = _arr[loadIndex]["statusText"];
            _loc_2.data = _loc_3;
            dispatchEvent(_loc_2);
            return;
        }// end function

        public function load() : void
        {
            if (_arr == null || _arr.length <= loadIndex)
            {
                dispatchEvent(new Event(Event.COMPLETE));
                return;
            }// end if
            if (MaterialLib.getInstance().hasUrl(_arr[loadIndex]["url"]))
            {
                loadIndex = loadIndex + 1;
                if (loadIndex >= _arr.length)
                {
                    dispatchEvent(new Event(Event.COMPLETE));
                    clearListener();
                }
                else
                {
                    load();
                }// end else if
                return;
            }
            else
            {
                loader.load(new URLRequest(_arr[loadIndex]["url"]));
            }// end else if
            return;
        }// end function

        private function onComp(param1:Event) : void
        {
            MaterialLib.getInstance().push(param1.currentTarget.applicationDomain, _arr[loadIndex]["url"]);
            if (_arr.length <= loadIndex)
            {
                dispatchEvent(new Event(Event.COMPLETE));
                clearListener();
            }
            else
            {
                loadIndex = loadIndex + 1;
                load();
            }// end else if
            return;
        }// end function

        private function httpStatus(param1:HTTPStatusEvent) : void
        {
            var _loc_2:ViewEvent;
            if (param1.status != 0 && param1.status != 200)
            {
                _loc_2 = new ViewEvent(ViewEvent.ERROR_LOAD);
                _loc_2.data = {error:"ioerror", value:param1.status};
                dispatchEvent(_loc_2);
                clearListener();
            }// end if
            return;
        }// end function

    }
}
