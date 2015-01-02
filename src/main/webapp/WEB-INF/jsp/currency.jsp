<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="ui-widget">
	<h3>Currencies</h3>
	<div style="padding-bottom: 15px; padding-top: 15px;">
		<button id="addCurrency">Add Currency</button>
	</div>
	<table id="tblCurrency" class="ui-widget ui-widget-content">
		<thead>
			<th>ID</th>
			<th>Short Name</th>
			<th>Full Name</th>
			<th></th>
			<th></th>
		</thead>
		<tbody>
		</tbody>	
	</table>
</div>

<div id="dlg-confirm-delete-currency" title="Delete the currency?" style="display: none;">
	These item will be permanently deleted and cannot be recovered. Are you sure?
</div>

<div id="dlgCurrency" style="display: none;"> 	
	<input hidden="hidden" id="currencyId"/> <br>
	<label for="currencyName">Short Name</label> <br>
	<input id="currencyName" name="currencyName" class="text ui-widget-content ui-corner-all"/> <br><br>
	<label for="currencyLongName">Long Name</label> <br>
	<input id="currencyLongName" name="currencyLongName" class="text ui-widget-content ui-corner-all"/> <br><br>
	<button class="btnCurrencySave">Save</button>
	<button class="btnCurrencySaveCancel">Cancel</button> 
</div>

<script type="text/javascript">
// Currenciy env. variables
var dtCurrency;
var dlgCurrency;
</script>
<!--

//-->
</script>
<script src="html/js/common.js"/>
<script src="html/js/currency.js"/>

<script type="text/javascript">

$(document).ready(function(){	
	
	dlgCurrency = new jBox('Modal', {
	    width: 400,
	    height: 300,
	    //attach: $('#myModal'),
	    title: 'Currency',
	    content: $("#dlgCurrency")
	});
	
	$("#addCurrency").on("click", function() {
		dlgCurrency.open();
	});
	
	dtCurrency = $('#tblCurrency').DataTable({
		"processing": true,
        "fnCreatedRow": function( nRow, data, iDataIndex ) {
	        $(nRow).attr('id', data.id);
	    },
        "ajax": {
        	url: jcost.common.Urls.getCurrencies,
        	type: jcost.common.Methods.GET,
        	dataSrc: ""
        },
        "columns": [
            { 
            	data: "id",
            	visible: false
            },
        	{ data: "shortName" },
        	{ data: "name" },
        	{
        		data: null,
        		defaultContent: "<button class='currency-btn-edit'>Edit</button>"
        	},
        	{
        		data: null,
        		defaultContent: "<button class='currency-btn-delete'>Delete</button>"
        	}
        ]
	});
	
	$(document).on("click", ".btnCurrencySave", function(){
		jcost.currency.saveCurrency();
	});
	$(document).on("click", ".btnCurrencySaveCancel", function(){
		jcost.currency.ui.closeDialog();
	});	
	
	$(document).on("click", ".currency-btn-edit", function(){
		var tr = $(this).closest("tr");
		jcost.currency.editCurrency(tr.prop("id"));		
	});
	
	
	$(document).on("click", ".currency-btn-delete", function(){
		var tr = $(this).closest("tr");
		jcost.common.ui.confirmDialog(
			"dlg-confirm-delete-currency", 
			jcost.currency.deleteCurrency, 
			tr.prop("id")
		);
	});
	
});

</script>
