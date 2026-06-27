package com.xyzfood.Render.services;

import java.lang.classfile.instruction.TableSwitchInstruction;
import java.util.List;

import com.xyzfood.Render.models.Table;
import com.xyzfood.Render.models.User;
import com.xyzfood.Render.dto.Response.APIResponse;

public class AdminService {
    private final ApiService apiService = ApiService.getInstance();

    public List<User> getCustomers() {
        return apiService.customers();
    }

    public List<Table> getTables() {
        return apiService.tables();
    }

    public Table getTable(int Tablenumber) {
        return apiService.getTable(Tablenumber);
    }

    public APIResponse saveTable(Table table) {
        return apiService.saveTable(table);
    }

    public APIResponse deleteTable(int tableNumber) {
        return apiService.deleteTable(tableNumber);
    }
}

