package LoginUser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class FaceLogin {
    public String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(IConstantFF.FACEBOOK_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", IConstantFF.FACEBOOK_CLIENT_ID)
                                .add("client_secret", IConstantFF.FACEBOOK_CLIENT_SECRET)
                                .add("redirect_uri", IConstantFF.FACEBOOK_REDIRECT_URI)
                                .add("code", code)
                                .build()
                )
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }
    public AccountFF getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = IConstantFF.FACEBOOK_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        AccountFF fbAccount= new Gson().fromJson(response, AccountFF.class);
        return fbAccount;
    }
}
