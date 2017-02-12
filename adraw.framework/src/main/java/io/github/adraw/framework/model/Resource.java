package io.github.adraw.framework.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限类型：menu、button、url
     */
    private String type;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限字符串
     */
    private String permission;

    private String icon;

    private Integer sort;

    private String url;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 状态值
     */
    private Boolean status;

    /**
     * 父ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_at")
    private Date updateAt;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取权限类型：menu、button、url
     *
     * @return type - 权限类型：menu、button、url
     */
    public String getType() {
        return type;
    }

    /**
     * 设置权限类型：menu、button、url
     *
     * @param type 权限类型：menu、button、url
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取权限名称
     *
     * @return name - 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名称
     *
     * @param name 权限名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取权限字符串
     *
     * @return permission - 权限字符串
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限字符串
     *
     * @param permission 权限字符串
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取资源描述
     *
     * @return description - 资源描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置资源描述
     *
     * @param description 资源描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取状态值
     *
     * @return status - 状态值
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态值
     *
     * @param status 状态值
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取父ID
     *
     * @return parent_id - 父ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父ID
     *
     * @param parentId 父ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return create_by
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * @return create_at
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * @param createAt
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * @return update_by
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return update_at
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * @param updateAt
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}