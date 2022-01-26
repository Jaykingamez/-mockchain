function alertFunc(message) {
	alert(message);
}

$(document).ready(function() {
	$(document).on('submit', '.prevent', function(event) {
		event.preventDefault();
		var formId = $(this).attr('id');
		var $form = $("#"+ formId);
		$.post($form.attr("action"), $form.serialize(), function(response) {
			// receive the json
			$.each(response, function(key, value) {
				$("#" + key).val(value);
				console.log('my key' + key);
				console.log('my value' + value);
			});
		});
		return false;
	});
});
