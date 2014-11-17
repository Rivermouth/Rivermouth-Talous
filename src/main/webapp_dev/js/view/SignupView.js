(function(win, doc, Holder, Info, api, main, bn) {
	
	var signupData = {
		name: {
			firstName: "",
			lastName: "",
		},
		role: "",
		address: {
			street: "",
			zip: "",
			city: "",
			country: ""
		},
		email: "",
		tel: "",
		company: {
			name: "",
			vatNumber: "",
			address: {
				street: "",
				zip: "",
				city: "",
				country: ""
			}
		},
		password: ""
	};
	
	function signupView() {
		var holder = new Holder({className: "signup"});
		holder.tab.text("Register");
		
		var form = new Info(signupData, -1);
		form.field.email.type = "email";
		form.field.password.type = "password";
		
		form.field.company.field.address.label.text = "address (leave empty if same as user address)";
		console.log(form);
		
		var button = doc.createElement("button");
		button.textContent = "Login";
		button.onclick = function() {
			if (bn.isObjectFieldsBlank(signupData.company.address)) {
				signupData.company.address = signupData.address;
			}
			
			console.log(signupData);
			
			api.register(signupData).execute(function(resp) {
				main.open("");
			});
		};
		
		holder.body.add(form);
		holder.footer.add(button);
		
		main.container.appendChild(holder.render());
	}
	
	bn.signupView = signupView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, bn);