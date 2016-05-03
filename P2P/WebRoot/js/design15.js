/**
 * 创建 教育程度 题目
 */

function createEdu(){

	$('.edit-content').append('<div class="item" id="single">'+
			//'<input type="hidden" name="hidden" value="教育程度"><!-- 隐藏域记录该题目的性质 -->'+
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
					'<div class="item-title"><input type="text" value="受教育程度" class="h2" name="itemTitle"></div>'+
				'</div>'+
				'<div class="item-choice">'+
					'<ul>'+
						'<li>'+
							'<input type="radio" name="radio" value="radio">'+
							'<input type="text" value="初中" class="label" name="cName">'+
							'<input type="hidden" id="choiceId" value="" name="choiceId">'+
							'<a href="javascript:;" class="del">删除</a>'+
						'</li>'+
						'<li>'+
							'<input type="radio" name="radio" value="radio">'+
							'<input type="text" value="高中" class="label" name="cName">'+
							'<input type="hidden" id="choiceId" value="" name="choiceId">'+
							'<a href="javascript:;" class="del">删除</a>'+
						'</li>'+
						'<li>'+
							'<input type="radio" name="radio" value="radio">'+
							'<input type="text" value="大专" class="label" name="cName">'+
							'<input type="hidden" id="choiceId" value="" name="choiceId">'+
							'<a href="javascript:;" class="del">删除</a>'+
						'</li>'+
						'<li>'+
							'<input type="radio" name="radio" value="radio">'+
							'<input type="text" value="本科" class="label" name="cName">'+
							'<input type="hidden" id="choiceId" value="" name="choiceId">'+
							'<a href="javascript:;" class="del">删除</a>'+
						'</li>'+
						'<li>'+
							'<input type="radio" name="radio" value="radio">'+
							'<input type="text" value="硕士及以上" class="label" name="cName">'+
							'<input type="hidden" id="choiceId" value="" name="choiceId">'+
							'<a href="javascript:;" class="del">删除</a>'+
						'</li>'+
					'</ul>'+
				'</div>'+
			'</div>'+
			'<div class="item-add"><a href="javascript:;">+添加选项</a></div>'+
		'</div>');
	again();
}

$('.choice-bar').find('#edu_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"教育程度",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createEdu();
				$('.edit-content').find('.item').last().find('#itemId').val(data.itemId);
				var choiceIds = $('.edit-content').find('.item').last().find('#choiceId');
				choiceIds[0].value = data.cid1;
				choiceIds[1].value = data.cid2;
				choiceIds[2].value = data.cid3;
				choiceIds[3].value = data.cid4;
				choiceIds[4].value = data.cid5;
				
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").html("").fadeOut(100);},2000);
			}
		},
		error:function(jqXHR){alert("服务器错误")}
	});
});