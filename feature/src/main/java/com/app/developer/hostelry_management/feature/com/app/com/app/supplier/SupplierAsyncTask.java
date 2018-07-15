package com.app.developer.hostelry_management.feature.com.app.com.app.supplier;

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.content.Context;
import android.os.AsyncTask;

import com.app.developer.hostelry_management.feature.com.app.AppDatabase;
import com.app.developer.hostelry_management.feature.com.app.dao.SupplierDao;
import com.app.developer.hostelry_management.feature.com.app.model.Supplier;

import java.lang.ref.WeakReference;
import java.util.List;

public class SupplierAsyncTask extends AsyncTask<Void, Void, List<Supplier>> {

    private WeakReference<Activity> weakActivity;
    private Context context;

    public SupplierAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Supplier> doInBackground(Void... Void) {
        SupplierDao supplierDao = AppDatabase.getAppDatabase(context).supplierDao();
        return supplierDao.getAll();
    }

    @Override
    protected void onPostExecute(List<Supplier> suppliers) {
        super.onPostExecute(suppliers);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
