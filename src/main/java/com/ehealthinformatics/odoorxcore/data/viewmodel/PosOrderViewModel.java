package com.ehealthinformatics.odoorxcore.data.viewmodel;


import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ehealthinformatics.odoorxcore.data.dao.AccountBankStatementDao;
import com.ehealthinformatics.odoorxcore.data.dao.AccountBankStatementLineDao;
import com.ehealthinformatics.odoorxcore.data.dao.DaoRepo;
import com.ehealthinformatics.odoorxcore.data.dao.PosOrderDao;
import com.ehealthinformatics.odoorxcore.data.dao.PosOrderLineDao;
import com.ehealthinformatics.odoorxcore.data.dao.QueryFields;
import com.ehealthinformatics.odoorxcore.data.dto.AccountBankStatement;
import com.ehealthinformatics.odoorxcore.data.dto.PosOrder;

public class PosOrderViewModel extends ViewModel {

    private PosOrderDao posOrderDao;
    private PosOrderLineDao posOrderLineDao;
    private final MutableLiveData<PosOrder> selected = new MutableLiveData<>();
    AccountBankStatementDao accountBankStatementDao;
    AccountBankStatementLineDao accountBankStatementLineDao;

    public PosOrderViewModel(){
        DaoRepo daoRepo = DaoRepo.getInstance();
        this.posOrderDao = daoRepo.getDao(PosOrderDao.class);
        this.posOrderLineDao = daoRepo.getDao(PosOrderLineDao.class);
        this.accountBankStatementDao = daoRepo.getDao(AccountBankStatementDao.class);
        this.accountBankStatementLineDao = daoRepo.getDao(AccountBankStatementLineDao.class);
    }

    public void loadData(final int id) {
        new AsyncTask<Void,Void, PosOrder>() {
            @Override
            protected PosOrder doInBackground(Void... voids) {
                PosOrder posOrder;
                if(id > 0){
                    posOrder = posOrderDao.get(id, QueryFields.all());
                    posOrder.getLines().addAll(posOrderLineDao.fromPosOrder(posOrder, QueryFields.all()));
                    posOrder.getAccountBankStatements().addAll(accountBankStatementDao.posSessionStatements(posOrder.getSession()));
                    for(AccountBankStatement statement: posOrder.getAccountBankStatements()) {
                        statement.getStatements().addAll(accountBankStatementLineDao.forStatement(posOrder, statement, QueryFields.all()));
                    }
                } else {
                    posOrder = posOrderDao.newOrder();
                }
                return posOrder;
            }
            @Override
            protected void onPostExecute(PosOrder data) {
                selected.setValue(data);
            }
        }.execute();
    }

    public LiveData<PosOrder> getSelected() {
        return selected;
    }

}