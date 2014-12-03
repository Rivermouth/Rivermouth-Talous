(function(win, doc, Holder, Info, api, main, bn, Card, ClientsCard, TabSwitcher, hbel) {
		
	function DashBoard() {
		var clientsCard = new ClientsCard();
		
		return hbel("div", null, null, clientsCard);
	}
	
	function EmployeeList() {
		this.element = new Holder();
		this.element.header.set(
			hbel("button", {
				onclick: function() {
					main.open("employees/new");
				}
			}, null, "New employee")
		);
		this.generateList();
		return this.element;
	}
	EmployeeList.prototype.generateList = function(data) {
		var self = this;
		
		if (!data) {
			api.employees.list().execute(function(resp) {
				self.generateList(resp.data);
			});
			return;
		}
		
		for (var i = 0, l = data.length; i < l; ++i) {
			this.element.body.add(this.newEmployeeCard(data[i]));
		}
		this.element.body.render();
	};
	EmployeeList.prototype.newEmployeeCard = function newEmployeeCardFn(data) {
		var elem = new Card(data, {
			"class": "employee", 
			onclick: function() {
				main.open("employees/" + data.id);
			}
		});
		
		elem.header.set("{{name.firstName}} {{name.lastName}}");
		
		elem.body.set([
			"<div>{{role}}</div>"
		]);
		
		return elem;
	};
	
	function ClientList() {
		this.element = new Holder();
		this.element.header.set(
			hbel("button", {
				onclick: function() {
					main.open("clients/new");
				}
			}, null, "New client")
		);
		this.generateList();
		return this.element;
	}
	ClientList.prototype.generateList = function(data) {
		var self = this;
		
		if (!data) {
			api.clients.list().execute(function(resp) {
				self.generateList(resp.data);
			});
			return;
		}
		
		for (var i = 0, l = data.length; i < l; ++i) {
			this.element.body.add(this.newClientCard(data[i]));
		}
		this.element.body.render();
	};
	ClientList.prototype.newClientCard = function newClientCardFn(data) {
		var elem = new Card(data, {
			"class": "client", 
			onclick: function() {
				main.open("clients/" + data.id);
			}
		});
		
		elem.header.set("{{name}}");
		
		elem.body.set([
			"<div>Projects: {{projects.length}}</div>"
		]);
		
		return elem;
	};
	
	function mainView(user) {
		
		var dashBoard = new DashBoard();
		var clientList = new ClientList();
		var employeeList = new EmployeeList();
		
		var elem = new TabSwitcher([
			{
				title: "Home",
				load: function(parentElem) {
					parentElem.set(dashBoard);
				}
			},
			{
				title: "Clients",
				load: function(parentElem) {
					parentElem.set(clientList);
				}
			},
			{
				title: "Employees",
				load: function(parentElem) {
					parentElem.set(employeeList);
				}
			},
			{
				title: "Files",
				load: function(parentElem) {
					
				}
			}
		], {"class": "main-view"});
		
		
		main.container.appendChild(elem.render());
	}
	
	bn.mainView = mainView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, bn, bn.Card, bn.ClientsCard, bn.TabSwitcher, hbel);
