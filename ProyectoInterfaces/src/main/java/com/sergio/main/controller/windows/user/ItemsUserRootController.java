package com.sergio.main.controller.windows.user;

import com.sergio.main.controller.windows.ItemsRootController;
import com.sergio.main.controller.windows.items.ItemBlueprintController;
import com.sergio.main.controller.windows.user.data.StatusItemsMenuUserController;
import com.sergio.main.model.datasource.enums.ActionSelected;
import com.sergio.main.model.datasource.enums.ItemsType;
import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.datasource.items.VisualWork;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import com.sergio.main.model.repository.api.dao.anime.AnimeDAOImpl;
import com.sergio.main.model.repository.api.dao.manga.MangaDAOImpl;
import com.sergio.main.model.util.ItemsPaginationControllerUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemsUserRootController extends ItemsRootController {

    private static final int BLUEPRINTS_QUANTITY = 20;

    private static ItemsUserRootController instance;

    @FXML
    protected Button btnPreviousPage;
    @FXML
    protected Button btnNextPage;
    @FXML
    protected FlowPane itemsRoot;
    @FXML
    protected ScrollPane scrollPane;

    private ItemsUserRootController(){

        super(new ItemsPaginationControllerUtilities(BLUEPRINTS_QUANTITY));
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

        User user = UserState.getUserLoggedData();
        List<VisualWork> list = new ArrayList<>();
        StatusItemsMenuUserController status = StatusItemsMenuUserController.getInstance();

        if (IPCU.getShownItemType().equals(ItemsType.ANIME)){

            loadAnime(list,user,status);

        }else {

            loadManga(list, user, status);

        }

        prepareElements(list, itemsRoot, btnNextPage, btnPreviousPage);

    }

    private void loadAnime(List<VisualWork> list, User user, StatusItemsMenuUserController status){


        AnimeDAOImpl dao = new AnimeDAOImpl();

        if (status.getActionSelected().equals(ActionSelected.FAVOURITE)){

            addAnime(list,user.getAnimeFavourites(),dao);

        }else if (status.getActionSelected().equals(ActionSelected.FOLLOWING)){

            addAnime(list,user.getAnimeFollowing(),dao);

        }

    }

    private void addAnime(List<VisualWork> list, List<Integer> idList, AnimeDAOImpl dao){

        for (int i = IPCU.getNumFirstItem(); i < idList.size(); i++){

            int id = idList.get(i);
            Anime anime = dao.getAnimeByID(id);
            list.add(anime);

        }

    }

    private void loadManga(List<VisualWork> list, User user, StatusItemsMenuUserController status){

        MangaDAOImpl dao = new MangaDAOImpl();

        if (status.getActionSelected().equals(ActionSelected.FAVOURITE)){

            addManga(list, user.getMangaFavourites(), dao);

        }else if (status.getActionSelected().equals(ActionSelected.FOLLOWING)){

            addManga(list, user.getMangaFollowing(), dao);

        }

    }

    private void addManga(List<VisualWork> list, List<Integer> idList, MangaDAOImpl dao){

        for (int i = IPCU.getNumFirstItem(); i < idList.size(); i++){

            int id = idList.get(i);
            Manga manga = dao.getMangaByID(id);
            list.add(manga);

        }

    }

    @Override
    public void resetButtons() {
        IPCU.resetButtons(btnNextPage, btnPreviousPage);
    }

}
