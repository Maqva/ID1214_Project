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
 * @author Magnus, Patrik.
 */
public class RecipeFileHandler {
    
    private final String FILE_PATH = "src/model/recipes.txt";
    private final String NEWLINE_DELIMITER ="##";
    
    /**
     * 
     * @param toSave
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void saveRecipeToFile(ArrayList<Recipe> toSave) throws FileNotFoundException, IOException{
        PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH)));
        for(Recipe r : toSave){
            outputWriter.println(r.getName());
            outputWriter.println(r.getInstructions().replaceAll("\n", NEWLINE_DELIMITER));
            String ingredientString ="";
            for (Ingredient i : r.getIngredients())
                ingredientString += (i.getName()+",");
            outputWriter.println(ingredientString);
        }
        outputWriter.flush();
        outputWriter.close();
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public ArrayList<Recipe> readFile() throws IOException{
        ArrayList<Recipe> recipesRead = new ArrayList();
        BufferedReader bfr = new BufferedReader( new FileReader(FILE_PATH));
        String line = bfr.readLine();
        while(line != null){
            String name = line;
            String instructions = bfr.readLine().replaceAll(NEWLINE_DELIMITER, "\n");
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
