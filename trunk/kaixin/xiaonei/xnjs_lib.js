null/**********css.js***********/
/******BEGIN LICENSE BLOCK*******
* 
* Common Public Attribution License Version 1.0.
*
* The contents of this file are subject to the Common Public Attribution 
* License Version 1.0 (the "License") you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://developers.facebook.com/fbopen/cpal.html. The License is based 
* on the Mozilla Public License Version 1.1 but Sections 14 and 15 have 
* been added to cover use of software over a computer network and provide 
* for limited attribution for the Original Developer. In addition, Exhibit A 
* has been modified to be consistent with Exhibit B.
* Software distributed under the License is distributed on an "AS IS" basis, 
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License 
* for the specific language governing rights and limitations under the License.
* The Original Code is Facebook Open Platform.
* The Original Developer is the Initial Developer.
* The Initial Developer of the Original Code is Facebook, Inc.  All portions 
* of the code written by Facebook, Inc are 
* Copyright 2006-2008 Facebook, Inc. All Rights Reserved.
*
*
********END LICENSE BLOCK*********/

/**
 *  @author epriestley, marcel
 *
 *  @requires string dom util ua
 *  @provides css
 */

var Util = {
  error: function(errmsg) {
    if (typeof console != 'undefined' && console.error) {
      console.error(errmsg);
    }
  }
};
////////////////////////////////

function trim(text) {
  return String(text).replace(/^\s*|\s*$/g, '');
}

var CSS = {

  hasClass : function(element, className) {
    if (element && className && element.className) {
      return new RegExp('\\b'+trim(className)+'\\b').test(element.className);
    }
    return false;
  },

  addClass : function(element, className) {
    if (element && className) {
      if (!CSS.hasClass(element, className)) {
        if (element.className) {
          element.className += ' ' + trim(className);
        } else {
          element.className  = trim(className);
        }
      }
    }

    return this;
  },

  removeClass : function(element, className) {
    if (element && className && element.className) {
      className = trim(className);

      var regexp = new RegExp('\\b'+className+'\\b', 'g');
      element.className = element.className.replace(regexp, '');
    }

    return this;
  },

  setOpacity : function(element, opacity) {
    var opaque = (opacity == 1);

    try {
      element.style.opacity = (opaque ? '' : ''+opacity);
    } catch (ignored) {}

    try {
      element.style.filter  = (opaque ? '' : 'alpha(opacity='+(opacity*100)+')');
    } catch (ignored) {}
  },

  getOpacity : function(element) {
    var opacity = CSS.getStyle(element,'filter');
    var val = null;
    if (opacity && (val=/(\d+(?:\.\d+)?)/.exec(opacity))) {
      return parseFloat(val.pop())/100;
    }
    else if (opacity = CSS.getStyle(element,'opacity')) {
      return parseFloat(opacity);
    }
    else {
      return 1.0;
    }
  },

  getStyle : function(element,property) {
    function hyphenate(property){
      return property.replace(/[A-Z]/g,function(match){return'-'+match.toLowerCase();});
    }
    if (window.getComputedStyle) {
      return window.getComputedStyle(element,null).getPropertyValue(hyphenate(property));
    }
    if (document.defaultView&&document.defaultView.getComputedStyle){
      var computedStyle = document.defaultView.getComputedStyle(element,null);
      if(computedStyle)
        return computedStyle.getPropertyValue(hyphenate(property));
      if(property == "display")
        return"none";
      Util.error("Can't retrieve requested style " + property + " due to a bug in Safari");
    }

    if (element.currentStyle) {
      return element.currentStyle[property];
    }
    return element.style[property];
  }
};

var has_css_class_name    = CSS.hasClass;
var add_css_class_name    = CSS.addClass;
var remove_css_class_name = CSS.removeClass;
var set_opacity           = CSS.setOpacity;

/*********dom.js**********/
var DOM = {
  getBoxHeight : function(obj) {
    var pT = parseInt(CSS.getStyle(obj,'paddingTop'),10),pB=parseInt(CSS.getStyle(obj,'paddingBottom'),10),bT=parseInt(CSS.getStyle(obj,'borderTopWidth'),10),bW=parseInt(CSS.getStyle(obj,'borderBottomWidth'),10);
    return obj.offsetHeight-(pT?pT:0)-(pB?pB:0)-(bT?bT:0)-(bW?bW:0);
  },

  getBoxWidth : function(obj) {
    var pL = parseInt(CSS.getStyle(obj,'paddingLeft'),10),pR=parseInt(CSS.getStyle(obj,'paddingRight'),10),bL=parseInt(CSS.getStyle(obj,'borderLeftWidth'),10),bR=parseInt(CSS.getStyle(obj,'borderRightWidth'),10);
    return obj.offsetWidth-(pL?pL:0)-(pR?pR:0)-(bL?bL:0)-(bR?bR:0);
  }
};

function get_caret_position(element){
  if (!is_node(element, ['input', 'textarea'])) {
    return {start: undefined, end: undefined};
  }

  if (!document.selection) {
    return {start: element.selectionStart, end: element.selectionEnd};
  }

  if (is_node(element, 'input')) {
    var range = document.selection.createRange();
    return {start: -range.moveStart('character', -element.value.length),
              end: -range.moveEnd('character', -element.value.length)};
  } else {
    var range = document.selection.createRange();
    var range2 = range.duplicate();
    range2.moveToElementText(element);
    range2.setEndPoint('StartToEnd', range);
    var end = element.value.length - range2.text.length;
    range2.setEndPoint('StartToStart', range);
    return {start: element.value.length - range2.text.length, end: end};
  }
}


// sets the caret position of a textarea or input. end is optional and will default to start
function set_caret_position(obj, start, end) {
  if (is_node(obj, ['input', 'textarea'])) {
    if (document.selection) {
      // IE is inconsistent about character offsets when it comes to carriage returns, so we need to manually take them into account
      if (obj.tagName == 'TEXTAREA') {
        var i = obj.value.indexOf("\r", 0);
        while (i != -1 && i < end) {
          end--;
          if (i < start) {
            start--;
          }
          i = obj.value.indexOf("\r", i + 1);
        }
      }
      var range = obj.createTextRange();
      range.collapse(true);
      range.moveStart('character', start);
      if (end != undefined) {
        range.moveEnd('character', end - start);
      }
      range.select();
    } else {
      obj.selectionStart = start;
      var sel_end = end == undefined ? start : end;
      obj.selectionEnd = Math.min(sel_end, obj.value.length);
      obj.focus();
    }
  }
}


/****************swfobject.js******************/
if(typeof deconcept=="undefined")var deconcept={};if(typeof deconcept.util=="undefined")deconcept.util={};if(typeof deconcept.SWFObjectUtil=="undefined")deconcept.SWFObjectUtil={};deconcept.SWFObject=function(swf,id,w,h,ver,c,quality,xiRedirectUrl,redirectUrl,detectKey){if(!document.getElementById){return;}
this.DETECT_KEY=detectKey?detectKey:'detectflash';this.skipDetect=deconcept.util.getRequestParameter(this.DETECT_KEY);this.params={};this.variables={};this.attributes=[];this.fallback_html='';this.fallback_js_fcn=function(){};if(swf){this.setAttribute('swf',swf);}
if(id){this.setAttribute('id',id);}
if(w){this.setAttribute('width',w);}
if(h){this.setAttribute('height',h);}
if(ver){this.setAttribute('version',new deconcept.PlayerVersion(ver.toString().split(".")));}
this.installedVer=deconcept.SWFObjectUtil.getPlayerVersion();if(!window.opera&&document.all&&this.installedVer.major>7){if(!deconcept.unloadSet){deconcept.SWFObjectUtil.prepUnload=function(){__flash_unloadHandler=function(){};__flash_savedUnloadHandler=function(){};window.attachEvent("onunload",deconcept.SWFObjectUtil.cleanupSWFs);}
window.attachEvent("onbeforeunload",deconcept.SWFObjectUtil.prepUnload);deconcept.unloadSet=true;}}
if(c){this.addParam('bgcolor',c);}
var q=quality?quality:'high';this.addParam('quality',q);this.setAttribute('useExpressInstall',false);this.setAttribute('doExpressInstall',false);var xir=(xiRedirectUrl)?xiRedirectUrl:window.location;this.setAttribute('xiRedirectUrl',xir);this.setAttribute('redirectUrl','');if(redirectUrl){this.setAttribute('redirectUrl',redirectUrl);}}
deconcept.SWFObject.prototype={useExpressInstall:function(path){this.xiSWFPath=!path?"/swf/expressinstall.swf":path;this.setAttribute('useExpressInstall',true);},setAttribute:function(name,value){this.attributes[name]=value;},getAttribute:function(name){return this.attributes[name]||"";},addParam:function(name,value){this.params[name]=value;},getParams:function(){return this.params;},addVariable:function(name,value){this.variables[name]=value;},getVariable:function(name){return this.variables[name]||"";},getVariables:function(){return this.variables;},getVariablePairs:function(){var variablePairs=[];var key;var variables=this.getVariables();for(key in variables){variablePairs[variablePairs.length]=key+"="+variables[key];}
return variablePairs;},getSWFHTML:function(){var swfNode="";if(navigator.plugins&&navigator.mimeTypes&&navigator.mimeTypes.length){if(this.getAttribute("doExpressInstall")){this.addVariable("MMplayerType","PlugIn");this.setAttribute('swf',this.xiSWFPath);}
swfNode='<embed type="application/x-shockwave-flash" src="'+htmlspecialchars(this.getAttribute('swf'))+'" width="'+htmlspecialchars(this.getAttribute('width'))+'" height="'+htmlspecialchars(this.getAttribute('height'))+'" style="'+htmlspecialchars(this.getAttribute('style')||"")+'"';swfNode+=' id="'+htmlspecialchars(this.getAttribute('id'))+'" name="'+htmlspecialchars(this.getAttribute('id'))+'" ';var params=this.getParams();for(var key in params){swfNode+=htmlspecialchars(key)+'="'+htmlspecialchars(params[key])+'" ';}
var pairs=this.getVariablePairs().join("&");if(pairs.length>0){swfNode+='flashvars="'+pairs+'"';}
swfNode+='/>';}else{if(this.getAttribute("doExpressInstall")){this.addVariable("MMplayerType","ActiveX");this.setAttribute('swf',this.xiSWFPath);}
swfNode='<object id="'+this.getAttribute('id')+'" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="'+this.getAttribute('width')+'" height="'+this.getAttribute('height')+'" style="'+(this.getAttribute('style')||"")+'">';swfNode+='<param name="movie" value="'+this.getAttribute('swf')+'" />';var params=this.getParams();for(var key in params){swfNode+='<param name="'+key+'" value="'+params[key]+'" />';}
var pairs=this.getVariablePairs().join("&");if(pairs.length>0){swfNode+='<param name="flashvars" value="'+pairs+'" />';}
swfNode+="</object>";}
return swfNode;},write:function(elementId){if(this.getAttribute('useExpressInstall')){var expressInstallReqVer=new deconcept.PlayerVersion([6,0,65]);if(this.installedVer.versionIsValid(expressInstallReqVer)&&!this.installedVer.versionIsValid(this.getAttribute('version'))){this.setAttribute('doExpressInstall',true);this.addVariable("MMredirectURL",escape(this.getAttribute('xiRedirectUrl')));document.title=document.title.slice(0,47)+" - Flash Player Installation";this.addVariable("MMdoctitle",document.title);}}
var n=(typeof elementId=='string')?document.getElementById(elementId):elementId;if(this.skipDetect||this.getAttribute('doExpressInstall')||this.installedVer.versionIsValid(this.getAttribute('version'))){n.innerHTML=this.getSWFHTML();return true;}else{if(this.getAttribute('redirectUrl')!=""){document.location.replace(this.getAttribute('redirectUrl'));}
need_version=this.getAttribute('version').major+'.'+this.getAttribute('version').minor+'.'+this.getAttribute('version').rev;have_version=this.installedVer.major+'.'+this.installedVer.minor+'.'+this.installedVer.rev;this.fallback_js_fcn(have_version,need_version);n.innerHTML=this.fallback_html;}
return false;}}
deconcept.SWFObjectUtil.getPlayerVersion=function(){var PlayerVersion=new deconcept.PlayerVersion([0,0,0]);if(navigator.plugins&&navigator.mimeTypes.length){for(k=0;k<navigator.plugins.length;k++){try{x=navigator.plugins[k];if(x.name=='Shockwave Flash'){PlayerVersion_tmp=new deconcept.PlayerVersion(x.description.replace(/([a-zA-Z]|\s)+/,"").replace(/(\s+r|\s+b[0-9]+)/,".").split("."));if(typeof PlayerVersion=='undefined'||PlayerVersion_tmp.major>PlayerVersion.major||(PlayerVersion_tmp.major==PlayerVersion.major&&(PlayerVersion_tmp.minor>PlayerVersion.minor||(PlayerVersion_tmp.minor==PlayerVersion.minor&&PlayerVersion_tmp.rev>PlayerVersion.rev)))){PlayerVersion=PlayerVersion_tmp;}}}catch(honk){}}}else if(navigator.userAgent&&navigator.userAgent.indexOf("Windows CE")>=0){var axo=1;var counter=3;while(axo){try{counter++;axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+counter);PlayerVersion=new deconcept.PlayerVersion([counter,0,0]);}catch(e){axo=null;}}}else{try{var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");}catch(e){try{var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");PlayerVersion=new deconcept.PlayerVersion([6,0,21]);axo.AllowScriptAccess="always";}catch(e){if(PlayerVersion.major==6){return PlayerVersion;}}
try{axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash");}catch(e){}}
if(axo!=null){PlayerVersion=new deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));}}
return PlayerVersion;}
deconcept.PlayerVersion=function(arrVersion){this.major=arrVersion[0]!=null?parseInt(arrVersion[0]):0;this.minor=arrVersion[1]!=null?parseInt(arrVersion[1]):0;this.rev=arrVersion[2]!=null?parseInt(arrVersion[2]):0;}
deconcept.PlayerVersion.prototype.versionIsValid=function(fv){if(this.major<fv.major)return false;if(this.major>fv.major)return true;if(this.minor<fv.minor)return false;if(this.minor>fv.minor)return true;if(this.rev<fv.rev)return false;return true;}
deconcept.util={getRequestParameter:function(param){var q=document.location.search||document.location.hash;if(param==null){return q;}
if(q){var pairs=q.substring(1).split("&");for(var i=0;i<pairs.length;i++){if(pairs[i].substring(0,pairs[i].indexOf("="))==param){return pairs[i].substring((pairs[i].indexOf("=")+1));}}}
return"";}}
deconcept.SWFObjectUtil.cleanupSWFs=function(){var objects=document.getElementsByTagName("OBJECT");for(var i=objects.length-1;i>=0;i--){objects[i].style.display='none';for(var x in objects[i]){if(typeof objects[i][x]=='function'){objects[i][x]=function(){};}}}}
if(!document.getElementById&&document.all){document.getElementById=function(id){return document.all[id];}}
var getQueryParamValue=deconcept.util.getRequestParameter;var FlashObject=deconcept.SWFObject;var SWFObject=deconcept.SWFObject;

/************vector.js**********/
/******BEGIN LICENSE BLOCK*******
* 
* Common Public Attribution License Version 1.0.
*
* The contents of this file are subject to the Common Public Attribution 
* License Version 1.0 (the "License") you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://developers.facebook.com/fbopen/cpal.html. The License is based 
* on the Mozilla Public License Version 1.1 but Sections 14 and 15 have 
* been added to cover use of software over a computer network and provide 
* for limited attribution for the Original Developer. In addition, Exhibit A 
* has been modified to be consistent with Exhibit B.
* Software distributed under the License is distributed on an "AS IS" basis, 
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License 
* for the specific language governing rights and limitations under the License.
* The Original Code is Facebook Open Platform.
* The Original Developer is the Initial Developer.
* The Initial Developer of the Original Code is Facebook, Inc.  All portions 
* of the code written by Facebook, Inc are 
* Copyright 2006-2008 Facebook, Inc. All Rights Reserved.
*
*
********END LICENSE BLOCK*********/

/**
 *  @requires event-extensions
 *  @provides vector
 */

/**
 *  A two-dimensional (x,y) vector which belongs to some coordinate domain.
 *  This class provides a consistent, reliable mechanism for acquiring,
 *  manipulating, and acting upon position and dimension informations within
 *  a rendered document.
 *
 *  All vectors are fourth-quadrant with an inverted "Y" axis -- that is, (0,0)
 *  is the upper left corner of the relevant coordinate system, and increasing
 *  X and Y values represent points farther toward the right and bottom,
 *  respectively.
 *
 *  Vectors belong to one of three coordinate domains:
 *
 *    pure
 *      A pure vector is a raw numeric vector which does not exist in any
 *      coordinate system. It has some X and Y coordinate, but does not
 *      represent any position on a rendered canvas.
 *
 *    document
 *      A document vector represents a position on a rendered canvas relative
 *      to the upper left corner of the canvas itself -- that is, the entire
 *      renderable area of the canvas, including parts which may not currently
 *      be visible because of the scroll position. The canvas point represented
 *      by a document vector is not affected by scrolling.
 *
 *    viewport
 *      A viewport vector represents a position on the visible area of the
 *      canvas, relative to the upper left corner of the current scroll area.
 *      That is, (0, 0) is the top left visible point, but not necessarily
 *      the top left point in the document (for instance, if the user has
 *      scrolled down the page). Note that vectors in the viewport coordinate
 *      system may legitimately contain negative elements; they represent
 *      points above and/or to the left of the visible area of the document.
 *
 *
 *  When you acquire a position vector, e.g. with Vector2.getEventPosition(),
 *  you MUST provide a coordinate system to represent it in. Methods which act
 *  on vectors MUST first convert them to the expected coordinate system.
 *  Following these rules consistently will prevent code from exhibiting
 *  unexpected behaviors which are a function of the scroll position.
 *
 *  @task canvas Getting Canvas and Event Vectors
 *  @task vector Manipulating Vectors
 *  @task convert Converting Vector Coordinate Domains
 *  @task actions Performing Actions with Vectors
 *  @task internal Internal
 *
 *  @author epriestley
 */

// Used to fix Opera bug 165620, "scrollLeft, scrollTop on inline elements
// return distances from edges of viewport (transmenu)" (fixed in Opera 9.5).
var operaIgnoreScroll = {'table': true, 'inline-table': true, 'inline': true};

function elementX(obj) {

  if (ua.safari() < 500 && obj.tagName == 'TR') {
    obj = obj.firstChild;
  }

  var left = obj.offsetLeft;
  var op = obj.offsetParent;

  while (obj.parentNode && document.body != obj.parentNode) {
    obj = obj.parentNode;
    if (!(ua.opera() < 9.50) || !operaIgnoreScroll[window.getComputedStyle(obj, '').getPropertyValue('display')]) {
      left -= obj.scrollLeft;
    }
    if (op == obj) {
      // Safari 2.0 doesn't support offset* for table rows
      if (ua.safari() < 500 && obj.tagName == 'TR') {
        left += obj.firstChild.offsetLeft;
      } else {
        left += obj.offsetLeft;
      }
      op = obj.offsetParent;
    }
  }
  return left;
}

function elementY(obj) {

  if (ua.safari() < 500 && obj.tagName == 'TR') {
    obj = obj.firstChild;
  }

  var top = obj.offsetTop;
  var op = obj.offsetParent;
  while (obj.parentNode && document.body != obj.parentNode) {
    obj = obj.parentNode;
    if (!isNaN(obj.scrollTop)) {
      if (!(ua.opera() < 9.50) || !operaIgnoreScroll[window.getComputedStyle(obj, '').getPropertyValue('display')]) {
        top -= obj.scrollTop;
      }
    }
    if (op == obj) {
      // Safari 2.0 doesn't support offset* for table rows
      if (ua.safari() < 500 && obj.tagName == 'TR') {
        top += obj.firstChild.offsetTop;
      } else {
        top += obj.offsetTop;
      }
      op = obj.offsetParent;
    }
  }
  return top;
}


/***********escape.js***********/
/******BEGIN LICENSE BLOCK*******
* 
* Common Public Attribution License Version 1.0.
*
* The contents of this file are subject to the Common Public Attribution 
* License Version 1.0 (the "License") you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://developers.facebook.com/fbopen/cpal.html. The License is based 
* on the Mozilla Public License Version 1.1 but Sections 14 and 15 have 
* been added to cover use of software over a computer network and provide 
* for limited attribution for the Original Developer. In addition, Exhibit A 
* has been modified to be consistent with Exhibit B.
* Software distributed under the License is distributed on an "AS IS" basis, 
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License 
* for the specific language governing rights and limitations under the License.
* The Original Code is Facebook Open Platform.
* The Original Developer is the Initial Developer.
* The Initial Developer of the Original Code is Facebook, Inc.  All portions 
* of the code written by Facebook, Inc are 
* Copyright 2006-2008 Facebook, Inc. All Rights Reserved.
*
*
********END LICENSE BLOCK*********/


/**
 *  Escape HTML characters in a string, rendering it safe for display in an
 *  HTML context.
 *
 *  @param string String to escape.
 *  @return string Escaped string.
 *
 *  @author marcel
 */
function htmlspecialchars(text) {

  if (typeof(text) == 'undefined' || !text.toString) {
    return '';
  }

  if (text === false) {
    return '0';
  } else if (text === true) {
    return '1';
  }

  return text
    .toString()
    .replace(/&/g, '&amp;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;');
}

/**************uri.js*************/
/******BEGIN LICENSE BLOCK*******
* 
* Common Public Attribution License Version 1.0.
*
* The contents of this file are subject to the Common Public Attribution 
* License Version 1.0 (the "License") you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://developers.facebook.com/fbopen/cpal.html. The License is based 
* on the Mozilla Public License Version 1.1 but Sections 14 and 15 have 
* been added to cover use of software over a computer network and provide 
* for limited attribution for the Original Developer. In addition, Exhibit A 
* has been modified to be consistent with Exhibit B.
* Software distributed under the License is distributed on an "AS IS" basis, 
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License 
* for the specific language governing rights and limitations under the License.
* The Original Code is Facebook Open Platform.
* The Original Developer is the Initial Developer.
* The Initial Developer of the Original Code is Facebook, Inc.  All portions 
* of the code written by Facebook, Inc are 
* Copyright 2006-2008 Facebook, Inc. All Rights Reserved.
*
*
********END LICENSE BLOCK*********/

copy_properties = function(u, v) {
  for (var k in v) {
    u[k] = v[k];
  }

  //  IE ignores `toString' in object iteration; make sure it gets copied if
  //  it exists. See:
  //    http://webreflection.blogspot.com/2007/07/
  //      quick-fix-internet-explorer-and.html

  //  Avoid a `ua' dependency since we can slip through by just doing
  //  capability detection.
  if (v.hasOwnProperty && v.hasOwnProperty('toString') &&
      (v.toString !== undefined) && (u.toString !== v.toString)) {
    u.toString = v.toString;
  }

  return u;
}

function /* class */ URI(uri) {
  if (uri === window) {
    Util.error('what the hell are you doing');
    return;
  }

  if (this === window) {
    return new URI(uri||window.location.href);
  }

  this.parse(uri||'');
}

copy_properties(URI, {
  /**
   *  Convert a Javascript object into an HTTP query string. This function is
   *  the inverse of explodeQuery().
   *
   *  @param  Object  Map of query keys to values.
   *  @return String  HTTP query string, like 'cow=quack&duck=moo'.
   *
   *  @task   query
   *
   *  @access public
   *  @author marcel
   */
  implodeQuery : function(obj, name) {
    name = name || '';

    var r = [];

    if (obj instanceof Array) {
      for (var ii = 0; ii < obj.length; ii++) {
        try {
          r.push(URI.implodeQuery(obj[ii], name ? name+'['+ii+']' : ii));
        } catch (ignored) {
          //  Don't care.
        }
      }
    } else if (typeof(obj) == 'object') {
      if (is_node(obj)) {
        r.push('{node}');
      } else {
        for (var k in obj) {
          try {
            r.push(URI.implodeQuery(obj[k], name ? name+'['+k+']' : k));
          } catch (ignored) {
            //  Don't care.
          }
        }
      }
    } else if (name && name.length) {
      r.push(encodeURIComponent(name)+'='+encodeURIComponent(obj));
    } else {
      r.push(encodeURIComponent(obj));
    }

    return r.join('&');
  }

}); // End URI Static Methods

function is_node(o, of_type) {

  if (typeof(Node) == 'undefined') {
    Node = null;
  }

  try {
    if (!o || !((Node != undefined && o instanceof Node) || o.nodeName)) {
      return false;
    }
  } catch(ignored) {
    return false;
  }

  if (typeof(of_type) !== "undefined") {

    if (!(of_type instanceof Array)) {
      of_type = [of_type];
    }

    var name;
    try {
      name = new String(o.nodeName).toUpperCase();
    } catch (ignored) {
      return false;
    }

    for (var ii = 0; ii < of_type.length; ii++) {
      try {
        if (name == of_type[ii].toUpperCase()) {
          return true;
        }
      } catch (ignored) {
      }
    }

    return false;
  }

  return true;
}

/****************ua.js******************/
/******BEGIN LICENSE BLOCK*******
* 
* Common Public Attribution License Version 1.0.
*
* The contents of this file are subject to the Common Public Attribution 
* License Version 1.0 (the "License") you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://developers.facebook.com/fbopen/cpal.html. The License is based 
* on the Mozilla Public License Version 1.1 but Sections 14 and 15 have 
* been added to cover use of software over a computer network and provide 
* for limited attribution for the Original Developer. In addition, Exhibit A 
* has been modified to be consistent with Exhibit B.
* Software distributed under the License is distributed on an "AS IS" basis, 
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License 
* for the specific language governing rights and limitations under the License.
* The Original Code is Facebook Open Platform.
* The Original Developer is the Initial Developer.
* The Initial Developer of the Original Code is Facebook, Inc.  All portions 
* of the code written by Facebook, Inc are 
* Copyright 2006-2008 Facebook, Inc. All Rights Reserved.
*
*
********END LICENSE BLOCK*********/

/**
 *  @provides ua
 */

/**
 *  User Agent and OS detection. Usage is straightforward:
 *
 *    if (ua.ie( )) {
 *      //  IE
 *    }
 *
 *  You can also do version checks:
 *
 *    if (ua.ie( ) >= 7) {
 *      //  IE7 or better
 *    }
 *
 *  The browser functions will return NaN if the browser does not match, so
 *  you can also do version compares the other way:
 *
 *    if (ua.ie( ) < 7) {
 *      //  IE6 or worse
 *    }
 *
 *  Note that the version is a float and may include a minor version number,
 *  so you should always use range operators to perform comparisons, not
 *  strict equality.
 *
 *  NOTE: You should also STRONGLY prefer capability detection to browser
 *  version detection where it's reasonable:
 *  http://www.quirksmode.org/js/support.html
 *
 *  Further, we have a large number of mature wrapper functions and classes
 *  which abstract away many browser irregularities. Check the docs or grep
 *  things before writing yet another copy of "event || window.event".
 *
 *  @task browser   Determining the User Agent
 *  @task os        Determining the User's Operating System
 *  @task internal  Internal methods
 *
 *  @author marcel, epriestley
 */
var ua = {

  /**
   *  Check if the UA is Internet Explorer.
   *
   *  @task browser
   *  @access public
   *
   *  @return float|NaN Version number (if match) or NaN.
   *  @author marcel
   */
  ie: function() {
    return this._ie;
  },


  /**
   *  Check if the UA is Firefox.
   *
   *  @task browser
   *  @access public
   *
   *  @return float|NaN Version number (if match) or NaN.
   *  @author marcel
   */
  firefox: function() {
    return this._firefox;
  },


  /**
   *  Check if the UA is Opera.
   *
   *  @task browser
   *  @access public
   *
   *  @return float|NaN Version number (if match) or NaN.
   *  @author marcel
   */
  opera: function() {
    return this._opera;
  },


  /**
   *  Check if the UA is Safari.
   *
   *  @task browser
   *  @access public
   *
   *  @return float|NaN Version number (if match) or NaN.
   *  @author marcel
   */
  safari: function() {
    return this._safari;
  },


  /**
   *  Check if the user is running Windows.
   *
   *  @task os
   *  @return bool `true' if the user's OS is Windows.
   *  @author marcel
   */
  windows: function() {
    return this._windows;
  },


  /**
   *  Check if the user is running Mac OS X.
   *
   *  @task os
   *  @return bool `true' if the user's OS is Mac OS X.
   *  @author marcel
   */
  osx: function() {
    return this._osx;
  },


  /**
   *  Populate the UA and OS information.
   *
   *  @access public
   *  @task internal
   *
   *  @return void
   *
   *  @author marcel
   */
  populate : function() {

    var agent = /(?:MSIE.(\d+\.\d+))|(?:(?:Firefox|GranParadiso|Iceweasel).(\d+\.\d+))|(?:Opera.(\d+\.\d+))|(?:AppleWebKit.(\d+(?:\.\d+)?))/.exec(navigator.userAgent);
    var os    = /(Mac OS X;)|(Windows;)/.exec(navigator.userAgent);

    if (agent) {
      ua._ie      = agent[1] ? parseFloat(agent[1]) : NaN;
      ua._firefox = agent[2] ? parseFloat(agent[2]) : NaN;
      ua._opera   = agent[3] ? parseFloat(agent[3]) : NaN;
      ua._safari  = agent[4] ? parseFloat(agent[4]) : NaN;
    } else {
      ua._ie      =
      ua._firefox =
      ua._opera   =
      ua._safari  = NaN;
    }

    if (os) {
      ua._osx     = !!os[1];
      ua._windows = !!os[2];
    } else {
      ua._osx     =
      ua._windows = false;
    }
  }
};


/************function.js***********/
/**
 *  Returns a function which binds the parameter object and method together.
 *
 *  Bind takes two arguments: an object (optionally, null), and a function
 *  (either the explicit function itself, or the name of a function). It binds
 *  them together and returns a function which, when called, calls the passed
 *  function with the passed object bound as `this'. That is, the following
 *  are nearly equivalent (but see below):
 *
 *    obj.method();
 *
 *    var fn2 = bind(obj, 'method');   // Late binding, see below.
 *    fn2();
 *
 *    var fn3 = bind(obj, obj.method); // Early binding, see below.
 *    fn3();
 *
 *  Binding can occur either by name (as with fn2) or by explicit method (as
 *  with fn3). When binding by name, the binding is "late" and resolved at call
 *  time, NOT at bind time:
 *
 *    function A() { return this.name + ' says "A".'; }
 *    function B() { return this.name + ' says "B".'; }
 *
 *    var obj = { name: 'zebra', f: A };
 *
 *    var earlyBind = bind(obj, f);   // Passing method = early binding
 *    var lateBind  = bind(obj, 'f'); // Passing string = late binding
 *
 *    earlyBind(); // A zebra says "A".
 *    lateBind();  // A zebra says "A".
 *
 *    obj.f = B;
 *
 *    earlyBind(); // A zebra says "A".
 *    lateBind();  // A zebra says "B".
 *
 *  One principle advantage of late binding is that you can late-bind an event
 *  handler, and change it without breaking the bindings.
 *
 *  Note that, because late binding isn't resolved until call time, it can also
 *  fail at call time.
 *
 *    var badLateBind = bind({ f: 42 }, 'f');
 *    badLateBind(); // Fatal error, can't call an integer.
 *
 *  Also note that you can not late bind a global function if you provide an
 *  object. This is a design decision that probably has arguments both ways,
 *  but forcing object bindings to always bind within object scope means global
 *  scope can't accidentally bleed into an object, which could be extremely
 *  astonishing.
 *
 *  Additionally, bind() can curry (purists might argue that this is actually
 *  "partial function application", but they can die in a well fire). Currying
 *  binds arguments to the return function:
 *
 *    function add(a, b) { return a + b; }
 *    var add3 = bind(null, add, 3);
 *    add3(4);                  // 7
 *    add3(5);                  // 8
 *    bind(null, add, 2, 3)();  // 5
 *
 *  bind() is also available as a member of Function:
 *
 *    var fn = function() { }.bind(obj);
 *
 *  This version of bind() can also curry, but it is impossible to perform late
 *  binding this way. For this reason, you may prefer to use the functional
 *  form of bind(), but you should prefer early binding (which catches errors
 *  sooner) to late binding (which may miss them) unless you actually need late
 *  binding (e.g., for event handlers).
 *
 *  bind() can be difficult to understand, particularly if you are not familiar
 *  with functional programming. However, it is worth understanding because it
 *  is awesomely powerful. bind() is the solution to every piece of code which
 *  looks like this:
 *
 *    // Everyone does this at first, but it's bad! Don't do it!
 *    var localCopyOfThis = this;
 *    this.onclick = function(event) {
 *      localCopyOfThis.doAction(event);
 *    }
 *
 *  Clearly, this is hacky, but it's not obvious how to do this better. The
 *  solution is:
 *
 *    this.onclick = this.doAction.bind(this);
 *
 *  @param obj|null An object to bind.
 *  @param function|string A function or method to bind, early or late.
 *  @param any... Zero or more arguments to curry.
 *
 *  @return function A function which, when called, calls the method with object
 *                   and arguments bound.
 *
 *  @author epriestley
 */
function bind(obj,method){
  var args=[];
  for(var ii=2;ii<arguments.length;ii++){
    args.push(arguments[ii]);
  }

  var fn=function(){ 
    var _obj = obj || (this == window ? false : this);
    var _args = args.slice();
    for(var jj = 0; jj < arguments.length; jj++){
      _args.push(arguments[jj]);
    }

    if(typeof(method) == "string"){
      if(_obj[method]){
        return _obj[method].apply(_obj,_args);
      }
    }
    else{
      return method.apply(_obj,_args);
    }
  };

  if(typeof method=='string'){
    fn.name=method;
  }
  else if(method){
    if(method.name){
      fn.name=method.name;
    }

    fn.toString = function(){
      return'bound<' + method.toString() + '>';
    }
  }

  return fn;
}

/**
 *  Fancy new version of Function.bind which can curry. See bind() for a
 *  slightly more comprehensive description.
 *
 *  @author epriestley
 */
Function.prototype.bind = function(context /*, arg, arg, arg*/) {
  var argv = [ arguments[0], this ];
  var argc = arguments.length;
  for (var ii = 1; ii < argc; ii++) {
    argv.push(arguments[ii]);
  }

  return bind.apply( null, argv );
}


/*************animation.js*************/
function animation(obj){if(obj==undefined){Util.error("Creating animation on non-existant object");return;}
if(this==window){return new animation(obj);}else{this.obj=obj;this._reset_state();this.queue=[];this.last_attr=null;}}
animation.resolution=20;animation.offset=0;animation.prototype._reset_state=function(){this.state={attrs:{},duration:500}}
animation.prototype.stop=function(){this._reset_state();this.queue=[];return this;}
animation.prototype._build_container=function(){if(this.container_div){this._refresh_container();return;}
if(this.obj.firstChild&&this.obj.firstChild.__animation_refs){this.container_div=this.obj.firstChild;this.container_div.__animation_refs++;this._refresh_container();return;}
var container=document.createElement('div');container.style.padding='0px';container.style.margin='0px';container.style.border='0px';container.__animation_refs=1;var children=this.obj.childNodes;while(children.length){container.appendChild(children[0]);}
this.obj.appendChild(container);this.obj.style.overflow='hidden';this.container_div=container;this._refresh_container();}
animation.prototype._refresh_container=function(){this.container_div.style.height='auto';this.container_div.style.width='auto';this.container_div.style.height=this.container_div.offsetHeight+'px';this.container_div.style.width=this.container_div.offsetWidth+'px';}
animation.prototype._destroy_container=function(){if(!this.container_div){return;}
if(!--this.container_div.__animation_refs){var children=this.container_div.childNodes;while(children.length){this.obj.appendChild(children[0]);}
this.obj.removeChild(this.container_div);}
this.container_div=null;}
animation.ATTR_TO=1;animation.ATTR_BY=2;animation.ATTR_FROM=3;animation.prototype._attr=function(attr,value,mode){attr=attr.replace(/-[a-z]/gi,function(l){return l.substring(1).toUpperCase();});var auto=false;switch(attr){case'background':this._attr('backgroundColor',value,mode);return this;case'margin':value=animation.parse_group(value);this._attr('marginBottom',value[0],mode);this._attr('marginLeft',value[1],mode);this._attr('marginRight',value[2],mode);this._attr('marginTop',value[3],mode);return this;case'padding':value=animation.parse_group(value);this._attr('paddingBottom',value[0],mode);this._attr('paddingLeft',value[1],mode);this._attr('paddingRight',value[2],mode);this._attr('paddingTop',value[3],mode);return this;case'backgroundColor':case'borderColor':case'color':value=animation.parse_color(value);break;case'opacity':value=parseFloat(value,10);break;case'height':case'width':if(value=='auto'){auto=true;}else{value=parseInt(value,10);}
break;case'borderWidth':case'lineHeight':case'fontSize':case'marginBottom':case'marginLeft':case'marginRight':case'marginTop':case'paddingBottom':case'paddingLeft':case'paddingRight':case'paddingTop':case'bottom':case'left':case'right':case'top':case'scrollTop':case'scrollLeft':value=parseInt(value,10);break;default:throw new Error(attr+' is not a supported attribute!');}
if(this.state.attrs[attr]===undefined){this.state.attrs[attr]={};}
if(auto){this.state.attrs[attr].auto=true;}
switch(mode){case animation.ATTR_FROM:this.state.attrs[attr].start=value;break;case animation.ATTR_BY:this.state.attrs[attr].by=true;case animation.ATTR_TO:this.state.attrs[attr].value=value;break;}}
animation.prototype.to=function(attr,value){if(value===undefined){this._attr(this.last_attr,attr,animation.ATTR_TO);}else{this._attr(attr,value,animation.ATTR_TO);this.last_attr=attr;}
return this;}
animation.prototype.by=function(attr,value){if(value===undefined){this._attr(this.last_attr,attr,animation.ATTR_BY);}else{this._attr(attr,value,animation.ATTR_BY);this.last_attr=attr;}
return this;}
animation.prototype.from=function(attr,value){if(value===undefined){this._attr(this.last_attr,attr,animation.ATTR_FROM);}else{this._attr(attr,value,animation.ATTR_FROM);this.last_attr=attr;}
return this;}
animation.prototype.duration=function(duration){this.state.duration=duration?duration:0;return this;}
animation.prototype.checkpoint=function(distance,callback){if(distance===undefined){distance=1;}
this.state.checkpoint=distance;this.queue.push(this.state);this._reset_state();this.state.checkpointcb=callback;return this;}
animation.prototype.blind=function(){this.state.blind=true;return this;}
animation.prototype.hide=function(){this.state.hide=true;return this;}
animation.prototype.show=function(){this.state.show=true;return this;}
animation.prototype.ease=function(ease){this.state.ease=ease;return this;}
animation.prototype.go=function(){var time=(new Date()).getTime();this.queue.push(this.state);for(var i=0;i<this.queue.length;i++){this.queue[i].start=time-animation.offset;if(this.queue[i].checkpoint){time+=this.queue[i].checkpoint*this.queue[i].duration;}}
animation.push(this);return this;}
animation.prototype._frame=function(time){var done=true;var still_needs_container=false;var whacky_firefox=false;for(var i=0;i<this.queue.length;i++){var cur=this.queue[i];if(cur.start>time){done=false;continue;}
if(cur.checkpointcb){this._callback(cur.checkpointcb,time-cur.start);cur.checkpointcb=null;}
if(cur.started===undefined){if(cur.show){this.obj.style.display='block';}
for(var a in cur.attrs){if(cur.attrs[a].start!==undefined){continue;}
switch(a){case'backgroundColor':case'borderColor':case'color':var val=animation.parse_color(CSS.getStyle(this.obj,a=='borderColor'?'borderLeftColor':a));if(cur.attrs[a].by){cur.attrs[a].value[0]=Math.min(255,Math.max(0,cur.attrs[a].value[0]+val[0]));cur.attrs[a].value[1]=Math.min(255,Math.max(0,cur.attrs[a].value[1]+val[1]));cur.attrs[a].value[2]=Math.min(255,Math.max(0,cur.attrs[a].value[2]+val[2]));}
break;case'opacity':var val=CSS.getOpacity(this.obj);if(cur.attrs[a].by){cur.attrs[a].value=Math.min(1,Math.max(0,cur.attrs[a].value+val));}
break;case'height':var val=DOM.getBoxHeight(this.obj);if(cur.attrs[a].by){cur.attrs[a].value+=val;}
break;case'width':var val=DOM.getBoxWidth(this.obj);if(cur.attrs[a].by){cur.attrs[a].value+=val;}
break;case'scrollLeft':case'scrollTop':var val=(this.obj==document.body)?(document.documentElement[a]||document.body[a]):this.obj[a];if(cur.attrs[a].by){cur.attrs[a].value+=val;}
cur['last'+a]=val;break;default:var val=parseInt(CSS.getStyle(this.obj,a),10);if(cur.attrs[a].by){cur.attrs[a].value+=val;}
break;}
cur.attrs[a].start=val;}
if((cur.attrs.height&&cur.attrs.height.auto)||(cur.attrs.width&&cur.attrs.width.auto)){if(ua.firefox()<3){whacky_firefox=true;}
this._destroy_container();for(var a in{height:1,width:1,fontSize:1,borderLeftWidth:1,borderRightWidth:1,borderTopWidth:1,borderBottomWidth:1,paddingLeft:1,paddingRight:1,paddingTop:1,paddingBottom:1}){if(cur.attrs[a]){this.obj.style[a]=cur.attrs[a].value+(typeof cur.attrs[a].value=='number'?'px':'');}}
if(cur.attrs.height&&cur.attrs.height.auto){cur.attrs.height.value=DOM.getBoxHeight(this.obj);}
if(cur.attrs.width&&cur.attrs.width.auto){cur.attrs.width.value=DOM.getBoxWidth(this.obj);}}
cur.started=true;if(cur.blind){this._build_container();}}
var p=(time-cur.start)/cur.duration;if(p>=1){p=1;if(cur.hide){this.obj.style.display='none';}}else{done=false;}
var pc=cur.ease?cur.ease(p):p;if(!still_needs_container&&p!=1&&cur.blind){still_needs_container=true;}
if(whacky_firefox&&this.obj.parentNode){var parentNode=this.obj.parentNode;var nextChild=this.obj.nextSibling;parentNode.removeChild(this.obj);}
for(var a in cur.attrs){switch(a){case'backgroundColor':case'borderColor':case'color':this.obj.style[a]='rgb('+
animation.calc_tween(pc,cur.attrs[a].start[0],cur.attrs[a].value[0],true)+','+
animation.calc_tween(pc,cur.attrs[a].start[1],cur.attrs[a].value[1],true)+','+
animation.calc_tween(pc,cur.attrs[a].start[2],cur.attrs[a].value[2],true)+')';break;case'opacity':CSS.setOpacity(this.obj,animation.calc_tween(pc,cur.attrs[a].start,cur.attrs[a].value));break;case'height':case'width':this.obj.style[a]=pc==1&&cur.attrs[a].auto?'auto':animation.calc_tween(pc,cur.attrs[a].start,cur.attrs[a].value,true)+'px';break;case'scrollLeft':case'scrollTop':var val=(this.obj==document.body)?(document.documentElement[a]||document.body[a]):this.obj[a];if(cur['last'+a]!=val){delete cur.attrs[a];}else{var diff=animation.calc_tween(pc,cur.attrs[a].start,cur.attrs[a].value,true)-val;if(DOMScroll.usingScrollWrapper()){this.obj[a]=diff+val;}else{if(a=='scrollLeft'){window.scrollBy(diff,0);}else{window.scrollBy(0,diff);}}
cur['last'+a]=diff+val;}
break;default:this.obj.style[a]=animation.calc_tween(pc,cur.attrs[a].start,cur.attrs[a].value,true)+'px';break;}}
if(p==1){this.queue.splice(i--,1);this._callback(cur.ondone,time-cur.start-cur.duration);}}
if(whacky_firefox){parentNode[nextChild?'insertBefore':'appendChild'](this.obj,nextChild);}
if(!still_needs_container&&this.container_div){this._destroy_container();}
return!done;}
animation.prototype.ondone=function(fn){this.state.ondone=fn;return this;}
animation.prototype._callback=function(callback,offset){if(callback){animation.offset=offset;/*workaround!!*/setTimeout(callback,0);/*callback.call(this);*/animation.offset=0;}}
animation.calc_tween=function(p,v1,v2,whole){return(whole?parseInt:parseFloat)((v2-v1)*p+v1,10);}
animation.parse_color=function(color){var hex=/^#([a-f0-9]{1,2})([a-f0-9]{1,2})([a-f0-9]{1,2})$/i.exec(color);if(hex){return[parseInt(hex[1].length==1?hex[1]+hex[1]:hex[1],16),parseInt(hex[2].length==1?hex[2]+hex[2]:hex[2],16),parseInt(hex[3].length==1?hex[3]+hex[3]:hex[3],16)];}else{var rgb=/^rgba? *\(([0-9]+), *([0-9]+), *([0-9]+)(?:, *([0-9]+))?\)$/.exec(color);if(rgb){if(rgb[4]==='0'){return[255,255,255];}else{return[parseInt(rgb[1],10),parseInt(rgb[2],10),parseInt(rgb[3],10)];}}else if(color=='transparent'){return[255,255,255];}else{throw'Named color attributes are not supported.';}}}
animation.parse_group=function(value){var value=trim(value).split(/ +/);if(value.length==4){return value;}else if(value.length==3){return[value[0],value[1],value[2],value[1]];}else if(value.length==2){return[value[0],value[1],value[0],value[1]];}else{return[value[0],value[0],value[0],value[0]];}}
animation.push=function(instance){if(!animation.active){animation.active=[];}
animation.active.push(instance);if(!animation.timeout){animation.timeout=setInterval(animation.animate.bind(animation),animation.resolution,false);}
animation.animate(true);}
animation.animate=function(last){var time=(new Date()).getTime();for(var i=last===true?animation.active.length-1:0;i<animation.active.length;i++){try{if(!animation.active[i]._frame(time)){animation.active.splice(i--,1);}}catch(e){animation.active.splice(i--,1);}}
if(animation.active.length==0){clearInterval(animation.timeout);animation.timeout=null;}}
animation.ease={}
animation.ease.begin=function(p){return p*p;}
animation.ease.end=function(p){p-=1;return-(p*p)+1;}
animation.ease.both=function(p){if(p<=0.5){return(p*p)*2;}else{p-=1;return(p*p)*-2+1;}}

