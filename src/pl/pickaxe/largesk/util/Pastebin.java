package pl.pickaxe.largesk.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;

public class Pastebin
{
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static String sendPost(String textToPaste, String nameOfPaste, String expireDate, String pasteFormat) throws Exception {

		String url = "http://pastebin.com/api/api_post.php";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		textToPaste = URLEncoder.encode(textToPaste, "UTF-8");
		nameOfPaste = URLEncoder.encode(nameOfPaste, "UTF-8");
		
		String urlParameters = "api_option=paste"
			+ "&api_user_key=" + Bukkit.getPluginManager().getPlugin("LargeSk").getConfig().getString("pastebinDeveloperKey"
			+ "&api_paste_private=0"
			+ "&api_paste_name=" + nameOfPaste
			+ "&api_paste_expire_date=" + expireDate
			+ "&api_paste_format=" + pasteFormat
			+ "&api_paste_code=" + textToPaste);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		Xlog.logInfo("Sending 'POST' request to URL : " + url);
		Xlog.logInfo("Post parameters : " + urlParameters);
		Xlog.logInfo("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		Xlog.logInfo(response.toString());
		return response.toString();
	}
}	
