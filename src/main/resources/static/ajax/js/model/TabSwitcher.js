(function(win, doc, Element, hbel) {
	
	function tabLogic() {
		var self = this;
		
		var tabHolder = self.parent;
		var holder = tabHolder.parent;
		
		this.element.onclick = function() {			
			tabHolder.data.selected = self.data.index;
			holder.render();
		};
		
		if (tabHolder.data.selected == this.data.index) {
			this.data.load(holder.body);
		}
	}
	
	/**
	 * tabs: [{}]
	 *   { title:str, load:fn(elementToDraw) }
	 * props: {}, css properties
	 */
	function TabSwitcher(tabs, props) {
		Element.call(this, props);
		
		this.props.class += " holder";
		
		var elem = hbel("div", this.props, null, null);
		
		elem.tabs = hbel("div", {"class": "tab-area holder-tab-area"}, {selected: 0, tabs: tabs}, function() {
			for (var i = 0, l = this.data.tabs.length; i < l; ++i) {		
				this.add(
					hbel("div", {
						"class": "tab " + (this.data.selected == i ? "selected" : "")
					}, {
						index: i, 
						load: this.data.tabs[i].load
					}, [
						this.data.tabs[i].title, 
						tabLogic
					])
				);
			}
		});
		
		elem.header = hbel("div", {"class": "header holder-header"}, null, null);
		elem.body = hbel("div", {"class": "body holder-body"}, null, null);
		elem.footer = hbel("div", {"class": "footer holder-footer"}, null, null);
		
		elem.set(elem.header, elem.tabs, elem.body, elem.footer);

		return elem;
	}
	
	TabSwitcher.prototype = Object.create(Element.prototype);
	
	window.bn.TabSwitcher = TabSwitcher;
	
})(window, document, bn.Element, hbel);
