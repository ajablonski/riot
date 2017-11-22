package uk.co.riot;
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */ 

import java.util.HashMap;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Angie
 */
public class MainContainer extends StackPane {
    
	//Holds the screens to be displayed
    private HashMap<String, Node> screens = new HashMap<>();
    public static ControlledScreen mCurrentScreen;
    
    public MainContainer() {
        super();
    }

    //Add the screen to the collection
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) {
        return screens.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenController = (ControlledScreen) myLoader.getController();
            myScreenController.setScreenParent(this);            
            if(!getChildren().isEmpty())
            	getChildren().remove(0); //remove the displayed screen
            
            getChildren().add(0, loadScreen);     //display the new screen
            mCurrentScreen = myScreenController;
            //ApplicationData.getSingleton().setCurrentScreen(myScreenController);
            return true;
        } catch (Exception e) {
            System.err.println("Could not load screen: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //This method tries to display the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    public boolean setScreen(final String name) {       
        if (screens.get(name) != null) {   //screen loaded
//            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
//                Timeline fade = new Timeline(
//                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
//                        new KeyFrame(new Duration(500), new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent t) {
//                        getChildren().remove(0);                    //remove the displayed screen
//                        getChildren().add(0, screens.get(name));     //add the screen
//                        Timeline fadeIn = new Timeline(
//                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
//                                new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
//                        fadeIn.play();
//                    }
//                }, new KeyValue(opacity, 0.0)));
//                fade.play();
                
                getChildren().remove(0);                    //remove the displayed screen
                getChildren().add(0, screens.get(name));     //add the screen               

            } else {
                //setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
//                Timeline fadeIn = new Timeline(
//                       new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
//                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
//                fadeIn.play();
            }
            return true;
        } else {
            System.err.println("Screen hasn't been loaded!");
            return false;
        }
    }
}

