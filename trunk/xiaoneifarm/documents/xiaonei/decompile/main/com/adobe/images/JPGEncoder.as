﻿package com.adobe.images
{
    import flash.display.*;
    import flash.utils.*;

    public class JPGEncoder extends Object
    {
        private var fdtbl_UV:Array;
        private var std_ac_chrominance_values:Array;
        private var std_dc_chrominance_values:Array;
        private var ZigZag:Array;
        private var YDC_HT:Array;
        private var YAC_HT:Array;
        private var bytenew:int = 0;
        private var fdtbl_Y:Array;
        private var std_ac_chrominance_nrcodes:Array;
        private var DU:Array;
        private var std_ac_luminance_values:Array;
        private var std_dc_chrominance_nrcodes:Array;
        private var UVTable:Array;
        private var YDU:Array;
        private var UDU:Array;
        private var byteout:ByteArray;
        private var UVAC_HT:Array;
        private var UVDC_HT:Array;
        private var bytepos:int = 7;
        private var VDU:Array;
        private var std_ac_luminance_nrcodes:Array;
        private var std_dc_luminance_values:Array;
        private var YTable:Array;
        private var std_dc_luminance_nrcodes:Array;
        private var bitcode:Array;
        private var category:Array;

        public function JPGEncoder(param1:Number = 50)
        {
            ZigZag = [0, 1, 5, 6, 14, 15, 27, 28, 2, 4, 7, 13, 16, 26, 29, 42, 3, 8, 12, 17, 25, 30, 41, 43, 9, 11, 18, 24, 31, 40, 44, 53, 10, 19, 23, 32, 39, 45, 52, 54, 20, 22, 33, 38, 46, 51, 55, 60, 21, 34, 37, 47, 50, 56, 59, 61, 35, 36, 48, 49, 57, 58, 62, 63];
            YTable = new Array(64);
            UVTable = new Array(64);
            fdtbl_Y = new Array(64);
            fdtbl_UV = new Array(64);
            std_dc_luminance_nrcodes = [0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0];
            std_dc_luminance_values = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
            std_ac_luminance_nrcodes = [0, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125];
            std_ac_luminance_values = [1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, 129, 145, 161, 8, 35, 66, 177, 193, 21, 82, 209, 240, 36, 51, 98, 114, 130, 9, 10, 22, 23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, 131, 132, 133, 134, 135, 136, 137, 138, 146, 147, 148, 149, 150, 151, 152, 153, 154, 162, 163, 164, 165, 166, 167, 168, 169, 170, 178, 179, 180, 181, 182, 183, 184, 185, 186, 194, 195, 196, 197, 198, 199, 200, 201, 202, 210, 211, 212, 213, 214, 215, 216, 217, 218, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250];
            std_dc_chrominance_nrcodes = [0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0];
            std_dc_chrominance_values = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
            std_ac_chrominance_nrcodes = [0, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119];
            std_ac_chrominance_values = [0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, 129, 8, 20, 66, 145, 161, 177, 193, 9, 35, 51, 82, 240, 21, 98, 114, 209, 10, 22, 36, 52, 225, 37, 241, 23, 24, 25, 26, 38, 39, 40, 41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, 130, 131, 132, 133, 134, 135, 136, 137, 138, 146, 147, 148, 149, 150, 151, 152, 153, 154, 162, 163, 164, 165, 166, 167, 168, 169, 170, 178, 179, 180, 181, 182, 183, 184, 185, 186, 194, 195, 196, 197, 198, 199, 200, 201, 202, 210, 211, 212, 213, 214, 215, 216, 217, 218, 226, 227, 228, 229, 230, 231, 232, 233, 234, 242, 243, 244, 245, 246, 247, 248, 249, 250];
            bitcode = new Array(65535);
            category = new Array(65535);
            DU = new Array(64);
            YDU = new Array(64);
            UDU = new Array(64);
            VDU = new Array(64);
            if (param1 <= 0)
            {
                param1 = 1;
            }// end if
            if (param1 > 100)
            {
                param1 = 100;
            }// end if
            var _loc_2:int;
            if (param1 < 50)
            {
                _loc_2 = int(5000 / param1);
            }
            else
            {
                _loc_2 = int(200 - param1 * 2);
            }// end else if
            initHuffmanTbl();
            initCategoryNumber();
            initQuantTables(_loc_2);
            return;
        }// end function

        private function initHuffmanTbl() : void
        {
            YDC_HT = computeHuffmanTbl(std_dc_luminance_nrcodes, std_dc_luminance_values);
            UVDC_HT = computeHuffmanTbl(std_dc_chrominance_nrcodes, std_dc_chrominance_values);
            YAC_HT = computeHuffmanTbl(std_ac_luminance_nrcodes, std_ac_luminance_values);
            UVAC_HT = computeHuffmanTbl(std_ac_chrominance_nrcodes, std_ac_chrominance_values);
            return;
        }// end function

        private function RGB2YUV(param1:BitmapData, param2:int, param3:int) : void
        {
            var _loc_6:int;
            var _loc_7:uint;
            var _loc_8:Number;
            var _loc_9:Number;
            var _loc_10:Number;
            var _loc_4:int;
            var _loc_5:int;
            while (_loc_5 < 8)
            {
                // label
                _loc_6 = 0;
                while (_loc_6 < 8)
                {
                    // label
                    _loc_7 = param1.getPixel32(param2 + _loc_6, param3 + _loc_5);
                    _loc_8 = Number(_loc_7 >> 16 & 255);
                    _loc_9 = Number(_loc_7 >> 8 & 255);
                    _loc_10 = Number(_loc_7 & 255);
                    YDU[_loc_4] = 0.299 * _loc_8 + 0.587 * _loc_9 + 0.114 * _loc_10 - 128;
                    UDU[_loc_4] = -0.16874 * _loc_8 + -0.33126 * _loc_9 + 0.5 * _loc_10;
                    VDU[_loc_4] = 0.5 * _loc_8 + -0.41869 * _loc_9 + -0.08131 * _loc_10;
                    _loc_4++;
                    _loc_6++;
                }// end while
                _loc_5++;
            }// end while
            return;
        }// end function

        private function writeBits(param1:BitString) : void
        {
            var _loc_2:* = param1.val;
            while (_loc_3-- >= 0)
            {
                // label
                if (_loc_2 & uint(1 << param1.len--))
                {
                    bytenew = bytenew | uint(1 << bytepos);
                }// end if
                bytepos--;
                if (bytepos < 0)
                {
                    if (bytenew == 255)
                    {
                        writeByte(255);
                        writeByte(0);
                    }
                    else
                    {
                        writeByte(bytenew);
                    }// end else if
                    bytepos = 7;
                    bytenew = 0;
                }// end if
            }// end while
            return;
        }// end function

        private function writeWord(param1:int) : void
        {
            writeByte(param1 >> 8 & 255);
            writeByte(param1 & 255);
            return;
        }// end function

        private function writeByte(param1:int) : void
        {
            byteout.writeByte(param1);
            return;
        }// end function

        private function writeDHT() : void
        {
            var _loc_1:int;
            writeWord(65476);
            writeWord(418);
            writeByte(0);
            _loc_1 = 0;
            while (_loc_1 < 16)
            {
                // label
                writeByte(std_dc_luminance_nrcodes[_loc_1 + 1]);
                _loc_1++;
            }// end while
            _loc_1 = 0;
            while (_loc_1 <= 11)
            {
                // label
                writeByte(std_dc_luminance_values[_loc_1]);
                _loc_1++;
            }// end while
            writeByte(16);
            _loc_1 = 0;
            while (_loc_1 < 16)
            {
                // label
                writeByte(std_ac_luminance_nrcodes[_loc_1 + 1]);
                _loc_1++;
            }// end while
            _loc_1 = 0;
            while (_loc_1 <= 161)
            {
                // label
                writeByte(std_ac_luminance_values[_loc_1]);
                _loc_1++;
            }// end while
            writeByte(1);
            _loc_1 = 0;
            while (_loc_1 < 16)
            {
                // label
                writeByte(std_dc_chrominance_nrcodes[_loc_1 + 1]);
                _loc_1++;
            }// end while
            _loc_1 = 0;
            while (_loc_1 <= 11)
            {
                // label
                writeByte(std_dc_chrominance_values[_loc_1]);
                _loc_1++;
            }// end while
            writeByte(17);
            _loc_1 = 0;
            while (_loc_1 < 16)
            {
                // label
                writeByte(std_ac_chrominance_nrcodes[_loc_1 + 1]);
                _loc_1++;
            }// end while
            _loc_1 = 0;
            while (_loc_1 <= 161)
            {
                // label
                writeByte(std_ac_chrominance_values[_loc_1]);
                _loc_1++;
            }// end while
            return;
        }// end function

        public function encode(param1:BitmapData) : ByteArray
        {
            var _loc_6:int;
            var _loc_7:BitString;
            byteout = new ByteArray();
            bytenew = 0;
            bytepos = 7;
            writeWord(65496);
            writeAPP0();
            writeDQT();
            writeSOF0(param1.width, param1.height);
            writeDHT();
            writeSOS();
            var _loc_2:Number;
            var _loc_3:Number;
            var _loc_4:Number;
            bytenew = 0;
            bytepos = 7;
            var _loc_5:int;
            while (_loc_5 < param1.height)
            {
                // label
                _loc_6 = 0;
                while (_loc_6 < param1.width)
                {
                    // label
                    RGB2YUV(param1, _loc_6, _loc_5);
                    _loc_2 = processDU(YDU, fdtbl_Y, _loc_2, YDC_HT, YAC_HT);
                    _loc_3 = processDU(UDU, fdtbl_UV, _loc_3, UVDC_HT, UVAC_HT);
                    _loc_4 = processDU(VDU, fdtbl_UV, _loc_4, UVDC_HT, UVAC_HT);
                    _loc_6 = _loc_6 + 8;
                }// end while
                _loc_5 = _loc_5 + 8;
            }// end while
            if (bytepos >= 0)
            {
                _loc_7 = new BitString();
                _loc_7.len = bytepos + 1;
                _loc_7.val = (1 << bytepos + 1)--;
                writeBits(_loc_7);
            }// end if
            writeWord(65497);
            return byteout;
        }// end function

        private function initCategoryNumber() : void
        {
            var _loc_3:int;
            var _loc_1:int;
            var _loc_2:int;
            var _loc_4:int;
            while (_loc_4 <= 15)
            {
                // label
                _loc_3 = _loc_1;
                while (_loc_3 < _loc_2)
                {
                    // label
                    category[32767 + _loc_3] = _loc_4;
                    bitcode[32767 + _loc_3] = new BitString();
                    bitcode[32767 + _loc_3].len = _loc_4;
                    bitcode[32767 + _loc_3].val = _loc_3;
                    _loc_3++;
                }// end while
                _loc_3 = -_loc_2--;
                while (_loc_3 <= -_loc_1)
                {
                    // label
                    category[32767 + _loc_3] = _loc_4;
                    bitcode[32767 + _loc_3] = new BitString();
                    bitcode[32767 + _loc_3].len = _loc_4;
                    bitcode[32767 + _loc_3].val = _loc_2-- + _loc_3;
                    _loc_3++;
                }// end while
                _loc_1 = _loc_1 << 1;
                _loc_2 = _loc_2 << 1;
                _loc_4++;
            }// end while
            return;
        }// end function

        private function writeDQT() : void
        {
            var _loc_1:int;
            writeWord(65499);
            writeWord(132);
            writeByte(0);
            _loc_1 = 0;
            while (_loc_1 < 64)
            {
                // label
                writeByte(YTable[_loc_1]);
                _loc_1++;
            }// end while
            writeByte(1);
            _loc_1 = 0;
            while (_loc_1 < 64)
            {
                // label
                writeByte(UVTable[_loc_1]);
                _loc_1++;
            }// end while
            return;
        }// end function

        private function writeAPP0() : void
        {
            writeWord(65504);
            writeWord(16);
            writeByte(74);
            writeByte(70);
            writeByte(73);
            writeByte(70);
            writeByte(0);
            writeByte(1);
            writeByte(1);
            writeByte(0);
            writeWord(1);
            writeWord(1);
            writeByte(0);
            writeByte(0);
            return;
        }// end function

        private function writeSOS() : void
        {
            writeWord(65498);
            writeWord(12);
            writeByte(3);
            writeByte(1);
            writeByte(0);
            writeByte(2);
            writeByte(17);
            writeByte(3);
            writeByte(17);
            writeByte(0);
            writeByte(63);
            writeByte(0);
            return;
        }// end function

        private function processDU(param1:Array, param2:Array, param3:Number, param4:Array, param5:Array) : Number
        {
            var _loc_8:int;
            var _loc_12:int;
            var _loc_13:int;
            var _loc_14:int;
            var _loc_6:* = param5[0];
            var _loc_7:* = param5[240];
            var _loc_9:* = fDCTQuant(param1, param2);
            _loc_8 = 0;
            while (_loc_8 < 64)
            {
                // label
                DU[ZigZag[_loc_8]] = _loc_9[_loc_8];
                _loc_8++;
            }// end while
            var _loc_10:* = DU[0] - param3;
            param3 = DU[0];
            if (_loc_10 == 0)
            {
                writeBits(param4[0]);
            }
            else
            {
                writeBits(param4[category[32767 + _loc_10]]);
                writeBits(bitcode[32767 + _loc_10]);
            }// end else if
            var _loc_11:int;
            while (_loc_11-- > 0 && DU[_loc_11] == 0)
            {
                // label
            }// end while
            if (_loc_11 == 0)
            {
                writeBits(_loc_6);
                return param3;
            }// end if
            _loc_8 = 1;
            while (_loc_8 <= _loc_11)
            {
                // label
                _loc_12 = _loc_8;
                while (DU[_loc_8] == 0 && _loc_8 <= _loc_11)
                {
                    // label
                    _loc_8++;
                }// end while
                _loc_13 = _loc_8 - _loc_12;
                if (_loc_13 >= 16)
                {
                    _loc_14 = 1;
                    while (_loc_14 <= _loc_13 / 16)
                    {
                        // label
                        writeBits(_loc_7);
                        _loc_14++;
                    }// end while
                    _loc_13 = int(_loc_13 & 15);
                }// end if
                writeBits(param5[_loc_13 * 16 + category[32767 + DU[_loc_8]]]);
                writeBits(bitcode[32767 + DU[_loc_8]]);
                _loc_8++;
            }// end while
            if (_loc_11 != 63)
            {
                writeBits(_loc_6);
            }// end if
            return param3;
        }// end function

        private function initQuantTables(param1:int) : void
        {
            var _loc_2:int;
            var _loc_3:Number;
            var _loc_8:int;
            var _loc_4:Array;
            _loc_2 = 0;
            while (_loc_2 < 64)
            {
                // label
                _loc_3 = Math.floor((_loc_4[_loc_2] * param1 + 50) / 100);
                if (_loc_3 < 1)
                {
                    _loc_3 = 1;
                }
                else if (_loc_3 > 255)
                {
                    _loc_3 = 255;
                }// end else if
                YTable[ZigZag[_loc_2]] = _loc_3;
                _loc_2++;
            }// end while
            var _loc_5:Array;
            _loc_2 = 0;
            while (_loc_2 < 64)
            {
                // label
                _loc_3 = Math.floor((_loc_5[_loc_2] * param1 + 50) / 100);
                if (_loc_3 < 1)
                {
                    _loc_3 = 1;
                }
                else if (_loc_3 > 255)
                {
                    _loc_3 = 255;
                }// end else if
                UVTable[ZigZag[_loc_2]] = _loc_3;
                _loc_2++;
            }// end while
            var _loc_6:Array;
            _loc_2 = 0;
            var _loc_7:int;
            while (_loc_7 < 8)
            {
                // label
                _loc_8 = 0;
                while (_loc_8 < 8)
                {
                    // label
                    fdtbl_Y[_loc_2] = 1 / (YTable[ZigZag[_loc_2]] * _loc_6[_loc_7] * _loc_6[_loc_8] * 8);
                    fdtbl_UV[_loc_2] = 1 / (UVTable[ZigZag[_loc_2]] * _loc_6[_loc_7] * _loc_6[_loc_8] * 8);
                    _loc_2++;
                    _loc_8++;
                }// end while
                _loc_7++;
            }// end while
            return;
        }// end function

        private function writeSOF0(param1:int, param2:int) : void
        {
            writeWord(65472);
            writeWord(17);
            writeByte(8);
            writeWord(param2);
            writeWord(param1);
            writeByte(3);
            writeByte(1);
            writeByte(17);
            writeByte(0);
            writeByte(2);
            writeByte(17);
            writeByte(1);
            writeByte(3);
            writeByte(17);
            writeByte(1);
            return;
        }// end function

        private function computeHuffmanTbl(param1:Array, param2:Array) : Array
        {
            var _loc_7:int;
            var _loc_3:int;
            var _loc_4:int;
            var _loc_5:* = new Array();
            var _loc_6:int;
            while (_loc_6 <= 16)
            {
                // label
                _loc_7 = 1;
                while (_loc_7 <= param1[_loc_6])
                {
                    // label
                    _loc_5[param2[_loc_4]] = new BitString();
                    _loc_5[param2[_loc_4]].val = _loc_3;
                    _loc_5[param2[_loc_4]].len = _loc_6;
                    _loc_4++;
                    _loc_3++;
                    _loc_7++;
                }// end while
                _loc_3 = _loc_3 * 2;
                _loc_6++;
            }// end while
            return _loc_5;
        }// end function

        private function fDCTQuant(param1:Array, param2:Array) : Array
        {
            var _loc_3:Number;
            var _loc_4:Number;
            var _loc_5:Number;
            var _loc_6:Number;
            var _loc_7:Number;
            var _loc_8:Number;
            var _loc_9:Number;
            var _loc_10:Number;
            var _loc_11:Number;
            var _loc_12:Number;
            var _loc_13:Number;
            var _loc_14:Number;
            var _loc_15:Number;
            var _loc_16:Number;
            var _loc_17:Number;
            var _loc_18:Number;
            var _loc_19:Number;
            var _loc_20:Number;
            var _loc_21:Number;
            var _loc_22:int;
            var _loc_23:int;
            _loc_22 = 0;
            while (_loc_22 < 8)
            {
                // label
                _loc_3 = param1[_loc_23 + 0] + param1[_loc_23 + 7];
                _loc_10 = param1[_loc_23 + 0] - param1[_loc_23 + 7];
                _loc_4 = param1[_loc_23 + 1] + param1[_loc_23 + 6];
                _loc_9 = param1[_loc_23 + 1] - param1[_loc_23 + 6];
                _loc_5 = param1[_loc_23 + 2] + param1[_loc_23 + 5];
                _loc_8 = param1[_loc_23 + 2] - param1[_loc_23 + 5];
                _loc_6 = param1[_loc_23 + 3] + param1[_loc_23 + 4];
                _loc_7 = param1[_loc_23 + 3] - param1[_loc_23 + 4];
                _loc_11 = _loc_3 + _loc_6;
                _loc_14 = _loc_3 - _loc_6;
                _loc_12 = _loc_4 + _loc_5;
                _loc_13 = _loc_4 - _loc_5;
                param1[_loc_23 + 0] = _loc_11 + _loc_12;
                param1[_loc_23 + 4] = _loc_11 - _loc_12;
                _loc_15 = (_loc_13 + _loc_14) * 0.707107;
                param1[_loc_23 + 2] = _loc_14 + _loc_15;
                param1[_loc_23 + 6] = _loc_14 - _loc_15;
                _loc_11 = _loc_7 + _loc_8;
                _loc_12 = _loc_8 + _loc_9;
                _loc_13 = _loc_9 + _loc_10;
                _loc_19 = (_loc_11 - _loc_13) * 0.382683;
                _loc_16 = 0.541196 * _loc_11 + _loc_19;
                _loc_18 = 1.30656 * _loc_13 + _loc_19;
                _loc_17 = _loc_12 * 0.707107;
                _loc_20 = _loc_10 + _loc_17;
                _loc_21 = _loc_10 - _loc_17;
                param1[_loc_23 + 5] = _loc_21 + _loc_16;
                param1[_loc_23 + 3] = _loc_21 - _loc_16;
                param1[_loc_23 + 1] = _loc_20 + _loc_18;
                param1[_loc_23 + 7] = _loc_20 - _loc_18;
                _loc_23 = _loc_23 + 8;
                _loc_22++;
            }// end while
            _loc_23 = 0;
            _loc_22 = 0;
            while (_loc_22 < 8)
            {
                // label
                _loc_3 = param1[_loc_23 + 0] + param1[_loc_23 + 56];
                _loc_10 = param1[_loc_23 + 0] - param1[_loc_23 + 56];
                _loc_4 = param1[_loc_23 + 8] + param1[_loc_23 + 48];
                _loc_9 = param1[_loc_23 + 8] - param1[_loc_23 + 48];
                _loc_5 = param1[_loc_23 + 16] + param1[_loc_23 + 40];
                _loc_8 = param1[_loc_23 + 16] - param1[_loc_23 + 40];
                _loc_6 = param1[_loc_23 + 24] + param1[_loc_23 + 32];
                _loc_7 = param1[_loc_23 + 24] - param1[_loc_23 + 32];
                _loc_11 = _loc_3 + _loc_6;
                _loc_14 = _loc_3 - _loc_6;
                _loc_12 = _loc_4 + _loc_5;
                _loc_13 = _loc_4 - _loc_5;
                param1[_loc_23 + 0] = _loc_11 + _loc_12;
                param1[_loc_23 + 32] = _loc_11 - _loc_12;
                _loc_15 = (_loc_13 + _loc_14) * 0.707107;
                param1[_loc_23 + 16] = _loc_14 + _loc_15;
                param1[_loc_23 + 48] = _loc_14 - _loc_15;
                _loc_11 = _loc_7 + _loc_8;
                _loc_12 = _loc_8 + _loc_9;
                _loc_13 = _loc_9 + _loc_10;
                _loc_19 = (_loc_11 - _loc_13) * 0.382683;
                _loc_16 = 0.541196 * _loc_11 + _loc_19;
                _loc_18 = 1.30656 * _loc_13 + _loc_19;
                _loc_17 = _loc_12 * 0.707107;
                _loc_20 = _loc_10 + _loc_17;
                _loc_21 = _loc_10 - _loc_17;
                param1[_loc_23 + 40] = _loc_21 + _loc_16;
                param1[_loc_23 + 24] = _loc_21 - _loc_16;
                param1[_loc_23 + 8] = _loc_20 + _loc_18;
                param1[_loc_23 + 56] = _loc_20 - _loc_18;
                _loc_23++;
                _loc_22++;
            }// end while
            _loc_22 = 0;
            while (_loc_22 < 64)
            {
                // label
                param1[_loc_22] = Math.round(param1[_loc_22] * param2[_loc_22]);
                _loc_22++;
            }// end while
            return param1;
        }// end function

    }
}
