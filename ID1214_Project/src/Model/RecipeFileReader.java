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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Magnus
 */
public class RecipeFileReader {
    
    BufferedReader bfr;
    private final String PATH = "src/model/recipes.txt";

    public RecipeFileReader() throws FileNotFoundException {
        this.bfr = new BufferedReader(new FileReader(PATH));
    }
    
    public ArrayList<Recipe> readFile() throws IOException{
        ArrayList<Recipe> recipesRead = new ArrayList();
        String line = bfr.readLine();
        while(line != null){
            String name = line;
            String instructions = bfr.readLine();
            String[] ingredients = bfr.readLine().split(",");
            Ingredient[] ingArray = new Ingredient[ingredients.length];
            for(int i = 0; i < ingredients.length; i++){
                ingArray[i] = new Ingredient(ingredients[i]);
            }
            recipesRead.add(new Recipe(name, instructions, ingArray));
            line = bfr.readLine();
        }
        bfr.close();
        return recipesRead;
    }
    
}
