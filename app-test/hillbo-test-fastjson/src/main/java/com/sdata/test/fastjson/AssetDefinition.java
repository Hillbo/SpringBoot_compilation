package com.sdata.test.fastjson;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

public class AssetDefinition implements Serializable {

    private static final long serialVersionUID = -9019971746126765693L;
    private Integer asset_cata;

    private String origins;

    private String office_id;

    private Integer asset_type;

    private String source_name;

    private int source_type;

    private String asset_desc;

    private Long records_count = -1L;

    private String storage_detail;

    private String dataset_file_path;

    private Integer whetherDelete;

    private int column_count;

    private int association_count;

    private int schedule_count;

    private String catalog_id;

    private String catalog_name;

    private Boolean indexed;

    private String share_token;

    private Integer data_type;

    private Integer isModify;

    private String sheetName;

    private int rid;

    private int maxIndex;

    private int judgeApp;

    private Object data_count;

    private Integer import_data_type;

    private String icon;

    private String is_temple_data;

    private Integer favorite;

    private String asset_owner;

    private Boolean is_shared;

    private String assetcata_name;

    private String assetcata_id;

    private Date pay_time;

    //是否关联资源目录
    private int mapping_flag;

    //是否关联质量
    private int quality_mapping_flag;

    private String model_type;

    private int execute_smart_recommend;

    private String assetVersion;

    private String tableName;

    private String plugin_id;

    private String tableAlias;

    private int sql_asset_flag;

    public Integer getAsset_cata() {
        return asset_cata;
    }

    public void setAsset_cata(Integer asset_cata) {
        this.asset_cata = asset_cata;
    }

    public String getOrigins() {
        return origins;
    }

    public void setOrigins(String origins) {
        this.origins = origins;
    }

    public String getOffice_id() {
        return office_id;
    }

    public void setOffice_id(String office_id) {
        this.office_id = office_id;
    }

    public Integer getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(Integer asset_type) {
        this.asset_type = asset_type;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public int getSource_type() {
        return source_type;
    }

    public void setSource_type(int source_type) {
        this.source_type = source_type;
    }

    public String getAsset_desc() {
        return asset_desc;
    }

    public void setAsset_desc(String asset_desc) {
        this.asset_desc = asset_desc;
    }

    public Long getRecords_count() {
        return records_count;
    }

    public void setRecords_count(Long records_count) {
        this.records_count = records_count;
    }

    public String getStorage_detail() {
        return storage_detail;
    }

    public void setStorage_detail(String storage_detail) {
        this.storage_detail = storage_detail;
    }

    public String getDataset_file_path() {
        return dataset_file_path;
    }

    public void setDataset_file_path(String dataset_file_path) {
        this.dataset_file_path = dataset_file_path;
    }

    public Integer getWhetherDelete() {
        return whetherDelete;
    }

    public void setWhetherDelete(Integer whetherDelete) {
        this.whetherDelete = whetherDelete;
    }

    public int getColumn_count() {
        return column_count;
    }

    public void setColumn_count(int column_count) {
        this.column_count = column_count;
    }

    public int getAssociation_count() {
        return association_count;
    }

    public void setAssociation_count(int association_count) {
        this.association_count = association_count;
    }

    public int getSchedule_count() {
        return schedule_count;
    }

    public void setSchedule_count(int schedule_count) {
        this.schedule_count = schedule_count;
    }

    public String getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(String catalog_id) {
        this.catalog_id = catalog_id;
    }

    public String getCatalog_name() {
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public Boolean getIndexed() {
        return indexed;
    }

    public void setIndexed(Boolean indexed) {
        this.indexed = indexed;
    }

    public String getShare_token() {
        return share_token;
    }

    public void setShare_token(String share_token) {
        this.share_token = share_token;
    }

    public Integer getData_type() {
        return data_type;
    }

    public void setData_type(Integer data_type) {
        this.data_type = data_type;
    }

    public Integer getIsModify() {
        return isModify;
    }

    public void setIsModify(Integer isModify) {
        this.isModify = isModify;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public int getJudgeApp() {
        return judgeApp;
    }

    public void setJudgeApp(int judgeApp) {
        this.judgeApp = judgeApp;
    }

    public Object getData_count() {
        return data_count;
    }

    public void setData_count(Object data_count) {
        this.data_count = data_count;
    }

    public Integer getImport_data_type() {
        return import_data_type;
    }

    public void setImport_data_type(Integer import_data_type) {
        this.import_data_type = import_data_type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIs_temple_data() {
        return is_temple_data;
    }

    public void setIs_temple_data(String is_temple_data) {
        this.is_temple_data = is_temple_data;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public String getAsset_owner() {
        return asset_owner;
    }

    public void setAsset_owner(String asset_owner) {
        this.asset_owner = asset_owner;
    }

    public Boolean getIs_shared() {
        return is_shared;
    }

    public void setIs_shared(Boolean is_shared) {
        this.is_shared = is_shared;
    }

    public String getAssetcata_name() {
        return assetcata_name;
    }

    public void setAssetcata_name(String assetcata_name) {
        this.assetcata_name = assetcata_name;
    }

    public String getAssetcata_id() {
        return assetcata_id;
    }

    public void setAssetcata_id(String assetcata_id) {
        this.assetcata_id = assetcata_id;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public int getMapping_flag() {
        return mapping_flag;
    }

    public void setMapping_flag(int mapping_flag) {
        this.mapping_flag = mapping_flag;
    }

    public int getQuality_mapping_flag() {
        return quality_mapping_flag;
    }

    public void setQuality_mapping_flag(int quality_mapping_flag) {
        this.quality_mapping_flag = quality_mapping_flag;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public int getExecute_smart_recommend() {
        return execute_smart_recommend;
    }

    public void setExecute_smart_recommend(int execute_smart_recommend) {
        this.execute_smart_recommend = execute_smart_recommend;
    }

    public String getAssetVersion() {
        return assetVersion;
    }

    public void setAssetVersion(String assetVersion) {
        this.assetVersion = assetVersion;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPlugin_id() {
        return plugin_id;
    }

    public void setPlugin_id(String plugin_id) {
        this.plugin_id = plugin_id;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public int getSql_asset_flag() {
        return sql_asset_flag;
    }

    public void setSql_asset_flag(int sql_asset_flag) {
        this.sql_asset_flag = sql_asset_flag;
    }



}
