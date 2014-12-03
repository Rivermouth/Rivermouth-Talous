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
	
	
	
	function Info(data, deepness, isChildForm) {
		Element.call(this, {"class": "info-block"});
		
		this.element = hbel((isChildForm ? "div" : "form"), this.props, data);
		this.element.renderLogic = function() {
			if (this.label) {
				this.add(hbel("div", {"class": "info-subtitle"}, null, this.label));
			}
		};
		
		if (deepness === null) deepness = 0;
		else if (deepness < 0) deepness = 999;
		
		this.element.field = {};
		
		this.element.configFields = function() {
			
		};
		
		this.element.hasNewParent = function() {
			this.set(this.renderLogic);
			var data = this.data;
			
			for (var k in data) {
				if (isRestictedField(k)) continue;
				
				var dat = data[k];
				var field;

				if (dat !== null && typeof dat === "object") {
					if (deepness > 0) {
						var info = new Info(dat, deepness-1, true);
						info.element.label = k;
						field = info.element;
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
	}
	
	Info.prototype = Object.create(Element.prototype);
	
	
	function InfoB(data, deepness, isChildForm) {
		var info = new Info(data, deepness, isChildForm);
		return info.element;
	}
	
	win.bn.Field = Field;
	win.bn.Info = InfoB;
	
})(window, document, bn.Element, hbel);
