package hf.security
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.utils.*;

    public class RequestKey extends EventDispatcher
    {
        private var _analog:Boolean = true;
        private var decodeKey:int = 0;
        private var currentSize:uint = 0;
        private var isInit:Boolean = true;
        private var encodeKey:String = "";
        private var keySocket:Socket;
        public static var port:int = 0;
        public static var host:String = null;
        public static var urserId:String = "0";

        public function RequestKey()
        {
            return;
        }// end function

        private function ioError(param1:IOErrorEvent) : void
        {
            trace(param1);
            return;
        }// end function

        private function loadKeyComp() : void
        {
            var keyEvent:* = new RequestKeyEvent(RequestKeyEvent.COMP);
            keyEvent.encodeKey = encodeKey;
            keyEvent.decodeKey = decodeKey;
            dispatchEvent(keyEvent);
            clear();
            try
            {
                keySocket.close();
            }// end try
            catch (e:Error)
            {
            }// end catch
            keySocket.removeEventListener(Event.CONNECT, onConnect);
            keySocket.removeEventListener(ProgressEvent.SOCKET_DATA, onSocketData);
            keySocket.removeEventListener(IOErrorEvent.IO_ERROR, socketIoError);
            keySocket.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, securityError);
            keySocket = null;
            return;
        }// end function

        private function onConnect(param1:Event) : void
        {
            if (isInit && analog)
            {
                sendPolicyFile();
            }// end if
            var _loc_2:* = new ByteArray();
            _loc_2.writeUTFBytes(RequestKey.urserId);
            keySocket.writeShort(_loc_2.length + 1);
            keySocket.writeByte(1);
            keySocket.writeBytes(_loc_2);
            keySocket.flush();
            return;
        }// end function

        private function clear() : void
        {
            try
            {
                keySocket.readUTFBytes(keySocket.bytesAvailable);
            }// end try
            catch (e:Error)
            {
            }// end catch
            return;
        }// end function

        private function socketIoError(param1:IOErrorEvent) : void
        {
            trace("socket连接出错");
            return;
        }// end function

        public function set analog(param1:Boolean) : void
        {
            _analog = param1;
            return;
        }// end function

        public function load() : void
        {
            keySocket = new Socket(RequestKey.host, RequestKey.port);
            keySocket.addEventListener(Event.CONNECT, onConnect);
            keySocket.addEventListener(ProgressEvent.SOCKET_DATA, onSocketData);
            keySocket.addEventListener(IOErrorEvent.IO_ERROR, socketIoError);
            keySocket.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityError);
            return;
        }// end function

        private function readData() : void
        {
            var _loc_1:ByteArray;
            if (currentSize == 0)
            {
                if (keySocket.bytesAvailable > 1)
                {
                    currentSize = keySocket.readShort();
                    if (keySocket.bytesAvailable >= currentSize)
                    {
                        _loc_1 = new ByteArray();
                        keySocket.readBytes(_loc_1, 0, currentSize);
                        dataPackHandler(_loc_1);
                        currentSize = 0;
                        readData();
                    }// end if
                }// end if
            }
            else if (keySocket.bytesAvailable >= currentSize)
            {
                _loc_1 = new ByteArray();
                keySocket.readBytes(_loc_1, currentSize);
                dataPackHandler(_loc_1);
                currentSize = 0;
                readData();
            }// end else if
            return;
        }// end function

        private function securityError(param1:SecurityErrorEvent) : void
        {
            trace("安全错误");
            return;
        }// end function

        private function swfLoaderComp(param1:Event) : void
        {
            encodeKey = param1.currentTarget.content.key();
            loadKeyComp();
            return;
        }// end function

        private function dataPackHandler(param1:ByteArray) : void
        {
            param1.position = 0;
            decodeKey = param1.readByte();
            var _loc_2:* = new ByteArray();
            param1.readBytes(_loc_2);
            var _loc_3:* = new Loader();
            _loc_3.contentLoaderInfo.addEventListener(Event.COMPLETE, swfLoaderComp, false, 0, true);
            _loc_3.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioError);
            _loc_3.loadBytes(_loc_2);
            return;
        }// end function

        public function get analog() : Boolean
        {
            return _analog;
        }// end function

        private function sendPolicyFile() : void
        {
            keySocket.writeUTFBytes("<policy-file-request/>");
            keySocket.flush();
            return;
        }// end function

        private function onSocketData(param1:ProgressEvent) : void
        {
            var _loc_2:String;
            if (isInit && analog)
            {
                if (keySocket.bytesAvailable == 88)
                {
                    isInit = false;
                    _loc_2 = keySocket.readUTFBytes(keySocket.bytesAvailable);
                    clear();
                }// end if
                return;
            }// end if
            readData();
            return;
        }// end function

    }
}
