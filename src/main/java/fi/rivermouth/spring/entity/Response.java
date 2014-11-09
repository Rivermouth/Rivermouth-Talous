package fi.rivermouth.spring.entity;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Response extends AbstractResponse<Responsable> {
	
	public Response() {}
	
	public Response(HttpStatus status, Responsable responsable) {
		super(status, responsable);
	}
	
	public Response(HttpStatus status, String kind, Object object) {
		super(status, new ResponseBody(kind, object));
	}
	
	public static class Message implements Responsable {
		
		private String message;
		
		public Message(String message) {
			this.message = message;
		}
		
		/**
		 * Create formatted Message
		 * this.message = String.format(message, args);
		 * @param message
		 * @param args
		 */
		public Message(String message, Object...args) {
			this.message = String.format(message, args);
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String getKind() {
			return "message";
		}
		
	}

	public static class ErrorMessage extends Message implements Responsable {
		
		public ErrorMessage(String message) {
			super(message);
		}
		
		public ErrorMessage(String message, Object...args) {
			super(message, args);
		}

		@Override
		public String getKind() {
			return "error";
		}
		
	}
	
}
