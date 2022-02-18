package Response;

import org.json.JSONObject;

public class ErrorResponse extends Response {
    String errorMsg;

    public ErrorResponse(String errorMsg){
        this.errorMsg = errorMsg;
    }

    public JSONObject executeRsponse() {


        JSONObject response = new JSONObject();
        JSONObject Data = new JSONObject();

        Data.put("message", errorMsg);

        response.put("result", "ERROR");
        response.put("data", Data);

        return response;
    }
}
