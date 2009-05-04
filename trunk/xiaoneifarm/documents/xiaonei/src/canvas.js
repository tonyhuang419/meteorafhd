null/* App Canvas Iframe smart-size*/
var smartIframes = [];
function smartSizingFrameAdded(){
    window.onresize = resizeSmartFrames;
    smartIframes = [];
    var allIframes = document.getElementsByTagName('iframe');
    for (var i = 0; i < allIframes.length; i++) {
        var frame = allIframes[i];
        if (frame.className == 'smart_sizing_iframe') {
            smartIframes.push(frame);
            frame.style.width = frame.parentNode.scrollWidth - 2 + "px";
        }
    }
    resizeSmartFrames();
}

if (window.innerHeight) {
    var windowHeight = function(){
        return window.innerHeight;
    };
}
else 
    if (document.documentElement && document.documentElement.clientHeight) {
        var windowHeight = function(){
            return document.documentElement.clientHeight;
        };
    }
    else {
        var windowHeight = function(){
            return document.body.clientHeight;
        };
    }
function resizeSmartFrames(){
    var height = windowHeight();
    for (var i = 0; i < smartIframes.length; i++) {
        var frame = smartIframes[i];
        var spaceLeft = height - elementY(frame) - 35;
        frame.style.height = spaceLeft / (smartIframes.length - i) + 'px';
    }
}

if (window.Bootloader) {
    Bootloader.done(1);
}

/* App Canvas, app rating */
var commentTxt = '请输入评价内容...';

function initStarRating() {
	updateRatingNum(initialRating);
	updateStar(initialRating);
	//$('mark_txt').innerHTML = txt[0];
}
function doMark(value, sendRating) {
	if (0 != value) {
		// updateStar(value);
		// updateRatingNum(value);
		// $('mark_txt').innerHTML = txt[value];
		$('StarRating').className = 'star-rating star-rated';
		if (sendRating) {
			var hostid = XN.Cookie.get('hostid');
			var cookie = XN.Cookie.get('xn_app_rating_' + hostid);
			if (cookie != null && cookie == 1) {
				XN.DO.alert({
					message:'<div class="messages_div no-margin"><strong>您已经点评过这个应用程序了</strong></div>',
					noFooter:false,
					autoHide:10
					});
			} else {
				showPanel(value);
			}
		}
	}
}
function updateRatingNum(rating) {
	var scoreContainer = $('scorethis');
	var integer = Math.floor(rating);
	var decimal = Math.floor((rating - integer) * 100);
	scoreContainer.innerHTML = "<strong>" + integer + "</strong>." + (decimal != 0 ? decimal : "00");
}
function updateStar(value) {
	var starRating = document.getElementById('StarRating');
	var rating = starRating.getElementsByTagName('li');
	var num = 5;
	rating[0].className = 'current-rating';
	rating[0].style.width = Math.floor(value * 100.0 / num) + "%";
	$('StarRating').className = 'star-rating star-rated';
}
function onCommentFocus() {
	if ($('appComment').value == commentTxt) $('appComment').value='';
}
function onRatingCheck() {
	$('appCommentFormError').innerHTML = '';
	$('appCommentFormError').style.display = 'none';
}
function showPanel(){
	var panel;
	if(!showPanel.panel){
	//如果之前没有创建一个面板,则新建一个
		//panel = showPanel.panel = new XN.UI.panel();
		panel = showPanel.panel = new XN.UI.dialog({addIframe:true});
		panel.setWidth(500);
		panel.header.setContent('对"' + appName + '"的点评');
		var content = '<p id="appCommentFormError" class="error" style="border:1px solid #DD3C10;padding:4px;background-color:#FFEBE8;display:none">test</p><form id="appCommentForm" class="clearfix">' +
			'<input type="hidden" name="app_id" value="' + appId + '"/>' +
			'<div><span style="color:#666">请评分: </span>' +
			'<label class="star1"><input type="radio" onclick="onRatingCheck()" value="1" name="rating"/><span>1</span></label>' +
			'<label class="star2"><input type="radio" onclick="onRatingCheck()" value="2" name="rating"/><span>2</span></label>' +
			'<label class="star3"><input type="radio" onclick="onRatingCheck()" value="3" name="rating"/><span>3</span></label>' +
			'<label class="star4"><input type="radio" onclick="onRatingCheck()" value="4" name="rating"/><span>4</span></label>' +
			'<label class="star5"><input type="radio" onclick="onRatingCheck()" value="5" name="rating"/><span>5</span></label>' +
			'</div>' +
			'<textarea style="height: 60px;" class="cmtwords" id="appComment" name="comment" onclick="onCommentFocus()">' + commentTxt + '</textarea>' + 
			'<span class="status-count" id="statusCount" style="float:left;height:20px;"></span>' + 
			'</form>';
		panel.body.setContent(content);
		var confirmButton = new XN.UI.button({
			text:'确定',
			onclick:function(){
				var radios = document.getElementsByName('rating');
				var checked = false;
				for (var i = 0; i < radios.length; i ++) {
					if (radios[i].checked) {
						checked = true;
						break;
					}
				}
				if (checked) {
					if ($('appComment').value == commentTxt) $('appComment').value='';
					var queryString = XN.Form.serialize('appCommentForm');
					Form.disable('appCommentForm');
					confirmButton.disable();
					new Ajax.Request('/apprating.do',
							{	method:'post',
								postBody : queryString,
								onFailure: function(transport){
									alert("发生错误，评分失败，请联系管理员。");
									panel.hide();	
								},
								onSuccess: function(transport){
									panel.hide();
									Form.enable('appCommentForm');
									confirmButton.enable();
									if (transport.responseText == 'done') {
										var hideTime = 2;
										setTimeout(function(){XN.DOM.enable();},hideTime * 1000);
										XN.DO.alert({
										message:'<div class="messages_div no-margin"><strong>感谢您对此应用进行评价，您的评分稍后才会生效</strong></div>',
										noFooter:true,
										autoHide:hideTime
										});
									} else {
										XN.DOM.enable();
										alert("发生错误，评分失败，请联系管理员。");
									}
								}
							}
						);
				} else {
					$('appCommentFormError').innerHTML = '请选择一个星级';
					$('appCommentFormError').style.display = 'block';
				}
			}
		});
		panel.footer.addChild(confirmButton);
		panel.footer.addChild(new XN.UI.button({
			text:'取消',
			className:'gray',
			onclick:function(){
				panel.hide();
				XN.DOM.enable();
			}
		}));
		panel.hide();
		(new XN.FORM.inputHelper('appComment')).setDefaultValue(commentTxt).countSize('statusCount',150);
	}else{
		panel = showPanel.panel;
	}
	// $('static_stars').style.width = Math.floor(value * 100.0 / 5) + "%";
	//$('panel_mark_txt').innerHTML = txt[value];
	$('appCommentForm').reset();
	$('statusCount').innerHTML = '0/150';
	// $('ratingInput').value = value;
	XN.DOM.disable();
	panel.show();
}
/* Footer Author list */
function showAuthor(){
    if($('authorList').style.display=='none'){
      $('authorList').style.display = 'block';
      posY = 6 - $('authorList').offsetHeight + "px";
      $('authorList').style.top = posY;
      $('contactBtn').className = 'contact-btn-active';
    }else{
      $('authorList').style.display='none';
      $('contactBtn').className = 'contact-btn';
    }
 }
/* Footer uninstall app*/
function confirmUninstall(appId, appName) {
	XN.DOM.disable();
	XN.DO.confirm({
		title: '卸载"' + appName + '"?',
		message: '<p style="margin:10px 0;">卸载该应用，它将不会再出现在你的个人主页，功能菜单以及<a href="http://app.xiaonei.com/apps/editapps.do">编辑菜单</a>等页面。如果想要再次使用，你必须再次添加这个应用。</p><p></p><p style="margin:10px 0;"><strong>是否真的要卸载"'+ appName + '"?</strong></p> ',
		params:{addIframe:true},
		callBack : function(r){
			if (r) {
				$('uninstallForm').submit();
			}
		},
		yes:'确定',
		no:'取消'
	});
}

/* Footer contact developer*/
function contactDeveloper(appId){
    //alert('test');
	XN.DOM.disable();
    var d = XN.DO.confirm({
    	title:'联系开发者',
    	yes:'发送',
    	params:{addIframe:true},
		callBack : sendUserMsg
    }).setWidth(440);
	
	sendLoading('载入中 ...');
    new XN.net.xmlhttp({ 
        url:'/contactDeveloper.do',
        method:'get',
        data:'appId=' + appId,
        onComplete: showSendWindow,
        onError: function(transport){
                  alert("发生错误，请联系管理员。");
               }
    });
    
    function sendLoading(msg){
    	if(!msg) {
    		msg = 'Loading ...';
    	}
    	d.header.hide();
		d.footer.hide(); 
		d.body.setContent( msg );
		d.refresh();
    }
    
    function showSendWindow(transport){
    	
    	d.header.show();
    	d.footer.show();
    	//d.setTitle('发信给开发者');
    	d.body.setContent(transport.responseText);
    	//d.getButton('确定').setText('发送');
    	d.refresh();
    }
    
    function sendUserMsg(flag){
    	if(flag){ 
	 		if($('con_type').value==0){
	 			
	 			$('con_err').style.display='block';
	 			$('con_err').innerHTML="请选择一个问题类型";
	 			this.preventHide();
	 			$('con_type').focus();
	 			return false;
	 		}
	 		
	 		if(($('user_mail').value!="")&&(!test_email($('user_mail').value))){
	 		
	 			$('con_err').style.display='block';
	 			$('con_err').innerHTML="请输入正确的邮件地址";
	 			this.preventHide();
	 			$('user_mail').focus();
	 			return false;
	 		}

	 		if($('con_content').value==""){
	 			$('con_err').style.display='block';
	 			$('con_err').innerHTML="请输入反馈信息";
	 			this.preventHide();
	 			$('con_content').focus();
	 			return false;		
	 		}
	 		

		    //alert(XN.FORM.serialize($('contactForm')));      
		    new XN.net.xmlhttp({ 
		        url:'/contactDeveloper.do',
		        data:XN.FORM.serialize($('contactForm')),
		        method:'post',
		        onSuccess: showSuccDialog,
		        onError: function(transport){
		                  alert("发生错误，请联系管理员。");
		               }
		    });
		    d.preventHide();
		    sendLoading('发送中 ...');
		 } else {
			 d.hide();
			 XN.DOM.enable();
		 }
    }
    
    function showSuccDialog(transport){

		//alert('succ');
		var toUserMsg = '您的消息已经发送给 '+ appName + ' 的开发人员。';
		d.header.show();
    	d.footer.show();
    	d.setTitle('发送成功');
    	d.body.setContent(toUserMsg);
    	d.getButton('发送').hide();
    	d.getButton('取消').hide();//.setText("确定");
		d.addButton({text:'确定', onclick:function(){
			d.hide();
			XN.DOM.enable();
		}});
		d.refresh();
		setTimeout(function(){d.hide();XN.DOM.enable();},1500);
	}
	
	
}

function test_email(strEmail) { 
  var myReg = /^([a-zA-Z0-9(\.)_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/; 
  if(myReg.test(strEmail)) return true; 
  return false; 
}




