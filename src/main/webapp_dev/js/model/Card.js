(function(win, doc, Element, Info) {
	
	function Card(params, data) {
		params.className = params.className || "";
		params.className += " card";
		
		Element.call(this, params);
		
		this.header = new Element({className: "header card-header"});
		this.body = new Element({className: "body card-body"});
		this.footer = new Element({className: "body card-footer"});
		
		this.elements = [this.header, this.body, this.footer];
		this.elementsBackup = null;
		
		this.data = data;
	}
	
	Card.prototype = Object.create(Element.prototype);
	
	Card.prototype.toggleEditMode = function() {
		if (this.elementsBackup) {
			this.elements = this.elementsBackup;
			this.elementsBackup = null;
		}
		else {		
			this.elementsBackup = this.elements;
			var info = new Info(this.data, -1);
			this.elements = [info];
			if (this.onEdit) this.onEdit(this, info);
		}
		
		this.update();
	};
	
	window.bn.Card = Card;
	
})(window, document, bn.Element, bn.Info);
