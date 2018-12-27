package com.example.project.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.project.R;
import com.example.project.util.CommonUtil;
import com.example.project.util.DialogUtils;


/**
 * Base activity if toolbar is needed.
 * If toolbar is not required then use {@link BaseActivity}
 */

public abstract class ToolbarBaseActivity<P extends IBasePresenter> extends BaseActivity<P> {

    private static final int SEARCH_QUERY_THRESHOLD = 2;
    private TextView mToolbarTitle;
    private NavigationView mLeftNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mProgressBarLayout;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private String mSearchQuery;
    private SearchView mSearchView;
    private Menu mOverflowMenu;
    private MenuItem mLogoutMenu;
    private boolean isSearchExpanded;
    //Maintain the state of server call as onCreateOptionsMenu will be called multiple times.
    private boolean isServerCallInProgress;
    private SearchView.OnQueryTextListener mTextSearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String newText) {
            // your text view here
            if (isSearchExpanded) {
                mProgressBar.setVisibility(View.GONE);
                mProgressBarLayout.setVisibility(View.VISIBLE);
                if (newText.length() > SEARCH_QUERY_THRESHOLD) {
                    makeSearchRequest(newText);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return true;
        }
    };
    private DialogUtils.DialogClickListener mLogoutDialogClickListener = new DialogUtils.DialogClickListener() {
        @Override
        public void onPositiveButtonClick() {
            CommonUtil.logout(mContext);
        }

        @Override
        public void onNegativeButtonClicked() {
            // Do nothing when negative button is clicked.
        }
    };
    private MenuItem.OnMenuItemClickListener mMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            DialogUtils.showLogoutAlertDialog(mContext, getString(R.string.logout_text),
                    getString(R.string.logout_message_text), getString(R.string.confirm_text),
                    getString(R.string.cancel_text), mLogoutDialogClickListener);
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View toolBarLayout = inflater.inflate(R.layout.activity_toolbar_base, null);
        setSupportActionBar((Toolbar) toolBarLayout.findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        inflater.inflate(layoutResID, (ViewGroup) toolBarLayout.findViewById(R.id.view_container), true);
        super.setContentView(toolBarLayout);

        init();
        setNavigationDrawerVisibility();
    }

    private void setNavigationDrawerVisibility() {
        if (!isNavigationDrawerRequired() && mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void closeNavigationDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    public void setDrawerListener(DrawerLayout.DrawerListener listener) {
        if (mDrawerLayout != null) {
            mDrawerLayout.addDrawerListener(listener);
        }
    }

    public void openNavigationDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private void makeSearchRequest(String searchQuery) {
        mSearchQuery = searchQuery;
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBarLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the title on toolbar sets hamburger icon
     *
     * @param toolbarTitle        - title for toolbar
     * @param isHomeButtonEnabled - should show hamburger
     */
    protected void setupToolbar(String toolbarTitle, boolean isHomeButtonEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeButtonEnabled);
        getSupportActionBar().setHomeButtonEnabled(isHomeButtonEnabled);
        setToolbarTitle(toolbarTitle);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                CommonUtil.hideKeyboard(mContext, mDrawerLayout);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(isHomeButtonEnabled);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    protected void setToolbarTitle(String title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Display back button instead of hamburger icon
     *
     * @param isUpEnabled - show back button if true
     */
    protected void setDisplayHomeAsUpEnabled(boolean isUpEnabled) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isUpEnabled);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mToolbarTitle != null) {
            Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
                params.setMargins(0, 0, (int) (actionBarHeight * 1.5), 0);
                mToolbarTitle.setLayoutParams(params);
            }
        }
        return super.onCreateOptionsMenu(mOverflowMenu);
    }

    private void showHideLogoutMenuOption(boolean shouldHide) {
        if (mLogoutMenu != null) {
            mLogoutMenu.setVisible(!shouldHide);
        }
    }

    protected void setHomeButtonAsBack() {
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    /**
     * Enables/Disables the search option
     *
     * @param isEnable - enable search is true else disable.
     */
    public void setSearchViewStatus(boolean isEnable) {
        if (mSearchView != null) {
            mSearchView.setEnabled(isEnable);
        }
    }

    private void init() {
        mToolbarTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mProgressBarLayout = (LinearLayout) findViewById(R.id.layout_base_progress_bar);
        mProgressBarLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        mProgressBar = (ProgressBar) mProgressBarLayout.findViewById(R.id.progress_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onNetworkCallsStarted() {
        isServerCallInProgress = false;
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (mLogoutMenu != null) {
            mLogoutMenu.setEnabled(false);
        }
    }

    protected void onNetworkCallsFinished() {
        isServerCallInProgress = true;
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        if (mLogoutMenu != null) {
            mLogoutMenu.setEnabled(true);
        }
    }

    protected NavigationView getNavigationView() {
        return mLeftNavigationView;
    }

    /**
     * Defines the availability of navigation drawer on toolbar activity
     *
     * @return true if Navigation Drawer is required else false.
     */
    protected abstract boolean isNavigationDrawerRequired();


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public interface NetworkCallStateListener {
        //Called when network calls are made.
        void onNetworkCallStarted();

        //Called when all the network calls are finished.
        void onNetworkCallFinished();
    }
}
