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
		if(str==""){
			return "0.00";
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
	
	function formatInputNumberReturnZero(inputObj){
	  if(inputObj.value==''){
	     inputObj.value=number_format(0);
	  }else{
	     var numStr = inputObj.value.toString().replace(/\$|\,|\s/g,'');
	     if(inputObj.value.length > 0  && numStr.search(/^(-|\+)?\d+(\.\d+)?$/) != -1){
	   		inputObj.value=number_format(inputObj.value);
	   }
	  }
	}
	
	//格式化输入框中的数字
	function formatInputNumber(inputObj){
		//在有值的情况下，而且是数字才格式化
	   var numStr = inputObj.value.toString().replace(/\$|\,|\s/g,'');
	   if(inputObj.value.length > 0  && numStr.search(/^(-|\+)?\d+(\.\d+)?$/) != -1){
	   		inputObj.value=number_format(inputObj.value);
	   }
	}
	
	//格式化金额
	function number_format(num){
        num = num.toString().replace(/\$|\,|\s/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    cents = num%100;
	    num = Math.floor(num/100).toString();
	    if(cents<10)
	    cents = "0" + cents;
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num + '.' + cents);
   }
   
   //文本不能输入非数字
   	function valNum(ev)   
	{   
  		 if(window.event.shiftKey)   
        {   
           ev.returnValue = "";   
        }   
        else   
        {   
   		 var e = ev.keyCode;   
  	  //允许的有大、小键盘的数字，左右键，backspace, delete, Control + C, Control + V   
  
    if(e != 188 && e != 190  &&e != 110 && e != 48 && e != 49 && e != 50 && e != 51 && e != 52 && e != 53 && e != 54 && e != 55 && e != 56 && e != 57 && e != 96 && e != 97 && e != 98 && e != 99 && e != 100 && e != 101 && e != 102 && e != 103 && e != 104 && e != 105 && e != 37 && e != 39 && e != 13 && e != 8 && e != 46 && e != 9)   
    {   
        if(ev.ctrlKey == false)   
        {   
            //不允许的就清空!   
            ev.returnValue = "";   
        }   
        else   
        {   
            //验证剪贴板里的内容是否为数字!   
            valClip(ev);   
        }   
    }   
    }   
} 

function delOption(opName,opValue){
	var op = document.getElementById(opName);
	var len  = op.options.length;
	for(var i=0;i<len; i++){
		if ( op.options[i].value == opValue ){
			op.options.remove(i);
			break;
		}
	}	
}
// 窗口居中
   function openWin(u, w, h) {
            var l = (screen.width - w) / 2;
            var t = (screen.height - h) / 2;
            var s = 'width=' + w + ', height=' + h + ', top=' + t + ', left=' + l;
            s += ', toolbar=no, scrollbars=yes, menubar=no, location=no, resizable=yes';
            open(u, 'oWin', s);
     }
     
     function openWin2(u, w, h,name) {
            var l = (screen.width - w) / 2;
            var t = (screen.height - h) / 2;
            var s = 'width=' + w + ', height=' + h + ', top=' + t + ', left=' + l;
            s += ', toolbar=no, scrollbars=yes, menubar=no, location=no, resizable=yes';
            open(u, name , s);
     }
     
   //检查自己输入的时间是否大于等于当前时间
   //如果大于等于返回true，反之返回false
 function compareDateWithNowTime(strDate)
{
	var stardDate=new Date();
	var y=stardDate.getYear();
	var m=stardDate.getMonth();
	var d=stardDate.getDate();
	var opDate=strDate.split("-");
	var opy=parseInt(opDate[0]);
	var opm=parseInt(opDate[1]);
	var opd=parseInt(opDate[2]);
	var dateOne=new   Date(y,m,d,0,0,0,0);
	var dateTwo=new Date(opy,opm-1,opd,0,0,0,0);
	return ((dateOne.getTime()-dateTwo.getTime())<=0);
		
}

function compareTwoDateWithStard(beginDate,endDate,days){
	var beginStr = beginDate.split("-");
	var endStr = endDate.split("-");
	var begin = new Date(beginStr[0],beginStr[1]-1,beginStr[2],0,0,0);
	var end = new Date(endStr[0],endStr[1]-1,endStr[2],0,0,0);
	var differenceValue =Math.abs((begin.getTime()-end.getTime())/(1000*60*60*24));
	if(differenceValue >=days){//如果两个日期之间的值大于等于比较的对象,返回false
		return false;
	}else{
		return true;
	}

}

//查询合同明细
function openCon(obj){
	var conUrl="/yx/contract/formalContractManage/formalContractManage.action?cmisysid="+obj+"&whereCome=4&randomNum="+Math.random();
	openWin(conUrl,1000,600);				
}

//增加下拉选项 是否选中（0不选，1选中）
function addOptionX(optionName , sValue ,sTest  , force ){
	var selectX = document.getElementById(optionName);
	var i=0;
	while( i < selectX.length){
		if(	selectX.options[i].text == sTest ){
			selectX[i].selected = true;
			return;
		}
		i++;
	}
	opt = new Option(sTest,sValue);
	selectX.add(opt);
	if(force==1){
		opt.selected = true;
	}
}
/////传入一个值，然后判断是否为float类型的数字
function validateSelfFloat(val){
		var numStr = val.toString().replace(/\$|\,|\s/g,'');
		var reg = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
		return reg.test(numStr);
	}
////传入一个数组。判断数组里面的元素是否重复
///return true表示不重复，false表示重复
function checkValueisRepate(arr){
	if(arr==null||arr.length==0 ){
		return true;//不重复
	}else{
		var flag = true;   //假设不重复 
        for(var i = 0;i < arr.length - 1;i++){ //循环开始元素 
	         for(var j = i + 1;j < arr.length;j++){ //循环后续所有元素 
	             //如果相等，则重复 
	             if(arr[i] == arr[j]){ 
	                       flag = false; //设置标志变量为重复 
	                       break;      //结束循环 
	             } 
	         } 
        } 
        return flag;
	}
}
   