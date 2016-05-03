var timer = null;
function show(){
	var oDl = document.getElementById('dropMenu');
	oDl.style.display='block';
}

function disappear(){
	var oDl = document.getElementById('dropMenu');
	oDl.style.display='none';
}

window.onload=function(){
	var oA = document.getElementById('drop');
	var oDM = document.getElementById('dropMenu');
	var oDl = document.getElementById('dl1');

	oA.onclick = function(){
		location.href = 'QuestionTemplet.html';
	}

	oA.onmouseover = function(){
		clearTimeout(timer);
		show();
	}
	oDM.onmouseover = function(){
		clearTimeout(timer);
		show();
	}
	
	oA.onmouseout = function(){
		timer=setTimeout('disappear()',500);
	}
	oDM.onmouseout = function(){
		timer=setTimeout('disappear()',500);
	}

}