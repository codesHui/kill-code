package com.imi.model.product;

import com.softvan.model.Paging;

public class AuditInfo extends Paging {
    private int currentRowNo;

    public int getCurrentRowNo() {
        return currentRowNo;
    }

    public void setCurrentRowNo(int currentRowNo) {
        this.currentRowNo = currentRowNo;
    }
}
