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

/**
 *
 * @author Sothe
 */
public class UrlReader{
     
    public String[] getHTMLContent(String webaddress) throws Exception {
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
            httpCon.disconnect();
        }
        return content.toArray(new String[1]);
    }
}
