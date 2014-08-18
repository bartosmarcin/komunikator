package WebService;

import com.android.volley.Request;

public class WebServiceRequest {
	private Request<?> request;
	
	public WebServiceRequest(Request<?> request){
		this.request = request;
	}
	
	public void cancel(){
		if(!request.hasHadResponseDelivered())
			request.cancel();
	}

}
