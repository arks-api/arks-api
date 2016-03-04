package org.cbsa.api.controller.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class RelatedWordScanner {

    private static final Logger logger = Logger
            .getLogger(RelatedWordScanner.class.getName());

    static {
        logger.setLevel(Level.INFO);
    }

    public static List<String> getRelatedWords(String searchQuery) {

        String[] sentence = searchQuery.toLowerCase().split("\\s");

        HashSet<String> hs = new HashSet<String>();

        for (String s : sentence) {

            hs.add(s);
        }

        logger.info("keywords before filtering are : " + hs + "\n\n");

        ArrayList<String> list = new ArrayList<String>(hs);
        // System.out.println("Arraylist is : "+list+"\n\n");

        ArrayList<String> flist = new ArrayList<>();
        JsonArray jj = null;

        ArrayList<String> abc = new ArrayList<>();

        abc.add("noun");
        abc.add("adjective");
        abc.add("verb");
        abc.add("rel");

        for (int i = 0; i < list.size(); i++) {

            for (String s : flist) {
                if (list.get(i).matches(s)) {

                    list.remove(i);
                    break;
                }
            }
        }

        logger.info("after filtering list is : " + list + "\n\n");
        for (int j = 0; j < list.size(); j++) {
            String url1 = "http://words.bighugelabs.com/api/2/a8f01d5804388be2ad7dd2e77082b3d6/";
            String url2 = "/json";

            try {
                URL syn = new URL(url1 + list.get(j) + url2);

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        syn.openStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {

                    Gson gson = new com.google.gson.Gson();

                    com.google.gson.JsonObject result = gson.fromJson(
                            inputLine, JsonElement.class).getAsJsonObject();
                    for (int i = 0; i < abc.size(); i++) {
                        if (result.get(abc.get(i)) != null) {
                            Gson gson1 = new com.google.gson.Gson();
                            com.google.gson.JsonObject result1 = gson1
                                    .fromJson(result.get(abc.get(i)),
                                            JsonElement.class)
                                    .getAsJsonObject();
                            JsonElement je = result1.get("syn");
                            jj = (JsonArray) je;

                            LinkedHashMap<String, ArrayList<String>> hm = new LinkedHashMap<String, ArrayList<String>>();

                            for (int k = 0; k < jj.size(); k++) {

                                flist.add(jj.get(k).getAsString());

                            }

                            hm.put(list.get(j), flist);

                        }
                    }
                }

                in.close();
            } catch (Exception e) {
            }
        }

        logger.info("Final keywords " + flist.toString());

        return flist;

    }
}
