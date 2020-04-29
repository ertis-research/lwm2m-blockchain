$(window).on('load', getAllClients());

function getAllClients(){
	$.get('api/bootstrap', function(data) {
		printClients(data)
	}).fail(function(data,status,er){
		alert("error: "+data+" status: "+status+" er:"+er);
	});
}

function addClient() {
	// get inputs
	var bootstrapInfo = new Object();
	bootstrapInfo.endpoint = $('#endpoint').val();
	bootstrapInfo.url_bs = $('#url_bs').val();
	bootstrapInfo.id_bs = $('#id_bs').val();
	bootstrapInfo.key_bs = $('#key_bs').val();
	bootstrapInfo.url_s = $('#url_s').val();
	bootstrapInfo.id_s = $('#id_s').val();
	bootstrapInfo.key_s = $('#key_s').val();

	$.ajax({
		url: "api/bootstrap",
		type: 'POST',
		dataType: 'json',
		data: JSON.stringify(bootstrapInfo),
		contentType: 'application/json',
		mimeType: 'application/json',

		success: function (data) {
			printClients(data)
		},
		error:function(data,status,er) {
			alert("error: "+data+" status: "+status+" er:"+er);
		}
	});
}

function removeClient(endpoint) {
	console.log(endpoint);
	$.ajax({
        type: "DELETE",
        url: 'api/bootstrap/'+endpoint,
    }).done(function (data) {
    	printClients(data)
    }).fail(function (xhr, status, error) {
      var err = "Unable to delete the bootstrap config";
      console.error(err,endpoint, status, xhr.responseText);
      alert(err + ": " +xhr.responseText);
    });
}

function printClients(data){
	$("tr:has(td)").remove();

	$.each(data, function (index, clientInfo) {
		$("#added-client-config").append("<tr> <td>"+clientInfo.endpoint+"</td> <td>"+clientInfo.url_bs+"</td>  <td>"+clientInfo.id_bs+"</td> <td>"+clientInfo.key_bs+"</td> <td>"+clientInfo.url_s+"</td>  <td>"+clientInfo.id_s+"</td> <td>"+clientInfo.key_s+"</td> <td><button onclick=\"removeClient('"+clientInfo.endpoint+"')\"> X </button> </td> </tr>")
	});
}
