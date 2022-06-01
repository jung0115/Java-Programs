// 산업통상자원부_고령자 및 장애인 배려 설계 지침 상세
// https://www.data.go.kr/data/15058916/openapi.do
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class seniorGuideDetail {
    public static void main(String[] args) {
	
    	// 인증키 (개인이 받아와야함)
    	String key = "78342ef69415faf144312f3565a3dbbf";
		
    	// 파싱한 데이터를 저장할 변수
    	String result = "";

    	try {
    		URL url = new URL("http://www.ibtk.kr/seniorGuideDetail/"+ key + "?model_query_pageable={enable:false}");

    		BufferedReader bf;

    		bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

    		result = bf.readLine();

            //System.out.print(result);

            JSONParser jsonParser = new JSONParser();
        	//JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray content = (JSONArray)jsonParser.parse(result);
            //System.out.print(content);

            
			ArrayList<String> title = new ArrayList<String>(); // 제목
			ArrayList<String> contents = new ArrayList<String>(); // 내용
			ArrayList<String> imgTitle = new ArrayList<String>(); // 이미지 제목
			ArrayList<String> imgUrl = new ArrayList<String>(); // 이미지 Url
			
			// 내용 별로 Array에 저장. index 번호가 같으면 하나의 정보!
            for(int i = 0; i < content.size(); i++) {
                JSONObject content_cut = (JSONObject)content.get(i);
				title.add((String)content_cut.get("title")); // 제목
                contents.add((String)content_cut.get("contents")); // 내용
				imgTitle.add((String)content_cut.get("imgTitle")); // 이미지 제목
				imgUrl.add((String)content_cut.get("imgUrl")); // 이미지 Url
            }

			// 내용 정리해서 출력
			StringBuilder printSet = new StringBuilder();
			for(int i = 0; i < content.size(); i++) {
				printSet.append(i+1).append("번").append("\n"); // 번호
				printSet.append("제목: ").append(title.get(i)).append("\n");
				printSet.append("- 내용").append("\n").append(contents.get(i)).append("\n");
				printSet.append("- 이미지").append("\n").append(imgTitle.get(i)).append("\n").append(imgUrl.get(i)).append("\n").append("\n");
			}

			System.out.print(printSet);
            

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
