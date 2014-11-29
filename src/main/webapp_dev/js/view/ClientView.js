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
	
	function showClient(data) {
		var infoHolder = bn.newInfoHolder("client-info", data, -1);
		infoHolder.onSave = function() {
			bn.api.clients.save(data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		var notesHolder = new Holder({className: "notes"});
		notesHolder.tab.set("Notes");

		for (var i = 0; i < 7; i++) {
			var card = bn.newNoteCard();
			notesHolder.body.add(card);
		}
		
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
				showClient(resp);
			},
			showError
		);
	}
	
	bn.clientView = clientView;
	
})(window, document, bn.Holder, bn.Info, bn.Card, bn.api, bn.main, bn);