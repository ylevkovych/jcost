<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="ui-widget">
	<h3>Residents</h3>
	<div style="padding-bottom: 15px; padding-top: 15px;">
		<button id="addResident">Add Resident</button>
	</div>
	<table id="tblResident" class="ui-widget ui-widget-content">
		<thead>
			<th>ID</th>
			<th>Name</th>
			<th>Comment</th>
			<th></th>
			<th></th>
		</thead>
		<tbody>
		</tbody>	
	</table>
</div>

<div id="dlg-confirm-delete-resident" title="Delete the resident?" style="display: none;">
	These item will be permanently deleted and cannot be recovered. Are you sure?
</div>

<div id="dlgResident" style="display: none;" title="Resident"> 	
	<form>
		<fieldset>
			<input hidden="hidden" id="residentId"/> 
			<label for="residentName">Name</label>
			<input id="residentName" name="residentName" class="text ui-widget-content ui-corner-all"/> 
			<label for="residentComment">Comment</label> 
			<input id="residentComment" name="residentComment" class="text ui-widget-content ui-corner-all"/>
		</fieldset>
	</form> 
</div>

<script type="text/javascript">
// Resident env. variables
var dtResident;
</script>

<script src="html/js/common.js"/>
<script src="html/js/resident.js"/>

<script type="text/javascript">

$(document).ready(function(){ 
	
	$( "#dlgResident" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			"Save": function(e) {
				e.preventDefault();
				jcost.resident.saveResident();
			},
			Cancel: function(e) {	
				e.preventDefault();
				jcost.resident.ui.clearDialog();
				jcost.resident.ui.closeDialog();
			}
		},
		close: function() {	}
	});

	dtResident = $('#tblResident').DataTable({
		"processing": true,
        "fnCreatedRow": function( nRow, data, iDataIndex ) {
	        $(nRow).attr('id', data.id);
	    },
        "ajax": {
        	url: jcost.common.Urls.getResidents,
        	type: jcost.common.Methods.GET,
        	dataSrc: ""
        },
        "columns": [
            { 
            	data: "id",
            	visible: false
            },
        	{ data: "name" },
        	{ data: "comment" },
        	{
        		data: null,
        		defaultContent: "<button class='resident-btn-edit'>Edit</button>"
        	},
        	{
        		data: null,
        		defaultContent: "<button class='resident-btn-delete'>Delete</button>"
        	}
        ]
	});
	
	$("#addResident").on("click", function(e) {
		if (jcost.common.ui.isHandled(e)) return;
		jcost.resident.ui.openDialog();
	});
		
	$(document).on("click", ".resident-btn-edit", function(e){
		if (jcost.common.ui.isHandled(e)) return;
		var tr = $(this).closest("tr");
		jcost.resident.editResident(tr.prop("id"));		
	});
	
	$(document).on("click", ".resident-btn-delete", function(e){
		if (jcost.common.ui.isHandled(e)) return;
		var tr = $(this).closest("tr");
		jcost.common.ui.confirmDialog(
			"dlg-confirm-delete-resident", 
			jcost.resident.deleteResident, 
			tr.prop("id")
		);
	});
	
});