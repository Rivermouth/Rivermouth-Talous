(function(win, doc, Element) {
	
	function Card(params) {
		params.className = params.className || "";
		params.className += " card";
		
		Element.call(this, params);
		
		this.header = new Element({className: "header card-header"});
		this.body = new Element({className: "body card-body"});
		
		this.elements = [this.header, this.body];
	}
	
	Card.prototype = Object.create(Element.prototype, {
	});
	
	window.bn.Card = Card;
	
})(window, document, bn.Element);
