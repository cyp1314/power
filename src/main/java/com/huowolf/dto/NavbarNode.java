package com.huowolf.dto;

import java.util.List;

/**
 * Created by huowolf on 2018/12/1.
 */
public class NavbarNode {

    /**
     * 显示的文本
     */
    private String title;

    /**
     * 图标
     */
    private String icon="";

    /**
     * 是否默认展开
     */
    private boolean spread=false;

    /**
     * 跳转地址
     */
    private String href;

    /**
     * 子菜单数据
     */
    private List<NavbarNode> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<NavbarNode> getChildren() {
        return children;
    }

    public void setChildren(List<NavbarNode> children) {
        this.children = children;
    }
}
