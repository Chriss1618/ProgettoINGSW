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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.vavr.control.Try;

public class Manager implements SubController {

    //SYSTEM
    private static final String TAG = "Manager_MenuFragments";
    public Integer[] LIST_INDEX_VIEW;
    public int MAIN;
    private final SourceInfo sourceInfo;

    //LAYOUT
    public final Context                    context;
    protected final ArrayList<ViewLayout>   Views;
    protected final View                    View;

    //FUNCTIONAL
    protected final BottomBarListener       bottomBarListener;
    protected final FragmentManager         fragmentManager;
    protected final ManagerActionFactory    ManagerAction;
    protected final ManagerRequestFactory   ManagerRequest;
    public Integer      onMain;
    public Integer      from;
    public Integer      positionList = 0;
    public SecureRandom rand ;
    public int lastRandom = -1;

    //DATA
    private Object data;

    public Manager(SourceInfo sourceInfo,Context context, View view, FragmentManager fragmentManager, BottomBarListener bottomBarListener) {
        Log.d(TAG, "Manager: Costruttore");
        Views = new ArrayList<>();
        rand = new SecureRandom();
        this.sourceInfo = sourceInfo;
        this.ManagerAction = new ManagerActionFactory();
        this.ManagerRequest = new ManagerRequestFactory();

        this.context                = context;
        this.View                   = view;
        this.fragmentManager        = fragmentManager;
        this.bottomBarListener      = bottomBarListener;

        Log.d(TAG, "Manager: TypeManager : -> "+ sourceInfo.getIndex_TypeManager());
        LIST_INDEX_VIEW = Objects.requireNonNull(ControlMapper.classManagerToView.get(sourceInfo.getIndex_TypeManager()));
        MAIN = LIST_INDEX_VIEW[0];
        addViews();

        Log.d(TAG, "Manager: FINE Costruttore");
    }

    public SourceInfo getSourceInfo() {
        return sourceInfo;
    }
    public Object getData(){ return data;}
    public void setData(Object data){
        this.data = data;
    }
    private void addViews(){
        for (int indexView : LIST_INDEX_VIEW)
            try{ Views.add( new ViewFactory().createView(sourceInfo.getIndex_TypeManager(),indexView,this)); }
            catch (IllegalAccessException | InstantiationException e) { Log.e(TAG, "Manager_MenuFragments: ", e); }
    }

    //ShowPages
    private void loadFragmentAsMain(int indexView){
        fragmentManager.beginTransaction()
                .replace(View.getId(), (Fragment) Views.get(positionList), String.valueOf(indexView))
                .setReorderingAllowed(true)
                .commit();

        getSourceInfo().setIndex_TypeView(indexView);
    }
    private void loadFragmentAsNormal(int indexView){
        getSourceInfo().setIndex_TypeView(indexView);
        fragmentManager.beginTransaction()
                .replace(View.getId(), (Fragment) Views.get(positionList), String.valueOf(indexView))
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showMain(){
        Log.d(TAG, "Manager: TypeManager : -> "+ sourceInfo.getIndex_TypeManager());
        onMain = MAIN;
        showView(MAIN,null);
    }

    public void HandleAction(Action action){
        new Thread(() -> ManagerAction.handleAction(action) ).start();
    }

    public void HandleRequest(Request request){
        new Thread(() -> ManagerRequest.handleRequest(request) ).start();

    }

    @Override
    public void changeOnMain(int indexMain, Object msg) {
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(300));
        showView(indexMain,msg);
    }
    private void showView(int indexFragment, Object msg){
        from = onMain;
        data = msg;

        this.onMain = getIndexFragment( indexFragment );

        if( onMain == MAIN ) loadFragmentAsMain( onMain );
        else loadFragmentAsNormal( onMain);
    }

    private int getIndexFragment(int indexFragment){
        for(int position = 0 ; position < LIST_INDEX_VIEW.length; position++){
            if(indexFragment == LIST_INDEX_VIEW[position] ){
                this.positionList = position;
                return LIST_INDEX_VIEW[position];
            }
        }
        return 0;
    }

    @Override
    public void closeView() {
        from = onMain;
        Views.get(positionList).EndAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(fragmentManager::popBackStack,300);
        Log.d(TAG, "closeView: TypeManager->"+sourceInfo.getIndex_TypeManager());
        onMain = Objects.requireNonNull(ViewFactory.PreviousIndexMapper.get(sourceInfo.getIndex_TypeManager())).get(sourceInfo.getIndex_TypeView());
    }

    //FUNCTIONAL
    public void hideBottomBar(){
        bottomBarListener.hideBottomBar();
    }
    public void showBottomBar(){
        bottomBarListener.showBottomBar();
    }

}
