package hf.model
{

    public class INI extends Object
    {
        private var _data:XML;
        private static var instance:INI;

        public function INI(param1:XML)
        {
            if (instance != null)
            {
                throw new Error("实例化单例类出错-INI");
            }// end if
            data = param1;
            return;
        }// end function

        public function getMaterialUrl(param1:String) : Array
        {
            var _l:int;
            var i:int;
            var _n:String;
            var _t:String;
            var _s:String;
            var _o:Object;
            var moduleName:* = param1;
            var _a:Array;
            if (data != null)
            {
                var _loc_4:int;
                var _loc_5:* = data.moduleList.module;
                var _loc_3:* = new XMLList("");
                for each (_loc_6 in _loc_5)
                {
                    // label
                    var _loc_7:* = _loc_5[_loc_4];
                    with (_loc_5[_loc_4])
                    {
                        if (@name == moduleName)
                        {
                            _loc_3[_loc_4] = _loc_6;
                        }// end if
                    }// end with
                }// end of for each ... in
                _l = _loc_3.material.length();
                i;
                while (i < _l)
                {
                    // label
                    var _loc_4:int;
                    var _loc_5:* = data.moduleList.module;
                    var _loc_3:* = new XMLList("");
                    for each (_loc_6 in _loc_5)
                    {
                        // label
                        var _loc_7:* = _loc_5[_loc_4];
                        with (_loc_5[_loc_4])
                        {
                            if (@name == moduleName)
                            {
                                _loc_3[_loc_4] = _loc_6;
                            }// end if
                        }// end with
                    }// end of for each ... in
                    _n = _loc_3.material[i].@url;
                    var _loc_4:int;
                    var _loc_5:* = data.moduleList.module;
                    var _loc_3:* = new XMLList("");
                    for each (_loc_6 in _loc_5)
                    {
                        // label
                        var _loc_7:* = _loc_5[_loc_4];
                        with (_loc_5[_loc_4])
                        {
                            if (@name == moduleName)
                            {
                                _loc_3[_loc_4] = _loc_6;
                            }// end if
                        }// end with
                    }// end of for each ... in
                    _t = _loc_3.material[i].@statusText;
                    var _loc_4:int;
                    var _loc_5:* = data.moduleList.module;
                    var _loc_3:* = new XMLList("");
                    for each (_loc_6 in _loc_5)
                    {
                        // label
                        var _loc_7:* = _loc_5[_loc_4];
                        with (_loc_5[_loc_4])
                        {
                            if (@name == moduleName)
                            {
                                _loc_3[_loc_4] = _loc_6;
                            }// end if
                        }// end with
                    }// end of for each ... in
                    _s = _loc_3.material[i].@size;
                    _o = new Object();
                    _o["url"] = _n;
                    _o["statusText"] = _t;
                    _o["size"] = _s;
                    _a.push(_o);
                    i = i++;
                }// end while
            }// end if
            return _a;
        }// end function

        public function set data(param1:XML) : void
        {
            _data = param1;
            return;
        }// end function

        public function getPort() : int
        {
            var _loc_1:int;
            if (data != null)
            {
                _loc_1 = data.security.@port;
            }// end if
            return _loc_1;
        }// end function

        public function getPostUrl() : String
        {
            var _loc_1:String;
            if (data != null)
            {
                _loc_1 = data.postUrl.@url;
            }// end if
            if (_loc_1 == "")
            {
                throw new Error("请求地址出错");
            }// end if
            return _loc_1;
        }// end function

        public function get data() : XML
        {
            return _data;
        }// end function

        public function loadingUrl() : String
        {
            var _loc_1:String;
            if (data != null)
            {
                _loc_1 = data.loading.@url;
            }// end if
            if (_loc_1 == "")
            {
                throw new Error("请求地址出错");
            }// end if
            return _loc_1;
        }// end function

        public function getModuleUrl(param1:String) : Object
        {
            var moduleName:* = param1;
            var returnValue:* = new Object();
            if (data != null)
            {
                var _loc_4:int;
                var _loc_5:* = data.moduleList.module;
                var _loc_3:* = new XMLList("");
                for each (_loc_6 in _loc_5)
                {
                    // label
                    var _loc_7:* = _loc_5[_loc_4];
                    with (_loc_5[_loc_4])
                    {
                        if (@name == moduleName)
                        {
                            _loc_3[_loc_4] = _loc_6;
                        }// end if
                    }// end with
                }// end of for each ... in
                returnValue["url"] = _loc_3.@url;
                var _loc_4:int;
                var _loc_5:* = data.moduleList.module;
                var _loc_3:* = new XMLList("");
                for each (_loc_6 in _loc_5)
                {
                    // label
                    var _loc_7:* = _loc_5[_loc_4];
                    with (_loc_5[_loc_4])
                    {
                        if (@name == moduleName)
                        {
                            _loc_3[_loc_4] = _loc_6;
                        }// end if
                    }// end with
                }// end of for each ... in
                returnValue["statusText"] = _loc_3.@statusText;
                var _loc_4:int;
                var _loc_5:* = data.moduleList.module;
                var _loc_3:* = new XMLList("");
                for each (_loc_6 in _loc_5)
                {
                    // label
                    var _loc_7:* = _loc_5[_loc_4];
                    with (_loc_5[_loc_4])
                    {
                        if (@name == moduleName)
                        {
                            _loc_3[_loc_4] = _loc_6;
                        }// end if
                    }// end with
                }// end of for each ... in
                returnValue["size"] = _loc_3.@size;
            }// end if
            if (returnValue == "")
            {
                throw new Error("模块地址出错" + moduleName);
            }// end if
            return returnValue;
        }// end function

        public function getHost() : String
        {
            var _loc_1:String;
            if (data != null)
            {
                _loc_1 = data.security.@host;
            }// end if
            return _loc_1;
        }// end function

        public static function getInstance(param1:Object = null) : INI
        {
            var _loc_2:* = XML(param1);
            if (instance == null)
            {
                instance = new INI(_loc_2);
            }// end if
            return instance;
        }// end function

    }
}
