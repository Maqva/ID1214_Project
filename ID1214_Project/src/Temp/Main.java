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
package Temp;

import Model.Ingredient;
import Model.PantryFileHandler;
import Model.Recipe;
import Model.RecipeFileReader;
import Model.RecipeTree;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Magnus
 */
public class Main {
    public static void main(String args[]){
        try {
            Ingredient[] temp1 = new Ingredient[4];
            temp1[0] = new Ingredient("Apple");
            temp1[1] = new Ingredient("Pebble");
            temp1[2] = new Ingredient("Orange");
            temp1[3] = new Ingredient("Apricot");
            PantryFileHandler temp = new PantryFileHandler();
            temp.savePantryToFile(temp1);
            Ingredient[] temp2 = temp.loadPantry();
            for(Ingredient i : temp2){
                System.out.println("Hittade ingrediensen: "+i.getName());
            }
            RecipeFileReader rfr = new RecipeFileReader();
            ArrayList<Recipe> tempList = rfr.readFile();
        } catch (FileNotFoundException ex) {
            System.err.println("filen hittads inte.");
        } catch (IOException ex) {
            System.err.println("Något gick fel vid fillsning.");
        }
        return;
    }
}
