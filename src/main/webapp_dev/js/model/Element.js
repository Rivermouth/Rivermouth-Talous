(function(win, doc) {
	
	function Element(params) {
		// Default params
		this.className = "element";
		this.elements = [];
		this.onClick = undefined;
		
		this.contentArea = doc.createElement("div");
		
		// Merge params
		Element.mergeRecursive(this, params || {});
	}
	
	Element.mergeRecursive = function mergeRecursiveFn(obj1, obj2) {
		for (var p in obj2) {
			try {
				// Property in destination object set; update its value.
				if (obj2[p].constructor == Object) {
					obj1[p] = Element.mergeRecursive(obj1[p], obj2[p]);
				}
				else {
					obj1[p] = obj2[p];
				}
			} catch(e) {
				// Property in destination object not set; create it and set its value.
				obj1[p] = obj2[p];
			}
		}
	};
	
	Element.prototype = {
		
		add: function(element) {
			this.elements.push(element);
		},
		
		render: function() {
			var outer = doc.createElement("div");
			outer.className = this.className;
			
			var inner = this.contentArea;
			inner.className = this.className + "-inner";
			
			this.update();
			
			outer.appendChild(inner);
			
			outer.onclick = this.onClick;
			
			return outer;
		},
		
		update: function() {
			var d = doc.createDocumentFragment();
			for (var i = 0, l = this.elements.length; i < l; ++i) {
				var el = this.elements[i];
				try {
					d.appendChild(el.render());
				}
				catch(e) {
					d.appendChild(el);
				}
			}
			this.contentArea.appendChild(d);
		},
		
		text: function(text) {
			if (text === null) return this.contentArea.textContent;
			this.contentArea.textContent = text;
		},
		
		html: function(html) {
			if (html === null) return this.contentArea.innerHTML;
			this.contentArea.innerHTML = html;
		}
		
	};
	
	win.bn.Element = Element;
	
})(window, document);