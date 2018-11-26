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

/**
 *
 * @author Magnus
 */
public class Ingredient implements Comparable<Ingredient>{
    private String name;
    private int weight;
    
    public Ingredient(String name){
        this.name = name;
        weight = 0;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns a sorting "weight" comparison between two Ingredient instances.
     * If the calling instance is Heavier, this will return a Negative value.
 If both instances are of equal weightDiff this will return 0.
 If the calling instance is Lighter, this will return a Positive value.
     * @param ing the ingredient to compare against.
     * @return the integer value of the Weight difference between the two ingredients.
     */
    @Override
    public int compareTo(Ingredient ing) {
        int weightDiff = ing.weight - this.weight;
        if (weightDiff == 0)
            return this.getName().compareToIgnoreCase(ing.getName());
        else
            return weightDiff;
    }

    public void setWeight(int i) {
        this.weight = i;
    }
    
}
