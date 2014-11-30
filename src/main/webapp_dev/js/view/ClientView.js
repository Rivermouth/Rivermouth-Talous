(function(win, doc, Holder, Info, Card, api, main, bn) {
	
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
	
	function createNotesView(client) {
		var elem = bn.newNotesHolder();
		
		elem.getFiles = function(callback) {
			api.clients.notes.list(client).execute(function(resp) {
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			api.clients.notes.save(client, data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		return elem;
	}
	
	function showClient(client) {
		var infoHolder = bn.newInfoHolder("client-info", client, -1);
		var notesHolder = createNotesView(client);
		
		infoHolder.tab.set("Client");
		infoHolder.save = function() {
			api.clients.save(this.data).execute(function(resp) {
				console.log(resp);
				main.open("clients/" + resp.data.id);
			});
		};
		
		main.container.appendChild(infoHolder.render());
		main.container.appendChild(notesHolder.render());
	}
	
	function showError(data) {
		main.container.textContent = JSON.stringify(data);
	}
	
	function clientView(clientId) {
		if (!clientId || clientId == "new") {
			showClient(newEmptyClientData());
			return;
		}
		
		api.clients.get(clientId).execute(
			function(resp) {
				showClient(resp.data);
			},
			showError
		);
	}
	
	bn.clientView = clientView;
	
})(window, document, bn.Holder, bn.Info, bn.Card, bn.api, bn.main, bn);