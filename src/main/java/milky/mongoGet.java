package milky;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import org.springframework.ui.Model;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Controller
public class mongoGet {

    @RequestMapping("/mongo")
    String home(Model model) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("kulcloudDb");
		MongoCollection<Document> collection = database.getCollection("ipaddrList");		Document myDoc = collection.find().first();
		//System.out.println(myDoc.toJson());
		MongoCursor<Document> cursor = collection.find().iterator();
		/*
		System.out.println(collection.find().iterator());	
		com.mongodb.MongoBatchCursorAdapter@46b1f633
		*/
		JSONArray jsonarray = new JSONArray();
		/* get type of myDoc
		System.out.println(myDoc.getClass().getName());
		*/
		try {
			while (cursor.hasNext()) {
				JSONObject jsonobj = new JSONObject();
				String json = cursor.next().toJson();
				jsonarray.add(json);
			}
		} finally {
			cursor.close();
		}
		model.addAttribute("mongodata",jsonarray);
		return "mongo";
    }

}
