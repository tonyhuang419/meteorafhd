package com.minutes.net
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.utils.*;

    public class FileLoader extends EventDispatcher
    {
        private var timer:Timer;
        private var _contents:Object;
        private var _urlArray:Array;
        private var preTime:Number = 0;
        private var _loader:Loader;
        private var _index:int = 0;

        public function FileLoader()
        {
            return;
        }// end function

        public function get contents() : Object
        {
            return _contents;
        }// end function

        public function get urlArray() : Array
        {
            return _urlArray;
        }// end function

        private function onIOError(param1:IOErrorEvent) : void
        {
            if (contents == null)
            {
                _contents = new Object();
            }// end if
            contents[urlArray[_index]] = "error";
            if (_index + 1 >= urlArray.length)
            {
                comp();
            }
            else
            {
                _index = _index + 1;
                load();
            }// end else if
            return;
        }// end function

        private function onProgress(param1:ProgressEvent) : void
        {
            trace(param1.bytesLoaded, param1.bytesTotal);
            return;
        }// end function

        public function load(param1:Array = null) : void
        {
            if (param1 != null)
            {
                urlArray = param1;
            }// end if
            if (urlArray == null)
            {
                throw new Error("需要设置urlArray或urls参数");
            }// end if
            if (_loader == null)
            {
                _loader = new Loader();
                _loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComp);
                _loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, onIOError);
                _loader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, onHttpStatus);
                _loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, onProgress);
                timer = new Timer(1000);
                timer.addEventListener(TimerEvent.TIMER, timerEventHandler);
                timer.start();
            }// end if
            if (urlArray.length == 0 || urlArray[_index] == "")
            {
                if (contents == null)
                {
                    _contents = new Object();
                }// end if
                contents[urlArray[_index]] = "";
                if (_index + 1 >= urlArray.length)
                {
                    comp();
                }
                else
                {
                    _index = _index + 1;
                    load();
                }// end else if
            }
            else
            {
                _loader.load(new URLRequest(urlArray[_index]));
                preTime = getTimer();
            }// end else if
            return;
        }// end function

        private function onComp(param1:Event) : void
        {
            if (contents == null)
            {
                _contents = new Object();
            }// end if
            contents[urlArray[_index]] = _loader.contentLoaderInfo.content;
            if (_index + 1 >= urlArray.length)
            {
                comp();
            }
            else
            {
                _index = _index + 1;
                load();
            }// end else if
            return;
        }// end function

        private function comp() : void
        {
            dispatchEvent(new Event(Event.COMPLETE));
            timer.stop();
            timer = null;
            _loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, onComp);
            _loader.contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, onIOError);
            _loader.contentLoaderInfo.removeEventListener(HTTPStatusEvent.HTTP_STATUS, onHttpStatus);
            _loader.contentLoaderInfo.removeEventListener(ProgressEvent.PROGRESS, onProgress);
            _index = 0;
            try
            {
                _loader.close();
            }// end try
            catch (e:Error)
            {
            }// end catch
            _loader = null;
            return;
        }// end function

        private function onHttpStatus(param1:HTTPStatusEvent) : void
        {
            trace("httpstatus" + param1.status);
            return;
        }// end function

        private function timerEventHandler(param1:TimerEvent) : void
        {
            var e:* = param1;
            if (getTimer() - preTime > 1000 * 10)
            {
                if (contents == null)
                {
                    _contents = new Object();
                }// end if
                contents[urlArray[_index]] = "error";
                if (_index + 1 >= urlArray.length)
                {
                    comp();
                }
                else
                {
                    _index = _index + 1;
                    try
                    {
                        _loader.close();
                    }// end try
                    catch (e:Error)
                    {
                    }// end catch
                    load();
                }// end if
            }// end else if
            return;
        }// end function

        public function set urlArray(param1:Array) : void
        {
            _urlArray = param1;
            return;
        }// end function

    }
}
