
function Map(){
	this.elements = new Array();
	
	this.size = function() {
		return this.elements.length;
	}

	this.isEmpty = function() {
		return (this.elements.length < 1);
	}

	this.clear = function() {
		this.elements = new Array();
	}

	this.put = function(_key, _value) {
		this.elements.push({key:_key, value:_value});
	}

	this.remove = function(_key) {
		var bln = false;
		try  {   
			for (i = 0; i < this.elements.length; i++) {  
				if (this.elements[i].key == _key){
					this.elements.splice(i, 1);
					return true;
				}
			}
		}catch(e){
			bln = false;    
		}
		return bln;
	}
   
	this.containsKey = function(_key) {
		var bln = false;
		try {
			for (i = 0; i < this.elements.length; i++) {  
				if (this.elements[i].key == _key){
					bln = true;
				}
			}
		}catch(e) {
			bln = false;    
		}
		return bln;
	}
    
	this.get = function(_key) {
		try{   
			for (i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return this.elements[i].value;
				}
			}
		}catch(e) {
			return null;   
		}
	}

	this.element = function(_index) {
		if (_index < 0 || _index >= this.elements.length)
		{
			return null;
		}
		return this.elements[_index];
	}
}
function Validator() {
	this.map = new Map();
	this.errors = new Errors();
	this.map.put("ip",/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/);
	this.map.put("datetime",/^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/);
	this.map.put("date",/^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/);
	this.map.put("date+blank", new isDateOrBlank());
	this.map.put("time",/^(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/);
	this.map.put("email", /^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}$/);
	this.map.put("telephone",/^\d{3}-\d{8}|\d{3}-\d{7}|\d{4}-\d{7}|\d{4}-\d{6}$/);
	this.map.put("mobile", /^(86)*0*13\d{9}$/);
	this.map.put("phone",/^\d{3}-\d{8}|\d{3}-\d{7}|\d{4}-\d{7}|\d{4}-\d{6}|(86)*0*13\d{9}$/);
	this.map.put("zip", /^[1-9]{1}\d{5}$/);
	this.map.put("float", /^(-?\d+)(\.\d+)?$/);
	this.map.put("+float",/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/);
	this.map.put("+float+0", /^\d+(\.\d+)?$/);
	this.map.put("-float",/^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/);
	this.map.put("-float+0", /^((-\d+(\.\d+)?)|(0+(\.0+)?))$/);
	this.map.put("integer", /^-?\d+$/);
	this.map.put("+integer", /^[0-9]*[1-9][0-9]*$/);
	this.map.put("+integer+0", /^\d+$/);
	this.map.put("-integer", /^-[0-9]*[1-9][0-9]*$/);
	this.map.put("-integer+0", /^((-\d+)|(0+))$/);
	this.map.put("null", new isNull());
	this.map.put("blank", new isBlank());
	this.map.put("notnull", new isNotNull());
	this.map.put("notblank", new isNotBlank());
	this.map.put("blankornull", new isBlankOrNull());
	this.map.put("greater", new isGreater());

	// test the value
	this.test = function(_key, _error, _value) {
		// arguments < 3
		if (arguments.length < this.test.length) {
			this.errors.add("[BUG] Fatal error: Invalid parameters number (Validator: Test).");
			return true;
		}

		var validator = this.map.get(_key);
		if (validator == void(0)) {
			this.errors.add("[BUG] Fatal error: '" + _key + "' no found.");
			return true;
		}
		// replace , when validate integer and float
		if(_key.indexOf("integer")!=-1 || _key.indexOf("float")!=-1){
			if(_value!=null){
				arguments[2]=_value.replace(/,/g, "");
			}
		}

		var args = "";
		for (var i = 2; i < arguments.length; i++) {
			if (args == "")
				args = "arguments[" + i + "]";
			else
				args = args + "," + "arguments[" + i + "]";
		}

		var func = "validator.test(" + args + ")";

		var pass = eval(func); // call this.validator.test function

		if (!pass) {
			this.errors.add(_error);
			return false;
		}

		return true;
	}

	// return errorString
	this.errorString = function() {
		return this.errors.toString();
	}

	// if has error
	this.hasErrors = function() {
		return (this.errors.size() > 0)
	}

	// errors size
	this.size = function() {
		return this.errors.size();
	}

	// clear the errors
	this.clear = function() {
		this.errors.clear();
	}

	// direct add error to validator
	this.addError = function(_error) {
		this.errors.add(_error);
	}

	// output error string
	this.writeErrors = function(obj, name) {
		var doc = obj.document;
		doc.clear();
		doc.writeln("<HTML>");
		doc.writeln("<HEAD>");
		doc.writeln("  <title>errors</title>");
		doc.writeln("  <Meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		doc.writeln("  <link rel='stylesheet' type='text/css' href='./../../css/main.css'>");
		doc.writeln("  <link rel='stylesheet' type='text/css' href='./../../css/blue.css'>");
		doc.writeln("</HEAD>");
		doc.writeln("<BODY topmargin='5' bottommargin='0' leftmargin='0' rightmargin='0'>");
		doc.writeln("<font color='#FF0000'><strong>");
		doc.writeln(this.errors.toString());
		doc.writeln("</strong></font>");
		doc.writeln("<\/BODY>");
		doc.writeln("<\/HTML>");
		doc.close();

		document.all[name].style.height = this.errors.size() * 19 + 13;
	}
}

function isDateOrBlank() {
	this.test = function(_value) {
		var reg = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$/;

		return (reg.test(_value) || (_value == ""));
	}
}

function isNull() {
	this.test = function(_value) {
		return (_value == void(0));
	}
}

function isNotNull() {
	this.test = function(_value) {
		return (_value != void(0));
	}
}

function isBlank() {
	this.test = function(_value) {
		return (_value == "");
	}
}

function isNotBlank() {
	this.test = function(_value) {
		if(_value == null){
			return false;
		}
		var strValue = ""+ _value;
		var noSpaceStr = strValue.replace(/(^\s+)|\s+$/g,"");
		return (noSpaceStr.length > 0);
	}
}

function isBlankOrNull() {
	this.test = function(_value) {
		return (_value == void(0)) || (_value == "");
	}
}

function isGreater() {
	this.test = function(_value1, _value2) {
		return (_value1 > _value2);
	}
}

function Errors() {
	this.map = new Map();

	this.size = function() {
		return this.map.size();
	}

	this.isEmpty = function() {
		return this.map.isEmpty();
	}

	this.clear = function() {
		this.map.clear();
	}

	this.add = function(_value) {
		this.map.put("global", _value);
	}

	this.toString = function() {
		var str = "";
		for (var i = 0; i < this.map.size(); i++) {
			var ele = "<li>" + this.map.element(i).value + "</li>";
			if (str == "") {
				str = ele;
			} else {
				str = str + "<br>" + ele;
			}
		}
		return str;
	}
}