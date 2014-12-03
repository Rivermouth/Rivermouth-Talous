(function(win, doc, Element, hbel) {
	
	function Holder(data, props) {
		Element.call(this, props);
		
		this.props.class += " holder";
		
		var elem = hbel("div", this.props, data, null);
		
		elem.header = hbel("div", {"class": "header holder-header"}, true, null);
		elem.body = hbel("div", {"class": "body holder-body"}, true, null);
		elem.footer = hbel("div", {"class": "footer holder-footer"}, true, null);
		
		elem.set(elem.header, elem.body, elem.footer);

		return elem;
	}
	
	Holder.prototype = Object.create(Element.prototype);
	
	window.bn.Holder = Holder;
	
})(window, document, bn.Element, hbel);
