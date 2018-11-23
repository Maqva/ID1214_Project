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
            /*Ingredient[] temp1 = new Ingredient[4];
            RecipeTree tempTree = new RecipeTree();
            temp1[0] = new Ingredient("Apple");
            temp1[1] = new Ingredient("Pear");
            temp1[2] = new Ingredient("Orange");
            temp1[3] = new Ingredient("Apricot");
            Recipe tempR1 = new Recipe("Fruit Sallad", "just mix it up", temp1);
            Ingredient[] temp2 = new Ingredient[3];
            temp2[0] = new Ingredient("Apple");
            temp2[1] = new Ingredient("Sugar");
            temp2[2] = new Ingredient("Flour");
            Recipe tempR2 = new Recipe("Apple Pie", "SHove it in the oven", temp2);
            tempTree.addRecipe(tempR1);
            tempTree.addRecipe (tempR2);
            Ingredient[] toSearch = {new Ingredient("apricot")};
            Recipe[] tempOutput = (tempTree.searchRecipesInTree(toSearch, 4));*/
            RecipeFileReader rfr = new RecipeFileReader();
            ArrayList<Recipe> tempList = rfr.readFile();
        System.out.println(tempList);
        } catch (FileNotFoundException ex) {
            System.err.println("filen hittads inte.");
        } catch (IOException ex) {
            System.err.println("Något gick fel vid fillsning.");
        }
        return;
    }
}
