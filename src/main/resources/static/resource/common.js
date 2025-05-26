$('select[data-value]').each(function(index, el) {
	const $el = $(el);

	defaultValue = $el.attr('data-value').trim();

	if (defaultValue.length > 0) {
		$el.val(defaultValue);
	}
});


function doGoodReaction(articleId) {
	$.ajax({
		url: '/usr/reactionPoint/doGoodReaction',
		type: 'POST',
		data: {
			relTypeCode: 'article',
			relId: articleId
		},
		dataType: 'text',
		
		success: function() {
			console.log("성공");
		},
		error: function() {
			alert("좋아요 오류 발생");

		}

	});
}