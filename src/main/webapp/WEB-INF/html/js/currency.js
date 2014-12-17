var jcost = jcost || {};
jcost.currency = jcost.currency || {};

//***************
// get currencies
jcost.currency.populateCurrencies = function() {
	var restCall = 
		new jcost.common.RestCall(
			jcost.common.Urls.getCurrencies,
			jcost.common.Methods.GET,
			null,
			null,
			jcost.currency.populateCurrenciesOnSuccess,
			jcost.currency.populateCurrenciesOnFail);
	jcost.common.callRest(restCall);
};
jcost.currency.populateCurrenciesOnSuccess = function(data) {

};
jcost.currency.populateCurrenciesOnFail = function() {

};

//***************
//save currencies
jcost.currency.saveCurrency = function() {
	
	var currency = {
		name: $("#currencyNane").val(),
		shortName: $("#curencyLongName").val()
	};
	jcost.currency.ui.blockUI();
	var restCall = 
		new jcost.common.RestCall(
			jcost.common.Urls.saveCurrency,
			jcost.common.Methods.POST,
			null,
			currency,
			jcost.currency.saveCurrenciesOnSuccess,
			jcost.currency.saveCurrenciesOnFail);
	jcost.common.callRest(restCall);
};
jcost.currency.saveCurrenciesOnSuccess = function() {
	jcost.currency.ui.closeDialog();
	jcost.currency.populateCurrencies();
};
jcost.currency.saveCurrenciesOnFail = function() {
	
};


//****************
//UI functions
jcost.currency.ui = {
	blockUI: function(){
		
	},
	closeDialog: function() {
		$("#dlgCurrency").dialog( "close" );
	}
};



jcost.currency.init = function() {	
	jcost.currency.populateCurrencies();	
};