// 서울올림픽기념국민체육진흥공단_국민체력100_운동처방_동영상정보
// https://www.data.go.kr/data/15084814/fileData.do#layer-api-guide
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SeoulExercise {
    public static void main(String[] args) {
	
    	// 인증키 (개인이 받아와야함)
    	String key = "7VgAbrUNHG0BQOPUubAEEkOT45PoaRK6TR92eLuGBsfqyhspb%2BY1oOyrwqIeWXYGrSVw9vMreaGpnekwpR8pGw%3D%3D";
        String limitPage = "248"; // 248
		
    	// 파싱한 데이터를 저장할 변수
    	String result = "";

    	try {
    		URL url = new URL("https://api.odcloud.kr/api/15084814/v1/uddi:3f8d6b98-0082-4792-92a8-90d40ecc4bce?page=1&perPage=" + limitPage +"&serviceKey=" + key );

    		BufferedReader bf;

    		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

    		result = bf.readLine();

            //System.out.print(result);

            JSONParser jsonParser = new JSONParser();
        	JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray content = (JSONArray)jsonObject.get("data");
            System.out.print(content);

            ArrayList<String> firstCategory = new ArrayList<String>(); // 대분류
            ArrayList<String> secondCategory = new ArrayList<String>(); // 중분류
            ArrayList<String> lastCategory = new ArrayList<String>(); // 소분류
			ArrayList<String> title = new ArrayList<String>(); // 제목
			ArrayList<String> videoUrl = new ArrayList<String>(); // 동영상 Url
			
			// 내용 별로 Array에 저장. index 번호가 같으면 하나의 정보!
            for(int i = 0; i < content.size(); i++) {
                JSONObject content_cut = (JSONObject)content.get(i);
                firstCategory.add((String)content_cut.get("대분류")); // 대분류
                secondCategory.add((String)content_cut.get("중분류")); // 중분류
                lastCategory.add((String)content_cut.get("소분류")); // 소분류
				title.add((String)content_cut.get("제목")); // 제목
				videoUrl.add((String)content_cut.get("동영상주소")); // 동영상 Url
            }

			// 내용 정리해서 출력
			StringBuilder printSet = new StringBuilder();
			for(int i = 0; i < content.size(); i++) {
				printSet.append(i+1).append("번").append("\n"); // 번호
                printSet.append(firstCategory.get(i));
                printSet.append(" > ").append(secondCategory.get(i));
                printSet.append(" > ").append(lastCategory.get(i)).append("\n");
				printSet.append("- 제목: ").append(title.get(i)).append("\n");
				printSet.append("- 동영상: ").append(videoUrl.get(i)).append("\n").append("\n");
			}

			System.out.print(printSet);

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
