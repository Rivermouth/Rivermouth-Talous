(function(win, doc, Holder, Info, api, main, bn, Card, hbel) {
		
	function addInfoHolder(data) {
		var infoHolder = bn.newInfoHolder("user-info", data, -1);
		infoHolder.onSave = function() {
			api.users.save(this.data).execute(function(resp) {
				console.log(resp);
			});
		};
		main.container.appendChild(infoHolder.render());
	}
	
	function createNewClientButton() {
		var elem = hbel("button", {
			onclick: function() {
				main.open("clients/new");
			}
		}, null, "New client");
		return elem;
	}
	
	function createNewNoteButton() {
		var elem = hbel("button", {
			
		}, null, "New note");
		return elem;
	}
	
	function addClientsHolder() {
		var elem = new Holder();
		
		elem.tab.set("Clients");
		
		var elemBody = new hbel("div", null, null, function() {
			this.updateList();
		});
		
		elemBody.updateList = function() {
			var self = this;
			
			if (!this.data) {
				api.clients.list().execute(
					function(resp) {
						self.data = resp.data;
						self.render();
					},
					function(){}
				);
				return;
			}
			
			for (var i = 0, l = this.data.length; i < l; ++i) {
				this.add(bn.newClientCard(this.data[i]));
			}
		};
		
		elem.body.set(elemBody);
		elem.footer.set(createNewClientButton());
		
		main.container.appendChild(elem.render());
	}
	
	function addNotesHolder() {
		var elem = new Holder();
		elem.tab.set("Notes");
		elem.body.set(new hbel("div", null, null, function() {
			for (var i = 0; i < 7; i++) {
				this.add(bn.newNoteCard());
			}
		}));
		elem.footer.set(createNewNoteButton());
		main.container.appendChild(elem.render());
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
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, bn, bn.Card, hbel);