(function(doc) {
	
	function clone(obj) {
		var copy;

		// Handle the 3 simple types, and null or undefined
		if (null == obj || "object" != typeof obj) return obj;

		// Handle Date
		if (obj instanceof Date) {
			copy = new Date();
			copy.setTime(obj.getTime());
			return copy;
		}

		// Handle Array
		if (obj instanceof Array) {
			copy = [];
			for (var i = 0, len = obj.length; i < len; i++) {
				copy[i] = clone(obj[i]);
			}
			return copy;
		}

		// Handle Object
		if (obj instanceof Object) {
			copy = {};
			for (var attr in obj) {
				if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
			}
			return copy;
		}

		throw new Error("Unable to copy obj! Its type isn't supported.");
	}
	
	function isFunction(obj) {
		return (typeof obj === "function");
	}
	
	function isHTMLElement(obj) {
		return (obj instanceof HTMLElement);
	}
	
	function isString(obj) {
		return (typeof obj === "string");
	}
	
	function isNumber(obj) {
		return (typeof obj === "number");
	}
	
	function isArray(obj) {
		return (obj instanceof Array);
	}
	
	function getVar(data, string) {
		console.log(string);
		var parts = string.split(".");
		var out = data;
		for (var i = 0, l = parts.length; i < l; ++i) {
			out = out[parts[i]];
		}
		return out;
	}
	
	function HBElement(tag, props, data, inners) {
		this._RENDERING = false;
		
		this._tag = tag;
		this._props = clone(props);
		this._data = clone(data);
		
		this._id = (new Date().getTime() + "" + Math.round(Math.random() * 1000));
		
		this._inners = [];
		
		this.parent = null;
		this.elements = [];
		this.data = data;
		
		this.rawElement = null;
		this.element = doc.createElement(tag);
		
		if (props !== null) {
			for (var i in props) {
				if (i.substring(0, 2) == "on") {
					this.element[i] = this._parseVariables(props[i]);
				}
				else {
					this.element.setAttribute(i, this._parseVariables(props[i]));
				}
			}
		}
		
		this.set(inners);
	}
	
	/* Statics */
	
	HBElement.isHBElement = function(obj) {
		return (obj instanceof HBElement);
	};
	
	
	/* User overridables */
	
	HBElement.prototype.onRender = function() {};
	HBElement.prototype.hasNewParent = function() {};
	
	
	/* Privates */
	
	HBElement.prototype._forEachHBElement = function(fn) {
		for (var i = 0, l = this._inners.length; i < l; ++i) {
			if (HBElement.isHBElement(this._inners[i])) {
				fn(this._inners[i], i);
			}
		}
	}
	
	HBElement.prototype._notifyChilds = function(fnName) {
		this._forEachHBElement(function(element) {
			element[fnName]();
		});
	};
	
	HBElement.prototype._hasNewParent = function() {
		this.getData();
		this._notifyChilds("_hasNewParent");
		this.hasNewParent();
	};
	
	HBElement.prototype._parseVariables = function(str) {
		if (!isString(str)) return str;
		
		var self = this;
		function replacer(match, p1, offset, string) {
			return getVar(self.getData(), p1);
		}
		var reg = new RegExp("{{(.*?)}}", "g");
		str = str.replace(reg, replacer);
		return str;
	};
	
	/**
	 * Render child elements
	 */
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
	
	HBElement.prototype._setParent = function(parent) {		
		if (this.parent === null || this.parent._id != parent._id) {
			this.parent = parent;
			this.getData();
			this._hasNewParent();
		}
	};
	
	HBElement.prototype._addInner = function(obj) {
		if (HBElement.isHBElement(obj)) {
			obj._setParent(this);
		}
		this._inners.push(obj);
	};
	
	
	/* Publics */
	
	HBElement.prototype.getData = function() {
		if (this.data === true && this.parent !== null) {
			this.data = this.parent.getData();
		}
		return this.data;
	};
	
	HBElement.prototype.render = function(targetElement) {
		this._RENDERING = true;
		
		this.onRender();
		
		this.elements = [];
		
		for (var i = 0, l = this._inners.length; i < l; ++i) {
			var obj = this._inners[i];
			
			if (isFunction(obj)) {
				obj.call(this);
			}
			else {
				this.add(obj);
			}
		}

		this.element.innerHTML = "";
		this.element.appendChild(this._renderElements());
		
		if (this.rawElement !== null) {
			if (isString(this.rawElement)) {
				this.element.innerHTML = this._parseVariables(this.rawElement);
			}
		}
		
		if (targetElement !== null && targetElement !== undefined) {
			targetElement.innerHTML = "";
			targetElement.appendChild(this.element);
		}
		
		this._RENDERING = false;
		return this.element;
	};
	
	HBElement.prototype.add = function(obj) {
		if (!this._RENDERING) {
			return this._addInner(obj);
		}
		
		if (isString(obj)) {
			this.rawElement = obj;
			return;
		}
		
		if (HBElement.isHBElement(obj)) {
			obj._setParent(this);
		}
		
		this.elements.push(obj);
	};
	
	HBElement.prototype.addTo = function(parent) {
		if (!HBElement.isHBElement(parent)) {
			throw parent.toString() + " is not HBElement";
		}
		parent.add(this);
	};
	
	/**
     * Detach HBElement from parent
	 * @param obj :(HBElement|index(num)|null)
	 * 
	 * if HBElement, detach HBElement from parent
	 * if index:num, detach nth element from parent
	 * if null, detach this from parent
	 */
	HBElement.prototype.detach = function(obj) {
		var elem = null;
		if (obj === null) {
			this.parent.detach(this);
		}
		else if (HBElement.isHBElement(obj)) {
			var self = this;
			this._forEachHBElement(function(element, i) {
				if (element.id == obj.id) {
					self.detach(i);
					return;
				}
			});
		}
		else if (isNumber(obj)) {
			elem = this._inners.splice(obj, 1)[0];
		}
		
		if (HBElement.isHBElement(elem)) {
			elem.parent = null;
			elem.data = elem._data;
		}
	};
	
	HBElement.prototype.detachAll = function() {
		for (var i = this._inners.length - 1; i >= 0; --i) {
			this.detach(i);
		}
		if (this._inners.length > 0) {
			console.warn("Error with detaching items");
		}
		this.rawElement = null;
	};
	
	HBElement.prototype.set = function() {
		this.detachAll();
		
		var inners = [];
		if (isArray(arguments[0])) {
			inners = arguments[0];
		}
		else {
			inners = arguments;
		}
		
		for (var i = 0, l = inners.length; i < l; ++i) {
			var inner = inners[i];
			this._addInner(inner);
		}
	};
	
	
	var HTMLBuilder = {
		
		/**
		 * @param tag :str, HTML tag name
		 * @param props :{}, HTML element properties
		 * @param data, data to bind. If true, inherit parent data
		 * @param childs, element's childs (HBElement|HTMLElement|RawHTMLElement(HTML string))
		 */
		element: function() {
			var inners = [];
			
			function loopInners(arr, startFrom) {
				for (var i = startFrom, l = arr.length; i < l; ++i) {
					inners.push(arr[i]);
				}
			}
			
			if (isArray(arguments[3])) {
				loopInners(arguments[3], 0);
			}
			else {
				loopInners(arguments, 3);
			}
			
			return new HBElement(arguments[0], arguments[1], arguments[2], inners);
		}
	};
	
	window.HTMLBuilder = HTMLBuilder;
	window.hbel = HTMLBuilder.element;
	
})(document);
