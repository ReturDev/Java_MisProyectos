package com.sergio.main.controller.windows.user;

import com.sergio.main.controller.windows.ItemsRootController;
import com.sergio.main.controller.windows.items.ItemBlueprintController;
import com.sergio.main.model.datasource.enums.ItemsType;
import com.sergio.main.model.datasource.items.VisualWork;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import com.sergio.main.model.repository.database.dao.UserDAOImpl;
import com.sergio.main.model.util.ItemsPaginationControllerUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class ItemsUserRootController extends ItemsRootController {

    private static ItemsUserRootController instance;
    protected final int BLUEPRINTS_QUANTITY = 20;


    private ItemsUserRootController(){

        super(new ItemsPaginationControllerUtilities());
        setShownItemType(ItemsType.ANIME);
        loadItemBlueprints();

    }

    public static ItemsUserRootController getInstance(){

        if (instance == null){

            instance = new ItemsUserRootController();

        }

        return instance;

    }


    @Override
    protected void onPreviousPage() {

        IPCU.onPreviousPage(itemsRoot, scrollPane);

    }

    @Override
    protected void onNextPage() {

        IPCU.onNextPage(itemsRoot, scrollPane);

    }

    @Override
    protected void loadItemBlueprints() {

        try {

            if (itemsBlueprints.isEmpty()) {

                for(int i = 0; i < BLUEPRINTS_QUANTITY; i++) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/items/itemBlueprintUserView.fxml"));
                    ItemBlueprintController controller = new ItemBlueprintController();

                    loader.setController(controller);

                    Pane itemPane = loader.load();
                    itemPane.getProperties().put("controller", controller);
                    itemsBlueprints.add(itemPane);

                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @Override
    protected void loadData() {

        User user =UserState.getUserLoggedData();
        List<VisualWork> list;

        if (IPCU.getShownItemType().equals(ItemsType.ANIME)){




        }else {



        }

    }

    @Override
    protected void prepareElements(List<VisualWork> list) {

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

        if (hasNextPage){

            btnNextPage.setDisable(false);

        }else {

            btnNextPage.setDisable(true);

        }


    }

}
