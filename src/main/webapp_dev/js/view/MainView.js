(function(win, doc, Holder, Info, api, main, bn, Card) {
		
	function addInfoHolder(data) {
		var infoHolder = bn.newInfoHolder("user-info", data, -1);
		main.container.appendChild(infoHolder.render());
	}
	
	function createNewClientButton() {
		var elem = doc.createElement("button");
		elem.textContent = "New client";
		elem.onclick = function() {
			main.open("clients/new");
		};
		return elem;
	}
	
	function addClientsHolder() {
		var clientsHolder = new Holder({className: "clients"});
		clientsHolder.tab.text("Clients");
		
		clientsHolder.footer.add(createNewClientButton());
		
		main.container.appendChild(clientsHolder.render());
		
		api.clients.list().execute(
			function(resp) {
				var clients = resp.data;
				for (var i = 0, l = clients.length; i < l; ++i) {
					clientsHolder.body.add(bn.newClientCard(clients[i]));
				}
				clientsHolder.update();
			},
			function(){}
		);
	}
	
	function addNotesHolder() {
		var notesHolder = new Holder({className: "notes"});
		notesHolder.tab.text("Notes");

		for (var i = 0; i < 7; i++) {
			var card = new Card({clasName: "note"});
			card.header.text("Muista laskutus");
			notesHolder.body.add(card);
		}

		main.container.appendChild(notesHolder.render());
	}
	
	function mainView(user) {
		if (!user) {
			api.me().execute(
				function(resp) {
					console.log(resp);
					mainView(resp);
				},
				function(errorResp) {
					console.warn(errorResp);
					main.open("login");
				}
			);
			return;
		}
		
		addInfoHolder(user.data);
		addClientsHolder();
		addNotesHolder();
	}
	
	bn.mainView = mainView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, bn, bn.Card);