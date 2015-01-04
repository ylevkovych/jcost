var jcost = jcost || {};

jcost.currency = {
		
	populateCurrencies: function() {
		dtCurrency.ajax.reload();
	},

	//***************
	//save currencies
	saveCurrency: function() {
		var currency = jcost.currency.processor.currencyFromModal();	
		var method = (currency && currency.id) ? jcost.common.Methods.PUT : jcost.common.Methods.POST;

		jcost.currency.ui.blockUI();
		var restCall = 
			new jcost.common.RestCall(
				jcost.common.Urls.saveCurrency,
				method,
				null,
				currency,
				jcost.currency.saveCurrencyOnSuccess,
				jcost.currency.saveCurrencyOnFail);
		jcost.common.callRest(restCall);
	},
	saveCurrencyOnSuccess: function() {
		jcost.currency.ui.closeDialog();
		jcost.currency.ui.clearDialog();
		jcost.common.notice.success("Currency successfully saved");
		jcost.currency.populateCurrencies();
		jcost.currency.saveCurrencyPostProcess();
	},
	saveCurrencyOnFail: function() {
		jcost.currency.saveCurrencyPostProcess();
	},
	saveCurrencyPostProcess: function() {
		jcost.currency.ui.unblockUI();
	},
	
	deleteCurrency: function(id) {
		if (!id && id !== 0) {
			return;
		}
		
		var params = JSON.stringify({
			currencyId: id
		});		
		
		var restCall = 
			new jcost.common.RestCall(
				jcost.common.Urls.removeCurrency,
				jcost.common.Methods.DELETE,
				params,
				null,
				jcost.currency.deleteCurrencyOnSuccess,
				jcost.currency.deleteCurrencyOnFail);
		jcost.common.callRest(restCall);
	},
	deleteCurrencyOnSuccess: function() {
		jcost.common.notice.success("Currency successfully removed");
		jcost.currency.populateCurrencies();
	},
	deleteCurrencyOnFail: function() { },

	
	editCurrency: function(id) {
		if (!id && id !== 0) {
			return;
		}
		
		var params = JSON.stringify({
			currencyId: id
		});		
		
		var restCall = 
			new jcost.common.RestCall(
				jcost.common.Urls.getCurrency,
				jcost.common.Methods.GET,
				params,
				null,
				jcost.currency.editCurrencyOnSuccess,
				jcost.currency.editCurrencyOnFail);
		jcost.common.callRest(restCall);
	},
	editCurrencyOnSuccess: function(data) {
		if (!data) return;		
		jcost.currency.ui.openDialog();
		jcost.currency.processor.currencyToModal(data);	
	},
	editCurrencyOnFail: function() { },

	init: function() {	
		jcost.currency.populateCurrencies();	
	}
};

//*****************
//UI functions
jcost.currency.ui = {
	blockUI: function(){
		
	},
	unblockUI: function() {
		
	},
	openDialog: function() {
		$( "#dlgCurrency" ).dialog("open");
	},
	closeDialog: function() {
		$( "#dlgCurrency" ).dialog("close");
	},
	clearDialog: function() {
		$("#currencyId").val("");
		$("#currencyName").val("");
		$("#currencyLongName").val("");
	}		
};

//*****************
//Model to view, view to model functions
jcost.currency.processor = {
	currencyFromModal: function() {
		var currency = {
			id: ($.trim($("#currencyId").val()) == "" ? null : $.trim($("#currencyId").val())),
			name: $("#currencyLongName").val(),
			shortName: $("#currencyName").val()
		};
		console.log(currency);
		return currency;
	},
	currencyToModal: function(data) {
		if (!data || !data.id) return;
		console.log("data.id: "+data.id+"; data,shortName: "+data.shortName);		
		$("#currencyId").val(data.id);
		$("#currencyName").val(data.shortName ? data.shortName : "");
		$("#currencyLongName").val(data.name ? data.name : "");
	}
		
};

