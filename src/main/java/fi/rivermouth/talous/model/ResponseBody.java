package fi.rivermouth.talous.model;

public class ResponseBody {
	
	private String kind;
	private Object data;

	public ResponseBody() {}

	public ResponseBody(Responsable responsable) {
		if (responsable == null) return;
		this.kind = responsable.getKind();
		this.data = responsable;
	}
	
	public ResponseBody(String kind, Object data) {
		this.kind = kind;
		this.data = data;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
