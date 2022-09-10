package com.iunctainc.iuncta.app.util.misc;



import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.iunctainc.iuncta.app.BuildConfig;
import com.iunctainc.iuncta.app.R;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

/**
 * Created by jatin on 7/25/2018.
 */

public class BackStackManager {
    private HashMap<String, Stack<Fragment>> backStack;
    private static HashMap<String, BackStackManager> map = new HashMap<>();
    private FragmentManager manager;
    private String currentTab;
    @Nullable
    private StackListioner stackListioner;

    public String getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(String currentTab) {
        this.currentTab = currentTab;
    }

    private BackStackManager() {
        backStack = new HashMap<>();
    }

    public void setStackListioner(@Nullable StackListioner stackListioner) {
        this.stackListioner = stackListioner;
    }

    public static BackStackManager getInstance(FragmentActivity context, @NonNull String id) {
        BackStackManager _instance = map.get(id);
        if (_instance != null) {
            _instance.manager = context.getSupportFragmentManager();
            return _instance;
        } else {
            BackStackManager _instance1 = new BackStackManager();
            _instance1.manager = context.getSupportFragmentManager();
            map.put(id, _instance1);
            return _instance1;
        }
    }

    public static void shutdown(@NonNull String id) {
        map.remove(id);
    }

    public static void shutdownAll() {
        map.clear();
    }

    public void clear() {
        backStack.clear();
        currentTab = null;
    }

    public void pushFragment(int containorid, @NonNull String tag, boolean shouldAnimate) {
        if (stackListioner == null)
            return;
        currentTab = tag;
        Stack<Fragment> list = backStack.get(tag);
        Fragment fragment = null;
        if (list != null) {
            fragment = list.lastElement();
        }
        if (fragment == null) {
            fragment = stackListioner.getFragment(tag);
            Stack<Fragment> stack = new Stack<>();
            stack.add(fragment);
            backStack.put(tag, stack);
        }
        stackListioner.onFragmentChange(tag, fragment, false);

        FragmentTransaction ft = getFragmentTransction(shouldAnimate);
        ft.replace(containorid, fragment);
        ft.disallowAddToBackStack();
        ft.commitAllowingStateLoss();
    }

    public void pushSubFragment(@IdRes int containorid, @NonNull String tag, boolean shouldAnimate, boolean allowCache) {
        if (stackListioner == null)
            return;
        try {
            currentTab = tag;
            if (backStack.get(tag) == null)
                return;
            Fragment fragment = stackListioner.getFragment(tag);
            stackListioner.onFragmentChange(tag, fragment, true);
            if (allowCache)
                Objects.requireNonNull(backStack.get(tag)).add(fragment);

            FragmentTransaction ft = getFragmentTransction(shouldAnimate);
            ft.replace(containorid, fragment);
            ft.disallowAddToBackStack();
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushSubFragment(@IdRes int containorid, @NonNull String tag, boolean shouldAnimate) {
        pushSubFragment(containorid, tag, shouldAnimate, true);
    }

    private FragmentTransaction getFragmentTransction(boolean shouldAnimate) {
        FragmentTransaction ft = manager.beginTransaction();
        if (BuildConfig.EnableAnim && shouldAnimate)
            ft.setCustomAnimations(R.anim.alpha_undim,
                    R.anim.alpha_dim,
                    R.anim.alpha_undim,
                    R.anim.alpha_dim);
        return ft;
    }


    public boolean setCurrentFragments(@IdRes int containorid) {
        if (stackListioner == null)
            return false;
        try {
            if (currentTab != null) {
                if (backStack.containsKey(currentTab)) {
                    FragmentTransaction ft = manager.beginTransaction();
                    Stack<Fragment> s = backStack.get(currentTab);
                    Fragment fragment = Objects.requireNonNull(s).lastElement();
                    stackListioner.onFragmentChange(currentTab, fragment, s.size() > 1);
                    ft.replace(containorid, fragment);
                    ft.commitAllowingStateLoss();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public boolean popLastFragment(@IdRes int containorid, @NonNull String tag, boolean animate) {
        if (stackListioner == null)
            return false;
        try {
            Stack<Fragment> fragments = backStack.get(tag);
            if (fragments != null && fragments.size() > 1) {
                backStack.get(tag).pop();
                FragmentTransaction ft = getFragmentTransction(animate);
                Fragment fragment = Objects.requireNonNull(backStack.get(tag)).lastElement();
                stackListioner.onFragmentChange(currentTab, fragment, backStack.get(tag).size() > 1);

                ft.replace(containorid, fragment);
                ft.disallowAddToBackStack();
                ft.commitAllowingStateLoss();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public interface StackListioner {

        @NonNull
        Fragment getFragment(@NonNull String tag);

        void onFragmentChange(@NonNull String tag, @NonNull Fragment fragment, boolean isSubFragment);

    }

}
