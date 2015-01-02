
var jcost = jcost || {};
jcost.common = jcost.common || {};

jcost.common.DEBUG_MODE = true;
jcost.common.TIMEOUT = 60000; // 60 sec

jcost.common.Methods = {
	GET: "GET",
	POST: "POST",
	PUT: "PUT",
	DELETE: "DELETE",
	
	getMethods: function() {
		return [this.GET, this.POST, this.PUT, this.DELETE];
	},
	
	getMethodsWithBody: function() {
		return [this.POST, this.PUT];
	},
	
	getMethodsWithoutBody: function() {
		return [this.GET, this.DELETE];
	}
};

jcost.common.RestCall = function(url,method,urlParams,jsonBody,callbackOnSuccess,callbackOnFail) {
	this.url = url;
	this.method = method;
	this.urlParams = urlParams;
	this.jsonBody = jsonBody;
	this.callbackOnSuccess = callbackOnSuccess;
	this.callbackOnFail = callbackOnFail;
};

jcost.common.RestCall.prototype.errorHandler = function(XMLHttpRequest, textStatus, errorThrown) {
	if (textStatus == "timeout") {
		
	} else {		
		new jBox('Notice', {
			title: errorThrown,
		    content: (XMLHttpRequest && XMLHttpRequest.responseText ? XMLHttpRequest.responseText : ""),
		    autoClose: false,
		    color: "red"
		});
		this.callbackOnFail(XMLHttpRequest, textStatus, errorThrown);
	};
};

jcost.common.RestCall.prototype.getParametrizedUrl = function() {
	var result = this.url;
	var paramObject = JSON.parse(this.urlParams);
	for(var k in paramObject){
		result = result.replace(new RegExp('{' + k + '}', 'g'), paramObject[k]);
	}	
	return result;
};


jcost.common.RestCall.prototype.isValid = function() {
	var valid = true;
	var objName = "RestCall";
	if (!this.url) {
		jcost.common.showDebug(objName+": Invalid url");
		valid = false;
	} else {
		
	}
	if (!this.method) {
		jcost.common.showDebug(objName+": Invalid http request method");
		valid = false;
	} else {
		if (!$.inArray(this.method, jcost.common.Methods.getMethods())) {
			jcost.common.showDebug(objName+": Invalid http request method");
			valid = false;
		}
	}
	return valid;
};


jcost.common.callRest = function(restCall) {
	if (!restCall || !(restCall instanceof jcost.common.RestCall) || !restCall.isValid) {
		jcost.common.showDebug("Invalid rest request.");
		return;
	}

	if ($.inArray(restCall.method, jcost.common.Methods.getMethodsWithoutBody()) >= 0) {
		$.ajax({
			url: restCall.getParametrizedUrl(),
			type: restCall.method,
			timeout: jcost.common.TIMEOUT,
			success: function(data) {
				restCall.callbackOnSuccess(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				jcost.common.RestCall.errorHandler(XMLHttpRequest, textStatus, errorThrown);
			}
		});
	} else if ($.inArray(restCall.method, jcost.common.Methods.getMethodsWithBody()) >= 0) {
		$.ajax({
			url: restCall.getParametrizedUrl(),
			type: restCall.method,
			timeout: jcost.common.TIMEOUT,
			dataType: "json",
			data: JSON.stringify(restCall.jsonBody),
			contentType: "application/json; charset=utf-8",
			success: function(data) {
				restCall.callbackOnSuccess(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				restCall.errorHandler(XMLHttpRequest, textStatus, errorThrown);
			}
		});
	}
};

jcost.common.ui = {		
	confirmDialog:	function(el, callback, callbackParam) {
		$("#"+el).dialog({
			 resizable: false,
			 height:240,
			 width:340,
			 modal: true,
			 buttons: {
				 "Yes": function() {							
					callback(callbackParam);				
				 	$( this ).dialog( "close" );
				 },
				 Cancel: function() {
				 	$( this ).dialog( "close" );
				 }
			 }
		});
	}
}

jcost.common.notice = {
	success: function(msg) {
		jcost.common.showDebug(msg);
		new jBox('Notice', {
			title: "",
		    content: (msg ? msg : ""),
		    color: "green"
		});
	}
};

jcost.common.showDebug = function(msg) {
	if (jcost.common.DEBUG_MODE) {
		console.log(msg);
	}
};

jcost.common.Urls = {
	// currency
	getCurrencies: "rest/currency",
	saveCurrency: "rest/currency",
	removeCurrency: "rest/currency/id/{currencyId}",
	getCurrency: "rest/currency/id/{currencyId}"
		
};

