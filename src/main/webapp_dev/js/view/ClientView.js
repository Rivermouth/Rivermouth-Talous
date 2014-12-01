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
	
	function createNotesHolder(client) {
		var elem = bn.newNotesHolder();
		
		elem.getFiles = function(callback) {
			api.clients.notes.list("me", client).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			api.clients.notes.save("me", client, data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		return elem;
	}
	
	function createFilesHolder(client) {
		var elem = bn.newFilesHolder();
		
		elem.getFiles = function(callback) {
			api.clients.files.list("me", client).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			this.form.element.method = "POST";
			this.form.element.action = api.files.savePath("me", "images", client.id);
			this.form.element.enctype = "multipart/form-data";
			this.form.element.submit();
			return;
			
			api.users.files.save(user, data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		return elem;
	}
	
	function showClient(client) {
		var infoHolder = bn.newInfoHolder("client-info", (client || newEmptyClientData()), -1);
		
		infoHolder.tab.set("Client");
		infoHolder.save = function() {
			api.clients.save(this.data).execute(function(resp) {
				console.log(resp);
				main.open("clients/" + resp.data.id);
			});
		};
		
		main.container.appendChild(infoHolder.render());
		
		if (client) {
			var notesHolder = createNotesHolder(client);
			var filesHolder = createFilesHolder(client);

			main.container.appendChild(notesHolder.render());
			main.container.appendChild(filesHolder.render());
		}
	}
	
	function showError(data) {
		main.container.textContent = JSON.stringify(data);
	}
	
	function clientView(clientId) {
		if (!clientId || clientId == "new") {
			showClient();
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