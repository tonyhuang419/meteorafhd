﻿package com.adobe.images
{
    import flash.display.*;
    import flash.utils.*;

    public class PNGEncoder extends Object
    {
        private static var crcTableComputed:Boolean = false;
        private static var crcTable:Array;

        public function PNGEncoder()
        {
            return;
        }// end function

        private static function writeChunk(param1:ByteArray, param2:uint, param3:ByteArray) : void
        {
            var _loc_8:uint;
            var _loc_9:uint;
            var _loc_10:uint;
            if (!crcTableComputed)
            {
                crcTableComputed = true;
                crcTable = [];
                _loc_9 = 0;
                while (_loc_9++ < 256)
                {
                    // label
                    _loc_8 = _loc_9;
                    _loc_10 = 0;
                    while (_loc_10++ < 8)
                    {
                        // label
                        if (_loc_8 & 1)
                        {
                            _loc_8 = uint(uint(3988292384) ^ uint(_loc_8 >>> 1));
                            continue;
                        }// end if
                        _loc_8 = uint(_loc_8 >>> 1);
                    }// end while
                    crcTable[_loc_9] = _loc_8;
                }// end while
            }// end if
            var _loc_4:uint;
            if (param3 != null)
            {
                _loc_4 = param3.length;
            }// end if
            param1.writeUnsignedInt(_loc_4);
            var _loc_5:* = param1.position;
            param1.writeUnsignedInt(param2);
            if (param3 != null)
            {
                param1.writeBytes(param3);
            }// end if
            var _loc_6:* = param1.position;
            param1.position = _loc_5;
            _loc_8 = 4294967295;
            var _loc_7:int;
            while (_loc_7 < _loc_6 - _loc_5)
            {
                // label
                _loc_8 = uint(crcTable[(_loc_8 ^ param1.readUnsignedByte()) & uint(255)] ^ uint(_loc_8 >>> 8));
                _loc_7++;
            }// end while
            _loc_8 = uint(_loc_8 ^ uint(4294967295));
            param1.position = _loc_6;
            param1.writeUnsignedInt(_loc_8);
            return;
        }// end function

        public static function encode(param1:BitmapData) : ByteArray
        {
            var _loc_6:uint;
            var _loc_7:int;
            var _loc_2:* = new ByteArray();
            _loc_2.writeUnsignedInt(2303741511);
            _loc_2.writeUnsignedInt(218765834);
            var _loc_3:* = new ByteArray();
            _loc_3.writeInt(param1.width);
            _loc_3.writeInt(param1.height);
            _loc_3.writeUnsignedInt(134610944);
            _loc_3.writeByte(0);
            writeChunk(_loc_2, 1229472850, _loc_3);
            var _loc_4:* = new ByteArray();
            var _loc_5:int;
            while (_loc_5 < param1.height)
            {
                // label
                _loc_4.writeByte(0);
                if (!param1.transparent)
                {
                    _loc_7 = 0;
                    while (_loc_7 < param1.width)
                    {
                        // label
                        _loc_6 = param1.getPixel(_loc_7, _loc_5);
                        _loc_4.writeUnsignedInt(uint((_loc_6 & 16777215) << 8 | 255));
                        _loc_7++;
                    }// end while
                }
                else
                {
                    _loc_7 = 0;
                    while (_loc_7 < param1.width)
                    {
                        // label
                        _loc_6 = param1.getPixel32(_loc_7, _loc_5);
                        _loc_4.writeUnsignedInt(uint((_loc_6 & 16777215) << 8 | _loc_6 >>> 24));
                        _loc_7++;
                    }// end while
                }// end else if
                _loc_5++;
            }// end while
            _loc_4.compress();
            writeChunk(_loc_2, 1229209940, _loc_4);
            writeChunk(_loc_2, 1229278788, null);
            return _loc_2;
        }// end function

    }
}
