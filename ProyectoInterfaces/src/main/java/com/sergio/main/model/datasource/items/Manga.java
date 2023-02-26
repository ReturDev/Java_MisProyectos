package com.sergio.main.model.datasource.items;

public class Manga extends VisualWork {

    public Manga(){
        super();
    }

    public Manga(VisualWork visualWork){

        this.id = visualWork.id;
        this.name = visualWork.name;
        this.image = visualWork.image;

    }
	
}
