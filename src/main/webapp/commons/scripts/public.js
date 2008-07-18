function $N() {
	return document.getElementsByName(arguments[0]);
}
function doSelectAll(str) {
	$A($N(str)).each(function(node) {
		if (!node.checked) {
			node.checked = true;
		}
	});
}
function doUnSelectAll(str) {
	$A($N(str)).each(function(node) {
		if (node.checked) {
			node.checked = false;
		} else {
			node.checked = true;
		}
	});
}
function suckerfish(type, tag, parentId) {
	if (window.attachEvent) {
		window.attachEvent("onload", function() {
			var sfEls = (parentId == null) ? document.getElementsByTagName(tag) : document.getElementById(parentId).getElementsByTagName(tag);
			type(sfEls);
		});
	}
}
sfFocus = function(sfEls) {
	for (var i = 0; i < sfEls.length; i++) {
		sfEls[i].onfocus = function() {
			this.className += "sffocus";
			if (this.value == this.defaultValue) {
				this.value = "";
			}
		}
		sfEls[i].onblur = function() {
			this.className = this.className.replace(new RegExp("sffocus\\b"), "");
			if (this.value == "") {
				this.value = this.defaultValue;
			}
		}
	}
}
//suckerfish(sfFocus, "input");
//suckerfish(sfFocus, "textarea");
//suckerfish(sfFocus, "select");
document.write("<div id='__smanDisp' style='position:absolute;display:none;" + this.style + "' onbulr> </div>");
document.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
function getAbsoluteHeight(ob) {
	return ob.offsetHeight
}
function getAbsoluteWidth(ob) {
	return ob.offsetWidth
}
function getAbsoluteLeft(ob) {
	var s_el = 0;
	el = ob;
	while (el) {
		s_el = s_el + el.offsetLeft;
		el = el.offsetParent;
	};
	return s_el
}

function getAbsoluteTop(ob) {
	var s_el = 0;
	el = ob;
	while (el) {
		s_el = s_el + el.offsetTop;
		el = el.offsetParent;
	};
	return s_el
}
var ajaxtools={url:"",parameters:"",method:"get"};
function smanPromptList(objInputId) {
	this.style = "background:#E8F7EB;border: 1px solid #CCCCCC;font-size:14px;cursor: default;"
	window.onload = function() {
		var objouter=$("__smanDisp") // 显示的DIV对象
		var objInput=$(objInputId); // 文本框对象
		var selectedIndex = -1;
		var intTmp; // 循环用的:)
		if (objInput == null) {
			alert('smanPromptList初始化失败:没有找到"' + objInputId + '"文本框');
			return;
		}
		// 文本框失去焦点
		objInput.onblur = function() {
			objouter.style.display = 'none';
		}
		window.onfocus = function() {
			objouter.style.display = 'none';
		}
		// 文本框按键抬起
		objInput.onkeyup = checkKeyCode;
		// 文本框得到焦点
		objInput.onfocus = checkAndShow;
		function checkKeyCode() {
			var ie = (document.all) ? true : false
			if (ie) {
				var keyCode = event.keyCode
				if (keyCode == 40 || keyCode == 38) { // 下上
					var isUp = false
					if (keyCode == 40)
						isUp = true;
					chageSelection(isUp)
				} else if (keyCode == 13) {// 回车
					outSelection(selectedIndex);
				} else {
					checkAndShow()
				}
			} else {
				checkAndShow()
			}
			divPosition()
		}
		function checkAndShow() {
			var strInput = objInput.value
			if (strInput != "") {
				divPosition();
				selectedIndex = -1;
				//远程得到数据
				new Ajax.Request(ajaxtools.url,{method:ajaxtools.method,parameters:ajaxtools.parameters+strInput,onComplete:function(rep){
					objouter.innerHTML = "";
					var re=eval('('+rep.responseText+')').arrList;
					for (intTmp = 0;intTmp<re.length; intTmp++) {
						addOption(re[intTmp], strInput);
					}
				}});				
				objouter.style.display = '';
			} else {
				objouter.style.display = 'none';
			}
			function addOption(value, keyw) {
				var v = value.replace(keyw, "<b><font color=red>" + keyw + "</font></b>");
				objouter.innerHTML += "<div onmouseover=\"this.className='sman_selectedStyle'\" onmouseout=\"this.className=''\" onmousedown=\"document.getElementById('"+ objInputId + "').value='" + value + "';\">" + v + "</div>"
			}
		}
		function chageSelection(isUp) {
			if (objouter.style.display == 'none') {
				objouter.style.display = '';
			} else {
				if (isUp)
					selectedIndex++
				else
					selectedIndex--
			}
			var maxIndex = objouter.children.length - 1;
			if (selectedIndex < 0) {
				selectedIndex = 0
			}
			if (selectedIndex > maxIndex) {
				selectedIndex = maxIndex
			}
			for (intTmp = 0; intTmp <= maxIndex; intTmp++) {

				if (intTmp == selectedIndex) {
					objouter.children[intTmp].className = "sman_selectedStyle";
				} else {
					objouter.children[intTmp].className = "";
				}
			}
		}
		function outSelection(Index) {
			if (!objouter.children[Index])
				return;
			objInput.value = objouter.children[Index].innerText;
			objouter.style.display = 'none';
		}
		function divPosition() {
			objouter.style.top = getAbsoluteHeight(objInput) + getAbsoluteTop(objInput);
			objouter.style.left = getAbsoluteLeft(objInput);
			objouter.style.width = getAbsoluteWidth(objInput)
		}
	}
}
	/**
	 * 获得一个元素所在的form
	 */
	function getOwnerForm(elementObj){
		//参数不能为空
		if(elementObj == null){
			alert("传入的对象不能为null");
			return null;
		}
		//不是html对象
		if(elementObj.tagName == null){
			alert("传入的对象不是html标签对象");
			return null;
		}
		//自己是form就接返回
		if(elementObj.tagName.toUpperCase() == "FORM"){
			return elementObj;
		}
		//获得自己的父对象
		var parentElementObj = elementObj.parentElement;
		while(parentElementObj != null){
			//如果是form就返回
			if(parentElementObj.tagName.toUpperCase() == "FORM"){
				return parentElementObj;
			}
			//不是继续往上找
			parentElementObj = parentElementObj.parentElement;
		}
		//找不到就返回null;
		return null;
	}
	/**
	 * 只获取字符串中的数字和+-.；为了兼容各种格式的数字(344,234,234.34)
	 */
	function getNumberChar(str){
		if(str==null){
			return "";
		}else{
			var nstr = str+"";
			var number = "";
			for(var i=0;i<nstr.length;i++){
				var c = nstr.charAt(i);
				if((c>='0' && c<='9')||c=='.'||c=='+'||c=='-'){
					number+=c;
				}
			}
			return number;
		}
	}
	/**
	 * 从字符串解析出整数
	 */
	function parseIntNumber(str){
		return parseInt(getNumberChar(str));
	}
	/**
	 * 从字符串解析出浮点数
	 */
	function parseFloatNumber(str){
		return parseFloat(getNumberChar(str));
	}
	//不能输入全角
	function quanjiao(obj)
	{
	    var str=obj.value;
	    if (str.length>0)
	    {
	        for (var i = str.length-1; i >= 0; i--)
	        {
	            unicode=str.charCodeAt(i);
	            if (unicode>65280 && unicode<65375)
	            {
	                alert("不能输入全角字符，请输入半角字符");
	                obj.value=str.substr(0,i);
	            }
	        }
	    }
	} 