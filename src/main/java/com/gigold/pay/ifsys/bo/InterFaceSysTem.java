/**
 * Title: InterFaceSysTem.java<br/>
 * Description: <br/>
 * Copyright: Copyright (c) 2015<br/>
 * Company: gigold<br/>
 *
 */
package com.gigold.pay.ifsys.bo;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gigold.pay.framework.core.Domain;

/**
 * Title: InterFaceSysTem<br/>
 * Description: <br/>
 * Company: gigold<br/>
 * 
 * @author xb
 * @date 2015年10月12日上午9:36:56
 *
 */

@Component
@Scope("prototype")
public class InterFaceSysTem extends Domain implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    private int id;
    
    private String isValid;

    /**
     * @return the isValid
     */
    public String getIsValid() {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the sysName
     */
    public String getSysName() {
        return sysName;
    }

    /**
     * @param sysName
     *            the sysName to set
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    /**
     * @return the sysDesc
     */
    public String getSysDesc() {
        return sysDesc;
    }

    /**
     * @param sysDesc
     *            the sysDesc to set
     */
    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc;
    }

    private String sysName;
    private String sysDesc;
}
