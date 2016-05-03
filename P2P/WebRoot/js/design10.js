/**
*创建 邮箱 题目
**/

function createMail(){
	$('.edit-content').append('<div class="item" id="single-write">'+
			//'<input type="hidden" name="hidden" value="邮箱"><!-- 隐藏域记录该题目的性质 -->'+
			//'<input type="hidden" name="itemNumber" id="itemNumber" value="2">'+
			'<input type="hidden" id="itemId" value="">'+
				'<div class="item-setup">'+
					'<a href="javascript:;" id="moveUp">上移</a>'+
					'<a href="javascript:;" id="moveDown">下移</a>'+
					'<a href="javascript:;" id="copy">复制</a>'+
					'<a href="javascript:;" id="delete">删除</a>'+
				'</div>'+
				'<div class="item-content">'+
					'<div class="drag">'+
						'<div class="item-title"><input type="text" value="邮箱" class="h2" name="itemTitle"></div>'+
					'</div>'+
					'<div class="item-choice">'+
						'<ul>'+
							'<li>'+
								'<input type="text" value="" class="label" name="cName">'+
								'<input type="hidden" id="choiceId" value="" name="choiceId">'+
							'</li>'+
						'</ul>'+
					'</div>'+
				'</div>'+
			'</div>');
again();
}

$('.choice-bar').find('#mail_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"手机",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createMail();
				$('.edit-content').find('.item').last().find('#itemId').val(data.itemId);
				$('.edit-content').find('.item').last().find('#choiceId').first().val(data.cid1);
				
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").html("").fadeOut(100);},2000);
			}
		},
		error:function(jqXHR){alert("服务器错误")}
	});
});