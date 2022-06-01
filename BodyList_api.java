// 산업통상자원부_인체치수 3차원형상측정 통계
// https://www.data.go.kr/data/15056554/openapi.do
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BodyList_api {
    public static void main(String[] args) {
	
    	// 인증키 (개인이 받아와야함)
    	String key = "e1f7770edea1d220c06f4483de368a71";
		
    	// 파싱한 데이터를 저장할 변수
    	String result = "";

    	try {
    		URL url = new URL("http://www.ibtk.kr/BodyList_api/"+ key);

    		BufferedReader bf;

    		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

    		result = bf.readLine();

            //System.out.print(result);

            JSONParser jsonParser = new JSONParser();
        	JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray content = (JSONArray)jsonObject.get("content");
            //System.out.print(content);

            
			ArrayList<String> year = new ArrayList<String>(); // 년도
			ArrayList<String> targetclsCd = new ArrayList<String>(); // 인체치수통계대상분류코드
			ArrayList<String> detailregionCd = new ArrayList<String>(); // 인체치수통계세부부위코드
			ArrayList<String> sex = new ArrayList<String>(); // 	성별(남성:M,여성:W)
			ArrayList<String> agesCd = new ArrayList<String>(); // 인체치수통계연령대코드
			ArrayList<String> samplesize = new ArrayList<String>(); // 표본크기(측정수)
			ArrayList<String> average = new ArrayList<String>(); // 평균
			ArrayList<String> standarddeviation = new ArrayList<String>(); // 표준편차
			ArrayList<String> minvalue = new ArrayList<String>(); // 최소값
			ArrayList<String> maxvalue = new ArrayList<String>(); // 최대값
			ArrayList<String> percentile1th = new ArrayList<String>(); // 백분위수_1th
			ArrayList<String> percentile5th = new ArrayList<String>(); // 백분위수_5th
			ArrayList<String> percentile10th = new ArrayList<String>(); // 백분위수_10th
			ArrayList<String> percentile25th = new ArrayList<String>(); // 백분위수_25th
			ArrayList<String> percentile50th = new ArrayList<String>(); // 백분위수_50th
			ArrayList<String> percentile75th = new ArrayList<String>(); // 백분위수_75th
			ArrayList<String> percentile90th = new ArrayList<String>(); // 백분위수_90th
			ArrayList<String> percentile95th = new ArrayList<String>(); // 백분위수_95th
			ArrayList<String> percentile99th = new ArrayList<String>(); // 백분위수_99th
			
			// 내용 별로 Array에 저장. index 번호가 같으면 하나의 정보!
            for(int i = 0; i < content.size(); i++) {
                JSONObject content_cut = (JSONObject)content.get(i);
				year.add((String)content_cut.get("year"));
                targetclsCd.add((String)content_cut.get("targetclsCd"));
				detailregionCd.add((String)content_cut.get("detailregionCd"));
				sex.add((String)content_cut.get("sex"));
				agesCd.add((String)content_cut.get("agesCd"));
				samplesize.add((String)content_cut.get("samplesize"));
				average.add((String)content_cut.get("average"));
				standarddeviation.add((String)content_cut.get("standarddeviation"));
				minvalue.add((String)content_cut.get("minvalue"));
				maxvalue.add((String)content_cut.get("maxvalue"));
				percentile1th.add((String)content_cut.get("percentile1th"));
				percentile5th.add((String)content_cut.get("percentile5th"));
				percentile10th.add((String)content_cut.get("percentile10th"));
				percentile25th.add((String)content_cut.get("percentile25th"));
				percentile50th.add((String)content_cut.get("percentile50th"));
				percentile75th.add((String)content_cut.get("percentile75th"));
				percentile90th.add((String)content_cut.get("percentile90th"));
				percentile95th.add((String)content_cut.get("percentile95th"));
				percentile99th.add((String)content_cut.get("percentile99th"));
            }
			
			// 내용 정리해서 출력
			StringBuilder printSet = new StringBuilder();
			for(int i = 0; i < content.size(); i++) {
				//printSet.append(i+1).append("번").append("\n"); // 번호
				printSet.append("연도: ").append(year.get(i)).append("\n");
				printSet.append("인체치수통계대상분류코드: ").append(targetclsCd.get(i)).append(" ").append("인체치수통계세부분류코드: ").append(targetclsCd.get(i)).append("\n");
				printSet.append("성별:").append(sex.get(i)).append("\n");
				printSet.append("인체치수통계연령대코드: ").append(agesCd.get(i)).append("\n");
				printSet.append("표본크기(측정수):").append(samplesize.get(i)).append("\n");
				printSet.append("평균:").append(average.get(i)).append(" ").append("표준편차:").append(standarddeviation.get(i)).append("\n");
				printSet.append("최소값:").append(minvalue.get(i)).append(" ").append("최대값:").append(maxvalue.get(i)).append("\n");
				printSet.append("백분위수_1th:").append(percentile1th.get(i)).append(" ");
				printSet.append("백분위수_5th:").append(percentile5th.get(i)).append(" ");
				printSet.append("백분위수_10th:").append(percentile10th.get(i)).append(" ");
				printSet.append("백분위수_25th:").append(percentile25th.get(i)).append(" ");
				printSet.append("백분위수_50th:").append(percentile50th.get(i)).append(" ");
				printSet.append("백분위수_75th:").append(percentile75th.get(i)).append(" ");
				printSet.append("백분위수_90th:").append(percentile90th.get(i)).append(" ");
				printSet.append("백분위수_95th:").append(percentile90th.get(i)).append(" ");
				printSet.append("백분위수_99th:").append(percentile90th.get(i)).append("\n").append("\n");
			}

			System.out.print(printSet);

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
