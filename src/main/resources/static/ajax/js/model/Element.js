(function(win, doc) {
	
	function Element(props, data) {
		// Default params
		this.props = {
			"class": "element"
		};
		
		// Merge params
		Element.mergeRecursive(this.props, props || {});
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
	
	win.bn.Element = Element;
	
})(window, document);