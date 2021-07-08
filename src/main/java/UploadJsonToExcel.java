import org.json.CDL;
import org.json.JSONArray;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Scanner;


public class UploadJsonToExcel {
    public static void main(String[] args) {
        String response = "";
        try {
            URL url = new URL("https://data.sfgov.org/resource/p4e4-a5a7.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            System.out.println("Response code is: " + responseCode);
            if (responseCode != 200)
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    response += sc.nextLine();
                }
                sc.close();
            }
            JSONArray json_arr = new JSONArray(response);
            uploadDataToExcel(json_arr);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadDataToExcel(JSONArray jsonData) throws IOException {
        File file = new File("/sheik/UploadToJson.csv");
        String csv = CDL.toString(jsonData);
        FileUtils.writeStringToFile(file, csv);
    }
}