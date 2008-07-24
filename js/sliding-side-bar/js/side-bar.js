
window.addEvent('domready', function() {


	
	var myHorizontalSlide = new Fx.Slide('2', {mode: 'horizontal'});

	$('1').addEvent('click', function(e){
		e.stop();
		myHorizontalSlide.slideIn();
	});

	$('1').addEvent('click', function(e){
		e.stop();
		myHorizontalSlide.slideOut();
	});

});
