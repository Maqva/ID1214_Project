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
import Model.RecipeTree;

/**
 *
 * @author Magnus
 */
public class Main {
    public static void main(String args[]){
        Ingredient[] temp1 = new Ingredient[4];
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
        int appleApple = temp1[0].compareTo(temp2[0]);
        int appleApricot = temp1[0].compareTo(temp1[3]);
        int orangeApple = temp1[2].compareTo(temp1[0]);
        System.out.println("jämförelsen mellan de två äpplena gav: "+appleApple
                + "\njämförelsen mellan Apple och Apricot gav: "+appleApricot
                + "\njämförelsen mellan Orange och Apple gav: " + orangeApple);
    }
}
