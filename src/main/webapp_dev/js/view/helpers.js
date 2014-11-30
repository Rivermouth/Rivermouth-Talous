(function(win, doc, bn, Holder, Info, Card, CardEditable, main, hbel) {
	
	function createNewFileButton(text, onClick) {
		var elem = hbel("button", {
			onclick: function() {
				onClick();
			}
		}, null, text);
		
		return elem;
	}
	
	bn.newFileHolder = function(className, newFileCardFn, buttonText) {
		var elem = new Holder(null, {"class": className});
		
		elem.getFiles = function() {};
		elem.saveFile = function() {};
		
		var elemBody = elem.body;
		elemBody.set(function() {
			this.updateList();
		});
		
		elemBody.updateList = function() {
			var self = this;
			
			if (!this.data) {
				elem.getFiles(function(data) {
					self.data = data;
					self.render();
				});
				return;
			}
			
			for (var i = 0, l = this.data.length; i < l; ++i) {
				this.add(newFileCardFn(this.data[i], elem.saveFile));
			}
		};
		
		elemBody.createNewFile = function() {
			this.add(newFileCardFn(null, elem.saveFile));
			this.render();
		};
		
		elem.footer.set(createNewFileButton(buttonText, function() {
			elemBody.createNewFile();
		}));
		
		return elem;
	};
	
	bn.newInfoHolder = function newInfoHolderFn(className, data, deepness) {
		var infoHolder = new Holder(data, {"class": "info " + className});
		infoHolder.save = function() {};
		
		var info = new Info(true, deepness);
		
		var saveButton = hbel("button", {
			onclick: function() {
				infoHolder.save();
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
	
	bn.newNoteCard = function newNoteCardFn(note, onSave) {
		if (!note) {
			note = {
				title: "",
				content: ""
			};
		}
		
		if (!note._converted && note.mimeType) {
			note.content = atob(note.content);
			note._converted = true;
		}
		
		var elem = new CardEditable(note, {"class": "note"});
		elem.header.set("{{title}}");
		elem.body.set("{{content}}");
		elem.footer.set(hbel("a", {
			onclick: function(evt) {
				evt.preventDefault();
				elem.toggleEditMode();
			},
			href: "#"
		}, null, "Edit..."));
		elem.onSave = onSave;
		
		return elem;
	};
	
	bn.newBillCard = function newNoteCardFn(bill, onSave) {
		if (!bill) {
			bill = {
				title: "",
				content: ""
			};
		}
		
		function setFieldTypeToFile(field) {
			field.type = "button";
			field.props.value = "Select file";
			field.props.onclick = function() {
				var self = this;
				bn.openFileFromDisk(".png", function(name, data) {
					self.value = "[" + name + "]";
					field.data.content = data;
				}, "readAsDataURL");
			};
		}
		
		var elem = new CardEditable(bill, {"class": "bill"});
		elem.header.set("{{title}}");
		elem.body.set(hbel("img", null, true, function() {
			this.element.src = atob(this.data.content);
		}));
		elem.footer.set(hbel("a", {
			onclick: function(evt) {
				evt.preventDefault();
				elem.toggleEditMode();
				
				setFieldTypeToFile(elem.form.field.content);
				elem.form.render();
			},
			href: "#"
		}, null, "Edit..."));
		elem.onSave = onSave;
		
		return elem;
	};
	
	bn.newNotesHolder = function() {
		var elem = bn.newFileHolder("notes", bn.newNoteCard, "New note");
		elem.tab.set("Notes");
		return elem;
	};
	
	bn.newBillsHolder = function() {
		var elem = bn.newFileHolder("bills", bn.newBillCard, "New bill");
		elem.tab.set("Bills");
		return elem;
	};
	
})(window, document, bn, bn.Holder, bn.Info, bn.Card, bn.CardEditable, bn.main, hbel);