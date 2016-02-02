package org.phabricator.java;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.collections.map.StaticBucketMap;
import org.codehaus.jackson.map.ObjectMapper;

import com.uber.jenkins.phabricator.conduit.ConduitAPIClient;
import com.uber.jenkins.phabricator.conduit.ConduitAPIException;

import net.sf.json.JSONObject;

public class MyTest {

	public static class Maniphest {

		public static class Info {
			
			public Date dateModified;

			public String[] projectPHIDs;

			public String objectName;

			public String status;

			public String statusName;

			public String uri;

			public String phid;

			public String[] ccPHIDs;

			public String id;

			public String priorityColor;

			public String title;

			public String[] auxiliary;

			public String priority;

			public String description;

			public String dateCreated;

			public String ownerPHID;

			public String[] dependsOnTaskPHIDs;

			public String authorPHID;

			public boolean isClosed;
		}
	}

	public abstract static class ConduitResult {
		public String error_code;
		public String error_info;
	}
	
	public static class ManiphestInfoResult extends ConduitResult {
		public Maniphest.Info result;
	}

	public static void main(String[] args) {

		ConduitAPIClient x = new ConduitAPIClient("http://s02.labs.h3.se/conduit/method",
				"api-kp7flwjp55ysb3p5plcqud4twc4k");

		JSONObject params = new JSONObject();

		params.element("task_id", "1");

		try {
			JSONObject perform = x.perform("maniphest.info", params);

			ObjectMapper mapper = new ObjectMapper();

			System.out.println(perform);
			
			System.out.println(perform.get("result"));

			ManiphestInfoResult readValue = mapper.readValue(perform.toString(), ManiphestInfoResult.class);

			// System.out.println(readValue.result);

			System.out.println(readValue.result.ownerPHID);
			
			System.out.println(readValue.result.dateModified);
			
			System.out.println(System.currentTimeMillis());
			
			System.out.println(new Date(Long.parseLong("1408269175")  *1000L));
//			
//			1451481928308;
//			1408269175
			


		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConduitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
