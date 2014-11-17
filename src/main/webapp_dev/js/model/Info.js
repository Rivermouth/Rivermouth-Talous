(function(win, doc, Element) {
	
	function Field(params) {
		this.type = params.type || "text";
		this.name = params.name || "";
		this.label = params.label || null;
		this.placeholder = params.placeholder || "";
		this.value = params.value || null;
		
		this.input = doc.createElement("input");
		this.input.value = this.value;
	}
	
	Field.prototype = {
		
		render: function() {
			var inputId = "input-" + (new Date()).getTime() + Math.round(Math.random() * 1000);
			
			var elem = doc.createElement("div");
			elem.className = "field";
			
			var label = doc.createElement("label");
			label.setAttribute("for", inputId);
			label.textContent = this.label;
			
			this.input.id = inputId;
			this.input.setAttribute("name", this.name);
			this.input.setAttribute("type", this.type);
			this.input.setAttribute("placeholder", this.placeholder);
			
			elem.appendChild(label);
			elem.appendChild(this.input);
			
			return elem;
		}
		
	};
	
	function Subtitle(text) {
		this.text = text;
	}
	
	Subtitle.prototype = {
		
		render: function() {
			if (!this.text) {
				return doc.createDocumentFragment();
			}
			
			var elem = doc.createElement("div");
			elem.className = "info-subtitle subtitle";
			elem.textContent = this.text;
			
			return elem;
		}
		
	};
	
	function Info(data, deepness) {
		Element.call(this, {className: "info-block"});
		
		this.contentArea = doc.createElement("form");
		
		if (deepness === null) deepness = 0;
		if (deepness < 0) deepness = 999;
		
		this.label = new Subtitle();
		this.field = {};
		
		this.elements.push(this.label);
		
		for (var k in data) {
			var dat = data[k];
			var field;
			
			if (dat !== null && typeof dat === "object") {
				if (deepness > 0) {
					field = new Info(dat, deepness-1);
					field.label.text = k;
					
					this.elements.push(field);
				}
			}
			else {
				field = new Field({
					type: "text",
					name: k,
					label: k,
					value: dat
				});
				this.elements.push(field);
				
				var fieldBnO = new bn.O();
				fieldBnO.name = field.name;
				fieldBnO.onChange = function(value) {
					data[this.name] = value;
				};
				var fieldBn = new bn(field.input, fieldBnO);
			}
			
			this.field[k] = field;
		}
	}
	
	Info.prototype = Object.create(Element.prototype, {
	});
	
	win.bn.Field = Field;
	win.bn.Info = Info;
	
})(window, document, bn.Element);