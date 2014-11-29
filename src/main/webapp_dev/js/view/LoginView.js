(function(win, doc, Holder, Info, api, main, hbel) {
	
	function loginView() {
		var loginData = {
			email: "",
			password: ""
		};
		
		var holder = new Holder(loginData, {className: "login"});
		holder.tab.set("Login");
		
		var form = new Info(loginData);
		holder.body.add(form);
		form.field.email.type = "email";
		form.field.password.type = "password";
		form.save = function() {
			api.auth(this.data).execute(function(resp) {
				main.open("");
			});
		};
		
		var loginButton = hbel("button", {
			onclick: function() {
				form.save();
			}
		}, null, "Login");
		
		var openRegisterViewButton = hbel("button", {
			onclick: function() {
				main.open("signup");
				main.switchView();
			}
		}, null, "Sign up");
		
		holder.footer.set([loginButton, openRegisterViewButton]);
		
		main.container.appendChild(holder.render());
	}
	
	bn.loginView = loginView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main, hbel);