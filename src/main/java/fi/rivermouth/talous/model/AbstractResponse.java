package fi.rivermouth.talous.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AbstractResponse<T> extends ResponseEntity<ResponseBody> {

	public AbstractResponse() {
		super(HttpStatus.OK);
	}

	public AbstractResponse(HttpStatus status, Responsable responsable) {
		super(new ResponseBody(responsable), status);
	}
	
	public AbstractResponse(HttpStatus status, String kind, T data) {
		super(new ResponseBody(kind, data), status);
	}
	
	public AbstractResponse(HttpStatus status, ResponseBody responseBody) {
		super(responseBody, status);
	}
	
}
