(function(win, doc, Holder, Info, Card, TabSwitcher, main, bn, hbel) {
	
	function BaseEntityView(entityName, api) {
		var self = this;
		
		this.entityName = entityName;
		this.api = api;
		this.data = null;
		
		this.tabs = [];
	}
	
	BaseEntityView.prototype.getEmptyEntityData = function getEmptyEntityDataFn() { return {}; };
	
	BaseEntityView.prototype.newNotesHolder = function newNotesHolderFn() {
		var self = this;
		var elem = bn.newNotesHolder();
		
		elem.getFiles = function(callback) {
			self.api.notes.list("me", self.data).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			self.api.notes.save("me", self.data, data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		return elem;
	};
	
	BaseEntityView.prototype.newFilesHolder = function newFilesHolderFn() {
		var self = this;
		var elem = bn.newFilesHolder();
		
		elem.getFiles = function(callback) {
			self.api.files.list("me", self.data).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			this.form.element.method = "POST";
			this.form.element.action = bn.api.files.savePath("me", "images", self.data.id);
			this.form.element.enctype = "multipart/form-data";
			this.form.element.submit();
			return;
			
			self.api.files.save("me", data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		return elem;
	}
	
	BaseEntityView.prototype.newInfoHolder = function newInfoHolderFn() {
		var self = this;
		var elem = bn.newInfoHolder("client-info", self.data, -1);
		
		elem.save = function() {
			self.api.save(this.data).execute(function(resp) {
				console.log(resp);
				main.open(self.entityName + "s/" + resp.data.id);
			});
		};
		
		return elem;
	}
	
	BaseEntityView.prototype.getMainTabName = function getMainTabNameFn() {
		return this.data.name;
	};
	
	BaseEntityView.prototype.showEntity = function showEntityFn() {
		var self = this;
		
		var isNewEntity = this.data ? false : true;
		
		if (isNewEntity) {
			this.data = this.getEmptyEntityData();
		}

		this.infoHolder = this.newInfoHolder();
		this.notesHolder = this.newNotesHolder();
		this.filesHolder = this.newFilesHolder();
		
		if (isNewEntity) {
			this.tabs = [
				{
					title: "New client",
					load: function(parentElem) {
						parentElem.set(self.infoHolder);
					}
				}
			];
		}
		else {
			this.tabs = [
				{
					title: this.getMainTabName(),
					load: function(parentElem) {
					}
				},
				{
					title: "Notes",
					load: function(parentElem) {
						parentElem.set(self.notesHolder);
					}
				},
				{
					title: "Files",
					load: function(parentElem) {
						parentElem.set(self.filesHolder);
					}
				},
				{
					title: "Info",
					load: function(parentElem) {
						parentElem.set(self.infoHolder);
					}
				}
			];
		}
		
		var elem = new TabSwitcher(this.tabs, {"class": "main-view"});

		elem.header.set(
			hbel("div", null, null, [
				hbel("a", {onclick: function() {
					main.open("");
				}}, null, "< Back to dashboard")
			])
		);

		main.container.appendChild(elem.render());
	}
	
	BaseEntityView.prototype.showError = function showErrorFn(data) {
		main.container.textContent = JSON.stringify(data);
	}
	
	BaseEntityView.prototype.openView = function openViewFn(entityId) {
		var self = this;

		if (!entityId || entityId == "new") {
			this.showEntity();
			return;
		}
		
		this.api.get(entityId).execute(
			function(resp) {
				self.data = resp.data;
				self.showEntity();
			},
			self.showError
		);
	}
	
	bn.EntityView = BaseEntityView;
	
})(window, document, bn.Holder, bn.Info, bn.Card, bn.TabSwitcher, bn.main, bn, hbel);
