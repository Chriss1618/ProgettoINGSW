package com.ratatouille.Controllers.SubControllers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.ratatouille.Controllers.ControlMapper;
import com.ratatouille.Models.Events.Action.Action;
import com.ratatouille.Models.Interfaces.ISubController;
import com.ratatouille.Models.Interfaces.IViewLayout;
import com.ratatouille.Models.Listeners.BottomBarListener;
import com.ratatouille.Models.Events.Request.Request;
import com.ratatouille.Models.Events.SourceInfo;
import com.ratatouille.Views.ViewFactory;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import io.vavr.control.Try;

public class Manager implements ISubController {
    //SYSTEM ------------------------------------------------------------------------------- //
    private static final String TAG = "Manager_MenuFragments";

    //LAYOUT ------------------------------------------------------------------------------- //
    public final Context                    context;
    protected final ArrayList<IViewLayout>   ViewsFragments;
    protected final View                    ViewContainer;

    //FUNCTIONAL --------------------------------------------------------------------------- //
    public final BottomBarListener          bottomBarListener;
    protected final SourceInfo              sourceInfo;
    protected final FragmentManager         fragmentManager;
    protected final ManagerActionFactory    ManagerAction;
    protected final ManagerRequestFactory   ManagerRequest;

    public Integer[] LIST_INDEX_VIEW ;
    public final int MAIN_VIEW_INDEX;

    public Integer      IndexOnMain;
    public Integer      IndexFrom;

    private long lastActionTime = 0;
    //DATA ------------------------------------------------------------------------------- //
    private Object data;
    private Object dataAlternative;

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
    public Object getDataAlternative(){
        return dataAlternative;
    }
    public void setDataAlternative(Object data){
        this.dataAlternative = data;
    }

    //ShowPages -------------------------------------------------------------------------- //
    @Override
    public void showMain(){
        IndexOnMain  = MAIN_VIEW_INDEX;
        IndexFrom = MAIN_VIEW_INDEX;
        loadFragment( getPositionOfView( IndexOnMain ) );

        getSourceInfo().setIndex_TypeView(IndexOnMain);
    }

    @Override
    public void changeOnMain(int indexMain) {}

    @Override
    public void changeOnMain(int indexMain, Object msg) {

        data = msg;
        IndexFrom = IndexOnMain;
        IndexOnMain = indexMain;

        closeView();
        loadFragment( getPositionOfView( IndexOnMain ) );

        getSourceInfo().setIndex_TypeView(IndexOnMain);
    }
    @Override
    public void closeView() {
        ViewsFragments.get( getPositionOfView(IndexFrom) ).EndAnimations();
        Try.run(() -> TimeUnit.MILLISECONDS.sleep(300));
    }

    @Override
    public void goBack(){
        IndexFrom = IndexOnMain;

        ViewsFragments.get( getPositionOfView(IndexFrom) ).EndAnimations();
        Log.d(TAG, "goBack: stackCount ->" + fragmentManager.getBackStackEntryCount());
        if(fragmentManager.getBackStackEntryCount() > 1){
            IndexOnMain = Integer.parseInt(Objects.requireNonNull(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 2).getName()));
            getSourceInfo().setIndex_TypeView(IndexOnMain);
        }

        new Handler(Looper.getMainLooper()).postDelayed( fragmentManager::popBackStack,300);
    }

    @Override
    public void goBack(int indexFrom){
        IndexFrom = indexFrom;

        ViewsFragments.get( getPositionOfView(IndexFrom) ).EndAnimations();
        Log.d(TAG, "goBack: stackCount ->" + fragmentManager.getBackStackEntryCount());
        if(fragmentManager.getBackStackEntryCount() > 2){
            IndexOnMain = Integer.parseInt(Objects.requireNonNull(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 3).getName()));
            getSourceInfo().setIndex_TypeView(IndexOnMain);
        }

        new Handler(Looper.getMainLooper()).postDelayed( ()->{
            fragmentManager.popBackStack();
            fragmentManager.popBackStack();
        },300);
    }

    public void useTemporaryNewView(int indexView,int typeManager){
        try{

            IViewLayout view = new ViewFactory().createView(typeManager,indexView,this);
            fragmentManager.beginTransaction()
                    .replace(ViewContainer.getId(), (Fragment) view, String.valueOf(indexView))
                    .setReorderingAllowed(true)
                    .addToBackStack(String.valueOf(indexView))
                    .commit();
            IndexFrom = IndexOnMain;
            IndexOnMain = indexView;
        }
        catch (IllegalAccessException | InstantiationException e) { Log.e(TAG, "Manager_MenuFragments: ", e); }
    }

    private void loadFragment(int indexViewFragmentArray){
        fragmentManager.beginTransaction()
                .replace(ViewContainer.getId(), (Fragment) ViewsFragments.get(indexViewFragmentArray), String.valueOf(IndexOnMain))
                .setReorderingAllowed(true)
                .addToBackStack( String.valueOf(IndexOnMain) )
                .commit();
    }
    private int getPositionOfView(int indexFragment){
        for(int position = 0 ; position < LIST_INDEX_VIEW.length; position++)
            if(indexFragment == LIST_INDEX_VIEW[position] )  return position;
        return 0;
    }

    //FUNCTIONAL ------------------------------------------------------------------------------- //
    public void HandleAction(Action action){
        final long DEBOUNCE_INTERVAL = 1000; // 1 second


        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActionTime < DEBOUNCE_INTERVAL) {
            return;
        }

        lastActionTime = currentTime;

        action.setManager(this);
        action.setSourceInfo(getSourceInfo());
        new Thread(() -> ManagerAction.MapAction(action) ).start();
    }
    public void HandleRequest(Request request){
        request.setManager(this);
        request.setSourceInfo(getSourceInfo());
        new Thread(() -> ManagerRequest.MapRequest(request) ).start();
    }

    public void hideBottomBar(){
        bottomBarListener.hideBottomBar();
    }
    public void showBottomBar(){
        bottomBarListener.showBottomBar();
    }

}