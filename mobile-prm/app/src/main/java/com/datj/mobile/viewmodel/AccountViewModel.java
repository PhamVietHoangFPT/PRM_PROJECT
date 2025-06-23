package com.datj.mobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.datj.mobile.data.remote.response.AccountResponse;
import com.datj.mobile.data.repository.AccountRepository;

public class AccountViewModel extends ViewModel {
    private final AccountRepository accountRepository;

    public AccountViewModel() {
        this.accountRepository = new AccountRepository();
    }

    public LiveData<AccountResponse> getAccount() {
        return accountRepository.getAccount();
    }
}
