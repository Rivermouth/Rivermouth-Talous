(function(win, doc, Holder, Info, Card, TabSwitcher, api, main, bn, hbel) {
	
	function newEmptyEntityData() {
		return {
			name: {
				firstName: "",
				lastName: ""
			},
			role: "",
			tel: "",
			email: "",
			address: {
				street: "",
				zip: "",
				city: "",
				country: ""
			}
		};
	}
	
	bn.employeeView = function(id) {
		var view = new bn.EntityView("employee", api.employees);
		
		view.getEmptyEntityData = function() {
			return newEmptyEntityData();
		};
		
		view.getMainTabName = function() {
			return this.data.name.firstName + " " + this.data.name.lastName;
		};
		
		view.openView(id, function() {
			view.infoHolder.info.field.email.type = "email";
			view.infoHolder.info.render();
		});
		
	};
	
})(window, document, bn.Holder, bn.Info, bn.Card, bn.TabSwitcher, bn.api, bn.main, bn, hbel);
