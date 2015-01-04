var jcost = jcost || {};

jcost.resident = {
	
	populateResidents: function() {
		dtResident.ajax.reload();
	},
		
	saveResident: function() {
		var resident = jcost.resident.processor.residentFromModal();	
		var method = (resident && resident.id) ? jcost.common.Methods.PUT : jcost.common.Methods.POST;

		jcost.resident.ui.blockUI();
		var restCall = 
			new jcost.common.RestCall(
				jcost.common.Urls.saveResident,
				method,
				null,
				resident,
				jcost.resident.saveResidentOnSuccess,
				jcost.resident.saveResidentOnFail);
		jcost.common.callRest(restCall);
	},
	saveResidentOnSuccess: function() {
		jcost.resident.ui.closeDialog();
		jcost.resident.ui.clearDialog();
		jcost.common.notice.success("Resident successfully saved");
		jcost.resident.populateResidents();
		jcost.resident.saveResidentPostProcess();
	},
	saveResidentOnFail: function() {
		jcost.resident.saveResidentPostProcess();
	},
	saveResidentPostProcess: function() {
		jcost.resident.ui.unblockUI();
	},
	
	deleteResident: function(id) {
		if (!id && id !== 0) {
			return;
		}
		
		var params = JSON.stringify({
			residentId: id
		});		
		
		var restCall = 
			new jcost.common.RestCall(
				jcost.common.Urls.removeResident,
				jcost.common.Methods.DELETE,
				params,
				null,
				jcost.resident.deleteResidentOnSuccess,
				jcost.resident.deleteResidentOnFail);
		jcost.common.callRest(restCall);
	},
	deleteResidentOnSuccess: function() {
		jcost.common.notice.success("Resident successfully removed");
		jcost.resident.populateResidents();
	},
	deleteResidentOnFail: function() {	},
	
	editResident: function(id) {
		if (!id && id !== 0) {
			return;
		}
		
		var params = JSON.stringify({
			residentId: id
		});		
		
		var restCall = 
			new jcost.common.RestCall(
				jcost.common.Urls.getResident,
				jcost.common.Methods.GET,
				params,
				null,
				jcost.resident.editResidentOnSuccess,
				jcost.resident.editResidentOnFail);
		jcost.common.callRest(restCall);
	},
	editResidentOnSuccess: function(data) {
		if (!data) return;		
		jcost.resident.processor.residentToModal(data);	
		jcost.resident.ui.openDialog();		
	},
	editResidentOnFail: function() {
		
	}		
};

//*****************
//UI functions
jcost.resident.ui = {
	blockUI: function(){
		
	},
	unblockUI: function() {
		
	},
	openDialog: function() {
		$( "#dlgResident" ).dialog("open");;
	},
	closeDialog: function() {
		$( "#dlgResident" ).dialog("close");
	},
	clearDialog: function() {
		$("#residentId").val("");
		$("#residentName").val("");
		$("#residentComment").val("");
	}
		
};

//*****************
//Model to view, view to model functions
jcost.resident.processor = {
	residentFromModal: function() {
		var resident = {
			id: ($.trim($("#residentId").val()) == "" ? null : $.trim($("#residentId").val())),
			name: $("#residentName").val(),
			comment: $("#residentComment").val()
		};
		return resident;
	},
	residentToModal: function(data) {
		if (!data.id) return;
				
		$("#residentId").val(data.id);
		$("#residentName").val(data.name ? data.name : "");
		$("#residentComment").val(data.comment ? data.comment : "");
	}
		
};