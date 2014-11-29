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
	
	bn.newNoteCard = function newClientCardFn(data) {
		var card = new Card({
			className: "note", 
			getEditLink: function() {
				var self = this;
				var elem = doc.createElement("a");
				elem.textContent = "Edit";
				elem.onclick = function() {
					self.element.classList.add("opened");
					card.toggleEditMode();
				};
				return elem;
			},
			getSaveLink: function() {
				var self = this;
				var elem = doc.createElement("a");
				elem.textContent = "Save";
				elem.onclick = function() {
					self.element.classList.remove("opened");
					card.toggleEditMode();
				};
				return elem;
			},
			getCancelLink: function() {
				var self = this;
				var elem = doc.createElement("a");
				elem.textContent = "Cancel";
				elem.onclick = function() {
					self.element.classList.remove("opened");
					card.toggleEditMode();
				};
				return elem;
			},
			onClick: function(card, elem) {
			},
			onRender: function(card, data) {
				card.header.text(data.title);
				card.body.text(data.content);
				card.footer.add(card.getEditLink());
			},
			onEdit: function(card, info) {
				info.field.content.type = "textarea";
				card.footer.add(card.getSaveLink());
			}
		}, {
			title: "Muista muistaa",
			content: "Moadm oa maod mawdmoawomawmoaw dawod awmdo ad w dawawawawaw awd aawd awmaio moiaw moimoimo awimoim awiomw iomaw "
		});
		return card;
	};
	
})(window, document, bn, bn.Holder, bn.Info, bn.Card, bn.main);