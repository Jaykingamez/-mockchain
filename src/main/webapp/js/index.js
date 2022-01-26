function alertFunc(message) {
	alert(message);
}

function getInfo(id){
	var $form = $(id);
	$.post($form.attr("action"), $form.serialize(), function(response) {
        // receive the json
    });
}
