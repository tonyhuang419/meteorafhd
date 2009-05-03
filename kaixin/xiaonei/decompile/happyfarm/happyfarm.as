package 
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.model.*;
    import hf.security.*;
    import hf.view.*;

    public class happyfarm extends Sprite
    {
        private var loadSize:int = 0;
        private var iniurl:String = "";
        private var mainMaterial:Array;
        private var preTime:uint = 0;
        private var preValue:uint = 0;
        private var _error:Boolean = false;
        private var loadedSize:int = 0;
        private var materialIndex:int = 0;
        private var uId:String = "";
        private var _loader:Loader;
        private var swfVersion:String = "";
        private var loadingBar:Loading;

        public function happyfarm()
        {
            mainMaterial = [];
            var _loc_1:* = this.stage.loaderInfo.parameters;
            var _loc_2:String;
            if (_loc_1 != null)
            {
                if (_loc_1.hasOwnProperty("v"))
                {
                    _loc_2 = _loc_1["v"];
                }// end if
                if (_loc_1.hasOwnProperty("uId"))
                {
                    uId = _loc_1["uId"];
                }// end if
                if (_loc_1.hasOwnProperty("inuId"))
                {
                    SessionKey.value = _loc_1["inuId"];
                }// end if
                if (_loc_1.hasOwnProperty("api_key"))
                {
                    FacebookArguments.api_key = _loc_1["api_key"];
                    FacebookArguments.ss = _loc_1["ss"];
                    FacebookArguments.session_key = _loc_1["session_key"];
                }// end if
            }// end if
            var _loc_3:* = this.stage.loaderInfo.url;
            iniurl = _loc_3.replace(/happyfarm\.swf.*/, "module/ini.xml");
            loadIniXML(_loc_2);
            stage.scaleMode = StageScaleMode.NO_SCALE;
            stage.align = StageAlign.TOP_LEFT;
            stage.showDefaultContextMenu = false;
            stage.frameRate = 12;
            addEventListener(Event.ENTER_FRAME, onEnterFrame);
            return;
        }// end function

        private function loadXMLComp(param1:Event) : void
        {
            INI.getInstance(param1.target.data);
            loadingBar = new Loading();
            addChild(loadingBar);
            mainMaterial = INI.getInstance().getMaterialUrl("main");
            var _loc_2:* = INI.getInstance().getModuleUrl("main");
            setLoadSize(mainMaterial, _loc_2);
            if (uId == "")
            {
                loadMaterial();
            }
            else
            {
                loadKey();
            }// end else if
            return;
        }// end function

        private function loaderComp(param1:Event) : void
        {
            MaterialLib.getInstance().push(param1.currentTarget.applicationDomain);
            loadedSize = loadedSize + Number(mainMaterial[materialIndex--]["size"]);
            loadingBar.fileLoaded = loadedSize;
            loadingBar.loaded = 0;
            if (materialIndex < mainMaterial.length)
            {
                loadMaterial();
            }
            else
            {
                loadMain();
            }// end else if
            return;
        }// end function

        private function loadMain() : void
        {
            if (_loader == null)
            {
                _loader = new Loader();
                _loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loaderMainComp);
                _loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, loadIOError);
                _loader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatus);
                _loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, progressHandler);
            }
            else
            {
                _loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, loaderComp);
                _loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loaderMainComp);
            }// end else if
            var _loc_1:* = INI.getInstance().getModuleUrl("main");
            _loader.load(new URLRequest(_loc_1["url"] + swfVersion));
            loadingBar.showText = _loc_1["statusText"];
            return;
        }// end function

        private function setLoadSize(param1:Array, param2:Object) : void
        {
            var _loc_3:int;
            var _loc_4:int;
            while (_loc_4 < param1.length)
            {
                // label
                _loc_3 = _loc_3 + int(param1[_loc_4]["size"]);
                _loc_4++;
            }// end while
            _loc_3 = _loc_3 + int(param2["size"]);
            loadSize = _loc_3;
            loadingBar.fileTotal = loadSize;
            return;
        }// end function

        private function loadIniXML(param1:String) : void
        {
            var _loc_2:String;
            var _loc_3:* = new URLLoader();
            _loc_3.addEventListener(IOErrorEvent.IO_ERROR, myLoaderIoError);
            _loc_3.addEventListener(Event.COMPLETE, loadXMLComp);
            if (iniurl == "")
            {
                _loc_2 = "module/ini.xml?v=" + param1;
            }
            else
            {
                _loc_2 = iniurl + "?v=" + param1;
            }// end else if
            _loc_3.load(new URLRequest(_loc_2));
            return;
        }// end function

        private function httpStatus(param1:HTTPStatusEvent) : void
        {
            return;
        }// end function

        private function loaderMainComp(param1:Event) : void
        {
            loadingBar.loadingEnd();
            var _loc_2:* = new Timer(1400, 1);
            _loc_2.addEventListener(TimerEvent.TIMER, setTimeoutHandler);
            _loc_2.start();
            return;
        }// end function

        private function myLoaderIoError(param1:IOErrorEvent) : void
        {
            var _loc_2:* = new TextField();
            _loc_2.autoSize = TextFieldAutoSize.LEFT;
            _loc_2.defaultTextFormat = new TextFormat("Tahoma", 30);
            _loc_2.text = "Load \"ini.xml\" ERROR";
            _loc_2.x = (stage.stageWidth - _loc_2.width) / 2;
            _loc_2.y = (stage.stageHeight - _loc_2.height) / 2;
            addChild(_loc_2);
            return;
        }// end function

        private function loadKey() : void
        {
            RequestKey.host = INI.getInstance().getHost();
            RequestKey.port = INI.getInstance().getPort();
            RequestKey.urserId = uId;
            var _loc_1:* = new RequestKey();
            _loc_1.addEventListener(RequestKeyEvent.COMP, requestKeyComp);
            _loc_1.load();
            return;
        }// end function

        private function loadMaterial() : void
        {
            if (_loader == null)
            {
                _loader = new Loader();
                _loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loaderComp);
                _loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, loadIOError);
                _loader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatus);
                _loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, progressHandler);
            }// end if
            if (materialIndex < mainMaterial.length)
            {
                _loader.load(new URLRequest(mainMaterial[materialIndex]["url"] + swfVersion));
                loadingBar.showText = mainMaterial[materialIndex]["statusText"];
                materialIndex = materialIndex + 1;
            }// end if
            if (mainMaterial.length == 0)
            {
                loadMain();
            }// end if
            return;
        }// end function

        private function onEnterFrame(param1:Event) : void
        {
            var _loc_2:uint;
            var _loc_3:XML;
            var _loc_4:String;
            if (_loader != null)
            {
                _loc_2 = _loader.contentLoaderInfo.bytesLoaded;
                if (_loc_2 != preValue)
                {
                    preTime = getTimer();
                    preValue = _loc_2;
                    if (!_error)
                    {
                        loadingBar.errorText = "";
                    }// end if
                }
                else if (getTimer() - preTime > 1000 * 10 && !_error)
                {
                    _loc_3 = INI.getInstance().data;
                    _loc_4 = _loc_3.version.@language;
                    if (_loc_4 == "" || _loc_4 == null || _loc_4 == "cn")
                    {
                        loadingBar.errorText = "网络加载太慢或网络中断，请稍候或刷新。";
                    }
                    else
                    {
                        loadingBar.errorText = "Network is too slow to load, or network outages, please wait or refresh.";
                    }// end if
                }// end else if
            }// end else if
            return;
        }// end function

        private function progressHandler(param1:ProgressEvent) : void
        {
            loadingBar.total = param1.bytesTotal;
            loadingBar.loaded = param1.bytesLoaded;
            return;
        }// end function

        private function setTimeoutHandler(param1:TimerEvent) : void
        {
            addChild(_loader.contentLoaderInfo.content);
            _loader.contentLoaderInfo.removeEventListener(Event.COMPLETE, loaderMainComp);
            _loader.contentLoaderInfo.removeEventListener(IOErrorEvent.IO_ERROR, loadIOError);
            _loader.contentLoaderInfo.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatus);
            _loader.contentLoaderInfo.removeEventListener(ProgressEvent.PROGRESS, progressHandler);
            removeEventListener(Event.ENTER_FRAME, onEnterFrame);
            _loader = null;
            removeChild(loadingBar);
            loadingBar = null;
            return;
        }// end function

        private function loadIOError(param1:IOErrorEvent) : void
        {
            _error = true;
            var _loc_2:* = INI.getInstance().data;
            var _loc_3:* = _loc_2.version.@language;
            if (_loc_3 == "" || _loc_3 == null || _loc_3 == "cn")
            {
                loadingBar.errorText = "文件加载出错";
            }
            else
            {
                loadingBar.errorText = "File IO ERROR";
            }// end else if
            return;
        }// end function

        private function requestKeyComp(param1:RequestKeyEvent) : void
        {
            SecurityKey.decodeKey = param1.decodeKey;
            SecurityKey.encodeKey = param1.encodeKey;
            loadMaterial();
            return;
        }// end function

    }
}
