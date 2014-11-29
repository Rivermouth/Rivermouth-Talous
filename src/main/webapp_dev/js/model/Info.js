(function(win, doc, Element, hbel) {
	
	function Field(label, props) {
		Element.call(this, props);
		
		if (this.props.value === null) this.props.value = "";
		
		this.props.class = "inp";
		this.props.id = "input-" + Math.round(new Date().getTime() + Math.random() * 1000);
		
		var elem =  hbel("div", {"class": "field"}, true, [
			hbel("label", {"class": "label", "for": this.props.id}, null, label),
			hbel("input", this.props, true, function() {
				var self = this;
				this.element.onchange = function() {
					self.data[this.name] = this.value;
				}
			})
		]);
			
		return elem;
	}
	
	Field.prototype = Object.create(Element.prototype);
	
	
	
	function Info(data, deepness) {
		Element.call(this, {"class": "info-block"});
		
		var elem = hbel("form", this.props, data, function() {console.log(this);
			if (this.label) {
				this.add(hbel("div", {"class": "info-subtitle"}, null, this.label));
			}
			this.renderFields(this.deepness);
		});
		
		if (deepness === null) elem.deepness = 0;
		else if (deepness < 0) elem.deepness = 999;
		
		elem.renderFields = function(deepness) {
			var data = this.data;
			
			for (var k in data) {
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
						type: this.field[k].type,
						name: k,
						value: dat
					});
				}
				
				this.add(field);
			}
		};
		
		elem.hasNewParent = function() {
			this.field = {};
			for (var k in this.data) {
				this.field[k] = { type: "text" };
			}
		};
		
		return elem;
	}
	
	Info.prototype = Object.create(Element.prototype);
	
	win.bn.Field = Field;
	win.bn.Info = Info;
	
})(window, document, bn.Element, hbel);