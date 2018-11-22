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
import java.util.Comparator;

/**
 *
 * @author Magnus
 */
public class Recipe {
    private Ingredient[] ingredients;
    private String instructions;
    private String name;
    
    public Recipe(String name, String instructions, Ingredient[] ingredients){
        this.name = name;
        this.instructions = instructions;
        ArrayList<Ingredient> tempSorter = new ArrayList();
        for(Ingredient i : ingredients)
            tempSorter.add(i);
        Collections.sort(tempSorter, new Comparator<Ingredient>(){
            @Override
            public int compare (Ingredient a, Ingredient b){
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });
        this.ingredients = new Ingredient[tempSorter.size()];
        for (int i = 0; i < tempSorter.size(); i++){
            this.ingredients[i] = tempSorter.get(i);
        }
    }
    
    public Ingredient[] getIngredients(){
        return ingredients;
    }
    
    public String getName() {
        return name;
    }
}
