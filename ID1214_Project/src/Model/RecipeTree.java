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

import java.util.ArrayList;

/**
 *
 * @author Magnus
 */
public class RecipeTree {
    private ArrayList<Node> root;
    
    private class Node{
        private Ingredient ingredient;
        private ArrayList<Recipe> recipes;
        private ArrayList<Node> branches;
        
        public String getIngredient(){
            return ingredient.getName();
        }
        
        public ArrayList<Recipe> getRecipe(){
            return recipes;
        }
    }
}
