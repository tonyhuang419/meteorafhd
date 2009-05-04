null
function generic_dialog(className,modal){this.className=className;this.content=null;this.obj=null;this.popup=null;this.overlay=null;this.modal=null;this.iframe=null;this.hidden_objects=[];if(modal==true){this.modal=true;}}
generic_dialog.dialog_stack=null;generic_dialog.prototype.should_hide_objects=ua.osx();generic_dialog.prototype.should_use_iframe=ua.ie()<7||(ua.osx()&&ua.firefox());generic_dialog.prototype.show_dialog=function(html){if(!this.obj){this.build_dialog();}
set_inner_html(this.content,html);if(generic_dialog.prototype.should_hide_objects){var imgs=this.content.getElementsByTagName('img');for(var i=0;i<imgs.length;i++){imgs[i].onload=imgs[i].onload?function(){this.onload.apply(this.img,arguments);this.dialog.hide_objects()}.bind({img:imgs[i],dialog:this,onload:imgs[i].onload}):this.hide_objects.bind(this);}}
this.show();this.focus_first_textbox();this.on_show_callback&&this.on_show_callback();return this;}
generic_dialog.prototype.focus_first_textbox=function(){function focus_textbox(node){var is_textbox=(node.tagName=="INPUT"&&node.type.toLowerCase()=="text")||(node.tagName=="TEXTAREA");if(is_textbox){try{node.focus();return false;}catch(e){};}
return true;}
iterTraverseDom(this.content,focus_textbox)}
generic_dialog.prototype.set_top=function(top){return this;}
generic_dialog.prototype.make_modal=function(){if(this.modal){return;}
this.modal=true;if(ua.ie()==7){this.build_iframe();}
this.build_overlay();this.reset_iframe();}
generic_dialog.prototype.show_loading=function(loading_html){return this.show_dialog('<div id="structs" class="share_composer share_status_post"><div class="loading"><p>'+loading_html+'</p></div></div>');}
generic_dialog.prototype.show_ajax_dialog_custom_loader=function(html,src,post_vars){post_vars=post_vars||false;this.show_loading(html);var myself=this;var ajax=new XnAjax(function(obj,text){myself.show_dialog(text);});if(post_vars){ajax.post(src,post_vars);}else{ajax.get(src);}
return this;}
generic_dialog.prototype.show_ajax_dialog=function(src,post_vars){post_vars=post_vars||false;var load='载入中...';return this.show_ajax_dialog_custom_loader(load,src,post_vars);}
generic_dialog.prototype.show_prompt=function(title,content){return this.show_dialog('<h2><span>'+title+'</span></h2><div class="dialog_content">'+content+'</div>');}
generic_dialog.prototype.show_message=function(title,content,button){if(button==null){button='Okay';}
return this.show_choice(title,content,button,function(){generic_dialog.get_dialog(this).fade_out(100)});}
generic_dialog.prototype.show_choice=function(title,content,button1,button1js,button2,button2js,buttons_left_msg,button3,button3js){var buttons='<div class="dialog_buttons" id="dialog_buttons">';if(typeof(buttons_left_msg)!='undefined'){buttons+='<div class="dialog_buttons_left_msg">';buttons+=buttons_left_msg;buttons+='</div>';}
buttons+='<input class="inputsubmit" type="button" value="'+button1+'" id="dialog_button1" />';if(button2){buttons+='<input class="inputsubmit" type="button" value="'+button2+'" id="dialog_button2" />';}
if(button3){buttons+='<input class="inputsubmit" type="button" value="'+button3+'" id="dialog_button3" />';}
this.show_prompt(title,this.content_to_markup(content)+buttons);var inputs=this.obj.getElementsByTagName('input');if(button3){button1obj=inputs[inputs.length-3];button2obj=inputs[inputs.length-2];button3obj=inputs[inputs.length-1];}else if(button2){button1obj=inputs[inputs.length-2];button2obj=inputs[inputs.length-1];}else{button1obj=inputs[inputs.length-1];}
if(button1js&&button1){if(typeof button1js=='string'){eval('button1js = function(){'+button1js+'}');}
button1obj.onclick=button1js;}
if(button2js&&button2){if(typeof button2js=='string'){eval('button2js = function(){'+button2js+'}');}
button2obj.onclick=button2js;}
if(button3js&&button3){if(typeof button3js=='string'){eval('button3js = function(){'+button3js+'}');}
button3obj.onclick=button3js;}
if(!this.modal){document.onkeyup=function(e){var keycode=(e&&e.which)?e.which:event.keyCode;var btn2_exists=(typeof button2obj!='undefined');var btn3_exists=(typeof button3obj!='undefined');var is_webkit=ua.safari();if(is_webkit&&keycode==13){button1obj.click();}
if(keycode==27){if(btn3_exists){button3obj.click();}else if(btn2_exists){button2obj.click();}else{button1obj.click();}}
document.onkeyup=function(){}}
button1obj.focus();}
return this;}
generic_dialog.prototype.show_choice_ajax=function(title,content_src,button1,button1js,button2,button2js,buttons_left_msg,button3,button3js){this.show_loading('Loading...');var handler=function(response){this.show_choice(title,response.getPayload(),button1,button1js,button2,button2js,buttons_left_msg,button3,button3js);}.bind(this);new AsyncRequest().setURI(content_src).setHandler(handler).send();return this;}
generic_dialog.prototype.show_form_ajax=function(title,src,button,reload_page_on_success){this.show_loading('Loading...');var form_id='dialog_ajax_form__'+gen_unique();var preSubmitErrorHandler=function(dialog,response){if(response.getError()!=true){dialog.hide();ErrorDialog.showAsyncError(response);}else{dialog.show_choice(title,response.getPayload(),'Okay',function(){dialog.fade_out(200);});}}.bind(null,this);var preSubmitHandler=function(dialog,response){var contents='<form id="'+form_id+'" onsubmit="return false;">'+response.getPayload()+'</form>';dialog.show_choice(title,contents,button,submitHandler,'Cancel',function(){dialog.fade_out(200);});}.bind(null,this);var submitHandler=function(){new AsyncRequest().setURI(src).setData(serialize_form(ge(form_id))).setHandler(postSubmitHandler).setErrorHandler(postSubmitErrorHandler).send();};var postSubmitHandler=function(dialog,response){dialog.show_choice(title,response.getPayload(),'Okay',function(){dialog.fade_out(200);});if(reload_page_on_success){window.location.reload();}else{setTimeout(function(){dialog.fade_out(500);},750);}}.bind(null,this);var postSubmitErrorHandler=function(dialog,response){if(response.getError()==1346001){preSubmitHandler(response);}else if(response.getError()!=true){ErrorDialog.showAsyncError(response);}else{preSubmitErrorHandler(response);}}.bind(null,this);new AsyncRequest().setURI(src).setReadOnly(true).setHandler(preSubmitHandler).setErrorHandler(preSubmitErrorHandler).send();return this;}
generic_dialog.prototype.show_form=function(title,content,button,target){content='<form action="'+target+'" method="post">'+this.content_to_markup(content);var post_form_id=ge('post_form_id');if(post_form_id){content+='<input type="hidden" name="post_form_id" value="'+post_form_id.value+'" />';}
content+='<div class="dialog_buttons"><input class="inputsubmit" name="confirm" type="submit" value="'+button+'" />';content+='<input type="hidden" name="next" value="'+htmlspecialchars(document.location.href)+'"/>';content+='<input class="inputsubmit" type="button" value="Cancel" onclick="generic_dialog.get_dialog(this).fade_out(100)" /></form>';this.show_prompt(title,content);return this;}
generic_dialog.prototype.content_to_markup=function(content){return(typeof content=='string')?'<div class="dialog_body">'+content+'</div>':'<div class="dialog_summary">'+content.summary+'</div><div class="dialog_body">'+content.body+'</div>';}
generic_dialog.prototype.hide=function(temporary){if(this.obj){this.obj.style.display='none';}
if(this.iframe){this.iframe.style.display='none';}
if(this.overlay){this.overlay.style.display='none';}
if(this.timeout){clearTimeout(this.timeout);this.timeout=null;return;}
if(this.hidden_objects.length){for(var i=0,il=this.hidden_objects.length;i<il;i++){this.hidden_objects[i].style.visibility='';}
this.hidden_objects=[];}
clearInterval(this.active_hiding);if(!temporary){if(generic_dialog.dialog_stack){var stack=generic_dialog.dialog_stack;for(var i=stack.length-1;i>=0;i--){if(stack[i]==this){stack.splice(i,1);}}
if(stack.length){stack[stack.length-1].show();}}
if(this.obj){this.obj.parentNode.removeChild(this.obj);this.obj=null;}}
return this;}
generic_dialog.prototype.fade_out=function(interval,timeout){if(!this.popup){return this;}
animation(this.obj).duration(timeout?timeout:0).checkpoint().to('opacity',0).hide().duration(interval?interval:350).ondone(this.hide.bind(this)).go();return this;}
generic_dialog.prototype.show=function(){if(this.obj&&this.obj.style.display){this.obj.style.visibility='hidden';this.obj.style.display='';this.reset_dialog();this.obj.style.visibility='';this.obj.dialog=this;}else{this.reset_dialog();}
this.hide_objects();clearInterval(this.active_hiding);this.active_hiding=setInterval(this.active_resize.bind(this),500);var stack=generic_dialog.dialog_stack?generic_dialog.dialog_stack:generic_dialog.dialog_stack=[];for(var i=stack.length-1;i>=0;i--){if(stack[i]==this){stack.splice(i,1);}else{stack[i].hide(true);}}
stack.push(this);return this;}
generic_dialog.prototype.enable_buttons=function(enable){var inputs=this.obj.getElementsByTagName('input');for(var i=0;i<inputs.length;i++){if(inputs[i].type=='button'||inputs[i].type=='submit'){inputs[i].disabled=!enable;}}}
generic_dialog.prototype.active_resize=function(){if(this.last_offset_height!=this.content.offsetHeight){this.hide_objects();this.last_offset_height=this.content.offsetHeight;}}
generic_dialog.prototype.hide_objects=function(){var objects=[];var ad_locs=['',0,1,2,4,5,9,3];for(var i=0;i<ad_locs.length;i++){var ad_div=ge('ad_'+ad_locs[i]);if(ad_div!=null){objects.push(ad_div);this.should_hide_objects=true;}}
if(!this.should_hide_objects){return;}
var rect={x:elementX(this.content),y:elementY(this.content),w:this.content.offsetWidth,h:this.content.offsetHeight};var iframes=document.getElementsByTagName('iframe');for(var i=0;i<iframes.length;i++){if(iframes[i].className.indexOf('share_hide_on_dialog')!=-1){objects.push(iframes[i]);}}
var swfs=document.getElementsByTagName('embed');for(var i=0;i<swfs.length;i++){objects.push(swfs[i]);}
for(var i=0;i<objects.length;i++){var node=objects[i].offsetHeight?objects[i]:objects[i].parentNode;swf_rect={x:elementX(node),y:elementY(node),w:node.offsetWidth,h:node.offsetHeight};if(!is_descendent(objects[i],this.content)&&rect.y+rect.h>swf_rect.y&&swf_rect.y+swf_rect.h>rect.y&&rect.x+rect.w>swf_rect.x&&swf_rect.x+swf_rect.w>rect.w&&array_indexOf(this.hidden_objects,node)==-1){this.hidden_objects.push(node);node.style.visibility='hidden';node.style.visibility='hidden';}}}
generic_dialog.prototype.build_dialog=function(){if(!this.obj){this.obj=document.createElement('div');}
this.obj.className='generic_dialog'+(this.className?' '+this.className:'');this.obj.style.display='none';onloadRegister(function(){document.body.appendChild(this.obj);}.bind(this));if(this.should_use_iframe||(this.modal&&ua.ie()==7)){this.build_iframe();}
if(!this.popup){this.popup=document.createElement('div');this.popup.className='generic_dialog_popup';}
this.popup.style.left=this.popup.style.top='';this.obj.appendChild(this.popup);if(this.modal){this.build_overlay();}}
generic_dialog.prototype.build_iframe=function(){if(!this.iframe&&!(this.iframe=ge('generic_dialog_iframe'))){this.iframe=document.createElement('iframe');this.iframe.id='generic_dialog_iframe';}
this.iframe.frameBorder='0';onloadRegister(function(){document.body.appendChild(this.iframe);}.bind(this));}
generic_dialog.prototype.build_overlay=function(){this.overlay=document.createElement('div');this.overlay.id='generic_dialog_overlay';if(document.body.clientHeight>document.documentElement.clientHeight){this.overlay.style.height=document.body.clientHeight+'px';}else{this.overlay.style.height=document.documentElement.clientHeight+'px';}
onloadRegister(function(){document.body.appendChild(this.overlay);}.bind(this));}
generic_dialog.prototype.reset_dialog=function(){if(!this.popup){return;}
onloadRegister(function(){this.reset_dialog_obj();this.reset_iframe();}.bind(this));}
generic_dialog.prototype.reset_iframe=function(){if(!this.should_use_iframe&&!(this.modal&&ua.ie()==7)){return;}
if(this.modal){this.iframe.style.left='0px';this.iframe.style.top='0px';this.iframe.style.width='100%';if((document.body.clientHeight>document.documentElement.clientHeight)&&(document.body.clientHeight<10000)){this.iframe.style.height=document.body.clientHeight+'px';}else if((document.body.clientHeight<document.documentElement.clientHeight)&&(document.documentElement.clientHeight<10000)){this.iframe.style.height=document.documentElement.clientHeight+'px';}else{this.iframe.style.height='10000px';}}else{this.iframe.style.left=elementX(this.frame)+'px';this.iframe.style.top=elementY(this.frame)+'px';this.iframe.style.width=this.frame.offsetWidth+'px';this.iframe.style.height=this.frame.offsetHeight+'px';}
this.iframe.style.display='';}
generic_dialog.prototype.reset_dialog_obj=function(){}
generic_dialog.prototype.set_width=function(w){this.obj.style.width=w?w+'px':'';}
generic_dialog.get_dialog=function(obj){while(!obj.dialog&&obj.parentNode){obj=obj.parentNode;}
return obj.dialog?obj.dialog:false;}
function pop_dialog(className,callback_function,modal){this.top=125;this.parent.construct(this,className,modal);this.on_show_callback=callback_function;}
pop_dialog.extend(generic_dialog);pop_dialog.prototype.build_dialog=function(){this.parent.build_dialog();this.obj.className+=' pop_dialog';this.popup.innerHTML='<table id="pop_dialog_table" class="pop_dialog_table">'+'<tr><td class="pop_topleft"></td><td class="pop_border"></td><td class="pop_topright"></td></tr>'+'<tr><td class="pop_border"></td><td class="pop_content" id="pop_content"></td><td class="pop_border"></td></tr>'+'<tr><td class="pop_bottomleft"></td><td class="pop_border"></td><td class="pop_bottomright"></td></tr>'+'</table>';this.frame=this.popup.getElementsByTagName('tbody')[0];this.content=this.popup.getElementsByTagName('td')[4];}
pop_dialog.prototype.reset_dialog_obj=function(){this.popup.style.top=(document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop)+this.top+'px';}
pop_dialog.prototype.set_top=function(top){this.top=top;}
function contextual_dialog(className){this.parent.construct(this,className);}
contextual_dialog.extend(generic_dialog);contextual_dialog.prototype.set_context=function(obj){this.context=obj;return this;}
contextual_dialog.prototype.build_dialog=function(){this.parent.build_dialog();this.obj.className+=' contextual_dialog';this.popup.innerHTML='<div class="contextual_arrow"><span>^_^keke1</span></div><div class="contextual_dialog_content"></div>';this.arrow=this.popup.getElementsByTagName('div')[0];this.content=this.frame=this.popup.getElementsByTagName('div')[1];}
contextual_dialog.prototype.reset_dialog_obj=function(){var x=elementX(this.context);var center=(document.body.offsetWidth-this.popup.offsetWidth)/2;if(x<document.body.offsetWidth/2){this.arrow.className='contextual_arrow_rev';var left=Math.min(center,x+this.context.offsetWidth-this.arrow_padding_x);var arrow=x-left+this.context.offsetWidth+this.arrow_padding_x;}else{this.arrow.className='contextual_arrow';var left=Math.max(center,x-this.popup.offsetWidth+this.arrow_padding_x);var arrow=x-left-this.arrow_padding_x-this.arrow_width;}
this.popup.style.top=(elementY(this.context)+this.context.offsetHeight-this.arrow.offsetHeight+this.arrow_padding_y)+'px';this.popup.style.left=left+'px';this.arrow.style.backgroundPosition=arrow+'px';}
contextual_dialog.prototype._remove_resize_events=function(){if(this._scroll_events){for(var i=0;i<this._scroll_events.length;i++){removeEventBase(this._scroll_events[i].obj,this._scroll_events[i].event,this._scroll_events[i].func);}}
this._scroll_events=[];}
contextual_dialog.prototype.show=function(){this._remove_resize_events();var obj=this.context;while(obj){if(obj.id!='content'&&(obj.scrollHeight&&obj.offsetHeight&&obj.scrollHeight!=obj.offsetHeight)||(obj.scrollWidth&&obj.offsetWidth&&obj.scrollWidth!=obj.offsetWidth)){var evt={obj:obj,event:'scroll',func:this.reset_dialog_obj.bind(this)};addEventBase(evt.obj,evt.event,evt.func);}
obj=obj.parentNode;}
var evt={obj:window,event:'resize',func:this.reset_dialog_obj.bind(this)};addEventBase(evt.obj,evt.event,evt.func);this.parent.show();}
contextual_dialog.prototype.hide=function(){this._remove_resize_events();this.parent.hide();}
contextual_dialog.prototype.arrow_padding_x=5;contextual_dialog.prototype.arrow_padding_y=10;contextual_dialog.prototype.arrow_width=13;function ErrorDialog(){this.parent.construct(this,'errorDialog',null,true);return this;};ErrorDialog.extend(pop_dialog);copy_properties(ErrorDialog.prototype,{showError:function(title,message){return this.show_message(title,message);}});copy_properties(ErrorDialog,{showAsyncError:function(response){try{return(new ErrorDialog()).showError(response.getErrorSummary(),response.getErrorDescription());}catch(ex){aiert(response);}}});