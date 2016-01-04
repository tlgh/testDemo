package pers.ksy.common.wechat.api;

import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class WechatApiClient {
	private static final String apiEntry = "https://api.weixin.qq.com/";
	private String appid;
    private String secret;

    
    
    public WechatApiClient(String appid, String secret) throws Exception{
        if ("".equals(appid) || "".equals(secret)){
            throw new Exception("appid 和 secret 不能为空");
        }
  
        this.appid = appid;
        this.secret = secret;
    }
    
	public HttpResponse get(String method, HashMap<String, String> parames)
			throws Exception {
		String url = apiEntry + method + "?" + getParamStr(method, parames);

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		return response;
	}
    
    public String getParamStr(String method, HashMap<String, String> parames){
        String str = "";
        try {
            str = URLEncoder.encode(buildParamStr(buildCompleteParams(method, parames)), "UTF-8")
                    .replace("%3A", ":")
                    .replace("%2F", "/")
                    .replace("%26", "&")
                    .replace("%3D", "=")
                    .replace("%3F", "?");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }
    
    private String buildParamStr(HashMap<String, String> param){
        String paramStr = "";
        Object[] keyArray = param.keySet().toArray();
        for(int i = 0; i < keyArray.length; i++){
            String key = (String)keyArray[i];

            if(0 == i){
                paramStr += (key + "=" + param.get(key));
            }
            else{
                paramStr += ("&" + key + "=" + param.get(key));
            }
        }

        return paramStr;
    }


    private HashMap<String, String> buildCompleteParams(String method, HashMap<String, String> parames) throws Exception{
        HashMap<String, String> commonParams = getCommonParams(method);
		if (null != parames) {
	        for (String key : parames.keySet()) {
				if(commonParams.containsKey(key)){
					throw new Exception("参数名冲突");
				}
				
				commonParams.put(key, parames.get(key));
			}
        }
        return commonParams;
    }

    private HashMap<String, String> getCommonParams(String method){
       HashMap<String, String> parames = new HashMap<String, String>();
        parames.put("appid", appid);
        parames.put("secret", secret);
        return parames;
    }
}
