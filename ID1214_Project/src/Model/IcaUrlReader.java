/*
 * Copyright (C) 2018 Magnus Qvarnstrom, Patrik Karlsten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sothe
 */
public class IcaUrlReader{
    private static final String INIT_RECIPE_LIST_PATTERN = "\"ingredients__list\"";
    private static final String INGREDIENT_PATTERN = "amount=\"(\\d+[\\.\\d]\\d*)\".+type=\"(.*)\">(?:\\d+ *\\d*)* (?:\\2 )*(.+)</s";
    private static final String LIST_END_PATTERN = "</ul>";
    
    public void getURLIngredients(String url){
        try {
            Pattern listInitPattern = Pattern.compile(INIT_RECIPE_LIST_PATTERN);
            Pattern listIngredientItemPattern = Pattern.compile(INGREDIENT_PATTERN);
            Pattern listEnd = Pattern.compile(LIST_END_PATTERN);
            String[] strings = getHTMLContent(url);
            int inList = 0;
            for(String s : strings){
                //System.out.println(s);
                if(inList == 0){
                    if (listInitPattern.matcher(s).find()){
                        System.out.println("Hittade ingrediens listan.");
                        inList = 1;
                    }
                }
                else{
                    Matcher ingredient = listIngredientItemPattern.matcher(s);
                    if(ingredient.find()){
                        System.out.print("hittade; Antal: \""+ingredient.group(1)+"\" mängd typ: \""+ingredient.group(2)+"\" ingrediens: \""+ingredient.group(3)+"\"\n");
                    }
                    else if (listEnd.matcher(s).find()){
                        inList = 0;
                        System.out.println("Lämnade ingrediens listan.");
                    }
                }
            }
        } 
        catch (Exception ex) {
            System.err.println("Något gick fel.");
        }
    }
           
    private String[] getHTMLContent(String webaddress) throws Exception {
        URL url = new URL(webaddress);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        ArrayList<String> content;
        content = new ArrayList();
        try (BufferedReader strRdr = new BufferedReader(new InputStreamReader(httpCon.getInputStream()))) {
            String temp = strRdr.readLine();
            while(temp != null){
                temp = temp.replaceAll("^\\s+", "");
                if (! temp.isEmpty())
                    content.add(temp);
                temp = strRdr.readLine();
            }
            strRdr.close();
        }
        System.out.println("Handled "+url);
        return content.toArray(new String[1]);
    }
}
