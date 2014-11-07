package fi.rivermouth.talous.model;

import org.springframework.stereotype.Component;

@Component
public class AppInfo {

	private final String name;
	private final String version;
	
	public AppInfo() {
		name = "Rivermouth Talous";
		version = "0.0.1";
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}
	
}
