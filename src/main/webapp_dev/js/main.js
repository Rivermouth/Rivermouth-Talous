(function(win, doc, Holder, Card, Info, user) {
	
	var body = doc.body;
	
	var container = doc.getElementById("container");
	
	function newClientCard(data) {
		var card = new Card({
			className: "client", 
			onClick: function() {
				open("clients/");
			}
		});
		card.header.text(data.name);
		card.body.html(
			"<div>Projects: " + data.projects.length + "</div>" +
			"<div>Notes: " + data.notes.length + "</div>"
		);
		return card;
	}
	
	function newInfoHolder(className, data, deepness) {
		var infoHolder = new Holder({className: "info " + className});
		infoHolder.tab.text("Info");
		
		var info = new Info(data, deepness);
		
		var saveButton = doc.createElement("button");
		saveButton.textContent = "Save";
		
		infoHolder.body.add(info);
		infoHolder.footer.add(saveButton);
		
		return infoHolder;
	}
	
	function mainView() {
		var clients = user.data.clients;
		
		var infoHolder = newInfoHolder("user-info", user.data, 0);
	
		var clientsHolder = new Holder({className: "clients"});
		clientsHolder.tab.text("Clients");

		for (var i = 0, l = clients.length; i < l; ++i) {
			clientsHolder.body.add(newClientCard(clients[i]));
		}

		var notesHolder = new Holder({className: "notes"});
		notesHolder.tab.text("Notes");

		for (var i = 0; i < 7; i++) {
			var card = new Card({clasName: "note"});
			card.header.text("Muista laskutus");
			notesHolder.body.add(card);
		}

		container.appendChild(infoHolder.render());
		container.appendChild(clientsHolder.render());
		container.appendChild(notesHolder.render());
		
	}
	
	function clientView(clientId) {
	
		var infoHolder = newInfoHolder("client-info", user.data.clients[0]);
		
		var notesHolder = new Holder({className: "notes"});
		notesHolder.tab.text("Notes");

		for (var i = 0; i < 7; i++) {
			var card = new Card({clasName: "note"});
			card.header.text("Muista laskutus");
			notesHolder.body.add(card);
		}
		
		container.appendChild(infoHolder.render());
		container.appendChild(notesHolder.render());
	}
	
	function open(path) {
		location.hash = "#/" + path;
	}
	
	function switchView() {
		var path = location.hash.substr(1);
		if (path.charAt(0) == "/") path = path.substring(1);
		
		var pathParts = path.split("/");
		
		container.innerHTML = "";
		
		switch (pathParts[0]) {
			case "clients":
				clientView();
				break;
			default:
				mainView();
				break;
		}
	}
	
	win.addEventListener("hashchange", function(evt) {
		switchView();
	}, false);
	
	
	switchView();
	
})(window, document, bn.Holder, bn.Card, bn.Info, bn.user);
