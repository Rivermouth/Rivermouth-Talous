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
		
		elem.header.set(createNewFileButton(buttonText, function() {
			elemBody.createNewFile();
		}));
		
		return elem;
	};
	
	bn.newInfoHolder = function newInfoHolderFn(className, data, deepness) {
		var infoHolder = new Holder(data, {"class": "info " + className});
		infoHolder.save = function() {};
		
		infoHolder.info = new Info(true, deepness);
		
		var saveButton = hbel("button", {
			onclick: function() {
				infoHolder.save();
			}
		}, null, "Save");
		
		infoHolder.header.set(saveButton);
		infoHolder.body.set(infoHolder.info);
		
		return infoHolder;
	};
	
	bn.newNoteCard = function newNoteCardFn(note, onSave) {
		if (!note) {
			note = {
				name: "",
				content: ""
			};
		}
		
		var elem = new CardEditable(note, {"class": "note"});
		elem.header.set("{{name}}");
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
	
	bn.newFileCard = function newNoteCardFn(data, onSave) {
		if (!data) {
			data = {
				name: "",
				content: ""
			};
		}
		
		function setFieldTypeToFile(field) {
			field.type = "file";
		}
		
		var elem = new CardEditable(data, {"class": "file"});
		elem.header.set("{{name}}");
		elem.body.set(hbel("img", null, true, function() {
			this.element.src = this.data.thumbnailUrl;
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
		return elem;
	};
	
	bn.newFilesHolder = function() {
		var elem = bn.newFileHolder("files", bn.newFileCard, "New file");
		return elem;
	};
	
})(window, document, bn, bn.Holder, bn.Info, bn.Card, bn.CardEditable, bn.main, hbel);