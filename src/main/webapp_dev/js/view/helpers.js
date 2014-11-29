(function(win, doc, bn, Holder, Info, Card, CardEditable, main, hbel) {	
	
	bn.newInfoHolder = function newInfoHolderFn(className, data, deepness) {
		var infoHolder = new Holder(data, {"class": "info " + className});
		infoHolder.onSave = function() {};
		
		var info = new Info(true, deepness);
		
		var saveButton = hbel("button", {
			onclick: function() {
				infoHolder.onSave();
			}
		}, null, "Save");
		
		infoHolder.tab.set("Info");
		infoHolder.body.set(info);
		infoHolder.footer.set(saveButton);
		
		return infoHolder;
	};
	
	bn.newClientCard = function newClientCardFn(data) {
		var elem = new Card(data, {
			"class": "client", 
			onclick: function() {
				main.open("clients/" + data.id);
			}
		});
		
		elem.header.set("{{name}}");
		
		elem.body.set([
			"<div>Projects: {{projects.length}}</div>" +
			"<div>Notes: {{notes.length}}</div>"
		]);
		
		return elem;
	};
	
	bn.newNoteCard = function newNoteCardFn(data) {
		data = {
			title: "Muista muistaa",
			content: "Moadm oa maod mawdmoawomawmoaw dawod awmdo ad w dawawawawaw awd aawd awmaio moiaw moimoimo awimoim awiomw iomaw "
		};
		
		var elem = new CardEditable(data, {"class": "note"});
		elem.header.set("{{title}}");
		elem.body.set("{{content}}");
		elem.footer.set(hbel("a", {
			onclick: function(evt) {
				evt.preventDefault();
				elem.toggleEditMode();
			},
			href: "#"
		}, null, "Edit..."));
		
		return elem;
	};
	
})(window, document, bn, bn.Holder, bn.Info, bn.Card, bn.CardEditable, bn.main, hbel);