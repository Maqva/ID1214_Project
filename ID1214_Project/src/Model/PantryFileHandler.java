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
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 *
 * @author Magnus
 */
public class PantryFileHandler{
    private final String FILE_PATH = "pantry.txt";
    
    public void savePantryToFile(Ingredient[] toSave) throws IOException{
        PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH)));
        for(Ingredient i : toSave){
            String output = i.getName();
            outputWriter.println(output);
        }
        outputWriter.flush();
        outputWriter.close();
    }
    
    public Ingredient[] loadPantry() throws FileNotFoundException, IOException{
        BufferedReader bfr = new BufferedReader(new FileReader(FILE_PATH));
        String line = bfr.readLine();
        ArrayList<Ingredient> output = new ArrayList();
        while(line != null){
            Ingredient temp = new Ingredient(line);
            output.add(temp);
            line = bfr.readLine();
        }
        bfr.close();
        int size = output.size();
        if (size > 0)
            return output.toArray(new Ingredient[size]);
        else
            return null;
    }
}
