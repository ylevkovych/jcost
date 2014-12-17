<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="ui-widget">
	<h3>Currencies</h3>
	<table id="tblCurrency" class="ui-widget ui-widget-content">
		<thead>
			<th>Short Name</th>
			<th>Full Name</th>
		</thead>
		<tbody>
		</tbody>	
	</table>
	<br>
	<button id="addCurrency">Add Currency</button>
</div>

<div id="dlgCurrency">
	<form>
		<fieldset>
			<input hidden="hidden" id="currencyId"/>
			<label for="currencyNane">Short Name</label>
			<input id="currencyNane" name="currencyName" class="text ui-widget-content ui-corner-all"/>
			<label for="curencyLongName">Long Name</label>
			<input id="curencyLongName" name="curencyLongName" class="text ui-widget-content ui-corner-all"/>
		</fieldset>
	</form>
</div>

<script src="html/js/common.js"/>
<script src="html/js/currency.js"/>

<script type="text/javascript">

$(document).ready(function(){
	
	$("#dlgCurrency").dialog({
		autoOpen: false,
		//height: 300,
		//width: 350,
		modal: true,
		buttons: {
			"Save": function() {				
				jcost.currency.saveCurrency();				
			},
			Cancel: function() {
				 $("#dlgCurrency").dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
	
	$("#addCurrency").on("click", function() {
		 $("#dlgCurrency").dialog("open");
	});
	
	jcost.currency.init();
});

</script>
