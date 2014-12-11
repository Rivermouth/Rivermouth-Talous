(function(win, doc, api, main, TabSwitcher, hbel) {
	
	var
		MOST_RECENT = "Most recent",
		MOST_PROFITABLE = "Most profitable"
	;
	
	function createList(data) {
		return hbel("table", null, null, function() {
			for (var i = 0, l = data.length; i < l; ++i) {
				this.add(hbel("tr", null, data[i], [
					hbel("td", null, true, "{{name}}"),
					hbel("td", null, true, "{{vatNumber}}"),
					function() {
						var self = this;
						this.element.onclick = function() {
							main.open("clients/" + self.data.id);
						}
					}
				]));
			}
		});
	}
	
	function createMostRecentList(parentElement) {
		api.clients.list().execute(function(resp) {
			parentElement.set(createList(resp.data));
			parentElement.render();
		});
	}
	
	function createMostProfitableList(parentElement) {
		
	}
	
	function ClientsCard() {
		var elem = new TabSwitcher([
			{
				title: MOST_RECENT,
				load: createMostRecentList
			},
			{
				title: MOST_PROFITABLE,
				load: createMostProfitableList
			}
		], {
			"class": "clients widget"
		});
		
		elem.header.set("Clients");
		
		return elem;
	}
	
	window.bn.ClientsCard = ClientsCard;
	
})(window, document, bn.api, bn.main, bn.TabSwitcher, hbel);			
			