nulldocument.domain='xiaonei.com';
function __DOMAIN() {
} 
var DOMAIN = new __DOMAIN();
DOMAIN.img = "http://img.xiaonei.com/"; 
function getEl(el) {
	return document.all ? document.all[el] : document.getElementById(el);
}
function setElementStyle(id, style) {
	getEl(id).className = style;
}
function check(id,defaultvalue,returnStr) {
 if(getEl(id).value == "") {
	 alert(defaultvalue + "不能为空");
	 getEl(id).select();
	 return false;
 }
 if(getEl(id).value == defaultvalue) {
	 alert(returnStr);
	 getEl(id).select();
	 return false;
 }
 return true;
}
function hideLayer(ALayerName) {
	if (ALayerName != "") 
	{ 
		if (document.getElementById)
			 document.getElementById(ALayerName).style.display = "none";
		else if (document.all)
			 document.all[ALayerName].style.display = "none";
		else if (document.layers)
			 eval("document." + ALayerName + ".display = 'none'");
	}
}
function showLayer(ALayerName) {
	if (ALayerName != "") {
		if (document.getElementById)
			 document.getElementById(ALayerName).style.display = "block";
		else if (document.all)
			 document.all[ALayerName].style.display = "block";
		else if (document.layers)
			 eval("document." + ALayerName + ".display = 'block'");     
	} 
}
function display(id, on_off) {
	var el = document.all ? document.all[id] : document.getElementById(id);
	if(el) el.style.display = on_off ? '' : 'none';
}
function add_comsch(comsch) {
	var i = ++getEl('max_com').value;
	if(i < 6) {
		if(comsch=="com")
		{
			getEl("comName"+i).value="";
			getEl("comTitle"+i).value="";
			getEl("comStarTime"+i).value="";
			getEl("comEndTime"+i).value="";
			display("comdiv"+i, true);
		}
		else
		{
			getEl("schName"+i).value="";
			display("schdiv"+i,true); 
		}
	}
}

function onReport(threadId, postId) {
	var win;
	var bl=confirm('本贴含有违规内容，将向站长举报。继续？'); 
	var string = "/Report.do?postId="; 
	string += postId;
	string += "&threadId=";
	string += threadId;
	if(bl){
		win=window.open(string, 'editPost', 'left=100,top=100,width=550,height=350,status=no,toolbar=no,menubar=no,scrollbars,resizable=yes');
		win.focus();
	}
	return false;
}
function LTrim(str) {
	var whitespace = new String(" \t\n\r");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(0)) != -1)
	{
		var j=0, i = s.length;
		while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
		{
			j++;
		}
		s = s.substring(j, i);
	}
	return s;
}
function RTrim(str) {
	var whitespace = new String(" \t\n\r");
	var s = new String(str);
	if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
	{
		var i = s.length - 1;
		while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
		{
			i--;
		}
		s = s.substring(0, i+1);
	}
	return s;
}
function Trim(str) {
	return RTrim(LTrim(str));
}
function checkISBN(isbn) {
	return !/(?=.{13}$)\d{1,5}([-])\d{1,7}\1\d{1,6}\1(\d|X|x)/.test(isbn);
}
function checkNum (str) {
	return!/\D/.test(str);
}
function isValidDate(str) {
	return /^(\d{1,4})(-|\/)(\d{2})\2(\d{2})$/.test(str);
}
function isEmail(email) {
	if(email.length==0) {
		alert("请填写电子邮件地址，否则我们将无法与您联系。");
		return false;
	}
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(!filter.test(email)) {  
		alert("抱歉，电子邮箱格式不对或者包含不合法字符");
		return false;
	} 
	return true;
}
// Get Absolute X Position of HTML Element
function findPosX(obj) {
  var curleft = 0;
  if (obj.offsetParent) {
    while (obj.offsetParent) {
      curleft += obj.offsetLeft
      obj = obj.offsetParent;
    }
  }
  else if (obj.x)
    curleft += obj.x;
  return curleft;
}
// Get Absolute Y Position of HTML Element
function findPosY(obj) {
  var curtop = 0;
  if(obj.offsetParent) {
    while (obj.offsetParent) {
      curtop += obj.offsetTop
      obj = obj.offsetParent;
    }
  }
  else if (obj.y)
    curtop += obj.y;
  return curtop;
}
function mousePosX(e) {
  var posx = 0;
  if (!e) var e = window.event;
  if (e.pageX)
    posx = e.pageX;
  else if (e.clientX && document.body.scrollLeft)
    posx = e.clientX + document.body.scrollLeft;
  else if (e.clientX && document.documentElement.scrollLeft)
    posx = e.clientX + document.documentElement.scrollLeft;
  else if (e.clientX)
    posx = e.clientX;
  return posx;
}
function mousePosY(e) {
  var posy = 0;
  if (!e) var e = window.event;
  if (e.pageY)
    posy = e.pageY;
  else if (e.clientY && document.body.scrollTop)
    posy = e.clientY + document.body.scrollTop;
  else if (e.clientY && document.documentElement.scrollTop)
    posy = e.clientY + document.documentElement.scrollTop;
  else if (e.clientY)
    posy = e.clientY;
  return posy;
}
// Debug Print to div on page
function debugOut(debugOutput) {
  if($('debugout') ) {
    $('debugout').style.overflow = "auto";
    $('debugout').innerHTML = debugOutput + "<br>" + $('debugout').innerHTML;
  }
}
function limitLen(str,len,escape) {
	if(escape) {
		str.replace('/</g','&lt;');
	 	str.replace('/>/g','&gt;');
		str.replace('/&/g','&amp;');
		str.replace('/#/g','&#35;');
		str.replace('/(/g','&#40;');
		str.replace('/)/g','&#41;');
		str.replace('/"/g','&#34;');
		str.replace('/\'/g','&#39;');
	}
	if(str.length>len) {
		return false;
	} else {
		return true;
	}
}
function cc(event)
{
  var e,r;
  if(event.srcElement)
    {
      e = event.srcElement;
      r = e.createTextRange();
      r.moveStart('character',0);
      r.collapse(true);
      r.select();
    }
  else
    {
      e= event.target;
      e.selectionStart = 0;
      e.selectionEnd = 0;
      return true;
    }
}
function noteme(el) {
	el.parentNode.nextSibling.className = "hey";
}
function dontnoteme(el) {
	el.parentNode.nextSibling.className = "note";
}

/*
function googleAd(height) {
	var Ff = getEl("googleFf"), Pc = getEl("googlePc");		
	if((!Ff)&&(!Pc)) return false;
	var sideAnchor = getEl("googleSideAnchor");
	var googleAdContent = getEl("googleAdContent");
	if(sideAnchor){
		sideAnchor.style.height = google_ad_height + "px";
		var x = findPosX(getEl("sidebar")), y = findPosY(sideAnchor);
		googleAdContent.style.left = x + "px";
		googleAdContent.style.top = y + "px";
		googleAdContent.style.display = "block";
	}
}
window.onload = googleAd;
window.onresize = googleAd;
*/
/*这个语句负责检查之前是否已经有定义好的onload函数；如果有，则将这个函数赋给oldload.如果没有，就定义一个空函数。
这么做是因为在util.js中已经给每个文件定义了一个body.onload()事件处理函数，这个函数用来调整 googleAdsence的位置。
*/
var oldload = (window.onload) ? window.onload : function () {};
window.onload = function() {
	oldload(); 	
	//取得每个文件的<body> ID.每个文件的body.onload函数都定义成 bodyId_onload形式，而bodyId和文件名相同 
	var pageId = document.body.id;
	//下面两句做的事情 1.判断是否定义了 bodyId_onload函数，如果没有，则给regAction定义一个空函数。
	str= 'var regAction; if(typeof('+pageId+'_onload) == "function") { regAction = ' + pageId + '_onload} else { regAction = function(){}};';
	eval(str);
	str = "regAction()";
	eval(str);
};
/*added by binqiang.lai 
  use to get the content of RTE editor.
  binqiang.lai can be reached by Ext.1667/bqq 9293
*/
 
//window.$=function(obj){return typeof(obj)=="string"?document.getElementById(obj):obj}
window.ow=function(win){return getEl(win).contentWindow}
function GetHTML(frameId){
	if(typeof(_DEBUG) != "undefined") {alert("inner GetHTML");}
	if(frameId) {
		return ow(frameId).getContent();
	}
	if(typeof(_DEBUG) != "undefined") {alert("after getContent");}
	return null ;
}
function isEmpty(frameId) {
	if(frameId) {
		return ow(frameId).isEmpty();
	}
}
function SetHTML(sz,frameId){
	if(frameId){
		ow(frameId).setHtml(sz);
	}
}
function SetFocus(frameId){
	if(frameId){
		ow(frameId).setFocus();
	}
}
function closeInfoWnd(name) {
	try {
		div = document.getElementById(name);
		if (div) {
			document.body.removeChild(div);
			delete div;
			div = null;
		}
	} catch(E) {}
}

var IM = new Object();
var web5q = 0;

function setPos(event,element,width) {
	alert(1)
	alert(event)
	var posx=findPosX(getParentNode(event));
	var posy=findPosY(getParentNode(event));
	alert(posx+":"+posy)
	element.style.left=posx-width-80+"px";
	element.style.top=posy+20+"px";
}

IM.setPos=function(el,element){
	var posx=findPosX(el);
	var posy=findPosY(el);
	element.style.left=posx-20+"px";
	element.style.top=posy+20+"px";
}
function downloadIM(tp){
	window.location = "http://im.xiaonei.com/setup/XiaoNeiSetup.exe";
	closeInfoWnd('ImDownload');
}

IM.getimv = function() {
	var version = "";
	try {
		web5q = web5q || new ActiveXObject("QImWeb.ImWebObj");
		version = web5q.GetImVersion();
	} catch(e){
	}
	return version;
}

IM.startIM = function(username, passwd, f) {
	try {
		web5q = web5q || new ActiveXObject("QImWeb.ImWebObj");
		if(web5q != null) {
			if(f == 1) {
				web5q.Start5QIMNew(username, passwd);
			} else {
				web5q.Start5QIM(username, passwd);
			}
		}else{
	           IM.bigDownload(event, tp);
	        }
	} catch(e) {
	}
}

IM.openIM = function(username, passwd, f, event, tp) {
	try {
		web5q = web5q || new ActiveXObject("QImWeb.ImWebObj");
		if(web5q != null) {
			if(f == 1) {
				web5q.Start5QIMPopupNew(username, passwd);
			} else {
				web5q.Start5QIMPopup(username, passwd);
			}
		} else {
			IM.bigDownload(event, tp);
		}
	} catch(e) {
		IM.bigDownload(event, tp);
	}
}
IM.download=function(){
	closeInfoWnd('ImDownload');
	var div = document.createElement("div");
	div.id = "ImDownload";
	div.className = "popupwrap";
	div.style.zIndex = "5000";
	var html = "<div class=\"popup\"><h4>浏览器不支持或未安装校内通</h4><p>下载登录校内通后就可以聊天了:)<br /><a href=\"http://im.xiaonei.com\" target=\"_blank\">了解校内通</a></p><p class=\"operation\"><input type=\"button\" value=\"立即下载\" class=\"subbutton\" onclick=\"javascript:downloadIM(78);\" /><input type=\"button\" value=\"关闭\" class=\"canbutton\" onclick=\"closeInfoWnd('ImDownload');\" /></p></div>";
 	div.innerHTML = html;
	document.body.appendChild(div);
	IM.setPos(IM.srcEl,div,300);
	div.style.display = "block";
}

IM.log = function(gid,uid) {
	var myDate = new Date;
	var url = 'logIM.do';
 	var pars = "c=1&gid=" + gid 
		+ "&hid=" + uid 
		+ '&t=' + myDate.getTime();
	var myAjax = new Ajax.Request(
				url, 
				{
							method: 'get', 
							parameters: pars 
				});

}
IM.srcEl;
IM.myid;
IM.toid;
IM.em;

function writepipe(uin, name) {
	if(uin > 0) {
	  var s = GetCookie('_pipe');
	  if (s) s += ':';
	  SetCookie('_pipe', s + uin + ':' + escape(name), null, '/', 'xiaonei.com');
	}
  
  var wistate = GetCookie('_wi');  
  if ('opening' == wistate) {
  } else if ('running' == wistate) {
  } else {
    SetCookie('_wi', 'opening', null, '/', 'xiaonei.com'); // lock
    window.wiw = window.open('http://xiaonei.com/webpager.do?toid=' + uin, '_blank', 'height=600,width=650,resizable=yes,location=yes');

  if(window.wiw_checker)
	  window.clearInterval(window.wiw_checker);
  window.wiw_checker = window.setInterval(
	  function(){
			if(window.wiw.closed)
			{
				// alert('open fail, opener clear the cookie!');
				window.clearInterval(window.wiw_checker);
				SetCookie('_wi', '', null, '/', 'xiaonei.com');
			}
		}
		, 1000);
    return true;
  };
  
  try {
    if (window.wiw) window.wiw.focus();
  } catch(e) {}
  return false;
};

function tnx2(event, myid, toid, em, name)
{
  if (IM.getimv() == '') {
    writepipe(toid, name);
  } else {
 	  IM.srcEl = event.srcElement;
 	  IM.myid = myid;
 	  IM.toid = toid;
 	  IM.em = em;
 	  try {
 		  var myAjax = new Ajax.Request(
 			  "tnx.do",
 			  {
 				  method: 'get',
 				  parameters: 'v=' + IM.getimv(),
 				  onComplete: tnxy2,
 				  onFailure: tnxn
 			  });
 	  }catch(e){
     }
  }
}

function tnx()
{
	try {
		var myAjax = new Ajax.Request(
			"tnx.do",
			{
				method: 'get',
				parameters: 'v=' + IM.getimv(),
				onComplete: tnxy,
				onFailure: tnxn
			});
	}catch(e){
  }
}
function tnxy(r)
{
	var p = r.responseText;
	IM.startIM(tnxe, p.substring(0, p.length - 1), p.substring(p.length - 1, p.length));
}
function tnxy2(r)
{
	var p = r.responseText;
	if(document.all) {
		try {
			IM.startIM(IM.em, p.substring(0, p.length - 1), p.substring(p.length - 1, p.length));
		} catch(e) {
			IM.download();
		}
		if(IM.toid > 0) {
			try {
				web5q.StartChat(IM.myid, IM.toid);
 			} catch(e) {
			}
		}
	}else{
		alert("你的浏览器不支持此功能！");
	}
	IM.log(IM.toid, IM.myid);
}

function tnxn(t){
}

function getErrorCode(str) {
	var myDate = new Date;
	var url = 'pages/jsError.jsp';
 	var pars = 'errorStr='+ str 
		 + '&t=' + myDate.getTime();
	var myAjax = new Ajax.Request(
				url, 
				{
							method: 'post', 
							parameters: pars, 
							onComplete: getErrorCode_showResponse,
							onFailure: getErrorCode_showError
				});
}
function getErrorCode_showResponse(r) {
	return true;
}
function getErrorCode_showError(t) {
	//var errmsg = t.status + ' -- ' + t.statusText; 
	//alert("抱歉，出错了；错误信息: " + errmsg + " 麻烦点页面底部的 “给管理员留言”报告错误");
}
function getIEVersonNumber()
{
    var ua = navigator.userAgent;
    var msieOffset = ua.indexOf("MSIE ");
    if(msieOffset < 0)
    {
        return 0;
    }
    return parseFloat(ua.substring(msieOffset + 5, ua.indexOf(";", msieOffset)));
}
function isIE6(){
	return (getIEVersonNumber() == 6);
}


function GetCookieVal (offset)
{
	var endstr = document.cookie.indexOf (";", offset);
	if (endstr == -1)
		endstr = document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr));
}

function GetCookie(name)
{
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen) {
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return GetCookieVal (j);
		i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) break; 
	}
	return null;
}

function SetCookie (name, value)
{
	var argv = SetCookie.arguments;
	var argc = SetCookie.arguments.length;
	var expires = (argc > 2) ? argv[2] : null;
	var path = (argc > 3) ? argv[3] : null;
	var domain = (argc > 4) ? argv[4] : null;
	var secure = (argc > 5) ? argv[5] : false;
	document.cookie = name + "=" + escape (value) +
		((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
		((path == null) ? "" : ("; path=" + path)) +
		((domain == null) ? "" : ("; domain=" + domain)) +
		((secure == true) ? "; secure" : "");
}
String.prototype.trim = function() {
	return this.replace(/^\s*|\s*$/g,"");
}

function formOnfocus (el) {
	el.onfocus = function() {
		el.style.backgroundColor = "#fcfcfc";
	}
	el.onblur = function() {
		el.style.backgroundColor = "#fff";
	}
}


function upload(id, count)
{
	if(!id) {
		document.location.href = "http://photo.xiaonei.com/choosealbum.do";
	} else {
		if (count >= 120) {
			// 满了
			document.location.href = "http://photo.xiaonei.com/choosealbum.do?full=1";
			return;
		}
		var installed = false;
		try {
			var comActiveX = new ActiveXObject("xnalbum.Uploader");
			if (comActiveX) installed = true;
		} catch(e) {}
		if(installed && document.all
				&& window.ActiveXObject
				&& navigator.userAgent.toLowerCase().indexOf("msie") > -1
				&& navigator.userAgent.toLowerCase().indexOf("opera") == -1) {
			document.location.href = "http://photo.xiaonei.com/tophotox.do?id=" + id;
		} else {
			document.location.href = "http://upload.xiaonei.com/addphoto.do?id=" + id;
		}
	}
}
