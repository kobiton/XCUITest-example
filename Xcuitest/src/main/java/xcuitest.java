import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.json.simple.JSONObject;

public class xcuitest {
  public static void main(String[] args) {
    UIAutomatorTest();
  }
  public static String generateBasicAuth() {
	  String username = "gyanadeeps";
      String apiKey = "15a9ea3f-38fb-450c-9706-08a72ed71950";
    byte[] encodedBytes = Base64.getEncoder().encode((username + ":" + apiKey).getBytes());
    String encodeAuth = new String(encodedBytes);
    return "Basic " + encodeAuth;
  }

  public static void UIAutomatorTest() {
    try {
      List<String> tests = new ArrayList<String>();
      tests.add("com.todolist.app.version1");
      //tests.add("ChangeTextBehaviorTest");
      //tests.add("ChangeTextBehaviorTest#testChangeText_sameActivity");

      
     
      JSONObject options = new JSONObject();

      options.put("sessionName", "Automation test session");
      options.put("sessionDescription", ""); 
      options.put("noReset", true);
      options.put("fullReset", false);     
      options.put("deviceName", "iPhone XS Max");
      // The tag is used for finding devices and the user can input only one tag. 
      // For example, the data value will be inputted: tagName="TagName1"
      options.put("tagName", "");
      options.put("platformVersion", "15.6");  
      // The given team is used for finding devices and the created session will be visible for all members within the team.
      options.put("groupId", 12170); // Group: AutomationDocs
      options.put("deviceGroup", "ORGANIZATION");
      options.put("app", "kobiton-store:v414642");
      options.put("testRunner", "https://kobiton-devvn.s3-ap-southeast-1.amazonaws.com/apps-test/uiautomator-espresso/esspresso-test-runner.apk"); 
      options.put("testFramework", "XCUITEST");

      // The data format value will be inputted:
//            tests = ["Test-Class-Name/Test-Method-Name"]

      // If the "tests" is blank, all tests in testRunner will be run.

      // To specific some test cases: let's say we have 2 classes: "Test-Class-Name-1"
      // and "Test-Class-Name-2". To run "Test-Class-Name-1" and "Test-Class-Name-2"
      // class in test runner file, the input will be:
//            tests = ["Test-Class-Name-1", "Test-Class-Name-2"]

      // To run "Test-Method-Name-1" method in "Test-Class-Name-1" class and run
      // "Test-Class-Name 2" class in test runner file, the input will be:
//            tests =["Test-Class-Name-1/Test-Method-Name-1", "Test-Class-Name-2"]
        
      options.put("tests", tests);
      options.put("testPlan","https://kobiton-devvn.s3-ap-southeast-1.amazonaws.com/apps-test/sample.xctestplan");

      
      JSONObject configuration = new JSONObject();
      configuration.put("configuration", options);

      // Access https://github.com/kobiton/samples/tree/master/xcuitest/csharp to learn more about configuration of Java language. 


      String url = "https://api.kobiton.com/hub/session";
      URL uri = new URL(url);
      HttpURLConnection con = (HttpURLConnection) uri.openConnection();

      con.setDoOutput(true);
      con.setDoInput(true);
      con.setUseCaches(false);


      String postData = configuration.toString();

      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Accept", "application/json");
      con.setRequestProperty("Authorization", generateBasicAuth());
      OutputStream os = con.getOutputStream();
      os.write(postData.getBytes());
      os.flush();

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }

      in.close();
      con.disconnect();

      System.out.println("Result: " + response.toString());
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }
}
