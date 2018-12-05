package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 销售退货计划单主信息实体bean
 * @author lb
 *
 */
public class SaleReturnPlanHeadInfoBean {
	/*
	 * 销售退货计划单号
	 */
	private String orderCode;	
	/*
	 * 仓库
	 */
	private String warehouseCode;
	/*
	 * 创建时间
	 */
	private String createDate;
	/*
	 * 创建人编码
	 */
	private String createUserCode;
	/*
	 * 创建人名称
	 */
	private String createUserName;
	/*
	 * 审核日期(yyyy/MM/dd HH:mm:ss)
	 */
	private String operuserDate;
	/*
	 * 审核人编码
	 */
	private String operuserCode;
	/*
	 * 审核人名称
	 */
	private String operuserName;
	/*
	 * 物流公司编码
	 */
	private String logisticsCompanyCode;
	/*
	 * 快递单号
	 */
	private String expressNo;
	/*
	 * 手机号
	 */
	private String phno;
	/*
	 * 客户
	 */
	private String stna;
	/*
	 * 货主
	 */
	private String goodsOwner;	
	/*
	 * 备注
	 */
	private String remark;
	/*
	 * 备用字段1
	 */
	private String gwf1;
	/*
	 * 备用字段2
	 */
	private String gwf2;
	/*
	 * 备用字段3
	 */
	private String gwf3;
	/*
	 * 备用字段4
	 */
	private String gwf4;
	/*
	 * 备用字段5
	 */
	private String gwf5;
	
	private List<SaleReturnPlanDetailInfoBean> detailList;

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getOperuserDate() {
		return operuserDate;
	}

	public void setOperuserDate(String operuserDate) {
		this.operuserDate = operuserDate;
	}

	public String getOperuserCode() {
		return operuserCode;
	}

	public void setOperuserCode(String operuserCode) {
		this.operuserCode = operuserCode;
	}

	public String getOperuserName() {
		return operuserName;
	}

	public void setOperuserName(String operuserName) {
		this.operuserName = operuserName;
	}

	public String getGoodsOwner() {
		return goodsOwner;
	}

	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGwf1() {
		return gwf1;
	}

	public void setGwf1(String gwf1) {
		this.gwf1 = gwf1;
	}

	public String getGwf2() {
		return gwf2;
	}

	public void setGwf2(String gwf2) {
		this.gwf2 = gwf2;
	}

	public String getGwf3() {
		return gwf3;
	}

	public void setGwf3(String gwf3) {
		this.gwf3 = gwf3;
	}

	public String getGwf4() {
		return gwf4;
	}

	public void setGwf4(String gwf4) {
		this.gwf4 = gwf4;
	}

	public String getGwf5() {
		return gwf5;
	}

	public void setGwf5(String gwf5) {
		this.gwf5 = gwf5;
	}

	public List<SaleReturnPlanDetailInfoBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SaleReturnPlanDetailInfoBean> detailList) {
		this.detailList = detailList;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getLogisticsCompanyCode() {
		return logisticsCompanyCode;
	}

	public void setLogisticsCompanyCode(String logisticsCompanyCode) {
		this.logisticsCompanyCode = logisticsCompanyCode;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getStna() {
		return stna;
	}

	public void setStna(String stna) {
		this.stna = stna;
	}

}
