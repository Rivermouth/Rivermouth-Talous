(function(win, doc, Holder, Info, Card, TabSwitcher, main, bn, hbel) {
	
	function BaseEntityView(entityName, api) {
		var self = this;
		
		this.entityName = entityName;
		this.api = api;
		this.data = null;
		
		this.tab = {
			main: {
				title: "Home",
				load: function(parentElem) {
				}
			},
			notes: {
				title: "Notes",
				load: function(parentElem) {
					parentElem.set(self.notesHolder);
				}
			},
			files: {
				title: "Files",
				load: function(parentElem) {
					parentElem.set(self.filesHolder);
				}
			},
			info: {
				title: "Info",
				load: function(parentElem) {
					parentElem.set(self.infoHolder);
				}
			}
		};
		
		this.tabs = [this.tab.main, this.tab.notes, this.tab.files, this.tab.info];
		
		this.element = new TabSwitcher(this.tabs, {"class": "main-view"});
		
		this.createHeader();
	}
	
	BaseEntityView.prototype.getEmptyEntityData = function getEmptyEntityDataFn() { return {}; };
	
	BaseEntityView.prototype.newNotesHolder = function newNotesHolderFn() {
		var self = this;
		var elem = bn.newNotesHolder();
		
		elem.getFiles = function(callback) {
			self.api.notes.list(self.data).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			self.api.notes.save(self.data, data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		return elem;
	};
	
	BaseEntityView.prototype.newFilesHolder = function newFilesHolderFn() {
		var self = this;
		var elem = bn.newFilesHolder();
		
		elem.getFiles = function(callback) {
			self.api.files.list(self.data).execute(function(resp) {
				console.log(resp);
				callback(resp.data);
			});
		};
		
		elem.saveFile = function(data) {
			this.form.element.method = "POST";
			this.form.element.action = self.api.files.savePath("images", self.data);
			this.form.element.enctype = "multipart/form-data";
			this.form.element.submit();
			return;
			
			self.api.files.save(data).execute(function(resp) {
				console.log(resp);
			});
		};
		
		return elem;
	}
	
	BaseEntityView.prototype.newInfoHolder = function newInfoHolderFn() {
		var self = this;
		var elem = bn.newInfoHolder("info", self.data, -1);
		
		elem.save = function() {
			self.api.save(this.data).execute(function(resp) {
				console.log(resp);
				main.open(self.entityName + "s/" + resp.data.id);
			});
		};
		
		return elem;
	}
	
	BaseEntityView.prototype.createHeader = function createHeaderFn() {
		this.element.header.set(
			hbel("div", null, null, [
				hbel("a", {onclick: function() {
					main.open("");
				}}, null, "< Back to dashboard")
			])
		);
	};
	
	BaseEntityView.prototype.getMainTabName = function getMainTabNameFn() {
		return this.data.name;
	};
	
	BaseEntityView.prototype.showEntity = function showEntityFn() {
		var self = this;
		
		this.isNewEntity = this.data ? false : true;
		
		if (this.isNewEntity) {
			this.data = this.getEmptyEntityData();
		}

		this.infoHolder = this.newInfoHolder();
		this.notesHolder = this.newNotesHolder();
		this.filesHolder = this.newFilesHolder();
		
		var tabsToUser = this.tabs;
		
		if (this.isNewEntity) {
			tabsToUser = [
				{
					title: "New client",
					load: function(parentElem) {
						parentElem.set(self.infoHolder);
					}
				}
			];
		}

		this.element.tabs.data.tabs = tabsToUser;
		
		main.container.appendChild(this.element.render());
	}
	
	BaseEntityView.prototype.showError = function showErrorFn(data) {
		main.container.textContent = JSON.stringify(data);
	}
	
	BaseEntityView.prototype.openView = function openViewFn(entityId, callback) {
		var self = this;
		
		function callCallback() {
			if (callback) callback();
		}
		
		if (this.data) {
			this.showEntity();
			callCallback();
			return;
		}

		if (!entityId || entityId == "new") {
			this.showEntity();
			callCallback();
			return;
		}
		
		this.api.get(entityId).execute(
			function(resp) {
				self.data = resp.data;
				self.showEntity();
				callCallback();
			},
			self.showError
		);
	}
	
	bn.EntityView = BaseEntityView;
	
})(window, document, bn.Holder, bn.Info, bn.Card, bn.TabSwitcher, bn.main, bn, hbel);
