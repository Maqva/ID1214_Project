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
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Magnus, Patrik.
 */
public class Recipe {
    private Ingredient[] ingredients;
    private String instructions;
    private String name;
    private int missingIngredients;
    
    /**
     * 
     * @param name
     * @param instructions
     * @param ingredients 
     */
    public Recipe(String name, String instructions, Ingredient[] ingredients){
        this.name = name;
        this.instructions = instructions;
        ArrayList<Ingredient> tempSorter = new ArrayList();
        tempSorter.addAll(Arrays.asList(ingredients));
        Collections.sort(tempSorter);
        this.ingredients = tempSorter.toArray(new Ingredient[tempSorter.size()]);
    }
    
    /**
     * 
     * @return 
     */
    public Ingredient[] getIngredients(){
        return ingredients;
    }
    
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return 
     */
    public String getInstructions(){
        return instructions;
    }
    
    /**
     * 
     * @param input 
     */
    public void setMissingIngredients(int input){
        missingIngredients = input;
    }
    
    /**
     * 
     */
    public int getMissedIngredients(){
        return missingIngredients;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        String output = "";
        output += (name);
        return output;
    }
}
