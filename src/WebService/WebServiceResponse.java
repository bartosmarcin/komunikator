package WebService;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class WebServiceResponse {
	private boolean isSuccess;
	private List<String> errors;
	private Object response;
	
	public static WebServiceResponse getFromJson(String json){
		ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.readValue(json, WebServiceResponse.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
				return null;
			} catch (JsonMappingException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}			
	}

	public boolean isIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String>errors) {
		this.errors = errors;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	
	
}
