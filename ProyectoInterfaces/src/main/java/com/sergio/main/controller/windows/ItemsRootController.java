package com.sergio.main.controller.windows;

import com.sergio.main.controller.windows.items.ItemBlueprintController;
import com.sergio.main.model.datasource.enums.ItemsType;
import com.sergio.main.model.datasource.items.VisualWork;
import com.sergio.main.model.util.ItemsPaginationControllerUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase abstracta de la que heredarán los controladores de las vistas donde se mostrarán los animes y mangas.
 */
public abstract class ItemsRootController {

    protected final ItemsPaginationControllerUtilities IPCU;
    protected List<Pane> itemsBlueprints;

    protected ItemsRootController(ItemsPaginationControllerUtilities ipcu) {

        IPCU = ipcu;
        this.itemsBlueprints =  new ArrayList<>();

    }

    @FXML
    protected abstract void onPreviousPage();

    @FXML
    protected abstract void onNextPage();

    protected abstract void loadItemBlueprints();

    protected abstract void loadData();

    protected void prepareElements(List<VisualWork> list, Pane itemsRoot,Button btnNextPage,Button btnPreviousPage){

        for(int i = 0; i < list.size(); i++){

            Pane itemBlueprint = itemsBlueprints.get(i);
            ItemBlueprintController controller = (ItemBlueprintController) itemBlueprint.getProperties().get("controller");
            controller.setItem(list.get(i));
            itemsRoot.getChildren().add(itemBlueprint);

        }

        if (IPCU.getPage() == 1){

            btnPreviousPage.setDisable(true);

        }else {

            btnPreviousPage.setDisable(false);

        }

        if (IPCU.hasNextPage()){

            btnNextPage.setDisable(false);

        }else {

            btnNextPage.setDisable(true);

        }

    }

    public void setShownItemType(ItemsType shownItemType) {

        IPCU.setShownItemType(shownItemType);

    }

    public abstract void resetButtons();

}
