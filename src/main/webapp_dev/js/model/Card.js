(function(win, doc, Element, Info, bn, hbel) {
	
	function createHeader() {
		return hbel("div", {"class": "header card-header"}, true, null);
	}
	
	function createBody() {
		return hbel("div", {"class": "body card-body"}, true, null);
	}
	
	function createFooter() {
		return hbel("div", {"class": "footer card-footer"}, true, null);
	}
	
	function Card(data, props) {
		Element.call(this, props);
		
		this.props.class += " card";
		
		var elem = hbel("div", this.props, data, null);
		elem.element.addEventListener("click", function() {
			this.classList.toggle("opened");
		}, false);
		
		elem.header = createHeader();
		elem.body = createBody();
		elem.footer = createFooter();
		
		elem.set(elem.header, elem.body, elem.footer);
		
		return elem;
	}
	
	Card.prototype = Object.create(Element.prototype);
	
	
	
	function CardEditable(data, props) {
		var elem = new Card(data, props);
		elem.onSave = function() {};
		
		var editElem = {
			header: createHeader(),
			body: createBody(),
			footer: createFooter()
		};
		
		var info = new Info(null, -1);
		
		editElem.header.set("Editing");
		editElem.body.set(info);
		editElem.footer.set([
			hbel("a", {
				onclick: function(evt) {
					evt.preventDefault();
					elem.data = bn.clone(info.data);
					elem.onSave(elem.data);
					elem.toggleEditMode();
				},
				href: "#"
			}, null, "Save"),
			hbel("a", {
				onclick: function(evt) {
					evt.preventDefault();
					elem.toggleEditMode();
				},
				href: "#"
			}, null, "Cancel")
		]);
		
		elem.isEditMode = false;
		elem.toggleEditMode = function() {
			this.isEditMode = !this.isEditMode;
			
			if (this.isEditMode) {
				this.element.classList.add("editing");
				info.data = bn.clone(this.data);
				this.set(editElem.header, editElem.body, editElem.footer);
			}
			else {
				this.element.classList.remove("editing");
				this.set(this.header, this.body, this.footer);
			}
			
			this.render();
		};
		
		elem.form = info;
		
		return elem;
	}
	
	window.bn.Card = Card;
	window.bn.CardEditable = CardEditable;
	
})(window, document, bn.Element, bn.Info, bn, hbel);
