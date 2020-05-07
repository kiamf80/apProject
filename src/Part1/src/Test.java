import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Card.initCards();
		Hero.initHero();
		Player.initPlayers();
		CLI.enterGame();
		while(true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String command = br.readLine();
			CLI.ExecuteCommand(command);
		}

	}

}
