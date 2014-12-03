(function(win, doc, Element, hbel) {
	
	var RESTRICTED_FIELD_NAMES = {
		"id": true,
		"mimeType": true,
		"owner": true,
		"attachedTo": true
	};
	
	function isRestictedField(name) {
		return (name.charAt(0) == "_" || RESTRICTED_FIELD_NAMES[name]);
	}
	
	function Field(label, props) {
		Element.call(this, props);
		
		if (this.props.value === null) this.props.value = "";
		
		this.props.class = "inp";
		this.props.id = "input-" + Math.round(new Date().getTime() + Math.random() * 1000);
		
		var elem =  hbel("div", {"class": "field"}, true, function() {
			this.props.type = this.type;
			
			this.add(hbel("label", {"class": "label", "for": this.props.id}, null, this.label));
			this.add(hbel("input", this.props, true, function() {
					var self = this;
					this.element.onchange = function() {
						self.data[this.name] = this.value;
					}
				})
			);
		});
		
		elem.props = this.props;
		elem.type = elem.props.type;
		elem.label = label;
			
		return elem;
	}
	
	Field.prototype = Object.create(Element.prototype);
	
	
	
	function Info(data, deepness) {
		Element.call(this, {"class": "info-block"});
		
		var elem = hbel("form", this.props, data, function() {
			if (this.label) {
				this.add(hbel("div", {"class": "info-subtitle"}, null, this.label));
			}
		});
		
		if (deepness === null) deepness = 0;
		else if (deepness < 0) deepness = 999;
		
		elem.field = {};
		
		elem.configFields = function() {
			
		};
		
		elem.hasNewParent = function() {
			this.set();
			var data = this.data;
			
			for (var k in data) {
				if (isRestictedField(k)) continue;
				
				var dat = data[k];
				var field;

				if (dat !== null && typeof dat === "object") {
					if (deepness > 0) {
						field = new Info(dat, deepness-1);
						field.label = k;
					}
				}
				else {
					field = new Field(k, {
						type: "text",
						name: k,
						value: dat
					});
				}
				
				this.field[k] = field;
				
				this.add(field);
			}
			
			this.configFields();
		};
		
		return elem;
	}
	
	Info.prototype = Object.create(Element.prototype);
	
	win.bn.Field = Field;
	win.bn.Info = Info;
	
})(window, document, bn.Element, hbel);