null
//
//XNJS Platform: app entries for XN platform
//Note: Don't modify this file unless you make sure you really understand what you are doing!
//  A minor fix may end up being a huge security hole.
//Another Note: Appropriate (not all) properties and/or methods of some objects are prefixed
//  by "xn_". You'd better follow this rule with any update of this file. If so, strong security
//  may be guaranteed with the help of the platform server.
//
function XNJS_Platform(appid) {
  if (XNJS_Platform.xn_apps[appid]) {
    return XNJS_Platform.xn_apps[appid];
  }

  //Privatize the appid property
  this.xn_get_appid = function() {return appid;};

  this.xn_bootstrapped = false;
  XNJS_Platform.xn_apps[appid] = this;
}

//An array-like object
XNJS_Platform.xn_apps = {};

//Get the app instance according to appid
XNJS_Platform.xn_get_app = function(appid){
  var app = XNJS_Platform.xn_apps[appid];
  if (app && app.xn_get_appid() == appid) {
    return app;
  }
  else {
    throw 'XNJS: Got invalid app ID for XNJS_Platform.xn_get_app! Platform may be corrupted...';
  }
}


//Platform bootstrap
XNJS_Platform.prototype.xn_bootstrap = function() {

  //Generate objects or classes used by apps
  if (!this.xn_bootstrapped) {
    var appid = this.xn_get_appid();
    var code = [
          'a', appid, '_Math = new XNJS_Math();',
          'a', appid, '_Date = XNJS_Date();',
          'a', appid, '_String = new XNJS_String();',
          'a', appid, '_RegExp = XNJS_RegExp();',
          'a', appid, '_Ajax = XNJS_getAjax(', appid, ');',
          'a', appid, '_Dialog = XNJS_getDialog(', appid, ');',
          'a', appid, '_Button = XNJS_getButton(', appid, ');',
          'a', appid, '_Session = new XNJS_Session(', appid, ');',
          'a', appid, '_Animation = XNJS_getAnimation(', appid, ');',
          'a', appid, '_document = new XNJS_Document(', appid, ');',
          'a', appid, '_undefined = undefined;',
          'a', appid, '_console = new XNJS_Console(', appid, ');',
          'a', appid, '_setTimeout = XNJS_Main.xn_setTimeout;',
          'a', appid, '_setInterval = XNJS_Main.xn_setInterval;',
          'a', appid, '_encodeURIComponent = XNJS_Main.xn_escapeURIComponent;',
          'a', appid, '_decodeURIComponent = XNJS_Main.xn_unescapeURIComponent;'
          ];
    for (var i in {clearTimeout: 1,
                   clearInterval: 1,
                   parseFloat: 1,
                   parseInt: 1,
                   isNaN: 1,
                   isFinite: 1}) {
      code = code.concat(['a', appid, '_', i, '=', i, ';']);
    }
    eval(code.join(''));

    this.xn_bootstrapped = true;
  }
}

//
// XNJS Main: define some global functions
//
function XNJS_Main() {
}

XNJS_Main.xn_setTimeout = function(js, timeout) {
  if (typeof js != 'function') {
    XNJS_Console_Impl.xn_error('XNJS: setTimeout may not be used with a string of JavaScript code. Please enclose your code in an function (may be anonymous).');
  } else {
    return setTimeout(js, timeout);
  }
}

XNJS_Main.xn_setInterval = function(js, timeout) {
  if (typeof js != 'function') {
    XNJS_Console_Impl.xn_error('XNJS: setTimeout may not be used with a string of JavaScript code. Please enclose your code in an function (may be anonymous).');
  } else {
    return setInterval(js, timeout);
  }
}

XNJS_Main.xn_escapeURIComponent = function(u){ 
  if(encodeURIComponent) {
    return encodeURIComponent(u);
  }
  if(escape) {
    return escape(u);
  }
  return null;
}

XNJS_Main.xn_unescapeURIComponent = function(u){ 
  if(decodeURIComponent) {
    return decodeURIComponent(u);
  }
  if(unescape) {
    return unescape(u);
  }
  return null;
}


//
//XNJS Document: base class for the mock document object
//
function XNJS_Document(appid) {
  if (!XNJS_Document.xn_proto_populated) {
    for (var i in XNJS_Document_Impl.prototype) {
      if (i != 'constructor') { //just for safeguard
        XNJS_Document.prototype[i] = XNJS_Document_Impl.prototype[i];
      }
    }
    XNJS_Document.xn_proto_populated = true;
  }

  var intermediate = {xn_data: {xn_appid:appid}, xn_instance: this};;
  var priv = XNJS_Document_Impl.xn_len;
  XNJS_Document_Impl.xn_insts[priv] = intermediate;
  XNJS_Document_Impl.xn_len++;

  //Privatize the priv property
  this.xn_get_priv = function() {return priv;}
}
XNJS_Document.xn_proto_populated = false;


//
//XNJS Document_Impl: implementation class for XNJS_Document
//
function XNJS_Document_Impl() {
}

//Array-like object
XNJS_Document_Impl.xn_insts = {};
XNJS_Document_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Document_Impl.xn_getdata = function(instance){
  if (instance instanceof XNJS_Document) {
    var intermediate = XNJS_Document_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate.xn_data;
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Document_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Document_Impl.xn_getdata...';
  }
}



XNJS_Document_Impl.prototype.getElementById = function(id) {
  var appid = XNJS_Document_Impl.xn_getdata(this).xn_appid;
  return XNJS_Dom_Impl.xn_get_instance(document.getElementById('app'+appid+'_'+id), appid);
}

XNJS_Document_Impl.prototype.getRootElement = function() {
  var appid = XNJS_Document_Impl.xn_getdata(this).xn_appid;
  //Double <div> are needed for root element
  return XNJS_Dom_Impl.xn_get_instance(document.getElementById('app_content_'+appid).firstChild, appid);
}

XNJS_Document_Impl.prototype.createElement = function(element) {
  var mix = XNJS_Safe.xn_safe_string(element).toLowerCase();
  if (XNJS_Safe.xn_allowed_elements[mix]) {
    return XNJS_Dom_Impl.xn_get_instance(document.createElement(mix), XNJS_Document_Impl.xn_getdata(this).xn_appid);
  } else {
    switch (mix) {
      case 'xn:swf':
        return new XNJS_Xnml_Dom('xn:swf', XNJS_Document_Impl.xn_getdata(this).xn_appid);
        break;

      default:
        throw 'XNJS: ' + mix + ' is not an allowed XNJS DOM element to be created';
        break;
    }
  }
}

XNJS_Document_Impl.prototype.setLocation = function(url) {
  url = XNJS_Safe.xn_safe_string(url);
  if (XNJS_Safe.xn_url_regex.test(url)) {
    document.location.href = url;
    return XNJS_Safe.xn_ref(this);
  } else {
    XNJS_Console_Impl.xn_error('XNJS: ' + url + ' is not a valid location!');
  }
}


//
//XNJS DOM: base class for the mock xnjs object
//
function XNJS_Dom(obj, appid) {
  if (!XNJS_Dom.xn_proto_populated) {
    for (var i in XNJS_Dom_Impl.prototype) {
      if (i != 'constructor') { //just for safeguard
        XNJS_Dom.prototype[i] = XNJS_Dom_Impl.prototype[i];
      }
    }
    XNJS_Dom.xn_proto_populated = true;
  }

  var intermediate = {xn_instance: this, xn_obj: obj, xn_events: {}, xn_appid: appid};;
  var obj_num = XNJS_Dom_Impl.xn_len;
  XNJS_Dom_Impl.xn_insts[obj_num] = intermediate;
  XNJS_Dom_Impl.xn_len++;

  obj.xn_instance = obj_num;

  //Privatize the priv property
  this.xn_get_obj = function() {return obj_num;}
}
XNJS_Dom.xn_proto_populated = false;

//
//XNJS Dom_Impl: implementation class for XNJS_Dom
//
function XNJS_Dom_Impl() {
}

//Array-like object
XNJS_Dom_Impl.xn_insts = {};
XNJS_Dom_Impl.xn_len = 0;

XNJS_Dom_Impl.xn_factory = function(obj, appid) {
  if (obj.id && obj.id.indexOf('app_content_') != -1) { //root element
    return new XNJS_Dom(obj, appid);
  }
  else if (!obj.tagName || ((!XNJS_Safe.xn_allowed_elements[obj.tagName.toLowerCase()] && !XNJS_Safe.xn_allowed_editable[obj.tagName.toLowerCase()]) ||
                       has_css_class_name(obj, '__xnml_tag') ||
                       (obj.tagName == 'INPUT' && (obj.name.substring(0, 2) == 'xn' || obj.name == 'post_form_id')) ||
                       obj.getAttribute('xn_protected') == 'true')) {
    return null;
  } else {
    return new XNJS_Dom(obj, appid);
  }
}


//Get XNJS DOM instance from object
XNJS_Dom_Impl.xn_get_instance = function(obj, appid) {
  if (!obj) {
    return null;
  }
  if (typeof obj.xn_instance == 'undefined') {
    return XNJS_Dom_Impl.xn_factory(obj, appid);
  } else {
    return XNJS_Dom_Impl.xn_insts[obj.xn_instance].xn_instance;
  }
}

XNJS_Dom_Impl.xn_get_instance_list = function(objlist, appid) {
  var objs = [];
  for (var i = 0; i < objlist.length; i++) {
    var obj = XNJS_Dom_Impl.xn_get_instance(objlist[i], appid);
    if (obj) {
      objs.push(obj);
    }
  }
  return objs;
}


//Get the object from XNJS DOM instance
XNJS_Dom_Impl.xn_get_obj = function(instance){
  if (instance instanceof XNJS_Xnml_Dom) {
    return XNJS_Xnml_Dom_Impl.xn_get_obj(instance);
  }
  else if (instance instanceof XNJS_Dom) {
    var intermediate = XNJS_Dom_Impl.xn_insts[instance.xn_get_obj()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate.xn_obj;
    }
    else {
      throw 'XNJS: Got invalid object for XNJS_Dom_Impl.xn_get_obj! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object for XNJS_Dom_Impl.xn_get_obj...';
  }
}


//Get the intermediate object from XNJS DOM instance
XNJS_Dom_Impl.xn_get_interobj = function(instance){
  if (instance instanceof XNJS_Dom) {
    var intermediate = XNJS_Dom_Impl.xn_insts[instance.xn_get_obj()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate;
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Dom_Impl.xn_get_interobj! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Dom_Impl.xn_get_interobj...';
  }
}


XNJS_Dom_Impl.xn_get_first_valid_instance = function(obj, next, appid) {
  var ret = null;

  while (obj && (!obj.id || (obj.id && obj.id.indexOf('app_content_') == -1))) {
    if (obj.tagName && obj.tagName.toLowerCase() == 'body') {
      ret = null;
	  break;
    }
    if (ret = XNJS_Dom_Impl.xn_get_instance(obj, appid)) {
      break;
    }
    obj = obj[next];
  }

  return ret;
}

// Only XNML Dom (such as <xn:swf>) needs explicit rendering
XNJS_Dom_Impl.xn_render = function(handle) {
  if (handle instanceof XNJS_Xnml_Dom) {
    XNJS_Xnml_Dom_Impl.xn_render(handle);
  }
}

XNJS_Dom_Impl.xn_clear_instances = function(obj, include) {
  if (include && obj.xn_instance) {
    var intermediate = XNJS_Dom_Impl.xn_insts[obj.xn_instance];
    if (intermediate && obj == intermediate.xn_obj) {
      delete intermediate.xn_obj;
      delete intermediate.xn_events;
      delete intermediate.xn_instance;
      delete intermediate;
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: Got invalid object reference for XNJS_Dom_Impl.xn_clear_instances! Platform may be corrupted...');
    }
    obj.xn_instance = undefined;
  }

  var cn = obj.childNodes;
  for (var i = 0; i < cn.length; i++) {
    XNJS_Dom_Impl.xn_clear_instances(cn[i], true);
  }
}

//Class method to set CSS style.
//Multiple styles can be embedded in an Object (style object) and be set to
//the 'obj' Object with one function call, e.g.,
//XNJS_Dom_Impl.xn_set_style(obj, {'width': '18px', 'color': 'rgb(255,255,255)'})
//In this case, the argument 'value' is not needed.
XNJS_Dom_Impl.xn_set_style = function(obj, style, value) {
  if (typeof style == 'string') {
    if (style == 'opacity') {
      set_opacity(obj, parseFloat(value)); //value is a safe number
    } else {
      value = XNJS_Safe.xn_safe_string(value);
      if (XNJS_Safe.xn_css_regex.test(value)) {
        obj.style[style] = value;
      } else {
        XNJS_Console_Impl.xn_error('XNJS: ' + style + ': ' + value + ' is not a valid CSS style');
      }
    }
  } else { //style embedded in an Object
    for (var i in style) {
      XNJS_Dom_Impl.xn_set_style(obj, i, style[i]);
    }
  }
}


//
//XNJS setters start ----------------------
//

XNJS_Dom_Impl.prototype.appendChild = function(child) {
  XNJS_Dom_Impl.xn_get_obj(this).appendChild(XNJS_Dom_Impl.xn_get_obj(child));
  XNJS_Dom_Impl.xn_render(child);
  return child;
}

XNJS_Dom_Impl.prototype.insertBefore = function(newChild, refChild) {
  if (refChild) {
    XNJS_Dom_Impl.xn_get_obj(this).insertBefore(XNJS_Dom_Impl.xn_get_obj(newChild), XNJS_Dom_Impl.xn_get_obj(refChild));
  } else {
    XNJS_Dom_Impl.xn_get_obj(this).appendChild(XNJS_Dom_Impl.xn_get_obj(newChild));
  }
  XNJS_Dom_Impl.xn_render(newChild);
  return newChild;
}

XNJS_Dom_Impl.prototype.removeChild = function(child) {
  XNJS_Dom_Impl.xn_get_obj(this).removeChild(XNJS_Dom_Impl.xn_get_obj(child));
  return child;
}

XNJS_Dom_Impl.prototype.replaceChild = function(newchild, oldchild) {
  XNJS_Dom_Impl.xn_get_obj(this).replaceChild(XNJS_Dom_Impl.xn_get_obj(newchild), XNJS_Dom_Impl.xn_get_obj(oldchild));
  return oldchild;
}

//If the argument deep is true, cloneNode() recursively clones all descendants
//of this node. Otherwise, it clones only this node.
XNJS_Dom_Impl.prototype.cloneNode = function(deep) {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    var clone_obj = intermediate.xn_obj.cloneNode(deep);
    //Note: In IE, the xn_instance property is also cloned by cloneNode(), so here invalidate it
    clone_obj.xn_instance = undefined;
    return XNJS_Dom_Impl.xn_get_instance(clone_obj, intermediate.xn_appid);
  }
  return null;
}

XNJS_Dom_Impl.prototype.setTextValue = function(text) {
  var obj = XNJS_Dom_Impl.xn_get_obj(this);
  XNJS_Dom_Impl.xn_clear_instances(obj, false);
  obj.innerHTML = htmlspecialchars(XNJS_Safe.xn_safe_string(text));
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setInnerXNML = function(xnml_string) {
  if (xnml_string instanceof XNJS_Xnml_String) {
    var html = XNJS_Xnml_String_Impl.xn_getstring(xnml_string);
  }
  else {
    throw 'XNJS: The argument of setInnerXNML must be created by the <xn:js-string> tag!';
  }
  var obj = XNJS_Dom_Impl.xn_get_obj(this);
  switch (obj.tagName) {
    case 'TEXTAREA':
      XNJS_Console_Impl.xn_error('XNJS: setInnerXNML is not supported on textareas. Please use .setValue instead.');
      break;

    // http://msdn2.microsoft.com/en-us/library/ms533897.aspx
    case 'COL':
    case 'COLGROUP':
    case 'TABLE':
    case 'TBODY':
    case 'TFOOT':
    case 'THEAD':
    case 'TR':
      XNJS_Console_Impl.xn_error('XNJS: setInnerXNML is not supported on this node.');
      break;

    default:
      XNJS_Dom_Impl.xn_clear_instances(obj, false);
      obj.innerHTML = html;
      break;
  }
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setAction = function(a) {
  a = XNJS_Safe.xn_safe_string(a);
  if (XNJS_Safe.xn_url_regex.test(a)) {
    XNJS_Dom_Impl.xn_get_obj(this).action = a;
    return XNJS_Safe.xn_ref(this);
  } else {
    XNJS_Console_Impl.xn_error('XNJS: ' + a + ' is not a valid hyperlink, when serAction');
  }
}

XNJS_Dom_Impl.prototype.setValue = function(value) {
  XNJS_Dom_Impl.xn_get_obj(this).value = XNJS_Safe.xn_safe_string(value);
  return XNJS_Safe.xn_ref(this);
}

//Set the display text (not value) of <option>
XNJS_Dom_Impl.prototype.setOptionText = function(value) {
  XNJS_Dom_Impl.xn_get_obj(this).text = XNJS_Safe.xn_safe_string(value);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setHref = function(href) {
  href = XNJS_Safe.xn_safe_string(href);
  if (XNJS_Safe.xn_url_regex.test(href)) {
    XNJS_Dom_Impl.xn_get_obj(this).href = href;
    return XNJS_Safe.xn_ref(this);
  } else {
    XNJS_Console_Impl.xn_error('XNJS: ' + href + ' is not a valid hyperlink, when setHref');
  }
}

XNJS_Dom_Impl.prototype.setTarget = function(target) {
  XNJS_Dom_Impl.xn_get_obj(this).target = XNJS_Safe.xn_safe_string(target);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setSrc = function(src) {
  src = XNJS_Safe.xn_safe_string(src);
  if (XNJS_Safe.xn_url_regex.test(src)) {
    XNJS_Dom_Impl.xn_get_obj(this).src = src;
    return XNJS_Safe.xn_ref(this);
  } else {
    XNJS_Console_Impl.xn_error('XNJS: ' + src + ' is not a valid hyperlink, when setSrc');
  }
}

XNJS_Dom_Impl.prototype.setClassName = function(classname) {
  XNJS_Dom_Impl.xn_get_obj(this).className = XNJS_Safe.xn_safe_string(classname);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setId = function(id) {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    if (intermediate.xn_obj.id.indexOf('app_content_') != -1) {
      XNJS_Console_Impl.xn_error('XNJS: The ID of the root element is not allowed to be modified!');
    }
    else {
      intermediate.xn_obj.id = ['app', intermediate.xn_appid, '_', XNJS_Safe.xn_safe_string(id)].join('');
    }
	return XNJS_Safe.xn_ref(this);
  }
  else {
      XNJS_Console_Impl.xn_error('XNJS: Got invalid object reference for setId! Platform may be corrupted...');
  }
}

XNJS_Dom_Impl.prototype.setDir = function(dir) {
  XNJS_Dom_Impl.xn_get_obj(this).dir = XNJS_Safe.xn_safe_string(dir);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setChecked = function(checked) {
  XNJS_Dom_Impl.xn_get_obj(this).checked = checked;
  return XNJS_Safe.xn_ref(this);
}

//Refer to getScrollLeft() for details
XNJS_Dom_Impl.prototype.setScrollLeft = function(val) {
  XNJS_Dom_Impl.xn_get_obj(this).scrollLeft = val;
  return XNJS_Safe.xn_ref(this);
}

//Refer to getScrollTop() for details
XNJS_Dom_Impl.prototype.setScrollTop = function(val) {
  XNJS_Dom_Impl.xn_get_obj(this).scrollTop = val;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setTabIndex = function(tabindex) {
  XNJS_Dom_Impl.xn_get_obj(this).tabIndex = tabindex;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setTitle = function(title) {
  XNJS_Dom_Impl.xn_get_obj(this).title = XNJS_Safe.xn_safe_string(title);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setName = function(name) {
  XNJS_Dom_Impl.xn_get_obj(this).name = XNJS_Safe.xn_safe_string(name);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setCols = function(cols) {
  XNJS_Dom_Impl.xn_get_obj(this).cols = cols;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setRows = function(rows) {
  XNJS_Dom_Impl.xn_get_obj(this).rows = rows;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setAccessKey = function(accesskey) {
  XNJS_Dom_Impl.xn_get_obj(this).accessKey = XNJS_Safe.xn_safe_string(accesskey);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setDisabled = function(disabled) {
  XNJS_Dom_Impl.xn_get_obj(this).disabled = disabled;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setReadOnly = function(readonly) {
  XNJS_Dom_Impl.xn_get_obj(this).readOnly = readonly;
  return XNJS_Safe.xn_ref(this);
}

//Note: seems not support by IE
XNJS_Dom_Impl.prototype.setType = function(type) {
  XNJS_Dom_Impl.xn_get_obj(this).type = XNJS_Safe.xn_safe_string(type);
  return XNJS_Safe.xn_ref(this);
}

//Note: set si=-1 to deselect all the options
XNJS_Dom_Impl.prototype.setSelectedIndex = function(si) {
  XNJS_Dom_Impl.xn_get_obj(this).selectedIndex = si;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setSelected = function(selected) {
  XNJS_Dom_Impl.xn_get_obj(this).selected = selected;
  return XNJS_Safe.xn_ref(this);
}

//Refer to XNJS_Dom_Impl.xn_set_style for details
XNJS_Dom_Impl.prototype.setStyle = function(style, value) {
  XNJS_Dom_Impl.xn_set_style(XNJS_Dom_Impl.xn_get_obj(this), style, value);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setMethod = function(m) {
  m = XNJS_Safe.xn_safe_string(m);
  XNJS_Dom_Impl.xn_get_obj(this).method = m.toLowerCase() == 'get' ? 'get' : 'post';
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.addClassName = function(classname) {
  add_css_class_name(XNJS_Dom_Impl.xn_get_obj(this), XNJS_Safe.xn_safe_string(classname));
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.removeClassName = function(classname) {
  remove_css_class_name(XNJS_Dom_Impl.xn_get_obj(this), XNJS_Safe.xn_safe_string(classname));
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.toggleClassName = function(classname) {
  this.hasClassName(classname) ? this.removeClassName(classname) : this.addClassName(classname);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setRowSpan = function(rowSpan) {
  XNJS_Dom_Impl.xn_get_obj(this).rowSpan = rowSpan;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setColSpan = function(colSpan) {
  XNJS_Dom_Impl.xn_get_obj(this).colSpan = colSpan;
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.setMaxLength = function(length) {
  XNJS_Dom_Impl.xn_get_obj(this).maxLength = length;
  return XNJS_Safe.xn_ref(this);
}

//
// Select a piece of text from 'start' to ('end' - 1) for <input>
// or <textarea>. The 'end' argument is optional, default to 'start'.
//
XNJS_Dom_Impl.prototype.setSelection = function(start, end) {
  var obj = XNJS_Dom_Impl.xn_get_obj(this);
  set_caret_position(obj, start, end);
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.submit = function() {
  XNJS_Dom_Impl.xn_get_obj(this).submit();
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.focus = function() {
  XNJS_Dom_Impl.xn_get_obj(this).focus();
  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.select = function() {
  XNJS_Dom_Impl.xn_get_obj(this).select();
  return XNJS_Safe.xn_ref(this);
}


//
//XNJS getters start ----------------------
//

XNJS_Dom_Impl.prototype.getParentNode = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    return XNJS_Dom_Impl.xn_get_first_valid_instance(intermediate.xn_obj.parentNode, 'parentNode', intermediate.xn_appid);
  }
  return null;
}

XNJS_Dom_Impl.prototype.getNextSibling = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    return XNJS_Dom_Impl.xn_get_first_valid_instance(intermediate.xn_obj.nextSibling, 'nextSibling', intermediate.xn_appid);
  }
  return null;
}

XNJS_Dom_Impl.prototype.getPreviousSibling = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    return XNJS_Dom_Impl.xn_get_first_valid_instance(intermediate.xn_obj.previousSibling, 'previousSibling', intermediate.xn_appid);
  }
  return null;
}

XNJS_Dom_Impl.prototype.getFirstChild = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    return XNJS_Dom_Impl.xn_get_first_valid_instance(intermediate.xn_obj.firstChild, 'nextSibling', intermediate.xn_appid);
  }
  return null;
}

XNJS_Dom_Impl.prototype.getLastChild = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    return XNJS_Dom_Impl.xn_get_first_valid_instance(intermediate.xn_obj.lastChild, 'previousSibling', intermediate.xn_appid);
  }
  return null;
}

XNJS_Dom_Impl.prototype.getChildNodes = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    return XNJS_Dom_Impl.xn_get_instance_list(intermediate.xn_obj.childNodes, intermediate.xn_appid);
  }
  return [];
}

XNJS_Dom_Impl.prototype.getElementsByTagName = function(tag) {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid) {
    return XNJS_Dom_Impl.xn_get_instance_list(intermediate.xn_obj.getElementsByTagName(tag), intermediate.xn_appid);
  }
  return [];
}

XNJS_Dom_Impl.prototype.getForm = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid && intermediate.xn_obj.form) {
    return XNJS_Dom_Impl.xn_get_instance(intermediate.xn_obj.form, intermediate.xn_appid);
  }
  return undefined;
}

XNJS_Dom_Impl.prototype.getOptions = function() {
  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (intermediate && intermediate.xn_obj && intermediate.xn_appid && intermediate.xn_obj.options) {
    return XNJS_Dom_Impl.xn_get_instance_list(intermediate.xn_obj.options, intermediate.xn_appid);
  }
  return undefined;
}

XNJS_Dom_Impl.prototype.getAction = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).action;
}

XNJS_Dom_Impl.prototype.getValue = function() {
  var obj = XNJS_Dom_Impl.xn_get_obj(this);
  if (obj.tagName == 'SELECT') {
    var si = obj.selectedIndex;
    if (si == -1) {
      return null;
    } else {
      return obj.value;
    }
  } else {
    return obj.value;
  }
}

//Get the display text (not value) of <option>
XNJS_Dom_Impl.prototype.getOptionText = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).text;
}

XNJS_Dom_Impl.prototype.getHref = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).href;
}

XNJS_Dom_Impl.prototype.getTarget = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).target;
}

XNJS_Dom_Impl.prototype.getSrc = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).src;
}

XNJS_Dom_Impl.prototype.getClassName = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).className;
}

XNJS_Dom_Impl.prototype.getTagName = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).tagName;
}

XNJS_Dom_Impl.prototype.getId = function() {
  var id = XNJS_Dom_Impl.xn_get_obj(this).id;
  if (id) {
    return id.replace(/^app\d+_/, '');
  }

  return id;
}

XNJS_Dom_Impl.prototype.getDir = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).dir;
}

XNJS_Dom_Impl.prototype.getChecked = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).checked;
}

//Get the width, in pixels, of the element and all its content, including
//the element's CSS padding and border, but not its margin.
//When the element has scrollbars (because of the CSS overflow attribute,
//for example), getOffsetWidth() simply reports the width of the visible
//portion of the element.
XNJS_Dom_Impl.prototype.getOffsetWidth = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).offsetWidth;
}

//Get the height, in pixels, of the element and all its content, including
//the element's CSS padding and border, but not its margin.
//When the element has scrollbars (because of the CSS overflow attribute,
//for example), getOffsetHeight() simply reports the height of the visible
//portion of the element.
XNJS_Dom_Impl.prototype.getOffsetHeight = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).offsetHeight;
}

//Get the width, in pixels, of viewport in which the element content is
//displayed.
//This is the getOffsetWidth() minus the width of the vertical scrollbars, etc.
XNJS_Dom_Impl.prototype.getClientWidth = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).clientWidth;
}

//Get the height, in pixels, of viewport in which the element content is
//displayed.
//This is the getOffsetHeight() minus the height of the horizontal scrollbars, etc.
XNJS_Dom_Impl.prototype.getClientHeight = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).clientHeight;
}

//Get the overall width, in pixels, of the element content.
//When the element has scrollbars (because of the CSS overflow attribute,
//for example), this may include the invisible portion of the element.
XNJS_Dom_Impl.prototype.getScrollWidth = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).scrollWidth;
}

//Get the overall height, in pixels, of the element content.
//When the element has scrollbars (because of the CSS overflow attribute,
//for example), this may include the invisible portion of the element.
XNJS_Dom_Impl.prototype.getScrollHeight = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).scrollHeight;
}

//Get the number of pixels that have scrolled off the left edge of the element.
//This can be used to convert between the element content coordinates and
//the element viewport coordinates.
XNJS_Dom_Impl.prototype.getScrollLeft = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).scrollLeft;
}

//Get the number of pixels that have scrolled off the top edge of the element.
//This can be used to convert between the element content coordinates and
//the element viewport coordinates.
XNJS_Dom_Impl.prototype.getScrollTop = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).scrollTop;
}

//Get the elements absolute position relative to the left of the page.
//This getter is used to simulate the functinality of offsetParent and
//offsetLeft properties, which are are not included in the XNJS DOM due
//to their unsafety.
XNJS_Dom_Impl.prototype.getAbsoluteLeft = function() {
  return elementX(XNJS_Dom_Impl.xn_get_obj(this));
}

//Get the elements absolute position relative to the top of the page.
//This getter is used to simulate the functinality of offsetParent and
//offsetTop properties, which are are not included in the XNJS DOM due
//to their unsafety.
XNJS_Dom_Impl.prototype.getAbsoluteTop = function() {
  return elementY(XNJS_Dom_Impl.xn_get_obj(this));
}

XNJS_Dom_Impl.prototype.getTabIndex = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).tabIndex;
}

XNJS_Dom_Impl.prototype.getTitle = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).title;
}

XNJS_Dom_Impl.prototype.getName = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).name;
}

XNJS_Dom_Impl.prototype.getCols = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).cols;
}

XNJS_Dom_Impl.prototype.getRows = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).rows;
}

XNJS_Dom_Impl.prototype.getAccessKey = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).accessKey;
}

XNJS_Dom_Impl.prototype.getDisabled = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).disabled;
}

XNJS_Dom_Impl.prototype.getReadOnly = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).readOnly;
}

XNJS_Dom_Impl.prototype.getType = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).type;
}

XNJS_Dom_Impl.prototype.getSelectedIndex = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).selectedIndex;
}

XNJS_Dom_Impl.prototype.getSelected = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).selected;
}

XNJS_Dom_Impl.prototype.getStyle = function(style_str) {
  return XNJS_Dom_Impl.xn_get_obj(this).style[XNJS_Safe.xn_idx(style_str)];
}

XNJS_Dom_Impl.prototype.getMethod = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).method;
}

XNJS_Dom_Impl.prototype.hasClassName = function(classname) {
  return has_css_class_name(XNJS_Dom_Impl.xn_get_obj(this), XNJS_Safe.xn_safe_string(classname));
}

XNJS_Dom_Impl.prototype.getNodeType = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).nodeType;
}

XNJS_Dom_Impl.prototype.getRowSpan = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).rowSpan;
}

XNJS_Dom_Impl.prototype.getColSpan = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).colSpan;
}

XNJS_Dom_Impl.prototype.getMaxLength = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).maxLength;
}

//
// Get the staring & ending character indices of the selected text
// in <input> or <textarea>. The returned value is an object with 
// two properties (start, end). The starting index is included, but
// the ending index is excluded in the selected text.
//
XNJS_Dom_Impl.prototype.getSelection = function() {
  var obj = XNJS_Dom_Impl.xn_get_obj(this);
  return get_caret_position(obj);
}

/*
XNJS_Dom_Impl.prototype.getData = function() {
  return XNJS_Dom_Impl.xn_get_obj(this).data;
}
*/

XNJS_Dom_Impl.prototype.getInnerHTML = function() {
  var obj = XNJS_Dom_Impl.xn_get_obj(this);
  return XNJS_Safe.xn_safe_string(obj.innerHTML);
}


//
// Event handler for XNJS DOM
// All events are passed through this function first.
// Important information is passed in as a 'this' array: [XNJS DOM object, callback, appid]
XNJS_Dom_Impl.xn_eventHandler = function(event) {
  //The DOM level 0 event model passes in a XNJS_Event object with the help of
  //our server XNJS parser. However, the DOM level 2 must passes in a real
  //DOM object.
  var e = (event instanceof XNJS_Event) ? event : new XNJS_Event(event ? event : window.event, this[2]);
  if (e.xn_ignore) {
    return;
  }
  var r = this[1].call(this[0], e);
  if (r === false) {
    e.preventDefault();
  }

  //Get the appropriate returned value
  return XNJS_Event_Impl.xn_destroy(e);
}

// The argument "type" should be a string that contains the lowercase name of the HTML
// handler attribute, with the leading "on" removed, even for IE browser.
// Special case note:
// (1) If the added event listener is the same function with the "on_<type>" attribute,
//     this function will be invoked for two times when the event occurs.
// (2) If addEventListener( ) is called multiple times to register the same function for
//     the same event type on the same object, different browsers have different behaviors.
//     IE simply invokes this funciton multiple times, but Firefox invokes this funciton
//     only once.
XNJS_Dom_Impl.prototype.addEventListener = function(type, func) {
  type = XNJS_Safe.xn_safe_string(type.toLowerCase());
  if (!XNJS_Safe.xn_allowed_events[type]) {
    XNJS_Console_Impl.xn_error('XNJS: ' + type + ' is not an allowed event type!');
    return false;
  }

  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (!intermediate || !intermediate.xn_obj) {
    XNJS_Console_Impl.xn_error('XNJS: Got invalid object reference for addEventListener! Platform may be corrupted...');
    return false;
  }
 
  var obj = intermediate.xn_obj;
  if (!intermediate.xn_events[type]) {
    intermediate.xn_events[type] = [];
  }

  var handler = null;
  if (obj.addEventListener) {
    obj.addEventListener(type, handler = XNJS_Dom_Impl.xn_eventHandler.bind([this, func, intermediate.xn_appid]), false);
  } else if (obj.attachEvent) {
    obj.attachEvent('on'+type, handler = XNJS_Dom_Impl.xn_eventHandler.bind([this, func, intermediate.xn_appid]));
  }
  intermediate.xn_events[type].push({xn_func: func, xn_handler: handler});

  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.removeEventListener = function(type, func) {
  type = XNJS_Safe.xn_safe_string(type.toLowerCase());
  if (!XNJS_Safe.xn_allowed_events[type]) {
    XNJS_Console_Impl.xn_error('XNJS: ' + type + ' is not an allowed event type!');
    return false;
  }

  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (!intermediate || !intermediate.xn_obj) {
    XNJS_Console_Impl.xn_error('XNJS: Got invalid object reference for removeEventListener! Platform may be corrupted...');
    return false;
  }

  var obj = intermediate.xn_obj;
  if (intermediate.xn_events[type]) {
    var i = 0;
    while (i < intermediate.xn_events[type].length) {
      if (intermediate.xn_events[type][i].xn_func == func) {
        if (obj.removeEventListener) {
          obj.removeEventListener(type, intermediate.xn_events[type][i].xn_handler, false);
        } else if (obj.detachEvent) {
          obj.detachEvent('on'+type, intermediate.xn_events[type][i].xn_handler);
        }
        intermediate.xn_events[type].splice(i, 1);
      }
      else {
        i++;
      }
    }
  }

  return XNJS_Safe.xn_ref(this);
}

XNJS_Dom_Impl.prototype.listEventListeners = function(type) {
  type = XNJS_Safe.xn_safe_string(type.toLowerCase());
  if (!XNJS_Safe.xn_allowed_events[type]) {
    XNJS_Console_Impl.xn_error('XNJS: ' + type + ' is not an allowed event type!');
    return false;
  }

  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (!intermediate || !intermediate.xn_obj) {
    XNJS_Console_Impl.xn_error('XNJS: Got invalid object reference for listEventListeners! Platform may be corrupted...');
    return false;
  }

  var events = [];

  if (intermediate.xn_events[type]) {
    for (var i = 0; i < intermediate.xn_events[type].length; i++) {
      events.push(intermediate.xn_events[type][i].xn_func);
    }
  }

  var obj = intermediate.xn_obj;
  if (obj['on'+type]) {
    events.push(obj['on'+type]);
  }

  return events;
}

XNJS_Dom_Impl.prototype.purgeEventListeners = function(type) {
  type = XNJS_Safe.xn_safe_string(type.toLowerCase());
  if (!XNJS_Safe.xn_allowed_events[type]) {
    XNJS_Console_Impl.xn_error('XNJS: ' + type + ' is not an allowed event type!');
    return false;
  }

  var intermediate = XNJS_Dom_Impl.xn_get_interobj(this);
  if (!intermediate || !intermediate.xn_obj) {
    XNJS_Console_Impl.xn_error('XNJS: Got invalid object reference for listEventListeners! Platform may be corrupted...');
    return false;
  }

  var obj = intermediate.xn_obj;
  if (intermediate.xn_events[type]) {
    for (var i = 0, il = intermediate.xn_events[type].length; i < il; i++) {
      if (obj.removeEventListener) {
        obj.removeEventListener(type, intermediate.xn_events[type][i].xn_handler, false);
      } else if (obj.detachEvent) {
        obj.detachEvent('on'+type, intermediate.xn_events[type][i].xn_handler);
      }
    }
    intermediate.xn_events[type] = undefined;
  }

  if (obj['on'+type]) {
    obj['on'+type] = null;
  }
  return XNJS_Safe.xn_ref(this);
}


//
// XNJS Event: wrapper for the DOM event object
//
function XNJS_Event(event, appid) {
  if (!XNJS_Event_Impl.xn_hacks) {
    XNJS_Event_Impl.xn_hacks = true;
    XNJS_Event_Impl.xn_should_check_double_arrows = ua.safari() && (ua.safari() < 500);
    XNJS_Event_Impl.xn_arrow_toggle = {};
  }

  for (var i in XNJS_Safe.xn_allowed_event_properties) {
    this[i] = event[i];
  }

  // Unify the "target" attribute
  var target = null;
  if (event.target) {
    target = event.target;
  } else if (event.srcElement) {
    target = event.srcElement;
  }
  if (target && target.nodeType == 3) {
    target = target.parentNode; // safari bug where target is a textnode which is ridiculous
  }
  this.target = XNJS_Dom_Impl.xn_get_instance(target, appid);

  // Unify the "clientX" and "clientY" attributes
  var posx = 0;
  var posy = 0;
  if (event.pageX || event.pageY) {
    posx = event.pageX;
    posy = event.pageY;
  } else if (event.clientX || event.clientY)  {
    posx = event.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
    posy = event.clientY + document.body.scrollTop  + document.documentElement.scrollTop;
  }
  this.pageX = posx;
  this.pageY = posy;

  // In Safari make sure arrow keys aren't being double fired
  if (XNJS_Event_Impl.xn_should_check_double_arrows && this.keyCode >= 37 && this.keyCode <= 40) {
    XNJS_Event_Impl.xn_arrow_toggle[this.type] = !XNJS_Event_Impl.xn_arrow_toggle[this.type];
    if (XNJS_Event_Impl.xn_arrow_toggle[this.type]) {
      this.xn_ignore = true;
    }
  }

  //Copy prototype from the implementation class
  if (!XNJS_Event.xn_proto_populated) {
    for (var i in XNJS_Event_Impl.prototype) {
      if (i != 'constructor') { //just for safeguard
        XNJS_Event.prototype[i] = XNJS_Event_Impl.prototype[i];
      }
    }
    XNJS_Event.xn_proto_populated = true;
  }

  var intermediate = {xn_data: {xn_appid: appid, xn_event: event}, xn_instance: this};;
  var priv = XNJS_Event_Impl.xn_len;
  XNJS_Event_Impl.xn_insts[priv] = intermediate;
  XNJS_Event_Impl.xn_len++;

  //Privatize the priv property
  this.xn_get_priv = function() {return priv;}
}
XNJS_Event.xn_proto_populated = false;


//
//XNJS XNJS_Event_Impl: implementation class for XNJS_Event
//
function XNJS_Event_Impl() {
}

//Array-like object
XNJS_Event_Impl.xn_insts = {};
XNJS_Event_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Event_Impl.xn_getdata = function(instance){
  if (instance instanceof XNJS_Event) {
    var intermediate = XNJS_Event_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate.xn_data;
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Event_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Event_Impl.xn_getdata...';
  }
}

XNJS_Event_Impl.xn_remove = function(instance) {
  if (instance instanceof XNJS_Event) {
    if (instance.xn_get_priv()) {
      var intermediate = XNJS_Event_Impl.xn_insts[instance.xn_get_priv()];
      if (intermediate && instance === intermediate.xn_instance) {
        delete intermediate.xn_data;
        delete intermediate;
        delete instance.xn_get_priv;
        delete instance;
      }
      else {
        throw 'XNJS: Got invalid object instance for XNJS_Event_Impl.xn_remove! Platform may be corrupted...';
      }
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Event_Impl.xn_getdata...';
  }
}

// This function can also be called in IE event model. It takes effect by
// the 'false' returned value in the IE case.
XNJS_Event_Impl.prototype.preventDefault = function() {
  var data = XNJS_Event_Impl.xn_getdata(this);
  if (!data.xn_prevented && data.xn_event.preventDefault) {
    data.xn_event.preventDefault();
    data.xn_prevented = true;
  }
  data.xn_return_value = false;
}

XNJS_Event_Impl.prototype.stopPropagation = function() {
  var event = XNJS_Event_Impl.xn_getdata(this).xn_event;
  if (event.stopPropagation) {
    event.stopPropagation();
  } else {
    event.cancelBubble = true;
  }
}

XNJS_Event_Impl.xn_destroy = function(instance) {
  var return_value = XNJS_Event_Impl.xn_getdata(instance).xn_return_value;
  XNJS_Event_Impl.xn_remove(instance); // tidy up or else there's going to be a lot of leaks on stuff like mousemove
  return return_value == undefined ? true : return_value;
}

//
// XNJS Date: a XNJS wrapper for Date object
// Simply link to the global Date object...
// Constructors include:
// new Date( )
// new Date(milliseconds)
// new Date(datestring)
// new Date(year, month, day, hours, minutes, seconds, ms)
//
// Class method:
//   Date.parse
function XNJS_Date() {
  var date = function() {
    var d = null;
    if (arguments.length == 1) {
      if (typeof arguments[0] == 'number') {
        d = new Date(arguments[0]);
      }
      else if (typeof arguments[0] == 'string') {
        d = new Date(Date.parse(arguments[0]));
      }
    }

    if (!d) {
      d = new Date();
    }

    //arguments = year, month, day, hours, minutes, seconds, ms
    if (arguments.length > 1) {
      d.setFullYear.apply(d, arguments);
    }

    return d;
  }

  date.parse = Date.parse;
  return date;
}


//
// XNJS String: a XNJS wrapper for String object
// Link to the global String object...
//
function XNJS_String() {
}
XNJS_String.prototype.fromCharCode = String.fromCharCode;


//
// XNJS RegExp: a XNJS wrapper for RegExp object
// Simply link to the global RegExp object...
//
function XNJS_RegExp() {
  var regexp = function() {
    var ret = arguments.length ? new RegExp(arguments[0], arguments[1]) : new RegExp();
    return ret;
  }
  return regexp;
}

//
// XNJS Math: a XNJS wrapper for Math object
// Simply link to the global Math object...
//
function XNJS_Math() {
}
XNJS_Math.prototype.abs = Math.abs;
XNJS_Math.prototype.acos = Math.acos;
XNJS_Math.prototype.asin = Math.asin;
XNJS_Math.prototype.atan = Math.atan;
XNJS_Math.prototype.atan2 = Math.atan2;
XNJS_Math.prototype.ceil = Math.ceil;
XNJS_Math.prototype.cos = Math.cos;
XNJS_Math.prototype.exp = Math.exp;
XNJS_Math.prototype.floor = Math.floor;
XNJS_Math.prototype.log = Math.log;
XNJS_Math.prototype.max = Math.max;
XNJS_Math.prototype.min = Math.min;
XNJS_Math.prototype.pow = Math.pow;
XNJS_Math.prototype.random = Math.random;
XNJS_Math.prototype.round = Math.round;
XNJS_Math.prototype.sin = Math.sin;
XNJS_Math.prototype.sqrt = Math.sqrt;
XNJS_Math.prototype.tan = Math.tan;
XNJS_Math.prototype.valueOf = function() {
  return XNJS_Safe.xn_ref(this);
}
XNJS_Math.prototype.E = Math.E;
XNJS_Math.prototype.LN2 = Math.LN2;
XNJS_Math.prototype.LN10 = Math.LN10;
XNJS_Math.prototype.LOG2E = Math.LOG2E;
XNJS_Math.prototype.PI = Math.PI;
XNJS_Math.prototype.SQRT1_2 = Math.SQRT1_2;
XNJS_Math.prototype.SQRT2 = Math.SQRT2;


//
// XNJS Session: Methods to access session data
function XNJS_Session(appid) {
  if (!XNJS_Session.xn_proto_populated) {
    for (var i in XNJS_Session_Impl.prototype) {
      if (i != 'constructor') { //just for safeguard
        XNJS_Session.prototype[i] = XNJS_Session_Impl.prototype[i];
      }
    }
    XNJS_Session.xn_proto_populated = true;
  }

  var app_instance = XNJS_Platform.xn_get_app(appid);
  var intermediate = {xn_data: {xn_appid:appid, xn_app_inst:app_instance}, xn_instance: this};;
  var priv = XNJS_Session_Impl.xn_len;
  XNJS_Session_Impl.xn_insts[priv] = intermediate;
  XNJS_Session_Impl.xn_len++;

  //Privatize the priv property
  this.xn_get_priv = function() {return priv;}
}
XNJS_Session.xn_proto_populated = false;


//
//XNJS Session_Impl: implementation class for XNJS_Session
//
function XNJS_Session_Impl() {
}

//Array-like object
XNJS_Session_Impl.xn_insts = {};
XNJS_Session_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Session_Impl.xn_getdata = function(instance){
  if (instance instanceof XNJS_Session) {
    var intermediate = XNJS_Session_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate.xn_data;
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Session_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Session_Impl.xn_getdata...';
  }
}

XNJS_Session_Impl.prototype.getUser = function() {
  var data = XNJS_Session_Impl.xn_getdata(this);
  if (data && data.xn_app_inst && data.xn_app_inst.xn_data.xn_loggedin) {
    return data.xn_app_inst.xn_data.xn_user;
  } else {
    return null;
  }
}

XNJS_Session_Impl.prototype.isApplicationAdded = function() {
  var data = XNJS_Session_Impl.xn_getdata(this);
  if (data && data.xn_app_inst && data.xn_app_inst.xn_data.xn_installed) {
    return data.xn_app_inst.xn_data.xn_installed;
  } else {
    return false;
  }
}

XNJS_Session_Impl.prototype.isLoggedIn = function() {
  var data = XNJS_Session_Impl.xn_getdata(this);
  if (data && data.xn_app_inst && data.xn_app_inst.xn_data.xn_loggedin) {
    return data.xn_app_inst.xn_data.xn_loggedin;
  } else {
    return false;
  }
}


//
//XNJS Xnml String: a wrapper for the xnml string
//
function XNJS_Xnml_String(htmlstring) {
  if (!XNJS_Xnml_String.xn_proto_populated) {
    for (var i in XNJS_Xnml_String_Impl.prototype) {
      if (i != 'constructor') { //just for safeguard
        XNJS_Xnml_String.prototype[i] = XNJS_Xnml_String_Impl.prototype[i];
      }
    }
    XNJS_Xnml_String.xn_proto_populated = true;
  }

  var intermediate = {xn_data: {xn_html:htmlstring}, xn_instance: this};;
  var priv = XNJS_Xnml_String_Impl.xn_len;
  XNJS_Xnml_String_Impl.xn_insts[priv] = intermediate;
  XNJS_Xnml_String_Impl.xn_len++;

  //Privatize the priv property
  this.xn_get_priv = function() {return priv;}
}
XNJS_Xnml_String.xn_proto_populated = false;

//
//XNJS_Xnml_String_Impl: implementation class for XNJS_Xnml_String
//
function XNJS_Xnml_String_Impl() {
}

//Array-like object
XNJS_Xnml_String_Impl.xn_insts = {};
XNJS_Xnml_String_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Xnml_String_Impl.xn_getdata = function(instance){
  if (instance instanceof XNJS_Xnml_String) {
    var intermediate = XNJS_Xnml_String_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate.xn_data;
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Xnml_String_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Xnml_String_Impl.xn_getdata...';
  }
}

//Get the appropriate html string
XNJS_Xnml_String_Impl.xn_getstring = function(html) {
  if (html instanceof XNJS_Xnml_String) {
    return XNJS_Xnml_String_Impl.xn_getdata(html).xn_html;
  } else {
    return htmlspecialchars(XNJS_Safe.xn_safe_string(html));
  }
}


//
// Get the XNJS Dialog Class
// XNJS Dialog: a wrapper for Xiaonei Dialog object
// Three types of Dialogs:
//   Alert Dialog: constructor(type, message[,title,type,X,Y,width,height,callBack]) 
//   Confirm Dialog: constructor(type, message[,title,callBack,yes,no,X,Y,width,height])
//   Panel Dialog: constructor(type, parameters)
//
function XNJS_getDialog(appid) {
  var proto = function(type) {
    var args = XNJS_Safe.xn_arg(arguments);
    if (args.length == 0 || typeof type != 'number') {
      //Default Dialog type
      type = XNJS_Dialog_Impl.xn_DIALOG_DEFAULT;
      args = [type].concat(args);
    }
    else if (type < XNJS_Dialog_Impl.xn_DIALOG_ALERT || type > XNJS_Dialog_Impl.xn_DIALOG_PANEL) {
      //Wrong Dialog type, use default
      XNJS_Console_Impl.xn_warn('XNJS: Invalid Dialog type, use Dialog.DIALOG_CONFIRM by default');
      type = XNJS_Dialog_Impl.xn_DIALOG_DEFAULT;
      args.splice(0, 1, type);
    }
  
    //"this" must not be used in this function, unless checked carefully
    if (!(this instanceof proto)) {
      throw 'XNJS: You must create XNJS Dialog using \"new Dialog(...)\"';
    }
  
    args = [this].concat(args);
    //Do not use Function.apply/call unless necessary
    var dialog = XNJS_Dialog_Impl.xn_build_dialog.apply(null, args);
  
    //Note that dialog may be null
    var intermediate = {xn_data: {xn_type:type, xn_dialog:dialog, xn_appid:appid}, xn_instance: this};;
    var priv = XNJS_Dialog_Impl.xn_len;
    XNJS_Dialog_Impl.xn_insts[priv] = intermediate;
    XNJS_Dialog_Impl.xn_len++;
  
    //Privatize the priv property
    this.xn_get_priv = function() {return priv;}
  }

  //Populate prototypes
  for (var i in XNJS_Dialog_Impl.prototype) {
    if (i != 'constructor') { //just for safeguard
      proto.prototype[i] = XNJS_Dialog_Impl.prototype[i];
    }
  }

  //Populate CONSTANTs
  proto.DIALOG_ALERT = XNJS_Dialog_Impl.xn_DIALOG_ALERT;
  proto.DIALOG_CONFIRM = XNJS_Dialog_Impl.xn_DIALOG_CONFIRM;
  proto.DIALOG_PANEL = XNJS_Dialog_Impl.xn_DIALOG_PANEL;

  return proto;
}

//
// XNJS XNJS_Dialog_Impl: implementation class for XNJS Dialog
//
function XNJS_Dialog_Impl() {
}

//Array-like object
XNJS_Dialog_Impl.xn_insts = {};
XNJS_Dialog_Impl.xn_len = 0;

//XNJA Dialog type
XNJS_Dialog_Impl.xn_DIALOG_ALERT = 1;
XNJS_Dialog_Impl.xn_DIALOG_CONFIRM = 2;
XNJS_Dialog_Impl.xn_DIALOG_PANEL = 3;
XNJS_Dialog_Impl.xn_DIALOG_DEFAULT = XNJS_Dialog_Impl.xn_DIALOG_CONFIRM;


//Get the private data from instance
XNJS_Dialog_Impl.xn_getdata = function(instance){
  if (instance instanceof Object) {
    var intermediate = XNJS_Dialog_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      var data = intermediate.xn_data;
      if (data && (instance instanceof eval('a' + data.xn_appid + '_Dialog'))) {
        return data;
      }
      else {
        throw 'XNJS: Got invalid object instance for XNJS_Dialog_Impl.xn_getdata (not instance of Dialog)...';
      }
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Dialog_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Dialog_Impl.xn_getdata...';
  }
}

XNJS_Dialog_Impl.xn_remove = function(instance) {
  if (instance instanceof Object) {
    if (instance.xn_get_priv()) {
      var intermediate = XNJS_Dialog_Impl.xn_insts[instance.xn_get_priv()];
      if (intermediate && instance === intermediate.xn_instance) {
        var data = intermediate.xn_data;
        if (data && (instance instanceof eval('a' + data.xn_appid + '_Dialog'))) {
          delete intermediate.xn_data;
          delete intermediate;
          delete instance.xn_get_priv;
          delete instance;
        }
        else {
          throw 'XNJS: Got invalid object instance for XNJS_Dialog_Impl.xn_remove (not instance of Dialog)...';
        }
      }
      else {
        throw 'XNJS: Got invalid object instance for XNJS_Dialog_Impl.xn_remove! Platform may be corrupted...';
      }
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Dialog_Impl.xn_remove...';
  }
}

//Build dialog with appropriate type.
//The caller guarantees that the first argument be a XNJS Dialog object and
//the second argument be a valid dialog type.
XNJS_Dialog_Impl.xn_build_dialog = function (instance, type) {
  var args = XNJS_Safe.xn_arg(arguments);
  args.splice(1, 1); //Delete the second argument: type
  var dialog = null;

  switch (type) {
    case XNJS_Dialog_Impl.xn_DIALOG_ALERT:
      dialog = XNJS_Dialog_Impl.xn_build_alert.apply(null, args);
      break;
    case XNJS_Dialog_Impl.xn_DIALOG_PANEL:
      dialog = XNJS_Dialog_Impl.xn_build_panel.apply(null, args);
      break;
    case XNJS_Dialog_Impl.xn_DIALOG_CONFIRM:
    default:
      dialog = XNJS_Dialog_Impl.xn_build_confirm.apply(null, args);
      break;
  }

  return dialog;
}

// Build the Alert Dialog
// arguments: instance, (message[,title,type,X,Y,width,height,callBack]) 
//              or
//            instance, parameters
XNJS_Dialog_Impl.xn_build_alert = function (instance) {
  var params = {};
  var args = XNJS_Safe.xn_arg(arguments);
  args.splice(0, 1); //Delete the first argument: instance

  if (args[0] instanceof Object) {
    params = args[0];
  }
  else {
    if (args.length >= 1 && typeof args[0] == 'string') {
      params.message = args[0];
    }
    if (args.length >= 2 && typeof args[1] == 'string') {
      params.title = args[1];
    }
    if (args.length >= 3 && typeof args[2] == 'string') {
      params.type = args[2];
    }
    if (args.length >= 4 && typeof args[3] == 'number') {
      params.X = args[3];
    }
    if (args.length >= 5 && typeof args[4] == 'number') {
      params.Y = args[4];
    }
    if (args.length >= 6 && typeof args[5] == 'number') {
      params.width = args[5];
    }
    if (args.length >= 7 && typeof args[6] == 'number') {
      params.height = args[6];
    }
    if (args.length >= 8 && typeof args[7] == 'function') {
      params.callBack = args[7];
    }
  }

  for (var i in params) {
    if (typeof params[i] == 'function') {
      params[i] = params[i].bind(instance);
    }
    else if (typeof params[i] != 'number') { //everything except number
      params[i] = XNJS_Xnml_String_Impl.xn_getstring(params[i]);
    }
  }

  XN.DO.alert(params);
  return null;
}

// Build the Confirm Dialog
// arguments: instance, (message[,title,callBack,yes,no,X,Y,width,height]) 
//              or
//            instance, parameters
XNJS_Dialog_Impl.xn_build_confirm = function (instance) {
  var params = {};
  var args = XNJS_Safe.xn_arg(arguments);
  args.splice(0, 1); //Delete the first argument: instance

  if (args[0] instanceof Object) {
    params = args[0];
  }
  else {
    if (args.length >= 1 && typeof args[0] == 'string') {
      params.message = args[0];
    }
    if (args.length >= 2 && typeof args[1] == 'string') {
      params.title = args[1];
    }
    if (args.length >= 3 && typeof args[2] == 'function') {
      params.callBack = args[2];
    }
    if (args.length >= 4 && typeof args[3] == 'string') {
      params.yes = args[3];
    }
    if (args.length >= 5 && typeof args[4] == 'string') {
      params.no = args[4];
    }
    if (args.length >= 6 && typeof args[5] == 'number') {
      params.X = args[5];
    }
    if (args.length >= 7 && typeof args[6] == 'number') {
      params.Y = args[6];
    }
    if (args.length >= 8 && typeof args[7] == 'number') {
      params.width = args[7];
    }
    if (args.length >= 9 && typeof args[8] == 'number') {
      params.height = args[8];
    }
  }

  for (var i in params) {
    if (typeof params[i] == 'function') {
      params[i] = params[i].bind(instance);
    }
    else if (typeof params[i] != 'number') { //everything except number
      params[i] = XNJS_Xnml_String_Impl.xn_getstring(params[i]);
    }
  }

  XN.DO.confirm(params);
  return null;
}

// Build the Panel Dialog
// arguments: instance, (body,[header,footer,X,Y,width,height]) 
//              or
//            instance, parameters
XNJS_Dialog_Impl.xn_build_panel = function (instance) {
  var params = {};
  var args = XNJS_Safe.xn_arg(arguments);
  args.splice(0, 1); //Delete the first argument: instance

  if (args[0] instanceof Object) {
    params = args[0];
  }
  else {
    if (args.length >= 1 && (args[0] instanceof XNJS_Xnml_String || typeof args[0] == 'string')) {
      params.body = args[0];
    }
    if (args.length >= 2 && (args[1] instanceof XNJS_Xnml_String || typeof args[1] == 'string')) {
      params.header = args[1];
    }
    if (args.length >= 3 && (args[2] instanceof XNJS_Xnml_String || typeof args[2] == 'string')) {
      params.footer = args[2];
    }
    if (args.length >= 4 && typeof args[3] == 'number') {
      params.X = args[3];
    }
    if (args.length >= 5 && typeof args[4] == 'number') {
      params.Y = args[4];
    }
    if (args.length >= 6 && typeof args[5] == 'number') {
      params.width = args[5];
    }
    if (args.length >= 7 && typeof args[6] == 'number') {
      params.height = args[6];
    }
  }

  for (var i in params) {
    if (typeof params[i] == 'function') {
      params[i] = params[i].bind(instance);
    }
    else if (typeof params[i] != 'number') { //everything except number
      params[i] = XNJS_Xnml_String_Impl.xn_getstring(params[i]);
    }
  }

  return new XN.UI.panel(params);
}

// Update a compoent (head/body/footer) of Dialog
// content may be a string, a XNJS_Xnml_String or a XNJS Button
XNJS_Dialog_Impl.xn_updateComponent = function(instance, api_method, content) {
  var data = XNJS_Dialog_Impl.xn_getdata(instance);
  var api_table = {
    'addHeader': ['header', 'addChild'],
    'addBody':   ['body',   'addChild'],
    'addFooter': ['footer', 'addChild'],
    'setHeader': ['header', 'setContent'],
    'setBody':   ['body',   'setContent'],
    'setFooter': ['footer', 'setContent']
  };

  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    var safe_content = '';
    if ((typeof content == 'string') || (content instanceof XNJS_Xnml_String)) {
      safe_content = XNJS_Xnml_String_Impl.xn_getstring(content);
    }
    else if (content instanceof eval('a' + data.xn_appid + '_Button')) {
      var button = XNJS_Button_Impl.xn_getdata(content).xn_button;
      safe_content = button ? button : '';
    }
    else {
      throw 'XNJS: Invalid content detected in ' + api_method;
    }
    var component = api_table[api_method][0];
    var native_method = api_table[api_method][1];
    data.xn_dialog[component][native_method](safe_content);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: ' + api_method + ' is only available on DIALOG_PANEL dialogs');
  }
}

//header may be a string, a XNJS_Xnml_String or a XNJS Button
XNJS_Dialog_Impl.prototype.addHeader = function(header) {
  var that = XNJS_Safe.xn_ref(this);
  XNJS_Dialog_Impl.xn_updateComponent(that, 'addHeader', header);
}

//body may be a string, a XNJS_Xnml_String or a XNJS Button
XNJS_Dialog_Impl.prototype.addBody = function(body) {
  var that = XNJS_Safe.xn_ref(this);
  XNJS_Dialog_Impl.xn_updateComponent(that, 'addBody', body);
}

//footer may be a string, a XNJS_Xnml_String or a XNJS Button
XNJS_Dialog_Impl.prototype.addFooter = function(footer) {
  var that = XNJS_Safe.xn_ref(this);
  XNJS_Dialog_Impl.xn_updateComponent(that, 'addFooter', footer);
}

//header may be a string, a XNJS_Xnml_String or a XNJS Button
XNJS_Dialog_Impl.prototype.setHeader = function(header) {
  var that = XNJS_Safe.xn_ref(this);
  XNJS_Dialog_Impl.xn_updateComponent(that, 'setHeader', header);
}

//body may be a string, a XNJS_Xnml_String or a XNJS Button
XNJS_Dialog_Impl.prototype.setBody = function(body) {
  var that = XNJS_Safe.xn_ref(this);
  XNJS_Dialog_Impl.xn_updateComponent(that, 'setBody', body);
}

//footer may be a string, a XNJS_Xnml_String or a XNJS Button
XNJS_Dialog_Impl.prototype.setFooter = function(footer) {
  var that = XNJS_Safe.xn_ref(this);
  XNJS_Dialog_Impl.xn_updateComponent(that, 'setFooter', footer);
}

XNJS_Dialog_Impl.prototype.destroy = function() {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    var dialog = data.xn_dialog;
    XNJS_Dialog_Impl.xn_remove(this);
    dialog.remove();
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: remove() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.hide = function() {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    data.xn_dialog.hide();
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: hide() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.show = function() {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    data.xn_dialog.show();
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: show() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.clear = function() {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    data.xn_dialog.clear();
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: show() is only available on DIALOG_PANEL dialogs');
  }
}

//type can be 'normal' or 'error'
XNJS_Dialog_Impl.prototype.setType = function(type) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (typeof type == 'string') {
      data.xn_dialog.setType(XNJS_Xnml_String_Impl.xn_getstring(type));
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setWidth() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.setWidth = function(w) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (typeof w == 'number') {
      data.xn_dialog.setWidth(w);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setWidth() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.setHeight = function(h) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (typeof h == 'number') {
      data.xn_dialog.setHeight(h);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setHeight() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.resizeTo = function(w, h) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if ((typeof w == 'number') && (typeof h == 'number')) {
      data.xn_dialog.resizeTo(w, h);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: resizeTo() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.setX = function(x) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (typeof x == 'number') {
      data.xn_dialog.setX(x);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setX() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.setY = function(y) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (typeof y == 'number') {
      data.xn_dialog.setY(y);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setY() is only available on DIALOG_PANEL dialogs');
  }
}

//x may be a XNJS_Dom object, or
//x,y are numbers.
XNJS_Dialog_Impl.prototype.moveTo = function(x, y) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (x instanceof XNJS_Dom) {
      var obj = XNJS_Dom_Impl.xn_get_obj(x);
      if (obj) {
        data.xn_dialog.moveTo(obj);
      }
    }
    else if ((typeof x == 'number') && (typeof y == 'number')) {
      data.xn_dialog.moveTo(x, y);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: moveTo() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.setAlignType = function(type) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    type = XNJS_Safe.xn_safe_string(type);
    var typePattern = new RegExp('^[1234]\\-[1234]$');
    if (typePattern.test(type)) {
      data.xn_dialog.setAlignType(type);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: ' + type + ' is not a valid align type!');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setAlignType() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.setOffsetX = function(x) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (typeof x == 'number') {
      data.xn_dialog.setOffsetX(x);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setOffsetX() is only available on DIALOG_PANEL dialogs');
  }
}

XNJS_Dialog_Impl.prototype.setOffsetY = function(y) {
  var data = XNJS_Dialog_Impl.xn_getdata(this);
  if (data && data.xn_type == XNJS_Dialog_Impl.xn_DIALOG_PANEL && data.xn_dialog) {
    if (typeof y == 'number') {
      data.xn_dialog.setOffsetY(y);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: setOffsetY() is only available on DIALOG_PANEL dialogs');
  }
}


//
// Get the XNJS Button Class
// XNJS Button: a wrapper for Xiaonei Button object
//   constructor([text, onclick, className, type])
//     or
//   constructor(parameters)
//
// Note: The third argument type is now not supported
function XNJS_getButton(appid) {
  var proto = function(type) {
    var args = XNJS_Safe.xn_arg(arguments);
  
    //"this" must not be used in this function, unless checked carefully
    if (!(this instanceof proto)) {
      throw 'XNJS: You must create XNJS Button using \"new Button(...)\"';
    }
  
    args = [this].concat(args);
    //Do not use Function.apply/call unless necessary
    var button = XNJS_Button_Impl.xn_build_button.apply(null, args);
  
    var intermediate = {xn_data: {xn_button:button, xn_appid:appid}, xn_instance: this};;
    var priv = XNJS_Button_Impl.xn_len;
    XNJS_Button_Impl.xn_insts[priv] = intermediate;
    XNJS_Button_Impl.xn_len++;
  
    //Privatize the priv property
    this.xn_get_priv = function() {return priv;}
  }

  //Populate prototypes
  for (var i in XNJS_Button_Impl.prototype) {
    if (i != 'constructor') { //just for safeguard
      proto.prototype[i] = XNJS_Button_Impl.prototype[i];
    }
  }

  return proto;
}

//
// XNJS XNJS_Button_Impl: implementation class for XNJS Button
//
function XNJS_Button_Impl() {
}

//Array-like object
XNJS_Button_Impl.xn_insts = {};
XNJS_Button_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Button_Impl.xn_getdata = function(instance){
  if (instance instanceof Object) {
    var intermediate = XNJS_Button_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      var data = intermediate.xn_data;
      if (data && (instance instanceof eval('a' + data.xn_appid + '_Button'))) {
        return data;
      }
      else {
        throw 'XNJS: Got invalid object instance for XNJS_Button_Impl.xn_getdata (not instance of Button)...';
      }
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Button_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Button_Impl.xn_getdata...';
  }
}

// Build the Button
// arguments: instance, [text, onclick, className, type] 
//              or
//            instance, parameters
// Note: The third argument type is now not supported
XNJS_Button_Impl.xn_build_button = function (instance) {
  var params = {};
  var args = XNJS_Safe.xn_arg(arguments);
  args.splice(0, 1); //Delete the first argument: instance

  //args may be empty
  if (args.length > 0) {
    if (args[0] instanceof Object) {
      params = args[0];
    }
    else {
      if (args.length >= 1 && typeof args[0] == 'string') {
        params.text = args[0];
      }
      if (args.length >= 2 && typeof args[1] == 'function') {
        params.onclick = args[1];
      }
      if (args.length >= 3 && typeof args[2] == 'string') {
        params.className = args[2];
      }
	  /** The third argument type is now not supported
      if (args.length >= 4 && typeof args[3] == 'string') {
        params.className = args[3];
      }
      */
    }
  }

  for (var i in params) {
    if (i == 'className') {
      //Check className for safety
      params[i] = htmlspecialchars(XNJS_Safe.xn_safe_string(params[i]));
    }
    else if (typeof params[i] == 'function') {
      params[i] = params[i].bind(instance);
    }
    else if (typeof params[i] != 'number') { //everything except number
      params[i] = XNJS_Xnml_String_Impl.xn_getstring(params[i]);
    }
  }

  return new XN.UI.button(params);
}

XNJS_Button_Impl.prototype.setText = function(text) {
  var data = XNJS_Button_Impl.xn_getdata(this);
  if (data && data.xn_button) {
    if (typeof text == 'string') {
      text = htmlspecialchars(XNJS_Safe.xn_safe_string(text));
      data.xn_button.setText(text);
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null button object in setText()');
  }
}

XNJS_Button_Impl.prototype.disable = function() {
  var data = XNJS_Button_Impl.xn_getdata(this);
  if (data && data.xn_button) {
    data.xn_button.disable();
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null button object in disable()');
  }
}

XNJS_Button_Impl.prototype.enable = function() {
  var data = XNJS_Button_Impl.xn_getdata(this);
  if (data && data.xn_button) {
    data.xn_button.enable();
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null button object in enable()');
  }
}

//Get the XNJS Ajax Class
function XNJS_getAjax(appid) {
  // proto: a wrapper & helper Class for Xiaonei Ajax
  var proto = function(responseType) {
    if (!responseType || (typeof responseType != 'number')) {
      XNJS_Console_Impl.xn_warn('XNJS: No argument for response type, use Ajax.RAW by default...');
      responseType = XNJS_Ajax_Impl.xn_RAW;
    }
  
    if (responseType < XNJS_Ajax_Impl.xn_RAW || responseType > XNJS_Ajax_Impl.xn_XNML) {
      //Use RAW type by default
      responseType = XNJS_Ajax_Impl.xn_RAW;
      XNJS_Console_Impl.xn_warn('XNJS: Invalid response type detected, use Ajax.RAW by default...');
    }
  
    //"this" must not be used in this function, unless checked carefully
    if (!(this instanceof proto)) {
      throw 'XNJS: You must create XNJS Ajax using \"new Ajax(responseType)\"';
    }
  
    var ajax = XNJS_Ajax_Impl.xn_new_xmlhttprequest();
  
    var intermediate = {xn_data: {xn_ajax:ajax, xn_type: responseType, xn_appid: appid}, xn_instance: this};;
    var priv = XNJS_Ajax_Impl.xn_len;
    XNJS_Ajax_Impl.xn_insts[priv] = intermediate;
    XNJS_Ajax_Impl.xn_len++;
  
    //Privatize the priv property
    this.xn_get_priv = function() {return priv;}
  }


  //Populate prototypes
  for (var i in XNJS_Ajax_Impl.prototype) {
    if (i != 'constructor') { //just for safeguard
      proto.prototype[i] = XNJS_Ajax_Impl.prototype[i];
    }
  }

  //Populate CONSTANTS
  proto.RAW = XNJS_Ajax_Impl.xn_RAW;
  proto.JSON = XNJS_Ajax_Impl.xn_JSON;
  proto.XNML = XNJS_Ajax_Impl.xn_XNML;

  return proto;
}

//
// XNJS XNJS_Ajax_Impl: implementation class for XNJS Ajax
//
function XNJS_Ajax_Impl() {
}

//Array-like object
XNJS_Ajax_Impl.xn_insts = {};
XNJS_Ajax_Impl.xn_len = 0;

//XNJS Ajax response type
XNJS_Ajax_Impl.xn_RAW = 1;
XNJS_Ajax_Impl.xn_JSON = 2;
XNJS_Ajax_Impl.xn_XNML = 3;

//XNJS Ajax Proxy URL
XNJS_Ajax_Impl.xn_proxy_url = XNJS_Config.xn_ajaxproxy_url;
XNJS_Ajax_Impl.xn_canvasproxy_url = XNJS_Config.xn_canvasajaxproxy_url;

//Get the private data from instance
XNJS_Ajax_Impl.xn_getdata = function(instance){
  if (instance instanceof Object) {
    var intermediate = XNJS_Ajax_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      var data = intermediate.xn_data;
      if (data && (instance instanceof eval('a' + data.xn_appid + '_Ajax'))) {
        return data;
      }
      else {
        throw 'XNJS: Got invalid object instance for XNJS_Ajax_Impl.xn_getdata (not instance of Ajax)...';
      }
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Ajax_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Ajax_Impl.xn_getdata...';
  }
}

XNJS_Ajax_Impl.xn_new_xmlhttprequest = function() {
  return new XN.NET.xmlhttp();
}

//
// url_mode: true -- canvas mode, url must be a canvas url
//           false -- nomal mode, url must be in the same domain as the callback url
//
XNJS_Ajax_Impl.prototype.post = function(url, query, url_mode) {
  var data = XNJS_Ajax_Impl.xn_getdata(this);

  url = XNJS_Safe.xn_safe_string(url);
  if (!XNJS_Safe.xn_url_regex.test(url)) {
    throw 'XNJS: No valid url for post()!';
  }
  else {
    if (url_mode) {
      if (url.indexOf(XNJS_Config.xn_canvas_url) != 0) {
        throw 'XNJS: In Ajax canvas url mode, the argument \"url\" must be a canvas url: \"' + XNJS_Config.xn_canvas_url + '/...\"';
      }
      else {
        var ajax_action = XNJS_Ajax_Impl.xn_proxy_url.substring(1);
        var canvas_action = XNJS_Ajax_Impl.xn_canvasproxy_url.substring(1);
        if (url.indexOf(ajax_action) != -1 || url.indexOf(canvas_action) != -1) {
          throw 'XNJS: You are not allowed to post to ' + ajax_action + ' or ' + canvas_action;
        }
      }
    }
    else if (!url_mode && url.indexOf(XNJS_Config.xn_domain) != -1) {
      throw 'XNJS: You are not allowed to post to the \"' + XNJS_Config.xn_domain + '\" in Ajax normal url mode, use your app server URL instead!';
    }
  }

  if (query && !(query instanceof Object)) {
    query = undefined;
    XNJS_Console_Impl.xn_error('XNJS: The argument \"query\" must be a object!');
  }

  if (data) {
    var ajax_query = {
      'url': url,
      'type': data.xn_type,
      'app_id': data.xn_appid
    };

    if (query) {
      ajax_query['query'] = query;
    }

    var escaped_query = URI.implodeQuery(ajax_query);
	
    var ajax = data.xn_ajax;
    ajax.post(url_mode ? XNJS_Ajax_Impl.xn_canvasproxy_url : XNJS_Ajax_Impl.xn_proxy_url,
              escaped_query,
              XNJS_Ajax_Impl.successHandler.bind(this),
              {onError: XNJS_Ajax_Impl.errorHandler.bind(this),
               asynchronous: true});
  }
}

XNJS_Ajax_Impl.successHandler = function (xml) {
  var data = XNJS_Ajax_Impl.xn_getdata(this);
  if (data) {
    try {
      if(!(this.ondone instanceof Function)){
        XNJS_Console_Impl.xn_error('XNJS: The ondone property of your Ajax object is not a valid function!');
        this.ondone = function(){};
      }
  
      var text = xml.responseText;
      if (text.indexOf('for(;;);') == -1) {
        throw 'XNJS: Invalid response format from Ajax Proxy!';
      }
  
      try {
        eval('var response = ' + text.substring(text.indexOf('{')));
      } catch(e) {
        throw 'XNJS: XNJS Ajax eval failed! Response: ' + text;
      }

      if (response.error !=  undefined) {
        throw 'XNJS: XNJS Ajax reponse error detected! error_message: ' + response.error_message;
      }

      if (this.ondone) {
        var data = response.data;
        switch(response.type){
          case XNJS_Ajax_Impl.xn_RAW:
      	    this.ondone.call(this, data);
            break;
          case XNJS_Ajax_Impl.xn_JSON:
            XNJS_Ajax_Impl.make_xnjs_string_recursive(data);
            this.ondone.call(this, data);
            break;
          case XNJS_Ajax_Impl.xn_XNML:
            this.ondone.call(this, new XNJS_Xnml_String(data));
            break;
        }
      }
      else {
        XNJS_Console_Impl.xn_error('XNJS: Please attach on ondone handler to properly handle ajax response!');
      }
    }
    catch(e) {
      if(this.onerror) {
        var errobj = {};
        errobj.error = (typeof response.error != 'undefined') ? response.error : true;
        errobj.error_message = (typeof response.error_message != 'undefined') ? response.error_message : 'Unknown error';
        this.onerror.call(this, errobj);
      }
      else {
        XNJS_Console_Impl.xn_error('XNJS: There was an uncaught Ajax error. Please attach on onerror handler to properly handle failures!');
      }
    }
  }
}

XNJS_Ajax_Impl.errorHandler = function (xml) {
  var data = XNJS_Ajax_Impl.xn_getdata(this);
  if (data) {
    if(this.onerror) {
      var errobj = {error: xml.status, error_message: 'Xiaonai Ajax proxy returned error code: ' + xml.status};
      this.onerror.call(this, errobj);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: There was an uncaught Ajax error. Please attach on onerror handler to properly handle failures!');
    }
  }
}

XNJS_Ajax_Impl.make_xnjs_string_recursive = function(obj){
  for(var i in obj){
    if(i.substring(0,5)=='xnml_'){
      obj[i]=new XNJS_Xnml_String(obj[i]);
    }
    else if(typeof obj[i] == 'object'){
      XNJS_Ajax_Impl.make_xnjs_string_recursive(obj[i]);
    }
  }
}


//
// XNJS XNML DOM: base class for the xnml object.
// There is only one type of xnml object in the current implementation,
// i.e., <xn:swf>
//
function XNJS_Xnml_Dom(type, appid) {
  if (!XNJS_Xnml_Dom.xn_proto_populated) {
    for (var i in XNJS_Xnml_Dom_Impl.prototype) {
      if (i != 'constructor') { //just for safeguard
        XNJS_Xnml_Dom.prototype[i] = XNJS_Xnml_Dom_Impl.prototype[i];
      }
    }
    XNJS_Xnml_Dom.xn_proto_populated = true;
  }

  var obj = document.createElement('div');
  obj.className='__xnml_tag';
  var intermediate = {xn_data: {xn_appid:appid, xn_type:type, xn_obj:obj}, xn_instance: this};;
  var priv = XNJS_Xnml_Dom_Impl.xn_len;
  XNJS_Xnml_Dom_Impl.xn_insts[priv] = intermediate;
  XNJS_Xnml_Dom_Impl.xn_len++;

  //Privatize the priv property
  this.xn_get_priv = function() {return priv;}
}
XNJS_Xnml_Dom.xn_proto_populated = false;


//
//XNJS Xnml_Dom_Impl: implementation class for XNJS_Xnml_Dom
//
function XNJS_Xnml_Dom_Impl() {
}

//Array-like object
XNJS_Xnml_Dom_Impl.xn_insts = {};
XNJS_Xnml_Dom_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Xnml_Dom_Impl.xn_getdata = function(instance){
  if (instance instanceof XNJS_Xnml_Dom) {
    var intermediate = XNJS_Xnml_Dom_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate.xn_data;
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Xnml_Dom_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Xnml_Dom_Impl.xn_getdata...';
  }
}

XNJS_Xnml_Dom_Impl.xn_get_obj = function(instance){
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(instance);
  if (data && data.xn_obj) {
    return data.xn_obj;
  }
  else {
    throw 'XNJS: Got invalid object for XNJS_Xnml_Dom_Impl.xn_get_obj! Platform may be corrupted...';
  }
}

XNJS_Xnml_Dom_Impl.xn_render = function(instance) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(instance);
  if (data) {
    if(data.xn_rendered) {
      return; //have rendered, return directly
    }
    if(!data.xn_id) {
      data.xn_id = 'swf' + parseInt(Math.random()*999999);
    }

    switch(data.xn_type) {
      case 'xn:swf':
        var flash_obj = new SWFObject(data.xn_swf_src, data.xn_id, data.xn_swf_width,
                                      data.xn_swf_height, '5.0.0',
                                      data.xn_swf_bgcolor ? data.xn_swf_bgcolor : '#000000', 
                                      data.xn_swf_quality ? data.xn_swf_quality : 'high');

        var flash_params = {
          loop: (data.xn_swf_loop?data.xn_swf_loop:true),
          scale: (data.xn_swf_scale?data.xn_swf_scale:'default'),
          align: (data.xn_swf_align?data.xn_swf_align:'l'),
          salign: (data.xn_swf_salign?data.xn_swf_salign:'tl')
        };
        for(var i in flash_params) {
          flash_obj.addParam(i, flash_params[i]);
        }
        flash_obj.addParam('wmode','transparent');
        flash_obj.addParam('allowScriptAccess','never');

        if (data.xn_flashvars) {
          for (var i in data.xn_flashvars) {
            flash_obj.addVariable(i, data.xn_flashvars[i]);
          }
        }

        var app = XNJS_Platform.xn_get_app(data.xn_appid);
        if (app && app.xn_validation_vars) {
          for (var i in app.xn_validation_vars) {
            flash_obj.addVariable(i, app.xn_validation_vars[i]);
          }
        }

        if(data.xn_swf_waitfor_click) {
          var img = document.createElement('img');
          img.src = data.xn_swf_imgsrc ? data.xn_swf_imgsrc : XNJS_Config.xn_swf_def_imgsrc;
          if (data.xn_swf_width) {
            img.width = data.xn_swf_width;
          }
          if (data.xn_swf_height) {
            img.height = data.xn_swf_height;
          }
          if (data.xn_swf_imgstyle) {
            XNJS_Dom_Impl.xn_set_style(img, data.xn_swf_imgstyle);
          }
          if(data.xn_swf_imgclass) {
            img.className = data.xn_swf_imgclass;
          }
          var anchor = document.createElement('a');
          anchor.href = '#';
          anchor.onclick = function(){flash_obj.write(data.xn_obj); return false;}
          anchor.appendChild(img);
          data.xn_obj.appendChild(anchor);
        }
        else {
          flash_obj.write(data.xn_obj);
        }
        break;

      default:
        throw 'XNJS: ' + data.xn_type + ' is not an allowed XNJS DOM element to be created';
        break;
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null Xnml Dom object in XNJS_Xnml_Dom_Impl.xn_render()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setId = function(id) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    id = XNJS_Safe.xn_safe_string(id);
    data.xn_id = ['app', data.xn_appid, '_', id].join('');
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null Xnml Dom object in setId()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setSWFSrc = function(swfsrc) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    swfsrc = XNJS_Safe.xn_safe_string(swfsrc);
    if (XNJS_Safe.xn_url_regex.test(swfsrc)) {
      data.xn_swf_src = swfsrc;
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: ' + swfsrc + ' is not a valid SWF src');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setSWFSrc()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setImgSrc = function(imgsrc) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    imgsrc = XNJS_Safe.xn_safe_string(imgsrc);
    if (XNJS_Safe.xn_url_regex.test(imgsrc)) {
      data.xn_swf_imgsrc = imgsrc;
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: ' + imgsrc + ' is not a valid Img src');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setImgSrc()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setWidth = function(width) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    if (typeof width == 'number') {
      data.xn_swf_width = width;
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: The argument of setWidth() must be a number!');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setWidth()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setHeight = function(height) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    if (typeof height == 'number') {
      data.xn_swf_height = height;
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: The argument of setHeight() must be a number!');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setHeight()');
  }
}

// Multiple styles can be embedded in an Object (style object), similar to
// XNJS_Dom_Impl.prototype.setStyle(style, value)
XNJS_Xnml_Dom_Impl.prototype.setImgStyle = function(style, value) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    if (!data.xn_swf_imgstyle) {
      data.xn_swf_imgstyle = {};
    }

    if (typeof style == 'string') {
      value = XNJS_Safe.xn_safe_string(value);
      if (XNJS_Safe.xn_css_regex.test(value)) {
        data.xn_swf_imgstyle[style] = value;
        return XNJS_Safe.xn_ref(this);
      } else {
        XNJS_Console_Impl.xn_error('XNJS: ' + style + ': ' + value + ' is not a valid CSS style!');
      }
    } else { //style embedded in an Object
      for (var i in style) {
        this.setImgStyle(i, style[i]);
        return XNJS_Safe.xn_ref(this);
      }
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null Xnml Dom object in setImgStyle()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setImgClass = function(img_class) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    img_class = XNJS_Safe.xn_safe_string(img_class);
    data.xn_swf_imgclass = img_class;
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null Xnml Dom object in setImgClass()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setFlashVar = function(key,val) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    if (!data.xn_flashvars) {
      data.xn_flashvars = {};
    }
    data.xn_flashvars[key] = val;
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null Xnml Dom object in setImgClass()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setSWFBGColor = function(bg) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    bg = XNJS_Safe.xn_safe_string(bg);
    if (XNJS_Safe.xn_css_regex.test(bg)) {
      data.xn_swf_bgcolor = bg;
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: ' + bg + ' is not a valid SWF BG Color');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setSWFBGColor()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setWaitForClick = function(wait) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    data.xn_swf_waitfor_click = wait ? true : false;
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setWaitForClick()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setLoop = function(loop) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    data.xn_swf_loop = loop ? true : false;
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setLoop()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setQuality = function(val) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    data.xn_swf_quality = XNJS_Safe.xn_safe_string(val);
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setQuality()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setScale = function(val) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    data.xn_swf_scale = XNJS_Safe.xn_safe_string(val);
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setScale()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setAlign = function(val) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    data.xn_swf_align = XNJS_Safe.xn_safe_string(val);
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setAlign()');
  }
}

XNJS_Xnml_Dom_Impl.prototype.setSAlign = function(val) {
  var data = XNJS_Xnml_Dom_Impl.xn_getdata(this);
  if (data) {
    data.xn_swf_salign = XNJS_Safe.xn_safe_string(val);
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null SWF object in setSAlign()');
  }
}


//
// Get the XNJS Animation Class
// XNJS Animation: a wrapper for Xiaonei Animation object
// Special notes: XNJS Animation object does not need to be created
//   using "new Animation(instance)"; "Animation(instance)" will be OK
//
function XNJS_getAnimation(appid) {
  var proto = function(instance) {
    if (!(instance instanceof XNJS_Dom)) {
      throw 'XNJS: Invalid argument! Usage: Animation(obj)';
    }
  
    //"this" must not be used in this function, unless checked carefully
    if (!(this instanceof proto)) {
      return new proto(instance);
    }

    var anim = new animation(XNJS_Dom_Impl.xn_get_obj(instance));
  
    var intermediate = {xn_data: {xn_animation:anim, xn_appid:appid}, xn_instance: this};;
    var priv = XNJS_Animation_Impl.xn_len;
    XNJS_Animation_Impl.xn_insts[priv] = intermediate;
    XNJS_Animation_Impl.xn_len++;
  
    //Privatize the priv property
    this.xn_get_priv = function() {return priv;}
  }

  //Populate prototypes
  for (var i in XNJS_Animation_Impl.prototype) {
    if (i != 'constructor') { //just for safeguard
      proto.prototype[i] = XNJS_Animation_Impl.prototype[i];
    }
  }

  //Default ease functions: begin, end, both
  proto.ease = {
    begin: animation.ease.begin,
    end:animation.ease.end,
    both:animation.ease.both
  };

  return proto;
}

//
// XNJS XNJS_Animation_Impl: implementation class for XNJS Animation
//
function XNJS_Animation_Impl() {
}

//Array-like object
XNJS_Animation_Impl.xn_insts = {};
XNJS_Animation_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Animation_Impl.xn_getdata = function(instance){
  if (instance instanceof Object) {
    var intermediate = XNJS_Animation_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      var data = intermediate.xn_data;
      if (data && (instance instanceof eval('a' + data.xn_appid + '_Animation'))) {
        return data;
      }
      else {
        throw 'XNJS: Got invalid object instance for XNJS_Animation_Impl.xn_getdata (not instance of Animation)...';
      }
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Animation_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Animation_Impl.xn_getdata...';
  }
}

XNJS_Animation_Impl.prototype.stop = function() {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    data.xn_animation.stop();
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in stop()');
  }
}

// This function is only valid when the CSS 'position' property has a value 
// other than 'static'.
XNJS_Animation_Impl.prototype.by = function(attr,val) {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    if (XNJS_Safe.xn_css_regex.test(val)) {
      data.xn_animation.by(attr,val);
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: ' + attr + ': ' + val + ' is not a valid animation CSS style');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in by()');
  }
}

XNJS_Animation_Impl.prototype.from = function(attr,val) {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    if ((typeof val != 'undefined' && XNJS_Safe.xn_css_regex.test(val)) ||
        (typeof val == 'undefined' && XNJS_Safe.xn_css_regex.test(attr))) {//for shorthand notation
      data.xn_animation.from(attr, val);
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: ' + attr + ': ' + val + ' is not a valid animation CSS style');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in from()');
  }
}

XNJS_Animation_Impl.prototype.to = function(attr,val) {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    if ((typeof val != 'undefined' && XNJS_Safe.xn_css_regex.test(val)) ||
        (typeof val == 'undefined' && XNJS_Safe.xn_css_regex.test(attr))) {//for shorthand notation
      data.xn_animation.to(attr, val);
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: ' + attr + ': ' + val + ' is not a valid animation CSS style');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in to()');
  }
}

XNJS_Animation_Impl.prototype.duration = function(duration) {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    if (typeof duration == 'number') {
      data.xn_animation.duration(duration);
      return XNJS_Safe.xn_ref(this);
    } 
    else {
      XNJS_Console_Impl.xn_error('XNJS: The argument of duration() must be a number!');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in to()');
  }
}

XNJS_Animation_Impl.prototype.ondone = function(callback) {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    if (typeof callback == 'function') {
      data.xn_animation.ondone(callback.bind(this));
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: The argument of ondone() must be a function!');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in ondone()');
  }
}

XNJS_Animation_Impl.prototype.checkpoint = function(stagger,callback) {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    data.xn_animation.checkpoint(stagger, (typeof callback=='function') ? callback.bind(this) : null);
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in stop()');
  }
}

XNJS_Animation_Impl.prototype.blind = function() {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    data.xn_animation.blind();
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in blind()');
  }
}

XNJS_Animation_Impl.prototype.show = function() {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    data.xn_animation.show();
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in show()');
  }
}

XNJS_Animation_Impl.prototype.hide = function() {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    data.xn_animation.hide();
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in hide()');
  }
}

XNJS_Animation_Impl.prototype.ease = function(easing) {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    if (typeof easing == 'function') {
      data.xn_animation.ease(easing.bind(this));
      return XNJS_Safe.xn_ref(this);
    }
    else {
      XNJS_Console_Impl.xn_error('XNJS: The argument of ease() must be a function!');
    }
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in ease()');
  }
}

XNJS_Animation_Impl.prototype.go = function() {
  var data = XNJS_Animation_Impl.xn_getdata(this);
  if (data && data.xn_animation) {
    data.xn_animation.go();
    return XNJS_Safe.xn_ref(this);
  }
  else {
    XNJS_Console_Impl.xn_error('XNJS: Got null animation object in go()');
  }
}


//
//XNJS Safe: utility for safety
//
function XNJS_Safe() {
}


XNJS_Safe.xn_allowed_elements = {
  a: true,
  abbr: true,
  acronym: true,
  address: true,
  b: true,
  br: true,
  bdo: true,
  big: true,
  blockquote: true,
  caption: true,
  center: true,
  cite: true,
  code: true,
  del: true,
  dfn: true,
  div: true,
  dl: true,
  dd: true,
  dt: true,
  em: true,
  fieldset: true,
  font: true,
  form: true,
  h1: true,
  h2: true,
  h3: true,
  h4: true,
  h5: true,
  h6: true,
  hr: true,
  i: true,
  img: true,
  input: true,
  ins: true,
  iframe: true,
  kbd: true,
  label: true,
  legend: true,
  li: true,
  ol: true,
  option: true,
  optgroup: true,
  p: true,
  pre: true,
  q: true,
  s: true,
  samp: true,
  select: true,
  small: true,
  span: true,
  strike: true,
  strong: true,
  sub: true,
  sup: true,
  table: true,
  textarea: true,
  tbody: true,
  td: true,
  tfoot: true,
  th: true,
  thead: true,
  tr: true,
  tt: true,
  u: true,
  ul: true
};

XNJS_Safe.xn_allowed_editable = {
  embed: true,
  object: true
};

XNJS_Safe.xn_allowed_events = {
  focus: true,
  click: true,
  mousedown: true,
  mouseup: true,
  dblclick: true,
  change: true,
  reset: true,
  select: true,
  submit: true,
  keydown: true,
  keypress: true,
  keyup: true,
  blur: true,
  load: true,
  mouseover: true,
  mouseout: true,
  mousemove: true,
  selectstart: true
};

XNJS_Safe.xn_allowed_event_properties = {
  type: true,
  ctrlKey: true,
  keyCode: true,
  metaKey: true,
  shiftKey: true,
  altKey: true
//  target/srcElement: true,
//  pageX/pageY: true,
}


// Xiaonei blacklist of properties for Object.
// Although the last 6 properties are implied on all objects on Firefox,
// we still list them here to preserve this restriction even on IE.
// Do not touch this unless with great care.
XNJS_Safe.xn_blacklist_props = {
  'caller': true,
  'watch': true,
  'constructor': true,
  '__proto__': true,
  '__parent__': true,
  '__defineGetter__': true,
  '__defineSetter__': true,
  //apply, call: true, bind are not safe
  'apply': true,
  'call': true,
  'bind': true,
  //The following are from Xiaonei prototype lib, they must be also disabled
  //Function.prototype
  'bindAsEventListener': true,
  //Number.prototype
  'toColorPart': true,
  'succ': true,
  'times': true,
  //String.prototype
  'stripTags': true,
  'stripScripts': true,
  'extractScripts': true,
  'evalScripts': true,
  'escapeHTML': true,
  'unescapeHTML': true,
  'toQueryParams': true,
  'toArray': true,
  'camelize': true,
  'inspect': true,
  'parseQuery': true,
  //Array.prototype
  'each': true,
  'all': true,
  'any': true,
  'collect': true,
  'detect': true,
  'findAll': true,
  'grep': true,
  'include': true,
  'inject': true,
  'invoke': true,
  'max': true,
  'min': true,
  'partition': true,
  'pluck': true,
  'reject': true,
  'sortBy': true,
  //'toArray': true,
  'zip': true,
  //'inspect': true,
  'map': true,
  'find': true,
  'select': true,
  'member': true,
  'entries': true,
  //'_reverse': true, //==Array.prototype.reverse
  '_each': true,
  'clear': true,
  'first': true,
  'last': true,
  'compact': true,
  'flatten': true,
  'without': true
  //'indexOf': true, //should be allowed
  //'reverse': true, //should be allowed
  //'shift': true,   //should be allowed
  //'inspect': true,
}

//Xiaonei safe CSS regular expression. Exclude 'CSS expression'.
XNJS_Safe.xn_css_regex = /^(?:[\w\-#%+.]+|rgb\(\d+ *, *\d+, *\d+\)|url\('?http[^ ]+?'?\)| +)*$/i
//Xiaonei safe url regular expression
XNJS_Safe.xn_url_regex = /^(?:https?|mailto|ftp|aim|irc|itms|gopher|\/|#)/;

//This is called on every array lookup to make sure safety
XNJS_Safe.xn_idx = function(idx) {
  if (typeof idx == 'string') {
    if (idx.indexOf('xn_') == 0 || XNJS_Safe.xn_blacklist_props[idx]) {
      //return '__unknown__';
      //It is more safe to throw an exception
      throw 'XNJS: The index \"' + idx + '\" is not allowed';
    }
  }
  else {
    if (typeof idx != 'number') {
      throw 'XNJS: Unknown index type: \"' + XNJS_Safe.xn_safe_string(idx) + '\"';
    }
  }

  return idx;
}

// This is called on the "this" pointer to make it safe
XNJS_Safe.xn_ref = function(that) {
  if (that == window || that == document) {
    return null;
  } else if (that.ownerDocument == document) {
    XNJS_Console_Impl.xn_error('XNJS: xn_ref called unexpected with a DOM object!');
    //TODO: it may be necessary to report this event to server
    return XNJS_Dom_Impl.xn_get_instance(that);//appid unknown here (null)
  } else {
    return that;
  }
}

// This is called on the "arguments" object to make it safe:
// turn it to a safe array
XNJS_Safe.xn_arg = function(args) {
  var new_args = [];
  for (var i = 0; i < args.length; i++) {
    new_args.push(args[i]);
  }
  return new_args;
}


//To make the string safe
XNJS_Safe.xn_safe_string = function(str) {
  if (ua.safari()) { // Safari is worthless when it comes to prototype.constructor
    delete String.prototype.replace;
    delete String.prototype.toLowerCase;
  }
  return str+'';
}


//
// XNJS Console: debugging utility for app developers
//
function XNJS_Console(appid) {
  if (!XNJS_Console.xn_proto_populated) {
    for (var i in XNJS_Console_Impl.prototype) {
      if (i != 'constructor') { //just for safeguard
        XNJS_Console.prototype[i] = XNJS_Console_Impl.prototype[i];
      }
    }
    XNJS_Console.xn_proto_populated = true;
  }

  var intermediate = {xn_data: {xn_appid:appid}, xn_instance: this};;
  var priv = XNJS_Console_Impl.xn_len;
  XNJS_Console_Impl.xn_insts[priv] = intermediate;
  XNJS_Console_Impl.xn_len++;

  //Privatize the priv property
  this.xn_get_priv = function() {return priv;}
}
XNJS_Console.xn_proto_populated = false;

//
//XNJS Console_Impl: implementation class for XNJS_Console
//
function XNJS_Console_Impl() {
}

//Array-like object
XNJS_Console_Impl.xn_insts = {};
XNJS_Console_Impl.xn_len = 0;

//Get the private data from instance
XNJS_Console_Impl.xn_getdata = function(instance){
  if (instance instanceof XNJS_Console) {
    var intermediate = XNJS_Console_Impl.xn_insts[instance.xn_get_priv()];
    if (intermediate && instance === intermediate.xn_instance) {
      return intermediate.xn_data;
    }
    else {
      throw 'XNJS: Got invalid object instance for XNJS_Console_Impl.xn_getdata! Platform may be corrupted...';
    }
  }
  else {
    throw 'XNJS: Got invalid object instance for XNJS_Console_Impl.xn_getdata...';
  }
}


//error console function for platform
XNJS_Console_Impl.xn_error = function(errmsg) {
  if (typeof console != 'undefined' && console.error) {
    console.error(errmsg);
  }
}

//warning console function for platform
XNJS_Console_Impl.xn_warn = function(warnmsg) {
  if (typeof console != 'undefined' && console.warn) {
    console.warn(warnmsg);
  }
}

// The main render function for XNJS Console
XNJS_Console_Impl.xn_render = function(console_inst, instance) {
  var console_data = XNJS_Console_Impl.xn_getdata(console_inst);
  if (console_data && (typeof instance != 'undefined')) {
    var xnjs_tools_list = [
          XNJS_Document,
          XNJS_Event,
          XNJS_Session,
          XNJS_Xnml_String,
          eval('a' + console_data.xn_appid + '_Dialog'),
          eval('a' + console_data.xn_appid + '_Button'),
          eval('a' + console_data.xn_appid + '_Ajax'),
          eval('a' + console_data.xn_appid + '_Animation')
        ];

    var xnjs_tools_impl_list = [
          XNJS_Document_Impl,
          XNJS_Event_Impl,
          XNJS_Session_Impl,
          XNJS_Xnml_String_Impl,
          XNJS_Dialog_Impl,
          XNJS_Button_Impl,
          XNJS_Ajax_Impl,
          XNJS_Animation_Impl
        ];

    for (var i = 0; i < xnjs_tools_list.length; i++) {
      if (instance instanceof xnjs_tools_list[i]) {
        break;
      }
    }

    if (i < xnjs_tools_list.length) {
      var new_obj = {};
      for (var j in instance) {
        new_obj[j] = instance[j];
      }
      delete new_obj.xn_get_priv;
      for (var j in new_obj) {
        new_obj[j] = XNJS_Console_Impl.xn_render(console_inst, new_obj[j]);
      }
  
      //Render private data
      var data = xnjs_tools_impl_list[i].xn_getdata(instance);
      for (var j in data) {
        new_obj['PRIV_'+j] = data[j];
      }

      return new_obj;
    }
    else if (instance instanceof XNJS_Dom) {
      var new_obj = {};
      for (var i in instance) {
        new_obj[i] = instance[i];
      }
      delete new_obj.xn_get_obj;
      new_obj.xn_PRIV_obj = XNJS_Dom_Impl.xn_get_obj(instance);
      return new_obj;
    }
    else if ((typeof instance == 'object') && instance && (instance.ownerDocument != document)) {
      var new_obj = (instance instanceof Array) ? [] : {};
      var changed = false;
      for (var i in instance) {
        (instance instanceof Array) ? new_obj.push(XNJS_Console_Impl.xn_render(console_inst, instance[i])) : new_obj[i] = XNJS_Console_Impl.xn_render(console_inst, instance[i]);
        if (new_obj[i] != instance[i]) {
          changed = true;
        }
      }
      return changed ? new_obj : instance;
    }
    else {
      return instance;
    }
  }
  else {
    return null;
  }
}

XNJS_Console_Impl.xn_render_args = function(console_inst, args) {
  var new_args = [];
  for (var i = 0; i < args.length; i++) {
    new_args[i] = XNJS_Console_Impl.xn_render(console_inst, args[i]);
  }
  return new_args;
}


XNJS_Console_Impl.prototype.log = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.log) {
    console.log.apply(console, XNJS_Console_Impl.xn_render_args(this, arguments));
  }
}

XNJS_Console_Impl.prototype.error = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.error) {
    console.error.apply(console, XNJS_Console_Impl.xn_render_args(this, arguments));
  }
}

XNJS_Console_Impl.prototype.warn = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.warn) {
    console.warn.apply(console, XNJS_Console_Impl.xn_render_args(this, arguments));
  }
}

XNJS_Console_Impl.prototype.debug = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.debug) {
    console.debug.apply(console, XNJS_Console_Impl.xn_render_args(this, arguments));
  }
}

XNJS_Console_Impl.prototype.assert = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.assert) {
    console.assert.apply(console, XNJS_Console_Impl.xn_render_args(this, arguments));
  }
}

XNJS_Console_Impl.prototype.dir = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.dir) {
    console.dir.apply(console, XNJS_Console_Impl.xn_render_args(this, arguments));
  }
}

XNJS_Console_Impl.prototype.group = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.group) {
    console.group.apply(console, XNJS_Console_Impl.xn_render_args(this, arguments));
  }
}

XNJS_Console_Impl.prototype.groupEnd = function() {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.groupEnd) {
    console.groupEnd();
  }
}

XNJS_Console_Impl.prototype.dirxml = function(instance) {
  var data = XNJS_Console_Impl.xn_getdata(this);
  if (data && (typeof console != 'undefined') && console.dirxml) {
    if (instance instanceof XNJS_Dom) {
      var obj = XNJS_Dom_Impl.xn_get_obj(instance);
      if (obj) {
        console.dirxml(obj);
      }
    }
    else {
      throw 'XNJS: console.dirxml() only support XNJS DOM object!';
    }
  }
}

