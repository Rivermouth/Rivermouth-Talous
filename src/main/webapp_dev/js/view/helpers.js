(function(win, doc, bn, Holder, Info, Card, main) {	
	
	bn.newInfoHolder = function newInfoHolderFn(className, data, deepness) {
		var infoHolder = new Holder({className: "info " + className});
		infoHolder.tab.text("Info");
		
		infoHolder.onSave = function() {};
		
		var info = new Info(data, deepness);
		
		var saveButton = doc.createElement("button");
		saveButton.textContent = "Save";
		saveButton.onclick = function() {
			infoHolder.onSave();
		};
		
		infoHolder.body.add(info);
		infoHolder.footer.add(saveButton);
		
		return infoHolder;
	};
	
	bn.newClientCard = function newClientCardFn(data) {
		var card = new Card({
			className: "client", 
			onClick: function() {
				main.open("clients/" + data.id);
			}
		});
		card.header.text(data.name);
		card.body.html(
			"<div>Projects: " + data.projects.length + "</div>" +
			"<div>Notes: " + data.notes.length + "</div>"
		);
		return card;
	};
	
})(window, document, bn, bn.Holder, bn.Info, bn.Card, bn.main);