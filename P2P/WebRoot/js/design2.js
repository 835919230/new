/**
*创建多选题
*create by 何肸
*2016/1/24/19:35
**/
function createMultiple(){
	Content.append('<div class="item" id="multiple">'+
			//'<input type="hidden" name="hidden" value="多选题"><!-- 隐藏域记录该题目的性质 -->'+
			'<input type="hidden" name="itemId" value="" id="itemId">'+//这里记录itemId
			//'<input type="hidden" name="itemNumber" id="itemNumber" value="2">'+
				'<div class="item-setup">'+
					'<a href="javascript:;" id="moveUp">上移</a>'+
					'<a href="javascript:;" id="moveDown">下移</a>'+
					'<a href="javascript:;" id="copy">复制</a>'+
					'<a href="javascript:;" id="delete">删除</a>'+
				'</div>'+
				'<div class="item-content">'+
					'<div class="drag">'+
						'<div class="item-title"><input type="text" value="多选题" class="h2" name="itemTitle"></div>'+
					'</div>'+
					'<div class="item-choice">'+
						'<ul>'+
							'<li>'+
								'<input type="checkbox" name="checkbox" value="checkbox">'+
								'<input type="text" value="选项一" class="label" name="cName">'+
								'<a href="javascript:;" class="del">删除</a>'+
								'<input type="hidden" name="choiceId" id="choiceId" value="">'+
							'</li>'+
							'<li>'+
								'<input type="checkbox" name="checkbox" value="checkbox">'+
								'<input type="text" value="选项二" class="label" name="cName">'+
								'<a href="javascript:;" class="del">删除</a>'+
								'<input type="hidden" name="choiceId" id="choiceId" value="">'+
							'</li>'+
						'</ul>'+
					'</div>'+
				'</div>'+
				'<div class="item-add"><a href="javascript:;">+添加选项</a></div>'+
			'</div>');
again();
}

$('.choice-bar').find('#ml_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"多选题",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createMultiple();
				$('.edit-content').find('.item').last().find('#itemId').val(data.itemId);
				var choiceIds = $('.edit-content').find('.item').last().find('#choiceId');
				choiceIds[0].value = data.cid1;
				choiceIds[1].value = data.cid2;
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").html("").fadeOut(100);},2000);
			}
		},
		error:function(jqXHR){alert("服务器错误")}
	});
});