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
import java.util.Collections;

/**
 *
 * @author Magnus, Patrik.
 */
public class Pantry {
    private ArrayList<Ingredient> inPantry;
    
    public Pantry(){
        inPantry = new ArrayList();
    }
    
    /**
     * 
     * @param toRemove 
     */
    public void removeIngredients(ArrayList<Ingredient> toRemove){
        for(Ingredient i : toRemove){
            for (int a = 0; a < inPantry.size(); a++){
                int comparisonWeight = i.compareTo(inPantry.get(a));
                if(comparisonWeight < 0)
                    break;
                else if (comparisonWeight == 0)
                    inPantry.remove(a);
                else
                    continue;
            }
        }
    }
    
    /**
     * 
     * @param ing 
     */
    public void addIngredient(Ingredient ing){
        inPantry.add(ing);
        Collections.sort(inPantry);
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Ingredient> getPantryIngredients(){
        return inPantry;
    }
}
