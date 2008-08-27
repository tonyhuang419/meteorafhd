<html>
<head>
	<title>test drag</title>
	<script type="text/javascript" src="/common/modules/NetLive/js/mootools.js"></script>
	<style type="text/css">
	.module {
		background-color: #ff0000;
		border: 1px dotted;
		cursor: move;
		position: relative;
	}
	.head {
		background-color: #cccccc;
		border: 1px dotted;
		height: 50px;
	}
	.body {
		background-color: #ccffcc;
		border: 1px dotted;
		height: 100px;
	}
	</style>
</head>
<body>

<table border="1" width="100%" groupid="gpBlogger">
<tr id="COLUMNS">
	<td id="COLUMN" width="50%" valign="top" class="COLUMN">
		<div  class="module">
			<div class="head">this is head</div>
			<div class="body">this is body</div>
		</div>
		<div  class="module">
			<div class="head">this is head</div>
			<div class="body">this is body</div>
		</div>
	</td>
	<td id="COLUMN" width="50%" valign="top" class="COLUMN">
		<div  class="module">
			<div class="head">this is head</div>
			<div class="body">this is body</div>
		</div>
		<div  class="module">
			<div class="head">this is head</div>
			<div class="body">this is body</div>
		</div>
	</td>
</tr>
</table>
<div id="blank" style="border:dotted 1px;display:none"></div>
<div id="log"></div>

<script>
	function ifInRect(coord_s, coord_t){
		var x = coord_s.left+coord_s.width/2;
		var y = coord_s.top+coord_s.height/2;
		var ret = false;
		if(x>coord_t.left && x<coord_t.left+coord_t.width){
			if(y>coord_t.top && y<coord_t.top+coord_t.height){
				ret = true;
			}
		}
		return ret;
	}
	$$('.module').each(function(drag){
		var ob_dragmove = new Drag.Move(drag, {
			droppables: $$('.COLUMN')
		});
		ob_dragmove.addEvent('onBeforeStart', function(){
			var left = this.element.getLeft();
			var top = this.element.getTop();
			$('blank').setStyle('display','block');
			$('blank').injectBefore(this.element);
			$('blank').setStyle('height',this.element.getStyle('height'));		this.element.setStyle('width',this.element.getStyle('width'));
			this.element.setStyle('position', 'absolute');
			this.element.setStyle('left',left);
			this.element.setStyle('top',top);
		});
		ob_dragmove.addEvent('onDrag', function(){
			var columns = $$('.COLUMN');
			var break_loop = false;
			for (var j =0 ; j<columns.length && !break_loop ; j++ )
			{
				var item = columns[j];
				if(ifInRect(ob_dragmove.element.getCoordinates(),item.getCoordinates())){
					var modules = item.getElements('.module');
					for (var i =0 ;i< modules.length ;i++ )
					{
						//if(ifInRect(ob_dragmove.element.getCoordinates(),modules[i].getCoordinates())){
						if (ob_dragmove.element.getCoordinates().top < modules[i].getCoordinates().top) {
							$('blank').injectBefore(modules[i]);
							break_loop = true ;
							break;
						}
					}
					//$('blank').setStyle('width',item.getCoordinates().width);
					if (!break_loop) $('blank').injectInside(item);						
				}
			};
		});
		ob_dragmove.addEvent('onComplete', function(){
			ob_dragmove.element.injectBefore($('blank'));
				ob_dragmove.element.setStyle('position', 'relative');
				ob_dragmove.element.setStyle('left', '0');
				ob_dragmove.element.setStyle('top', '0');
				ob_dragmove.element.setStyle('width', '100%');
				$('blank').setStyle('display','none');		
		});
	});
	$$('.COLUMN').each(function(item){
		item.addEvents({
			'over': function(el, obj){
				$('log').appendText('over\n');
			},
			'leave': function(el, obj){
				$('log').appendText('leave\n');
			}
		});
	});
	</script>
</body>
</html>