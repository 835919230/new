/**
*创建多行填空题
*create by 何肸
*2016/1/24/19:54
**/

function createMutipleWrite(){

	$('.edit-content').append('<div class="item" id="multiple-write">'+
			//'<input type="hidden" name="hidden" value="多行填空题"><!-- 隐藏域记录该题目的性质 -->'+
			'<input type="hidden" id="itemId" value="">'+
			//'<input type="hidden" name="itemNumber" id="itemNumber" value="2">'+
			'<div class="item-setup">'+
				'<a href="javascript:;" id="moveUp">上移</a>'+
				'<a href="javascript:;" id="moveDown">下移</a>'+
				'<a href="javascript:;" id="copy">复制</a>'+
				'<a href="javascript:;" id="delete">删除</a>'+
			'</div>'+
			'<div class="item-content">'+
				'<div class="drag">'+
					'<div class="item-title"><input type="text" value="多行填空题" class="h2" name="itemTitle"></div>'+
				'</div>'+
				'<div class="item-choice">'+
					'<ul>'+
						'<li>'+
							'<textarea cols="30" rows="10" name="cName">'+'</textarea>'+
							'<input type="hidden" id="choiceId" value="" name="choiceId">'+
						'</li>'+
					'</ul>'+
				'</div>'+
				'</div>'+
		'</div>');
	again();
}

$('.choice-bar').find('#ml_wr_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"多行填空题",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createMutipleWrite();
				$('.edit-content').find('.item').last().find('#itemId').val(data.itemId);
				$('.edit-content').find('.item').last().find('#choiceId').first().val(data.cid1);
				
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").html("").fadeOut(100);},2000);
			}
		},
		error:function(jqXHR){alert("服务器错误")}
	});
});