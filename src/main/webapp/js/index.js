function alertFunc(message) {
	alert(message);
}

$(document).ready(function() {
	$(document).on('submit', '.prevent', function(event) {
		event.preventDefault();
		var formId = $(this).attr('id');
		var $form = $("#"+ formId);
		console.log($form.attr("action"));
		/*
		$.post($form.attr("action"), $form.serialize(), function(response) {
			console.log(response);
			// receive the json
			if(response.type === undefined){
				$("#receiver").hide();
			}
			$.each(response, function(key, value) {
				if (key.includes("Amount")){
					$("#" + key).html(value + ".00");
				} else if(key === "transactionId") 
					$("#" + key).val(value);
				else{
					$("#" + key).html(value);
				}
			});
		});
		return false;
		*/
	});
});
