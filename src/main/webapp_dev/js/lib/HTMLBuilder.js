(function(doc) {
	
	function isFunction(obj) {
		return (typeof obj === "function");
	}
	
	function isHTMLElement(obj) {
		return (obj instanceof HTMLElement);
	}
	
	function isString(obj) {
		return (typeof obj === "string");
	}
	
	function HBElement(tag, props, data) {
		this.parent = null;
		this.elements = [];
		this.data = data;
		
		this.rawElement = null;
		this.element = doc.createElement(tag);
		
		if (props !== null) {
			for (var i in props) {
				this.element.setAttribute(i, props[i]);
			}
		}
	}
	
	HBElement.isHBElement = function(obj) {
		return (obj instanceof HBElement);
	};
	
	HBElement.prototype._parseHTMLString = function(htmlElem) {
		for (var k in this.data) {
			var reg = new RegExp("{{" + k + "}}", "g");
			htmlElem = htmlElem.replace(reg, this.data[k]);
		}
		return htmlElem;
	};
	
	HBElement.prototype._renderElements = function() {
		var d = doc.createDocumentFragment();

		for (var i = 0, l = this.elements.length; i < l; ++i) {
			var obj = this.elements[i];
			var htmlElem = null;

			if (HBElement.isHBElement(obj)) {
				htmlElem = obj.render();
			}
			else if (isHTMLElement(obj)) {
				htmlElem = obj;
			}

			if (htmlElem !== null) {
				d.appendChild(htmlElem);
			}
		}
		
		return d;
	};
	
	HBElement.prototype.render = function(targetElement) {
		if (this.elements.length > 0) {
			
			var oldElements = this.elements;
			this.elements = [];

			for (var i = 0, l = oldElements.length; i < l; ++i) {
				var obj = oldElements[i];

				if (isFunction(obj)) {
					obj.call(this);
				}
				else {
					this.add(obj);
				}
			}
			
			this.element.innerHTML = "";
			this.element.appendChild(this._renderElements());

			this.elements = oldElements;
			
		}
		
		if (this.rawElement !== null) {
			if (isString(this.rawElement)) {
				this.element.innerHTML = this._parseHTMLString(this.rawElement);
			}
		}
		
		if (targetElement !== null && targetElement !== undefined) {
			targetElement.innerHTML = "";
			targetElement.appendChild(this.element);
		}
		
		return this.element;
	};
	
	HBElement.prototype.add = function(obj) {
		if (isString(obj)) {
			this.rawElement = obj;
			return;
		}
		
		if (HBElement.isHBElement(obj)) {
			obj.parent = this;
		}
		
		this.elements.push(obj);
	};
	
	
	var HTMLBuilder = {
		element: function() {
			var elem = new HBElement(arguments[0], arguments[1], arguments[2]);
			
			for (var i = 3, l = arguments.length; i < l; ++i) {
				var inner = arguments[i];
				elem.add(inner);
			}
			
			return elem;
		}
	};
	
	window.HTMLBuilder = HTMLBuilder;
	window.hbel = HTMLBuilder.element;
	
})(document);
