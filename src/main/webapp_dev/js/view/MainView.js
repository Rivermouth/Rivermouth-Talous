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
	
	function createNewEmployeeButton() {
		var elem = hbel("button", {
			onclick: function() {
				main.open("employees/new");
			}
		}, null, "New employee");
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
	
	function addEmployeesHolder() {
		var elem = new Holder();
		
		elem.tab.set("Employees");
		
		var elemBody = new hbel("div", null, null, function() {
			this.updateList();
		});
		
		elemBody.updateList = function() {
			var self = this;
			
			if (!this.data) {
				api.employees.list().execute(
					function(resp) {
						self.data = resp.data;
						self.render();
					},
					function(){}
				);
				return;
			}
			
			for (var i = 0, l = this.data.length; i < l; ++i) {
				this.add(bn.newEmployeeCard(this.data[i]));
			}
		};
		
		elem.body.set(elemBody);
		elem.footer.set(createNewEmployeeButton());
		
		main.container.appendChild(elem.render());
	}
	
	function addNotesHolder(user) {
		var elem = bn.newNotesHolder();
		
		elem.getFiles = function(callback) {
			api.users.notes.list(user).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			api.users.notes.save(user, data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		main.container.appendChild(elem.render());
	}
	
	function addFilesHolder(user) {
		var elem = bn.newFilesHolder();
		
		elem.getFiles = function(callback) {
			api.users.files.list(user).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			this.form.element.method = "POST";
			this.form.element.action = api.files.savePath("me", "images");
			this.form.element.enctype = "multipart/form-data";
			this.form.element.submit();
			return;
			
			api.users.files.save(user, data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		main.container.appendChild(elem.render());
	}
	
	function mainView(user) {
		if (!user) {
			api.me().execute(
				function(resp) {
					console.log(resp);
					mainView(resp.data);
				},
				function(errorResp) {
					console.warn(errorResp);
					main.open("login");
				}
			);
			return;
		}
		
		addInfoHolder(user);
		addClientsHolder(user);
		addEmployeesHolder(user);
		addNotesHolder(user);
		addFilesHolder(user);
	}
	
	bn.mainView = mainView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, bn, bn.Card, hbel);