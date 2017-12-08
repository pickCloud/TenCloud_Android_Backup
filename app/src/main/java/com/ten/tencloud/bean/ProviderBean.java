package com.ten.tencloud.bean;

import java.util.List;

/**
 * 运营商
 * Created by lxq on 2017/12/4.
 */

public class ProviderBean {

    /**
     * provider : 微软云
     * regions : ["美国东部"]
     */

    private String provider;
    private boolean isSelect;
    private List<String> regions;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }
}
