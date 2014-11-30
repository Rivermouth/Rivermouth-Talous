(function(win, doc, Holder, Info, api, main, bn, hbel) {
	
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
		var holder = new Holder(null, {"class": "signup"});
		holder.tab.set("Register");
		
		var form = new Info(signupData, -1);
		holder.body.set(form);
		
		form.field.email.type = "email";
		form.field.password.type = "password";
		form.field.company.field.address.label.text = "address (leave empty if same as user address)";
		
		var button = hbel("button", {
			onclick: function() {
				if (bn.isObjectFieldsBlank(signupData.company.address)) {
					signupData.company.address = signupData.address;
				}

				console.log(signupData);

				api.register(signupData).execute(function(resp) {
					main.open("");
				});
			}
		}, null, "Login");
		
		holder.footer.set(button);
		
		main.container.appendChild(holder.render());
	}
	
	bn.signupView = signupView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, bn, hbel);