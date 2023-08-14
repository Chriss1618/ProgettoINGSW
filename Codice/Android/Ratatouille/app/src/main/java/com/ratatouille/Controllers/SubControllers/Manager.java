package com.ratatouille.Controllers.SubControllers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.SubController;
import com.ratatouille.Models.Interfaces.ViewLayout;
import com.ratatouille.Models.Listeners.BottomBarListener;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Events.SourceInfo;
import com.ratatouille.Views.ViewFactory;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class Manager implements SubController {
    //SYSTEM
    private static final String TAG = "Manager_MenuFragments";

    //LAYOUT
    public final Context                    context;
    public final BottomBarListener          bottomBarListener;
    protected final ArrayList<ViewLayout>   ViewsFragments;
    protected final View                    ViewContainer;

    //FUNCTIONAL
    protected final SourceInfo              sourceInfo;
    protected final FragmentManager         fragmentManager;
    protected final ManagerActionFactory    ManagerAction;
    protected final ManagerRequestFactory   ManagerRequest;

    public Integer[] LIST_INDEX_VIEW ;
    public final int MAIN_VIEW_INDEX;

    public Integer      IndexOnMain;
    public Integer      IndexFrom;

    //DATA
    private Object data;

    public Manager(SourceInfo sourceInfo,Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        this.ViewsFragments     = new ArrayList<>();
        this.ManagerAction      = new ManagerActionFactory();
        this.ManagerRequest     = new ManagerRequestFactory();

        this.context                = context;
        this.ViewContainer          = view;
        this.sourceInfo             = sourceInfo;
        this.fragmentManager        = fragmentManager;
        this.bottomBarListener      = bottomBarListener;

        LIST_INDEX_VIEW = ControlMapper.classManagerToView.get( sourceInfo.getIndex_TypeManager() );
        MAIN_VIEW_INDEX = Objects.requireNonNull( LIST_INDEX_VIEW )[0];

        addViews();
    }
    private void addViews(){
        for (int indexView : LIST_INDEX_VIEW)
            try{ ViewsFragments.add( new ViewFactory().createView(sourceInfo.getIndex_TypeManager(),indexView,this)); }
            catch (IllegalAccessException | InstantiationException e) { Log.e(TAG, "Manager_MenuFragments: ", e); }
    }

    public SourceInfo getSourceInfo() {
        return sourceInfo;
    }
    public Object getData(){ return data;}
    public void setData(Object data){
        this.data = data;
    }

    //ShowPages
    private void loadFragmentAsMain(int positionList){
        getSourceInfo().setIndex_TypeView(IndexOnMain);
        fragmentManager.beginTransaction()
                .replace(ViewContainer.getId(), (Fragment) ViewsFragments.get(positionList), String.valueOf(IndexOnMain))
                .setReorderingAllowed(true)
                .commit();
    }
    private void loadFragmentAsNormal(int positionList){
        getSourceInfo().setIndex_TypeView(IndexOnMain);
        fragmentManager.beginTransaction()
                .replace(ViewContainer.getId(), (Fragment) ViewsFragments.get(positionList), String.valueOf(IndexOnMain))
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showMain(){
        IndexOnMain = MAIN_VIEW_INDEX;
        showView(MAIN_VIEW_INDEX,null);
    }

    public void HandleAction(Action action){
        action.setManager(this);
        action.setSourceInfo(getSourceInfo());
        new Thread(() -> ManagerAction.handleAction(action) ).start();
    }
    public void HandleRequest(Request request){
        request.setManager(this);
        request.setSourceInfo(getSourceInfo());
        new Thread(() -> ManagerRequest.handleRequest(request) ).start();
    }

    @Override
    public void changeOnMain(int indexMain, Object msg) {
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(300));
        showView(indexMain,msg);
    }

    private void showView(int indexFragment, Object msg){
        IndexFrom = IndexOnMain;
        data = msg;
        int position =  getPositionView( indexFragment );

        if( IndexOnMain == MAIN_VIEW_INDEX ) loadFragmentAsMain( position );
        else loadFragmentAsNormal( position );
    }

    private int getPositionView(int indexFragment){
        for(int position = 0 ; position < LIST_INDEX_VIEW.length; position++){
            if(indexFragment == LIST_INDEX_VIEW[position] ){
                IndexOnMain = LIST_INDEX_VIEW[position];
                return position;
            }
        }
        return 0;
    }

    @Override
    public void closeView() {
        int temp = IndexFrom;
        IndexFrom = IndexOnMain;
        ViewsFragments.get( getPositionView(IndexOnMain) ).EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(fragmentManager::popBackStack,300);
        IndexOnMain = temp;
        getSourceInfo().setIndex_TypeView(IndexOnMain);
    }

    //FUNCTIONAL
    public void hideBottomBar(){
        bottomBarListener.hideBottomBar();
    }
    public void showBottomBar(){
        bottomBarListener.showBottomBar();
    }

}
