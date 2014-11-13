(function(win, doc, Element) {
	
	function Holder(params) {
		params.className += " holder";
		
		Element.call(this, params);
		
		this.tab = new Element({className: "tab holder-tab"});		
		this.header = new Element({className: "header holder-header"});
		this.body = new Element({className: "body holder-body"});
		this.footer = new Element({className: "footer holder-footer"});
		
		this.elements = [this.tab, this.header, this.body, this.footer];
	}
	
	Holder.prototype = Object.create(Element.prototype, {
	});
	
	window.bn.Holder = Holder;
	
})(window, document, bn.Element);
