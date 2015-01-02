var jcost = jcost || {};

//***************
// get currencies
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
				jcost.currency.saveCurrenciesOnSuccess,
				jcost.currency.saveCurrenciesOnFail);
		jcost.common.callRest(restCall);
	},
	saveCurrenciesOnSuccess: function() {
		jcost.currency.ui.closeDialog();
		jcost.currency.ui.clearDialog();
		jcost.common.notice.success("Currency successfully saved");
		jcost.currency.populateCurrencies();
		jcost.currency.saveCurrenciesPostProcess();
	},
	saveCurrenciesOnFail: function() {
		var tltp = $(document).tooltip({
			content: function() {
				return "Errors occured!!!!!!!";
			}
		});
		console.log("tltp: "+tltp);
		tltp.tooltip("open");
		jcost.currency.saveCurrenciesPostProcess();
	},
	saveCurrenciesPostProcess: function() {
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
				jcost.currency.deleteCurrenciesOnSuccess,
				jcost.currency.deleteCurrenciesOnFail);
		jcost.common.callRest(restCall);
	},
	deleteCurrenciesOnSuccess: function() {
		jcost.common.notice.success("Currency successfully removed");
		jcost.currency.populateCurrencies();
	},
	deleteCurrenciesOnFail: function() { },

	
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
				jcost.currency.editCurrenciesOnSuccess,
				jcost.currency.editCurrenciesOnFail);
		jcost.common.callRest(restCall);
	},
	editCurrenciesOnSuccess: function(data) {
		if (!data) return;		
		jcost.currency.ui.fillDialog(data);	
		jcost.currency.ui.openDialog();
	},
	editCurrenciesOnFail: function() { },

	init: function() {	
		jcost.currency.populateCurrencies();	
	}
};

//*****************
//UI functions
jcost.currency.ui = {
	blockUI: function(){
		//$("#currencyName").val("");
		//$("#currencyLongName").val("");
	},
	unblockUI: function() {
		
	},
	openDialog: function() {
		dlgCurrency.open();
	},
	closeDialog: function() {
		dlgCurrency.close();
	},
	clearDialog: function() {
		$("#currencyId").val("");
		$("#currencyName").val("");
		$("#currencyLongName").val("");
	},
	fillDialog: function(data) {
		if (!data.id) return;
				
		$("#currencyId").val(data.id);
		$("#currencyName").val(data.shortName ? data.shortName : "");
		$("#currencyLongName").val(data.name ? data.name : "");
	}
		
};

//*****************
//Model functions
jcost.currency.processor = {
	currencyFromModal: function() {
		var currency = {
			id: ($.trim($("#currencyId").val()) == "" ? null : $.trim($("#currencyId").val())),
			name: $("#currencyLongName").val(),
			shortName: $("#currencyName").val()
		};
		return currency;
	}
		
};

