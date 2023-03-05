package com.sergio.main.model.util;

import com.sergio.main.model.datasource.enums.ItemsType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class ItemsPaginationControllerUtilities {

    private int page;
    private ItemsType shownItemType;
    private boolean hasNextPage;
    private int itemsPerPage;
    private int numFirstItem;

    public ItemsPaginationControllerUtilities(){}

    public ItemsPaginationControllerUtilities(int itemsPerPage){

        this.itemsPerPage = itemsPerPage;

    }

    public void onPreviousPage(Pane itemsRoot, ScrollPane scrollPane) {

        page--;
        setNumFirstItem();
        cleanRoot(itemsRoot,scrollPane);

    }

    public void onNextPage(Pane itemsRoot, ScrollPane scrollPane) {

        page++;
        setNumFirstItem();
        cleanRoot(itemsRoot, scrollPane);

    }

    private void cleanRoot(Pane itemsRoot, ScrollPane scrollPane){

        itemsRoot.getChildren().clear();
        scrollPane.vvalueProperty().set(scrollPane.getVmin());

    }

    public void resetButtons(Button btnNextPage, Button btnPreviousPage){

        page = 1;

        setNumFirstItem();

        btnNextPage.setDisable(true);

        btnPreviousPage.setDisable(true);

    }

    public void resetButtons(Button btnNextPage, Button btnPreviousPage, Pane itemsRoot, ScrollPane scrollPane){

        cleanRoot(itemsRoot, scrollPane);
        resetButtons(btnNextPage,btnPreviousPage);

    }

    public void checkNextPageLocalPagination(int totalItems){

        if (totalItems - itemsPerPage > numFirstItem){

            System.out.println(totalItems);
            System.out.println(numFirstItem);

            hasNextPage = true;

        }else{

            hasNextPage = false;

        }

    }

    private void setNumFirstItem(){

        this.numFirstItem = (page-1) * itemsPerPage;

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

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNumFirstItem() {
        return numFirstItem;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }
}
