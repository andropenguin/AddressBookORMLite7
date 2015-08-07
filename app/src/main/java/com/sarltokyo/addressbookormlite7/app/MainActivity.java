package com.sarltokyo.addressbookormlite7.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.sarltokyo.addressbookormlite7.adapter.AdapterEx;
import com.sarltokyo.addressbookormlite7.asynctask.PersonLoader;
import com.sarltokyo.addressbookormlite7.core.ContextLogic;
import com.sarltokyo.addressbookormlite7.core.ContextLogicFactory;
import com.sarltokyo.addressbookormlite7.db.OpenHelper;
import com.sarltokyo.addressbookormlite7.entity.Person;

import java.sql.SQLException;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Person>> {

    private final static String TAG = MainActivity.class.getSimpleName();

    private ListView mListView;
    private OpenHelper mOpenHelper;

    public final static String TYPE = "type";
    public final static String CREATE_DATA_TYPE = "create";
    public final static String UPDATE_DATA_TYPE = "update";

    private AdapterEx mPersonsAdapter;
    private ContextLogic mContextLogic;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo: ここ重要。DBをproduction用、test用に切り替えられるようにする。
        mContextLogic = ContextLogicFactory.createContextLogic(this);
        mOpenHelper = mContextLogic.createOpenHelper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        showProgress();
        LoaderManager manager = getSupportLoaderManager();
        if (manager.getLoader(0) != null) {
            manager.destroyLoader(0);
        }
        manager.initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.input_add) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            intent.putExtra(TYPE, CREATE_DATA_TYPE);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProgress() {
        Log.d(TAG, "showProcess()");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("running");
        mProgressDialog.show();
    }

    private void dismissProgress() {
        Log.d(TAG, "dismissProgress()");
        mProgressDialog.dismiss();
    }

    @Override
    public Loader<List<Person>> onCreateLoader(int id, Bundle bundle) {
        return new PersonLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Person>> loader, List<Person> data) {
        Log.d(TAG, "PersonLoader finished");
        dismissProgress();

        if (data != null) {
            mPersonsAdapter = new AdapterEx(this, data);
            mListView = (ListView)findViewById(android.R.id.list);
            mListView.setAdapter(mPersonsAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    updateAddress(view);
                }
            });

            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return deletePerson(view);
                }
            });
        } else {
            // todo
            Toast.makeText(this, "Some error occured.", Toast.LENGTH_LONG).show();;
        }

        LoaderManager manager = getSupportLoaderManager();
        // 既にローダーがある場合は破棄
        if (manager.getLoader(0) != null) {
            manager.destroyLoader(0);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Person>> loader) {
        // nop
    }

    private void updateAddress(View view) {
        Log.d(TAG, "updateAddress is called");

        int positon;
        String name = null;

        if (!(view.getTag() instanceof Integer)) {
            return;
        }
        positon = (Integer)view.getTag();
        if (positon < 0 | mPersonsAdapter.getCount() <= positon) {
            return;
        }

        Log.d(TAG, "position in updateAddress = " + positon);
        Person person = mPersonsAdapter.getItem(positon);
        Log.d(TAG, "person in updateAddress = " + person);
        try {
            name = mOpenHelper.findPerson(person.getName()).getName();
            Log.d(TAG, "name in updateAddress = " + name);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        if (name == null) return; // todo: 多分、ありえない
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        intent.putExtra(TYPE, UPDATE_DATA_TYPE);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    private boolean deletePerson(View view) {
        int positon;

        if (!(view.getTag() instanceof Integer)) {
            return true;
        }
        positon = (Integer)view.getTag();
        if (positon < 0 | mPersonsAdapter.getCount() <= positon) {
            return true;
        }

        Person person = mPersonsAdapter.getItem(positon);
        try {
            boolean isDeleted = mOpenHelper.deletePerson(person.getName());
            if (isDeleted) {
                Toast.makeText(MainActivity.this, person.getName() + " was deleted.", Toast.LENGTH_LONG).show();

                List<Person> list = mOpenHelper.findPerson();
                mPersonsAdapter = new AdapterEx(this, list);
                mListView.setAdapter(mPersonsAdapter);
                mPersonsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, person.getName() + " cannot be deleted.", Toast.LENGTH_LONG).show();
            }
            return true;
        } catch (SQLException e) {
            Toast.makeText(MainActivity.this, person.getName() + " cannot be deleted.", Toast.LENGTH_LONG).show();
            Log.e(TAG, e.getMessage());
            return true;
        }
    }

}
