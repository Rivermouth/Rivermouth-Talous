(function(win, doc, Holder, Info, api, main) {
	
	function loginView() {
		var loginData = {
			email: "",
			password: ""
		};
		
		var holder = new Holder({className: "login"});
		holder.tab.text("Login");
		
		var form = new Info(loginData);
		form.field.email.type = "email";
		form.field.password.type = "password";
		
		var loginButton = doc.createElement("button");
		loginButton.textContent = "Login";
		loginButton.onclick = function() {
			api.auth(loginData).execute(function(resp) {
				main.open("");
			});
		};
		
		var openRegisterViewButton = doc.createElement("button");
		openRegisterViewButton.textContent = "Sign up";
		openRegisterViewButton.onclick = function() {
			main.open("signup");
			main.switchView();
		};
		
		holder.body.add(form);
		holder.footer.add(loginButton);
		holder.footer.add(openRegisterViewButton);
		
		main.container.appendChild(holder.render());
	}
	
	bn.loginView = loginView;
	
})(window, document, bn.Holder, bn.Info, bn.api, bn.main);