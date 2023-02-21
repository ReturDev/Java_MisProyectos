package com.sergio.main.model.util;

import com.sergio.main.model.datasource.enums.ItemsType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class ItemsPaginationControllerUtilities {

    private int page;
    private ItemsType shownItemType;

    public ItemsPaginationControllerUtilities(){}

    public void onPreviousPage(Pane itemsRoot, ScrollPane scrollPane) {

        page--;
        cleanRoot(itemsRoot,scrollPane);

    }

    public void onNextPage(Pane itemsRoot, ScrollPane scrollPane) {

        page++;
        cleanRoot(itemsRoot, scrollPane);

    }

    private void cleanRoot(Pane itemsRoot, ScrollPane scrollPane){

        itemsRoot.getChildren().clear();
        scrollPane.vvalueProperty().set(scrollPane.getVmin());

    }

    public void resetButtons(Button btnNextPage, Button btnPreviousPage){

        page = 1;

        btnNextPage.setDisable(true);

        btnPreviousPage.setDisable(true);


    }

    public int getPage() {
        return page;
    }

    public ItemsType getShownItemType() {
        return shownItemType;
    }

    public void setShownItemType(ItemsType shownItemType) {
        this.shownItemType = shownItemType;
    }
}
