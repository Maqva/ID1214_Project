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
package id1214_project_fx;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * A custom-made <code>JavaFX</code> component, a subclass of Pane that allows the user to toggle it to "selected" or "unselected".
 * Also contains a <code>Label</code> with a name associated with a <code>Ingredient</code> instance in <code>pantry</code>.
 * @author Magnus, Patrik
 */
public class SelectablePane extends Pane{
    private boolean isSelected;
    private Label text;
    
    /**
     * Initialiser of <code>SelectablePane</code>.
     * @param input The text to be written to <code>text</code>.
     */
    public SelectablePane (String input){
        text = new Label();
        text.setText(input);
        this.getChildren().add(text);
        this.isSelected = false;
        initialiseStyleAndEvents();
        this.setMaxWidth(250);
        this.setMinHeight(25);
    }

    /**
     * Getter for <code>isSelected</code>
     * @return Current value of <code>isSelected</code>.
     */
    public boolean isSelected(){
        return isSelected;
    }

    /**
     * Getter for the content of <code>text</code>.
     * @return The String body of <code>text</code>.
     */
    public String getLabel(){
        return text.getText();
    }

    /**
     * Method called at initialisation. Sets the style of the <code>SelectablePane</code> and sets all mouse-event handlers for when the user interacts with it.
     */
    private void initialiseStyleAndEvents(){
        this.setStyle("-fx-border-color: LIGHTGREY; -fx-background-color: #F2F2F2");
        this.setOnMouseClicked((MouseEvent event)-> {
            this.isSelected = !this.isSelected;
            String style = (this.isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2;-fx-border-color: LIGHTGREY";
            this.setStyle(style);
        });
        this.setOnMouseEntered((MouseEvent event) -> {
            String style = (this.isSelected)?"-fx-background-color: #D0F5A9;-fx-border-color: GREEN":"-fx-background-color: #F2F2F2;-fx-border-color: BLACK";
            this.setStyle(style);
        });
        this.setOnMouseExited((MouseEvent me) -> {
            String style = (this.isSelected)?"-fx-background-color: #D0F5A9; -fx-border-color: RED":"-fx-background-color: #F2F2F2; -fx-border-color: LIGHTGREY";
            this.setStyle(style);
        });
    }
}
