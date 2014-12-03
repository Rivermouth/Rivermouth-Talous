(function(win, doc, Holder, Info, Card, TabSwitcher, api, main, bn, hbel) {
	
	function newEmptyClientData() {
		return {
			name: "",
			vatNumber: "",
			address: {
				street: "",
				zip: "",
				city: "",
				country: ""
			}
		};
	}
	
	bn.clientView = function(id) {
		var view = new bn.EntityView("client", api.clients);
		
		view.getEmptyEntityData = function() {
			return newEmptyClientData();
		};
		
		view.openView(id);
	};
	
})(window, document, bn.Holder, bn.Info, bn.Card, bn.TabSwitcher, bn.api, bn.main, bn, hbel);