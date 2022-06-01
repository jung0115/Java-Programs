// 산업통상자원부_운동 서비스 표준정보
// 고령자 운동 서비스
// https://www.data.go.kr/data/15005602/openapi.do
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SeniorExercise {
    public static void main(String[] args) {
	
    	// 인증키 (개인이 받아와야함)
    	String key = "dd54e04b140a4795ddc23401e0dc833e";

    	// 파싱한 데이터를 저장할 변수
    	String result = "";
		
    	try {
    		URL url = new URL("http://www.ibtk.kr/SeniorExercise/"+ key);

    		BufferedReader bf;

    		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

    		result = bf.readLine();

            //System.out.print(result);

            JSONParser jsonParser = new JSONParser();
        	JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray content = (JSONArray)jsonObject.get("content");

			ArrayList<String> mainMenuTitle = new ArrayList<String>(); // 메인 메뉴 제목
			ArrayList<String> subMenuTitle = new ArrayList<String>(); // 서브 메뉴 제목
			ArrayList<String> exerciseMethod = new ArrayList<String>(); // 운동 방법
			ArrayList<String> exerciseIntensity = new ArrayList<String>(); // 운동 강도
			ArrayList<String> exerciseFrequency = new ArrayList<String>(); // 운동 빈도
			ArrayList<String> exerciseTime = new ArrayList<String>(); // 운동 시간
			ArrayList<String> exerciseVideosUrl = new ArrayList<String>(); // 운동 비디오 URL
			ArrayList<String> exerciseVideosTime = new ArrayList<String>(); // 운동 비디오 시간
			ArrayList<String> thumbnailUrl = new ArrayList<String>(); // 운동 비디오 작은 썸네일 URL
			ArrayList<String> bigThumbnailUrl = new ArrayList<String>(); // 운동 비디오 큰 썸네일 URL
			
			// 내용 별로 Array에 저장. index 번호가 같으면 하나의 정보!
            for(int i = 0; i < content.size(); i++) {
                JSONObject content_cut = (JSONObject)content.get(i);
				mainMenuTitle.add((String)content_cut.get("mainMenuTitle")); // 메인 메뉴 제목
                subMenuTitle.add((String)content_cut.get("subMenuTitle")); // 서브 메뉴 제목
				exerciseMethod.add((String)content_cut.get("exerciseMethod")); // 운동 방법
				exerciseIntensity.add((String)content_cut.get("exerciseIntensity")); // 운동 강도
				exerciseFrequency.add((String)content_cut.get("exerciseFrequency")); // 운동 빈도
				exerciseTime.add((String)content_cut.get("exerciseTime")); // 운동 시간
				exerciseVideosUrl.add((String)content_cut.get("exerciseVideosUrl")); // 운동 비디오 URL
				exerciseVideosTime.add((String)content_cut.get("exerciseVideosTime")); // 운동 비디오 시간
				thumbnailUrl.add((String)content_cut.get("thumbnailUrl")); // 운동 비디오 작은 썸네일 URL
				bigThumbnailUrl.add((String)content_cut.get("bigThumbnailUrl")); // 운동 비디오 큰 썸네일 URL
            }

			// 내용 정리해서 출력
			StringBuilder printSet = new StringBuilder();
			for(int i = 0; i < content.size(); i++) {
				printSet.append(i+1).append("번").append("\n"); // 번호
				printSet.append("메인 메뉴 제목: ").append(mainMenuTitle.get(i)).append("\n");
				printSet.append(" > 서브 메뉴 제목: ").append(subMenuTitle.get(i)).append("\n");
				printSet.append("1) 운동 방법").append("\n").append(exerciseMethod.get(i)).append("\n");
				printSet.append("2) 운동 강도").append("\n").append(exerciseIntensity.get(i)).append("\n");
				printSet.append("3) 운동 시간").append("\n").append(exerciseFrequency.get(i)).append("\n");
				printSet.append("4) 운동 비디오").append("\n").append(exerciseVideosTime.get(i)).append("\n").append(exerciseVideosUrl.get(i)).append("\n");
				printSet.append("5) 운동 비디오 썸네일").append("\n").append(thumbnailUrl.get(i)).append("\n").append(bigThumbnailUrl.get(i)).append("\n").append("\n");
			}

			System.out.print(printSet);

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}