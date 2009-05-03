null// Copyright (c) 2005-2006 Paul Colton - Please see http://www.aflax.org

function AFLAX(path,trace,enableFlashSettings,localStoreReadyCallback)
{if(path!=null)AFLAX.path=path;if(trace!=null)AFLAX.tracing=trace;if(localStoreReadyCallback==undefined||localStoreReadyCallback==null)localStoreReadyCallback="";this.id="aflax_obj_"+AFLAX.count++;if(enableFlashSettings!=undefined||enableFlashSettings==true)
{if(document.getElementById("flashSettings")==null&&arguments.length>0)
{var flashSettingsStyle="width: 215px; height: 138px; position: absolute; z-index: 100;left: -500px; top: -500px";document.write('<div id="flashSettings" style="'+flashSettingsStyle+'"\>Flash Settings Dialog</div\>\n');AFLAX.settings=new AFLAX();AFLAX.settings.addFlashToElement("flashSettings",215,138,"#FFFFFF",localStoreReadyCallback,true);}}}
AFLAX.version="0.41";AFLAX.tracing=false;AFLAX.count=0;AFLAX.path="aflax.swf";AFLAX.settings=null;AFLAX.prototype.id=null;AFLAX.prototype.flash=null;AFLAX.prototype.root=null;AFLAX.prototype.stage=null;AFLAX.prototype.getHTML=function(width,height,bgcolor,callback,transparent,absolutePosition)
{var requiredVersion=new com.deconcept.PlayerVersion([8,0,0]);var installedVersion=com.deconcept.FlashObjectUtil.getPlayerVersion();if(installedVersion.versionIsValid(requiredVersion)==false)
{return"<div style='border:2px solid #FF0000'>To see this contents you need to install <a target='_blank' href='http://www.macromedia.com/go/getflashplayer'>Flash Player</a> version 8.0 or higher.</div>";}
bgcolor=bgcolor||"#FFFFFF";callback=callback||"_none_";var content='\
<object width="'+width+'" height="'+height+'" id="'+this.id+'" type="application/x-shockwave-flash" data="'+AFLAX.path+'?callback='+callback+'"';if(absolutePosition)
content+='style="position: absolute"';content+='>\
<param name="allowScriptAccess" value="sameDomain" />\
<param name="bgcolor" value="'+bgcolor+'" />\
<param name="movie" value="'+AFLAX.path+'?callback='+callback+'" />\
<param name="scale" value="noscale" />\
<param name="salign" value="lt" />\
';if(transparent)
content+='<param name="wmode" value="transparent" />';content+='</object>';if(AFLAX.tracing)
content+='<div style="border:1px solid #ddd;padding: 4px;background-color: #fafafa;font-size: 8pt;" id="aflaxlogger"></div>';return content;}
AFLAX.prototype.addFlashToElement=function(parentElementOrId,width,height,bgcolor,callback,transparent)
{var parentNode=typeof parentElementOrId=="string"?document.getElementById(parentElementOrId):parentElementOrId;var content=this.getHTML(width,height,bgcolor,callback,transparent);var container=document.createElement("div");container.innerHTML=content;var element=container.removeChild(container.firstChild);while(parentNode.firstChild)
parentNode.removeChild(parentNode.firstChild);parentNode.appendChild(element);return element;}
AFLAX.prototype.insertFlash=function(width,height,bgcolor,callback,transparent,absolutePosition)
{var content=this.getHTML(width,height,bgcolor,callback,transparent,absolutePosition);document.write(content);if(AFLAX.tracing)
AFLAX.trace("AFLAX Logger initialized.");return document.getElementById(this.id);}
AFLAX.prototype.getRoot=function()
{if(this.root==null)
{this.root=new AFLAX.MovieClip(this,null,"_root");}
return this.root;}
AFLAX.prototype.getStage=function()
{if(this.stage==null)
{this.stage=new AFLAX.MovieClip(this,null,"_stage");this.stage.exposeProperty("width",this.stage);this.stage.exposeProperty("height",this.stage);this.stage.exposeProperty("scaleMode",this.stage);this.stage.exposeProperty("showMenu",this.stage);this.stage.exposeProperty("align",this.stage);}
return this.stage;}
AFLAX.prototype.getFlash=function()
{if(this.flash==null)
{this.flash=document[this.id];}
return this.flash;}
AFLAX.returnsHash={"true":true,"false":false,"undefined":undefined,"null":null,"NaN":NaN};AFLAX.prototype.callFunction=function(name)
{var ret=this.getFlash().CallFunction("<invoke name=\""+
name+"\" returntype=\"javascript\">"+
__flash__argumentsToXML(arguments,1)+"</invoke>");if(AFLAX.returnsHash.hasOwnProperty(ret))
{ret=AFLAX.returnsHash[ret];}
else if(ret.charAt(0)=='"')
{if(ret.charAt(ret.length-1)=='"')
ret=ret.substring(1,ret.length-1);}
else
{ret=ret-0;}
return ret;}
AFLAX.prototype.storeValue=function(key,value,statusCallback,serialize)
{if(serialize==true)
value="[JSON]"+JSON.stringify(value);if(statusCallback==undefined||statusCallback==null)
return this.callFunction("aflaxStoreValue",[key,value]);else
return this.callFunction("aflaxStoreValue",[key,value,statusCallback]);}
AFLAX.prototype.getStoredValue=function(key)
{var value=this.callFunction("aflaxGetValue",[key]);value=value.split('\\"').join('"');value=value.split("\\'").join("'");alert(value);if(value.substring(0,6)=="[JSON]")
return JSON.parse(value.substring(6));else
return value;}
AFLAX.hideFlashSettings=function()
{var flashDiv=document.getElementById("flashSettings");flashDiv.style.left=-500+"px";flashDiv.style.top=-500+"px";}
AFLAX.showFlashSettings=function(x,y,mode)
{if(x==undefined)x=100;if(y==undefined)y=100;if(mode==undefined)mode=1;var flashDiv=document.getElementById("flashSettings");flashDiv.style.left=x+"px";flashDiv.style.top=y+"px";AFLAX.settings.callStaticFunction("System","showSettings",mode);}
AFLAX.prototype.callStaticFunction=function(objectName,func)
{var args=new Array();args[0]=objectName;args[1]=func;for(var i=2;i<arguments.length;i++)
{args[i]=arguments[i];}
return this.callFunction("aflaxCallStaticFunction",args);}
AFLAX.prototype.getStaticProperty=function(objectName,property)
{return this.callFunction("aflaxGetStaticProperty",[objectName,property]);}
AFLAX.prototype.attachEventListener=function(obj,event,handler)
{var id=obj;if(obj.id!=undefined)
id=obj.id;this.callFunction("aflaxAttachEventListener",[id,event,handler]);}
AFLAX.prototype.callBulkFunctions=function(funcs)
{var s=new Array(funcs.length);for(var i=0,j=funcs.length;i<j;i++)
{var func=funcs[i];s[i]=func.join("\1");}
var commands=s.join("\2");this.callFunction("aflaxBulkCallFunction",commands);}
AFLAX.prototype.updateAfterEvent=function()
{this.callFunction("aflaxUpdateAfterEvent");}
AFLAX.prototype.createFlashArray=function(elements)
{var _array=new AFLAX.FlashObject(this,"Array")
_array.exposeFunction("push",_array);_array.exposeFunction("reverse",_array);_array.exposeProperty("length",_array);var len=elements.length;for(var i=0;i<len;i++)
_array.push(elements[i]);return _array;}
AFLAX.extend=function(baseClass,newClass)
{var pseudo=function(){};pseudo.prototype=baseClass.prototype;newClass.prototype=new pseudo();newClass.prototype.baseConstructor=baseClass;newClass.prototype.superClass=baseClass.prototype;newClass.prototype._prototype=newClass.prototype;if(baseClass.prototype.superClass==undefined){baseClass.prototype.superClass=Object.prototype;}
return newClass;}
AFLAX.extractArgs=function(args,startIndex)
{var newArgs=new Array();for(var i=startIndex;i<args.length;i++)
{newArgs[i-startIndex]=args[i];}
return newArgs;}
AFLAX.FlashObject=function(aflaxRef,flashObjectName,objectArgs,objectID)
{if(arguments.length==0)return;this.aflax=aflaxRef;this.flash=this.aflax.getFlash();this._prototype=AFLAX.FlashObject.prototype;if(objectArgs==null||objectArgs==undefined)
objectArgs=new Array();if(flashObjectName!=null&&flashObjectName!=undefined)
{var args=new Array();args[0]=flashObjectName;for(i=0;i<objectArgs.length;i++)
{var a=objectArgs[i];if(a.id!=undefined)
{a="ref:"+a.id;}
args[i+1]=a;}
this.id=this.aflax.callFunction("aflaxCreateObject",args);}
else
{if(objectID!=null&&objectID!=undefined)
{this.id=objectID;}}
if(this.bound==false)
{}}
AFLAX.FlashObject.prototype.bound=false;AFLAX.FlashObject.prototype.id=null;AFLAX.FlashObject.prototype._prototype=null;AFLAX.FlashObject.prototype.aflax=null;AFLAX.FlashObject.prototype.flash=null;AFLAX.FlashObject.prototype.callFunction=function(functionName)
{var args=new Array();args[0]=this.id;args[1]=functionName;for(i=1;i<arguments.length;i++)
{var val=arguments[i];if(val==null)
{val="null";}
if(typeof(val)=="string")
{if(val.substring(0,4)=="ref:")
{var varPart=val.substring(4);var restPart=null;if(varPart.indexOf(".")!=-1)
{restPart=varPart.substring(varPart.indexOf("."));varPart=varPart.substring(0,varPart.indexOf("."));}
val="ref:"+eval(varPart).id;if(restPart!=null)
val+=restPart;}}
if(val.id!=undefined)
{val="ref:"+val.id;}
args[i+1]=val;}
var retval=this.aflax.callFunction("aflaxCallFunction",args);return retval;}
AFLAX.FlashObject.prototype.bind=function(properties,functions,mappings)
{if(properties!=null&&properties!=undefined)
{for(var pn=0;pn<properties.length;pn++)
{this.exposeProperty(properties[pn]);}}
if(functions!=null&&functions!=undefined)
{for(var fn=0;fn<functions.length;fn++)
{this.exposeFunction(functions[fn]);}}
if(mappings!=null&&mappings!=undefined)
{for(var mn=0;mn<mappings.length;mn++)
{this.mapFunction(mappings[mn]);}}}
AFLAX.FlashObject.prototype.exposeProperty=function(propertyName,targetPrototype)
{var methodSuffix=propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);var target=this._prototype;if(targetPrototype!=null)
target=targetPrototype;target["get"+methodSuffix]=function()
{var r=this.aflax.callFunction("aflaxGetProperty",[this.id,propertyName]);if(r==null)return null;if(r==undefined)return;if(typeof(r)=="string")
return r.split("\\r").join("\r").split("\\n").join("\n");else
return r;}
target["set"+methodSuffix]=function(val)
{this.aflax.callFunction("aflaxSetProperty",[this.id,propertyName,val]);}}
AFLAX.FlashObject.prototype.exposeFunction=function(functionName,targetPrototype)
{var target=this._prototype;if(targetPrototype!=null)
target=targetPrototype;target[functionName]=function()
{var args=new Array();args[0]=this.id;args[1]=functionName;for(var i=0;i<arguments.length;i++)
args[i+2]=arguments[i];return this.aflax.callFunction("aflaxCallFunction",args);}}
AFLAX.FlashObject.prototype.mapFunction=function(functionName,targetPrototype)
{var target=this._prototype;if(targetPrototype!=null)
target=targetPrototype;target[functionName]=function()
{var args=new Array();args[0]=this.id;for(var i=0;i<arguments.length;i++)
{var a=arguments[i];if(a.id!=undefined)a=a.id;args[i+1]=a;}
var fName="aflax"+functionName.substring(0,1).toUpperCase()+functionName.substring(1);return this.aflax.callFunction(fName,args);}}
AFLAX.MovieClip=function(aflaxRef,parentClipID,clipID)
{if(arguments.length==0)return;arguments.callee.prototype.baseConstructor.call(this,aflaxRef,null,null,clipID);if(clipID==undefined||clipID==null)
{if(parentClipID!=undefined&&parentClipID!=null&&this.flash.aflaxCreateEmptyMovieClip!=undefined&&this.flash.aflaxCreateEmptyMovieClip!=null)
this.id=this.aflax.callFunction("aflaxCreateEmptyMovieClip",[parentClipID]);else
this.id=this.aflax.callFunction("aflaxCreateEmptyMovieClip",["_root"]);}
if(AFLAX.MovieClip.bound==false)
{this.bind(AFLAX.MovieClip.movieClipProperties,AFLAX.MovieClip.movieClipFunctions,AFLAX.MovieClip.movieClipMappings);AFLAX.MovieClip.bound=true;}}
AFLAX.extend(AFLAX.FlashObject,AFLAX.MovieClip);AFLAX.MovieClip.prototype.drawCircle=function(x,y,radius)
{var r=radius;var degToRad=Math.PI/180;var n=8;var theta=45*degToRad;var cr=radius/Math.cos(theta/2);var angle=0;var cangle=angle-theta/2;var commands=new Array(n+1);var commandIndex=0;commands[commandIndex++]=[this.id,"moveTo",x+r,y];for(var i=0;i<n;i++)
{angle+=theta;cangle+=theta;var endX=r*Math.cos(angle);var endY=r*Math.sin(angle);var cX=cr*Math.cos(cangle);var cY=cr*Math.sin(cangle);commands[commandIndex++]=[this.id,"curveTo",x+cX,y+cY,x+endX,y+endY];}
this.aflax.callBulkFunctions(commands);}
AFLAX.MovieClip.bound=false;AFLAX.MovieClip.movieClipProperties=["_x","_y","_height","_width","_rotation","_xmouse","_ymouse","_xscale","_yscale","_alpha","blendMode","_visible","cacheAsBitmap"];AFLAX.MovieClip.movieClipFunctions=["moveTo","lineTo","curveTo","lineStyle","beginFill","endFill","clear","getURL","removeMovieClip"];AFLAX.MovieClip.movieClipMappings=["attachVideo","createTextField","addEventHandler","attachBitmap","applyFilter","loadMovie"];AFLAX.MovieClip.prototype.clone=function()
{var newClip=this.aflax.callFunction("aflaxDuplicateMovieClip",[this.id]);var mc=new AFLAX.MovieClip(this.aflax,null,newClip);return mc;}
AFLAX.CameraClip=function(aflaxRef,parentClipID)
{if(arguments.length==0)return;arguments.callee.prototype.baseConstructor.call(this,aflaxRef,parentClipID,null);if(parentClipID==undefined||parentClipID==null)
{parentClipID="_root";}
this.id=this.aflax.callFunction("aflaxCreateVideoClip",[parentClipID]);var cam=this.aflax.callFunction("aflaxGetCamera");this.attachVideo(cam);}
AFLAX.CameraClip.GetCameras=function(aflaxRef)
{return aflaxRef.getFlash().aflaxGetStaticProperty(["Camera","names"]);}
AFLAX.extend(AFLAX.MovieClip,AFLAX.CameraClip);AFLAX.VideoClip=function(aflaxRef,parentClipID,url,cueCallback,loadCallback)
{if(arguments.length==0)return;arguments.callee.prototype.baseConstructor.call(this,aflaxRef,parentClipID,null);if(parentClipID==undefined||parentClipID==null)
{parentClipID="_root";}
this.id=this.aflax.callFunction("aflaxCreateVideoClip",[parentClipID]);var nc=new AFLAX.FlashObject(this.aflax,"NetConnection");nc.callFunction("connect",null);var ns=new AFLAX.FlashObject(this.aflax,"NetStream",[nc]);ns.exposeProperty("time",ns);this.netStream=ns;this.attachVideo(ns);if(loadCallback!=null&&loadCallback!=undefined)
this.aflax.flash.aflaxAttachVideoStatusEvent([ns.id,loadCallback]);if(cueCallback!=null&&cueCallback!=undefined)
this.aflax.flash.aflaxAttachCuePointEvent([ns.id,cueCallback]);ns.callFunction("setBufferTime",0);ns.callFunction("play",url);}
AFLAX.extend(AFLAX.MovieClip,AFLAX.VideoClip);AFLAX.VideoClip.prototype.netStream=null;AFLAX.VideoClip.GetStatusValue=function(statusString,valueName)
{var s=statusString;var args=s.split(";");var params=new Array();for(var i=0;i<args.length;i++)
{var n=args[i].split("=");if(n[0]!="")
{params[n[0]]=n[1];}}
return params[valueName];}
AFLAX.VideoClip.NetStream_Buffer_Empty="NetStream.Buffer.Empty";AFLAX.VideoClip.NetStream_Buffer_Full="NetStream.Buffer.Full";AFLAX.VideoClip.NetStream_Buffer_Flush="NetStream.Buffer.Flush";AFLAX.VideoClip.NetStream_Play_Start="NetStream.Play.Start";AFLAX.VideoClip.NetStream_Play_Stop="NetStream.Play.Stop";AFLAX.VideoClip.NetStream_Play_StreamNotFound="NetStream.Play.StreamNotFound";AFLAX.VideoClip.NetStream_Seek_InvalidTime="NetStream.Seek.InvalidTime";AFLAX.VideoClip.NetStream_Seek_Notify="NetStream.Seek.Notify";AFLAX.TextField=function(aflaxRef,clipID)
{if(arguments.length==0)return;arguments.callee.prototype.baseConstructor.call(this,aflaxRef,null,clipID);if(AFLAX.TextField.bound==false)
{this.bind(AFLAX.TextField.textFieldProperties,AFLAX.TextField.textFieldFunctions);AFLAX.TextField.bound=true;}}
AFLAX.extend(AFLAX.MovieClip,AFLAX.TextField);AFLAX.TextField.bound=false;AFLAX.TextField.textFieldProperties=["type","multiline","wordWrap","text","htmlText","embedFonts"];AFLAX.TextField.textFieldFunctions=["setTextFormat"];if(AFLAX.tracing==true)
{window.onerror=AFLAX.windowError;}
AFLAX.windowError=function(message,url,line){AFLAX.trace('Error on line '+line+' of document '+url+': '+message);return true;}
AFLAX.trace=function(message)
{if(AFLAX.tracing==true)
{var div=document.getElementById("aflaxlogger");if(div!=null)
{var p=document.createElement('p');p.style.margin=0;p.style.padding=0;p.style.textAlign="left";var text=document.createTextNode(message);p.appendChild(text);div.appendChild(p);}}}
AFLAX.Socket=function(aflax,host,port,onConnectEvent,onDataEvent,onCloseEvent)
{var flash=aflax.getFlash();var connection=new AFLAX.FlashObject(aflax,"XMLSocket");flash.aflaxAttachSocketEvents([connection.id,onConnectEvent,onDataEvent,onCloseEvent]);connection.exposeFunction("connect",connection);connection.exposeFunction("close",connection);connection.exposeFunction("send",connection);connection.connect(host,port);return connection;}
if(typeof com=="undefined")var com=new Object();if(typeof com.deconcept=="undefined")com.deconcept=new Object();if(typeof com.deconcept.util=="undefined")com.deconcept.util=new Object();if(typeof com.deconcept.FlashObjectUtil=="undefined")com.deconcept.FlashObjectUtil=new Object();com.deconcept.FlashObjectUtil.getPlayerVersion=function(){var PlayerVersion=new com.deconcept.PlayerVersion(0,0,0);if(navigator.plugins&&navigator.mimeTypes.length){var x=navigator.plugins["Shockwave Flash"];if(x&&x.description){PlayerVersion=new com.deconcept.PlayerVersion(x.description.replace(/([a-z]|[A-Z]|\s)+/,"").replace(/(\s+r|\s+b[0-9]+)/,".").split("."));}}else if(window.ActiveXObject){try{var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash");PlayerVersion=new com.deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));}catch(e){}}
return PlayerVersion;}
com.deconcept.PlayerVersion=function(arrVersion){this.major=parseInt(arrVersion[0])||0;this.minor=parseInt(arrVersion[1])||0;this.rev=parseInt(arrVersion[2])||0;}
com.deconcept.PlayerVersion.prototype.versionIsValid=function(fv){if(this.major<fv.major)return false;if(this.major>fv.major)return true;if(this.minor<fv.minor)return false;if(this.minor>fv.minor)return true;if(this.rev<fv.rev)return false;return true;}
Array.prototype.______array='______array';var JSON={org:'http://www.JSON.org',copyright:'(c)2005 JSON.org',license:'http://www.crockford.com/JSON/license.html',stringify:function(arg){var c,i,l,s='',v;switch(typeof arg){case'object':if(arg){if(arg.______array=='______array'){for(i=0;i<arg.length;++i){v=this.stringify(arg[i]);if(s){s+=',';}
s+=v;}
return'['+s+']';}else if(typeof arg.toString!='undefined'){for(i in arg){v=arg[i];if(typeof v!='undefined'&&typeof v!='function'){v=this.stringify(v);if(s){s+=',';}
s+=this.stringify(i)+':'+v;}}
return'{'+s+'}';}}
return'null';case'number':return isFinite(arg)?String(arg):'null';case'string':l=arg.length;s='"';for(i=0;i<l;i+=1){c=arg.charAt(i);if(c>=' '){if(c=='\\'||c=='"'){s+='\\';}
s+=c;}else{switch(c){case'\b':s+='\\b';break;case'\f':s+='\\f';break;case'\n':s+='\\n';break;case'\r':s+='\\r';break;case'\t':s+='\\t';break;default:c=c.charCodeAt();s+='\\u00'+Math.floor(c/16).toString(16)+
(c%16).toString(16);}}}
return s+'"';case'boolean':return String(arg);default:return'null';}},parse:function(text){var at=0;var ch=' ';function error(m){throw{name:'JSONError',message:m,at:at-1,text:text};}
function next(){ch=text.charAt(at);at+=1;return ch;}
function white(){while(ch!=''&&ch<=' '){next();}}
function str(){var i,s='',t,u;if(ch=='"'){outer:while(next()){if(ch=='"'){next();return s;}else if(ch=='\\'){switch(next()){case'b':s+='\b';break;case'f':s+='\f';break;case'n':s+='\n';break;case'r':s+='\r';break;case't':s+='\t';break;case'u':u=0;for(i=0;i<4;i+=1){t=parseInt(next(),16);if(!isFinite(t)){break outer;}
u=u*16+t;}
s+=String.fromCharCode(u);break;default:s+=ch;}}else{s+=ch;}}}
error("Bad string");}
function arr(){var a=[];if(ch=='['){next();white();if(ch==']'){next();return a;}
while(ch){a.push(val());white();if(ch==']'){next();return a;}else if(ch!=','){break;}
next();white();}}
error("Bad array");}
function obj(){var k,o={};if(ch=='{'){next();white();if(ch=='}'){next();return o;}
while(ch){k=str();white();if(ch!=':'){break;}
next();o[k]=val();white();if(ch=='}'){next();return o;}else if(ch!=','){break;}
next();white();}}
error("Bad object");}
function num(){var n='',v;if(ch=='-'){n='-';next();}
while(ch>='0'&&ch<='9'){n+=ch;next();}
if(ch=='.'){n+='.';while(next()&&ch>='0'&&ch<='9'){n+=ch;}}
if(ch=='e'||ch=='E'){n+='e';next();if(ch=='-'||ch=='+'){n+=ch;next();}
while(ch>='0'&&ch<='9'){n+=ch;next();}}
v=+n;if(!isFinite(v)){error("Bad number");}else{return v;}}
function word(){switch(ch){case't':if(next()=='r'&&next()=='u'&&next()=='e'){next();return true;}
break;case'f':if(next()=='a'&&next()=='l'&&next()=='s'&&next()=='e'){next();return false;}
break;case'n':if(next()=='u'&&next()=='l'&&next()=='l'){next();return null;}
break;}
error("Syntax error");}
function val(){white();switch(ch){case'{':return obj();case'[':return arr();case'"':return str();case'-':return num();default:return ch>='0'&&ch<='9'?num():word();}}
return val();}};

var ua = navigator.userAgent.toLowerCase();
OS = {};
OS.isFirefox = ua.indexOf ("gecko") != -1 && ua.indexOf ("rv") != -1;
OS.isOpera = ua.indexOf ("opera") != -1;
OS.isWebkit = ua.indexOf ("webkit") != -1;
OS.isChrome = OS.isWebkit && ua.indexOf ("chrome")!= -1;
OS.isIE = !OS.isOpera && ua.indexOf ("msie") != -1;
OS.isIE6 = false;
OS.isIE7 = false;
OS.isFirefox2 = false;
OS.browserOK = function()
{
	if(/MSIE (\d+\.\d+);/.test(navigator.userAgent))
	{
		var ieversion=new Number(RegExp.$1)
		log.println('ieversion - ' + ieversion);
		this.isIE6 = (ieversion == 6);
		this.isIE7 = (ieversion == 7);
		if(ieversion >= 6)
			return true;
	}
	else if(OS.isFirefox)
	{
	    var geckov = ua.replace(/^.*rv\:|\).*$/g,'');
	    
		if(/1\.9/.test(geckov))
			return true;
		if(/1\.8/.test(geckov) && !/1\.8\.0/.test(geckov))
		{
			OS.isFirefox2 = true;
			return true;
		}
	}
	else if(OS.isChrome)
	{
		return false;
		return true;
	}
	return false;
}

var h = window.location.hostname;
var rootDomain;
var xiaonei = false;
if(h.indexOf('xiaonei.com')>=0)
{
	document.domain="xiaonei.com";
	rootDomain = 'xiaonei.com';
	xiaonei = true;
}
else if(h.indexOf('kaixin.com')>=0)
{
	document.domain="kaixin.com";
	rootDomain = 'kaixin.com';
	xiaonei = false;
}

var topDoc = top.document;
var log = null;
var gPersistData = null;
var hostName = 'me';

ROOT_NODE_ID='wpiroot';

DESC_SETTING_HEADER='聊天设置';
DESC_ONLINE_FRIENDS='在线好友';
DESC_PLAY_SOUND=' 收到新消息时播放提示音';
				
DESC_BAR_DOWNLOAD_IM=[		
		['校内通', '新鲜事实时收，好友动态尽掌握', '立即下载'],
		['校内通', '校内人专用的即时聊天软件', '立即下载']
	];

DESC_BLIST_ALWAYS_VISIBLE=' 保持在线好友列表始终可见';
DESC_SHOW_ONLINE_FRIENDS_ONLY='show online friends only';
DESC_STORE_CHAT_HISTROY=' 保留聊天和提醒记录&nbsp';
DESC_CLEAR_HISTORY='清除';
DESC_CLEAR_HISTORY_FAIL='清除历史失败，建议刷新页面重试';
DESC_CLEAR_HISROTY_SUCCESS='已删除历史记录';

DESC_HOST_CHANGE_TO_ONLINE='您上线了。';
DESC_HOST_CHANGE_TO_OFFLINE='您下线了。';

DESC_NOTIFY_HEADER='提醒';

DESC_CHANGE_TO_ONLINE='上线了。';
DESC_CHANGE_TO_OFFLINE='下线了。';

DESC_WEB_ONLINE_TIP='网页在线';
DESC_CLIENT_ONLINE_TIP='校内通在线';
DESC_OFFLINE_TIP='对方不在线';
DESC_HOST_OFFLINE_TIP='您处于离线状态';

DESC_HOST_STATUS_ONLINE='您处于在线状态 ';
DESC_HOST_STATUS_OFFLINE='您处于离线状态 ';
DESC_HOST_STATUS_CONN_FAIL='无法连接聊天服务器 ';
DESC_SET_HOST_STATUS_ONLINE='设为\"在线\"';
DESC_SET_HOST_STATUS_OFFLINE='设为\"离线\"';

DESC_NO_FRIENDS_ONLINE_TIP='&nbsp;无好友在线。';
DESC_LOADING_FRIENDS_TIP='&nbsp;&nbsp;正在加载...';

DESC_HOST_STATUS_NO_SESSION='您的登录已失效。';

var imui = {
	showImui : function()
	{
		var n = topDoc.createElement('p');
		if(OS.isIE)
		{
			try{
				n.doScroll('left');
			}catch (ex) {
				setTimeout(imui.showImui, 50);
				return;
			}
		}
		if(pagerType.NEW_PAGER == gPagerType)
		{
			imui.tabBar.init();
		}
	},
	getXhr : function()
	{
		try { return new XMLHttpRequest(); } catch (e) 
		{
			try { return new ActiveXObject('microsoft.xmlhttp'); } 
			catch (e) {
				try { return new ActiveXObject('msxml2.xmlhttp'); } catch (e) {}
			}
		}
		return null;
	},

	getSafeClientWidth : function() 
	{
		var b = topDoc.body;
		var p = b.parentNode;
		var bcWidth = b.clientWidth;
		var pcWidth = p.clientWidth;
		if (OS.isIE) { // && !OS.isOpera
			return (pcWidth == 0) ? bcWidth : pcWidth;
		} else if (OS.isFirefox) {
			return (pcWidth == p.offsetWidth
					&& pcWidth == p.scrollWidth) ? bcWidth : pcWidth;
		}
		return bcWidth;
	},
	
	getXOffset : function(obj) 
	{
		var curleft = obj.offsetLeft;
		if (obj.offsetParent) {
			while (obj = obj.offsetParent) {
				curleft += obj.offsetLeft;
			}
		}
		return curleft;
	}
};

imui.chatHeader = function(w)
{
	var hdr = topDoc.createElement("div");
	hdr.className = 'chat-head';

	var buttons = topDoc.createElement("div");
	buttons.className = 'head-btn';

	var minimizeBtn = topDoc.createElement("a");
	minimizeBtn.href = '#nogo';

	minimizeBtn.className = 'minimize';
	minimizeBtn.title="最小化";

	minimizeBtn.onfocus = "this.blur();";

	var closeBtn = topDoc.createElement("a");
	closeBtn.href = '#nogo';
	closeBtn.className = 'close';
	closeBtn.title="关闭";
	closeBtn.onfocus = "this.blur();";

	if(minimizeBtn.addEventListener) 
	{
		minimizeBtn.addEventListener('click', this.min_click_fn, true);
		closeBtn.addEventListener('click', this.close_click_fn, true);
	}
	else 
	{
		minimizeBtn.onclick = this.min_click_fn;
		closeBtn.onclick = this.close_click_fn;
	}

	buttons.appendChild(closeBtn);
	buttons.appendChild(minimizeBtn);

	var pic = topDoc.createElement("a");
	pic.href = 'http://' + rootDomain + '/getuser.do?id=' + w.touin;
	pic.target = '_blank';
	pic.className = 'chat-info-pic';
	pic.alt = w.toname;
	pic.innerHTML = '<img src="' + w.toprofile + '" /></a>';

	var name_span = topDoc.createElement("div");
	name_span.className = 'head-name';
	name_span.innerHTML = '<a href="http://' + rootDomain + '/getuser.do?id=' + w.touin + '" target="_blank">' + w.toname + '</a>';
	
	hdr.appendChild(buttons);
	hdr.appendChild(pic);
	hdr.appendChild(name_span);

	this.pic = pic;	
	this.domNode = hdr;	
	this.domNode.widget = w;
}
imui.chatHeader.prototype.updateProfile = function()
{
	this.pic.innerHTML = '<img src="' + this.domNode.widget.toprofile + '" /></a>';
}
imui.chatHeader.prototype.min_click_fn = function()
{
	var w = this.parentNode.parentNode.widget;
	w.focus(false);
	imui.chatTabs.currentFocus = null;
}
imui.chatHeader.prototype.close_click_fn = function()
{
	var w = this.parentNode.parentNode.widget;
	imui.chatTabs.onCloseWidget(w);
}

imui.chatDoing = function(w)
{
	var info = topDoc.createElement("div");
	info.className = 'chat-info';
	var e = topDoc.createElement("div");
	e.innerHTML = w.doing;  //use dom node to manip escaped character. good!

	var doing = e.innerHTML;
	info.title = doing;

	if(doing.length > 25)
	{
		doing = doing.substr(0, 23);
		doing += '...';
	}

	info.innerHTML ='<div class="chat-info-status">' + doing+ '</div><br />';

	this.domNode = info;	
	this.domNode.widget = this;	
}

imui.chatConv = function(w)
{
	var t = topDoc.createElement("div");
	t.className = "chat-conv";

	this.table = topDoc.createElement("table");

	t.appendChild(this.table);	
	this.domNode = t;	
	this.domNode.widget = this;	

	this.widget = w;

	this.recvCnt = 0;
	this.sentCnt = 0;

	this.msgQueueHead = null;
	this.msgQueueTail = null;
	this.msgCnt = 0;
}

imui.chatConv.prototype.appendNote = function(note)
{
	var tr = this.table.insertRow(-1);
	var td = tr.insertCell(0);
	td.className = 'visibility-change';

	var ts = this.getFormatedTime(new Date());

	td.innerHTML = '<span class="time-stamp">' + ts + '</span>' + note;

	this.afterAppend(tr);
}

imui.chatConv.prototype.extendInt = function(i)
{
	if(i>=0 && i < 10) 
		return '0' + i;
	return i;
}
imui.chatConv.prototype.getFormatedTime = function(d)
{
	return this.extendInt(d.getMonth()+1) + '-' 
			+ this.extendInt(d.getDate()) + ' ' 
			+ this.extendInt(d.getHours())+ ':' 
			+ this.extendInt(d.getMinutes())+ ':' 
			+ this.extendInt(d.getSeconds());
}

imui.chatConv.prototype.appendImAd = function()
{
	if(!xiaonei)
		return;
	var tr = this.table.insertRow(-1);
	var td = tr.insertCell(0);
	
	td.innerHTML = '<div class="dlxnt">'
					+  '<p><a href="http://im.xiaonei.com/index.html?c=chatbox" class="dlink" style="background: transparent url(http://xnimg.cn/imgpro/chat/dlbtn.png) no-repeat scroll 0pt center; '
					+         'display: inline-block; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; padding-left: 20px;"'
					+     ' target="_blank">下载校内通软件</a>'
                    +     '<span class="twf" style="">和朋友聊天。</span>'
                    +  '</p>'
                    +  '<p>永久保存聊天记录，实时收新鲜事...</p>'
                    +'</div>';
}
imui.chatConv.prototype.notifyFail = function(msg)
{
	if(!msg.msg_content)
		return;
	if(msg.msg_content == 'undefined')
	{
		imHelper.reportChat('error/undef_notify_fail', true);
		return;
	}
	var tr = this.table.insertRow(-1);
	var td = tr.insertCell(0);
	
	var p = topDoc.createElement('p');
	p.appendChild(topDoc.createTextNode('消息"' + msg.msg_content + '"发送失败'));
	if(p.innerHTML.length == 0) p.innerHTML = '&nbsp;';
	p.className = 'send-error';
	td.appendChild(p);
	this.domNode.scrollTop = this.domNode.scrollHeight;
}

imui.chatConv.prototype.append = function(msg, isHistory)
{
	if(!msg.msg_content)
		return;
	if(msg.msg_content == 'undefined')
	{
		imHelper.reportChat('error/undef_recv', true);
		return;
	}
	var tr = this.table.insertRow(-1);
	var td = tr.insertCell(0);

	var ts = this.getFormatedTime(new Date(parseInt(msg.timestamp)));
	var	hostId = imHelper.getLoginUin();

	if(msg.fromuin == hostId)
	{
		td.innerHTML = '<h5 class="self"><span class="time-stamp">' + ts + '</span>' + hostName + '</h5>';

		var p = topDoc.createElement('p');
    	p.appendChild(topDoc.createTextNode(msg.msg_content));        	

    	if(p.innerHTML.replace(/\s/g,'').length == 0)
		{
			p.innerHTML = '&nbsp;';
		}
    	p.className = 'p-self';
    	td.appendChild(p);
        	
        if(!isHistory)
        {
			++this.sentCnt;
        	if(this.recvCnt>0)
        	{
        		if(this.sentCnt==1)
        		{
					imHelper.reportChat('chatstat/mutual_init_sent&' + this.recvCnt + '&' + this.sentCnt);
				}
				else
				{
					imHelper.reportChat('chatstat/mutual_sent&' + this.recvCnt + '&' + this.sentCnt);
				}
        	}
        	else
        	{
				imHelper.reportChat('chatstat/single_sent&' + this.recvCnt + '&' + this.sentCnt);
        	}        	
		}
	}
	else if(msg.touin == hostId)
	{	
		td.innerHTML = '<h5 class="other"><span class="time-stamp">' + ts + '</span><a href="http://' + rootDomain + '/getuser.do?id=' 
			+ this.widget.touin + '" target="_blank">' + this.widget.toname + '</a></h5>';

		var p = topDoc.createElement('p');
    	p.appendChild(topDoc.createTextNode(msg.msg_content));
    	if(p.innerHTML.length == 0) p.innerHTML = '&nbsp;';
    	p.className = 'p-other';
    	td.appendChild(p);
    	
        if(!isHistory)
        {
			++this.recvCnt;
        	if(this.sentCnt>0)
        	{
        		if(this.recvCnt == 1)
        		{
					imHelper.reportChat('chatstat/mutual_init_recv&' + this.recvCnt + '&' + this.sentCnt);
				}
				else
				{
					imHelper.reportChat('chatstat/mutual_recv&' + this.recvCnt + '&' + this.sentCnt);
				}
        	}
        	else
        	{
				imHelper.reportChat('chatstat/single_recv&' + this.recvCnt + '&' + this.sentCnt);
        	}        	
		}
	}
	else
	{
		imHelper.report('t/parse_error_'+hostId+'_'+msg.fromuin+'_'+msg.touin, true);
		return;
	}

	this.msgCnt++;

	this.afterAppend(tr);
}

imui.chatConv.prototype.afterAppend = function(tr)
{
	if(!this.msgQueueHead)
	{
		this.msgQueueHead = tr;
		this.msgQueueTail = tr;
	}
	else 
	{
		this.msgQueueTail.nextMessage = tr;
		this.msgQueueTail = tr;

		if(this.msgCnt > 50)
		{
			var p = this.msgQueueHead; 
			this.msgQueueHead = p.nextMessage; 

			this.table.deleteRow(0);
			this.msgCnt--;
		}
	}
	this.domNode.scrollTop = this.domNode.scrollHeight;
}

imui.chatEditor = function(w)
{
	var edit_div = topDoc.createElement("div");
	edit_div.className = "chat-input";

	var t = topDoc.createElement("input");
	t.type = "text";
	t.className = 'chat-shadow-input gray-text';
	t.widget = w;
	t.value = '输入聊天内容';

	this.input = t;

	edit_div.appendChild(t);

	this.widget = w;
	this.domNode = edit_div;
	this.domNode.widget = this;	

	if(t.addEventListener) 
	{
		t.addEventListener('keydown', this.key_fn, false);
		t.addEventListener('click', this.click_fn, false);
	}
	else 
	{
		t.onkeydown = this.key_fn;
		t.onclick = this.click_fn;
	}
}

imui.chatEditor.prototype.click_fn = function(e)
{
	if(this.className == 'chat-shadow-input gray-text')
	{
		this.value = '';
		this.className = 'chat-shadow-input';
	}
}
imui.chatEditor.prototype.key_fn = function(e)
{
	if(this.className == 'chat-shadow-input gray-text')
	{
		this.value = '';
		this.className = 'chat-shadow-input';
	}
		
	if(!e) e = top.event;
	if(e.keyCode == 13)
	{
		if(hostName && hostName != "")
		{	
			return this.widget.send.click_fn();
		}
		return false;
	}
	return true;
}

imui.chatEditor.prototype.focus = function(b)
{
	if(b)
	{
		this.domNode.firstChild.focus();
	}
}

imui.chatSend = function(w)
{
	var btn = topDoc.createElement("input");
	btn.type = "button";
	btn.className = 'input-button';
	btn.widget = w;
	btn.value = '发送';

	this.send = btn;

	this.widget = w;
	this.domNode = btn;
	this.domNode.widget = this;	

	if(btn.addEventListener) 
	{
		btn.addEventListener('click', this.click_fn, false);
	}
	else 
	{
		btn.onclick = this.click_fn;
	}
}

imui.chatSend.prototype.click_fn = function(ev)
{
	var wid = this.widget.widget;
	if(!wid)
		wid = this.widget;

	var input = wid.editor.input;

	if(!input || input.value.length <= 0)
	{
		return;
	}
	
	if(input.className == 'chat-shadow-input gray-text')
	{
		input.className == 'chat-shadow-input';
		input.value='';
		return false;
	}
	var maxLen = 1024;
	if(input.value.length <= 0 || input.value.length > maxLen)
	{
		return false;
	}
				
	try{
		gPersistData.pushEvent(eventType.MSG_SEND, imHelper.getLoginUin(), hostName,
			wid.touin, input.value);
		imHelper.report(REPORT_MSG_PUSH);
		gPersistData.syncEvent();
	}
	catch(e){
		log.println(e); 
		if(e.message) 
			log.println(e.message + e.number);
	}
	input.value='';
	input.focus(true);
	return true;
}

imui.chatTab = function(w)
{
	this.widget = w;

	var close = topDoc.createElement("div");
	close.className = "x";
	close.onfocus = "this.blur();";

	var nameDiv = topDoc.createElement("div");
	nameDiv.className = "chattab-name";

	var name = topDoc.createElement("span");

	if(w.onlineStatus & 4)
	{
		name.className = 'imonline';
		name.title = DESC_CLIENT_ONLINE_TIP;
	}
	else if(w.onlineStatus > 0)
	{
		name.className = 'online';
		name.title = DESC_WEB_ONLINE_TIP;
	}
	else
	{
		name.className = 'offline';
		name.title = DESC_OFFLINE_TIP;
	}

	name.innerHTML = w.toname;

	nameDiv.appendChild(name);

	this.newMsgCntDiv = topDoc.createElement("div");
	this.newMsgCntDiv.className = "m-chat-msgcount hide";
	if(close.addEventListener) 
	{
		close.addEventListener('click', this.close_click_fn, true);
		nameDiv.addEventListener('click', this.name_click_fn, true);
		nameDiv.addEventListener('mouseover', this.name_mouseover_fn, true);
		nameDiv.addEventListener('mouseout', this.name_mouseout_fn, true);
		this.newMsgCntDiv.addEventListener('click', this.count_click_fn, true);
	}
	else 
	{
		close.onclick = this.close_click_fn;
		nameDiv.onclick = this.name_click_fn;
		nameDiv.onmouseover = this.name_mouseover_fn;
		nameDiv.onmouseout = this.name_mouseout_fn;
		this.newMsgCntDiv.onclick = this.count_click_fn;
	}

	this.close = close;
	this.close.widget = this;
	this.nameDiv = nameDiv;
	this.nameDiv.widget = this;
	this.newMsgCntDiv.widget = this;
};

imui.chatTab.prototype.close_click_fn = function()
{
	var w = this.widget.widget;
	imui.chatTabs.onCloseWidget(w);
}
imui.chatTab.prototype.name_mouseover_fn = function()
{
	var w = this.widget.widget;
	if(w.active) return;
	if(w.highlight) return;

	this.parentNode.className = "m-chat-button-chattab m-chat-button-hover";
}

imui.chatTab.prototype.name_mouseout_fn = function()
{
	var w = this.widget.widget;
	if(w.active) return;
	if(w.highlight) return;
	this.parentNode.className = "m-chat-button-chattab";
}

imui.chatTab.prototype.name_click_fn = function()
{
	var w = this.widget.widget;
	if(w.active)
	{
		w.focus(false);
		imui.chatTabs.currentFocus = null;
	}
	else
	{
		imui.chatTabs.switchFocus(w);
		w.editor.focus();
	}
}

imui.chatTab.prototype.count_click_fn = function()
{
	var w = this.widget.widget;
	w.focus(true);
}
imui.chatTab.prototype.setOnline = function(b)
{
	if(b)
	{
		//this.domNode.className = '';
	}
	else
	{
	}
}

imui.chatTab.prototype.focus = function(b)
{
	return;
}

imui.chatWidget = function(touin, toname, toprofile, doing, status)
{
	//user info
	this.touin = touin;
	this.toname = toname;
	this.doing = doing;
	
	if(!toprofile || toprofile.length == 0)
	{
		this.toprofile = 'http://head.' + rootDomain + '/photos/0/0/men_tiny.gif';
		this.hasBuddyInfo = false;
		this.onlineStatus = 2;	//im online
	}
	else
	{		
		this.toprofile = toprofile;
		this.hasBuddyInfo = true;
		this.onlineStatus = status;
	}
	
	//status
	this.highlight = false;
	this.active = false;
	this.newMsgCnt = 0;

	this.header = new imui.chatHeader(this);
	this.doing = new imui.chatDoing(this);
	this.conv = new imui.chatConv(this);
	this.editor = new imui.chatEditor(this);
	this.send = new imui.chatSend(this);
	this.tab = new imui.chatTab(this);

	this.createDomNode(touin);
	
	this.loadHistory();
	this.conv.appendImAd();
};

imui.chatWidget.prototype.loadHistory = function()
{
	var m = persistMap.messageHistory.messages;
	var list = persistMap.messageHistory.find(this.touin);
	if(!list) return;
	var maxHisttoryCnt = 50;
	
	var latestMessage = 0;
	var latestTimestamp = 0;
	for(var i=0; i<list.length; i++)
	{
		var e = gPersistData.parseEvent(m[list[i]][0], m[list[i]][1]);
		if(e.timestamp < latestTimestamp)
			break;

		latestMessage = i;
		latestTimestamp = e.timestamp;
	}
	
	var startMessage;
	if(list.length <= maxHisttoryCnt)
		startMessage = (latestMessage + 1) % list.length;
	else
	{
		if(latestMessage+1 >= maxHisttoryCnt)
			startMessage = latestMessage + 1 - maxHisttoryCnt;
		else
			startMessage = list.length - (maxHisttoryCnt - (latestMessage+1));
	}
	var i = startMessage;
	var cnt = 0;
	while(true)
	{
		var e = gPersistData.parseEvent(m[list[i]][0], m[list[i]][1]);
		this.appendHistoryMsg(e);
		if(i == latestMessage)
			break;
		if(cnt ++ > 200)
		{
			imHelper.report('t/loadchatbusy', true);
			break;
		}
		i = (i+1)%list.length;
	}
}

imui.chatWidget.prototype.setHostOnline = function(bOnline)
{
	var e = this.tab.nameDiv.firstChild;
	if(bOnline)
	{
	}
	else
	{
		this.onlineStatus = -1;
		e.className = 'offline';
		e.title = DESC_HOST_OFFLINE_TIP;
	}
}

imui.chatWidget.prototype.setOnline = function(status)
{
	if(this.hasBuddyInfo && this.onlineStatus == status)
		return;
	if(!this.hasBuddyInfo)
	{
		var p = imui.utilTabs.friends.getToprofile(this.touin);
		if(p && p.length > 0)
		{			
			this.hasBuddyInfo = true;
			this.toprofile = p;
			this.header.updateProfile();
		}		
	}
	var e = this.tab.nameDiv.firstChild;
	if(status & 4)
	{
		e.className = 'imonline';
		e.title = DESC_CLIENT_ONLINE_TIP;
		if(this.onlineStatus == 0)
			this.conv.appendNote(this.toname + DESC_CHANGE_TO_ONLINE);
	}
	else if(status > 0)
	{
		e.className = 'online';
		e.title = DESC_WEB_ONLINE_TIP;
		if(this.onlineStatus == 0)
			this.conv.appendNote(this.toname + DESC_CHANGE_TO_ONLINE);
	}
	else
	{
		e.className = 'offline';
		e.title = DESC_OFFLINE_TIP;
		this.conv.appendNote(this.toname + DESC_CHANGE_TO_OFFLINE);
	}
	this.onlineStatus = status;
}

imui.chatWidget.prototype.focus = function(b, nosync)
{
	if(b)
	{
		if(!this.hasBuddyInfo)
		{
			var p = imui.utilTabs.friends.getToprofile(this.touin);
			if(p && p.length > 0)
			{
				this.hasBuddyInfo = true;
				this.toprofile = p;
				this.header.updateProfile();
			}		
		}
		
		this.newMsgCnt = 0;
		this.tab.newMsgCntDiv.className = "m-chat-msgcount hide";
		
		this.window.className = 'm-chat-window';
		this.domNode.className = 'm-chat-button-chattab m-chat-button-active';

		this.conv.domNode.scrollTop = this.conv.domNode.scrollHeight;

		if(OS.isIE)	//IE focus() fix
		{
			if(!nosync)
				this.editor.focus(true);
		}
		else
			this.editor.focus(true);

		if(this.highlight)
		{
			persistMap.unreadInfo.removeUin(this.touin);
			imui.tabBar.bTitleFlash = false;
		}
	}
	else
	{
		this.window.className = 'm-chat-window hide';
		this.domNode.className = 'm-chat-button-chattab';
	}
	this.active = b;
	this.highlight = false;
	if(!nosync)
	{
		imui.controller.updateChatPresence(this.touin, this.active);
	}
};

imui.chatWidget.prototype.appendMsg = function(msg)
{
	if(!msg) return;
	this.conv.append(msg, false);
	
	if(this.active)
		return;
		
	this.setNewMsgCnt(++this.newMsgCnt);
	persistMap.unreadInfo.updateUin(this.touin, this.newMsgCnt);
	
	if(!imui.tabBar.bTitleFlash)
	{
		imui.tabBar.bTitleFlash = true;
		imui.tabBar.flashTitle(2);
	}
};

imui.chatWidget.prototype.appendFailMsg = function(msg)
{
	this.conv.notifyFail(msg);
}

imui.chatWidget.prototype.appendHistoryMsg = function(msg)
{
	if(!msg) return;
	this.conv.append(msg, true);
};

imui.chatWidget.prototype.setNewMsgCnt = function(cnt)
{
	if(this.active) return;
	
	this.domNode.className = 'm-chat-button-chattab uhavemsg';
	this.highlight = true;
	this.newMsgCnt = cnt;
	if(this.newMsgCnt > 0)
	{		
		this.tab.newMsgCntDiv.innerHTML = this.newMsgCnt;
		this.tab.newMsgCntDiv.className = "m-chat-msgcount";
	}
}

imui.chatWidget.prototype.createDomNode= function(to) 
{
	var node = topDoc.createElement("div");
	node.className = "m-chat-button-chattab";
	
	var window = topDoc.createElement("div");
	window.className = "m-chat-window hide";

	window.appendChild(this.header.domNode);
	window.appendChild(this.doing.domNode);
	window.appendChild(this.conv.domNode);
	window.appendChild(this.editor.domNode);
	this.editor.domNode.appendChild(this.send.domNode);	//将send btn放在editor中的...

	node.appendChild(this.tab.close);
	node.appendChild(this.tab.nameDiv);
	node.appendChild(this.tab.newMsgCntDiv);

	node.appendChild(window);
	
	this.window = window;
	this.domNode = node;

	this.window.widget = this;
	this.domNode.widget = this;	
};

imui.settingOnlineSwitch = function() {
	this.domNode = topDoc.createElement('div');
	this.domNode.className = 'ifonline offline';
	this.domNode.widget = this;

	this.statusDesc = topDoc.createElement('span');
	this.statusDesc.style.color = '#333';
	
	this.setStatus = topDoc.createElement('a');
	this.setStatus.style.color = '#005EAC';
	this.setStatus.href = '#nogo';

	var click_fn = function(){
		var w = this.parentNode.widget;
		w.toggleHostOnline();
	}

	if(this.setStatus.addEventListener) 
	{
		this.setStatus.addEventListener('click', click_fn, true);
	}
	else 
	{
		this.setStatus.onclick = click_fn;
	}

	this.domNode.appendChild(this.statusDesc);
	this.domNode.appendChild(this.setStatus);

	if(imui.tabBar.hostOnline)
	{
		this.domNode.className = 'ifonline online';
		this.statusDesc.innerHTML = DESC_HOST_STATUS_ONLINE;
		this.setStatus.innerHTML = DESC_SET_HOST_STATUS_OFFLINE;
	}
	else
	{
		this.domNode.className = 'ifonline offline';
		if(gPersistData.bUseIm)
		{
			this.setConnFailed();
		}
		else
		{
			this.statusDesc.innerHTML = DESC_HOST_STATUS_OFFLINE;		
			this.setStatus.innerHTML = DESC_SET_HOST_STATUS_ONLINE;
		}
	}
}

imui.settingOnlineSwitch.prototype.toggleHostOnline = function() {
	gPersistData.setUseIm(!imui.tabBar.hostOnline);
	if(!imui.tabBar.hostOnline)
	{
		this.statusDesc.innerHTML = '正在连接... ';
		this.setStatus.innerHTML = '';
	}
	gConn.switchConn();
}

imui.settingOnlineSwitch.prototype.setNoSession = function() {
	this.statusDesc.innerHTML = DESC_HOST_STATUS_NO_SESSION + ' <a href="#" onclick="top.location.reload(false);">刷新</a>';
	this.setStatus.innerHTML = '';
}

imui.settingOnlineSwitch.prototype.setConnFailed = function() {
	this.statusDesc.innerHTML = DESC_HOST_STATUS_CONN_FAIL;
	this.setStatus.innerHTML = '重试';
}

imui.settingOnlineSwitch.prototype.setHostOnline = function(b) {
	if(b)
	{
		this.domNode.className = 'ifonline online';
		this.statusDesc.innerHTML = DESC_HOST_STATUS_ONLINE;
		this.setStatus.innerHTML = DESC_SET_HOST_STATUS_OFFLINE;

		imui.tabBar.refreshOnlineStatus();
	}
	else
	{
		this.domNode.className = 'ifonline offline';
		
		if(gPersistData.bUseIm)
		{
			this.setConnFailed();
		}
		else
		{
			this.statusDesc.innerHTML = DESC_HOST_STATUS_OFFLINE;		
			this.setStatus.innerHTML = DESC_SET_HOST_STATUS_ONLINE;
		}
	}
}

imui.settingWidget = function() {
	//status
	this.active = false;

	this.header = new imui.buddyHeader(this, DESC_SETTING_HEADER);

	this.createDomNode();
	this.createConv();
	this.window.appendChild(this.header.domNode);
	this.window.appendChild(this.conv);
}

imui.settingWidget.prototype.createConv = function() 
{
	this.conv = topDoc.createElement('div');
	this.conv.className = 'chat-conv';

	var opts = topDoc.createElement('div');
	opts.className = 'setoption';

	var table = topDoc.createElement('table');
	table.className = 'optionlist';

	opts.appendChild(table);
	this.conv.appendChild(opts);
	
	var tr = table.insertRow(-1);
	var td = tr.insertCell(0);
	td.className = "checkboxitem";
	td.innerHTML = '<input type="checkbox" /><span style="color:#333;">' + DESC_PLAY_SOUND + '</span>';
	var ckSound = td.firstChild;
	ckSound.checked = gPersistData.bPlaySound;
	this.ckSound = ckSound; //ie6 fix
	var sound_click_fn = function()
	{
		gPersistData.setPlaySound(!gPersistData.bPlaySound);
	};

	tr = table.insertRow(-1);
	td = tr.insertCell(0);
	td.innerHTML = '<input type="checkbox" /><span style="color:#333;">' + DESC_BLIST_ALWAYS_VISIBLE + '</span>';

	var ckBlist = td.firstChild;
	ckBlist.checked = gPersistData.bBlistAlwaysVisible;
	this.ckBlist = ckBlist; //ie6 fix
	var blist_click_fn = function()
	{
		gPersistData.setBlistAlwaysVisible(!gPersistData.bBlistAlwaysVisible);
	};
	
	tr = table.insertRow(-1);
	td = tr.insertCell(0);
	td.innerHTML = '<input type="checkbox" /><span style="color:#333;">' + DESC_STORE_CHAT_HISTROY + '</span>';

	var clearHistory = topDoc.createElement('a');
	clearHistory.innerHTML = DESC_CLEAR_HISTORY;
	clearHistory.style.color = '#005EAC';
	clearHistory.href = "#nogo";
	td.appendChild(clearHistory);

	
	this.ckStoreHistory = td.firstChild;
	this.ckStoreHistory.checked = gPersistData.bStoreHistory;
	var storehis_click_fn = function()
	{
		gPersistData.setStoreHistory(!gPersistData.bStoreHistory);
	};

	var clear_history_click_fn = function()
	{
		try{
		imui.utilTabs.notify.conv.domNode.innerHTML = '<div class="notifyitem">&nbsp;无新提醒。<div>';
		persistMap.notifyHistory.clear();
		}catch(e){alert(DESC_CLEAR_HISTORY_FAIL);return;}
		try{
		persistMap.messageHistory.clear();
		}catch(e){alert(DESC_CLEAR_HISTORY_FAIL);return;}
		alert(DESC_CLEAR_HISROTY_SUCCESS);
	};

	if(ckSound.addEventListener) 
	{
		ckSound.addEventListener('click', sound_click_fn, true);
		ckBlist.addEventListener('click', blist_click_fn, true);
		this.ckStoreHistory.addEventListener('click', storehis_click_fn, true);
		clearHistory.addEventListener('click', clear_history_click_fn, true);
	}
	else 
	{
		ckSound.onclick = sound_click_fn;
		ckBlist.onclick = blist_click_fn;
		this.ckStoreHistory.onclick = storehis_click_fn;
		clearHistory.onclick = clear_history_click_fn;
	}

	this.onlineSwitch = new imui.settingOnlineSwitch();
	this.conv.appendChild(this.onlineSwitch.domNode);
}

imui.settingWidget.prototype.setNoSession = function()
{
	this.onlineSwitch.setNoSession();
}
imui.settingWidget.prototype.createDomNode= function() 
{
	var node = topDoc.createElement("div");

	if(imui.tabBar.hostOnline)
		node.className = "m-chat-button-status";
	else
		node.className = "m-chat-button-status offline";
		

	var window = topDoc.createElement("div");
	window.className = "m-chat-window status-control hide";

	node.appendChild(window);
	
	this.window = window;
	this.domNode = node;

	this.window.widget = this;
	this.domNode.widget = this;	
		
	var t = topDoc.createElement("div");
	t.className = "chattip hide";
	t.innerHTML =  '<div class="chattip-content">设置</div><div class="chattip-for"></div>';
	this.widgetTip = t;
	this.domNode.appendChild(t);

	var mouseover_fn = function(e)
	{
		var w = this.widget;
		if(w.active)
			return;
		w.widgetTip.className = "chattip";
		if(imui.tabBar.hostOnline)
			w.domNode.className = "m-chat-button-status m-chat-button-hover";
		else
			w.domNode.className = "m-chat-button-status m-chat-button-hover offline";
	}

	var mouseout_fn = function(e)
	{
		var w = this.widget;
		if(w.active)
			return;
		w.widgetTip.className = "chattip hide";
		if(imui.tabBar.hostOnline)
			w.domNode.className = "m-chat-button-status";
		else
			w.domNode.className = "m-chat-button-status offline";
	}

	if(node.addEventListener) 
	{
		node.addEventListener('mouseout', mouseout_fn, true);
		node.addEventListener('mouseover', mouseover_fn, true);
	}
	else 
	{
		node.onmouseout = mouseout_fn;
		node.onmouseover = mouseover_fn;
	}
};

imui.settingWidget.prototype.clickHide = function(e)
{
	var o;
	if(e)
	{
		o = e.target;
	}
	else
	{
		o = top.event.srcElement;
	}

	if(o == this.domNode)
	{
		this.focus(!this.active);
		return;
	}

	while(o)
	{
		if(o == this.domNode)
		{
			return;
		}
		o = o.parentNode;
	}

	if(this.active)
	{
		this.focus(false);
	}
}
imui.settingWidget.prototype.focus = function(b)
{
	if(this.active == b)
		return;
	this.widgetTip.className = "chattip hide";
	if(b)
	{
		if(imui.tabBar.hostOnline)
			this.domNode.className = "m-chat-button-status m-chat-button-active";//offline";
		else
			this.domNode.className = "m-chat-button-status m-chat-button-active offline";

		this.window.className = "m-chat-window status-control";
		imui.utilTabs.friends.focus(false);
		imui.utilTabs.notify.focus(false);
	}
	else
	{
		if(imui.tabBar.hostOnline)
			this.domNode.className = "m-chat-button-status";//offline";
		else
			this.domNode.className = "m-chat-button-status offline";

		this.window.className = "m-chat-window status-control hide";
	}
	this.active = b;	
	imui.controller.updateUtilPresence(SETTING_WIDGET_BIT, b);
};
imui.settingWidget.prototype.setHostOnline = function(b) {
	//imui.settingOnlineSwitch.prototype.setHostOnline(b);
	if(b)
	{
		if(this.active)
			this.domNode.className = "m-chat-button-status m-chat-button-active";
		else
			this.domNode.className = "m-chat-button-status";
	}
	else
	{
		if(this.active)
			this.domNode.className = "m-chat-button-status m-chat-button-active offline";
		else
			this.domNode.className = "m-chat-button-status offline";
	}
	this.onlineSwitch.setHostOnline(b);
}

imui.buddyHeader = function(w, header_desc)
{
	var hdr = topDoc.createElement("div");
	hdr.className = 'chat-head';

	var buttons = topDoc.createElement("div");
	buttons.className = 'head-btn';

	var minimizeBtn = topDoc.createElement("a");
	minimizeBtn.href = '#nogo';
	minimizeBtn.className = 'minimize';

	minimizeBtn.onfocus = "this.blur();";
	var min_click_fn = function(){
                var w = this.parentNode.parentNode.widget;
		w.focus(false);
        }
	if(minimizeBtn.addEventListener) 
	{
		minimizeBtn.addEventListener('click', min_click_fn, true);
	}
	else 
	{
		minimizeBtn.onclick = min_click_fn;
	}
        
	buttons.appendChild(minimizeBtn);

	var name_span = topDoc.createElement("div");
	name_span.className = 'head-name';
	//name_span.innerHTML = DESC_ONLINE_FRIENDS;
	name_span.innerHTML = header_desc;
	
	hdr.appendChild(buttons);
	hdr.appendChild(name_span);

	this.domNode = hdr;	
	this.domNode.widget = w;
}

imui.buddyConv = function(w)
{
	var t = topDoc.createElement("div");
	t.className = "chat-conv";

	this.table = topDoc.createElement("table");

	t.appendChild(this.table);	
	this.domNode = t;
	this.domNode.widget = this;	

	this.widget = w;
}

imui.buddyConv.prototype.setNoFriends = function(loading)
{
	if(this.table.rows.length > 0)
		return;
	var tr = this.table.insertRow(-1);
	var td = tr.insertCell(0);
	td.className = "buddy-list-item available";
	td.innerHTML = loading ? DESC_LOADING_FRIENDS_TIP : DESC_NO_FRIENDS_ONLINE_TIP;
}
imui.buddyConv.prototype.append = function(friend)
{
	var tr = this.table.insertRow(-1);
	var td = tr.insertCell(0);
	td.className = "buddy-list-item available";

	var a = topDoc.createElement('a');
	a.className = 'clearfix';
	a.href = '#nogo';
	a.user_info = friend;
	var click_fn = function(){
		var f = this.user_info;
		imui.chatTabs.onActivateWidget(f.id, f.name, f.tiny, f.doing, f.status);
		imui.chatTabs.switchFocus(f.id);
	}

	if(a.addEventListener) 
	{
		a.addEventListener('click', click_fn, false);
	}
	else 
	{
		a.onclick = click_fn;
	}

	var html;
	
	if(friend.status & 0x04)
	{
		html = '<span class="im-available-dot" title="' + DESC_CLIENT_ONLINE_TIP + '"></span>';
	}
	else
	{
		html = '<span class="available-dot" title="' + DESC_WEB_ONLINE_TIP + '"></span>';
	}

	if(this.widget.imageLoaded)
	{
		html += '<span class="friendico"><img src="' + friend.tiny 
			+ '"/></span><span class="buddy-list-item-name" title="' + friend.doing + '">' + friend.name + '</span>';
			
		a.innerHTML = html;
		td.appendChild(a);	
	}	
	else
	{	
		html += '<span class="friendico"></span><span class="buddy-list-item-name" title="' + friend.doing + '">' + friend.name + '</span>';

		a.innerHTML = html;
		td.appendChild(a);	
		
		tr.tinyHeader = a.firstChild.nextSibling;
		tr.tinyUrl = friend.tiny;
	}
}

imui.buddyConv.prototype.loadImage = function()
{	
	if(this.widget.imageLoaded)
		return;

	var rows = 	this.table.rows;

	for(var i = 0; i < rows.length; i++)
	{
		var tr = rows[i];
		if(tr.tinyUrl)
		{
			tr.tinyHeader.innerHTML = '<img src="' + tr.tinyUrl + '"/>';
			tr.tinyUrl = null;
		}
	}	
	this.widget.imageLoaded = true;
}

imui.buddyTab = function(w)
{
	var name = topDoc.createElement("span");
	name.innerHTML = '<span style="color:#333;">' + DESC_ONLINE_FRIENDS + ' (<strong>0</strong>)</span>';

	var mouseover_fn = function()
	{
		var w = this.widget;
		if(w.active) return;

		this.parentNode.className = "m-chat-button-onlinefriends m-chat-button-hover";
	};

	var mouseout_fn = function()
	{
		var w = this.widget;
		if(w.active) return;

		this.parentNode.className = "m-chat-button-onlinefriends";
	};

	var click_fn = function()
	{
		var w = this.widget;
		if(w.active)
		{
			w.focus(false);
		}
		else
		{
			w.focus(true);
			//w.refetchBuddies(true);	//点开好友列表的时候，要取一下，deep
		}
	};
	if(name.addEventListener)
	{
		name.addEventListener('mouseout', mouseout_fn, true);
		name.addEventListener('mouseover', mouseover_fn, true);
		name.addEventListener('click', click_fn, true);
	}
	else 
	{
		name.onmouseout = mouseout_fn;
		name.onmouseover = mouseover_fn;
		name.onclick = click_fn;
	}

	this.domNode = name;
	this.domNode.widget = w;
};

imui.buddyTab.prototype.setOnlineCount = function(cnt)
{
	this.domNode.innerHTML = '<span style="color:#333;">' + DESC_ONLINE_FRIENDS + ' (<strong>' + cnt + '</strong>)</span>';
}

imui.buddySearch = function(w) {
	var dv = topDoc.createElement('div');
	dv.className = 'sortholder';
	dv.innerHTML = '<span class="sorticon"/>';
	
	var e = topDoc.createElement('input');
	e.widget = w;
	e.value = '查找好友';
	e.className = 'input-text gray-text';	
	var focus_fn = function(e)
	{
		if(this.className == 'input-text gray-text')
		{
			this.value = '';
			this.className = 'input-text';
		}
	}
	var blur_fn = function(e)
	{
		if(this.value == 0)
		{
			this.value = '查找好友';
			this.className = 'input-text gray-text';
		}
	}
	var keyup_fn = function(e)
	{
		if(!e) e = top.event;
		var wid = this.widget;
	
		wid.showBuddyInfo(this.value);
		if(this.value.length > 0)
			wid.search.cancelBtn.className = 'cancel';
		else
			wid.search.cancelBtn.className = 'cancel hide';
	}
	
	var b = topDoc.createElement('button');
	b.className = 'cancel hide';
	b.title = '取消';
	b.widget = w;
	var click_fn = function(e)
	{
		var w = this.widget;
		w.showBuddyInfo('');
		w.search.showCancelBtn(false);
	}
	
	if(b.addEventListener) 
	{
		b.addEventListener('click', click_fn, true);
		e.addEventListener('focus', focus_fn, true);
		e.addEventListener('keyup', keyup_fn, true);
		e.addEventListener('blur', blur_fn, true);
	}
	else 
	{
		b.onclick = click_fn;
		
		e.onfocus = focus_fn;
		e.onkeyup = keyup_fn;
		e.onblur = blur_fn;
	}
	
	dv.appendChild(e);
	dv.appendChild(b);
	
	this.cancelBtn = b;
	this.keyInput = e;
	this.domNode = dv;
}

imui.buddySearch.prototype.showCancelBtn = function(b) {
	if(b)
	{
		this.cancelBtn.className = 'cancel';
	}
	else
	{
		this.cancelBtn.className = 'cancel hide';
		this.keyInput.className = 'input-text gray-text';	
		this.keyInput.value = '查找好友';	
	}
}

imui.buddySearch.prototype.focus = function(b) {
	if(b)
	{
		this.keyInput.focus();
	}
}

MIN_BUDDY_REFRESH_INTERVAL = 10000;

imui.buddyWidget = function() {
	this.hostid = null;
	this.hostname = null;
	this.onlineFriendsCnt = 0;

	this.active = false;

	this.buddyList = [];
	this.refreshTime = 0;
	
	this.imageLoaded = false;

	this.domNode = topDoc.createElement('div');
	this.domNode.className = "m-chat-button-onlinefriends";

	var window = topDoc.createElement("div");
	window.className = "m-chat-window buddy-list hide";

	this.header = new imui.buddyHeader(this, DESC_ONLINE_FRIENDS); 
	this.search = new imui.buddySearch(this); 
	
	this.conv = new imui.buddyConv(this);

	window.appendChild(this.header.domNode);
	window.appendChild(this.search.domNode);
	window.appendChild(this.conv.domNode);

	this.tab = new imui.buddyTab(this);

	this.domNode.appendChild(this.tab.domNode);
	this.domNode.appendChild(window);

	this.domNode.widget = this;
	this.window = window;

	//this.refetchBuddies(false);
	
	this.searchKey = null;
	
	this.leftMarginRemended = false;
}

imui.buddyWidget.prototype.clearSearch = function() {
	if(this.searchKey && this.searchKey.length > 0)
	{
		this.showBuddyInfo('');
		this.search.showCancelBtn(false);
	}
}

imui.buddyWidget.prototype.hideNotice = function(b) {
	this.window.removeChild(this.notice);
}

imui.buddyWidget.prototype.setHostOnline = function(b) {
	if(b)
	{
		if(!this.newlyOnline)
		{
			this.refetchBuddies(false);	//还好，有cache
			this.newlyOnline = true;
		}
	}
	this.focus(false);
}

imui.buddyWidget.prototype.getToprofile = function(uin) {	
	for(var i = 0; i < this.buddyList.length; i++)
	{
		if(this.buddyList[i].id == uin)
			return this.buddyList[i].tiny;
	}
	return null;
}

imui.buddyWidget.prototype.getOnlineStatus = function(uin) {
	for(var i = 0; i < this.buddyList.length; i++)
	{
		if(this.buddyList[i].id == uin)
			return this.buddyList[i].status;
	}
	return 0;
}
imui.buddyWidget.prototype.getInfo = function(uin) {
	for(var i = 0; i < this.buddyList.length; i++)
	{
		if(this.buddyList[i].id == uin)
			return this.buddyList[i];
	}
	return null;
}

imui.buddyWidget.prototype.refetchBuddies = function(deep) {
	if(this.buddyLoading)
		return;
	if(!imui.tabBar.hostOnline)	//如果不在线，根本就不会取
		return;
	if(!imui.utilTabs.friends)
	{
		imHelper.reportChat('error/fetch_buddy_time', true);
		return;
	}
	
	if(this.onlineFriendsCnt == 0 || this.buddyList.length > 0)
	{
		if((new Date()).getTime() - this.refreshTime < MIN_BUDDY_REFRESH_INTERVAL) 
			return;
	}
	var xhr = imui.getXhr();
	if(!xhr) return;

	//var t = (new Date()).getTime();
	var t = imHelper.getLoginUin();
	if(deep)
	{
		xhr.open("GET", "/getonlinefriends.do?" + t, true);
	}
	else
	{
		xhr.open("GET", "/getonlinecount.do?" + t, true);
	}
	this.refreshTime = (new Date()).getTime();
	this.buddyLoading = true;
	if(this.conv && this.buddyList.length <= 0)
	{
		this.conv.setNoFriends(true);
	}

	xhr.onreadystatechange = function(){
    	if(xhr.readyState==4)
		{
			imui.utilTabs.friends.buddyLoading = false;
			if(xhr.status == 200 || xhr.status == 211)
			{
				imui.utilTabs.friends.setBuddyInfo(xhr);
			}
			else
			{
				imui.utilTabs.friends.refetchOnNobody();
			}
	    }
	};
	xhr.send(null);
}


imui.buddyWidget.prototype.refetchOnNobody = function() {
	
	if(this.refetchInterval)
	{
		this.refetchInterval *= 2;
		if(this.refetchInterval > 900000)
			this.refetchInterval = 900000;
	}
	else
	{
		this.refetchInterval = 17479;	//如果有cache，则实际上不会重取. cache有rsp header内容来决定
	}
	setTimeout(function(){
			imui.utilTabs.friends.refetchBuddies(true);
		}, this.refetchInterval);
	;
}


imui.buddyWidget.prototype.setBuddyInfo = function(xhr) {
	var rsp;
	try{
	eval('rsp=' + xhr.responseText);
	}catch(e){this.refetchOnNobody();return;}
	//alert(xhr.responseText);
	if(!rsp.hostid) return;


	if(!rsp.friends)
	{
		rsp.friends = [];
	}

	var f = rsp.friends;
	var id = rsp.hostid;

	this.hostid = rsp.hostid;
	this.hostname = rsp.hostname;
	if(rsp.hostname)
		hostName = rsp.hostname;

	this.buddyList = rsp.friends;
	this.onlineFriendsCnt = rsp.onlineFriendsCount;
	//this.onlineFriendsCnt = 0;	//test

	var tbl = this.conv.table;
	
	this.showBuddyInfo(this.searchKey);

	if(this.onlineFriendsCnt < 0)	//没有好友，或server查询超时
	{
		this.refetchOnNobody();
		this.onlineFriendsCnt = 0;
	}

	this.tab.setOnlineCount(this.onlineFriendsCnt);
	imui.controller.onlineFriendsReady = true;
	
	imui.tabBar.refreshOnlineStatus();
}

imui.buddyWidget.prototype.showBuddyInfo = function(searchKey) {
	var tbl = this.conv.table;
	var f = this.buddyList;
	this.searchKey = searchKey;	
	if(searchKey)
		searchKey = searchKey.toLowerCase();
	try{
		if(searchKey && searchKey.length>0)
		{			
			var curHeight = this.conv.domNode.clientHeight;
			this.conv.domNode.style.minHeight = curHeight + 'px';
			this.conv.domNode.scrollTop = 0;
		}
		else
			this.conv.domNode.style.minHeight = '20px';

		while(tbl.rows.length>0)
			tbl.deleteRow(0); 

		for(var i=0; i<f.length; i++)
		{
			var name = f[i].name.toLowerCase();
			if(searchKey && searchKey.length>0 && name.indexOf(searchKey)<0)
				continue;
			this.conv.append(f[i]);
		}
	}catch(e){return;}
}

imui.buddyWidget.prototype.clickHide = function(e) {
	var o;
	if(e)
	{
		o = e.target;
	}
	else
	{
		o = top.event.srcElement;
	}

	if(o == this.domNode)
	{
		this.focus(!this.active);
		return;
	}
	if(gPersistData.bBlistAlwaysVisible)
		return;
	if(!o.parentNode)	//ie fix
		return;
	while(o)
	{
		if(o == this.domNode)
		{
			return;
		}
		o = o.parentNode;
	}

	if(this.active)
	{
		this.focus(false);
	}
}

imui.buddyWidget.prototype.focus = function(b, nosync)
{
	if(this.active == b)
		return;
	if(b)
	{
		this.domNode.className = "m-chat-button-onlinefriends m-chat-button-active";
		this.window.className = "m-chat-window buddy-list";
		this.refetchBuddies(true);	//展开好友列表,获取所有列表信息
		if(OS.isIE && !this.leftMarginRemended)
		{
			var leftd = imui.getXOffset(this.domNode);
			var leftw = imui.getXOffset(this.window);
			if(leftw - leftd != 1)
			{
				var x = - 97 - (leftw - leftd - 1);
				this.window.style.marginLeft = x + 'px';
			}
			this.leftMarginRemended = true;
		}
		
		imui.utilTabs.settings.focus(false);
		imui.utilTabs.notify.focus(false);
		this.conv.loadImage();

		if(OS.isIE)
		{
			if(!nosync)
				this.search.focus(true);
		}
		else
			this.search.focus(true);
	}
	else
	{
		this.domNode.className = "m-chat-button-onlinefriends";
		this.window.className = "m-chat-window buddy-list hide";
	}

	this.active = b;
	imui.controller.updateUtilPresence(BUDDY_WIDGET_BIT, b);
};

imui.notifyConv = function(w)
{
	var t = topDoc.createElement("div");
	t.className = "chat-conv";

	this.domNode = t;

	this.domNode.widget = this;	
	this.widget = w;

	this.notifyCnt = 0;
	
	var n = topDoc.createElement("div");
	n.className = "notifyitem";
	n.innerHTML =  '&nbsp;无新提醒。';	
	
	this.noNotifyDiv = n;	
	
	
	this.domNode.appendChild(n);
	this.loadHistory();
}

imui.notifyConv.prototype.loadHistory = function()
{
	var maxHistoryCnt = 32;
	var h = persistMap.notifyHistory.notifies;
	if(h.length == 0)
	{
		if(imHelper.getCookie('wimnseq') == null)
			persistMap.setNotifySeq(0);

		return;
	}
	
	var latestNotify = 0;
	var latestTimestamp = 0;
	
	for(i = 0; i < h.length; i++)
	{
		var e = gPersistData.parseEvent(h[i][0], h[i][1]);
		if(e.timestamp > latestTimestamp)
		{
			latestTimestamp = e.timestamp;
			latestNotify = i;
		}
	}
	gPersistData.localNotifySeq = h[latestNotify][0];
	
	var nseq = imHelper.getCookie('wimnseq');
	if(nseq == null)
		persistMap.setNotifySeq(h[latestNotify][0]);			
	
	var appendCnt = 0;
	var uin = imHelper.getLoginUin();
	for(i = latestNotify; i >= 0; i--)
	{
		var e = gPersistData.parseEvent(h[i][0], h[i][1]);
		
		if(e.touin != uin)
		{
			continue;
		}
		
		this.append(e, true);
		if(++appendCnt >= maxHistoryCnt)
			return;
	}
	for(i = h.length-1; i > latestNotify; i--)
	{
		var e = gPersistData.parseEvent(h[i][0], h[i][1]);
		if(e.touin != uin)
		{
			continue;
		}
		this.append(e, true);
		if(++appendCnt >= maxHistoryCnt)
			return;
	}
}
imui.notifyConv.prototype.updateNoNotify = function()
{
	if(this.notifyCnt > 0)
		this.noNotifyDiv.className = "notifyitem hide";
	else
		this.noNotifyDiv.className = "notifyitem";
}

imui.notifyConv.prototype.remove = function(seq)
{	
	var item = this.domNode.firstChild;
	while(item)
	{
		if(item.notifySeq == seq)
		{
			var p = item.parentNode;
			p.removeChild(item);
				
			if(--this.notifyCnt == 0)
				this.updateNoNotify();
			break;
		}
		item = item.nextSibling;
	}
}

imui.notifyConv.prototype.append = function(e, isHistory)
{
	this.notifyCnt++;

	var n = topDoc.createElement("div");
	
	if(isHistory)
		n.className = "notifyitem";
	else
		n.className = "notifyitem new";

	n.notifySeq = e.seq;
	
	n.innerHTML =  '<a href="#nogo" title="' + e.seq +'" class="close"></a>' + e.content;

	var mouseover_fn = function()
	{
		this.formerClass = this.className;
		this.className = "notifyitem hover";
 	}
 
	var mouseout_fn = function()
	{
		if(this.formerClass)
		this.className = this.formerClass;
	}
 
	var close_click_fn = function()
	{
		var p = this.parentNode;
		persistMap.removeNotify(p.notifySeq);
		gPersistData.pushEvent(eventType.NOTIFY_REMOVE, p.notifySeq);
	}

	var c = n.firstChild;
	
	var link_click_fn = function()
	{
		var p = this.parentNode.parentNode;
		if(!p) return;
		persistMap.removeNotify(p.notifySeq);
		gPersistData.pushEvent(eventType.NOTIFY_REMOVE, p.notifySeq);
	}
	
	var link = n.lastChild.lastChild;
	
	while(link && link.tagName != 'A')
	{
		link = link.previousSibling;
	}
	if(n.addEventListener) 
	{
		n.addEventListener('mouseout', mouseout_fn, true);
		n.addEventListener('mouseover', mouseover_fn, true);
		c.addEventListener('click', close_click_fn, true);
		
		if(link)
		{
			link.addEventListener('click', link_click_fn, true);
		}
 	}
	else 
	{
		n.onmouseout = mouseout_fn;
		n.onmouseover = mouseover_fn;
		c.onclick = close_click_fn;
		if(link)
		{
			link.onclick = link_click_fn;
		}
	}

	if(isHistory)
		this.domNode.appendChild(n);
	else
		this.domNode.insertBefore(n, this.domNode.firstChild);

	if(this.notifyCnt > 50)
	{
		this.domNode.removeChild(this.domNode.lastChild);
		this.notifyCnt--;
	}
	this.updateNoNotify();

	this.domNode.scrollTop = 0;
}

imui.notifyWidget = function() {
	this.active = false;

	this.createDomNode();

	this.header = new imui.buddyHeader(this, DESC_NOTIFY_HEADER); 
	this.conv = new imui.notifyConv(this);

	this.window.appendChild(this.header.domNode);
	this.window.appendChild(this.conv.domNode);

	this.newMsgCntDiv = topDoc.createElement("div");
	this.newMsgCntDiv.className = "m-chat-msgcount hide";
	this.newMsgCnt = 0;
	this.newMsgCntDiv.innerHTML = this.newMsgCnt;
	this.newMsgCntDiv.widget = this;
	this.domNode.appendChild(this.newMsgCntDiv);

	
	var click_fn = function()
	{
		this.widget.focus(true);
	}
	if(this.newMsgCntDiv.addEventListener) 
	{
		this.newMsgCntDiv.addEventListener('click', click_fn, true);
	}
	else 
	{
		this.newMsgCntDiv.onclick = click_fn;
	}
}

imui.notifyWidget.prototype.showNewCntDiv = function(b)
{
	if(b)
		this.newMsgCntDiv.className = "m-chat-msgcount";
	else
		this.newMsgCntDiv.className = "m-chat-msgcount hide";
}

imui.notifyWidget.prototype.setNewMsgCnt = function(cnt)
{
	if(cnt <= 0)
		this.newMsgCntDiv.className = "m-chat-msgcount hide";
	else
		this.newMsgCntDiv.className = "m-chat-msgcount";

	this.newMsgCnt = cnt;
	this.newMsgCntDiv.innerHTML = cnt;
}

imui.notifyWidget.prototype.incrementNewMsgCnt = function()
{
	this.newMsgCnt ++;
	this.newMsgCntDiv.innerHTML = this.newMsgCnt;
	if(this.newMsgCnt == 1)
		this.newMsgCntDiv.className = "m-chat-msgcount";
}

imui.notifyWidget.prototype.createDomNode= function() 
{
	var node = topDoc.createElement("div");
	node.className = "m-chat-button-notifications";
	
	var window = topDoc.createElement("div");
	window.className = "m-chat-window notifications hide";

	node.appendChild(window);
	
	this.window = window;
	this.domNode = node;

	this.window.widget = this;
	this.domNode.widget = this;
	
	var t = topDoc.createElement("div");
	t.className = "chattip hide";
	t.innerHTML =  '<div class="chattip-content">提醒</div><div class="chattip-for"></div>';	
	this.widgetTip = t;
	this.domNode.appendChild(t);

	var mouseover_fn = function(e)
	{
		var w = this.widget;
		if(w.active)
			return;
		if(w.newMsgCnt == 0)
			w.widgetTip.className = "chattip";
		w.domNode.className = "m-chat-button-notifications m-chat-button-hover";
	}

	var mouseout_fn = function(e)
	{
		var w = this.widget;
		if(w.active)
			return;
			
		w.widgetTip.className = "chattip hide";
		w.domNode.className = "m-chat-button-notifications";
	}

	if(node.addEventListener) 
	{
		node.addEventListener('mouseout', mouseout_fn, true);
		node.addEventListener('mouseover', mouseover_fn, true);
	}
	else 
	{
		node.onmouseout = mouseout_fn;
		node.onmouseover = mouseover_fn;
	}

};

imui.notifyWidget.prototype.clickHide = function(e) {
	var o;
	if(e)
	{
		o = e.target;
	}
	else
	{
		o = top.event.srcElement;
	}

	if(o == this.domNode)
	{
		this.focus(!this.active);
		return;
	}

	while(o)
	{
		if(o == this.domNode)
		{
			return;
		}
		o = o.parentNode;
	}

	if(this.active)
	{
		this.focus(false);
	}
}
imui.notifyWidget.prototype.removeItem = function(seq) {
	this.conv.remove(seq);
}
imui.notifyWidget.prototype.append = function(e) {
	this.conv.append(e);

	if(!this.active)
	{
		this.incrementNewMsgCnt();
		
		if(!imui.tabBar.bTitleFlash)
		{
			imui.tabBar.bTitleFlash = true;
			imui.tabBar.flashTitle(1);
		}
	}
}

imui.notifyWidget.prototype.focus = function(b)
{
	if(this.active == b)
		return;
	this.widgetTip.className = "chattip hide";
	if(b)
	{
		this.domNode.className = "m-chat-button-notifications m-chat-button-active";
		this.window.className = "m-chat-window notifications";
		imui.utilTabs.settings.focus(false);
		imui.utilTabs.friends.focus(false);		
	
		this.setNewMsgCnt(0);
		imui.tabBar.bTitleFlash = false;
	}
	else
	{
		this.domNode.className = "m-chat-button-notifications";
		this.window.className = "m-chat-window notifications hide";
		
		var e = this.conv.domNode.firstChild;
		while(e && e.className == 'notifyitem new')
		{
			e.className = "notifyitem";
			e = e.nextSibling;
		}
	}

	this.active = b;
	imui.controller.updateUtilPresence(NOTIFY_WIDGET_BIT, b);
};

//the first node have highest priority to be visible.
// and , the last node, lowest.
imui.widgetList = function() {
	//widget double linked list

	this.first = null;
	this.last = null;

	this.size = 0;
}

imui.widgetList.prototype.insertTail = function(w)
{
	if(this.first == null)
		this.first = w;
	w.prevWidget = this.last;
	if(this.last)
		this.last.nextWidget = w;
	this.last = w;
	w.nextWidget = null;

	++this.size;
}

imui.widgetList.prototype.insertHead = function(w)
{
	if(this.last == null)
	{
		this.last = w;
	}
	w.prevWidget = null;

	w.nextWidget = this.first;
	if(this.first)
		this.first.prevWidget = w;
	this.first = w;

	++this.size;
}
		
imui.widgetList.prototype.removeTail = function()
{
	return this.remove(this.last);
}

imui.widgetList.prototype.removeHead = function()
{
	return this.remove(this.first);
}

imui.widgetList.prototype.remove = function(w)
{
	if(typeof w != 'object')
		w = this.find(w);
	if(!w) return null;
	//widget double linked list
	if(this.first == w)
		this.first = w.nextWidget;
	if(this.last == w)
		this.last = w.prevWidget;
	
	if(w.prevWidget)
	{
		w.prevWidget.nextWidget = w.nextWidget;
	}
	if(w.nextWidget)
	{
		w.nextWidget.prevWidget = w.prevWidget;
	}
	--this.size;

	return w;
}

imui.widgetList.prototype.getNewMsgCnt = function()
{	
	var w = this.first;
	var cnt = 0;
	while(w)
	{
		cnt += w.newMsgCnt;
		w = w.nextWidget;
	}
	return cnt;
}
imui.widgetList.prototype.dump = function()
{
	var w = this.first;
	log.print('widgets dump : ');
	while(w)
	{
		log.print(w.toname + ' - ');
		w = w.nextWidget;
	}
	log.println('.');
}

imui.widgetList.prototype.find = function(f)
{
	var fuin;
	if(typeof f == 'object')
		fuin = f.touin;
	else
		fuin = f;

	var w = this.first;
	for(; w; w = w.nextWidget)
	{
		if(w.touin == fuin)
			return w;
	}
	return null;
}

var CHAT_WIDTH = 137;
var SCROLL_WIDTH = 74;
var DOING_WIDTH = xiaonei ? 36 : 278;
//var OTHER_WIDTH = 446;
var SETTING_WIDTH = 202;
imui.chatTabs = {
	leftHiddenWidgets : null, 
	rightHiddenWidgets : null, 

	closedWidgets : null, 
	visibleWidgets : null, 
	
	leftScrollBtn : null, //scroll btn on the left
	rightScrollBtn : null, //scroll btn on the right

	domNode : null,

	init : function()
	{
		this.leftHiddenWidgets = new imui.widgetList();
		this.rightHiddenWidgets = new imui.widgetList();

		this.closedWidgets = new imui.widgetList();
		this.visibleWidgets = new imui.widgetList();

		this.domNode = topDoc.createElement("div");
		this.domNode.className  = "m-allchattab";
		
		if(window.screen.width == 1024)
		{
			this.domNode.style.maxWidth = '598px';
		}

		this.chats = topDoc.createElement("div");
		this.chats.className  = "m-chat-chattab";


		this.leftScrollBtn = new imui.scrollBtn(true);
		this.rightScrollBtn = new imui.scrollBtn(false);

		this.domNode.appendChild(this.leftScrollBtn.domNode);
		this.domNode.appendChild(this.rightScrollBtn.domNode);
		this.domNode.appendChild(this.chats);

		this.calcLayout();

		if(top.addEventListener) {
			top.addEventListener('resize', function(){imui.chatTabs.onWindowResize();}, true);
		}
		else if(top.attachEvent)
		{
			top.attachEvent('onresize', function(){imui.chatTabs.onWindowResize();});
		}
		if(OS.isIE6)
			top.IMHack.hackToolBar();
	},

	//scroll			
	updateScrollBtns : function()
	{
		if(OS.isIE6 || OS.isFirefox2 || this.maxVisibleChats <=0 || 
			(this.leftHiddenWidgets.size <= 0
			&& this.rightHiddenWidgets.size <= 0))
		{
			this.leftScrollBtn.hide();
			this.rightScrollBtn.hide();
			return;
		}

		this.leftScrollBtn.updateDisplay(this.leftHiddenWidgets);
		this.rightScrollBtn.updateDisplay(this.rightHiddenWidgets);
	},

	scrollRight : function()
	{
		var r = this.rightHiddenWidgets.removeHead();
		if(!r) return;

		var v = this.visibleWidgets.removeTail();
		if(!v) return;
		this.chats.removeChild(v.domNode);

		this.visibleWidgets.insertHead(r);
		this.chats.insertBefore(r.domNode, this.chats.firstChild);
		this.leftHiddenWidgets.insertHead(v);

		this.updateScrollBtns();
	},
	scrollLeft : function()
	{
		var l = this.leftHiddenWidgets.removeHead();
		if(!l) return;

		var v = this.visibleWidgets.removeHead();
		if(!v) return;
		this.chats.removeChild(v.domNode);

		this.visibleWidgets.insertTail(l);
		this.chats.appendChild(l.domNode);
		this.rightHiddenWidgets.insertHead(v);

		this.updateScrollBtns();
	},

	scrollVisible : true,
	doingVisible : true,
	maxVisibleChats : 5,
	hasSpace : function(b)
	{
		return this.visibleWidgets.size < this.maxVisibleChats;
	},

	calcLayout : function()
	{
		var w = imui.getSafeClientWidth();
		
		if(w <= SETTING_WIDTH + SCROLL_WIDTH + CHAT_WIDTH)
		{
			this.maxVisibleChats = 0;
			this.scrollVisible = false;
			this.doingVisible = false;
		}
		else if(w <= SETTING_WIDTH + SCROLL_WIDTH + CHAT_WIDTH + DOING_WIDTH)
		{
			this.maxVisibleChats = parseInt((w - SETTING_WIDTH - SCROLL_WIDTH) / CHAT_WIDTH);
			this.scrollVisible = true;
			this.doingVisible = false;
		}
		else
		{
			this.maxVisibleChats = parseInt((w - SETTING_WIDTH - SCROLL_WIDTH - DOING_WIDTH) / CHAT_WIDTH);
			this.scrollVisible = true;
			this.doingVisible = true;
		}
		if(this.maxVisibleChats < 0)
			this.maxVisibleChats = 0;

		if(OS.isIE7) //每次窗口宽度变化, 都应该重算宽度
			this.domNode.style.width = (CHAT_WIDTH * this.maxVisibleChats + SCROLL_WIDTH - 24) + 'px';
	},
	onWindowResize : function()
	{
		this.calcLayout();

		while(this.maxVisibleChats < this.visibleWidgets.size)
		{
			log.println('widget hidden: ' + this.visibleWidgets.first.toname);
			this.onHideWidget(this.visibleWidgets.last);
		}

		while(this.maxVisibleChats > this.visibleWidgets.size)	//显示右边隐藏的窗口
		{
			w = this.rightHiddenWidgets.removeHead();
			if(!w) break;
			log.println('show right hidden widget : ' + w.toname);
			this.updateScrollBtns();

			this.visibleWidgets.insertTail(w);
			this.chats.insertBefore(w.domNode, this.chats.firstChild);
		}

		while(this.maxVisibleChats > this.visibleWidgets.size) //显示左边隐藏的窗口
		{
			w = this.leftHiddenWidgets.removeHead();
			if(!w) break;
			log.println('show left hidden widget : ' + w.toname);
			this.updateScrollBtns();

			this.visibleWidgets.insertTail(w);
			this.chats.appendChild(w.domNode);
		}

		//imui.tabBar.logos.show(doingVisible);
		try{
		if(this.doingVisible)
			top.XN.app.status.notify.show();
		else
			top.XN.app.status.notify.hide();
		}catch(e){}
	},

	setHostOnline : function(b)
	{
		//disorder
		if(!this.visibleWidgets || !this.visibleWidgets.first)
		{
			return;
		}
		var w = this.visibleWidgets.first;
		for(; w; w = w.nextWidget)
		{
			w.setHostOnline(b);
		}

		for(w = this.closedWidgets.first; w; w = w.nextWidget)
		{
			w.setHostOnline(b);
		}

		for(w = this.rightHiddenWidgets.first; w; w = w.nextWidget)
		{
			w.setHostOnline(b);
		}

		for(w = this.leftHiddenWidgets.first; w; w = w.nextWidget)
		{
			w.setHostOnline(b);
		}
	},

	//widgets management
	onActivateWidget : function(to, toname, profile, doing, status)
	{
		if(!imui.utilTabs || !imui.utilTabs.friends)
			return;
		imui.utilTabs.friends.clearSearch();
		
		imui.tabBar.logos.showSimple(true);
		var w = this.rightHiddenWidgets.find(to);
		if(w)
		{
			log.println(w.toname + 'exists im hidden widgets');
			return w;
		}

		w = this.leftHiddenWidgets.find(to);
		if(w)
		{
			log.println(w.toname + 'exists im hidden widgets');
			return w;
		}

		w = this.visibleWidgets.find(to);
		if(w != null)
			return w;

		if(!profile)
		{
			var f = imui.utilTabs.friends;
			var info = f.getInfo(to);
			if(info)
			{
				toname = info.name;
				profile = info.tiny;
				doing = info.doing;
				status = info.status;
			}
			else
			{
				profile = '';
				doing = "";
				status = "";
				f.refetchBuddies(true);	//用户不在的时候，就重取一下好友列表, deep方式
			}
		}

		w = this.closedWidgets.remove(to);
		if(!w)
		{
			if(!toname)
			{
				toname = persistMap.messageHistory.getSenderName(to);
				if(!toname)
					return null;
			}
			w = new imui.chatWidget(to, toname, profile, doing, status);
		}

		if(!this.hasSpace())
		{
			this.leftHiddenWidgets.insertTail(w);
			imui.chatTabs.updateScrollBtns();
		}
		else
		{
			this.visibleWidgets.insertTail(w);
			w.focus(false, true);
			this.chats.appendChild(w.domNode);
			if(OS.isIE6)
				top.IMHack.hackWidget(w.domNode);
		}

		return w;
	},

	removeWidget : function(r)
	{
		var w = this.visibleWidgets.remove(r);
		if(!w) return null;
		this.chats.removeChild(w.domNode);

		if(w.active)
		{
			w.focus(false);
			imui.chatTabs.currentFocus = null;
		}
		return w;
	},

	onHideWidget : function(r)
	{
		var w = this.removeWidget(r);
		if(w)
		{
			this.leftHiddenWidgets.insertHead(w); 
			imui.chatTabs.updateScrollBtns();
			log.print('hidden ');
			this.leftHiddenWidgets.dump(); 
		}
	},

	onCloseWidget : function(c)
	{
		var w = this.removeWidget(c);
		if(!w) return;

		this.closedWidgets.insertHead(w);
		if(this.closedWidgets.size > 10)
			this.closedWidgets.removeTail(); 

		this.closedWidgets.dump(); 

		if(this.rightHiddenWidgets.size > 0)
		{
			w = this.rightHiddenWidgets.removeHead();
			imui.chatTabs.updateScrollBtns();
			this.visibleWidgets.insertHead(w);
			this.chats.insertBefore(w.domNode, this.chats.firstChild);
		}
		else if(this.leftHiddenWidgets.size > 0)
		{
			w = this.leftHiddenWidgets.removeHead();
			imui.chatTabs.updateScrollBtns();
			this.visibleWidgets.insertTail(w);
			this.chats.appendChild(w.domNode);
		}
		
		if(!imui.tabBar.hostOnline)
		{
			if(this.visibleWidgets.size <= 0)
			{
				var node = topDoc.getElementById(ROOT_NODE_ID);
				node.className = "m-chat-box offline clearfix";
				imui.tabBar.logos.showSimple(true);
			}
		}
		else 
		{
			if(this.visibleWidgets.size == 0)
			{
				imui.tabBar.logos.showSimple(false);
			}
		}
	},

	currentFocus : null,
	switchFocus : function(newFocus, nosync)
	{
		if(typeof newFocus != 'object')
			newFocus = imui.chatTabs.visibleWidgets.find(newFocus);
		if(newFocus == null)
		{
			this.currentFocus = null;
			return;
		}

		if(this.currentFocus == newFocus)
		{
			return;
		}
		
		if(this.currentFocus)
		{
			this.currentFocus.focus(false, true);	//never sync at this step
		}
		newFocus.focus(true, nosync);
		this.currentFocus = newFocus;
	},
	
	syncFocus : function(newFocus)
	{
		var newFocus = parseInt(newFocus);
		if(isNaN(newFocus) || newFocus == 0)
		{
			if(this.currentFocus)
			{
				this.currentFocus.focus(false, true);
				this.currentFocus = null;
			}
		}
		else
		{
			newFocus = this.onActivateWidget(newFocus);
			this.switchFocus(newFocus, true);
		}
	}
}
imui.utilTabs = {
	domNode : null,
	friendsVisible : false,

	init : function()
	{
		this.domNode = topDoc.createElement("div");
		this.domNode.className  = "m-chat-presence";

		this.settings = new imui.settingWidget();
		this.notify = new imui.notifyWidget();
		this.friends = new imui.buddyWidget();
		this.friends.refetchBuddies(false);

		this.domNode.appendChild(this.settings.domNode);
		
		if(OS.isIE6)
			top.IMHack.hackWidget(this.settings.domNode);
		var click_fn = function(e)
		{
			imui.utilTabs.settings.clickHide(e);
			imui.utilTabs.notify.clickHide(e);
			imui.utilTabs.friends.clickHide(e);
		}

		if(topDoc.addEventListener) 
		{
			topDoc.addEventListener('click', click_fn, true);
		}
		else
		{
			topDoc.onclick = click_fn;
		}

		if(imui.tabBar.hostOnline)
		{
			this.domNode.appendChild(this.notify.domNode);
			this.domNode.appendChild(this.friends.domNode);
			if(OS.isIE6)
			{
				top.IMHack.hackWidget(this.notify.domNode);
				top.IMHack.hackWidget(this.friends.domNode);
			}
			this.friendsVisible = true;
		}
		else
		{
			log.println('buddy widget should be hidden');
		}
	},
	setHostOnline : function(b)
	{
		if(!this.friends)
			return;
		this.friends.setHostOnline(b);
		if(b)
		{
			if(!this.friendsVisible)
			{
				this.domNode.appendChild(this.notify.domNode);
				this.domNode.appendChild(this.friends.domNode);
				if(OS.isIE6)
				{
					top.IMHack.hackWidget(this.notify.domNode);
					top.IMHack.hackWidget(this.friends.domNode);
				}
				this.friendsVisible = true;
			}
		}
		else
		{
			if(this.friendsVisible)
			{
				this.domNode.removeChild(this.friends.domNode);
				this.domNode.removeChild(this.notify.domNode);
				this.friendsVisible = false;
			}
		}

		this.settings.setHostOnline(b);
	}
}

imui.scrollBtn = function(left)
{
	this.leftward = left;

	this.domNode = topDoc.createElement("div");
	if(left)
		this.domNode.className = "m-chat-button-chattab hide";
	else
		this.domNode.className = "m-chat-button-chattab hide";

	this.domNode.widget = this;
	this.isMouseup = true;

	this.hiddenCntDiv = topDoc.createElement("div");
	this.hiddenCntDiv.innerHTML = '<a onfocus="this.blur();" href="#nogo">0</a>';

	this.newMsgCntDiv = topDoc.createElement("div");
	this.newMsgCntDiv.className = "m-chat-msgcount hide";
	this.newMsgCnt = 0;
	this.newMsgCntDiv.innerHTML = this.newMsgCnt;

	this.domNode.appendChild(this.hiddenCntDiv);
	this.domNode.appendChild(this.newMsgCntDiv);

	if(this.domNode.addEventListener)
	{
		this.domNode.addEventListener('mousedown', this.mousedown_fn, true);
		this.domNode.addEventListener('mouseup', this.mouseup_fn, true);
		this.domNode.addEventListener('mouseout', this.mouseup_fn, true);
	}
	else
	{
		this.domNode.onmousedown = this.mousedown_fn;
		this.domNode.onmouseup = this.mouseup_fn;
		this.domNode.onmouseout = this.mouseup_fn;
	}
}

imui.scrollBtn.prototype.setNewMsgCnt = function(cnt)
{
	if(cnt <= 0)
		this.newMsgCntDiv.className = "m-chat-msgcount hide";
	else
		this.newMsgCntDiv.className = "m-chat-msgcount";

	this.newMsgCnt = cnt;
	this.newMsgCntDiv.innerHTML = cnt;
}


imui.scrollBtn.prototype.incrementNewMsgCnt = function()
{
	this.newMsgCnt ++;
	this.newMsgCntDiv.innerHTML = this.newMsgCnt;
	if(this.newMsgCnt == 1)
		this.newMsgCntDiv.className = "m-chat-msgcount";
}

imui.scrollBtn.prototype.mouseup_fn = function()
{
	var w = this.widget;

	w.isMouseup = true;
}

imui.scrollBtn.prototype.timerScroll= function()
{
	var w = this;
	if(w.isMouseup) return;
	w.scroll();

	setTimeout(function(){
			w.timerScroll(); 
			}, 360);
}
imui.scrollBtn.prototype.mousedown_fn = function()
{
	var w = this.widget;
	w.isMouseup = false;
	w.timerScroll();
}

imui.scrollBtn.prototype.scroll = function()
{
	if(this.leftward)
		imui.chatTabs.scrollLeft();
	else
		imui.chatTabs.scrollRight();
}

imui.scrollBtn.prototype.hide = function()
{
	this.domNode.className = "m-chat-button-chattab hide";
}

imui.scrollBtn.prototype.updateDisplay = function(list)
{
	if(list.size > 0)
	{
		if(this.leftward)
		{
			this.domNode.className = "m-chat-button-chattab showmore leftbtn";
			this.domNode.title = '左侧有' + list.size + '个隐藏窗口';
		}
		else
		{
			this.domNode.className = "m-chat-button-chattab showmore rightbtn";
			this.domNode.title = '右侧有' + list.size + '个隐藏窗口';
		}

	}
	else
	{
		if(this.leftward)
		{
			this.domNode.className = "m-chat-button-chattab showmore leftbtn disable";
		}
		else
		{
			this.domNode.className = "m-chat-button-chattab showmore rightbtn disable";
		}

		this.domNode.title = '无隐藏窗口';
	}
	this.hiddenCntDiv.innerHTML = '<a onfocus="this.blur();" href="#nogo">' + list.size + '</a>';
	this.setNewMsgCnt(list.getNewMsgCnt());
}

imui.tabBarLogos = function(online)
{
	this.simple = true;
	var n = topDoc.createElement("div");
	n.className = 'chatnote';	
	
	var t = topDoc.createElement("div");
	t.className = "chattip hide";
	t.innerHTML =  '<div class="chattip-content">校内通主页</div><div class="chattip-for"></div>';
	
	this.widgetTip = t;	
		
	var i = (new Date()).getTime() % DESC_BAR_DOWNLOAD_IM.length;
	
	this.homeLink = topDoc.createElement("a");
	this.homeLink.className = "imico";
	this.homeLink.widget = this;
	this.homeLink.href = 'http://im.xiaonei.com/?ref=wp?c=e1' + i;
	this.homeLink.target = "_blank";
	this.homeLink.innerHTML = '<img src="http://xnimg.cn/imgpro/chat/imico_2.png"/>';
	
	var mouseover_fn = function(e)
	{
		var w = this.widget;
		w.widgetTip.className = "chattip";
	}
	var mouseout_fn = function(e)
	{
		var w = this.widget;
		w.widgetTip.className = "chattip hide";
	}
	
	if(this.homeLink.addEventListener) 
	{
		this.homeLink.addEventListener('mouseout', mouseout_fn, true);
		this.homeLink.addEventListener('mouseover', mouseover_fn, true);
	}
	else
	{
		this.homeLink.onmouseout = mouseout_fn;
		this.homeLink.onmouseover = mouseover_fn;
	}
		
	this.rightLinks = topDoc.createElement("span");
	
	var h = '<a style="color:#005EAC" href="http://im.xiaonei.com/?ref=wp&c=e2' + i;
	h += '" target="_blank"><b>' + DESC_BAR_DOWNLOAD_IM[i][0] + '</b></a>'
			+ ' - <a style="color:#005EAC" href="http://im.xiaonei.com/?ref=wp?c=e3' + i;
	h += '" target="_blank">';
	h += DESC_BAR_DOWNLOAD_IM[i][1] + '</a> - <a style="color:#005EAC" href="http://im.xiaonei.com/xnsetup.exe?ref=wp&d=ewp1' + i
			+ '" target="_blank">' + DESC_BAR_DOWNLOAD_IM[i][2] + '</a>';
	this.rightLinks.innerHTML = h;
	this.rightLinks.className = 'hide';
	
	n.appendChild(this.widgetTip);
	n.appendChild(this.homeLink);
	n.appendChild(this.rightLinks);
	if(online)
	{
		this.simple = false;
	}

	this.domNode = n;
}
imui.tabBarLogos.prototype.show = function(b)
{
	if(b)
		this.domNode.className = 'chatnote';
	else
		this.domNode.className = 'chatnote hide';
}
imui.tabBarLogos.prototype.showSimple = function(b)
{
	if(b == this.simple)
		return;
		
	this.rightLinks.className = b ? 'hide' : '';
	this.simple = b;
}

imui.tabBar = {
	domNode : null,
	settingWidget : null,
	logos : null,
	chatTabs : null,
	initialized : false,

	init : function(bConnected)
	{
		if(this.initialized)
			return;
		var node = topDoc.getElementById(ROOT_NODE_ID);
		if(node)
		{
			if(node.firstChild) 
			{
				imHelper.report('t/duplicateimui2');
				return;
			}
		}
		else
		{
			node = topDoc.createElement('div');
			node.id = ROOT_NODE_ID;
			node.className = 'hide';
			topDoc.body.appendChild(node);
		}
		
		imui.chatTabs.init();
		imui.utilTabs.init();

		if(this.domNode == null)
		{
			if(this.hostOnline)
				node.className = "m-chat-box clearfix";
			else
				node.className = "m-chat-box offline clearfix";

			var n2 = topDoc.createElement("div");
			n2.className = "m-chat";

			var n3 = topDoc.createElement("div");
			n3.className  = "m-chat-tabbar";
			
			n3.appendChild(imui.utilTabs.domNode);
			n3.appendChild(imui.chatTabs.domNode);

			this.logos = new imui.tabBarLogos(this.hostOnline);
	
			n2.appendChild(this.logos.domNode);
			n2.appendChild(n3);
						
			if(node.firstChild)
			{
				imHelper.report('t/duplicateimui3', true);
			}
			else
				node.appendChild(n2);

			this.domNode = n3;
			this.domNode.widget = this;
			//if(OS.isIE)
			{
				var wpirootParent = node.parentNode;
				wpirootParent.removeChild(node);
				topDoc.body.appendChild(node);
			}
			if(OS.isIE6)	//IE6 check state bug fix
			{
				imui.utilTabs.settings.ckSound.checked = gPersistData.bPlaySound;
				imui.utilTabs.settings.ckBlist.checked = gPersistData.bBlistAlwaysVisible;
				imui.utilTabs.settings.ckStoreHistory.checked = gPersistData.bStoreHistory;				
			}
		}
		this.initialized = true;
		imui.controller.loadUnread();
	},
	
	oldTitle : null,
	bTitleFlash : false,
	titleFlashCounter : 0,
	flashTitle : function(type) {
		if(!gConn.localConnect)
			return;
			
		if(!this.oldTitle)
		{
			this.oldTitle = top.document.title;
		}

		if(this.bTitleFlash)
		{
			this.titleFlashCounter ++;

			if(this.titleFlashCounter > 7)
			{
				if(this.titleFlashCounter == 8)
				{
					top.document.title = '【　　　】- ' + this.oldTitle;
						
					if(type == 1)
					{
						imui.utilTabs.notify.showNewCntDiv(false);
					}
					else
					{
					}
				}
				else
				{
					this.titleFlashCounter = 0;
					if(type == 1)
					{
						top.document.title = '【新提醒】- ' + this.oldTitle;
						imui.utilTabs.notify.showNewCntDiv(true);
					}
					else
					{
						top.document.title = '【新消息】- ' + this.oldTitle;
					}
				}
			}
			if(type == 1)
				setTimeout("imui.tabBar.flashTitle(1)", 250);
			else
				setTimeout("imui.tabBar.flashTitle(2)", 250);
		}
		else
		{
			top.document.title = this.oldTitle;
		}
	},

	onConnClose : function()
	{
		this.onHostOnlineChange(false);
	},

	onSessionOut: function()
	{
		this.hostOnline = false;
		if(imui.tabBar.initialized)
		{
			imui.utilTabs.setHostOnline(false);
			imui.chatTabs.setHostOnline(false);
			this.showHostOnlineChange(false);
			imui.utilTabs.settings.setNoSession();
		}
	},

	onConnSuccess : function()
	{
		this.onHostOnlineChange(true);
	},	

	// host online status
	hostOnline : false,
	onHostOnlineChange : function(b)
	{
		if(this.hostOnline == b)
		{
			//return;
		}

		this.hostOnline = b;
		if(imui.tabBar.initialized)
		{	
			var node = topDoc.getElementById(ROOT_NODE_ID);
			if(b)
			{
				if(imui.chatTabs.visibleWidgets && imui.chatTabs.visibleWidgets.size <= 0)
					imui.tabBar.logos.showSimple(false);	
				node.className = "m-chat-box clearfix";
			}
			else
			{
				if(imui.chatTabs.visibleWidgets && imui.chatTabs.visibleWidgets.size <= 0)
				{
					imui.tabBar.logos.showSimple(true);
					node.className = "m-chat-box offline clearfix";
				}
			}
			
			imui.utilTabs.setHostOnline(b);
			imui.chatTabs.setHostOnline(b);
			this.showHostOnlineChange(b);
			if(b)
				try{
				top.IMClew.init();
				}catch(e){}
		}
	},

	showHostOnlineChange: function(b)
	{
		var warr = [];
		warr.push(imui.chatTabs.visibleWidgets);
		warr.push(imui.chatTabs.closedWidgets);
		warr.push(imui.chatTabs.rightHiddenWidgets);
		warr.push(imui.chatTabs.leftHiddenWidgets);
		
		//var f = imui.utilTabs.friends;
		for(var i=0; i<warr.length; i++)
		{		
			for(var w = warr[i].first; w; w = w.nextWidget)
			{
				if(b)
					w.conv.appendNote(DESC_HOST_CHANGE_TO_ONLINE);
				else
					w.conv.appendNote(DESC_HOST_CHANGE_TO_OFFLINE);
			}
		}
	},

	refreshOnlineStatus : function()
	{
		var f = imui.utilTabs.friends;
		var warr = [];
		warr.push(imui.chatTabs.visibleWidgets);
		warr.push(imui.chatTabs.closedWidgets);
		warr.push(imui.chatTabs.rightHiddenWidgets);
		warr.push(imui.chatTabs.leftHiddenWidgets);
		for(var i=0; i<warr.length; i++)
		{
			for(var w = warr[i].first; w; w = w.nextWidget)
			{
				w.setOnline(f.getOnlineStatus(w.touin));
			}
		}
	}
};

function initVars()
{
	return;
	top.imui = imui;
	top.log = log;
	top.gPersistData = gPersistData;
}

if(window.addEventListener) {
	window.addEventListener('load', initVars, true);
}
else if(window.attachEvent)
{
	window.attachEvent('onload', initVars);
}


imui.controller = {	

	loadUnread : function()
	{
		persistMap.unreadInfo.loadCookie();
		var ur = persistMap.unreadInfo._data;
		if(!imui.utilTabs.friends.buddyList)
		{
			setTimeout(imui.controller.loadUnread, 100);
		}
		else
		{
			for(var i=0; i<ur.length; i+=2)
			{			
				var tabs = imui.chatTabs;
				var w = tabs.onActivateWidget(ur[i]);
				if(w)
				{
					w.setNewMsgCnt(ur[i+1]);
				}
			}
		}
	},
		
	onlineFriendsReady : false,
	updateUtilPresence : function(widgetBit, active)
	{
		var presence = persistMap.getPresenceDesc();
		
		var pres = 0;		
		var chat = '';
		
		if(presence)
		{
			pres = parseInt(presence.substr(0, 1));		
			chat = presence.substring(1, presence.length);	
		}

		if(active)
			pres |= widgetBit;
		else
			pres &= ~widgetBit;
			
		var newPresence = pres.toString() + chat.toString();
		if(presence != newPresence)
			persistMap.setPresenceDesc(newPresence);			
	},
		
	updateChatPresence : function(touin, active)
	{
		var presence = persistMap.getPresenceDesc();		
		var pres = parseInt(presence.substr(0, 1));			
		var chat = presence.substr(1, presence.length - 1);	
					
		if(active)
			chat = touin;
		else 
			chat = "";
				
		var newPresence = pres.toString() + chat.toString();
		if(presence != newPresence)
			persistMap.setPresenceDesc(newPresence);	
	},
	
	onRecvNotify : function(e)
	{
		if(gPagerType == pagerType.NEW_PAGER)
		{
			imui.utilTabs.notify.append(e);	
		}
	},
	
	onRemoveNotify : function(e)
	{
		log.println('notify ' + e.remove_seq + ' deleted');
		if(gPagerType == pagerType.NEW_PAGER)
		{
			imui.utilTabs.notify.removeItem(e.remove_seq);
		}
	},
	
	onRecvMessage : function(m)
	{	
		var tabs = imui.chatTabs;
		var w = tabs.onActivateWidget(m.fromuin, m.fromname);
		if(w)
		{
			w.appendMsg(m);
			tabs.leftHiddenWidgets.dump();

			if(tabs.leftHiddenWidgets.find(w))
				tabs.leftScrollBtn.incrementNewMsgCnt();
			else if(tabs.rightHiddenWidgets.find(w))
				tabs.rightScrollBtn.incrementNewMsgCnt();
		}
	},
	onSendMessage : function(m)
	{
		var w = imui.chatTabs.visibleWidgets.find(m.touin);
		if(w) w.appendMsg(m);
	},
	onSendMessageFail : function(m)
	{
		var w = imui.chatTabs.visibleWidgets.find(m.touin);
		if(w) w.appendFailMsg(m);
	},	
	
	onShowSetting : function(b)
	{
		imui.utilTabs.settings.focus(b);
	},
	onShowNotify : function(b)
	{
		//if(imui.utilTabs.notify)
		imui.utilTabs.notify.focus(b);
	},
	onShowBuddy : function(b)
	{
		imui.utilTabs.friends.focus(b, true);
	}
}

domain=document.domain;

REPORT_UNSUPPORTED_BROWSER="feedback/na_browser_wpi"
REPORT_NO_STORAGE="feedback/no_storage_wpi"
REPORT_LOAD_SETTING_ERR="feedback/read_setting_err_wpi"
REPORT_NO_FLASH="feedback/no_flash_wpi"

REPORT_CONN_BEGIN="feedback/conn_begin_wpi"
REPORT_CONN_SUCCESS="feedback/conn_success"
REPORT_CONN_TIMEOUT="feedback/conn_timeout"
REPORT_CONN_CLOSED="feedback/conn_closed"
REPORT_CONN_RETRY_ONFAIL="feedback/conn_fail_n_retry"
REPORT_CONN_RETRY_OUT="feedback/conn_overretry"

REPORT_TIMESTAMP_EXPIRE="feedback/timestamp_expire"

REPORT_MSG_PUSH="feedback/msg_push"
REPORT_MSG_SENT="feedback/msg_sent"
REPORT_MSG_RECV="feedback/msg_recv"

REPORT_LOGIN_REFUSED="t/dark27_login_refused"

REPORT_NULL_STORAGE="t/dark27_null_storage"
REPORT_STORAGE_WERR="t/dark27_pstorage_write_fail"
REPORT_AFLAX_CONNECT="t/dark27_aflax_connect"
REPORT_DUP_CONNECTION="t/dark27_dup_connection"
REPORT_PUSH_CONN_EVENT="t/dark27_push_conn_event"
REPORT_OUTDATED_DATA="t/dark27_outdated_data"
var pagerType = {
	NO_PAGER : 1,
	CLASSIC_PAGER : 2,
	NOTIFY_PAGER  : 3,
	NEW_PAGER     : 4
};
var gPagerType = null;

function logger()
{
	this.el = document.createElement('div');
	this.el.className = 'debug';
	this.cnt = 0;
	with(this.el.style)
	{
		fontSize = '9pt';
		position = 'absolute';
		textAlign = 'left';
		top = '10px';
		left = '100px';
		width = '800px';
		height = '700px';
		border = '1px solid';
		color = 'white';
		backgroundColor = '#000000';
		verticalAlign = 'bottom';
		overflow = 'auto';

		//display = 'none';
	}
	//document.body.appendChild(this.el);
}
logger.prototype.print = function(str)
{
	return;
	var node = document.createTextNode(str);
	this.el.appendChild(node);
}
logger.prototype.println = function(str)
{
	return;
	if(this.cnt ++ > 100)
	{
		this.cnt = 0;
		this.el.innerHTML = '';
	}
	var node = document.createTextNode(str);
	this.el.appendChild(node);
	node = document.createElement('br');
	this.el.appendChild(node);
	this.el.scrollTop = this.el.scrollHeight;
}

var log = new logger();

var imHelper = {
	flashInstalled : function()
	{
		var requiredVersion = new com.deconcept.PlayerVersion([8,0,0]);
		var installedVersion = com.deconcept.FlashObjectUtil.getPlayerVersion();
		return installedVersion.versionIsValid(requiredVersion);
	},

	xmlEncode : function(str) {
		str=str.replace(/&/g,"&amp;");
		str=str.replace(/</g,"&lt;");
		str=str.replace(/>/g,"&gt;");
		str=str.replace(/'/g,"&apos;");
		str=str.replace(/\"/g,"&quot;");
		return str;
	},

	// encoding & decoding user name... not implemented yet
	buildMessage : function(uinfrom, namefrom, uinto, nameto, text) {		
		var sessionId = this.getCookie('XNESSESSIONID');		
		var msg = '<?xml version="1.0" encoding="utf-8"?>\n'
		  + '<hi cmd="msg">\n<fromid>' + uinfrom + '</fromid>\n'
		  + '<fromname>' + this.xmlEncode(namefrom) + '</fromname>\n'
		  + '<toid>' + uinto + '</toid>\n'
		  + '<content>' + this.xmlEncode(text) + '</content>\n'
		  + '<session>' + sessionId + '</session>\n'
		  + '</hi>\n\0';
		return msg;
	},
	buildLoginMsg : function(uin, token)
	{
		var sessionId = this.getCookie('XNESSESSIONID');
		var msg = '<?xml version="1.0" encoding="utf-8"?><hi cmd="login">\n'
		  + '<userid>' + uin + '</userid>\n'
		  + '<username>empty</username>\n'
		  + '<ticket>' + token +'</ticket>\n'
		  + '<session>' + sessionId + '</session>\n'
		  + '</hi>\n\0';
		return msg;
	},
	reportChat : function(str)
	{
		if(!gConn.localConnect)
			return;
		
		var user = this.getLoginUin();
		if(!user) return;

		var img = document.createElement("img");

		var sessionId = this.getCookie('XNESSESSIONID');
		var ts = new Date().getTime();

		img.setAttribute("src", 'http://stat.talk.xiaonei.com/' + str + "&" + user); 
		img.setAttribute("width","0");
		img.setAttribute("height","0");
		img.style.dispaly = 'none';
		document.body.appendChild(img);
	},
	report : function(str, always)
	{ 
		return;
		var user = this.getLoginUin();
		if(!user) return;

		if(!always && (user%100 != 93))
			return;

		var fv = com.deconcept.FlashObjectUtil.getPlayerVersion();
		var flashv = fv.major +'.' + fv.minor + '.' + fv.rev;
		var img = document.createElement("img");

		var sessionId = this.getCookie('XNESSESSIONID');
		var ts = new Date().getTime();

		img.setAttribute("src", 'http://stat.talk.xiaonei.com/' + str + "&" + flashv + "&" + sessionId + "&" + user + "&" + ts); 
		img.setAttribute("width","0");
		img.setAttribute("height","0");
		img.style.dispaly = 'none';
		document.body.appendChild(img);
	},
	getCookie : function(name) 
	{
	  var dc = document.cookie;
	  var fp = dc.indexOf(name+"=");
	  if(fp!=-1) {
	    fp += name.length+1;
	    var ep=dc.indexOf(";",fp);
	    if(ep==-1){ep=dc.length;}
	    return unescape(dc.substring(fp,ep));
	  }
	  return null;
	},
	
	_initUin : null,
	isLoginUser : function()
	{
		var uin = this.getLoginUin();
		if(!uin) return false;
		if(this._initUin == null)
			this._initUin = this.getLoginUin();
		else
		{
			//log.println(uin + ' <- ' + this._initUin);
			if(uin != this._initUin) 
				return false;
		}
		return this.getCookie('societyguester') != null;
	},

	getLoginUin : function() 
	{
		var uin = this.getCookie('hostid');
		if(!uin)
			uin = this.getCookie('userid');
		if(!uin)
			uin = this.getCookie('id');
		return uin;
	},
	
	querySingleNodeValue : function (ele, aname) 
	{
	  for(var i=0; i<ele.childNodes.length; ++i)
	  {
	    var v = ele.childNodes[i];
	    if (v.tagName == aname)
	    {
	      for (var j=0; j<v.childNodes.length; ++j) {
		if (v.childNodes[j].nodeValue != null)
		  return v.childNodes[j].nodeValue;
	      }
	    };
	  };
	  return '';
	},
 
	extractMessage : function(text) 
	{
		text = text.replace(/\\/g, '\\\\');
		var doc, cmd;
		try {
			// code for IE
			if (window.ActiveXObject)
			{
			  doc=new ActiveXObject("Microsoft.XMLDOM");
			  doc.async="false";
			  doc.loadXML(text);
			}
			// code for Mozilla, Firefox, Opera, etc.
			else
			{
			  var parser=new DOMParser();
			  doc=parser.parseFromString(text,"text/xml");
			}
		} catch(e) { return null; }

		// documentElement always represents the root node
		try {
			var x=doc.documentElement;
			if (x.xml) // code for IE
			{
			    cmd = x.selectSingleNode('//hi/@cmd').text;
			}
			else
			{
			    if (x.hasAttributes())
			    {
			    	cmd = x.getAttribute('cmd');
			    }
			}
		} catch(e) { return null; }
	
		// name, content
		var fromuin, fromname, touin, toname, msg_content, timestamp, res;
		var r = new Object();

		if (cmd == 'msg' || cmd == 'offline_msg')
		{
			if (x.xml)
		  	{
				var o =x.selectSingleNode('//hi/fromid'); 
				if(o && o.text) fromuin = o.text;
				o =x.selectSingleNode('//hi/fromname'); 
				if(o && o.text) fromname = o.text;

				var o =x.selectSingleNode('//hi/toid'); 
				if(o && o.text) touin = o.text;
				o =x.selectSingleNode('//hi/toname'); 
				if(o && o.text) toname = o.text;

				msg_content =x.selectSingleNode('//hi/content').text; 
				//o =x.selectSingleNode('//hi/content'); 
				//if(o && o.text) msg_content = o.text;

				o =x.selectSingleNode('//hi/sendtime'); 
				if(o && o.text) timestamp = o.text;
			} else {
				fromuin = this.querySingleNodeValue(x, 'fromid');
				fromname = this.querySingleNodeValue(x, 'fromname');

				touin = this.querySingleNodeValue(x, 'toid');
				toname = this.querySingleNodeValue(x, 'toname');

				msg_content = this.querySingleNodeValue(x, 'content');
				timestamp = this.querySingleNodeValue(x, 'sendtime');
			}

			r.fromuin = fromuin;
			r.fromname = fromname;
			r.touin = touin;
			r.msg_content = msg_content;
			r.timestamp = timestamp;
		}
		else if (cmd.indexOf('_res')!= -1) 
		{
			if (x.xml)
			{
				var o =x.selectSingleNode('//hi/res'); 
				if(o && o.text) res = o.text;
			}
			else
				res = this.querySingleNodeValue(x, 'res');

			r.res = res;
		}
		else if (cmd.indexOf('notify')!= -1) 
		{
			if (x.xml)
			{
				var o =x.selectSingleNode('//hi/toid'); 
				if(o && o.text) touin = o.text;

				o =x.selectSingleNode('//hi/content'); 
				if(o && o.text) msg_content = o.text;

				o =x.selectSingleNode('//hi/title'); 
				if(o && o.text) title = o.text;
			}
			else
			{
				touin = this.querySingleNodeValue(x, 'toid');
				msg_content = this.querySingleNodeValue(x, 'content');
				title = this.querySingleNodeValue(x, 'title');
			}
			r.touin = touin;
			r.content = msg_content;
			r.title = title;
		}
	
		r.cmd = cmd;
		return r;
 	},
 	startXnclient : function()
	{
		var a;
		try{
			a = new ActiveXObject('xntalk.Application');
		}catch(e){return false;}

		if(!a) return false;
		var loginRes;
		try{
			loginRes = a.login();
		}catch(e){return false;}

		if(loginRes == 1)
			return false;

		return true;
	},
	playMessageSound : function()
	{
		try{
			if(!gPersistData.bPlaySound) return;
			gConn.aflax.callFunction("playMessageSound");
		}
		catch(e) {}
	},
	playNotifySound : function()
	{
		try{
			if(!gPersistData.bPlaySound) return;
			gConn.aflax.callFunction("playNotifySound");
		}
		catch(e) {}
	},
	
	getPagerType : function()
	{
		if(!imHelper.flashInstalled())
		{
			log.println('flash not installed or too old');
			imHelper.report(REPORT_NO_FLASH);
			return pagerType.NO_PAGER;
		}
		
		if(!OS.browserOK())
		{
			imHelper.report(REPORT_UNSUPPORTED_BROWSER);			
			return pagerType.NO_PAGER;
		}
	
		if(!persistMap.ieStorage.clearOldData())
		{
			log.println('global storage not usable');
			imHelper.report(REPORT_NO_STORAGE);
			return pagerType.CLASSIC_PAGER;
		}

		if(!gPersistData.readSettings())
		{
			log.println('read setting error. global storage unusable');
			imHelper.report(REPORT_LOAD_SETTING_ERR);
			return pagerType.CLASSIC_PAGER;
		}
		//if(OS.isIE6)
			//return pagerType.CLASSIC_PAGER;
			//return pagerType.NOTIFY_PAGER;
		return pagerType.NEW_PAGER;
	} 	
};

MAX_PERSIST_SEQ=32;
MAX_MESSAGE_SEQ=100;
MAX_NOTIFY_SEQ=24;

var persistMap = {
	errorCode : 0,
	
	getPresenceDesc : function()
	{
		var p = imHelper.getCookie('wimpresence');
		return p ? p : '0';
	},
	setPresenceDesc : function(v)
	{
		document.cookie = 'wimpresence=' + v;
	},
	
	getNotifySeq : function()
	{
		return this._getCookieSeq('wimnseq=');
	},
	setNotifySeq : function(seq)
	{
		this._setCookieSeq('wimnseq=', seq);
	},
	
	getMessageSeq : function()
	{
		return this._getCookieSeq('wimmseq=');
	},
	setMessageSeq : function(seq)
	{
		this._setCookieSeq('wimmseq=', seq);
	},
	
	getGlobalSeq : function()
	{
		return this._getCookieSeq('wimgseq=');
	},
	setGlobalSeq : function(seq)
	{
		this._setCookieSeq('wimgseq=', seq);
	},
	
	_getCookieSeq : function(pattern)
	{
		//var pattern = 'wimgseq=';
		var cks = document.cookie;
		var seq = 0;
		if(cks.length > 0)
		{ 
			var i = cks.indexOf(pattern);
			if(i >= 0)
			{
				i += pattern.length;
				if(i == cks.length-1 || cks.substr(i+1, 1)==';')
				{
					seq = parseInt(cks.substr(i, 1));
				}
				else
				{
					seq = parseInt(cks.substr(i, 2));
				}
			}
		}
		if(isNaN(seq)) seq = 0;
		
		return seq;
	},
	_setCookieSeq : function(pattern, seq)
	{
		//document.cookie = 'wimgseq=' + seq;
		document.cookie = pattern + seq;
	},
	
	////////////////////////////////////////
	
	rwExcept : false,	// read/write error flag
	data : null,
	init : function()
	{
		if(OS.isIE)
		{
			this.data = persistMap.ieStorage;
			this.data.initStorageNodes();
		}
		else if(OS.isFirefox)
			this.data = persistMap.ffStorage;
		else if(OS.isChrome)
		{
			this.data = persistMap.crmStorage;
			this.data.init();
		}
	},
	
	setMessage : function(seq, value)
	{
		if(this.data)
			return this.data.setStorage(persistMap.dataType.MESSAGE, seq, value);
		return true;
	},
	getMessage : function(seq)
	{
		if(this.data)
			return this.data.getStorage(persistMap.dataType.MESSAGE, seq);
		return null;
	},
	removeMessage : function(seq)
	{
		if(this.data)
			this.data.removeStorage(persistMap.dataType.MESSAGE, seq);
	},
	
	setNotify : function(seq, value)
	{
		if(this.data)
			return this.data.setStorage(persistMap.dataType.NOTIFY, seq, value);
		return true;
	},
	getNotify : function(seq)
	{
		if(this.data)
			return this.data.getStorage(persistMap.dataType.NOTIFY, seq);
		return null;
	},	
	removeNotify : function(seq)
	{
		if(this.data)
			this.data.removeStorage(persistMap.dataType.NOTIFY, seq);
	},
	
	setSeq : function(seq, value)
	{
		if(this.data)
			return this.data.setStorage(persistMap.dataType.SEQ, seq, value);
		return true;
	},
	getSeq : function(seq)
	{
		if(this.data)
			return this.data.getStorage(persistMap.dataType.SEQ, seq);
		return null;
	},
	removeSeq : function(seq)
	{
		if(this.data)
			this.data.removeStorage(persistMap.dataType.SEQ, seq);
	},

	set : function(name, value)
	{
		if(this.data)
			return this.data.setStorage(persistMap.dataType.SETTING, name, value);
		return false;
	},
	get : function(name)
	{
		if(this.data)
			return this.data.getStorage(persistMap.dataType.SETTING, name);
		return null;
	}
};
persistMap.dataType = {
	SETTING : 1,
	SEQ : 2,
	NOTIFY : 3,
	MESSAGE : 4
}
persistMap.ieStorage = {
	storage : null,	//通用的状态, 例如bUseIm, bPlaySound等.
	
	seqStorage : null,	
	messageStorage : [],
	messageStorgeCnt : 5,
	notifyStorage : null,	
	
	getStorage : function(type, name)
	{
		switch(type)
		{
		case persistMap.dataType.SETTING:
			return this._getIeStorage(this.storage, 'xiaonei_im', name);
		case persistMap.dataType.SEQ:
			return this._getIeStorage(this.seqStorage, 'seq_storage', 's'+name);
		case persistMap.dataType.NOTIFY:
			return this._getIeStorage(this.notifyStorage, 'notify_storage', 'n'+name);
		case persistMap.dataType.MESSAGE:
			var n = name % this.messageStorgeCnt;			
			return this._getIeStorage(this.messageStorage[n], 'msg_storage' + n , 'm'+name);
		}
	},
	setStorage : function(type, name, value)
	{
		switch(type)
		{
		case persistMap.dataType.SETTING:
			return this._setIeStorage(this.storage, 'xiaonei_im', name, value);
		case persistMap.dataType.SEQ:
			return this._setIeStorage(this.seqStorage, 'seq_storage', 's'+name, value);
		case persistMap.dataType.NOTIFY:
			return this._setIeStorage(this.notifyStorage, 'notify_storage', 'n'+name, value);
		case persistMap.dataType.MESSAGE:
			var n = name % this.messageStorgeCnt;			
			return this._setIeStorage(this.messageStorage[n], 'msg_storage' + n , 'm'+name, value);
		}
		return null;
	},
	removeStorage : function(type, name, value)
	{
		switch(type)
		{
		case persistMap.dataType.SETTING:
			this._removeIeStorage(this.storage, 'xiaonei_im', name);
			break;
		case persistMap.dataType.SEQ:
			this._removeIeStorage(this.seqStorage, 'seq_storage', 's'+name);
			break;
		case persistMap.dataType.NOTIFY:
			this._removeIeStorage(this.notifyStorage, 'notify_storage', 'n'+name);
			break;			
		case persistMap.dataType.MESSAGE:
			var n = name % this.messageStorgeCnt;			
			this._removeIeStorage(this.messageStorage[n], 'msg_storage' + n , 'm'+name);
			break;
		}
	},
	
	clearOldData : function()
	{	
		if(!OS.isIE)
			return true;
		if(!this.storage)
			this.storage = this._createStorageNode();
		try{
			this.storage.load('xiaonei_im');
			for(var i=0; i<100; i++)
			{	
				var v = this.storage.getAttribute('s'+i.toString());
				if(!v) break;
				this.storage.removeAttribute('s'+i.toString());
			}
			this.storage.save('xiaonei_im');
		}
		catch(e)
		{
			this.errorCode = e.number & 0xFFFF;
			return false;
		}
		return true;
	},
	
	_createStorageNode : function()
	{
		var n = document.createElement('div');
		n.style.display = 'none';
		n.style.behavior = 'url(#default#userData)';
		document.body.appendChild(n);  
		return n;
	},
	
	initStorageNodes : function()
	{
		this.storage = this._createStorageNode();
		this.seqStorage = this._createStorageNode();
		
		for(var i=0; i<this.messageStorgeCnt; i++)
		{
			this.messageStorage[i] = this._createStorageNode();
		}
		
		this.notifyStorage = this._createStorageNode();
	},	

	_setIeStorage : function(node, saveName, key, value)
	{
		if(!OS.isIE) return false;
		try{
			node.setAttribute(key,value);
			node.save(saveName);
		}
		catch(e)
		{
			log.println("error : " + (e.number & 0xFFFF) + ' - ' + e.message);
			persistMap.errorCode = e.number & 0xFFFF;
			persistMap.rwExcept = true;
			return false;
		}
	},
	_getIeStorage : function(node, loadName, key)
	{
		if(!OS.isIE) return null;
		try{
			node.load(loadName);
			return node.getAttribute(key);
		}
		catch(e)
		{
			persistMap.errorCode = e.number & 0xFFFF;
			persistMap.rwExcept = true;
			return null;
		}
	},
	_removeIeStorage : function(node, saveName, key)
	{
		try{		
			node.load(saveName);
			node.removeAttribute(key);
			node.save(saveName);
		}
		catch(e){
			persistMap.errorCode = e.number & 0xFFFF;
			persistMap.rwExcept = true;
		}
	}	
}

persistMap.ffStorage = {
	getStorage : function(type, name)
	{
		return this._getFfStorage(this._getNameTyType(type, name));
	},
	setStorage : function(type, name, value)
	{
		return this._setFfStorage(this._getNameTyType(type, name), value);
	},	
	removeStorage : function(type, name)
	{
		this._removeFfStorage(this._getNameTyType(type, name));
	},
	
	_getNameTyType: function(type, name)
	{
		switch(type)
		{
		case persistMap.dataType.SEQ:
			return 's'+name;
		case persistMap.dataType.NOTIFY:
			return 'n'+name;
		case persistMap.dataType.MESSAGE:
			return 'm'+name;
		}
		return name;
	},
	_setFfStorage : function(key, value)
	{
		try{
			globalStorage[domain].setItem(key, value);
		}catch(e){
			persistMap.rwExcept = true;
			return false;
		}
		return true;
	},
	_getFfStorage : function(key)
	{
		try{
			return globalStorage[domain].getItem(key);
		}catch(e){
			persistMap.rwExcept = true;
			return null;
		}
	},
	
	_removeFfStorage : function(key)
	{
		try{
			globalStorage[domain].removeItem(key);
		}catch(e){
			persistMap.rwExcept = true;
		}
	}
}

persistMap.crmStorage = {
	getStorage : function(type, name)
	{
		return this._getCrmStorage(this._getTableTyType(type), name);
	},
	setStorage : function(type, name, value)
	{
		return this._setCrmStorage(this._getTableTyType(type), name, value);
	},	
	removeStorage : function(type, name)
	{
		this._removeCrmStorage(this._getTableTyType(type), name);
	},
	loadAll : function(type)
	{
		try{
			var rs = this.db.execute('select name, content from ' + this._getTableTyType(type) 
				+ ' order by name asc');
			return rs;
		}catch(e){
			persistMap.rwExcept = true;
			rs.close();
			return null;
		}
	},
	
	_db : null,
	init : function()
	{
		try{
			this.db = google.gears.factory.create('beta.database');
			this.db.open('xn_wim2');
			this.db.execute('create table if not exists setting' +
           		' (name text, content text)');
			this.db.execute('create table if not exists seq' +
           		' (name INTEGER NOT NULL PRIMARY KEY, content text)');
			this.db.execute('create table if not exists notify' +
           		' (name INTEGER NOT NULL PRIMARY KEY, content text)');
			this.db.execute('create table if not exists message' +
           		' (name INTEGER NOT NULL PRIMARY KEY, content text)');
		}
		catch(e)
		{
			this.db.execute("ROLLBACK"); 
			this.db = null;
			persistMap.rwExcept = true;
		}
	},
	_getTableTyType : function(type)
	{
		switch(type)
		{
		case persistMap.dataType.SETTING:
			return 'setting';
		case persistMap.dataType.SEQ:
			return 'seq';
		case persistMap.dataType.NOTIFY:
			return 'notify';
		case persistMap.dataType.MESSAGE:
			return 'message';
		}
		return 'none';
	},	
	_setCrmStorage : function(table, key, value)
	{
		try{
			this.db.execute('REPLACE into ' + table + ' values (?, ?)', [key, value]);
		}catch(e){
			persistMap.rwExcept = true;
			return false;
		}
		return true;
	},
	_getCrmStorage : function(table, key)
	{
		try{
			var rs = this.db.execute('select content from ' + table + ' where name=?', [key]);
			var ret = null;
			if(rs.isValidRow())
			{
				ret = rs.field(0);
			}
			rs.close();
			return ret;
		}catch(e){
			persistMap.rwExcept = true;
			rs.close();
			return null;
		}
	},
	
	_removeCrmStorage : function(table, key)
	{
		try{
			this.db.execute('delete from ' + table + ' where name=?', [key]);
		}catch(e){
			persistMap.rwExcept = true;
		}
	}
}

//widgets with unread messages, used for refresh.
persistMap.unreadInfo = {
	_data : [],
	loadCookie : function()
	{
		var p = imHelper.getCookie('wimunread');
		if(!p) return;
		this._data = p.split('-');
	},
	updateCookie : function()
	{
		var p = '';
		if(this._data.length > 1)
		{
			p = this._data[0] + '-' + this._data[1];
		}
		for(var i=2; i<this._data.length; i+=2)
		{
			p += '-' + this._data[i];
			p += '-' + this._data[i+1];
		}
		
		document.cookie = 'wimunread=' + p;
	},
	updateUin : function(uin, count)
	{
		if(!gConn.localConnect)
			return;
		for(var i=0; i<this._data.length; i+=2)
		{
			if(this._data[i] == uin)
			{
				if(this._data[i+1] != count)
				{
					this._data[i+1] = count;
					this.updateCookie();
				}					
				return;
			}
		}
		this._data.push(uin);
		this._data.push(count);
		this.updateCookie();
	},
	removeUin : function(uin)
	{
		//if(!gConn.localConnect)	//在某些情况下，如果没有连接，就会导致无法清除！所以该条件要拿掉
		//	return;
		var i = 0;
		for(; i<this._data.length; i+=2)
		{
			if(this._data[i] == uin)
				break;
		}
		for(; i+2<this._data.length; i+=2)
		{
			this._data[i] = this._data[i+2];
			this._data[i+1] = this._data[i+3];
		}
		if(this._data[i])
		{
			this._data.pop();
			this._data.pop();
			this.updateCookie();
		}
	}
}

persistMap.notifyHistory = {
	notifies : [],
	load : function()
	{
		if(OS.isIE)
		{
			try{
			persistMap.ieStorage.notifyStorage.load('notify_storage');
			}catch(e){return false;}
			
			for(var i=0; i<MAX_NOTIFY_SEQ; i++)
			{
				try{
				var s = persistMap.ieStorage.notifyStorage.getAttribute('n' + i);
				if(!s) continue;
				this.notifies.push([i, s]);
				}catch(e){}
			}
			
		}
		else if(OS.isFirefox)
		{
			for(var i=0; i<MAX_NOTIFY_SEQ; i++)
			{
				try{
				var s = persistMap.ffStorage._getFfStorage('n' + i);
				if(!s) continue;
				this.notifies.push([i, s]);
				}catch(e){}
			}
		}
		else if(OS.isChrome)
		{
			var rs = persistMap.crmStorage.loadAll(persistMap.dataType.NOTIFY);
			try{
				while (rs.isValidRow()) {
					var v = rs.field(1);
					if(!v) continue;
					this.notifies.push([rs.field(0), v]);					
					rs.next();
				}
				rs.close();
			}
			catch(e){rs.close();}
		}
		else
			return false;
		return true;
	},
	clear : function(){	
		this.notifies = [];
		if(OS.isIE)
		{
			try{
			persistMap.ieStorage.notifyStorage.load('notify_storage');
			}catch(e){return false;}
			
			for(var i=0; i<MAX_NOTIFY_SEQ; i++)
			{
				try{
				persistMap.ieStorage.notifyStorage.removeAttribute('n' + i);
				}catch(e){}
			}
			try{
			persistMap.ieStorage.notifyStorage.save('notify_storage');
			}catch(e){}
		}
		else if(OS.isFirefox)
		{
			for(var i=0; i<MAX_NOTIFY_SEQ; i++)
			{
				try{
				var s = persistMap.ffStorage._removeFfStorage('n' + i);
				}catch(e){}
			}
		}
		else if(OS.isChrome)
		{
			var rs = persistMap.crmStorage.loadAll(persistMap.dataType.NOTIFY);
			try{
				while (rs.isValidRow()) {
					var v = rs.field(1);
					if(!v) continue;
					this.notifies.push([rs.field(0), v]);					
					rs.next();
				}
				rs.close();
			}
			catch(e){rs.close();}
		}
		else
			return false;
		return true;
	}
}

persistMap.messageHistory = {
	messages : [],
	getSenderName : function(uin)
	{
		var list = this.find(uin);
		if(!list) return null;
		
		for(var i=0; i<list.length; i++)
		{
			var m = this.messages[list[i]];
			var e = gPersistData.parseEvent(m[0], m[1]);				
			if(e.fromuin == uin)
			{
				return e.fromname;
			}
		}
		return null;		
	},
	load : function()
	{
		if(OS.isIE)
		{
			var scnt = persistMap.ieStorage.messageStorgeCnt;
			for(var i=0; i<scnt; i++)
			{
				try{
				persistMap.ieStorage.messageStorage[i].load('msg_storage'+i);
				}catch(e){}
			}
			
			for(var i=0; i<MAX_MESSAGE_SEQ; i++)
			{
				try{
				var s = persistMap.ieStorage.messageStorage[i%scnt].getAttribute('m' + i);				
				if(!s) continue;
				this.messages.push([i, s]);
				}catch(e){}
			}
		}
		else if(OS.isFirefox)
		{
			for(var i=0; i<MAX_MESSAGE_SEQ; i++)
			{
				var s = persistMap.ffStorage._getFfStorage('m' + i);
				if(!s) continue;
				this.messages.push([i, s]);
			}
		}
		else if(OS.isChrome)
		{
		}
		else
			return false;
		
		this._makeIndex();
		return true;
	},
	clear : function(){
		this.messages = [];
		this._index = [];
		if(OS.isIE)
		{
			var scnt = persistMap.ieStorage.messageStorgeCnt;
			for(var i=0; i<scnt; i++)
			{
				try{
				persistMap.ieStorage.messageStorage[i].load('msg_storage'+i);
				}catch(e){}
			}			
			for(var i=0; i<MAX_MESSAGE_SEQ; i++)
			{
				try{
				persistMap.ieStorage.messageStorage[i%scnt].removeAttribute('m' + i);	
				}catch(e){}
			}
			for(var i=0; i<scnt; i++)
			{
				try{
				persistMap.ieStorage.messageStorage[i].save('msg_storage'+i);
				}catch(e){}
			}
		}
		else if(OS.isFirefox)
		{
			for(var i=0; i<MAX_MESSAGE_SEQ; i++)
			{
				try{
				persistMap.ffStorage._removeFfStorage('m' + i);
				}catch(e){}
			}
		}
		else if(OS.isChrome)
		{
		}
		else
			return false;
		
		this._makeIndex();
		return true;
	},
	dump: function()
	{
		for(var i=0; i<this._index.length; i++)
		{
			log.print(this._index[i][0] + ' : ');
			var v = this._index[i][1];
			for(var j=0; j<v.length; j++)
			{
				log.print(v[j] + ' ');
			}
			log.println('.');
		}
	},
	find : function(uin)
	{
		for(var i=0; i<this._index.length; i++)
		{
			if(this._index[i][0] == uin)
				return this._index[i][1];
		}
		return null;
	},
	_index : [],
	_pushIndex : function(uin, i)
	{
		var v = this.find(uin);
		if(!v)
		{
			this._index[this._index.length] = [uin, []];
			v = this._index[this._index.length - 1][1];
		}
		v[v.length] = i;
	},
	_makeIndex : function()
	{
		var uin = imHelper.getLoginUin();
		
		var latestSeq = 0;
		var latestTimestamp = 0;
		
		for(var i=0; i<this.messages.length; i++)
		{
			var e = gPersistData.parseEvent(this.messages[i][0], this.messages[i][1]);
			if(e.fromuin == uin)
			{
				this._pushIndex(e.touin, i);
			}
			else if(e.touin == uin)
			{
				this._pushIndex(e.fromuin, i);
			}
			
			if(latestTimestamp < e.timestamp)
			{
				latestTimestamp = e.timestamp;
				latestSeq = this.messages[i][0];	//latest message sequence
			}			
		}
		
		gPersistData.localMessageSeq = latestSeq;
		if(imHelper.getCookie('wimmseq') == null)
			persistMap.setMessageSeq(latestSeq);
	}
}

persistMap.init();
	
var eventType = {
	MSG_SEND	: 0x31,
	MSG_RECV	: 0x32,
	MSG_SEND_FAIL	: 0x33,
	NOTIFY_RECV	: 0x4,
	NOTIFY_REMOVE	: 5,

	CONN_START	: 10,
	CONN_SUCCESS	: 11,
	CONN_CLOSE	: 12,

	IM_DISABLE	: 20,
	IM_ENABLE	: 21
	
}
var connState = {
	CLOSED : 0,
	CONNECTING : 1,
	NOCONN_MAX : 3
};

NOTIFY_WIDGET_BIT=0x01;
SETTING_WIDGET_BIT=0x02;
BUDDY_WIDGET_BIT=0x04;

var gPersistData = {
	//settings
	readSettings : function()
	{
		this.bUseIm = this.getUseIm();
		if(this.bUseIm == null)
			this.bUseIm = true;

		this.bPlaySound = this.getPlaySound();
		if(this.bPlaySound == null)
			this.bPlaySound = true;
		
		this.bBlistAlwaysVisible= this.getBlistAlwaysVisible();
		if(this.bBlistAlwaysVisible == null)
			this.bBlistAlwaysVisible = false;

		this.bStoreHistory= this.getStoreHistory();
		if(this.bStoreHistory == null)
			this.bStoreHistory = true;

		return (persistMap.rwExcept == false);
	},

	getPlaySound : function()
	{
		var ps = persistMap.get('playSound');
		return parseInt(ps) != 0;
	},
	setPlaySound : function(b)
	{
		persistMap.set('playSound', b ? '1' : '0');
		this.bPlaySound = b;
	},
	bPlaySound : true, 

	getBlistAlwaysVisible : function()
	{
		var ps = persistMap.get('blistVisible');
		return parseInt(ps) == 1;
	},
	setBlistAlwaysVisible : function(b)
	{
		persistMap.set('blistVisible', b ? '1' : '0');
		this.bBlistAlwaysVisible = b;
	},
	bBlistAlwaysVisible : false, 

	getStoreHistory : function()
	{
		var ps = persistMap.get('storeHistory');
		return parseInt(ps) != 0;
	},
	setStoreHistory : function(b)
	{
		persistMap.set('storeHistory', b ? '1' : '0');
		this.bStoreHistory = b;
	},
	bStoreHistory : true, 

	getUseIm : function()
	{
		var ps = persistMap.get('useIm');
		var v = parseInt(ps);
		return parseInt(ps) != 0;
	},
	setUseIm : function(b)
	{
		persistMap.set('useIm', b ? '1' : '0');
		this.bUseIm = b;
	},
	bUseIm : null, 

	MAX_PERSIST_SEQ : 100,
	SYNC_EVENT_INTERVAL : 513,  //in ms

	writeFailCnt : 0,
	
	pushEvent : function(type, fromuin, fromname, touin, msg)
	{
		var timestamp = (new Date()).getTime();

		var value = type + '\n' + timestamp;
		var res = true;
		if(type == eventType.MSG_SEND || type == eventType.MSG_RECV)
		{
			value += '\n' + fromuin + '\n' + fromname.replace(/\n/g, " ") + '\n' + touin +'\n'+ msg;			
			
			var newSeq = persistMap.getMessageSeq();
			newSeq = (parseInt(newSeq) + 1) % MAX_MESSAGE_SEQ;
			
			log.println('push event : ' + newSeq + ' - ' + value);
			res = persistMap.setMessage(newSeq, value);
			
			persistMap.setMessageSeq(newSeq);
		}
		else if(type == eventType.MSG_SEND_FAIL)
		{
			//aflax中不会push该事件
		}
		else if(type == eventType.NOTIFY_RECV)
		{
			value += '\n' + fromuin + '\n' + fromname.replace(/\n/g, " ") + '\n' + touin;
			
			var newSeq = persistMap.getNotifySeq();
			newSeq = (parseInt(newSeq) + 1) % MAX_NOTIFY_SEQ;
			
			log.println('push event : ' + newSeq + ' - ' + value);
			res = persistMap.setNotify(newSeq, value);
			persistMap.setNotifySeq(newSeq);
		}
		else
		{		
			for(var i=1; i<arguments.length; i++)
				value += '\n' + arguments[i];
			
			var newSeq = persistMap.getGlobalSeq();
			newSeq = (parseInt(newSeq) + 1) % MAX_PERSIST_SEQ;
			
			res = persistMap.setSeq(newSeq, value);
			persistMap.setGlobalSeq(newSeq);
		}
			
		if(!res)
		{
			imHelper.report(REPORT_STORAGE_WERR + type);
		}
	}, 
 	parseEvent : function(seq, str)
	{
		var e = {};
		str=str.toString();
		var i = 0;
		var j = str.indexOf('\n', i);
		e.type = parseInt(str.substring(i, j));

		i = j+1;
		j = str.indexOf('\n', i);
		e.timestamp = str.substring(i, j);

		if(e.type == eventType.MSG_SEND || e.type == eventType.MSG_RECV)
		{ 
			i = j+1;
			j = str.indexOf('\n', i);
			e.fromuin = str.substring(i, j);
			//log.println(e.fromuin);

			i = j+1;
			j = str.indexOf('\n', i);
			e.fromname = str.substring(i, j);

			i = j+1;
			j = str.indexOf('\n', i);
			e.touin= str.substring(i, j);

			e.msg_content = str.substring(j+1);
		}
		else if(e.type == eventType.MSG_SEND_FAIL)
		{
			i = j+1;
			j = str.indexOf('\n', i);
			e.fromuin = str.substring(i, j);
			//log.println(e.fromuin);

			i = j+1;
			j = str.indexOf('\n', i);
			e.fromname = str.substring(i, j);

			i = j+1;
			j = str.indexOf('\n', i);
			e.touin= str.substring(i, j);

			e.msg_content = str.substring(j+1);
		}
		else if(e.type == eventType.NOTIFY_RECV)
		{
			i = j+1;
			j = str.indexOf('\n', i);
			e.touin = str.substring(i, j);

			i = j+1;
			j = str.indexOf('\n', i);
			e.title = str.substring(i, j);

			e.content = str.substring(j+1);
		}
		else if(e.type == eventType.NOTIFY_REMOVE)
		{
			e.remove_seq = str.substring(j+1);
		}
		e.seq = seq;
		return e;
	},
	
	localSeq : parseInt(persistMap.getGlobalSeq()),
	localMessageSeq : parseInt(persistMap.getMessageSeq()),
	localNotifySeq : parseInt(persistMap.getNotifySeq()),
	
	seqRetryFlag : false,
	
	syncEvent : function()
	{
		//log.println('to sync event.');
		if(gConn.pageUnloaded)
		{
			return;
		}

		if(!imHelper.isLoginUser())
		{
			if(gConn.connection && gConn.connection.close)
			{
				gConn.connection.close();
				gConn.connection = null;
				gConn.localConnect = false;
				gConn.setConnState(connState.CLOSED);
			}
			//if(!OS.isIE6)
				imui.tabBar.onSessionOut();
			return;
		}

		var seq = persistMap.getGlobalSeq();
		var lastSeq;
		while(this.localSeq != seq)
		{
			log.println('sync seq event');
			lastSeq = this.localSeq;
			this.localSeq++;
			this.localSeq %= MAX_PERSIST_SEQ;
			var es = persistMap.getSeq(this.localSeq);
			if(es == null)
			{
				log.println('es null');
				imHelper.report(REPORT_NULL_STORAGE);
				continue;
			}

			var e = this.parseEvent(this.localSeq, es);
			var now = (new Date()).getTime();
			if(e.timestamp && e.timestamp - now > 5000)
			{
				log.println('error - outdated data read!');
				imHelper.report(REPORT_OUTDATED_DATA);
				if(this.localSeq == 0)
					this.localSeq = MAX_PERSIST_SEQ - 1;
				else
					this.localSeq--;
				break;
			}
			this._dispatchEvent(e);
		}		
		seq = persistMap.getMessageSeq();
		while(this.localMessageSeq != seq)
		{
			lastSeq = this.localMessageSeq;
			this.localMessageSeq++;
			log.println('sync message event ' + this.localMessageSeq);
			
			this.localMessageSeq %= MAX_MESSAGE_SEQ;
			var es = persistMap.getMessage(this.localMessageSeq);
			if(es == null)
			{
				imHelper.report(REPORT_NULL_STORAGE);
				continue;
			}

			var e = this.parseEvent(this.localMessageSeq, es);
			var now = (new Date()).getTime();
			if(e.timestamp && e.timestamp - now > 5000)
			{
				imHelper.report(REPORT_OUTDATED_DATA);
				if(this.localMessageSeq == 0)
					this.localMessageSeq = MAX_MESSAGE_SEQ - 1;
				else
					this.localMessageSeq--;
				break;
			}
			this._dispatchEvent(e);
		}
		
		seq = persistMap.getNotifySeq();
		while(this.localNotifySeq != seq)
		{
			log.println('seq : ' + seq);
			lastSeq = this.localNotifySeq;
			this.localNotifySeq++;
			this.localNotifySeq %= MAX_NOTIFY_SEQ;
			var es = persistMap.getNotify(this.localNotifySeq);
			if(es == null)
			{
				imHelper.report(REPORT_NULL_STORAGE);
				continue;
			}

			var e = this.parseEvent(this.localNotifySeq, es);
			var now = (new Date()).getTime();
							
			if(e.timestamp && e.timestamp - now > 5000)
			{
				log.println('error - outdated data read!');
				imHelper.report(REPORT_OUTDATED_DATA);
				if(this.localNotifySeq == 0)
					this.localNotifySeq = MAX_NOTIFY_SEQ - 1;
				else
					this.localNotifySeq--;
				break;
			}
			this._dispatchEvent(e);
		}
		this._syncPresence();
		window.setTimeout('gPersistData.syncEvent()',this.SYNC_EVENT_INTERVAL);
	},
	
	_dispatchEvent : function(e)
	{
		var state = gConn.getConnState();		
		var hostUin = imHelper.getLoginUin();
		switch(e.type)
		{
		case eventType.MSG_SEND:
			if(state > connState.NOCONN_MAX)
			{
				if(e.fromuin == hostUin)
				{
					//log.println('might need display');
					//log.println('content : ' + e.fromuin + '->' + e.touin);
					imui.controller.onSendMessage(e);
				}
				if(gConn.localConnect)
				{
					log.println('msg sent...');
					if(e.msg_content)
						gConn.sendMsg(e);
				}
			}
			else if(state == connState.CONNECTING)
			{
				log.println('connecting ... plz wait');

				if(gConn.localConnect)
					this.pushEvent(e.type, e.fromuin, e.fromname, e.touin, e.msg);
			}
			else
			{
				log.println('no connection, send fail : ' + e.msg_content);
			}
			break;
		case eventType.MSG_RECV:
			if(!gConn.localConnect)
				log.println('recv pushed message : ' + e.msg_content);

			if(e.touin == hostUin)
			{
				if(gPagerType == pagerType.NEW_PAGER)
				{
					imui.controller.onRecvMessage(e);
				}
			}

			break;
		case eventType.MSG_SEND_FAIL:
			imui.controller.onSendMessageFail(e);
			break;
		case eventType.CONN_SUCCESS:
			log.println('event : conn created');
			imHelper.report(REPORT_AFLAX_CONNECT);

			if(gPagerType == pagerType.NEW_PAGER)
				imui.tabBar.onConnSuccess();

			break;
		case eventType.CONN_CLOSE:
			log.println('event : conn close ' + this.localSeq);
			if(gPagerType == pagerType.NEW_PAGER)
				imui.tabBar.onConnClose();

			// more refinement required here....
			log.println('CONN_CLOSE - connState : ' + state);
			imHelper.report(REPORT_AFLAX_CONNECT);
			if(!this.localConnect)
			{
				gConn.initAflaxConn();  //reentry bug?
			}
			else
				imHelper.report(REPORT_DUP_CONNECTION);
			break;
		case eventType.IM_DISABLE:
			if(gConn.connection && gConn.connection.close)
				gConn.connection.close();
			gConn.connection = null;
			gConn.localConnect = false;
			gPersistData.bUseIm = false;

			imui.tabBar.onConnClose();

			gConn.setConnState(connState.CLOSED);
			break;
		case eventType.IM_ENABLE:
			gPersistData.bUseIm = true;
			break;
		case eventType.NOTIFY_RECV:
			imui.controller.onRecvNotify(e);
			break;
		case eventType.NOTIFY_REMOVE:
			imui.controller.onRemoveNotify(e);
			break;
		}
	},
	
	_lastPresence : 0,
	_syncPresence : function()
	{
		if(!imui.controller.onlineFriendsReady)
			return;
		var np = persistMap.getPresenceDesc();
		if(np==null || this._lastPresence == np)
		{
			return;
		}
		var pres = parseInt(np.substr(0,1));
		
		imui.controller.onShowNotify(pres & NOTIFY_WIDGET_BIT);
		imui.controller.onShowSetting(pres & SETTING_WIDGET_BIT);
		imui.controller.onShowBuddy(pres & BUDDY_WIDGET_BIT);

		imui.chatTabs.syncFocus(np.substring(1,np.length));
		
		this._lastPresence = np;
	}	
};

var gConn = {
	aflax : null, 
	connection : null,
	localConnect : false, //local page is connecting or has connected the server?
	CONN_CHECK_INTERVAL : 2859, 	//in ms
	CONN_TIMESTAMP_EXPIRES  : 150000, 	//in ms

	CONNECT_TIMEOUT   : 30000,	//in ms
	pageUnloaded : false,
	
	switchConn : function()
	{
		if(gPersistData.bUseIm)
		{
			var s = this.getConnState();
			if(s == connState.CLOSED)
				this.initAflaxConn(true);
			else if(s > connState.NOCONN_MAX)
			{
				log.println('improper conn exist...');
				imui.tabBar.onConnSuccess();
			}

			gPersistData.pushEvent(eventType.IM_ENABLE);
		}
		else
		{
			gPersistData.pushEvent(eventType.IM_DISABLE);
		}
	},

	getConnState : function()
	{ 
		var cks = document.cookie;
		if(cks.length > 0)
		{ 
			var pattern = 'wimconn=';
			var i = cks.indexOf(pattern);
			if(i >= 0)
			{ 
				i += pattern.length;
				var j = i;
				var j = cks.indexOf(";", i);
    			if (j == -1)
        			j = cks.length;
        
				var s = cks.substring(i, j);
				return parseInt(s);
			}
		} 
		return connState.CLOSED;
	},

	setConnState : function(state)
	{
		document.cookie = 'wimconn=' + state;
	},

	connectingCounter : 0, 
	tsUpdateCount : 0,
	connStateCheck: function()
	{
		if(!imHelper.isLoginUser())
			return;

		if(gConn.pageUnloaded)
			return;

		var state = gConn.getConnState();

		if(state == connState.CONNECTING)
		{
			//connection timeout
			if(++this.connectingCounter * this.CONN_CHECK_INTERVAL > this.CONNECT_TIMEOUT)
			{
				log.println('connect timeout');
				this.connection = null;
				this.connectingCounter = 0;

				this.setConnState(connState.CLOSED);

				imHelper.report(REPORT_CONN_TIMEOUT);
				if(!this.localConnect)
				{
					this.localConnect = false;
					window.setTimeout('gConn.connStateCheck()', gConn.CONN_CHECK_INTERVAL);
				}
				else
				{
					log.println('timeout and no reconnect');
					this.initReentryFlag = 1;
				}
				return;
			}
		}
		else
		{
			this.connectingCounter = 0;

			if(state == connState.CLOSED)
			{
				log.println('connState closed. try to connect...');
				this.initAflaxConn();
			}
			else if(state > connState.NOCONN_MAX)
			{
				var t = (new Date()).getTime();				
				if(t - state > gConn.CONN_TIMESTAMP_EXPIRES)
				{
					this.setConnState(connState.CLOSED);
				}
				else
				{
					if(this.localConnect)
					{
						this.tsUpdateCount++;
						if(this.tsUpdateCount > 10)
						{
							this.tsUpdateCount = 0;
							this.setConnState(t);
						}
					}
				}
			}
			else
			{
				this.setConnState(connState.CLOSED);
			}
		}
				
		window.setTimeout('gConn.connStateCheck()', gConn.CONN_CHECK_INTERVAL);
	},
	connectFailCnt : 0,
	connectFailFlag: 0,

	connBroken : false,
	onConnectEvent : function(val)
	{
		this.initReentryFlag = 0;
		if(val)
		{
			this.connBroken = false;

			this.connectFailCnt = 0;
			log.println("Connection successful.");
			
			var t = (new Date()).getTime();			
			gConn.setConnState(t);

			log.println("push CONN_SUCCESS.");
			gPersistData.pushEvent(eventType.CONN_SUCCESS);

			var uin = imHelper.getLoginUin();

			var login = imHelper.buildLoginMsg(uin, imHelper.getCookie('societyguester'));
			this.connection.send(login);
			setTimeout('imHelper.report(REPORT_CONN_SUCCESS)', 0);
		}
		else
		{
			log.println("connect server fail and retry.");
			this.connBroken = true; // flash incorrectly trigger event more than once.
				// reetrance caused by this bug must be avoided! 

			if(++this.connectFailCnt <= 3)
			{
				log.println("retry............ : " + this.connectFailCnt);
				gConn.onCloseEvent();
			}
			else
			{
				log.println("retry out." + this.connectFailCnt);
				this.setConnState(connState.CLOSED);
				gPersistData.pushEvent(eventType.CONN_CLOSE);
				setTimeout('imHelper.report(REPORT_CONN_RETRY_OUT)', 0);
			}
		}
	},
	

	loginRes : 0,
	onDataEvent : function (str)
	{
		var m = imHelper.extractMessage(str);
		if(!m)
		{
			return;
		}
		
		log.println('m : ' + m);
			for(var x in m) log.println(x + ' : ' + m[x]);

		if(m.cmd == 'msg' || m.cmd == 'offline_msg')
		{
			imHelper.playMessageSound();
			gPersistData.pushEvent(eventType.MSG_RECV, m.fromuin, m.fromname, m.touin, m.msg_content);
			imHelper.report(REPORT_MSG_RECV);
		}
		else if(m.cmd == 'login_res')
		{
			log.println('login result received @ -- ' + this.dc);

			this.loginRes = m.res;
			if(this.loginRes != 0)
			{
				if(this.connection)
				{
					this.connection.close();
					this.connection = null;
				}
				this.initReentryFlag = 1;
				this.setConnState(connState.CLOSED);
				gPersistData.pushEvent(eventType.CONN_CLOSE);

				imHelper.report(REPORT_LOGIN_REFUSED);
			}
		}
		else if(m.cmd == 'notify') 
		{
			var wistate = imHelper.getCookie('_wi');
			if ('running' != wistate)
			{
				imHelper.playNotifySound();
				gPersistData.pushEvent(eventType.NOTIFY_RECV, m.touin, m.title, m.content);
			}
		}
	},
	
	onCloseEvent : function ()
	{
		log.println("Connection closed.");
		imHelper.report(REPORT_CONN_CLOSED);

		this.setConnState(connState.CONNECTING);
		if(this.initReentryFlag)
			return;
		this.initReentryFlag = 1;

		this.localConnect = true;

		imHelper.report(REPORT_AFLAX_CONNECT);
		setTimeout("gConn.aflaxConnect()", 100);
	},
	msgPushCount : 0,
	pushTestMessage : function()
	{
		return;
	},

	pushPersistMessage : function()
	{
		return;
	},

	sendMsg : function (e)
	{
		if(!this.connection || !e.msg_content)
		{
			log.println('send() - conn error!');
		}
		else
		{
			if(e.msg_content == 'undefined')
			{
				imHelper.reportChar('error/undef_to_send', true);
				return;
			}
			this.connection.send(imHelper.buildMessage(e.fromuin, e.fromname, e.touin, e.toname, e.msg_content));
			imHelper.report(REPORT_MSG_SENT);
		}
	},
	initReentryFlag : 0,
	flashLoaded : false,
	initAflaxConn : function(isReconnect)
	{
		if(!gPersistData.bUseIm)
		{
			return;
		}
		if(isReconnect)
		{
			this.connectFailCnt = 0;
		}
		if(this.connectFailCnt > 3)
		{
			return;
		}
		if(!this.flashLoaded) 
		{
			return;
		}
		if(!imHelper.isLoginUser())
		{
			log.println('not login user');
			return;
		}
		if(this.initReentryFlag)
		{
			return;
		}
		this.initReentryFlag = 1;

		log.println('enter init flax conn');

		var s = this.getConnState();
		if(s != connState.CLOSED)
		{
			log.println('connected or connecting in other page : ' + s);
			this.initReentryFlag = 0;
			if(s > connState.NOCONN_MAX)
			{
				imui.tabBar.onConnSuccess();
			}
			return;
		}
		this.setConnState(connState.CONNECTING);
		log.println('start connecting...');
		this.localConnect = true;

		//use insertFlash()
		if(this.aflax != null)
		{
			this.aflaxConnect();
		}
	},
	connBeginCount : 0,
	aflaxConnect: function ()
	{
		if(this.loginRes != 0)
			return;
		if(!gPersistData.bUseIm)
			return;

		log.println('socket connecting...');
		if(this.connection && this.connection.close)
		{
			log.println('clear conn');
			this.connection.close();
			this.connection = null;
		}

		imHelper.report(REPORT_CONN_BEGIN);
		try{
			this.connection = new AFLAX.Socket(this.aflax, 
				//'wtalk.xiaonei.com', 39000, 
				'wtalk.' + rootDomain, 39000, 
				'gConn.onConnectEvent', 'gConn.onDataEvent', 'gConn.onCloseEvent');
		}
		catch(e){}
	},
	clearConnCount : 0,
	clearAflaxConn : function()
	{
		//XMLSocket.close() shouldn't be called here! both ie and ff say 'syntax error'.
		this.connection = null;
		this.pageUnloaded = true;

		log.println('exit the page');
		if(this.localConnect)
		{
			this.localConnect = false;
			this.clearConnCount++;
			this.setConnState(connState.CLOSED);
			gPersistData.pushEvent(eventType.CONN_CLOSE);
		}
	},	

	loadConn : function()
	{
		gConn.aflax = new AFLAX("./aflax/aflax.swf");
		gConn.aflax.insertFlash(1, 1, "#ffffff", "gConn.onAflaxLoad", false);
		
		window.setTimeout('gPersistData.syncEvent()', 1000);
		window.setTimeout('gConn.connStateCheck()', 2000);
		
		if( window.addEventListener ) {
			window.addEventListener('unload', gConn.unloadWebim, true);
		}
		else if(window.attachEvent)
		{
			window.attachEvent('onbeforeunload', gConn.unloadWebim);
		}		
	},
	
	onAflaxLoad : function()
	{
		log.println('flash load ok....');
		this.flashLoaded = true;
		this.initAflaxConn();
		imHelper.report(REPORT_AFLAX_CONNECT);
	}, 
	unloadWebim : function()
	{
		if(OS.isIE) //flash bug fix.
		{
			var objs = document.getElementsByTagName("OBJECT");
			for (var i=0; i < objs.length; i++) 
			{
				for (var x in objs[i]) 
				{
					if (typeof objs[i][x] == 'function') {
						objs[i][x] = null;
					}
				}
			}
		}
		gConn.clearAflaxConn();
	}
};

function registerWebim()
{
 	if(xiaonei && OS.isIE && imHelper.startXnclient())
 	{
		return;
	}

	var host;
	try{
		host = top.location.hostname;
	}
	catch(e){return;}
	gPagerType = imHelper.getPagerType();

	switch(gPagerType) {
	case pagerType.NO_PAGER:
		break;
	case pagerType.CLASSIC_PAGER:
		//imui.showImui();
		break;
	case pagerType.NOTIFY_PAGER:
		//imui.showImui();
		break;
	case pagerType.NEW_PAGER:
		if(gPersistData.bStoreHistory)
		{
			try{
			persistMap.notifyHistory.load();
			persistMap.messageHistory.load();
			}catch(e){}
		}
		else
		{
			try{
			persistMap.notifyHistory.clear();
			persistMap.messageHistory.clear();
			}catch(e){}
		}
		gConn.loadConn();
		try
		{
			imui.showImui();
		}catch(e){}		
		break;
	}
}
