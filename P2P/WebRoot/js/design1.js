var editContent = document.getElementById('editContent');
/**
*创建单选题
*created by 何肸
*2016/1/24/ 18:43
**/
var Content = $('.edit-content');
function createSingle(){
	Content.append('<div class="item" id="single">'+
			//'<input type="hidden" name="hidden" value="单选题"><!-- 隐藏域记录该题目的性质 -->'+
				'<input type="hidden" name="itemId" value="" id="itemId">'+//这里记录itemId
			//'<input type="hidden" name="itemNumber" id="itemNumber" value="2">'+//这里记录choice数目
				'<div class="item-setup">'+
					'<a href="javascript:;" id="moveUp">上移</a>'+
					'<a href="javascript:;" id="moveDown">下移</a>'+
					'<a href="javascript:;" id="copy">复制</a>'+
					'<a href="javascript:;" id="delete">删除</a>'+
				'</div>'+
				'<div class="item-content">'+
					'<div class="drag">'+
						'<div class="item-title"><input type="text" value="单选题" class="h2" name="itemTitle"></div>'+
					'</div>'+
					'<div class="item-choice">'+
						'<ul>'+
							'<li>'+
								'<input type="radio" name="radio" value="选项1">'+
								'<input type="text" value="选项1" class="label" name="cName">'+
								'<a href="javascript:;" class="del">删除</a>'+
								'<input type="hidden" name="choiceId" id="choiceId" value="">'+
							'</li>'+
							'<li>'+
								'<input type="radio" name="radio" value="选项1">'+
								'<input type="text" value="选项1" class="label" name="label">'+
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

$('.choice-bar').find('#sg_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"单选题",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createSingle();
//				var last = $('.item').last();
				$('.edit-content').find('.item').last().find('#itemId').val(data.itemId);
				var parent = $('input[value='+data.itemId+']').parent()
//				$('.edit-content').find('.item').last().find('#choiceId').first().val(data.cid1);
//				$('.edit-content').find('.item').last().find('#choiceId').last().val(data.cid1);
				parent.find('#choiceId').first().val(data.cid1);
				parent.find('li').last().find('#choiceId').val(data.cid2);
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").html("").fadeOut(100);},2000);
			}
		},
		error:function(jqXHR){alert("服务器错误")}
	});
});
